import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static java.lang.StrictMath.floor;

/**
 * Created by martin on 4/16/16.
 */
public class Game {
    List<Card> cards = new ArrayList<>();
    List<Player> players;
    boolean gameOver = false;
    Player roundWinner = null;
    Card winningCard = null;
    public Game() {
    }

    void createDeck() {
        cards.clear();
        for (int a = 1; a < 6; a++){
            switch (a){
                case 1:
                    cards.add(new Princess(this));
                    cards.add(new Countess(this));
                    cards.add(new King(this));
                case 2:
                    cards.add(new Prince(this));
                    cards.add(new Handmaid(this));
                    cards.add(new Baron(this));
                    cards.add(new Priest(this));
                case 3:
                case 4:
                case 5:
                    cards.add(new Guard(this));
                    break;

            }
        }

        //TODO take out : testing only
        /*
        Random rnjesus = new Random();
        int lim = cards.size();
        for (int z = 0; z < lim; z++){
            System.out.println(cards.remove((int)floor(rnjesus.nextDouble()*cards.size())));
        }*/
    }

    void runGame(List<Player> players) {

        //notify everyone that the game is on
        for (Player p : players) {
            p.notifyStartGame(players.size(), p.getId());
        }

        //run the rounds until the game is over (a player has achieved 4 points)
        int count = 1;
        this.players = players;
        do {
            System.out.println("Round " + count);
            runRound(players);

            //display current scores
            for(Player p : players){
                System.out.println("Player " + p.getId() + ": " + p.getScore());
            }

        } while(!gameOver);
    }

    public List<Player> getPlayers() {
        return players;
    }

    void runRound(List<Player> players) { // play until decksize <= 1
        Card chosenCard;
        createDeck();
        for (Player p : players) {
            p.setupRound(drawCard());
        }

        boolean roundOver = false;

        while(!roundOver) { // one iteration through the players
            for (Player p : players) {
                if (!p.isActive()) continue;    //skip this player if they are out of the round

                //notify everyone of whose turn it is
                for (Player curr : players) {
                    curr.notifyTurnStarted(p);
                }
                //TODO takeout
                System.out.println("It is the turn of PLAYER " + p.getId());

                chosenCard = p.doTurn(drawCard());

                /* TODO takeout????? redundant ?????
                //notify all players that this is the card being played
                for(Player pl : players){
                    pl.notifyChooseCard(chosenCard);
                } */

                //notify everyone of card chosen to be played by the current turn taker
                for (Player curr : players) {
                    curr.notifyCardPlayed(p, chosenCard);
                }

                chosenCard.play(p);

                roundOver = isRoundOver(cards);
                if(roundOver) break;
            }
            //TODO takeout
            System.out.println("There are " + cards.size() + " cards left in the deck");

        }

        //TODO takeout
        System.out.println("The round is over. Deck contains " + cards.size() + "cards");

        roundWinner.incScore(winningCard);
        if (roundWinner.getScore() == 4) gameOver = true;
    }

    boolean isRoundOver(List<Card> cards){
        boolean cardsRemaining = false;
        int count = 0;
        boolean roundOver;
        Player lastPlayer = null;

        if (cards.size() > 1) cardsRemaining = true;

        for( Player p : players){
            if (p.isActive()){
                lastPlayer = p;
                count++;
            }
        }
        if (count == 1) {
            System.out.println("Win by default");
            roundWinner = lastPlayer;
            winningCard = lastPlayer.getCurrentCard();
        }

        if (!cardsRemaining && count > 1) {
            finalCompareHands();
            System.out.println("Win by contest");
        }

        roundOver = (count == 1) || !cardsRemaining;
        if(cards.size() > 1) cardsRemaining = true;
        return roundOver;
    }

    void finalCompareHands(){
        List<Player> activeOnes = new ArrayList<>();
        for(Player p : players){
            if (p.isActive())
                activeOnes.add(p);
        }

        //compare the hands of everyone remaining, highest wins, ties are won by the previous player (lower id)
        Player p1;
        Player p2;
        Player best = null;
        for(int i = 0; i < activeOnes.size() - 1; i++){
            p1 = activeOnes.get(i);
            p2 = activeOnes.get(i+1);
            if (p1.getCurrentCard().getRank() > p1.getCurrentCard().getRank()){
                best = p2;
                p1.setActive(false);
            }
            else{
                best = p1;
                p2.setActive(false);
            }
        }
        roundWinner = best;
        winningCard = best.getCurrentCard();
    }
    public Player playerHasWon(){
        Player livePlayer = null;
        int count = 0;
         for(Player p : players){
             if (p.isActive()){
                 count++;
                 livePlayer = p;
             }
         }
         if (count > 1) return null;
         return livePlayer;
     }
     public Player findPlayer(int targetId){
         Iterator<Player> itr = players.iterator();
         Player temp;
         int id;
         while(itr.hasNext()){
             temp = itr.next();
             id = temp.getId();
             if (id == targetId){
                 return temp;
             }
         }
         System.out.println("Player not found (game.java)");
         return null;
     }

    public Card drawCard(){
        Random rnjesus = new Random();
        return cards.remove((int)floor(rnjesus.nextDouble()*cards.size()));
    }
}

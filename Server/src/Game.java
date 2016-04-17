import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static java.lang.StrictMath.floor;
import static java.lang.StrictMath.round;

/**
 * Created by martin on 4/16/16.
 */
public class Game {
    List<Card> cards = new ArrayList<>();
    List<Player> players;
    boolean gameOver = false;
    public Game() {
    }

    void createDeck() {
        /*for (int c = 0; c < 12; c++) {
            cards.add(new DumbCard(this));
        }*/
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
        //run the rounds until the game is over (a player has achieved 4 points)
        int count = 1;
        do {
            System.out.println("Round " + count);
            runRound(players);

            //display current scores
            for(Player p : players){
                System.out.println("Player " + p.getId() + ": " + p.getScore());
            }

        } while(!gameOver);
    }
    void runRound(List<Player> players) { // play until decksize <= 1
        Card chosenCard;
        this.players = players;
        createDeck();
        Player lastPlayerStanding = null;
        for (Player p : players) {
            p.setupRound(drawCard());
        }

        do { // one iteration through the players
            for (Player p : players) {
                if (!p.isActive()) continue;    //skip this player if they are out of the round

                //notify everyone of whose turn it is
                for (Player curr : players) {
                    curr.notifyTurnStarted(p);
                }
                //TODO takeout
                System.out.println("It is the turn of PLAYER " + p.getId());

                chosenCard = p.doTurn(drawCard());

                //notify everyone of card chosen to be played by the current turn taker
                for (Player curr : players) {
                    curr.notifyCardPlayed(p, chosenCard);
                }

                chosenCard.play(p);
                if (cards.size() <= 1) gameOver = true; //continue playing until one card left (reserved card)

                lastPlayerStanding = playerHasWon();
                if ( lastPlayerStanding != null)//if there is only player then he/she wins the round
                    gameOver = true;
                    break;
            }
            //TODO takeout
            System.out.println("There are " + cards.size() + " cards left in the deck");

        } while (cards.size() > 1 && gameOver == false);

        //TODO takeout
        System.out.println("The round is over. Deck contains " + cards.size() + "cards");

        lastPlayerStanding.incScore();
        if (lastPlayerStanding.getScore() == 4) gameOver = true;
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

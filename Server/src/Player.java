import java.util.List;

/**
 * Created by martin on 4/16/16.
 */
public class Player {
    final Client client;
    final int id;
    Card currentCard;
    final Game game;
    private boolean active;
    private boolean isProtected;
    int score = 0;

    public Player(Client client, int id, Game game) {
        this.client = client;
        this.id = id;
        this.game = game;
        active = true;
    }

    public void setupRound(Card card) {
        currentCard = card;
        client.setupRound(card);
    }

    public Card doTurn(Card card) {
        if (this.isProtected()) this.setProtected(false); //protection ends after a round.

        int playedCard = -1;

        // check for the countess
        if (card.getRank() == 7){ //new card is the countess
            if (currentCard.getRank() == 5 || currentCard.getRank() == 6){
                playedCard = 1;
            }
        } else if (currentCard.getRank() == 7) { //current card is the countess
            if (card.getRank() == 5 || card.getRank() == 6){
                playedCard = 0;
            }
        }
        List<Player> players = game.getPlayers();
        if (playedCard == -1) { //if the countess hasn't been triggered then let the player choose
            playedCard = client.getPlayedCard(card);

        }
        if (playedCard == 1) { //new card chosen
            for(Player p : players){
                p.notifyPlayerPlay(this, card);
            }
            return card;
        }

        // old card chosen
        for(Player p : players){
            p.notifyPlayerPlay(this, currentCard);
        }
        Card temp = currentCard;
        currentCard = card;
        return temp;

    }

    public Player choosePlayer(){
        int id = client.choosePlayer();
        Player chosen =  game.findPlayer(id);
        List<Player> players = game.getPlayers();
        for(Player p : players){
            p.notifyChoosePlayer(chosen);
        }
        return chosen;
    }
    public int chooseCard(){
        return client.chooseCard();
    }

    public void notifyTurnStarted(Player p) {
        client.notifyTurnStarted(p);
    }

    public void notifyCardPlayed(Player p, Card c) {
        client.notifyCardPlayed(p, c);
    }

    public void notifyChosen(Card card){
        //TODO takeout
        System.out.println("You (player " + this.getId() +") have been chosen to receive the effects of " + card);
        client.notifyChosen(card);
    }

    public void notifyStartGame(Card card, int numPlayers, int currId){
        client.notifyStartGame(card, numPlayers, currId);
    }

    public void notifyStartRound(Card card){
        client.notifyStartRound(card);
    }

    public void notifyChooseCard(Card card){
        client.notifyChooseCard(card);
    }

    public void notifyChoosePlayer(Player p){
        client.notifyChoosePlayer(p);
    }

    public void notifyPlayerPlay(Player p, Card c){
        client.notifyPlayerPlay(p, c);
    }

    public void notifyPlayerWon(Player p, Card c){
        client.notifyPlayerWon(p, c);
    }

    public void notifyPlayerLost(Player p, Card c){
        client.notifyPlayerLost(p, c);
    }

    public void notifyBaron(Player p1, Player p2, Player winner, Card loserCard){
        client.notifyBaron(p1, p2, winner, loserCard);
    }

    public void notifyKing(Player p1, Player p2){
        client.notifyKing(p1, p2);
    }

    public void notifyGuard(Player p1, Player p2, int guess, boolean correct){
        client.notifyGuard(p1, p2, guess, correct);
    }

    public void notifyPrince(Player p1, Player p2, Card card){
        client.notifyPrince(p1, p2, card);
    }

    public void notifyPriest(Player p1, Player p2){
        client.notifyPriest(p1, p2);
    }

    public Client getClient() {
        return client;
    }
    public int getId() {
        return id;
    }
    public Card getCurrentCard() {
        return currentCard;
    }

    public void setActive(boolean active) {
        this.active = active;
        if (!active){
            List<Player> players = game.getPlayers();
            for(Player p : players){
                p.notifyPlayerLost(this, currentCard);
            }
        }
    }

    public boolean isActive() {
        return active;
    }

    public boolean isProtected() {
        return isProtected;
    }

    public void setProtected(boolean aProtected) {
        isProtected = aProtected;
    }

    public int incScore(Card card){
        List<Player> players = game.getPlayers();
        for( Player p : players ){
            p.notifyPlayerWon(this, card);
        }
        return ++this.score;
    }

    public int getScore() {
        return score;
    }
}

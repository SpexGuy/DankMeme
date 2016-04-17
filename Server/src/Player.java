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

        if (playedCard == -1) { //if the countess hasn't been triggered then let the player choose
            playedCard = client.getPlayedCard(card);
        }
        if (playedCard == 1)
            return card;
        Card temp = currentCard;
        currentCard = card;
        return temp;

    }

    public Player choosePlayer(){
        int id = client.choosePlayer();
        return game.findPlayer(id);
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

    public int incScore(){
        return ++this.score;
    }

    public int getScore() {
        return score;
    }
}

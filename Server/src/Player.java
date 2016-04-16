/**
 * Created by martin on 4/16/16.
 */
public class Player {
    final Client client;
    final int id;
    Card currentCard;

    public Player(Client client, int id) {
        this.client = client;
        this.id = id;
    }

    public void setupRound(Card card) {
        currentCard = card;
        client.setupRound(card);
    }

    public Card doTurn(Card card) {
        int playedCard = client.getPlayedCard(card);
        if (playedCard == 1)
            return card;
        Card temp = currentCard;
        currentCard = card;
        return temp;
    }

    public void notifyTurnStarted(Player p) {
        client.notifyTurnStarted(p);
    }

    public void notifyCardPlayed(Player p, Card c) {
        client.notifyCardPlayed(p, c);
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
}

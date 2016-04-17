/**
 * Created by martin on 4/16/16.
 */
public interface Client {

    void setupRound(Card startCard);
    // 0 -> old card, 1 -> new card
    int getPlayedCard(Card newCard);
    void notifyTurnStarted(Player p);
    void notifyCardPlayed(Player p, Card card);
    int choosePlayer();
    int chooseCard();
    void notifyChosen(Card c);

}

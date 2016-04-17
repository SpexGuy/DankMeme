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
    void notifyStartGame(Card card, int numPlayers, int currId);
    void notifyStartRound(Card card);
    void notifyChooseCard(Card card);
    void notifyChoosePlayer(Player p);
    void notifyPlayerPlay(Player p, Card c);
    void notifyPlayerWon(Player p, Card c);
    void notifyPlayerLost(Player p, Card c);
    void notifyKing(Player p1, Player p2);
    void notifyGuard(Player p1, Player p2, int guess, boolean correct);
    void notifyPrince(Player p1, Player p2, Card card);
    void notifyPriest(Player p1, Player p2);
    void notifyBaron(Player p1, Player p2, Player winner, Card loserCard);

}

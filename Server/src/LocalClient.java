import java.util.Scanner;

/**
 * Created by martin on 4/16/16.
 */
public class LocalClient implements Client {
    private Card currentcard;
    final Scanner stdin;

    public LocalClient(Scanner stdin) {
        this.stdin = stdin;
    }

    @Override
    public void setupRound(Card startCard) {
        currentcard = startCard;
    }

    @Override
    public int getPlayedCard(Card newCard) {
        System.out.print("Play " + currentcard + " (0) or " + newCard + " (1)? ");
        int input = stdin.nextInt();
        if (input == 0) {
            System.out.println("Playing " + currentcard);
            currentcard = newCard;
            return 0;
        } else {
            System.out.println("Playing " + newCard);
            return 1;
        }
    }

    public int choosePlayer(){
        System.out.print("Choose a player id: ");
        int chosenId = stdin.nextInt();
        return chosenId;
    }
    public int chooseCard(){
        System.out.print("Predict a card (rank): ");
        int prediction = stdin.nextInt();
        return prediction;
    }

    @Override
    public void notifyTurnStarted(Player p) {
    }

    @Override
    public void notifyCardPlayed(Player p, Card card) {
    }

    @Override
    public void notifyChosen(Card c){
        System.out.println("You have been chosen to receive the effects of " + c);
    }


    public void notifyStartGame(Card card, int numPlayers, int currId){
    }

    public void notifyStartRound(Card card){
    }

    public void notifyChooseCard(Card card){
    }

    public void notifyChoosePlayer(Player p){
    }

    public void notifyPlayerPlay(Player p, Card c){
    }

    public void notifyPlayerWon(Player p, Card c){
    }

    public void notifyPlayerLost(Player p, Card c){
    }

    public void notifyBaron(Player p1, Player p2, Player winner, Card loserCard){
    }

    public void notifyKing(Player p1, Player p2){
    }

    public void notifyGuard(Player p1, Player p2, int guess, boolean correct){
    }

    public void notifyPrince(Player p1, Player p2, Card card){
    }

    public void notifyPriest(Player p1, Player p2){
    }

}

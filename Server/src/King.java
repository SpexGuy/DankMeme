/**
 * Created by Jordan on 4/16/2016.
 *
 * Trade hands with another player of your choice.
 */
public class King extends Card {
    final int rank = 6;

    public King(Game game) {
        super(game);
    }

    public void play(Player whoPlayed){
        Player opponent = whoPlayed.choosePlayer();
        assert(opponent.isActive());
        if (opponent.isProtected()) return; //no effect if chosen opponent is protected

        Card temp = opponent.getCurrentCard();
        opponent.currentCard = whoPlayed.currentCard;
        whoPlayed.currentCard = temp;

    }

    @Override
    public int getRank() {
        return rank;
    }

    private void notifyTheChosenOne(Card c, Player chosenOne){
        chosenOne.notifyChosen(c);
    }
}

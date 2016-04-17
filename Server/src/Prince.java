/**
 * Created by Jordan on 4/16/2016.
 *
 * Choose any player including yourself to discard his or her hand and draw a new card.
 */
public class Prince extends Card {
    final int rank = 5;

    public Prince(Game game) {
        super(game);
    }

    public void play(Player whoPlayed){
        Player chosenPlayer = whoPlayed.choosePlayer();
        assert(chosenPlayer.isActive());
        notifyTheChosenOne(this, chosenPlayer);
        if (chosenPlayer.isProtected()) return; //no effect if chosen player is protected

        //force discard
        //check for the princess
        if (chosenPlayer.currentCard.getRank() == 8)
            chosenPlayer.setActive(false);
        else {
            chosenPlayer.currentCard = game.drawCard();
        }
    }

    @Override
    public int getRank() {
        return rank;
    }

    private void notifyTheChosenOne(Card c, Player chosenOne){
        chosenOne.notifyChosen(c);
    }
}
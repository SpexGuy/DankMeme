import java.util.List;

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

    public void play(Player whoPlayed) {
        Player chosenPlayer = whoPlayed.choosePlayer();
        assert (chosenPlayer.isActive());
        if (chosenPlayer.isProtected()) return; //no effect if chosen player is protected


        //notify everyone of who is discarding
        List<Player> players = game.getPlayers();
        for (Player p : players) {
            p.notifyPrince(whoPlayed, chosenPlayer, chosenPlayer.getCurrentCard());
        }

        //force discard
        //check for the princess
        if (chosenPlayer.currentCard.getRank() == 8) {
            chosenPlayer.setActive(false);
        }
        else {
            chosenPlayer.currentCard = game.drawCard();
            chosenPlayer.notifyReplaceCard(chosenPlayer.currentCard);
        }
    }

    @Override
    public int getRank() {
        return rank;
    }

}

import java.util.List;

/**
 * Created by Jordan on 4/16/2016.
 *
 * Look at another player's hand.
 */
public class Priest extends Card {
    final int rank = 2;

    public Priest(Game game) {
        super(game);
    }

    public void play(Player whoPlayed){
        Player opponent = whoPlayed.choosePlayer();
        assert(opponent.isActive());
        notifyTheChosenOne(this, opponent);
        if (opponent.isProtected()) return; //no effect if chosen player is protected

        List<Player> players = game.getPlayers();
        for (Player p : players){
            p.notifyPriest(whoPlayed, opponent);
        }

        //TODO display opponent's current card;
        System.out.println(opponent.getCurrentCard());
    }

    @Override
    public int getRank() {
        return rank;
    }

    private void notifyTheChosenOne(Card c, Player chosenOne){
        chosenOne.notifyChosen(c);
    }
}

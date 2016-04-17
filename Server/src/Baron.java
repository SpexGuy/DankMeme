import java.util.List;

/**
 * Created by Jordan on 4/16/2016.
 *
 * You and another player compare hands. Lower value is out.
 */
public class Baron extends Card {
    final int rank = 3;

    public Baron(Game game) {
        super(game);
    }

    public void play(Player whoPlayed){
        Player opponent = whoPlayed.choosePlayer();
        assert(opponent.getId() != whoPlayed.getId());
        assert(opponent.isActive());
        notifyTheChosenOne(this, opponent);
        if (opponent.isProtected()) return; //no effect if opponent is protected

        //opponent is valid --> compare hands
        int oppRank = opponent.getCurrentCard().getRank();
        if (oppRank > this.rank){
            whoPlayed.setActive(false);
            List<Player> players = game.getPlayers();
            for (Player p : players){
                p.notifyBaron(whoPlayed, opponent, opponent, opponent.getCurrentCard());
            }

        } else if (oppRank < this.rank){
            opponent.setActive(false);
            List<Player> players = game.getPlayers();
            for (Player p : players){
                p.notifyBaron(whoPlayed, opponent, whoPlayed, this);
            }

        } else {
            //tie = nothing happens
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

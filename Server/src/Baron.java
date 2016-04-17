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
        if (opponent.isProtected()) return; //no effect if opponent is protected

        //opponent is valid --> compare hands
        int oppRank = opponent.getCurrentCard().getRank();
        if (oppRank > this.rank){
            whoPlayed.setActive(false);
            for (Player p : game.getPlayers()) {
                p.notifyBaron(whoPlayed, opponent, opponent, whoPlayed.currentCard);
            }

        } else if (oppRank < this.rank){
            opponent.setActive(false);
            for (Player p : game.getPlayers()) {
                p.notifyBaron(whoPlayed, opponent, whoPlayed, opponent.currentCard);
            }

        } else {
            //tie = nothing happens
        }

    }

    @Override
    public int getRank() {
        return rank;
    }
}

/**
 * Created by Jordan on 4/16/2016.
 *
 * until your next turn, ignore all effects from other players cards.
 */
public class Handmaid extends Card {

    final int rank = 4;

    public Handmaid(Game game) {
        super(game);
    }

    public void play(Player whoPlayed){
        whoPlayed.setProtected(true);
    }

    @Override
    public int getRank() {
        return rank;
    }

}

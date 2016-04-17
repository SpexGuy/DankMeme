/**
 * Created by Jordan on 4/16/2016.
 */
public class Princess extends Card {
    final int rank = 8;

    public Princess(Game game) {
        super(game);
    }

    public void play(Player whoPlayed){
        whoPlayed.setActive(false);
    }

    @Override
    public int getRank() {
        return rank;
    }
}

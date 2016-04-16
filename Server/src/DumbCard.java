/**
 * Created by martin on 4/16/16.
 */
public class DumbCard extends Card {
    public DumbCard(Game game) {
        super(game);
    }

    @Override
    int getRank() {
        return -1;
    }

    @Override
    void play(Player whoPlayed) {
        System.out.println("Player "+whoPlayed+" is dumb.");
    }

    @Override
    public String toString() {
        return "Dumb Card";
    }
}

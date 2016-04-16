/**
 * Created by martin on 4/16/16.
 */
public abstract class Card {
    protected final Game game;

    public Card(Game game) {
        this.game = game;
    }

    abstract int getRank();
    abstract void play(Player whoPlayed);
}

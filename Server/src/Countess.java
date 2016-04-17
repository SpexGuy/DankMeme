/**
 * Created by Jordan on 4/16/2016.
 *
 * If you have this card and the king or prince in your hand, you must discard this card.
 */
public class Countess extends Card {
    final int rank = 7;

    public Countess(Game game) {
        super(game);
    }

    public void play(Player whoPlayed){
        //this cards discard condition is checked in player.java
        //if chosen merely discard with no other effects
    }

    @Override
    public int getRank() {
        return rank;
    }
}

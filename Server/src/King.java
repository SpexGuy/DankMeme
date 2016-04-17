import java.util.List;

/**
 * Created by Jordan on 4/16/2016.
 *
 * Trade hands with another player of your choice.
 */
public class King extends Card {
    final int rank = 6;

    public King(Game game) {
        super(game);
    }

    public void play(Player whoPlayed){
        Player opponent = whoPlayed.choosePlayer();
        assert(opponent.isActive());
        if (opponent.isProtected()) return; //no effect if chosen opponent is protected

        whoPlayed.notifySwap(opponent, opponent.currentCard);
        opponent.notifySwap(whoPlayed, whoPlayed.currentCard);

        Card temp = opponent.getCurrentCard();
        opponent.currentCard = whoPlayed.currentCard;
        whoPlayed.currentCard = temp;

        //notify everyone of the swap
        List<Player> players = game.getPlayers();
        for(Player p : players){
            p.notifyKing(whoPlayed, opponent);
        }

    }

    @Override
    public int getRank() {
        return rank;
    }
}

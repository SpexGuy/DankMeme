import java.util.List;

/**
 * Created by Jordan on 4/16/2016.
 *
 * Name a non guard card and choose another player. If that player has that card he or she is out of the round
 */
public class Guard extends Card {
    final int rank = 1;

    public Guard(Game game) {
        super(game);
    }

    public void play(Player whoPlayed){
        Player opponent = whoPlayed.choosePlayer(this);
        while (opponent.getId() == whoPlayed.getId()){
            opponent = whoPlayed.choosePlayer(this);
        }
        assert(opponent.isActive());
        if (opponent.isProtected()) return; //no effect if chosen player is protected

        int predictedCard;
        do {
            predictedCard = whoPlayed.chooseCard();
        } while(predictedCard == 1);

        List<Player> players = game.getPlayers();
        if (opponent.getCurrentCard().getRank() == predictedCard){
            opponent.setActive(false);
            for(Player p : players){
                p.notifyGuard(whoPlayed, opponent, predictedCard, true);
            }
        } else {
            for(Player p : players){
                p.notifyGuard(whoPlayed, opponent, predictedCard, false);
            }
        }

    }

    @Override
    public int getRank() {
        return rank;
    }
}

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
        Player opponent = whoPlayed.choosePlayer();
        while (opponent.getId() == whoPlayed.getId()){
            opponent = whoPlayed.choosePlayer();
        }
        assert(opponent.isActive());
        notifyTheChosenOne(this, opponent);
        if (opponent.isProtected()) return; //no effect if chosen player is protected

        int predictedCard;
        do {
            predictedCard = whoPlayed.chooseCard();
        } while(predictedCard == 1);

        if (opponent.getCurrentCard().getRank() == predictedCard)
            opponent.setActive(false);
    }

    @Override
    public int getRank() {
        return rank;
    }

    private void notifyTheChosenOne(Card c, Player chosenOne){
        chosenOne.notifyChosen(c);
    }
}
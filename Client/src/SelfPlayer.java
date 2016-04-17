/**
 * Created by martin on 4/17/16.
 */
public class SelfPlayer extends Player {
    final Game game;
    final Hand hand;
    CardType currentCard = null;
    CardType secondCard = null;

    public SelfPlayer(Game game, int id) {
        super(id);
        this.game = game;
        this.hand = new Hand(game, this, Game.WIDTH/2, Game.HEIGHT - CardType.height);
    }

    public void setup(CardType card) {
        currentCard = card;
        secondCard = null;
        hand.setup();
    }

    void addChoice(CardType option) {
        assert(secondCard == null);
        secondCard = option;
    }

    public void chooseCard(int card) {
        if (card == 1) {
            game.discard(1);
            secondCard = null;
        } else {
            game.discard(0);
            currentCard = secondCard;
            secondCard = null;
        }
    }

    public CardType removeCard() {
        assert(secondCard == null);
        CardType value = currentCard;
        currentCard = null;
        return value;
    }

    public void replace(CardType card) {
        currentCard = card;
    }


}

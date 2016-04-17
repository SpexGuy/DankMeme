import java.awt.*;

/**
 * Created by martin on 4/17/16.
 */
public class SelfPlayerView extends PlayerView {
    final Hand hand;

    public SelfPlayerView(Game game, SelfPlayer p) {
        super(game, 0, Game.HEIGHT - CardType.height, Game.WIDTH, CardType.height,
                new CardAccordion(50, Game.HEIGHT - 50 - CardAccordion.cardHeight, p.history), p);
        hand = new Hand(game, p, Game.WIDTH/2, y);

    }

    @Override
    void drawCards(Graphics2D g) {
        hand.draw(g);
    }

    @Override
    boolean hover(int mx, int my) {
        if (super.hover(mx, my)) return true;
        return hand.hover(mx, my);
    }

    @Override
    boolean click(int mx, int my) {
        if (super.click(mx, my)) return true;
        return hand.click(mx, my);
    }

    @Override
    public void activate(CardType card) {
        pickable = (card == CardType.Prince);
    }
}

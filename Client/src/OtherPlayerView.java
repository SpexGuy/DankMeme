import java.awt.*;

/**
 * Created by martin on 4/17/16.
 */
public class OtherPlayerView extends PlayerView {
    private static final int totalHeight = Game.HEIGHT - CardType.height;
    private static final int playerHeight = totalHeight / 3;
    private static final int playerWidth = Game.WIDTH;

    private static final int cardOffset = 10;
    private static final int cardHeight = playerHeight - 2*cardOffset;
    private static final double scale = (double) cardHeight / CardType.height;
    private static final int cardWidth = (int) (scale * CardType.width);

    public OtherPlayerView(Game game, int idx, Player p) {
        super(game, 0, idx * playerHeight, playerWidth, playerHeight,
                new CardAccordion(cardOffset + cardWidth + 50 + cardOffset, idx * playerHeight + cardOffset, p.history), p);
    }

    void drawCards(Graphics2D g) {
        if (p.active) {
            g.drawImage(back, cardOffset, y + cardOffset, cardWidth, cardHeight, null);
            if (p.isChoosing) {
                g.drawImage(back, cardOffset + 60, y + cardOffset, cardWidth, cardHeight, null);
            }
        }
    }
}

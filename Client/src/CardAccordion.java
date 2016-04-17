import java.awt.*;
import java.util.List;

/**
 * Created by martin on 4/16/16.
 */
public class CardAccordion {
    private static final int expandTime = 300;
    private static final int closedWidth = 25;
    private static final int cardWidth = 50;
    private static final double scale = (double) cardWidth / CardType.width;
    private static final int cardHeight = (int) (scale * CardType.height);

    final int x, y;
    private final List<CardType> cards;
    private int currentTime = 0;
    private float currentWidth = 0;
    private boolean expanded = false;

    public CardAccordion(int x, int y, List<CardType> cards) {
        this.x = x;
        this.y = y;
        this.cards = cards;
    }

    void expand(boolean shouldExpand) {
        expanded = shouldExpand;
    }

    public void update(int dt) {
        if (expanded) {
            currentTime += dt;
            if (currentTime > expandTime)
                currentTime = expandTime;
        } else {
            currentTime -= dt;
            if (currentTime < 0)
                currentTime = 0;
        }

        int expandedWidth = cardWidth * (cards.size()-1);
        float timepos = (float) currentTime / expandTime;
        currentWidth = Util.lerp(timepos, closedWidth, expandedWidth);
    }

    public void draw(Graphics2D g) {

//        g.setColor(Color.RED);
//        if (expanded) {
//            g.drawRect(x-1, y-1, expandedWidth+2, cardHeight+2);
//        } else {
//            g.drawRect(x-1, y-1, closedWidth+2, cardHeight+2);
//        }

        for (int c = 0; c < cards.size(); c++) {
            float cardpos = c == 0 ? 0 : (float) c / (cards.size()-1);
            int xoff = (int) (cardpos * currentWidth);

            g.drawImage(cards.get(c).image, x+xoff, y, cardWidth, cardHeight, null);
        }
    }

    public boolean hover(int mx, int my) {
        int width = (int) currentWidth + cardWidth;

        int ax = mx - x;
        int ay = my - y;
        if (ax >= 0 && ax < width &&
            ay >= 0 && ay < cardHeight) {

            expanded = true;
            return true;
        }

        expanded = false;
        return false;
    }
}

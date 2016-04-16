import java.awt.*;
import java.util.ArrayList;
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
    private final List<CardType> cards = new ArrayList<>();
    private int currentTime = 0;
    private boolean expanded = false;

    public CardAccordion(int x, int y) {
        this.x = x;
        this.y = y;
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
    }

    public void draw(Graphics2D g) {
        int expandedWidth = cardWidth * (cards.size()-1);

//        g.setColor(Color.RED);
//        if (expanded) {
//            g.drawRect(x-1, y-1, expandedWidth+2, cardHeight+2);
//        } else {
//            g.drawRect(x-1, y-1, closedWidth+2, cardHeight+2);
//        }

        for (int c = 0; c < cards.size(); c++) {
            float timepos = (float) currentTime / expandTime;
            float width = Util.lerp(timepos, closedWidth, expandedWidth);

            float cardpos = c == 0 ? 0 : (float) c / (cards.size()-1);
            int xoff = (int) (cardpos * width);

            g.drawImage(cards.get(c).image, x+xoff, y, cardWidth, cardHeight, null);
        }
    }

    public void addCard(CardType card) {
        cards.add(card);
    }
    public void reset() {
        cards.clear();
    }
}

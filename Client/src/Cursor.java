import java.awt.*;

/**
 * Created by martin on 4/17/16.
 */
public class Cursor {
    private static final int width = 50;
    private static final double scale = (double) width / CardType.width;
    private static final int height = (int) (scale * CardType.height);

    private CardType card = null;
    private int x, y;

    public void show(CardType type) {
        card = type;
    }

    public void hide() {
        card = null;
    }

    public void hover(int mx, int my) {
        x = mx;
        y = my;
    }

    public void draw(Graphics2D g) {
        if (card != null) {
            g.drawImage(card.image, x, y, width, height, null);
        }
    }

}

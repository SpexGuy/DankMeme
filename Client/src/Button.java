import java.awt.*;

/**
 * Created by martin on 4/16/16.
 */
public abstract class Button {
    final int x, y, width, height;
    protected boolean hovered = false;

    protected Button(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    boolean hit(int mx, int my) {
        int ax = mx - x;
        if (ax < 0 || ax >= width) return false;
        int ay = my - y;
        if (ay < 0 || ay >= height) return false;
        return true;
    }

    boolean hover(int mx, int my) {
        hovered = hit(mx, my);
        return hovered;
    }

    boolean click(int mx, int my) {
        if (hit(mx, my)) {
            activate();
            return true;
        }
        return false;
    }

    abstract void draw(Graphics2D g);
    abstract void activate();
}

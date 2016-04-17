import java.awt.*;

/**
 * Created by martin on 4/16/16.
 */
public class Mouse {
    volatile Point pos;
    volatile Point click;

    public synchronized Point getPosIfUpdated() {
        Point value = pos;
        pos = null;
        return value;
    }

    public synchronized Point checkClick() {
        Point value = click;
        click = null;
        return value;
    }

    public synchronized void setClickPos(Point point) {
        click = point;
    }

    public void setPos(Point point) {
        pos = point;
    }
}

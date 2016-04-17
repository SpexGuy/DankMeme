/**
 * Created by martin on 4/17/16.
 */
public class TurnStartedNotification extends Notification {
    int player;

    public TurnStartedNotification(int player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "TurnStartedNotification{" +
                "player=" + player +
                '}';
    }
}

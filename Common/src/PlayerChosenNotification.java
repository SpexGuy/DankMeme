/**
 * Created by martin on 4/17/16.
 */
public class PlayerChosenNotification extends Notification {
    int id;
    public PlayerChosenNotification(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PlayerChosenNotification{" +
                "id=" + id +
                '}';
    }
}

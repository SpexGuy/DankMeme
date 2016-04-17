/**
 * Created by martin on 4/17/16.
 */
public class ReplaceNotification extends Notification {
    int rank;
    public ReplaceNotification(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "ReplaceNotification{" +
                "rank=" + rank +
                '}';
    }
}

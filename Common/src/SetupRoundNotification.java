/**
 * Created by martin on 4/17/16.
 */
public class SetupRoundNotification extends Notification {
    int rank;
    public SetupRoundNotification(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "SetupRoundNotification{" +
                "rank=" + rank +
                '}';
    }
}

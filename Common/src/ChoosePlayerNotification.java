/**
 * Created by martin on 4/17/16.
 */
public class ChoosePlayerNotification extends Notification {
    int rank;

    ChoosePlayerNotification(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "ChoosePlayerNotification{" +
                "rank=" + rank +
                '}';
    }
}

/**
 * Created by martin on 4/17/16.
 */
public class GetCardNotification extends Notification {
    int rank;
    public GetCardNotification(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "GetCardNotification{" +
                "rank=" + rank +
                '}';
    }
}

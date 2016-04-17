/**
 * Created by martin on 4/17/16.
 */
public class CardPlayedNotification extends Notification {
    final int id;
    final int rank;

    public CardPlayedNotification(int id, int rank) {
        this.id = id;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "CardPlayedNotification{" +
                "id=" + id +
                ", rank=" + rank +
                '}';
    }
}

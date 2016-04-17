/**
 * Created by martin on 4/17/16.
 */
public class PlayerLostNotification extends Notification {
    int id, rank;
    public PlayerLostNotification(int id, int rank) {
        this.id = id;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "PlayerLostNotification{" +
                "id=" + id +
                ", rank=" + rank +
                '}';
    }
}

/**
 * Created by martin on 4/17/16.
 */
public class PlayerWonNotification extends Notification {
    int id, rank;
    public PlayerWonNotification(int id, int rank) {
        this.id = id;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "PlayerWonNotification{" +
                "id=" + id +
                ", rank=" + rank +
                '}';
    }
}

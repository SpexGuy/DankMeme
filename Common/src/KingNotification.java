/**
 * Created by martin on 4/17/16.
 */
public class KingNotification extends Notification {
    int id1, id2;
    public KingNotification(int id1, int id2) {
        this.id1 = id1;
        this.id2 = id2;
    }

    @Override
    public String toString() {
        return "KingNotification{" +
                "id1=" + id1 +
                ", id2=" + id2 +
                '}';
    }
}

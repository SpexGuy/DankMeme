/**
 * Created by martin on 4/17/16.
 */
public class PriestNotification extends Notification {
    final int source;
    final int target;

    public PriestNotification(int source, int target) {
        this.source = source;
        this.target = target;
    }

    @Override
    public String toString() {
        return "PriestNotification{" +
                "source=" + source +
                ", target=" + target +
                '}';
    }
}

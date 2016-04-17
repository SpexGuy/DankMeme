/**
 * Created by martin on 4/17/16.
 */
public class SwapNotification extends Notification {
    final int source, rank;

    public SwapNotification(int source, int rank) {
        this.source = source;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "SwapNotification{" +
                "source=" + source +
                ", rank=" + rank +
                '}';
    }
}

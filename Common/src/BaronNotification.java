/**
 * Created by martin on 4/17/16.
 */
public class BaronNotification extends Notification {
    final int source;
    final int target;
    final boolean sourceWon;
    final int lostCard;

    public BaronNotification(int source, int target, boolean sourceWon, int lostCard) {
        this.source = source;
        this.target = target;
        this.sourceWon = sourceWon;
        this.lostCard = lostCard;
    }

    @Override
    public String toString() {
        return "BaronNotification{" +
                "source=" + source +
                ", target=" + target +
                ", sourceWon=" + sourceWon +
                ", lostCard=" + lostCard +
                '}';
    }
}

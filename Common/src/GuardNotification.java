/**
 * Created by martin on 4/17/16.
 */
public class GuardNotification extends Notification {
    final int source;
    final int target;
    final int guess;
    final boolean correct;

    public GuardNotification(int source, int target, int guess, boolean correct) {
        this.source = source;
        this.target = target;
        this.guess = guess;
        this.correct = correct;
    }

    @Override
    public String toString() {
        return "GuardNotification{" +
                "source=" + source +
                ", target=" + target +
                ", guess=" + guess +
                ", correct=" + correct +
                '}';
    }
}

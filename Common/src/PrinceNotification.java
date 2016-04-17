/**
 * Created by martin on 4/17/16.
 */
public class PrinceNotification extends Notification {
    int player, target, discard;
    public PrinceNotification(int player, int target, int discard) {
        this.player = player;
        this.target = target;
        this.discard = discard;
    }

    @Override
    public String toString() {
        return "PrinceNotification{" +
                "player=" + player +
                ", target=" + target +
                ", discard=" + discard +
                '}';
    }
}

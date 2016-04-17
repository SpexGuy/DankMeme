/**
 * Created by martin on 4/17/16.
 */
public class GameStartNotification extends Notification {
    int numPlayers;
    int currId;

    public GameStartNotification(int numPlayers, int currId) {
        this.numPlayers = numPlayers;
        this.currId = currId;
    }

    @Override
    public String toString() {
        return "GameStartNotification{" +
                "numPlayers=" + numPlayers +
                ", currId=" + currId +
                '}';
    }
}

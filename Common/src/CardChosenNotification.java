/**
 * Created by martin on 4/17/16.
 */
public class CardChosenNotification extends Notification {
    int card;

    public CardChosenNotification(int card) {
        this.card = card;
    }

    public int getCard() {
        return card;
    }

    @Override
    public String toString() {
        return "CardChosenNotification{" +
                "card=" + card +
                '}';
    }
}

import java.awt.*;

/**
 * Created by martin on 4/16/16.
 */
public class Hand {
    private final Game game;
    private final SelfPlayer player;
    private final int x, y;
    private int highlight = -1;

    public Hand(Game game, SelfPlayer player, int x, int y) {
        this.game = game;
        this.player = player;
        this.x = x;
        this.y = y;
    }

    public void setup() {
        highlight = -1;
    }

    void draw(Graphics2D g) {
        if (player.secondCard != null) {
            assert(player.currentCard != null);
            g.drawImage(player.currentCard.image, x - CardType.width, y, null);
            g.drawImage(player.secondCard.image, x, y, null);

            g.setColor(new Color(1f, 1f, 1f, 0.2f));
            if (highlight == 0) {
                g.fillRect(x - CardType.width, y, CardType.width, CardType.height);
            } else if (highlight == 1) {
                g.fillRect(x, y, CardType.width, CardType.height);
            }

        } else if (player.currentCard != null) {
            g.drawImage(player.currentCard.image, x - CardType.width/2, y, null);
        }
    }

    private int getCard(int mx, int my) {
        if (my < y) return -1;
        if (mx < x - CardType.width) return -1;
        if (mx >= x + CardType.width) return -1;

        // Ok, the mouse is on one of the cards. Which one?
        return (mx < x) ? 0 : 1;
    }

    boolean hover(int mx, int my) {
        if (player.secondCard == null) return false;

        highlight = getCard(mx, my);
        return highlight >= 0;
    }

    boolean click(int mx, int my) {
        if (player.secondCard == null) return false;

        int card = getCard(mx, my);
        if (card < 0) return false;

        // We are choosing a card!!
        player.chooseCard(card);
        return true;
    }
}

import java.awt.*;

/**
 * Created by martin on 4/17/16.
 */
public class PlayerView extends Button {
    private static final int totalHeight = Game.HEIGHT - CardType.height;
    private static final int playerHeight = totalHeight / 3;
    private static final int playerWidth = Game.WIDTH;

    private static final int cardOffset = 10;
    private static final int cardHeight = playerHeight - 2*cardOffset;
    private static final double scale = (double) cardHeight / CardType.height;
    private static final int cardWidth = (int) (scale * CardType.width);

    private static final Image back = ImageStore.get().getImage("back.png");

    final Game game;
    final Player p;
    final CardAccordion history;
    boolean pickable = false;

    public PlayerView(Game game, int idx, Player p) {
        super(0, idx * playerHeight, playerWidth, playerHeight);
        this.game = game;
        this.p = p;
        this.history = new CardAccordion(cardOffset + cardWidth + 50 + cardOffset, y + cardOffset, p.history);
    }

    void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.drawRoundRect(0, y, playerWidth, playerHeight, 10, 10);
        if (p.active) {
            if (p.protect) {
                g.setColor(new Color(1f,1f,1f,0.2f));
                g.fillRoundRect(0, y, playerWidth, playerHeight, 10, 10);
            }
            g.drawImage(back, cardOffset, y + cardOffset, cardWidth, cardHeight, null);
            if (p.isChoosing) {
                g.drawImage(back, cardOffset + 60, y + cardOffset, cardWidth, cardHeight, null);
            }
        }
        history.draw(g);
        if (p.active && pickable && hovered) {
            g.setColor(new Color(1f, 1f, 1f, 0.3f));
            g.fillRoundRect(0, y, playerWidth, playerHeight, 10, 10);
        }
    }

    @Override
    void activate() {
        if (pickable) {
            game.playerChosen(p);
        }
    }

    @Override
    boolean hover(int mx, int my) {
        super.hover(mx, my);
        return history.hover(mx, my);
    }

    public void update(int dt) {
        history.update(dt);
    }
}


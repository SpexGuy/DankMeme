import java.awt.*;

/**
 * Created by martin on 4/17/16.
 */
public abstract class PlayerView extends Button {

    protected static final Image back = ImageStore.get().getImage("back.png");

    final Game game;
    final Player p;
    final CardAccordion history;
    protected boolean pickable = false;

    public PlayerView(Game game, int x, int y, int width, int height, CardAccordion history, Player p) {
        super(x, y, width, height);
        this.game = game;
        this.p = p;
        this.history = history;
    }

    void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.drawRoundRect(x, y, width, height, 10, 10);
        if (p.active && p.protect) {
            g.setColor(new Color(1f,1f,1f,0.2f));
            g.fillRoundRect(x, y, width, height, 10, 10);
        }
        drawCards(g);
        history.draw(g);
        if (p.active && pickable && hovered) {
            g.setColor(new Color(1f, 1f, 1f, 0.3f));
            g.fillRoundRect(x, y, width, height, 10, 10);
        }
    }

    abstract void drawCards(Graphics2D g);

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

    @Override
    boolean click(int mx, int my) {
        if (pickable) return super.click(mx, my);
        return false;
    }

    public void update(int dt) {
        history.update(dt);
    }

    public void activate(CardType card) {
        pickable = true;
    }
    public void deactivate() {
        pickable = false;
    }
}


import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by martin on 4/17/16.
 */
public class TypeSelect {

    public static final int width = 100;
    public static final double scale = (double) width / CardType.width;

    final Game game;
    final List<CardButton> buttons;
    boolean visible = false;

    public TypeSelect(Game game) {
        this.game = game;
        buttons = new ArrayList<>();
        int numButtons = CardType.values().length
        for (int c = 0; c < numButtons; c++) {
            CardType card = CardType.values()[c];
            // TODO: init buttons
            Game.WIDTH;
            Game.HEIGHT;
            int x = ???;
            int y = ???;
            buttons.add(new CardButton(x, y, scale, card))
        }
    }

    public void draw(Graphics2D g) {
        if (!visible) return;
        // TODO: draw background
        for (CardButton b : buttons) {
            b.draw(g);
        }
    }

    public void click(int mx, int my) {
        if (!visible) return;
        for (CardButton b : buttons) {
            b.click(mx, my);
        }
    }

    public void hover(int mx, int my) {
        if (!visible) return;
        for (CardButton b : buttons) {
            b.hover(mx, my);
        }
    }

    public void show() {
        visible = true;
    }

    public void hide() {
        visible = false;
    }

    class CardButton extends Button {

        final CardType card;

        protected CardButton(int x, int y, double scale, CardType card) {
            super(x, y, (int) (CardType.width * scale), (int) (CardType.height * scale));
            this.card = card;
        }

        @Override
        void draw(Graphics2D g) {
            g.drawImage(card.image, x, y, width, height, null);
            if (hovered) {
                g.setColor(new Color(1f, 1f, 1f, 0.3f));
                g.drawRect(x, y, width, height);
            }
        }

        @Override
        void activate() {
            game.cardGuessed(card);
        }
    }
}

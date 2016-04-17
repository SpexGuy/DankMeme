import javax.smartcardio.Card;
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
        int numButtons = CardType.values().length - 1;
        int spaceBtw = 15;
        int middle = Game.WIDTH /2;
        int startX = (int)(middle - (CardType.width*scale/2*7 + spaceBtw*3));

        for (int c = 0; c < numButtons; c++) {
            CardType card = CardType.values()[c+1];

            // init buttons
            int x = (int) (startX + (c*CardType.width*scale));
            if (c > 0) x = x + (c*spaceBtw); //if not first button, take into account space btw buttons
            int y = (int) ((Game.HEIGHT/2) - (CardType.height*scale/2));
            buttons.add(new CardButton(x, y, scale, card));
        }
    }

    public void draw(Graphics2D g) {
        if (!visible) return;

        // draw background
        int thickness = 15;
        int width = (int) (CardType.width*scale*7 + 8*thickness);
        int height = (int) (CardType.height*scale + 2*thickness);
        int x = (Game.WIDTH - width) / 2;
        int y = (Game.HEIGHT - height) / 2;
        g.setColor(Color.blue);
        g.fillRoundRect(x, y, width, height, 10, 10);

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

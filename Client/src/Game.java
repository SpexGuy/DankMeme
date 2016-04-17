import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by martin on 4/16/16.
 */
public class Game {
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 640;

    private Window window;

    CardAccordion history = new CardAccordion(50, 50);

    private final Hand hand = new Hand(this, WIDTH/2, HEIGHT - CardType.height);

    private final Mouse mouse = new Mouse();

    public void mainLoop() {
        window = new Window(WIDTH, HEIGHT, new KeyInputHandler(), new MouseInputHandler());
        long lastLoopTime = System.currentTimeMillis();

        hand.setup(CardType.Guard);

        Image back = ImageStore.get().getImage("back.png");

        // keep looping round til the game ends
        while (true) {
            // work out how long its been since the last update, this
            // will be used to calculate how far the entities should
            // move this loop
            // It better not be over 2^31 ms.
            long now = System.currentTimeMillis();
            int delta = (int) (now - lastLoopTime);
            lastLoopTime = now;

            // Timed updates
            history.update(delta);

            // Hover updates
            Point newPos = mouse.getPosIfUpdated();
            if (newPos != null) {
                hand.hover(newPos.x, newPos.y);
                history.hover(newPos.x, newPos.y);
            }

            // Click updates
            Point clickPos = mouse.checkClick();
            if (clickPos != null) {
                hand.click(clickPos.x, clickPos.y);
            }

            // Get hold of a graphics context for the accelerated
            // surface and blank it out
            Graphics2D g = window.getRenderFrame();
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, WIDTH, HEIGHT);

            //g.drawImage(back, xPos, yPos, null);
            history.draw(g);
            hand.draw(g);

            window.finishRenderFrame(g);

            try { Thread.sleep(10); } catch (Exception ignored) {}

        }
    }

    public void chooseCard(CardType card) {
        hand.addChoice(card);
    }

    public void discard(CardType card, int id) {
        history.addCard(card);
        // server.chooseCard(id);
    }

    public void setup(CardType initial) {
        history.reset();
        hand.setup(initial);
    }

    public void lose() {
        history.addCard(hand.removeCard());
    }

    private class MouseInputHandler extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e) {
            mouse.setClickPos(e.getPoint());
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mouse.setPos(e.getPoint());
        }
        @Override
        public void mouseDragged(MouseEvent e) {
            // ignore drag events - treat them like moves.
            mouseMoved(e);
        }
    }

    private class KeyInputHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() >= '1' && e.getKeyChar() <= '8') {
                int idx = e.getKeyChar() - '1';
                chooseCard(CardType.values()[idx]);
            } else if (e.getExtendedKeyCode() == KeyEvent.VK_R) {
                setup(CardType.Guard);
            } else if (e.getExtendedKeyCode() == KeyEvent.VK_L) {
                lose();
            }
        }
    }

}

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

    CardAccordion test = new CardAccordion(50, 50);

    int xPos = 0;
    int yPos = 0;

    public void mainLoop() {
        window = new Window(WIDTH, HEIGHT, new KeyInputHandler(), new MouseInputHandler());
        long lastLoopTime = System.currentTimeMillis();

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

            test.update(delta);

            // Get hold of a graphics context for the accelerated
            // surface and blank it out
            Graphics2D g = window.getRenderFrame();
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, WIDTH, HEIGHT);

            //g.drawImage(back, xPos, yPos, null);
            test.draw(g);

            window.finishRenderFrame(g);

            try { Thread.sleep(10); } catch (Exception ignored) {}

        }
    }

    private class MouseInputHandler extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            xPos = e.getX();
            yPos = e.getY();
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
            if (e.getExtendedKeyCode() == KeyEvent.VK_O) {
                test.expand(true);
            } else if (e.getExtendedKeyCode() == KeyEvent.VK_C) {
                test.expand(false);
            } else if (e.getKeyChar() >= '1' && e.getKeyChar() <= '8') {
                int idx = e.getKeyChar() - '1';
                test.addCard(CardType.values()[idx]);
            } else if (e.getExtendedKeyCode() == KeyEvent.VK_R) {
                test.reset();
            }
        }
    }

}

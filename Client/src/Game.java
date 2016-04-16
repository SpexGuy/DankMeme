import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by martin on 4/16/16.
 */
public class Game {
    private Window window;

    public void mainLoop() {
        window = new Window(new KeyInputHandler(), new MouseInputHandler());
        long lastLoopTime = System.currentTimeMillis();

        // keep looping round til the game ends
        while (true) {
            // work out how long its been since the last update, this
            // will be used to calculate how far the entities should
            // move this loop
            // It better not be over 2^31 ms.
            long now = System.currentTimeMillis();
            int delta = (int) (now - lastLoopTime);
            lastLoopTime = now;

            // Get hold of a graphics context for the accelerated
            // surface and blank it out
            Graphics2D g = window.getRenderFrame();
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, 800, 600);


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
        }
    }

}

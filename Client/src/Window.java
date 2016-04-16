import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by martin on 4/16/16.
 */
public class Window extends Canvas {
    private List<Drawable> drawables = new ArrayList<>();
    private BufferStrategy strategy;

    public Window(KeyAdapter keyboard, MouseAdapter mouse) {
        // create a frame to contain our game
        JFrame container = new JFrame("DankMeme Client v0.0.1");

        // get hold the content of the frame and set up the resolution of the game
        JPanel panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(new Dimension(800,600));
        panel.setLayout(null);

        // setup our canvas size and put it into the content of the frame
        setBounds(0,0,800,600);
        panel.add(this);

        // Tell AWT not to bother repainting our canvas since we're
        // going to do that our self in accelerated mode
        setIgnoreRepaint(true);

        // finally make the window visible
        container.pack();
        container.setResizable(false);
        container.setVisible(true);

        // add a listener to respond to the user closing the window. If they
        // do we'd like to exit the game
        container.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // add a key input system to our canvas
        addKeyListener(keyboard);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);

        // request the focus so key events come to us
        requestFocus();

        // create the buffering strategy which will allow AWT
        // to manage our accelerated graphics
        createBufferStrategy(2);
        strategy = getBufferStrategy();
    }

    public Graphics2D getRenderFrame() {
        return (Graphics2D) strategy.getDrawGraphics();
    }

    public void finishRenderFrame(Graphics2D g) {
        g.dispose();
        strategy.show();
    }
}

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by martin on 4/16/16.
 */
public class Game {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 640;

    private ConcurrentLinkedQueue<Notification> notifications;

    private Window window;

    private final Hand hand = new Hand(this, WIDTH/2, HEIGHT - CardType.height);

    private final Mouse mouse = new Mouse();

    private List<Player> players;
    private List<PlayerView> playerViews;
    private Player self;

    public void mainLoop() {
        window = new Window(WIDTH, HEIGHT, new KeyInputHandler(), new MouseInputHandler());
        long lastLoopTime = System.currentTimeMillis();

        startGame(4, 1);
        setup(CardType.Guard);

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
            for (PlayerView p : playerViews) {
                p.update(delta);
            }

            // Hover updates
            Point newPos = mouse.getPosIfUpdated();
            if (newPos != null) {
                hand.hover(newPos.x, newPos.y);
                for (PlayerView p : playerViews) {
                    p.hover(newPos.x, newPos.y);
                }
            }

            // Click updates
            Point clickPos = mouse.checkClick();
            if (clickPos != null) {
                hand.click(clickPos.x, clickPos.y);
            }

            //currentMode.act();

            // Get hold of a graphics context for the accelerated
            // surface and blank it out
            Graphics2D g = window.getRenderFrame();
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, WIDTH, HEIGHT);

            //g.drawImage(back, xPos, yPos, null);
            //history.draw(g);
            for (PlayerView p : playerViews) {
                p.draw(g);
            }
            hand.draw(g);

            window.finishRenderFrame(g);

            try { Thread.sleep(10); } catch (Exception ignored) {}

        }
    }

    private abstract class Mode {
        abstract void act();
    }
    private class Idle extends Mode {
        void act() {
            Notification notif = notifications.poll();
            if (notif != null) {
                processNotification(notif);
            }
        }
    }
    private class King extends Mode {
        void act() {

        }
    }
    private class Guard extends Mode {
        void act() {

        }
    }
    private class Baron extends Mode {
        void act() {

        }
    }
    private class Prince extends Mode {
        void act() {

        }
    }
    private class Priest extends Mode {
        void act() {

        }
    }

    void processNotification(Notification notif) {

    }


    public void discard(CardType card, int id) {
        // history.addCard(card);
        // server.chooseCard(id);
    }


    public void startGame(int numPlayers, int myId) {
        players = new ArrayList<>();
        playerViews = new ArrayList<>();
        for (int c = 0; c < numPlayers; c++) {
            if (c != myId) {
                Player p = new Player(c);
                players.add(p);
                playerViews.add(new PlayerView(playerViews.size(), p));
            } else {
                self = new Player(c); // TODO: special self player
                players.add(self);
            }
        }
    }

    public void setup(CardType initial) {
        hand.setup(initial);
        for (Player p : players) {
            p.setup();
        }
    }

    public void chooseCard(CardType card) {
        hand.addChoice(card);

        if (card == CardType.Baron || card == CardType.Prince) {
            choosePlayer(card);
        }
    }

    public void choosePlayer(CardType card) {
        //choosePlayerDialog.show(card == CardType.Prince);
        System.out.println("Choose a player");
    }
    public void playerChosen(Player p) {
        //choosePlayerDialog.hide();
        // server.choosePlayer(p);
    }

    public void playerStart(Player p) {
        p.pickup();
    }

    public void playerPlayed(Player p, CardType card) {
        p.discard(card);
    }

    public void playerLost(Player p, CardType card) {
        p.lose(card);
    }

    public void playerWon(Player p, CardType card) {
        p.win(card);
    }

    public void lose() {
        //history.addCard(hand.removeCard());
    }

    public void baron(int p1, int p2, int winner, CardType loserCard) {

    }

    public void king(int p1, int p2) {

    }

    public void guard(int p1, int p2, CardType guess, boolean success) {

    }

    public void prince(int p1, int p2, CardType card) {

    }

    public void priest(int p1, int p2) {

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
            } else if (e.getExtendedKeyCode() == KeyEvent.VK_A) {
                Player p = playerViews.get(0).p;
                if (!p.isChoosing)
                    p.pickup();
                else
                    p.discard(CardType.values()[(int) (Math.random() * 8)]);
            } else if (e.getExtendedKeyCode() == KeyEvent.VK_B) {
                Player p = playerViews.get(1).p;
                if (!p.isChoosing)
                    p.pickup();
                else
                    p.discard(CardType.values()[(int) (Math.random() * 8)]);
            } else if (e.getExtendedKeyCode() == KeyEvent.VK_C) {
                Player p = playerViews.get(2).p;
                if (!p.isChoosing)
                    p.pickup();
                else
                    p.discard(CardType.values()[(int) (Math.random() * 8)]);
            }
        }
    }

}

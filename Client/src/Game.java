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

    ConcurrentLinkedQueue<Notification> notifications = new ConcurrentLinkedQueue<>();

    private Window window;

    private Show show = new Show();

    private final Mouse mouse = new Mouse();

    private final Server server;

    private final TypeSelect typeSelect = new TypeSelect(this);

    private final Cursor cursor = new Cursor();

    private boolean gameStarted;

    private List<Player> players;
    private List<PlayerView> playerViews;
    private SelfPlayer self;

    public Game(Server server) {
        this.server = server;
    }

    public void mainLoop() {
        window = new Window(WIDTH, HEIGHT, new KeyInputHandler(), new MouseInputHandler());
        long lastLoopTime = System.currentTimeMillis();

        Image back = ImageStore.get().getImage("back.png");

        Graphics2D g2 = window.getRenderFrame();
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(0, 0, WIDTH, HEIGHT);

        g2.setColor(Color.RED);
        g2.drawString("Waiting for players...", 100, 100);

        window.finishRenderFrame(g2);

        gameStarted = false;
        while (!gameStarted) {
            Notification notif = notifications.poll();
            if (notif != null) {
                processNotification(notif);
            }
        }

        // keep looping round til the game ends
        while (true) {
            // Server updates
            Notification notif = notifications.poll();
            if (notif != null) {
                processNotification(notif);
            }

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
            show.update(delta);

            // Hover updates
            Point newPos = mouse.getPosIfUpdated();
            if (newPos != null) {
                for (PlayerView p : playerViews) {
                    p.hover(newPos.x, newPos.y);
                }
                typeSelect.hover(newPos.x, newPos.y);
                cursor.hover(newPos.x, newPos.y);
            }

            // Click updates
            Point clickPos = mouse.checkClick();
            if (clickPos != null) {
                for (PlayerView p : playerViews) {
                    p.click(clickPos.x, clickPos.y);
                }
                typeSelect.click(clickPos.x, clickPos.y);
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
            typeSelect.draw(g);
            show.draw(g);
            cursor.draw(g);

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

    Player getPlayer(int id) {
        return players.get(id);
    }
    CardType getCard(int rank) {
        return CardType.values()[rank-1];
    }

    void processNotification(Notification notif) {
        System.out.println(notif);

        if (notif instanceof CardPlayedNotification) {
            CardPlayedNotification cpn = (CardPlayedNotification) notif;
            playerPlayed(getPlayer(cpn.id), getCard(cpn.rank));

        } else if (notif instanceof GetCardNotification) {
            CardType newCard = getCard(((GetCardNotification) notif).rank);
            chooseCard(newCard);

        } else if (notif instanceof SetupRoundNotification) {
            CardType initCard = getCard(((SetupRoundNotification) notif).rank);
            setup(initCard);

        } else if (notif instanceof TurnStartedNotification) {
            Player turnStarted = getPlayer(((TurnStartedNotification) notif).player);
            playerStart(turnStarted);

        } else if (notif instanceof ChoosePlayerNotification) {
            choosePlayer(getCard(((ChoosePlayerNotification) notif).rank));

        } else if (notif instanceof GameStartNotification) {
            GameStartNotification gsn = (GameStartNotification) notif;
            startGame(gsn.numPlayers, gsn.currId);

        } else if (notif instanceof GuessCardNotification) {
            guessCard();

        } else if (notif instanceof PlayerLostNotification) {
            PlayerLostNotification pln = (PlayerLostNotification) notif;
            playerLost(getPlayer(pln.id), getCard(pln.rank));

        } else if (notif instanceof PlayerWonNotification) {
            PlayerWonNotification pwn = (PlayerWonNotification) notif;
            playerWon(getPlayer(pwn.id), getCard(pwn.rank));

        } else if (notif instanceof ReplaceNotification) {
            CardType card = getCard(((ReplaceNotification) notif).rank);
            self.replace(card);
            self.discard(card);

        } else if (notif instanceof PriestNotification) {
            PriestNotification pn = (PriestNotification) notif;
            priest(getPlayer(pn.source), getPlayer(pn.target));

        } else if (notif instanceof BaronNotification) {
            BaronNotification bn = (BaronNotification) notif;
            baron(getPlayer(bn.source), getPlayer(bn.target), bn.sourceWon, getCard(bn.lostCard));

        } else if (notif instanceof KingNotification) {
            KingNotification kn = (KingNotification) notif;
            king(getPlayer(kn.id1), getPlayer(kn.id2));

        } else if (notif instanceof GuardNotification) {
            GuardNotification gn = (GuardNotification) notif;
            guard(getPlayer(gn.source), getPlayer(gn.target), getCard(gn.guess), gn.correct);

        } else if (notif instanceof PrinceNotification) {
            PrinceNotification pn = (PrinceNotification) notif;
            prince(getPlayer(pn.player), getPlayer(pn.target), getCard(pn.discard));

        } else if (notif instanceof SwapNotification) {
            SwapNotification sn = (SwapNotification) notif;
            swapCard(getPlayer(sn.source), getCard(sn.rank));

        } else {
            System.err.println("Unimplemented notification found: " + notif);
        }
    }


    public void discard(int id) {
        server.chooseCard(id);
    }


    public void startGame(int numPlayers, int myId) {
        gameStarted = true;
        players = new ArrayList<>();
        playerViews = new ArrayList<>();
        for (int c = 0; c < numPlayers; c++) {
            if (c != myId) {
                Player p = new Player(c);
                players.add(p);
                playerViews.add(new OtherPlayerView(this, playerViews.size(), p));
            } else {
                self = new SelfPlayer(this, c);
                players.add(self);
            }
        }
        playerViews.add(new SelfPlayerView(this, self));
    }

    public void setup(CardType initial) {
        self.setup(initial);
        for (Player p : players) {
            p.setup();
        }
    }

    public void chooseCard(CardType card) {
        self.addChoice(card);
    }

    public void choosePlayer(CardType card) {
        //choosePlayerDialog.show(card == CardType.Prince);
        System.out.println("Choose a player");
        for (PlayerView p : playerViews) {
            p.activate(card);
        }
        cursor.show(card);
    }
    public void playerChosen(Player p) {
        //choosePlayerDialog.hide();
        server.choosePlayer(p.id);
        for (PlayerView v : playerViews) {
            v.deactivate();
        }
        cursor.hide();
    }

    public void swapCard(Player source, CardType card) {
        self.replace(card);
    }

    public void guessCard() {
        typeSelect.show();
    }

    public void cardGuessed(CardType card) {
        server.guessCard(card);
        typeSelect.hide();
    }

    public void playerStart(Player p) {
        p.pickup();
    }

    public void playerPlayed(Player p, CardType card) {
        p.play(card);
    }

    public void playerLost(Player p, CardType card) {
        p.lose(card);
    }

    public void playerWon(Player p, CardType card) {
        p.win(card);
        if (p == self)
            show.reset("YOU WON");
        else
            show.reset("Player "+p.id+" won.");

    }

    public void baron(Player p1, Player p2, boolean p1Won, CardType loserCard) {
        if (p1Won) {
            p2.lose(loserCard);
        } else {
            p1.lose(loserCard);
        }
    }

    public void king(Player p1, Player p2) {
        System.out.println("P1 and P2 swap cards");
    }

    public void guard(Player p1, Player p2, CardType guess, boolean success) {
        if (success) {
            p2.lose(guess);
        }
    }

    public void prince(Player p1, Player p2, CardType card) {
        p2.discard(card);
    }

    public void priest(Player p1, Player p2) {
        System.out.println("P1 looks at P2's hand");
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
            if (e.getKeyChar() >= '2' && e.getKeyChar() <= '8') {
                int idx = e.getKeyChar() - '1';
                cardGuessed(CardType.values()[idx]);
            } else if (e.getExtendedKeyCode() == KeyEvent.VK_A) {
                playerChosen(players.get(0));
            } else if (e.getExtendedKeyCode() == KeyEvent.VK_B) {
                playerChosen(players.get(1));
            } else if (e.getExtendedKeyCode() == KeyEvent.VK_C) {
                playerChosen(players.get(2));
            } else if (e.getExtendedKeyCode() == KeyEvent.VK_D) {
                playerChosen(players.get(3));
            }
        }
    }

}

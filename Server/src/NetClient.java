import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by martin on 4/17/16.
 */
public class NetClient implements Client {
    final Socket socket;
    final ObjectOutputStream oos;
    final ObjectInputStream ois;

    public NetClient(Socket socket) throws IOException {
        this.socket = socket;
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    private void writeObject(Object o) {
        try {
            oos.writeObject(o);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setupRound(Card startCard) {
        writeObject(new SetupRoundNotification(startCard.getRank()));
    }

    @Override
    public int getPlayedCard(Card newCard) {
        try {
            oos.writeObject(new GetCardNotification(newCard.getRank()));
            Object chosen = ois.readObject();
            CardChosenNotification ccn = (CardChosenNotification) chosen;
            return ccn.getCard();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void notifyTurnStarted(Player p) {
        writeObject(new TurnStartedNotification(p.getId()));
    }

    @Override
    public void notifyCardPlayed(Player p, Card card) {
        writeObject(new CardPlayedNotification(p.getId(), card.getRank()));
    }

    @Override
    public int choosePlayer(Card card) {
        writeObject(new ChoosePlayerNotification(card.getRank()));
        try {
            PlayerChosenNotification pcn = (PlayerChosenNotification) ois.readObject();
            return pcn.id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int chooseCard() {
        writeObject(new GuessCardNotification());
        try {
            CardChosenNotification ccn = (CardChosenNotification) ois.readObject();
            return ccn.card;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void notifyStartGame(int numPlayers, int currId) {
        writeObject(new GameStartNotification(numPlayers, currId));
    }

    @Override
    public void notifyPlayerWon(Player p, Card c) {
        writeObject(new PlayerWonNotification(p.getId(), c.getRank()));
    }

    @Override
    public void notifyPlayerLost(Player p, Card c) {
        writeObject(new PlayerLostNotification(p.getId(), c.getRank()));
    }

    @Override
    public void notifyKing(Player p1, Player p2) {
        writeObject(new KingNotification(p1.getId(), p2.getId()));
    }

    @Override
    public void notifyGuard(Player p1, Player p2, int guess, boolean correct) {
        writeObject(new GuardNotification(p1.getId(), p2.getId(), guess, correct));
    }

    @Override
    public void notifyPrince(Player p1, Player p2, Card card) {
        writeObject(new PrinceNotification(p1.getId(), p2.getId(), card.getRank()));
    }

    @Override
    public void notifyPriest(Player p1, Player p2) {
        writeObject(new PriestNotification(p1.getId(), p2.getId()));
    }

    @Override
    public void notifyBaron(Player p1, Player p2, Player winner, Card loserCard) {
        writeObject(new BaronNotification(p1.getId(), p2.getId(), winner == p1, loserCard.getRank()));
    }

    @Override
    public void notifyReplaceCard(Card newCard) {
        writeObject(new ReplaceNotification(newCard.getRank()));
    }

    @Override
    public void notifySwap(Player opponent, Card newCard) {
        writeObject(new SwapNotification(opponent.getId(), newCard.getRank()));
    }
}

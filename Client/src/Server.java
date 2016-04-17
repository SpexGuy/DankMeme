import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Queue;

/**
 * Created by martin on 4/17/16.
 */
public class Server {
    Socket s;
    ObjectInputStream ois;
    ObjectOutputStream oos;

    void connect() throws IOException {
        s = new Socket("127.0.0.1", 0x1337);
        oos = new ObjectOutputStream(s.getOutputStream());
        ois = new ObjectInputStream(s.getInputStream());
        System.out.println("Connected to server!");
    }

    void start(final Queue<Notification> consumer) {
        Thread listener = new Thread() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Notification notif = (Notification) ois.readObject();
                        if (notif == null) break;
                        consumer.offer(notif);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.exit(-1);
                    }
                }
                System.out.println("Got null notification?");
            }
        };
        listener.setDaemon(true);
        listener.start();
    }

    private void writeObject(Object o) {
        try {
            oos.writeObject(o);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void chooseCard(int id) {
        writeObject(new CardChosenNotification(id));
    }

    public void choosePlayer(int id) {
        writeObject(new PlayerChosenNotification(id));
    }

    public void guessCard(CardType card) {
        writeObject(new CardChosenNotification(card.ordinal()+1));
    }
}

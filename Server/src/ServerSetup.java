import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by martin on 4/17/16.
 */
public class ServerSetup {

    public List<Player> getConnections(final Game game) throws IOException {
        final List<Player> players = new ArrayList<Player>();
        final ServerSocket socket = new ServerSocket(0x1337);
        final AtomicBoolean running = new AtomicBoolean(false);
        running.set(true);

        Thread connector = new Thread() {
            @Override
            public void run() {
                try {
                    while(running.get()) {
                        Socket connection = socket.accept();
                        synchronized (running) {
                            if (running.get()) {
                                players.add(new Player(new NetClient(connection), players.size(), game));
                            }
                        }
                        System.out.println("Player joined!");
                    }
                } catch(SocketException e) {
                    // Socket was probably closed to shut down the thread. Ignore.
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        connector.setDaemon(true);
        connector.start();

        System.out.println("Waiting for connections... Press enter to continue.");
        Scanner stdin = new Scanner(System.in);
        stdin.nextLine();

        System.out.println("Shutting down the ServerSocket...");
        socket.close();

        running.set(false);

        return players;
    }
}

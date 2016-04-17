import java.io.IOException;

/**
 * Created by martin on 4/16/16.
 */
public class ClientRunner {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Hello, Client!");

        Server server = new Server();
        server.connect();

        Game game = new Game(server);
        server.start(game.notifications);

        game.mainLoop();
    }
}

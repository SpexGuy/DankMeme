import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by martin on 4/16/16.
 */
public class ServerRunner {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello, Server!");

        Game game = new Game();

        ServerSetup setup = new ServerSetup();
        List<Player> players = setup.getConnections(game);

        Scanner stdin = new Scanner(System.in);
        while(players.size() < 2)
            players.add(new Player(new LocalClient(stdin), players.size(), game));

        game.runGame(players);
    }
}

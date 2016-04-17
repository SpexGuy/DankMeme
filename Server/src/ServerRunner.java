import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by martin on 4/16/16.
 */
public class ServerRunner {
    public static void main(String[] args) {
        System.out.println("Hello, Server!");

        int numPlayers = 4;
        List<Player> players = new ArrayList<>(numPlayers);
        Game game = new Game();

        Scanner stdin = new Scanner(System.in);
        for (int c = 0; c < numPlayers; c++) {
            players.add(new Player(new LocalClient(stdin), c, game));
        }


        game.runGame(players);
    }
}

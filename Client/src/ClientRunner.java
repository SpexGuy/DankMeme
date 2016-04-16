/**
 * Created by martin on 4/16/16.
 */
public class ClientRunner {
    public static void main(String[] args) {
        System.out.println("Hello, Client!");

        Game game = new Game();
        game.mainLoop();
    }
}

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martin on 4/16/16.
 */
public class Game {
    List<Card> cards = new ArrayList<>();

    void createDeck() {
        for (int c = 0; c < 12; c++) {
            cards.add(new DumbCard(this));
        }
    }

    void runGame(List<Player> players) {
        createDeck();
        for (Player p : players) {
            p.setupRound(cards.remove(cards.size()-1));
        }

        for (Player p : players) {
            p.doTurn(cards.remove(cards.size()-1));
        }
    }
}

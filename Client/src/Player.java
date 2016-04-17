import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by martin on 4/16/16.
 */
public class Player {
    final int id;
    boolean active;
    boolean protect;
    boolean isChoosing = false;
    int points = 0;

    private final List<CardType> _history = new ArrayList<>();
    public final List<CardType> history = Collections.unmodifiableList(_history);

    public Player(int id) {
        this.id = id;
    }

    public void pickup() {
        isChoosing = true;
        protect = false;
    }

    public void play(CardType card) {
        isChoosing = false;
        if (card == CardType.Handmaid)
            protect = true;
        discard(card);
    }

    public void discard(CardType card) {
        if (card == CardType.Princess)
            lose(card);
        else
            _history.add(card);
    }

    public void lose(CardType card) {
        _history.add(card);
        active = false;
    }

    public void win(CardType card) {
        _history.add(card);
        active = false;
        points++;
    }

    public void setup() {
        active = true;
        protect = false;
        isChoosing = false;
        _history.clear();
    }
}

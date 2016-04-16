import java.awt.*;

/**
 * Created by martin on 4/16/16.
 */
public enum CardType {
    Guard("guard.png", 1),
    Priest("priest.png", 2),
    Baron("baron.png", 3),
    Handmaid("handmaid.png", 4),
    Prince("prince.png", 5),
    King("king.png", 6),
    Countess("countess.png", 7),
    Princess("princess.png", 8);

    public static final int width = 235;
    public static final int height = 328;

    final Image image;
    final int rank;
    CardType(String filename, int rank) {
        this.image = ImageStore.get().getImage(filename);
        this.rank = rank;
    }
}

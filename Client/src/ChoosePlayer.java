import java.awt.*;

/**
 * Created by martin on 4/16/16.
 */
public class ChoosePlayer {
    private class PlayerButton extends Button {
        final Player player;

        protected PlayerButton(int x, int y, int width, int height, Player player) {
            super(x, y, width, height);
            this.player = player;
        }

        @Override
        void draw(Graphics2D g) {
            if (player.active) {
                if (player.protect) {
                    g.setColor(new Color(0f, 0f, 0f, 0.3f));
                    g.fillRect(x, y, width, height);
                } else if (hovered) {
                    g.setColor(new Color(1f, 1f, 1f, 0.3f));
                    g.fillRect(x, y, width, height);
                }
            }
        }

        @Override
        void activate() {
            if (player.active && !player.protect) {
                game.playerChosen(player);
            }
        }
    }


    final Game game;
    private boolean visible = false;
    private final PlayerButton p0, p1, p2, pSelf;

    public ChoosePlayer(Game game) {
        this.game = game;
        p0 = null;
        p1 = null;
        p2 = null;
        pSelf = null;
    }

    void show(boolean canChooseSelf) {
        visible = true;
    }

    void hide() {
        visible = false;
    }

    void draw(Graphics2D g) {
        if (!visible) return;


    }

    boolean hover(int x, int y) {
        if (!visible) return false;

//        if (game.getLeftPlayer() != null) {
//            leftButton.hover(x, y);
//        }
        return false;
    }
}

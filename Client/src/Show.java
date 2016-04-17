import java.awt.*;

/**
 * Created by Jordan on 4/17/2016.
 */
public class Show {
    int timer = 0;
    String text = null;

    public Show() {
    }

    public void setTimer(int time){
        this.timer = time;
    }

    public void reset(String text){
        timer = 1000;
        this.text = text;
    }

    public void update(int delta){
        //delta is the amount of time that has passed
        timer -= delta;
    }
    public void draw(Graphics2D g){
        //TODO implement draw
        double screenProportion = 0.5;
        int height = (int) (Game.HEIGHT*screenProportion);
        int width = (int) (Game.WIDTH*screenProportion);
        int x = ((Game.WIDTH - width)/2);
        int y = ((Game.HEIGHT - height)/2);

        g.setColor(Color.BLACK);
        g.fillRoundRect(x, y, width, height, 10, 10);
        Font old = g.getFont();
        Font newFont = new Font(old.getName(), old.getStyle(), 76);
        g.setFont(newFont);
        FontMetrics bigFM =  g.getFontMetrics();

        x = Game.WIDTH/2 - bigFM.stringWidth(text)/2;
        y = Game.HEIGHT/2 + bigFM.getHeight()/2 - bigFM.getDescent();
        g.setColor(Color.CYAN);
        g.drawString(text, x, y);
        g.setFont(old);
    }

}

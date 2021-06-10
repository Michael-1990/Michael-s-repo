package tankrotationexample;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpeedUp extends PowerUp {
    private int x;
    private int y;
    private boolean alive;

    private BufferedImage img;
    private Rectangle rect;

    SpeedUp(int x, int y, BufferedImage img){
        this.x = x;
        this.y = y;
        this.img = img;
        this.rect = new Rectangle(x,y,img.getWidth(),img.getHeight());
        this.alive = true;
    }

    @Override
    void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, x, y, null);
    }
    @Override
    void interact(GameObject o){
        if (o instanceof Tank){
            alive = false;
        }
    }
    @Override
    Rectangle getRectangle(){return rect;}

    @Override
    boolean isAlive(){return alive;}
}

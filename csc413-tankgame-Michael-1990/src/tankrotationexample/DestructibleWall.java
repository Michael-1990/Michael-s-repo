package tankrotationexample;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DestructibleWall extends Wall {

   private int x;
    private int y;
    private BufferedImage img;
    private Rectangle rect;
    private boolean alive;

    DestructibleWall(int x, int y, BufferedImage img){
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
    int getX(){return x;}
    @Override
    int getY(){return y;}
    @Override
    Rectangle getRectangle(){return rect;}
    @Override
    void interact(GameObject o){
        if(o instanceof Bullet){
            alive = false;
        }
    }
    @Override
    boolean isAlive(){return alive;}

}

package src;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall extends GameObject {
    private int x;
    private int y;
    private BufferedImage img;
    private Rectangle rect;

    private boolean jumpable;
    private boolean alive;

    Wall(int x, int y, BufferedImage img){
        this.x = x;
        this.y = y;
        this.img = img;
        this.rect = new Rectangle(x,y,img.getWidth(),img.getHeight());
        this.jumpable = true;
        this.alive = true;
    }

    @Override
    void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, x, y, null);
    }
    @Override
    void interact(GameObject o){
        if(o instanceof Wall){
            if(o.getX() == this.x && o.getY() < this.y){
                jumpable = false;
            }
        }
    }
    @Override
    int getX(){return x;}
    @Override
    int getY(){return y;}
    @Override
    void setAlive(boolean alive){this.alive = alive;}
    @Override
    Rectangle getRectangle(){return rect;}
    @Override
    boolean isJumpable(){return jumpable;}
    @Override
    boolean isAlive(){return alive;}


}

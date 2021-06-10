package tankrotationexample;

import java.awt.*;
import java.awt.image.BufferedImage;

public class IndestructibleWall extends Wall {
    private int x;
    private int y;
    private BufferedImage img;
    private Rectangle rect;

    IndestructibleWall(int x, int y, BufferedImage img){
        this.x = x;
        this.y = y;
        this.img = img;
        this.rect = new Rectangle(x,y,img.getWidth(),img.getHeight());
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

}

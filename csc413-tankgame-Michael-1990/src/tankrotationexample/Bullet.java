package tankrotationexample;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject {
    private int x;
    private int y;
    private int vx;
    private int vy;
    private int angle;
    private boolean alive;

    private Rectangle rect;
    private Tank tank;

    private final int R = 2;
    private final int ROTATIONSPEED = 4;


    private BufferedImage img;

    Bullet(int x, int y, int vx, int vy, int angle, BufferedImage img, Tank tank) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        this.rect = new Rectangle(x,y,img.getWidth(),img.getHeight());
        this.alive = true;
        this.tank = tank;
    }

    public void update(){
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx*2;
        y += vy*2;
        rect.setLocation(x,y);
    }

    @Override
    void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        this.update();
    }
    @Override
    Rectangle getRectangle(){
        return this.rect;
    }
    @Override
    boolean isAlive(){return alive;}
    @Override
    void interact(GameObject o){
            if(o instanceof Wall || o instanceof Tank) {
                if(o != this.tank){
                    alive = false;
                }


        }
    }
}

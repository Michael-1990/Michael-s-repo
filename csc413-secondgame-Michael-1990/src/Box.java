package src;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Box extends GameObject {
    private int x;
    private int y;
    private int weight;
    private int speed;
    private BufferedImage img;
    private Rectangle rect;

    private boolean stacked;
    private boolean jumpable;
    private boolean falling;
    private boolean alive;

    Box (int x, int y, int weight, int speed, BufferedImage img){
        this.x = x;
        this.y = y;
        this.weight = weight;
        this.speed = speed;
        this.img = img;
        this.rect = new Rectangle(x, y, img.getWidth(), img.getHeight());

        this.stacked = false;
        this.jumpable = false;
        this.falling = true;
        this.alive = true;
    }

    public void update(){
        if (falling == true){
            y += speed;
        }
    }

    @Override
    void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rect.setLocation(x, y);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        update();
    }

    @Override
    void interact(GameObject o) {
        if(o instanceof  Wall) {
            falling = false;

            if(stacked){
                jumpable = false;
            }else{
                jumpable = true;
            }
        }
        if(o instanceof Box){
            if(o.getX() == this.x){
                if(o.getY() > this.y){
                    if(this.weight <= o.getWeight()){
                        falling = false;
                        jumpable = true;
                    }
                    if(stacked){
                        jumpable = false;
                    }
                }else if(o.getY() < this.y){
                    if(o.getWeight() > this.weight){
                        alive = false;
                    }else{
                        stacked = true;
                    }
                }
            }
        }
    }

    @Override
    Rectangle getRectangle(){
        return rect;
    }
    @Override
    int getX(){return x;}
    @Override
    int getY(){return y;}
    @Override
    int getWeight(){return weight;}
    @Override
    void setAlive(boolean alive){this.alive = alive;}
    @Override
    boolean isAlive(){return alive;}
    @Override
    boolean isFalling() {return falling;}
    @Override
    boolean isJumpable() {return jumpable;}
}

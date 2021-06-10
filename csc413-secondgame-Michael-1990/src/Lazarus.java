package src;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Lazarus extends GameObject {

    private int currX;
    private int currY;

    private int spawnX;
    private int spawnY;

    private int lives;
    private int health;
    private int speed;
    private int direction;

    private int x;
    private int y;
    private int vx;
    private int vy;
    private int accel;

    private boolean alive;
    private boolean falling;
    private boolean canJump;
    private Rectangle rect, feet, head, l_hand, r_hand;
    private GameWorld world;

    private BufferedImage img;
    private boolean RightPressed;
    private boolean LeftPressed;


    Lazarus(int x, int y, int vx, int vy, BufferedImage img, GameWorld world) {

        this.spawnX = x;
        this.spawnY = y;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.accel = 0;
        this.direction = 1;

        this.img = img;
        this.world = world;

        this.rect = new Rectangle(x+4, y+8, img.getWidth()-8, img.getHeight()-8);
        this.head = new Rectangle(rect.x+8, rect.y,rect.width/2,5);
        this.feet = new Rectangle(head.x,rect.y+rect.height-5,head.width,5);
        this.l_hand = new Rectangle(rect.x,rect.y+7,5,rect.height/2);
        this.r_hand = new Rectangle(rect.x+rect.width-5,l_hand.y,5,rect.height/2);

        this.alive = true;
        this.falling = false;
        this.canJump = true;

        this.lives = 3;
        this.health = 100;
        this.speed = 1;

    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    public void update() {
        if (this.LeftPressed) {
            this.moveLeft();
        }
        if (this.RightPressed) {
            this.moveRight();
        }
        if(falling){
            y += speed * accel;
            currY = y;
            accel++;
            canJump = false;
        }
        if(this.y < 480){
            falling = true;
        }
        setRect();
    }

    private void moveLeft() {
        currX = x;
        currY = y;
        x -= speed * vx;
        direction = -1;
        setRect();
    }

    private void moveRight() {
        currX = x;
        currY = y;
        x += speed * vx;
        direction = 1;
        setRect();
    }

    @Override
    public String toString() {
        return "x=" + rect.x + ", y=" + rect.y;
        /*return null;*/
    }


    @Override
    void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rect.setLocation(x+4, y+8);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        /*g2d.draw(rect);
        g2d.draw(head);
        g2d.draw(feet);
        g2d.draw(l_hand);
        g2d.draw(r_hand);*/
    }

    @Override
    void interact(GameObject o) {
        if(o instanceof  Wall) {
            if(this.r_hand.intersects(o.getRectangle())||this.l_hand.intersects(o.getRectangle())) {
            x = currX;
            }
            if(this.feet.intersects(o.getRectangle())) {
                y -= 1;
                falling = false;
                canJump = true;
                accel = 0;
            }
        }
        if(o instanceof Box){
            if(!o.isJumpable()){
                x = currX - direction;
            }else if(o.isJumpable()){
                if(this.r_hand.intersects(o.getRectangle())||this.l_hand.intersects(o.getRectangle())) {
                    this.jump();
                }
            }

            if(this.feet.intersects(o.getRectangle())){
                y -= 1;
                falling = false;
                canJump = true;
                accel = 0;
            }else if(!this.feet.intersects(o.getRectangle())){
                x += direction;
            }

            if(o.isFalling() && this.head.intersects(o.getRectangle())){
                this.takeDamage();
            }
        }
        if(o instanceof Button){
            this.x = spawnX;
            this.y = spawnY;
            this.world.LazarusWinEvent();
        }
    }

    @Override
    Rectangle getRectangle(){
        return rect;
    }
    void setRect(){
        rect.setLocation(x+4, y+8);
        head.setLocation(rect.x +8, rect.y);
        feet.setLocation(head.x,rect.y+rect.height-5);
        l_hand.setLocation(rect.x,rect.y+7);
        r_hand.setLocation(rect.x+rect.width-5,l_hand.y);
    }
    @Override
    int getX(){return x;}
    @Override
    int getY(){return y;}

    void jump(){
        if(canJump){
            this.y -= 60;
            this.x += 15 * direction;
        }
        falling = true;
    }
    
    void takeDamage(){
        this.x = spawnX;
        this.y = spawnY;

        this.world.LazarusDeathEvent();
    }
    @Override
    boolean isAlive(){return alive;}
}

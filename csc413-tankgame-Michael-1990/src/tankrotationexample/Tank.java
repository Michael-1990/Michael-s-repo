package tankrotationexample;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Field;

import static javax.imageio.ImageIO.read;

/**
 *
 * @author anthony-pc
 */
public class Tank extends GameObject {

    private int currX;
    private int currY;

    private int spawnX;
    private int spawnY;

    private String livecolor;
    private int lives;
    private int health;
    private int speed;

    private int x;
    private int y;
    private int vx;
    private int vy;
    private int angle;
    private int fireRate = 0;

    private boolean alive;
    private BufferedImage bimg;
    private Rectangle rect;
    private TRE world;
    private Bullet bullet;

    private final int R = 2;
    private final int ROTATIONSPEED = 4;

    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean FirePressed;


    Tank(int x, int y, int vx, int vy, int angle, BufferedImage img, TRE world, String color) {

        this.spawnX = x;
        this.spawnY = y;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        this.world = world;
        this.rect = new Rectangle(x, y, img.getWidth(), img.getHeight());

        this.alive = true;

        this.livecolor = color;
        this.lives = 3;
        this.health = 100;
        this.speed = 1;

        try {
            bimg = read(new File("resources/Shell.gif"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleFirePressed() {
        this.FirePressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void unToggleFirePressed() {
        this.FirePressed = false;
    }


    public void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.FirePressed) {
            if(fireRate == 0) {
                this.FireAction();
            }
            fireRate = 50;
        }
        rect.setLocation(x, y);
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        currX = x;
        currY = y;
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= speed * vx;
        y -= speed * vy;
        rect.setLocation(x, y);
    }

    private void moveForwards() {
        currX = x;
        currY = y;
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += speed * vx;
        y += speed * vy;
        rect.setLocation(x, y);
    }

    private void FireAction() {
        int xoffset = x + (img.getWidth() / 2) - (bimg.getWidth() / 2);
        int yoffset = y + (img.getWidth() / 2) - (bimg.getWidth() / 2);
        bullet = new Bullet(xoffset, yoffset, speed * vx,  speed * vy, this.angle, bimg, this);
        world.addBullet(bullet);
    }

    @Override
    public String toString() {
        /*return "x=" + rect.x + ", y=" + rect.y + ", angle=" + angle;*/
        return this.livecolor + " Tank";
    }


    @Override
    void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rect.setLocation(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);

        Color color;
        for (int i = 0; i < lives; i++){

            try {
                Field field = Color.class.getField(livecolor);
                color = (Color)field.get(null);
            } catch (Exception e) {
                color = null; // Not defined
            }

            g2d.setColor(color);
            g2d.fillOval(x + i * 10, y - 10, 10, 10);
        }

        String healthcolor = "";
        if(75 < health && health <= 100){
            healthcolor = "green";
        }else if(25< health && health <= 75){
            healthcolor = "yellow";
        }else if(0< health && health <=25){
            healthcolor = "red";
        }

        try {
            Field field = Color.class.getField(healthcolor);
            color = (Color)field.get(null);
        } catch (Exception e) {
            color = null; // Not defined
        }

        g2d.setColor(color);
        g2d.fillRect(x,y + img.getHeight(),(health/2),10);

        if(fireRate > 0)
            fireRate--;
    }

    @Override
    void interact(GameObject o) {
            if(o instanceof  Wall || o instanceof Tank && o != this) {
                x = currX;
                y = currY;
            }
            if(o instanceof Bullet && o != this.bullet){this.takeDamage();}
            if(o instanceof HealthUp){health = 100;}
            if(o instanceof SpeedUp){speed ++;}
    }

    @Override
    Rectangle getRectangle(){
        return rect;
    }
    @Override
    int getX(){return x;}
    int getY(){return y;}

    int getCameraBound(int dimension,int left, int right){
        int camera;

        if(dimension > left && dimension < right){
            camera = dimension - left;
        }else if (dimension > right){
            camera = right-left;
        }else
            camera = 0;

        return camera;
    }
    void takeDamage(){
        health = health - 5;

        if(health == 0 && lives > 0){
            lives--;
            //on loss of life, reset speed
            speed = 1;

            //respawn tank
            x = spawnX;
            y = spawnY;

            health = 100;
        }

        if(lives == 0){
            this.alive = false;
        }

    }
    @Override
    boolean isAlive(){return alive;}
}
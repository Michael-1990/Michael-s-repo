package src;

import java.awt.*;
//abstract gameObject class
public abstract class GameObject {
    //gameObject function prototypes
    void drawImage(Graphics g){}
    void interact(GameObject o){}
    void setAlive(boolean alive){}
    int getWeight(){return 0;}
    int getX(){return 0;}
    int getY(){return 0;}

    Rectangle getRectangle(){
        Rectangle rect = new Rectangle();
        return rect;
    }

    boolean isAlive(){return true;}
    boolean isFalling(){return true;}
    boolean isJumpable(){return true;}
}

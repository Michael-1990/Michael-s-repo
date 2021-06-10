package tankrotationexample;

import java.awt.*;
//abstract gameObject class
public abstract class GameObject {
    //gameObject function prototypes
    void drawImage(Graphics g){}
    void interact(GameObject o){}
    int getX(){return 0;}
    int getY(){return 0;}

    Rectangle getRectangle(){
        Rectangle rect = new Rectangle();
        return rect;
    }

    boolean isAlive(){return true;}
}

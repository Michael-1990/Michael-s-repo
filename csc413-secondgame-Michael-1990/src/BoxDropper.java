package src;

import java.awt.*;

public class BoxDropper extends GameObject {
    private int x;
    private int y;
    private Lazarus lazarus;
    private Rectangle rect;

    BoxDropper(int x, int y, Lazarus lazarus){
        this.x = x;
        this.y = y;
        this.lazarus = lazarus;
        this.rect = new Rectangle(x,y,40,10);
    }

    public void update() {
        if (lazarus.getX() + 40 <= this.x) {
            this.moveLeft();
        }
        if (lazarus.getX() >= this.x + 40) {
            this.moveRight();
        }
        rect.setLocation(x, y);
    }

    private void moveLeft() {
        x -= 40;
        rect.setLocation(x, y);
    }

    private void moveRight() {
        x += 40;
        rect.setLocation(x, y);
    }

    @Override
    void drawImage (Graphics g){
        g.drawRect(this.x,this.y,40,10);
        update();
    }
    @Override
    int getX(){return this.x;}
    int getY(){return this.y;}

}

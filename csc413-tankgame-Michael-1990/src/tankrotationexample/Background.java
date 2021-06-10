package tankrotationexample;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Background extends GameObject {
    private int x;
    private int y;
    private BufferedImage img;

    Background(int x, int y, BufferedImage img){
        this.x = x;
        this.y = y;
        this.img = img;
    }

    @Override
    void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, x, y, null);
    }
}

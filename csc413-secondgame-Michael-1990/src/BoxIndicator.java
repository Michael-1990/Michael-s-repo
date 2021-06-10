package src;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class BoxIndicator extends GameObject {
    private int x;
    private int y;
    private int type_1;
    private int type_2;
    private BufferedImage img1;
    private BufferedImage img2;

    BoxIndicator(int x, int y, BufferedImage img, int type){
        this.x = x;
        this.y = y;
        type_1 = type;
        type_2 = 0;
        this.img1 = img;
        this.img2 = null;
    }

    @Override
    void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img1, x, y, null);
    }

    BufferedImage newImage(BufferedImage img1){
        this.img2 = this.img1;
        this.img1 = img1;

        return img2;
    }

    int newType(int type){
        type_2 = type_1;
        type_1 = type;

        return type_2;
    }
}

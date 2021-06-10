/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankrotationexample;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Scanner;

import static javax.imageio.ImageIO.read;

/**
 *
 * @author anthony-pc
 */
public class TRE extends JPanel  {

    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 960;

    public int world_w = 2*SCREEN_WIDTH;
    public int world_h = 2*SCREEN_HEIGHT;

    public int camera1_x;
    public int camera1_y;
    public int camera2_x;
    public int camera2_y;

    private BufferedImage world;
    private Graphics2D buffer;
    private JFrame jf;

    private ArrayList<GameObject> gameobjects = new ArrayList<>();
    private Tank t1, t2;
    private Background bg;
    private Wall wl;
    private PowerUp pw;

    private boolean isRunning;

    public static void main(String[] args) {
        Thread x;
        TRE trex = new TRE();
        trex.init();
        try {

            while (trex.isRunning) {
                trex.t1.update();
                trex.t2.update();

                trex.checkCollisions();

                trex.repaint();
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {

        }

    }


    private void init() {
        this.isRunning = true;
        this.jf = new JFrame("Tank Rotation");
        this.world = new BufferedImage(2*TRE.SCREEN_WIDTH, 2*TRE.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
        BufferedImage t1img = null, t2img = null, bimg = null;
        try {
            BufferedImage tmp;
            System.out.println(System.getProperty("user.dir"));
            /*
             * note class loaders read files from the out folder (build folder in netbeans) and not the
             * current working directory.
             */
            t1img = read(new File("resources/Tank1.gif"));
            t2img = read(new File("resources/Tank2.gif"));
            bimg = read(new File("resources/Background.bmp"));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        //populate background
        for (int i = 0; i < world_w; i += bimg.getWidth()){
            for(int j = 0; j < world_h; j += bimg.getHeight()) {
                bg = new Background(i, j, bimg);
                gameobjects.add(bg);
            }
        }

        this.getMap();

        t1 = new Tank(100, 200, 0, 0, 0, t1img,this, "red");
        gameobjects.add(t1);
        t2 = new Tank(world_w-100, world_h-200, 0, 0, 0, t2img,this, "blue");
        gameobjects.add(t2);


        TankControl tc1 = new TankControl(t1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);

        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);


        this.jf.addKeyListener(tc1);
        this.jf.addKeyListener(tc2);


        this.jf.setSize(TRE.SCREEN_WIDTH, TRE.SCREEN_HEIGHT + 30);
        this.jf.setResizable(false);
        jf.setLocationRelativeTo(null);

        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {

        AffineTransform scale = AffineTransform.getScaleInstance(0.10,0.10);
        scale.translate(4*SCREEN_WIDTH,7.5*SCREEN_HEIGHT);
        Graphics2D g2 = (Graphics2D) g;
        buffer = world.createGraphics();
        super.paintComponent(g2);

        for(int i = 0; i < gameobjects.size(); i++){
            gameobjects.get(i).drawImage(buffer);
        }

        //set x coordinates for splitscreen cameras
        camera1_x = t1.getCameraBound(t1.getX(),SCREEN_WIDTH/4,world_w-SCREEN_WIDTH/4);
        camera1_y = t1.getCameraBound(t1.getY(),SCREEN_HEIGHT/2, world_h-SCREEN_HEIGHT/2);

        camera2_x = t2.getCameraBound(t2.getX(),SCREEN_WIDTH/4,world_w-SCREEN_WIDTH/4);
        camera2_y = t2.getCameraBound(t2.getY(),SCREEN_HEIGHT/2, world_h-SCREEN_HEIGHT/2);

        g2.drawImage(world.getSubimage(camera1_x,camera1_y,SCREEN_WIDTH/2,SCREEN_HEIGHT),0,0,null);

        g2.drawImage(world.getSubimage(camera2_x,camera2_y,SCREEN_WIDTH/2,SCREEN_HEIGHT),SCREEN_WIDTH/2,0,null);
        g2.drawImage(world,scale,null);
    }

    public void addBullet(Bullet b){
        this.gameobjects.add(b);
    }

    public void getMap(){
        int j = 0;
        File mapFile = new File("resources/map.txt");

        try {
            BufferedImage IWimg =read(new File("resources/Wall2.gif"));
            BufferedImage DWimg =read(new File("resources/Wall1.gif"));
            BufferedImage HPimg =read(new File("resources/Pickup.gif"));
            BufferedImage SPimg =read(new File("resources/Rocket.gif"));
            Scanner scan = new Scanner(mapFile);

            while(scan.hasNextLine()){
                String line;
                line = scan.nextLine();

                //read text file to populate game map with walls and powerups

                for (int i = 0; i < line.length(); i++){
                    if(line.charAt(i) == 'i'){
                        wl = new IndestructibleWall(i*IWimg.getWidth(),j*IWimg.getHeight(),IWimg);
                        gameobjects.add(wl);
                    }else if(line.charAt(i) == 'd'){
                        wl = new DestructibleWall(i*IWimg.getWidth(),j*IWimg.getHeight(),DWimg);
                        gameobjects.add(wl);
                    }else if(line.charAt(i) == 'h'){
                        pw = new HealthUp(i*IWimg.getWidth(),j*IWimg.getHeight(),HPimg);
                        gameobjects.add(pw);
                    }else if(line.charAt(i) == 's'){
                        pw = new SpeedUp(i*IWimg.getWidth(),j*IWimg.getHeight(),SPimg);
                        gameobjects.add(pw);
                    }
                }
                j++;
            }
        }catch (Exception e){e.printStackTrace();}
    }
    void checkCollisions(){
        //function to check for collisions between objects
        //compares all game objects to eachother and calls
        //object specific function in event of Rectangle intersection
        for (int i = 0; i < this.gameobjects.size(); i++){
            GameObject c = this.gameobjects.get(i);
            for (int j = 0; j < this.gameobjects.size(); j++){
                GameObject co = this.gameobjects.get(j);
                if(c == co) continue;
                if(c.getRectangle().intersects(co.getRectangle())){
                    co.interact(c);
                }

            }
            if(this.gameobjects.get(i).isAlive() == false){
                if(this.gameobjects.get(i ) == t1){
                    tankDeathEvent(t2);
                }
                else if(this.gameobjects.get(i) == t2){
                    tankDeathEvent(t1);
                }
                gameobjects.remove(i);
            }
        }
    }
    void tankDeathEvent(Tank tank){
        String message = tank.toString();
        JOptionPane.showMessageDialog(jf,message + " is the winner","Congratulations!",JOptionPane.PLAIN_MESSAGE);
        this.isRunning = false;
    }
}

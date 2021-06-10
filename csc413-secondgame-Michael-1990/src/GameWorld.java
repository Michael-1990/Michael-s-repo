/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

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
public class GameWorld extends JPanel  {

    public static final int SCREEN_WIDTH = 640;
    public static final int SCREEN_HEIGHT = 480;

    public static final int BLOCK_WIDTH = 40;
    public static final int BLOCK_HEIGHT = 40;

    public int start_x = SCREEN_WIDTH/2;
    public int start_y = SCREEN_HEIGHT - 2*BLOCK_HEIGHT;

    private BufferedImage world;
    private BufferedImage lazimg;
    private Graphics2D buffer;
    private JFrame jf;

    private ArrayList<GameObject> gameobjects = new ArrayList<>();
    private ArrayList<BufferedImage> boxlist;
    private ArrayList<File> mapList;
    private Lazarus lazarus;
    private BoxDropper boxDropper;
    private BoxIndicator boxIndicator;
    private Background bg;
    private Wall wl;
    private Button b1;
    private Box box;

    private int boxtimer_start;
    private int boxtimer;

    private int level;
    private int boxSpeed;

    private boolean isRunning;

    public static void main(String[] args) {
        Thread x;
        GameWorld gworld = new GameWorld();
        gworld.init();
        try {

            while (gworld.isRunning) {
                gworld.lazarus.update();

                gworld.checkCollisions();

                gworld.repaint();
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {

        }

    }


    private void init() {
        this.isRunning = true;
        this.jf = new JFrame("Lazarus");
        this.world = new BufferedImage(GameWorld.SCREEN_WIDTH, GameWorld.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.lazimg = null;
        BufferedImage bimg = null;

        this.mapList = new ArrayList<>();
        this.level = 0;
        this.boxSpeed = 1;

        try {
            BufferedImage tmp;
            System.out.println(System.getProperty("user.dir"));
            /*
             * note class loaders read files from the out folder (build folder in netbeans) and not the
             * current working directory.
             */
            lazimg = read(new File("resources/Lazarus_stand.gif"));
            bimg = read(new File("resources/Background.bmp"));

            mapList.add(new File("resources/map.txt"));
            mapList.add(new File("resources/map_2.txt"));
            mapList.add(new File("resources/map_3.txt"));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        //populate background
        bg = new Background(0, 0, bimg);
        gameobjects.add(bg);

        this.getMap(mapList.get(level));

        lazarus = new Lazarus(start_x, start_y, 2, 0, lazimg, this);
        gameobjects.add(lazarus);

        boxDropper = new BoxDropper(start_x, 0,lazarus);
        gameobjects.add(boxDropper);

        LazarusControl laz_c1 = new LazarusControl(lazarus, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);

        boxlist = getBoxImages();

        int type = (int)(Math.random() * ((4 - 1) + 1)) + 1;
        boxIndicator = new BoxIndicator(0,0,this.boxlist.get(type-1),type - 1);
        gameobjects.add(boxIndicator);

        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);


        this.jf.addKeyListener(laz_c1);


        this.jf.setSize(GameWorld.SCREEN_WIDTH, GameWorld.SCREEN_HEIGHT + 30);
        this.jf.setResizable(false);
        jf.setLocationRelativeTo(null);

        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {

        AffineTransform scale = AffineTransform.getScaleInstance(1,1);
        Graphics2D g2 = (Graphics2D) g;
        buffer = world.createGraphics();
        super.paintComponent(g2);

        int type = (int)(Math.random() * ((4 - 1) + 1)) + 1;

        if(boxtimer == 0) {
            box = new Box(this.boxDropper.getX(), this.boxDropper.getY(), boxIndicator.newType(type), boxSpeed, boxIndicator.newImage(this.boxlist.get(type-1)));
            gameobjects.add(box);
            boxtimer = boxtimer_start;
        }

        for(int i = 0; i < gameobjects.size(); i++){
            gameobjects.get(i).drawImage(buffer);
        }
        
        g2.drawImage(world,scale,null);

        this.boxtimer--;
    }

    public ArrayList<BufferedImage> getBoxImages(){
        ArrayList<BufferedImage> imgList = new ArrayList<>();

        try {
            BufferedImage cardImg = read(new File("resources/CardBox.gif"));
            BufferedImage woodImg = read(new File("resources/WoodBox.gif"));
            BufferedImage stoneImg = read(new File("resources/StoneBox.gif"));
            BufferedImage metalImg = read(new File("resources/MetalBox.gif"));

            imgList.add(cardImg);
            imgList.add(woodImg);
            imgList.add(stoneImg);
            imgList.add(metalImg);

        }catch (Exception e){e.printStackTrace();}
                return imgList;
    }

    public void getMap(File file){
        int j = 0;
        File mapFile = file;

        try {
            BufferedImage Wimg =read(new File("resources/Wall.gif"));
            BufferedImage Bimg =read(new File("resources/Button.gif"));
            Scanner scan = new Scanner(mapFile);

            while(scan.hasNextLine()){
                String line;
                line = scan.nextLine();

                //read text file to populate game map with walls and powerups

                for (int i = 0; i < line.length(); i++){
                    try{
                        boxtimer_start = Integer.parseInt(line);
                    }catch(NumberFormatException e){i = i;}

                    if(line.charAt(i) == 'b'){
                        b1 = new Button(i*Wimg.getWidth(),j*Wimg.getHeight(),Bimg);
                        gameobjects.add(b1);
                    }else if(line.charAt(i) == 'w'){
                        wl = new Wall(i*Wimg.getWidth(),j*Wimg.getHeight(),Wimg);
                        gameobjects.add(wl);
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
                if(this.gameobjects.get(i) == lazarus){
                    LazarusDeathEvent();
                }
                gameobjects.remove(i);
            }
        }
    }
    void LazarusDeathEvent(){
        for (int i = 0; i < gameobjects.size(); i++){
            if(this.gameobjects.get(i) instanceof  Box){
              this.gameobjects.get(i).setAlive(false);
            }
        }

    }
    void LazarusWinEvent(){
        for (int i = 0; i < gameobjects.size(); i++){
            if(this.gameobjects.get(i) instanceof  Wall || this.gameobjects.get(i) instanceof Button){
                this.gameobjects.get(i).setAlive(false);
            }
        }
        this.boxSpeed ++;
        this.level++;
        if(level == mapList.size()) {
            JOptionPane.showMessageDialog(jf, "You Win!", "Congratulations!", JOptionPane.PLAIN_MESSAGE);
            this.isRunning = false;
        }else {
            getMap(mapList.get(level));
            this.LazarusDeathEvent();
        }
    }
}

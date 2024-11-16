package main;

import tile.TileManager;
import javax.swing.JPanel;
import java.awt.*;
import entity.Player;
import object.SuperObject;

public class GamePanel extends JPanel implements Runnable{
    /* implement lets us access interface (abstract class) methods */

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile size is the standard for pixel games
    final int scale = 3; // constant variable that will scale to fit the big screen monitors

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    // screen ratio is 4 by 3 (width x height)
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels (pixel is 48x48)
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels (pixel is 48x48)

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FPS
    int FPS = 60;

    // SYSTEM
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;
    /**
     * Threads allow us to run the game in the background
     */

    // ENTITY AND OBJECT
    public Player player = new Player(this,keyH);
    public SuperObject[] obj = new SuperObject[10];

    public GamePanel () {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Creates a dimension with the specified width and height
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        /*
         * Double buffering is used to make sure the user doesn't notice the visible artifacts of how your picture is drawn.
         * Double buffering creates an off-screen image, draws to that image using the image's graphics object, then, in one step, call the image using the target window's graphics object and the off-screen image.
         * this provides better rendering performances
         */
        this.addKeyListener(keyH);
        this.setFocusable(true); // With this, main.GamePanel can be "focused" to receive key input
    }

    public void setupGame() {

        aSetter.setObject();

        playMusic(0);
    }

    public void startGameThread() {

        gameThread = new Thread(this); // "this" means we are passing the main.GamePanel class to the constructor
        gameThread.start();
    }

    @Override
    public void run() {

        /* Sleep method
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null) *//* while the gameThread exists *//* {

            // 1 UPDATE: update information such as character positions
            update();

            // 2 DRAW: draw the screen with the updated information
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }

            if (timer >= 1000000000) {
                timer = 0;
            }
        }
    }

    public void update() {

        player.update();

    }

    @Override
    public void paintComponent (Graphics g) {

        super.paintComponent(g); // Super allows referencing the parent class or superclass

        Graphics2D g2 = (Graphics2D)g; // changing graphics to graphics2D which gives more control over geometry, coordinate transformations, color management and text layout

        //  TILE
        tileM.draw(g2);

        // OBJECT
         for( int i = 0; i < obj.length; i++) {
             if (obj[i] != null) {
                 obj[i].draw(g2, this);
             }
         }

        // PLAYER
        player.draw(g2);

        // UI
        ui.draw(g2);

        g2.dispose(); // Disposes the graphics content and releases any system resources that it is using
    }

    public void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {

        music.stop();
    }

    public void playSE (int i) {

        se.setFile(i);
        se.play();
    }
}

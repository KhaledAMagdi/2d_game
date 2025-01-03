package Main;

import Event.EventHandler;
import Object.ObjectManager;
import entity.MonsterManager;
import tile.TileManager;
import entity.Player;
import entity.NPCManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    //-------screen settings-------//
    final int originalTileSize = 16; //base tile size
    final int scale = 5; //scale the tile size

    public final int tileSize = originalTileSize * scale; //final tile size
    public final int maxScreenCol = 16; //raw width screen
    public final int maxScreenRow = 12; //raw height screen
    public final int screenWidth = tileSize * maxScreenCol;  //actual screen width
    public final int screenHeight = tileSize * maxScreenRow; //actual screen height


    //-------world settings-------//
    public final int maxWorldCol = 50; //raw world width
    public final int maxWorldRow = 50; //raw world height
    public final int worldWidth = tileSize * maxWorldCol; //actual world width
    public final int worldHeight = tileSize * maxWorldRow; //actual world height

    //-------game FPS-------//
    final int FPS = 60;

    //-------classes included-------//
    //all the classes used to create this game revolve around the gamepanel class
    //we create a class that controls a specific aspect of the game then call it in the gamepanel class to use it
    Thread gameThread; //main thread
    TileManager tileM = new TileManager(this); //tile manager
    public KeyHandler keyH = new KeyHandler(this); //key handling
    public Player player = new Player(this, keyH);  //player manager
    public CollisionChecker cChecker = new CollisionChecker(this); //collision handling
    public ObjectManager objectM = new ObjectManager(this); //object manager
    public UI ui = new UI(this); //user interface manager
    public NPCManager npcM = new NPCManager(this); //NPC manager
    public EventHandler eventH = new EventHandler(this); //event handling
    public MonsterManager monsterM = new MonsterManager(this);

    //-------game states-------//
    public final int playState = 1; //normal play state
    public final int pauseState = -1; //paused game state
    public final int dialogueState = 2; //dialogue display state
    public final int titleState = 0; //title screen state
    public boolean devMode = false; //developer state
    public int gameState = titleState; //seting the default game state


    //-------constructor-------//
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));//creates window in the screen dimensions mentioned
        this.setBackground(Color.black);//background color
        this.setDoubleBuffered(true);//flickering stability
        this.addKeyListener(keyH);//key handling
        this.setFocusable(true);//focuses this game panel
    }

    //-------running main thread-------//
    public void startGameThread() {
        gameThread = new Thread(this);//adds thread
        gameThread.start();//runs thread
    }

    //-------FPS management-------//
    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; //ratio of nanosecond to frame draw
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime(); //current time of the system
            delta += (currentTime - lastTime) / drawInterval; //the time between each frame
            timer += (currentTime - lastTime); //updates timer 
            lastTime = currentTime; //updates last time

            if (delta >= 1) //when delta reaches 1 means draw new frame
            {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) //checks how many frames drawn in the last second
            {
                if (devMode)
                    System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    //-------updates game(position,dialogue,etc)-------//
    public void update() {

        switch (gameState)//checks game state and updates accordingly
        {
            case playState -> {
                player.update();
                npcM.update();
                monsterM.update();
            }
            case pauseState -> {

            }
        }
    }

    //-------main draw method-------//
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameState == titleState) //checks game state and draw accordingly
        {
            ui.draw(g2);
        }
        else
        {
            //-------title-------//
            tileM.draw(g2);

            //-------object-------//
            objectM.draw(g2);

            //-------NPC-------//
            npcM.draw(g2);

            //-------player-------//
            player.draw(g2);

            //-------UI-------//
            ui.draw(g2);

            //-------event-------//
            eventH.draw(g2);

            //-------monsters-------//
            monsterM.draw(g2);

            g2.dispose();
        }
    }
}

package Main;

import Event.EventHandler;
import Object.*;
import entity.MonsterManager;
import tile.TileManager;
import entity.Player;
import entity.NPCManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    //-------screen settings-------//
    final int originalTileSize = 16; //base tile size
    final int scale = 5; //scale the tile size

    public final int tileSize = originalTileSize * scale; //final tile size
    public final int maxScreenCol = 20; //raw width screen
    public final int maxScreenRow = 12; //raw height screen
    public final int screenWidth = tileSize * maxScreenCol;  //actual screen width 960 pixels
    public final int screenHeight = tileSize * maxScreenRow; //actual screen height
    //-------fullscreen-------//s
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;
    config config1 = new config(this);

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
    public ObjectManager objectM = new ObjectManager(this); //object manager
    public ProjectileManager projM = new ProjectileManager(this);
    public UI ui = new UI(this); //user interface manager
    public NPCManager npcM = new NPCManager(this); //NPC manager
    public EventHandler eventH = new EventHandler(this); //event handling
    public MonsterManager monsterM = new MonsterManager(this);
    public CollisionChecker cChecker = new CollisionChecker(this); //collision handling
    public Player player = new Player(this, keyH);  //player manager

    //-------game states-------//
    public final int titleState = 0; //title screen state
    public final int optionsState = -2;
    public final int pauseState = -1; //paused game state
    public final int playState = 1; //normal play state
    public final int dialogueState = 2; //dialogue display state
    public final int characterState = 3; //stats screen
    public final int gameoverState = 4;
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
    public void setupGame()
    {
        tempScreen = new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();
        if(fullScreenOn == true)
        {
            setFullScreen();
        }
    }

    public void retry(){
        player.resortHUD();
        player.setDefualtPosition();
        monsterM = new MonsterManager(this);
        npcM = new NPCManager(this);
    }

    public void restart()
    {
        player.setDefaultValues();
        player.setDefualtPosition();
        objectM = new ObjectManager(this);
        monsterM = new MonsterManager(this);
        npcM = new NPCManager(this);
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
                drawtoTempScreen();
                drawToScreen();
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
                projM.update();
            }
            case pauseState -> {

            }
        }
    }

    public void drawToScreen()
    {
        Graphics g = getGraphics();
        g.drawImage(tempScreen,0,0,screenWidth2,screenHeight2,null);
        g.dispose();
    }

    public void drawtoTempScreen()
    {
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

            //-------Projectile-------//
            projM.draw(g2);

            //-------player-------//
            player.draw(g2);

            //-------event-------//
            eventH.draw(g2);

            //-------monsters-------//
            monsterM.draw(g2);

            //-------UI-------//
            ui.draw(g2);

        }
    }

    public void setFullScreen()
    {
        //Get local screen device
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        //Get Full screen Width and height
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }
}

package Main;

import javax.swing.JFrame;

public class Main
{
    public static JFrame window;

    public static void main(String[] args)
    {
        //-------setup window-------//
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Mini Sowrds Man");

        //-------creates the main calss of the game-------//
        GamePanel gamePanel = new GamePanel();

        //-------adds it to the window-------//
        window.add(gamePanel);
        window.pack();
       // window.setUndecorated(true);

        gamePanel.config1.loadConfig();
        if(gamePanel.fullScreenOn == true)
        {
            //window.setUndecorated(true);
        }

        //-------sets location of the window and visibility-------//
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        //-------starts main thread-------//
        gamePanel.setupGame();
        gamePanel.startGameThread();

    }
}
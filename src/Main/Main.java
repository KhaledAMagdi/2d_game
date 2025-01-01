package Main;

import javax.swing.JFrame;

public class Main 
{
    public static void main(String[] args) 
    {
        //-------setup window-------//
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Mini Sowrds Man");
        
        //-------creates the main calss of the game-------//
        GamePanel gamePanel = new GamePanel();
        
        //-------adds it to the window-------//
        window.add(gamePanel);
        window.pack();
        
        //-------sets location of the window and visibility-------//
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        //-------starts main thread-------//
        gamePanel.startGameThread();
    }
}
//<3

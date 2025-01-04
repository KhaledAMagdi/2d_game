package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener 
{
    GamePanel gp; //gamepanel
    public boolean upPressed, downPressed, rightPressed, leftPressed, enterPressed, rPressed, ePressed; //used keys
    
    //-------constructor-------//
    public KeyHandler(GamePanel gp)
    {
        this.gp = gp;
    }
    
    //-------unused method-------//
    @Override
    public void keyTyped(KeyEvent e) {}

    //-------key pressed event handler-------//
    @Override
    public void keyPressed(KeyEvent e) 
    {
        int code = e.getKeyCode();//key pressed
        
        if(gp.gameState == gp.titleState)//check game state
        {
            titleState(code);
        }
        else if(gp.gameState == gp.playState)//check game state
        {
            playState(code);
        }
        else if(gp.gameState == gp.pauseState)//check game state
        {
            pauseState(code);
        }
        else if(gp.gameState == gp.dialogueState)//check game state
        {
            dialogueState(code);
        }
        else if(gp.gameState == gp.characterState)
        {
            characterState(code);
        }
        else if(gp.gameState == gp.optionsState)
        {
            optionsState(code);
        }

    }
    public void optionsState(int code)
    {
        if(code == KeyEvent.VK_L)
        {
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER)
        {
            enterPressed = true;
        }

        int maxCommandNum = 0; //max number of items in options menu so cursor doesnt go beyond border
        switch(gp.ui.subState)
        {
            case 0: maxCommandNum = 5; break;
            case 3: maxCommandNum = 1; break;
        }
            if(code == KeyEvent.VK_W)
            {
                gp.ui.commandNum--;
                // ADD SE HERE
                if(gp.ui.commandNum < 0)
                {
                    gp.ui.commandNum = maxCommandNum;
                }
            }
            if(code == KeyEvent.VK_S)
                {
                    gp.ui.commandNum++;
                    //Add SE HERE
                    if(gp.ui.commandNum > maxCommandNum)
                {
                gp.ui.commandNum = 0;
                }
        }

    }

    public void titleState(int code)
    {
        if(code == KeyEvent.VK_W)//if W is pressed
        {if(gp.ui.commandNum > 0)//on further selection
            gp.ui.commandNum--;//go back by one
        }
        if(code == KeyEvent.VK_S)//if s is pressed
        {
            if(gp.ui.commandNum < 2)//on further backward seelct
                gp.ui.commandNum++;//go upwards by one
        }
        if(code == KeyEvent.VK_ENTER)//if enter is pressed
        {
            switch (gp.ui.commandNum) //check which selection is the user on
            {
                case 0 -> gp.gameState = gp.playState;//new game
                case 1 -> gp.gameState = gp.playState;//load game
                case 2 -> System.exit(0);//exit game
            }
        }
    }

    public void playState(int code)
    {
        if(code == KeyEvent.VK_W)//if w is pressed
        {
            upPressed = true;//update variable
        }
        if(code == KeyEvent.VK_A)//if a is pressed
        {
            leftPressed = true;//update variable
        }
        if(code == KeyEvent.VK_S)//if s is pressed
        {
            downPressed = true;//update variable
        }
        if(code == KeyEvent.VK_D)//if d is pressed
        {
            rightPressed = true;//update variable
        }
        if(code == KeyEvent.VK_ENTER)//if enter is pressed
        {
            enterPressed = true;//update variable
        }
        if(code == KeyEvent.VK_R)//if enter is pressed
        {
            rPressed = true;//update variable
        }
        if(code == KeyEvent.VK_E)//if enter is pressed
        {
            ePressed = true;//update variable
        }
        if(code == KeyEvent.VK_C)
        {
            gp.gameState = gp.characterState;
        }
        if(code == KeyEvent.VK_ESCAPE)//if ESC is pressed
        {
            gp.gameState = gp.pauseState;//change game state
        }
        if(code == KeyEvent.VK_L)//if L is pressed
        {
            gp.gameState = gp.optionsState;//change game state
        }
        if(code == KeyEvent.VK_P)//if p is pressed
        {
            if(gp.devMode)//if gamedev more is on
                gp.devMode = false; //turn off
            else if(!gp.devMode)//if off
                gp.devMode = true;//turn on
        }
        if(gp.devMode)//game dev mode
        {
            if(code == KeyEvent.VK_EQUALS)//change speed
                gp.player.speed++;
            if(code == KeyEvent.VK_MINUS)
                gp.player.speed--;
            if(code == KeyEvent.VK_K)//change keys in inventory
                gp.player.inventory.add(gp.objectM.objs[3]);
            if(code == KeyEvent.VK_L)
                if(gp.player.checkKey())
                    gp.player.inventory.remove(gp.objectM.objs[3]);
        }
    }

    public void pauseState(int code)
    {
        if(code == KeyEvent.VK_ESCAPE)//if esc pressed
        {
            gp.gameState = gp.playState;//cahnge game state
        }
        if(code == KeyEvent.VK_ENTER)
        {
            System.exit(0);
        }
    }

    public void dialogueState(int code)
    {
        if(code == KeyEvent.VK_ENTER)//if enter is pressed
        {
            gp.gameState = gp.playState;//change game state
        }
    }

    public void characterState(int code)
    {
        if(code == KeyEvent.VK_C)
        {
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_W)//if w is pressed
        {
            if(gp.ui.slotRow > 0)
                gp.ui.slotRow--;
        }
        if(code == KeyEvent.VK_A)//if a is pressed
        {
            if(gp.ui.slotCol > 0)
                gp.ui.slotCol--;
        }
        if(code == KeyEvent.VK_S)//if s is pressed
        {
            if(gp.ui.slotRow < 4)
                gp.ui.slotRow++;
        }
        if(code == KeyEvent.VK_D)//if d is pressed
        {
            if(gp.ui.slotCol < 5)
                gp.ui.slotCol++;
        }
        if(code == KeyEvent.VK_ENTER)
        {
            gp.player.selectItem();
        }
    }

    //-------key released vevent handler-------//
    @Override
    public void keyReleased(KeyEvent e) 
    {
        int code = e.getKeyCode();//key released
        
        if(code == KeyEvent.VK_W)//if w
        {
            upPressed = false;//update variable
        }
        if(code == KeyEvent.VK_A)//if a
        {
            leftPressed = false;//update variable
        }
        if(code == KeyEvent.VK_S)//if s
        {
            downPressed = false;//update variable
        }
        if(code == KeyEvent.VK_D)//if d
        {
            rightPressed = false;//update variable
        }
        if(code == KeyEvent.VK_ENTER)//if enter
        {
            enterPressed = false;//update variable
        }
        if(code == KeyEvent.VK_R)//if enter
        {
            rPressed = false;//update variable
        }
        if(code == KeyEvent.VK_E)//if enter is pressed
        {
            ePressed = false;//update variable
        }
    }  
}

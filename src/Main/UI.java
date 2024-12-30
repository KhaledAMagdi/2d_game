package Main;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public class UI 
{
    //-------variables needed-------//
   GamePanel gp; //game panel
   Graphics2D g2; //graphics used to draw
   Font arial; //font
   Font Comic; //font
   Font Impact; //font
   public boolean messageOn = false; //object displays a message or not
   public String message = ""; //object message
   public int messageCounter = 0; //message counter
   public String currentDialogue = ""; //current dialogue to be drawn
   public int commandNum = 0; //game select
   BufferedImage heart,halfHeart,emptyHeart,keyImage; //images to be used in UI

   //-------constructor-------//
   public UI(GamePanel gp)
    {
	this.gp = gp; //refer to game panel
	arial = new Font("Arial", Font.PLAIN, 30); //font
        Comic = new Font("Comic Sans MS", Font.PLAIN, 30); //font
        Impact = new Font("Palatino Linotype", Font.PLAIN, 30); //font
          
        heart = gp.objectM.objs[0].image[0]; //load image from object
        halfHeart = gp.objectM.objs[1].image[0]; //load image from object
        emptyHeart = gp.objectM.objs[2].image[0]; //load image from object
        keyImage = gp.objectM.objs[3].image[0]; //load image from object
    }
   
   //-------message to be displayed-------//
   public void showMessage(String text)
    {
       message = text;
       messageOn = true;
    }

   //-------main draw method-------//
   public void draw(Graphics2D g2)
    {
       this.g2 = g2; //graphics used
       g2.setFont(arial); //font to be used
       g2.setColor(Color.white); //colour of said thing to be drawn
       
       if(gp.gameState == gp.titleState)//check game state
       {
           drawTitleScreen(); //title screen draw method
       }
       if(gp.gameState == gp.playState)
        {
            //checks collision with npc or object to display player prompt
            if(gp.player.interactNPC(gp.cChecker.checkEntity(gp.player,gp.npcM.npcs)) || gp.player.pickUpObject(gp.cChecker.checkObject(gp.player,true)))
                drawPlayerPrompt(); //player prompt draw method
                    
            drawKeys(); //keys in inventory draw method
            drawPlayerLife(); //health bar draw method
        }
       if(gp.gameState == gp.pauseState)
        {
           drawPauseScreen(); //pause menu draw method
           drawKeys(); //keys in inventory draw method
           drawPlayerLife(); //health bar draw method
        }
       if(gp.gameState == gp.dialogueState)
       {
           drawPlayerLife(); //health bar draw method
           drawDialogueScreen(); //dialogue draw method
       }     
    }
   
   //-------key in inventory-------//
   public void drawKeys()
   {
        int x = 0; //x position
        int y =gp.tileSize*2; //y position
        g2.setFont(arial); //font
        g2.setColor(Color.white); //colour of text
        g2.drawImage(keyImage,x,y, gp.tileSize, gp.tileSize,null); //draws key image
        g2.drawString("x "+gp.player.hasKey, x+gp.tileSize, y+gp.tileSize-10); //draw amount of keys
        
        if(messageOn == true)//new key aquired
        {
            g2.setFont(g2.getFont().deriveFont(20F));//sets font
            g2.drawString(message,x+20,y+gp.tileSize*2);//draws said message
               
            messageCounter++;//increments message to be 
            
            if(messageCounter > 60) //message stays displayed for 60 frames
            {
                messageCounter = 0;
                messageOn = false;
            }
        } 
   }
   
   //-------player health bar-------//
   public void drawPlayerLife()
   {
       int x = gp.tileSize/5; //x position
       int y = gp.tileSize/5; //y position
       int i = 0; //control variable
       
       while(i < gp.player.maxLife/2) //every 2 maxlife = 1 heart, while loop to draw all empty hearts
       {
           g2.drawImage(emptyHeart,x,y,gp.tileSize,gp.tileSize,null); //draw empty heart
           i++; //increment control
           
           x += gp.tileSize+10; //increment x position to draw hearts next to each other
       }
       
       x = gp.tileSize/5; //rest x position
       y = gp.tileSize/5; //rest y position
       i = 0; //rest control variable
       
       while( i < gp.player.life) //draw actual heart
       {
           g2.drawImage(halfHeart,x,y,gp.tileSize,gp.tileSize,null); //draw half heart as 1 maxlife = half heart
           i++;
           
           if(i < gp.player.life) //if life is still larger means player has a full heart
           {
               g2.drawImage(heart,x,y,gp.tileSize,gp.tileSize,null); //draw full heart
           }
           i++;//increment control
           x += gp.tileSize+10;//increment x position to draw heart next to each other
       }
   }
   
   //-------title screen-------//
   public void drawTitleScreen()
   {
       
        g2.drawImage(gp.tileM.tile[116].image,0,0,gp.screenWidth,gp.screenHeight,null);//backgropund image
       
        g2.setFont(Comic);//font used
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,100f));//font settings
        String text = "Mini Swords Man";//text to be drawn
        int x = getXforMidtext(text);//x position
        int y = gp.tileSize * 3;//y position
        g2.setColor(Color.BLACK);//colour of shadow
        g2.drawString(text,x+4,y+3);//draw text shadow
        g2.setColor(Color.WHITE);//colour of text
        g2.drawString(text, x, y);//draw actual text
       
        x += (gp.tileSize * 4);//x positon
        y += (gp.tileSize * 2);//y position
        g2.drawImage(gp.player.down[0],x,y,gp.tileSize*2,gp.tileSize*2,null);//draw player in the middle as manzar ya3ny
       
        g2.setFont(Impact);//font used
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,48));//font settings
        text = "New Game";//text to be drawn
        x = getXforMidtext(text);//x position
        y += (gp.tileSize * 4);//y position
        g2.drawString(text,x,y); //draws selection
        
        if(commandNum == 0)//checks which selection the user is on
        {
            g2.drawString(">",x-gp.tileSize/2,y);//draw select thingy example : "> new game"
        }
        
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,48));//font settings
        text = "Load Game";//text to be drawn
        x = getXforMidtext(text);//x position
        y += (gp.tileSize);//y position
        g2.drawString(text,x,y);//draw selection
        
        if(commandNum == 1)//checks which selection the user is on
        {
            g2.drawString(">",x-gp.tileSize/2,y);//draw select thingy
        }
        
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,48));//font settings
        text = "Quit";//text to be drawn
        x = getXforMidtext(text);//x position
        y += (gp.tileSize);//y position
        g2.drawString(text,x,y);//draw selection
        
        if(commandNum == 2)//checks which selection the user is on
        {
            g2.drawString(">",x-gp.tileSize/2,y);//draw select thingy
        }
   }
   
   //-------pause screen-------//
    public void drawPauseScreen()
    {
        String text = "PAUSED"; //text to be drawn
        int x = getXforMidtext(text);//x position
        int y = gp.screenHeight/2;//y position
        g2.drawString(text, x, y);//draw text
    }
    
    //-------dialogue screen-------//
    public void drawDialogueScreen()
    {
        RoundRectangle2D window = new RoundRectangle2D.Double(); //dialogue window
        window.setRoundRect(gp.tileSize * 2,gp.tileSize / 2,gp.screenWidth - (gp.tileSize * 4),gp.tileSize * 2,35,35); //dialoge window specifications
        
        drawSubWindow(window); //draw window

        int x = (int)window.getX() + gp.tileSize/2; //x position
        int y = (int)window.getY() + gp.tileSize/2; //y position
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,24F)); //font settings
        for(String line : currentDialogue.split("\n")) //checks \n in text to be drawn as new line
        {
            g2.drawString(line,x,y);//draw string
            y += 30; //increment y when \n to appear new line
        }
    }
    
    //-------player prompt screen-------//
    public void drawPlayerPrompt()
    {
        int x = gp.player.screenX*2 - (gp.tileSize * 3); //x position
        int y = gp.player.screenY*2 + (gp.tileSize/2); //y position
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,24F)); //font settings
        g2.drawString("Press Enter to Interact",x,y); //draw text

        if(gp.devMode)
            System.out.println("Interaction complete");//consol output to signify method call
    }
    
    //-------dialogue box-------//
    public void drawSubWindow(RoundRectangle2D window)
    {
        Color c = new Color(0,0,0,200); //colour of box
        g2.setColor(c);//colour to be drawn
        g2.fill(window);//fill window
        g2.draw(window);//draw window
        
        c = new Color(255,255,255);//colour of the stroke
        g2.setColor(c);//colour to be drawn
        g2.setStroke(new BasicStroke(5));//stroke size
        g2.draw(window);//draw stroke
    }
    
    //-------middle of screen-------//
    public int getXforMidtext(String text)
    {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();//length of text
        return gp.screenWidth/2 - length/2;//middle of screen with respect to text length
    }
}
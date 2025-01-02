package entity;

import Main.GamePanel;
import Main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player extends Entity
{
   GamePanel gp;
    KeyHandler keyH;
    
    public final int screenX;
    public final int screenY;
    public int hasKey =0;
    
    int numOfImages = 12;
    
    public Player(GamePanel gp, KeyHandler  keyH)
    {
        //super(gp);
        this.gp = gp;
        this.keyH = keyH;
        
        screenX = gp.screenWidth/2 - (gp.tileSize / 2) ; //player spawns in middle of screen and middle of tile
        screenY = gp.screenHeight/2 - (gp.tileSize / 2);
        
        solidArea = new Rectangle((gp.tileSize/2)-((gp.tileSize/3)/2), (gp.tileSize/2)-((gp.tileSize/3)/2), gp.tileSize/3, gp.tileSize/3);
        solidAreaDefaultX = (gp.tileSize/2)-((gp.tileSize/3)/2);
        solidAreaDefaultY = (gp.tileSize/2)-((gp.tileSize/3)/2);
        setDefaultValues();
        getPlayerImage();
    }
    
    private void setDefaultValues()
    {
        worldX = gp.tileSize * 9;
        worldY = gp.tileSize * 7;
        speed = 20;
        direction = "down";
        
        maxLife = 6;
        life = maxLife;
    }
    
    private void getPlayerImage()
    {
           try{
            for(int i = 0; i < numOfImages; i++)
            {
                String file = String.format("/res/player/up%03d.png/",i);
                up[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
            for(int i = 0; i < numOfImages; i++)
            {
                String file = String.format("/res/player/down%03d.png/",i);
                down[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
            for(int i = 0; i < numOfImages; i++)
            {
                String file = String.format("/res/player/right%03d.png/",i);
                right[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
            for(int i = 0; i < numOfImages; i++)
            {
                String file = String.format("/res/player/left%03d.png/",i);
                left[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
           }catch(IOException e)
           {
                System.out.println("Failed to load image");
           }
    }
    
    public void update()
    {
        if(keyH.upPressed || keyH.downPressed ||keyH.leftPressed || keyH.rightPressed)
        {
            if(keyH.upPressed)
            {  
                direction = "up"; 
            }
            if(keyH.downPressed)
            {
                direction = "down";  
            }
            if(keyH.leftPressed)
            {
                direction = "left"; 
            }
            if(keyH.rightPressed)
            {
                direction = "right";
            }

            if(!collisionOn)
            {
               switch(direction)
               {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
               }
            }
            
            //Check event
            gp.eventH.checkEvent();
            
            //Check tile collision
            collisionOn = false;
            gp.cChecker.checkTile(this);
       
            //Check objects collision
            int objIndex = gp.cChecker.checkObject(this, true); //gets index of collision
            pickUpObject(objIndex);
       
            //Check NPC collision
            int npcIndex = gp.cChecker.checkEntity(this,gp.npcM.npcs);
            interactNPC(npcIndex);

            int monsterIndex = gp.cChecker.checkEntity(this,gp.monsterM.monster);
            contactMonster(monsterIndex);

            spriteCounter++;
            if(spriteCounter > 11)
            {
                if(spriteNum < numOfImages)
                    spriteNum++;
                else
                    spriteNum = 1;
            
                spriteCounter = 0;
            }
        }
        else
        {
            spriteCounter = 0;
            spriteNum = 1;
        }

        if(invincible)
        {
            invincibleTimer++;
            if(invincibleTimer > 60)
            {
                invincible = false;
                invincibleTimer = 0;
            }
        }
    }
    
    public boolean pickUpObject(int i)
    {
    	if(i != 999) // means we touched an object
    	{
            switch(gp.objectM.objs[i].name) 
            {
                case "key" -> 
                {
                    hasKey++;
                    gp.ui.showMessage(gp.objectM.objs[i].msgShown);
                    gp.objectM.objs[i] = null;
                    System.out.println("Key:"+hasKey);
                }
    		case "chest" -> 
                {
                    if(hasKey > 0)
                    {
                        if(gp.keyH.enterPressed)
                        {
                            gp.ui.showMessage(gp.objectM.objs[i].msgShown);
                            gp.objectM.objs[i] = null;
                            hasKey--;
                            life--;
                            System.out.println("Key:"+hasKey);
                        }
                        return true;
                    }
                }
            }
    	}
        return false;
    }
    
    public boolean interactNPC(int i)
    {
        if(i != 999)
    	{
            if(gp.keyH.enterPressed)
            {
                gp.gameState = gp.dialogueState;
                gp.npcM.speak(i);
            }
            gp.keyH.enterPressed = false;
            
            return true;
        }
        return false;
    }

    public void contactMonster(int i)
    {
        if(i != 999)
        {
            if(!invincible) {
                life--;
                invincible = true;
            }
        }
    }
            
    public void draw(Graphics2D g2)
    {
        BufferedImage image = null;

        switch(direction)
        {
            case "up" ->
            {
                for(int i = 0; i < numOfImages; i++)
                {
                    if(spriteNum == i+1)
                        image = up[i];
                }
            }
            case "down" ->
            {
                for(int i = 0; i < numOfImages; i++)
                {
                    if(spriteNum == i+1)
                        image = down[i];
                }
            }
            case "left" ->
            {
                for(int i = 0; i < numOfImages; i++)
                {
                    if(spriteNum == i+1)
                        image = left[i];
                }
            }
            case "right" ->
            {
                for(int i = 0; i < numOfImages; i++)
                {
                    if(spriteNum == i+1)
                        image = right[i];
                }
            }
        }

        if(invincible)
        {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        
        if(gp.devMode)
        {
            System.out.println("invinsibletimer: "+invincibleTimer);

            g2.setColor(Color.magenta);
            g2.drawRect(screenX + solidArea.x,screenY + solidArea.y, solidArea.width, solidArea.height);
            
            String text = String.format(" -/= Speed = "+speed);
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,20));
            
            int x = 0;
            int y = (gp.tileSize * 5);
            g2.drawString(text,x,y); 
            
            text = " K/L keys";
            y += gp.tileSize/4;
            g2.drawString(text,x,y); 
        }
    }
}

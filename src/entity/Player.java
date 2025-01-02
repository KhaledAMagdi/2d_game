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
    int numOfSlashImages = 5;
    
    public Player(GamePanel gp, KeyHandler  keyH)
    {
        //super(gp);
        this.gp = gp;
        this.keyH = keyH;
        
        screenX = gp.screenWidth/2 - (gp.tileSize / 2) ; //player spawns in middle of screen and middle of tile
        screenY = gp.screenHeight/2 - (gp.tileSize / 2);
        
        solidArea = new Rectangle((gp.tileSize/2)-((gp.tileSize/3)/2), (gp.tileSize/2)-((gp.tileSize/3)/2), gp.tileSize/3, gp.tileSize/3);
        attackarea = new Rectangle((gp.tileSize/2)-((gp.tileSize/3)/2), (gp.tileSize/2)-((gp.tileSize/3)/2), 20, 20); //change width and height to change attack range
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
            for(int i = 0; i < numOfSlashImages; i++)
            {
                String file = String.format("/res/player/slashdown%d.png/",i);
                slashdown[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
            for(int i = 0; i < numOfSlashImages; i++)
            {
                String file = String.format("/res/player/slashup%d.png/",i);
                slashup[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
            for(int i = 0; i < numOfSlashImages; i++)
            {
                String file = String.format("/res/player/slashleft%d.png/",i);
                slashleft[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
            for(int i = 0; i < numOfSlashImages; i++)
            {
                String file = String.format("/res/player/slashright%d.png/",i);
                slashright[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
           }catch(IOException e)
           {
                System.out.println("Failed to load image");
           }
    }
    
    public void update()
    {
        if(attacking == true)
        {
            attacking();
        }

       else if(keyH.upPressed || keyH.downPressed ||keyH.leftPressed || keyH.rightPressed)
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
                if(!attacking)
                {
                    if(spriteNum < numOfImages)
                        spriteNum++;
                    else
                        spriteNum = 1;

                    spriteCounter = 0;
                }
                else
                {
                    if(spriteNum < numOfSlashImages)
                        spriteNum++;
                    else
                        spriteNum = 1;

                    spriteCounter = 0;
                }

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
        else
        {
            if(gp.keyH.rpressed == true)
            {
                attacking = true;
            }
        }

        return false;
    }
    public void attacking()
    {
        spriteCounter++;

        if(spriteCounter <= 5)  //if within first 5 frames load attack image 1
        {
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25)
        {
            spriteNum = 2;
            //Save current worldX, worldY and solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //Adujyst player worldX and Y for attack area
            switch(direction)
            {
                case "up": worldY -= attackarea.height; break;
                case "down": worldY += attackarea.height; break;
                case "left": worldX -= attackarea.width; break;
                case "right": worldX += attackarea.width; break;
            }
            //attack Area becomes solid area
            solidArea.width = attackarea.width;
            solidArea.height = attackarea.height;

            int monsterIndex = gp.cChecker.checkEntity(this, gp.monsterM.monster);
            damageMonster(monsterIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if(spriteCounter > 25)
        {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
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
    public void damageMonster(int i)
    {
        if(i != 999)
        {
            System.out.println("Hit!");
            gp.monsterM.monster[i].life -= 1;
//            if(gp.monsterM.monster[i].life <= 0) //need to implement death for player to make monster die
//            {
//                gp.monsterM.monster[i] = null;
//            }
        }
        else
        {
            System.out.println("Miss!");
        }
    }
            
    public void draw(Graphics2D g2)
    {
        BufferedImage image = null;

        switch(direction)
        {
            case "up" ->
            {
                if(!attacking)
                {
                    for(int i = 0; i < numOfImages; i++)
                    {
                        if(spriteNum == i+1)
                            image = up[i];
                    }
                }
                else if(attacking)
                {
                    for(int i = 0; i < numOfSlashImages; i++) //Screen adjustments may be needed if slash image changes players position (video 26 min 15)
                    {
                        if(spriteNum == i+1)
                            image = slashup[i];
                    }
                }

            }
            case "down" ->
            {
                if(!attacking)
                {
                    for(int i = 0; i < numOfImages; i++)
                    {
                        if(spriteNum == i+1)
                            image = down[i];
                    }
                }
                else if(attacking)
                {
                    for(int i = 0; i < numOfSlashImages; i++)
                    {
                        if(spriteNum == i+1)
                            image = slashdown[i];
                    }
                }
            }
            case "left" ->
            {
                if(!attacking)
                {
                    for(int i = 0; i < numOfImages; i++)
                    {
                        if(spriteNum == i+1)
                            image = left[i];
                    }
                }
                else if(attacking)
                {
                    for(int i = 0; i < numOfSlashImages; i++)
                    {
                        if(spriteNum == i+1)
                            image = slashleft[i];
                    }
                }
            }
            case "right" ->
            {
                if(!attacking)
                {
                    for(int i = 0; i < numOfImages; i++)
                    {
                        if(spriteNum == i+1)
                            image = right[i];
                    }
                }
                else if(attacking)
                {
                    for(int i = 0; i < numOfSlashImages; i++)
                    {
                        if(spriteNum == i+1)
                            image = slashright[i];
                    }
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

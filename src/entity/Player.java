package entity;

import Main.GamePanel;
import Main.KeyHandler;

import java.awt.*;

public class Player extends Entity
{
   GamePanel gp;
    KeyHandler keyH;
    
    public final int screenX;
    public final int screenY;
    public int hasKey =0;

    public Player(GamePanel gp, KeyHandler  keyH)
    {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;
        
        screenX = gp.screenWidth/2 - (gp.tileSize / 2) ; //player spawns in middle of screen and middle of tile
        screenY = gp.screenHeight/2 - (gp.tileSize / 2);
        numOfImages = 12;
        numOfSlashImages = 5;
        name = "";
        
        solidArea = new Rectangle((gp.tileSize/2)-((gp.tileSize/3)/2), (gp.tileSize/2)-((gp.tileSize/3)/2), gp.tileSize/3, gp.tileSize/3);
        attackArea = new Rectangle((gp.tileSize/2)-((gp.tileSize/3)/2), (gp.tileSize/2)-((gp.tileSize/3)/2), 20, 20); //change width and height to change attack range
        solidAreaDefaultX = (gp.tileSize/2)-((gp.tileSize/3)/2);
        solidAreaDefaultY = (gp.tileSize/2)-((gp.tileSize/3)/2);
        setDefaultValues();
        getImage();
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
    
    public void update()
    {
        if(attacking)
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
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);
       
            //Check NPC collision
            int npcIndex = gp.cChecker.checkEntity(this,gp.npcM.npcs);
            interactNPC(npcIndex);

            //Check monster collision
            int monsterIndex = gp.cChecker.checkEntity(this,gp.monsterM.monsters);
            contactMonster(monsterIndex);

            spriteCounter++;
            if(spriteCounter > 11)
            {
                if(attacking)
                {
                    if(spriteNum < numOfSlashImages)
                        spriteNum++;
                    else
                        spriteNum = 1;

                }
                else
                {
                    if(spriteNum < numOfImages)
                        spriteNum++;
                    else
                        spriteNum = 1;

                }

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
        else
        {
            if(gp.keyH.rpressed)
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

            //adjust player worldX and Y for attack area
            switch(direction)
            {
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }
            //attack Area becomes solid area
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            int monsterIndex = gp.cChecker.checkEntity(this, gp.monsterM.monsters);
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
        if(i != 999 && !gp.monsterM.monsters[i].invincible)
        {
            System.out.println("Hit!");
            gp.monsterM.monsters[i].life -= 1;
            System.out.println(gp.monsterM.monsters[i].life);
            gp.monsterM.monsters[i].invincible = true;
        }
        else
        {
            System.out.println("Miss!");
        }
    }
}

package entity;

import Main.GamePanel;
import Main.KeyHandler;

import java.awt.*;
import java.util.ArrayList;
import Object.*;

public class Player extends Entity
{
   GamePanel gp;
    KeyHandler keyH;
    
    public final int screenX;
    public final int screenY;
    public ArrayList<SuperObject> inventory = new ArrayList<>();
    public final int invSize = 30;

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
        setItems();
    }
    
    private void setDefaultValues()
    {
        worldX = gp.tileSize * 9;
        worldY = gp.tileSize * 7;
        speed = 20;
        direction = "down";
        
        maxLife = 6;
        life = maxLife;
        level = 1;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = gp.objectM.objs[4];
        currentShield = gp.objectM.objs[8];
        attack = getAttack();
        defense = getDefence();
        projectile = gp.projM.projs[0];
    }

    public void setItems()
    {
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(gp.objectM.objs[3]);
    }

    public int getAttack() {
        return attack = strength * currentWeapon.attackValue;
    }

    public int getDefence() {
        return defense = dexterity * currentShield.defenseValue;
    }
    
    @Override
    public void update()
    {
        if(attacking)
        {
            attacking();
        }
       else if(keyH.upPressed || keyH.downPressed ||keyH.leftPressed || keyH.rightPressed || keyH.enterPressed)
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

            if(!collisionOn && !keyH.enterPressed)
            {
               switch(direction)
               {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
               }
            }

            keyH.enterPressed = false;

            spriteCounter++;
            if (spriteCounter > 11) {
                if (attacking) {
                    if (spriteNum < numOfSlashImages) {
                        spriteNum++;
                    } else {
                        spriteNum = 1;
                    }
                } else {
                    if (spriteNum < numOfImages)
                        spriteNum++;
                    else
                        spriteNum = 1;

                }
                spriteCounter = 0;
            }

        } else {
            spriteCounter = 0;
            spriteNum = 1;
        }

       if(gp.keyH.ePressed && !projectile.alive)
       {
           projectile.set(worldX, worldY, direction, true, this);
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
            if(inventory.size() < invSize) {
                switch (gp.objectM.objs[i].name) {
                    case "key" -> {
                        inventory.add(gp.objectM.objs[3]);
                        gp.ui.addMessage(gp.objectM.objs[i].msgShown);
                        gp.objectM.objs[i].drawable = false;
                    }
                    case "chest" -> {
                        if (checkKey()) {
                            if (gp.keyH.enterPressed) {
                                gp.ui.addMessage(gp.objectM.objs[i].msgShown);
                                gp.objectM.objs[i] = null;
                                inventory.remove(gp.objectM.objs[3]);
                            }
                            return true;
                        }
                    }
                    default -> {
                        inventory.add(gp.objectM.objs[i]);
                        if(gp.objectM.objs[i].msgShown != null)
                            gp.ui.addMessage(gp.objectM.objs[i].msgShown);
                        gp.objectM.objs[i].drawable = false;
                    }
                }
            }else
            {
                gp.ui.addMessage("Inventory is full!");
            }
        }
        return false;
    }

    public boolean checkKey()
    {
        boolean isItemFound =false;
        for(SuperObject item: inventory){
            if(item.equals(gp.objectM.objs[3])){
                isItemFound = true;
                return isItemFound;
            }
        }
        return  isItemFound;
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
            if(gp.keyH.rPressed)
            {
                attacking = true;
            }
        }

        return false;
    }

    public void attacking() {
        spriteCounter++;

        // Define the total sprite sequences with corresponding settings
        if (spriteCounter <= 25) {
            // Determine the sprite and attack frame numbers based on the counter
            int frameInterval = 5;
            spriteNum = (spriteCounter - 1) / frameInterval + 1;

            if (spriteCounter % frameInterval == 0) {
                adjustAttackAreaTemporary();
            }
        } else {
            resetAttackState();
        }
    }

    private void adjustAttackAreaTemporary() {
        // Save current state
        int currentWorldX = worldX;
        int currentWorldY = worldY;
        int solidAreaWidth = solidArea.width;
        int solidAreaHeight = solidArea.height;

        // Adjust based on direction
        switch (direction) {
            case "up" -> worldY -= attackArea.height;
            case "down" -> worldY += attackArea.height;
            case "left" -> worldX -= attackArea.width;
            case "right" -> worldX += attackArea.width;
        }

        // Set attack area
        solidArea.width = attackArea.width;
        solidArea.height = attackArea.height;

        // Check and damage monsters
        int monsterIndex = gp.cChecker.checkEntity(this, gp.monsterM.monsters);
        damageMonster(monsterIndex, attack);

        // Reset to previous state
        worldX = currentWorldX;
        worldY = currentWorldY;
        solidArea.width = solidAreaWidth;
        solidArea.height = solidAreaHeight;
    }

    private void resetAttackState() {
        spriteNum = 1;
        spriteCounter = 0;
        attacking = false;
    }

    public void contactMonster(int i)
    {
        if(i != 999)
        {
            if(!invincible) {
                int damage = gp.monsterM.monsters[i].attack - defense;
                if(damage < 0) damage = 0;
                life -= damage;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i, int attack)
    {
        if(i != 999 && !gp.monsterM.monsters[i].invincible)
        {
            int damage = attack - gp.monsterM.monsters[i].defense;
            if(damage < 0) damage = 0;
            gp.monsterM.monsters[i].life -= damage;
            gp.monsterM.monsters[i].invincible = true;
            gp.ui.addMessage(damage + "damage!");
            gp.monsterM.monsters[i].damageReaction();

            if(gp.monsterM.monsters[i].life <= 0) {
                gp.ui.addMessage("The monster " + gp.monsterM.monsters[i].name + " has been killed!");
                gp.ui.addMessage("Exp + " + gp.monsterM.monsters[i].exp);
                gp.monsterM.monsters[i].dying = true;
                exp += gp.monsterM.monsters[i].exp;
                checkLevelUp();
            }
        }
    }

    public void checkLevelUp() {
        if (exp >= nextLevelExp) {
            level++;
            nextLevelExp += nextLevelExp;
            maxLife += 2;
            strength++;
            dexterity++;

            if(life < maxLife-1)
                life += 2;

            attack = getAttack();
            defense = getDefence();

            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = String.format("Level up! New level: " + level + "\nStats has increased!");
        }
    }

    public void selectItem()
    {
       int itemIndex = gp.ui.getItemIndex();

       if(itemIndex < inventory.size())
       {
           SuperObject item = inventory.get(itemIndex);

           if(item.type == item.sword){
               currentWeapon = item;
               getAttack();
               gp.ui.addMessage("You have equipped " + item.name);
           }
           if(item.type == item.shield){
               currentShield = item;
               getDefence();
               gp.ui.addMessage("You have equipped " + item.name);
           }
           if(item.type == item.consumable)
           {
               gp.objectM.use(item);
               inventory.remove(itemIndex);
           }
       }
    }
}
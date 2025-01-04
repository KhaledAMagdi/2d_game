package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import Object.*;

import Main.GamePanel;

import javax.imageio.ImageIO;

//Abstract Class
public class Entity {
    public GamePanel gp;
    public int worldX, worldY;
    public int speed;

    public String name = "";
    public BufferedImage[] up = new BufferedImage[12];
    public BufferedImage[] down = new BufferedImage[12];
    public BufferedImage[] right = new BufferedImage[12];
    public BufferedImage[] left = new BufferedImage[12];
    public BufferedImage[] slashup = new BufferedImage[5];
    public BufferedImage[] slashdown = new BufferedImage[5];
    public BufferedImage[] slashright = new BufferedImage[5];
    public BufferedImage[] slashleft = new BufferedImage[5];
    public BufferedImage[] swordup = new BufferedImage[5];
    public BufferedImage[] sworddown = new BufferedImage[5];
    public BufferedImage[] swordleft = new BufferedImage[5];
    public BufferedImage[] swordright = new BufferedImage[5];
    public int numOfImages;
    public int numOfSlashImages = 0;
    public boolean attacking = false;

    public String direction = "down";

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public int actionLock = 0;
    public Rectangle solidArea;
    public Rectangle attackArea;

    public boolean collisionOn = false;
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    public String[] dialogue;

    public int dialogueIndex = 0;

    public int maxLife;
    public int life;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int magic;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public SuperObject currentWeapon;
    public SuperObject currentShield;

    public boolean invincible = false;
    public int invincibleTimer = 0;

    public int type = 0; // 0->player 1->npc 2->monster
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public String typeString = "";

    public boolean alive = true;
    public boolean dying = false;
    public int dyingCounter = 0;

    public boolean hpBarOn = false;
    public int hpBarCounter = 0;

    public int maxMana;
    public int mana;
    public Projectiles projectile;

    public Entity(GamePanel gp) {this.gp = gp;}

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        BufferedImage weapon = null;

        switch (direction) {
            case "up" -> {
                if (!attacking) {
                    for (int i = 0; i < numOfImages; i++) {
                        if (spriteNum == i + 1)
                            image = up[i];
                    }
                } else if (attacking) {
                    for (int i = 0; i < numOfSlashImages; i++) //Screen adjustments may be needed if slash image changes players position (video 26 min 15)
                    {
                        if (spriteNum == i + 1)
                            image = slashup[i];
                    }
                    for (int j = 0; j < numOfSlashImages; j++) {
                        if (spriteNum == j + 1)
                            weapon = swordup[j];
                    }
                }
            }
            case "down" -> {
                if (!attacking) {
                    for (int i = 0; i < numOfImages; i++) {
                        if (spriteNum == i + 1)
                            image = down[i];
                    }
                } else if (attacking) {
                    for (int i = 0; i < numOfSlashImages; i++) {
                        if (spriteNum == i + 1)
                            image = slashdown[i];
                    }
                    for (int j = 0; j < numOfSlashImages; j++) {
                        if (spriteNum == j + 1)
                            weapon = sworddown[j];
                    }
                }
            }
            case "left" -> {
                if (!attacking) {
                    for (int i = 0; i < numOfImages; i++) {
                        if (spriteNum == i + 1)
                            image = left[i];
                    }
                } else if (attacking) {
                    for (int i = 0; i < numOfSlashImages; i++) {
                        if (spriteNum == i + 1)
                            image = slashleft[i];
                    }
                    for (int j = 0; j < numOfSlashImages; j++) {
                        if (spriteNum == j + 1)
                            weapon = swordleft[j];
                    }
                }
            }
            case "right" -> {
                if (!attacking) {
                    for (int i = 0; i < numOfImages; i++) {
                        if (spriteNum == i + 1)
                            image = right[i];
                    }
                } else if (attacking) {
                    for (int i = 0; i < numOfSlashImages; i++) {
                        if (spriteNum == i + 1)
                            image = slashright[i];
                    }
                    for (int j = 0; j < numOfSlashImages; j++) {
                        if (spriteNum == j + 1)
                            weapon = swordright[j];
                    }
                }
            }
        }

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(type == type_monster && hpBarOn) {

            double oneScale = (double) gp.tileSize / maxLife;
            double hpBar = oneScale * life;

            g2.setColor(new Color(35,35,35));
            g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12);

            g2.setColor(new Color(255, 0, 30));
            g2.fillRect(screenX, screenY - 15, (int)hpBar, 10);

            hpBarCounter++;

            if(hpBarCounter >= 500) {
                hpBarCounter = 0;
                hpBarOn = false;
            }

        }


        if (invincible) {
            hpBarOn = true;
            hpBarCounter = 0;
            changeAlpha(g2, 0.5f);
        }
        if(dying)
        {
            dyingAnimation(g2);
        }

        if(alive){
            if(attacking)
            {
                switch(direction){
                    case "down" ->{
                        g2.drawImage(image, screenX, screenY, (int)(gp.tileSize),(int)(gp.tileSize), null);
                        g2.drawImage(weapon, screenX-(gp.tileSize/10), screenY+(gp.tileSize/4), (int)(gp.tileSize),(int)(gp.tileSize), null);
                    }
                    case "up" ->{
                        g2.drawImage(weapon, screenX, screenY-(gp.tileSize/10), (int)(gp.tileSize),(int)(gp.tileSize), null);
                        g2.drawImage(image, screenX, screenY, (int)(gp.tileSize),(int)(gp.tileSize), null);
                    }
                    case "left" ->{
                        g2.drawImage(image, screenX, screenY, (int)(gp.tileSize),(int)(gp.tileSize), null);
                        g2.drawImage(weapon, screenX-(gp.tileSize/5), screenY+(gp.tileSize/10), (int)(gp.tileSize),(int)(gp.tileSize), null);
                    }
                    case "right" ->{
                        g2.drawImage(image, screenX, screenY, (int)(gp.tileSize),(int)(gp.tileSize), null);
                        g2.drawImage(weapon, screenX+(gp.tileSize/5), screenY+(gp.tileSize/10), (int)(gp.tileSize),(int)(gp.tileSize), null);
                    }
                }
            }
            else g2.drawImage(image, screenX, screenY, (int)(gp.tileSize),(int)(gp.tileSize), null);
        }

        changeAlpha(g2, 1f);

        if (gp.devMode) {

            if(invincible) {
                System.out.println("invinsibletimer: " + invincibleTimer);
            }

            g2.setColor(Color.magenta);
            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);

            if(type == type_player) {
                String text = String.format(" -/= Speed = " + speed);
                g2.setColor(Color.white);
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20));

                int x = 0;
                int y = (gp.tileSize * 5);
                g2.drawString(text, x, y);

                text = " K/L keys";
                y += gp.tileSize / 4;
                g2.drawString(text, x, y);
            }
        }
    }

    void dyingAnimation(Graphics2D g2)
    {
        dyingCounter++;

        int i = 5;

        if(dyingCounter <= i){changeAlpha(g2, 1f);}
        if(dyingCounter > i && dyingCounter <= i*2){changeAlpha(g2, 0f);}
        if(dyingCounter > i*2 && dyingCounter <= i*3){changeAlpha(g2, 1f);}
        if(dyingCounter > i*3 && dyingCounter <= i*4){changeAlpha(g2, 0f);}
        if(dyingCounter > i*4 && dyingCounter <= i*5){changeAlpha(g2, 1f);}
        if(dyingCounter > i*5 && dyingCounter <= i*6){changeAlpha(g2, 0f);}
        if(dyingCounter > i*6 && dyingCounter <= i*7){changeAlpha(g2, 1f);}
        if(dyingCounter > i*7 && dyingCounter <= i*8){changeAlpha(g2, 0f);}
        if(dyingCounter > i*8)
        {
            dying = false;
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alpha)
    {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    }

    public void getImage() {
        if (type == type_player)
            typeString = "player";
        if (type == type_npc)
            typeString = "npcs";
        if (type == type_monster)
            typeString = "monsters";

        try {
            for (int i = 0; i < numOfImages; i++) {
                String file = String.format("/res/%s/%sup%d.png/", typeString, name, i);
                up[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
            for (int i = 0; i < numOfImages; i++) {
                String file = String.format("/res/%s/%sdown%d.png/", typeString, name, i);
                down[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
            for (int i = 0; i < numOfImages; i++) {
                String file = String.format("/res/%s/%sright%d.png/", typeString, name, i);
                right[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
            for (int i = 0; i < numOfImages; i++) {
                String file = String.format("/res/%s/%sleft%d.png/", typeString, name, i);
                left[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
            for (int i = 0; i < numOfSlashImages; i++) {
                String file = String.format("/res/%s/slashdown%d.png/", typeString, i);
                slashdown[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
            for (int i = 0; i < numOfSlashImages; i++) {
                String file = String.format("/res/%s/slashup%d.png/", typeString, i);
                slashup[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
            for (int i = 0; i < numOfSlashImages; i++) {
                String file = String.format("/res/%s/slashleft%d.png/", typeString, i);
                slashleft[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
            for (int i = 0; i < numOfSlashImages; i++) {
                String file = String.format("/res/%s/slashright%d.png/", typeString, i);
                slashright[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
            for (int i = 0; i < numOfSlashImages; i++) {
                String file = String.format("/res/%s/sword_down%d.png/", typeString, i);
                sworddown[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
            for (int i = 0; i < numOfSlashImages; i++) {
                String file = String.format("/res/%s/sword_up%d.png/", typeString, i);
                swordup[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
            for (int i = 0; i < numOfSlashImages; i++) {
                String file = String.format("/res/%s/sword_right%d.png/", typeString, i);
                swordright[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
            for (int i = 0; i < numOfSlashImages; i++) {
                String file = String.format("/res/%s/sword_left%d.png/", typeString, i);
                swordleft[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
        } catch (IOException e) {
            System.out.println("Failed to load image");
        }
    }

    public void setAction() {
        Random random = new Random();
        int i = random.nextInt(100) + 1;
        actionLock++;

        if (actionLock == 120) {
            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLock = 0;
        }

        if(i > 99 && !projectile.alive) {
            projectile.set(worldX, worldY, direction, true, this);
        }
    }

    public void damageReaction()
    {
        actionLock = 0;

        direction = gp.player.direction;
    }

    public void update() {

        setAction();

        if (!collisionOn) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkPlayer(this);
        gp.cChecker.checkEntity(this, gp.npcM.npcs);
        gp.cChecker.checkEntity(this, gp.monsterM.monsters);
    }
}
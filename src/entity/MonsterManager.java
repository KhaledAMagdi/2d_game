package entity;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class MonsterManager
{
    GamePanel gp;
    public Entity[] monster;
    int npcNum=1;

    public MonsterManager(GamePanel gp)
    {
        this.gp = gp;

        monster = new Entity[npcNum];

        initiateMonsters();
        getImage();
    }

    private void initiateMonsters()
    {
        monster[0] = new Entity(gp);
        monster[0].type = 2;
        monster[0].name = "bat_";
        monster[0].speed = 3;
        monster[0].numOfImages = 2;
        monster[0].idle = new BufferedImage[monster[0].numOfImages];
        monster[0].worldX = 10 * gp.tileSize;
        monster[0].worldY = 9 * gp.tileSize;
        monster[0].solidArea = new Rectangle(0, 0, (int)(gp.tileSize*0.5), (int)(gp.tileSize*0.5));
        monster[0].solidAreaDefaultX = monster[0].solidArea.x;
        monster[0].solidAreaDefaultY = monster[0].solidArea.y;
        monster[0].direction = "idle";
        monster[0].maxLife = 3;
        monster[0].life = monster[0].maxLife;
    }

    public void getImage()
    {
        for (Entity monster : monster)
        {
            monster.getImage();
        }
    }

    public void setAction() {
        for (Entity monster : monster) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;
            monster.actionLock++;

            if (monster.actionLock == 120) {
                if (i <= 25) {
                    monster.direction = "up";
                }
                if (i > 25 && i <= 50) {
                    monster.direction = "down";
                }
                if (i > 50 && i <= 75) {
                    monster.direction = "left";
                }
                if (i > 75 && i <= 100) {
                    monster.direction = "right";
                }
                monster.actionLock = 0;
            }
        }
    }

    public void update() {
        for (Entity monster : monster) {

            setAction();

            if (!monster.collisionOn) {
                switch (monster.direction) {
                    case "up" -> monster.worldY -= monster.speed;
                    case "down" -> monster.worldY += monster.speed;
                    case "left" -> monster.worldX -= monster.speed;
                    case "right" -> monster.worldX += monster.speed;
                }
            }

            //Check tile collision
            monster.collisionOn = false;
            gp.cChecker.checkTile(monster);

            if(gp.cChecker.checkPlayer(monster) && !gp.player.invincible)
            {
                gp.player.life--;
                gp.player.invincible = true;
            }

            monster.spriteCounter++;

            if (monster.spriteCounter > 14) {
                if (monster.spriteNum < monster.numOfImages)
                    monster.spriteNum++;
                else
                    monster.spriteNum = 1;

                monster.spriteCounter = 0;
            }

            if(monster.invincible)
            {
                monster.invincibleTimer++;
                if(monster.invincibleTimer > 60)
                {
                    monster.invincible = false;
                    monster.invincibleTimer = 0;
                }
            }
        }
    }

    public void draw(Graphics2D g2)
    {
        for(Entity monster : monster)
        {
            monster.draw(g2);
        }
    }
}

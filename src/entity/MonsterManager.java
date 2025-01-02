package entity;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import static java.lang.Math.abs;

public class MonsterManager {
    GamePanel gp;
    public Entity[] monster;
    int numOfImages = 2;
    public int npcNum = 1;

    public MonsterManager(GamePanel gp) {
        this.gp = gp;

        monster = new Entity[npcNum];

        initiateMonsters();
        getImage();
    }

    private void initiateMonsters() {
        monster[0] = new Entity();
        monster[0].type = 2;
        monster[0].name = "bat";
        monster[0].speed = 3;
        monster[0].idle = new BufferedImage[numOfImages];
        monster[0].worldX = 20 * gp.tileSize;
        monster[0].worldY = 9 * gp.tileSize;
        monster[0].solidArea = new Rectangle(60, 60, (int) (gp.tileSize * 0.5), (int) (gp.tileSize * 0.5));
        monster[0].solidAreaDefaultX = monster[0].solidArea.x;
        monster[0].solidAreaDefaultY = monster[0].solidArea.y;
        monster[0].direction = "idle";
        monster[0].maxLife = 3;
        monster[0].life = monster[0].maxLife;
    }

    public void getImage() {
        for (Entity monster : monster) {
            try {
                for (int i = 0; i < numOfImages; i++) {
                    String file = String.format("/res/monsters/%s%d.png/", monster.name, i);
                    monster.idle[i] = ImageIO.read(getClass().getResourceAsStream(file));
                }
            } catch (IOException e) {
                System.out.println("Failed to load image");
            }
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
            //Monster Damage
            if (gp.cChecker.checkPlayer(monster) && !gp.player.invincible) {
                gp.player.life--;
                gp.player.invincible = true;
            }

            monster.spriteCounter++;

            if (monster.spriteCounter > 14) {
                if (monster.spriteNum < numOfImages)
                    monster.spriteNum++;
                else
                    monster.spriteNum = 1;

                monster.spriteCounter = 0;
            }

            if (monster.invincible) {
                monster.invincibleTimer++;
                if (monster.invincibleTimer > 60) {
                    monster.invincible = false;
                    monster.invincibleTimer = 0;
                }
            }
        }

    }

    public void deathAnimation(Graphics2D g2) {
        float flip = 0;
        for (int i = 0; i < npcNum; i++) {
            monster[i].dyingCounter++;
            for (int j = 0; j <= 40; j += 5) //Blinking effect
            {
                if (monster[i].dyingCounter > j && monster[i].dyingCounter <= j + 5) {
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, flip));
                    flip = abs(flip - 1);
                }
            }
            if (monster[i].dyingCounter > 40) {
                monster[i].dead = true;
                monster[i].alive = false;
            }
        }


    }


    public void draw(Graphics2D g2) {
        for (Entity monster : monster) {
            BufferedImage image = null;
            if (monster != null)
            {
                for (int i = 0; i < numOfImages; i++) {
                    if (monster.spriteNum == i + 1)
                        image = monster.idle[i];
                }

                int screenX = monster.worldX - gp.player.worldX + gp.player.screenX;
                int screenY = monster.worldY - gp.player.worldY + gp.player.screenY;

                if (monster.invincible) {
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                }
                if (monster.dead = true) {
                    deathAnimation(g2);
                }

                g2.drawImage(image, screenX, screenY, gp.tileSize * 2, (int) (gp.tileSize * 2), null);

                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

                if (gp.devMode) {
                    g2.setColor(Color.magenta);
                    g2.drawRect(screenX + monster.solidArea.x, screenY + monster.solidArea.y, monster.solidArea.width, monster.solidArea.height);
                }
            }
        }
    }
}

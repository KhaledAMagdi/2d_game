package entity;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MonsterManager {
    GamePanel gp;
    public Entity[] monsters;
    int npcNum = 1;

    public MonsterManager(GamePanel gp) {
        this.gp = gp;

        monsters = new Entity[npcNum];

        initiateMonsters();
        getImage();
    }

    private void initiateMonsters() {
        monsters[0] = new Entity(gp);
        monsters[0].type = 2;
        monsters[0].name = "bat_";
        monsters[0].speed = 3;
        monsters[0].numOfImages = 3;
        monsters[0].worldX = 10 * gp.tileSize;
        monsters[0].worldY = 9 * gp.tileSize;
        monsters[0].solidArea = new Rectangle(0, 0, (int) (gp.tileSize * 0.5), (int) (gp.tileSize * 0.5));
        monsters[0].solidAreaDefaultX = monsters[0].solidArea.x;
        monsters[0].solidAreaDefaultY = monsters[0].solidArea.y;
        monsters[0].maxLife = 3;
        monsters[0].life = monsters[0].maxLife;
    }

    public void getImage() {
        for (Entity monster : monsters) {
            monster.getImage();
        }
    }

    public void setAction() {
        for (Entity monster : monsters) {
            monster.setAction();
        }
    }

    public void update() {
        for (Entity monster : monsters) {

            monster.update();

            if (gp.cChecker.checkPlayer(monster) && !gp.player.invincible) {
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

            if (monster.invincible) {
                monster.invincibleTimer++;
                if (monster.invincibleTimer > 60) {
                    monster.invincible = false;
                    monster.invincibleTimer = 0;
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        for (Entity monster : monsters) {
            monster.draw(g2);
        }
    }
}

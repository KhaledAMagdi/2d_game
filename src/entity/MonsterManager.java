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
        monsters[0].maxLife = 1;
        monsters[0].life = monsters[0].maxLife;
    }

    public void getImage() {
        for (Entity monster : monsters) {
                monster.getImage();
        }
    }

    public void setAction() {
        for (Entity monster : monsters) {
            if(monster != null)
                monster.setAction();
        }
    }

    public void update() {
        for (int i = 0; i < monsters.length; i++ ) {
            if(monsters[i] != null) {
                if (monsters[i].alive && !monsters[i].dying) {
                    monsters[i].update();

                    if (gp.cChecker.checkPlayer(monsters[i]) && !gp.player.invincible) {
                        gp.player.life--;
                        gp.player.invincible = true;
                    }

                    monsters[i].spriteCounter++;

                    if (monsters[i].spriteCounter > 14) {
                        if (monsters[i].spriteNum < monsters[i].numOfImages)
                            monsters[i].spriteNum++;
                        else
                            monsters[i].spriteNum = 1;

                        monsters[i].spriteCounter = 0;
                    }

                    if (monsters[i].invincible) {
                        monsters[i].invincibleTimer++;
                        if (monsters[i].invincibleTimer > 60) {
                            monsters[i].invincible = false;
                            monsters[i].invincibleTimer = 0;
                        }
                    }
                }
                if(!monsters[i].alive) {
                    monsters[i] = null;
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        for (Entity monster : monsters) {
            if(monster != null)
                monster.draw(g2);
        }
    }
}

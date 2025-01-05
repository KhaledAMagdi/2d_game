package entity;

import Main.GamePanel;

import java.awt.*;
import java.util.Random;

import Object.*;

public class MonsterManager {
    GamePanel gp;
    public Entity[] monsters;
    int monsterNum = 26;

    public MonsterManager(GamePanel gp) {
        this.gp = gp;

        monsters = new Entity[monsterNum];

        initiateMonsters();
        getImage();
    }

    private void initiateMonsters() {
//---------------Template---------------//
//        monsters[i] = new Entity(gp);
//        monsters[i].type = 2;
//        monsters[i].name = "bat_";
//        monsters[i].speed = 3;
//        monsters[i].numOfImages = 3;
//        monsters[i].worldX = 10 * gp.tileSize;
//        monsters[i].worldY = 9 * gp.tileSize;
//        monsters[i].solidArea = new Rectangle(0, 0, (int) (gp.tileSize * 0.5), (int) (gp.tileSize * 0.5));
//        monsters[i].solidAreaDefaultX = monsters[i].solidArea.x;
//        monsters[i].solidAreaDefaultY = monsters[i].solidArea.y;
//        monsters[i].maxLife = 4;
//        monsters[i].life = monsters[i].maxLife;
//        monsters[i].attack = 3;
//        monsters[i].defense = 0;
//        monsters[i].exp = 3;
//        i++;
// ---------------Custom made objects---------------//
        for (int i = 0; i < 10; i++) {
            monsters[i] = new Entity(gp);
            monsters[i].type = monsters[i].type_monster;
            monsters[i].name = "bat_";
            monsters[i].speed = 3;
            monsters[i].numOfImages = 3;
            monsters[i].worldX = (10 + i) * gp.tileSize;
            monsters[i].worldY = 9 * gp.tileSize;
            monsters[i].solidArea = new Rectangle(3*(int)(gp.tileSize * 0.25),3*(int)(gp.tileSize * 0.25),(int)(gp.tileSize * 0.5), (int)(gp.tileSize * 0.5));
            monsters[i].solidAreaDefaultX = monsters[i].solidArea.x;
            monsters[i].solidAreaDefaultY = monsters[i].solidArea.y;
            monsters[i].maxLife = 4;
            monsters[i].life = monsters[i].maxLife;
            monsters[i].attack = 2;
            monsters[i].defense = 0;
            monsters[i].exp = 3;
            monsters[i].projectile = gp.projM.projs[1];
        }
        int j = 0;
        monsters[j].direction = "down";
        j++;
        monsters[j].direction = "up";
        j++;
        monsters[j].direction = "left";
        j++;
        monsters[j].direction = "right";
        j++;
        monsters[j].direction = "down";
        j++;
        monsters[j].direction = "up";
        j++;
        monsters[j].direction = "left";
        j++;
        monsters[j].direction = "right";
        j++;
        monsters[j].direction = "down";
        j++;
        monsters[j].direction = "up";
        j++;

        for (int i = 10; i < 25; i++) {
            monsters[i] = new Entity(gp);
            monsters[i].type = monsters[i].type_monster;
            monsters[i].name = "mushrom_";
            monsters[i].speed = 3;
            monsters[i].numOfImages = 11;
            monsters[i].worldX = (10 + i) * gp.tileSize;
            monsters[i].worldY = 30 * gp.tileSize;
            monsters[i].solidArea = new Rectangle(3*(int)(gp.tileSize * 0.25),3*(int)(gp.tileSize * 0.25),(int)(gp.tileSize * 0.5), (int)(gp.tileSize * 0.5));
            monsters[i].solidAreaDefaultX = monsters[i].solidArea.x;
            monsters[i].solidAreaDefaultY = monsters[i].solidArea.y;
            monsters[i].maxLife = 10;
            monsters[i].life = monsters[i].maxLife;
            monsters[i].attack = 3;
            monsters[i].defense = 2;
            monsters[i].exp = 5;
            monsters[i].projectile = gp.projM.projs[2];
        }
        for (int i = 25; i < 26; i++) {
            monsters[i] = new Entity(gp);
            monsters[i].type = monsters[i].type_boss;
            monsters[i].name = "mushrom_";
            monsters[i].speed = 3;
            monsters[i].numOfImages = 11;
            monsters[i].worldX = (10 + i) * gp.tileSize;
            monsters[i].worldY = 30 * gp.tileSize;
            monsters[i].solidArea = new Rectangle(13*(int)(gp.tileSize * 0.25),14*(int)(gp.tileSize * 0.25),(int)(gp.tileSize * 0.5) * 3, (int)(gp.tileSize * 0.5) * 4)  ;
            monsters[i].solidAreaDefaultX = monsters[i].solidArea.x;
            monsters[i].solidAreaDefaultY = monsters[i].solidArea.y;
            monsters[i].maxLife = 100;
            monsters[i].life = monsters[i].maxLife;
            monsters[i].attack = 10;
            monsters[i].defense = 10;
            monsters[i].exp = 50;
            monsters[i].projectile = gp.projM.projs[1];
        }
    }

    public void getImage() {
        for (Entity monster : monsters) {
            if (monster != null)
                monster.getImage();
        }
    }

    public void update() {
        for (int i = 0; i < monsters.length; i++) {
            if (monsters[i] != null) {
                if (monsters[i].alive && !monsters[i].dying) {
                    monsters[i].update();

                    if (gp.cChecker.checkPlayer(monsters[i]) && !gp.player.invincible) {
                        int damage = monsters[i].attack - gp.player.defense;
                        if (damage < 0) damage = 0;
                        gp.player.life -= damage;
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
                        if (monsters[i].invincibleTimer > 30) {
                            monsters[i].invincible = false;
                            monsters[i].invincibleTimer = 0;
                        }
                    }
                }
                if (!monsters[i].alive) {
                    monsters[i].checkDrop();
                    monsters[i] = null;
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        for (Entity monster : monsters) {
            if (monster != null)
                monster.draw(g2);
        }
    }
}

package Object;

import Main.GamePanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import entity.*;

public class ObjectManager {
    GamePanel gp;
    public SuperObject[] objs;
    int objNum = 100;

    public ObjectManager(GamePanel gp) {
        this.gp = gp;

        objs = new SuperObject[objNum];

        initiateObjects();
        getObjectsImage();
    }

    private void initiateObjects() {
//---------------Template---------------//
//        objs[i] = new SuperObject(gp);
//        objs[i].image = new BufferedImage[1];
//        objs[i].name = "name";
//        objs[i].collision = true;
//        objs[i].worldX = x;
//        objs[i].worldY = y;
//        objs[i].solidArea = new Rectangle( x, y, w, h);
//        objs[i].solidAreaDefaultX = objs[i].solidArea.x;
//        objs[i].solidAreaDefaultY = objs[i].solidArea.y;
//        objs[i].msgShown = "msg";
//        objs[i].drawable = flase;
//        objs[i].attackValue = attack;
//        objs[i].defenseValue = defense;
//        objs[i].discription = "discription";
//        objs[i].type = objs[i].consumable;
//        objs[i].price = price;
//---------------Set objects for UI---------------//
        int i = 0;

;
        objs[i] = new SuperObject(gp); //0 heart
        objs[i].image = new BufferedImage[1];
        objs[i].name = "heart";
        objs[i].collision = false;
        objs[i].msgShown = "You've gotten a heart";
        objs[i].drawable = false;
        objs[i].attackValue = 0;
        objs[i].defenseValue = 0;
        objs[i].discription = "[heart]\nHeals the wounded";
        objs[i].type = objs[i].consumable;
        objs[i].price = 10;
        objs[i].solidArea = new Rectangle( 0, 0, gp.tileSize, gp.tileSize);
        objs[i].solidAreaDefaultX = objs[i].solidArea.x;
        objs[i].solidAreaDefaultY = objs[i].solidArea.y;
        i++;

        objs[i] = new SuperObject(gp); //1 half heart
        objs[i].image = new BufferedImage[1];
        objs[i].name = "half_heart";
        objs[i].collision = false;
        objs[i].msgShown = "You've gotten half a heart";
        objs[i].drawable = false;
        objs[i].attackValue = 0;
        objs[i].defenseValue = 0;
        objs[i].discription = "[heart]\nHeals the wounded";
        objs[i].type = objs[i].consumable;
        objs[i].price = 10;
        objs[i].solidArea = new Rectangle( 0, 0, gp.tileSize, gp.tileSize);
        objs[i].solidAreaDefaultX = objs[i].solidArea.x;
        objs[i].solidAreaDefaultY = objs[i].solidArea.y;
        i++;

        objs[i] = new SuperObject(gp); //2 empty heart
        objs[i].image = new BufferedImage[1];
        objs[i].name = "empty_heart";
        objs[i].collision = false;
        objs[i].msgShown = "You've gotten nothing?";
        objs[i].drawable = false;
        objs[i].attackValue = 0;
        objs[i].defenseValue = 0;
        objs[i].discription = "[Heart]\nHeals the wounded";
        objs[i].type = objs[i].consumable;
        objs[i].price = 10;
        objs[i].solidArea = new Rectangle( 0, 0, gp.tileSize, gp.tileSize);
        objs[i].solidAreaDefaultX = objs[i].solidArea.x;
        objs[i].solidAreaDefaultY = objs[i].solidArea.y;
        i++;

        objs[i] = new SuperObject(gp); //3 mana
        objs[i].image = new BufferedImage[1];
        objs[i].name = "mana";
        objs[i].collision = false;
        objs[i].msgShown = "You've gotten a mana crystal";
        objs[i].drawable = false;
        objs[i].attackValue = 0;
        objs[i].defenseValue = 0;
        objs[i].discription = "[Mana]\nRegenerates the exhausted";
        objs[i].type = objs[i].consumable;
        objs[i].price = 20;
        objs[i].solidArea = new Rectangle( 0, 0, gp.tileSize, gp.tileSize);
        objs[i].solidAreaDefaultX = objs[i].solidArea.x;
        objs[i].solidAreaDefaultY = objs[i].solidArea.y;
        i++;

        objs[i] = new SuperObject(gp); //4 empty mana
        objs[i].image = new BufferedImage[1];
        objs[i].name = "empty_mana";
        objs[i].collision = false;
        objs[i].msgShown = "You've gotten nothing ?";
        objs[i].drawable = false;
        objs[i].attackValue = 0;
        objs[i].defenseValue = 0;
        objs[i].discription = "[heart]\nRegenerates the exhausted";
        objs[i].type = objs[i].consumable;
        objs[i].price = 20;
        objs[i].solidArea = new Rectangle( 0, 0, gp.tileSize, gp.tileSize);
        objs[i].solidAreaDefaultX = objs[i].solidArea.x;
        objs[i].solidAreaDefaultY = objs[i].solidArea.y;
        i++;

        objs[i] = new SuperObject(gp); //5 key
        objs[i].image = new BufferedImage[1];
        objs[i].name = "key";
        objs[i].collision = true;
        objs[i].msgShown = "You've gotten a key!";
        objs[i].drawable = false;
        objs[i].attackValue = 0;
        objs[i].defenseValue = 0;
        objs[i].discription = "[key]\nCan open several things";
        objs[i].type = objs[i].consumable;
        objs[i].price = 50;
        objs[i].solidArea = new Rectangle( 0, 0, gp.tileSize, gp.tileSize);
        objs[i].solidAreaDefaultX = objs[i].solidArea.x;
        objs[i].solidAreaDefaultY = objs[i].solidArea.y;
        i++;

        objs[i] = new SuperObject(gp); //6 wooden sword
        objs[i].image = new BufferedImage[1];
        objs[i].name = "wooden_sword";
        objs[i].collision = false;
        objs[i].msgShown = "You've gotten a sword!";
        objs[i].drawable = false;
        objs[i].attackValue = 1;
        objs[i].defenseValue = 0;
        objs[i].discription = "[Wooden Sword]\nFragile but handy";
        objs[i].type = objs[i].sword;
        objs[i].price = 10;
        objs[i].solidArea = new Rectangle( 0, 0, gp.tileSize, gp.tileSize);
        objs[i].solidAreaDefaultX = objs[i].solidArea.x;
        objs[i].solidAreaDefaultY = objs[i].solidArea.y;
        i++;

        objs[i] = new SuperObject(gp); //7 gold sword
        objs[i].image = new BufferedImage[1];
        objs[i].name = "gold_sword";
        objs[i].collision = false;
        objs[i].msgShown = "You've gotten a sword!";
        objs[i].drawable = false;
        objs[i].attackValue = 3;
        objs[i].defenseValue = 0;
        objs[i].discription = "[Gold Sword]\nA little better";
        objs[i].type = objs[i].sword;
        objs[i].price = 40;
        objs[i].solidArea = new Rectangle( 0, 0, gp.tileSize, gp.tileSize);
        objs[i].solidAreaDefaultX = objs[i].solidArea.x;
        objs[i].solidAreaDefaultY = objs[i].solidArea.y;
        i++;

        objs[i] = new SuperObject(gp); //8 iron sword
        objs[i].image = new BufferedImage[1];
        objs[i].name = "iron_sword";
        objs[i].collision = false;
        objs[i].msgShown = "You've gotten a sword!";
        objs[i].drawable = false;
        objs[i].attackValue = 5;
        objs[i].defenseValue = 0;
        objs[i].discription = "[Iron Sword]\nSeems like a huge upgrade";
        objs[i].type = objs[i].sword;
        objs[i].price = 80;
        objs[i].solidArea = new Rectangle( 0, 0, gp.tileSize, gp.tileSize);
        objs[i].solidAreaDefaultX = objs[i].solidArea.x;
        objs[i].solidAreaDefaultY = objs[i].solidArea.y;
        i++;

        objs[i] = new SuperObject(gp); //9 diamond sword
        objs[i].image = new BufferedImage[1];
        objs[i].name = "diamond_sword";
        objs[i].collision = false;
        objs[i].msgShown = "You've gotten a sword!";
        objs[i].drawable = false;
        objs[i].attackValue = 10;
        objs[i].defenseValue = 0;
        objs[i].discription = "[Diamond Sword]\nFeels heavy, And premium";
        objs[i].type = objs[i].sword;
        objs[i].price = 100;
        objs[i].solidArea = new Rectangle( 0, 0, gp.tileSize, gp.tileSize);
        objs[i].solidAreaDefaultX = objs[i].solidArea.x;
        objs[i].solidAreaDefaultY = objs[i].solidArea.y;
        i++;

        objs[i] = new SuperObject(gp); //10 wooden shield
        objs[i].image = new BufferedImage[1];
        objs[i].name = "wooden_shield";
        objs[i].collision = false;
        objs[i].msgShown = "You've gotten a shield!";
        objs[i].drawable = false;
        objs[i].attackValue = 0;
        objs[i].defenseValue = 1;
        objs[i].discription = "[Wooden shield]\nFragile but handy";
        objs[i].type = objs[i].shield;
        objs[i].price = 10;
        objs[i].solidArea = new Rectangle( 0, 0, gp.tileSize, gp.tileSize);
        objs[i].solidAreaDefaultX = objs[i].solidArea.x;
        objs[i].solidAreaDefaultY = objs[i].solidArea.y;
        i++;

        objs[i] = new SuperObject(gp); //11 reinforced shield
        objs[i].image = new BufferedImage[1];
        objs[i].name = "reinforced_shield";
        objs[i].collision = false;
        objs[i].msgShown = "You've gotten a shield!";
        objs[i].drawable = false;
        objs[i].attackValue = 0;
        objs[i].defenseValue = 3;
        objs[i].discription = "[Reinforced Shield]\nA little upgrade";
        objs[i].type = objs[i].shield;
        objs[i].price = 40;
        objs[i].solidArea = new Rectangle( 0, 0, gp.tileSize, gp.tileSize);
        objs[i].solidAreaDefaultX = objs[i].solidArea.x;
        objs[i].solidAreaDefaultY = objs[i].solidArea.y;
        i++;

        objs[i] = new SuperObject(gp); //12 encased shield
        objs[i].image = new BufferedImage[1];
        objs[i].name = "encased_shield";
        objs[i].collision = false;
        objs[i].msgShown = "You've gotten a shield!";
        objs[i].drawable = false;
        objs[i].attackValue = 0;
        objs[i].defenseValue = 5;
        objs[i].discription = "[Encased Shield]\nSeems like a huge upgrade";
        objs[i].type = objs[i].shield;
        objs[i].price = 80;
        objs[i].solidArea = new Rectangle( 0, 0, gp.tileSize, gp.tileSize);
        objs[i].solidAreaDefaultX = objs[i].solidArea.x;
        objs[i].solidAreaDefaultY = objs[i].solidArea.y;
        i++;

        objs[i] = new SuperObject(gp); //13 iron shield
        objs[i].image = new BufferedImage[1];
        objs[i].name = "iron_shield";
        objs[i].collision = false;
        objs[i].msgShown = "You've gotten a shield!";
        objs[i].drawable = false;
        objs[i].attackValue = 0;
        objs[i].defenseValue = 10;
        objs[i].discription = "[Iron Shield]\nFeels unstoppable";
        objs[i].type = objs[i].shield;
        objs[i].price = 100;
        objs[i].solidArea = new Rectangle( 0, 0, gp.tileSize, gp.tileSize);
        objs[i].solidAreaDefaultX = objs[i].solidArea.x;
        objs[i].solidAreaDefaultY = objs[i].solidArea.y;
        i++;

        objs[i] = new SuperObject(gp); //14 blue potion
        objs[i].image = new BufferedImage[1];
        objs[i].name = "potion_blue";
        objs[i].collision = false;
        objs[i].msgShown = "You've gotten a potion!";
        objs[i].drawable = false;
        objs[i].attackValue = 0;
        objs[i].defenseValue = 1;
        objs[i].discription = "[Potion]\nBlue?\nI wonder what it does";
        objs[i].type = objs[i].consumable;
        objs[i].price = 15;
        objs[i].solidArea = new Rectangle( 0, 0, gp.tileSize, gp.tileSize);
        objs[i].solidAreaDefaultX = objs[i].solidArea.x;
        objs[i].solidAreaDefaultY = objs[i].solidArea.y;
        i++;

        objs[i] = new SuperObject(gp); //15 bluer potion
        objs[i].image = new BufferedImage[1];
        objs[i].name = "potion_darkblue";
        objs[i].collision = false;
        objs[i].msgShown = "You've gotten a potion!";
        objs[i].drawable = false;
        objs[i].attackValue = 0;
        objs[i].defenseValue = 1;
        objs[i].discription = "[Potion]\nEVEN BLUER??\nI wonder what it does";
        objs[i].type = objs[i].consumable;
        objs[i].price = 50;
        objs[i].solidArea = new Rectangle( 0, 0, gp.tileSize, gp.tileSize);
        objs[i].solidAreaDefaultX = objs[i].solidArea.x;
        objs[i].solidAreaDefaultY = objs[i].solidArea.y;
        i++;

        objs[i] = new SuperObject(gp); //16 Coin
        objs[i].image = new BufferedImage[1];
        objs[i].name = "coin";
        objs[i].collision = false;
        objs[i].msgShown = "You've gotten a coin!";
        objs[i].drawable = false;
        objs[i].attackValue = 0;
        objs[i].defenseValue = 1;
        objs[i].discription = "[Coin]\nA coin\nWhat are you reading it's a coin?";
        objs[i].type = objs[i].consumable;
        objs[i].price = 10;
        objs[i].solidArea = new Rectangle( 0, 0, gp.tileSize, gp.tileSize);
        objs[i].solidAreaDefaultX = objs[i].solidArea.x;
        objs[i].solidAreaDefaultY = objs[i].solidArea.y;
        i++;
// ---------------Custom made objects---------------//

        // Enable collision for all objects
        for (SuperObject obj : objs) {
            if (obj != null) {
                obj.collision = true;
            }
        }
    }

    private void getObjectsImage() {
        for (int j = 0; j < objNum; j++) {
            if (objs[j] != null) {
                for (int i = 0; i < objs[j].image.length; i++) {
                    String fileName = String.format("/res/objects/%s%d.png/", objs[j].name, i);
                    try {
                        objs[j].image[i] = ImageIO.read(getClass().getResourceAsStream(fileName));
                    } catch (IOException e) {
                        System.out.println("Failed to load image: " + fileName);
                    }
                }
            }
        }
    }

    public void use(SuperObject obj) {
        switch (obj.name) {
            case "potion_blue" -> {
                gp.gameState = gp.dialogueState;
                if (gp.player.mana < gp.player.maxMana)
                    gp.player.mana = gp.player.maxMana;
                gp.ui.currentDialogue = "You drank the potion ?\n It recovered your mana!";
            }
            case "potion_darkblue" -> {
                gp.gameState = gp.dialogueState;
                gp.player.maxMana++;
                gp.ui.currentDialogue = "You drank the potion ?\n It increased your mana!";
            }
            case "coin" -> {
                gp.ui.addMessage("You've found a coin!");
                gp.player.coin++;
            }
            case "heart" -> {
                gp.ui.addMessage("You've found a heart!");
                if (gp.player.life < gp.player.maxLife - 1)
                    gp.player.life += 2;
                else if (gp.player.life < gp.player.maxLife)
                    gp.player.life++;
            }
            case "mana" -> {
                gp.ui.addMessage("You've found some mana!");
                if (gp.player.mana < gp.player.maxMana)
                    gp.player.mana++;
            }
        }
    }

    public void dropItem(SuperObject item, Entity user) {
        for (SuperObject obj : objs) {
            if (obj != null) {
                if (item.equals(obj)) {
                    obj.drawable = true;
                    obj.worldX = user.worldX;
                    obj.worldY = user.worldY;
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        for (SuperObject obj : objs) {
            if (obj != null) {
                if (obj.drawable) {
                    for (BufferedImage image : obj.image) {
                        int screenX = obj.worldX - gp.player.worldX + gp.player.screenX;
                        int screenY = obj.worldY - gp.player.worldY + gp.player.screenY;

                        if (obj.worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                                && obj.worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                                && obj.worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                                && obj.worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

                            if (gp.devMode) {
                                g2.setColor(Color.magenta);
                                g2.drawRect(screenX + obj.solidArea.x, screenY + obj.solidArea.y, obj.solidArea.width, obj.solidArea.height);
                            }
                        }
                    }
                }
            }
        }
    }
}

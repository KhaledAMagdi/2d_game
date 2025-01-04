package Object;

import Main.GamePanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;   

public class ObjectManager
{
    GamePanel gp;
    public SuperObject[] objs;
    int objNum = 100;

    public ObjectManager(GamePanel gp)
    {
        this.gp = gp;

        objs = new SuperObject[objNum];

        initiateObjects();
        getObjectsImage();
    }

    private void initiateObjects() {
//---------------Template---------------//
//        objs[] = new SuperObject();
//        objs[].name = "object name";
//        objs[].image = new BufferedImage[number of images in the object];
//        objs[].worldX = x position * gp.tileSize;
//        objs[].worldY = y position * gp.tileSize;
//        objs[].solidArea = new Rectangle(0,0,gp.tileSize,gp.tileSize); <-- hitbox
//        objs[].solidAreaDefaultX = 0; <-- hitbox
//        objs[].solidAreaDefaultY = 0; <-- hitbox
//        objs[].msgShown = "message"; <-- if the object displays a message when interacted with
//        i++;
//---------------Set objects for UI---------------//
        int i = 0;

        objs[i] = new SuperObject(); //0
        objs[i].name = "heart";
        objs[i].image = new BufferedImage[1];
        objs[i].drawable = false;
        i++;

        objs[i] = new SuperObject(); //1
        objs[i].name = "half_heart";
        objs[i].image = new BufferedImage[1];
        objs[i].drawable = false;
        i++;

        objs[i] = new SuperObject(); //2
        objs[i].name = "empty_heart";
        objs[i].image = new BufferedImage[1];
        objs[i].drawable = false;
        i++;

        objs[i] = new SuperObject(); //3
        objs[i].name = "key";
        objs[i].image = new BufferedImage[1];
        objs[i].drawable = false;
        objs[i].discription = "[" + objs[i].name + "]\nA key that can be used to open \nseveral things";
        i++;

        objs[i] = new SuperObject(); //4
        objs[i].name = "wooden_sword";
        objs[i].image = new BufferedImage[1];
        objs[i].drawable = false;
        objs[i].attackValue = 1;
        objs[i].discription = "[" + objs[i].name + "]\nAn old sword but handy";
        objs[i].type = objs[i].sword;
        i++;

        objs[i] = new SuperObject(); //5
        objs[i].name = "gold_sword";
        objs[i].image = new BufferedImage[1];
        objs[i].worldX = 9 * gp.tileSize;
        objs[i].worldY = 9 * gp.tileSize;
        objs[i].solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        objs[i].msgShown = "You've gotten a sword!";
        objs[i].attackValue = 2;
        objs[i].discription = "[" + objs[i].name + "]\nA little bit of an upgrade";
        objs[i].type = objs[i].sword;
        i++;

        objs[i] = new SuperObject(); //6
        objs[i].name = "iron_sword";
        objs[i].image = new BufferedImage[1];
        objs[i].worldX = 10 * gp.tileSize;
        objs[i].worldY = 9 * gp.tileSize;
        objs[i].solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        objs[i].msgShown = "You've gotten a sword!";
        objs[i].attackValue = 3;
        objs[i].discription = "[" + objs[i].name + "]\nStronger much ?!";
        objs[i].type = objs[i].sword;
        i++;

        objs[i] = new SuperObject(); //7
        objs[i].name = "diamond_sword";
        objs[i].image = new BufferedImage[1];
        objs[i].worldX = 11 * gp.tileSize;
        objs[i].worldY = 9 * gp.tileSize;
        objs[i].solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        objs[i].msgShown = "You've gotten a sword!";
        objs[i].attackValue = 5;
        objs[i].discription = "[" + objs[i].name + "]\nFeels heavy";
        objs[i].type = objs[i].sword;
        i++;

        objs[i] = new SuperObject(); //8
        objs[i].name = "wooden_shield";
        objs[i].image = new BufferedImage[1];
        objs[i].drawable = false;
        objs[i].defenseValue = 1;
        objs[i].discription = "[" + objs[i].name + "]\nAn old shield but handy";
        objs[i].type = objs[i].shield;
        i++;

        objs[i] = new SuperObject(); //9
        objs[i].name = "reinforced_shield";
        objs[i].image = new BufferedImage[1];
        objs[i].worldX = 12 * gp.tileSize;
        objs[i].worldY = 9 * gp.tileSize;
        objs[i].solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        objs[i].msgShown = "You've gotten a shield!";
        objs[i].defenseValue = 2;
        objs[i].discription = "[" + objs[i].name + "]\nReinforced wooden shield";
        objs[i].type = objs[i].shield;
        i++;

        objs[i] = new SuperObject(); //10
        objs[i].name = "encased_shield";
        objs[i].image = new BufferedImage[1];
        objs[i].worldX = 13 * gp.tileSize;
        objs[i].worldY = 9 * gp.tileSize;
        objs[i].solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        objs[i].msgShown = "You've gotten a shield!";
        objs[i].defenseValue = 4;
        objs[i].discription = "[" + objs[i].name + "]\nEncased wooden shield";
        objs[i].type = objs[i].shield;
        i++;

        objs[i] = new SuperObject(); //11
        objs[i].name = "iron_shield";
        objs[i].image = new BufferedImage[1];
        objs[i].worldX = 14 * gp.tileSize;
        objs[i].worldY = 9 * gp.tileSize;
        objs[i].solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        objs[i].msgShown = "You've gotten a shield!";
        objs[i].defenseValue = 6;
        objs[i].discription = "[" + objs[i].name + "]\nFinally an iron shield";
        objs[i].type = objs[i].shield;
        i++;

        objs[i] = new SuperObject(); //12
        objs[i].name = "potion_blue";
        objs[i].image = new BufferedImage[1];
        objs[i].worldX = 10 * gp.tileSize;
        objs[i].worldY = 11 * gp.tileSize;
        objs[i].solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        objs[i].msgShown = "You've gotten a potion!";
        objs[i].discription = "[" + objs[i].name + "]\nA blue potion\nI wonder what it does";
        objs[i].type = objs[i].consumable;
        i++;

// ---------------Custom made objects---------------//
        objs[i] = new SuperObject();
        objs[i].name = "key";
        objs[i].image = new BufferedImage[1];
        objs[i].worldX = 16 * gp.tileSize;
        objs[i].worldY = 9 * gp.tileSize;
        objs[i].solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        objs[i].solidAreaDefaultX = 0;
        objs[i].solidAreaDefaultY = 0;
        objs[i].msgShown = "You've gotten a key!";
        objs[i].type = objs[i].consumable;
        i++;

        objs[i] = new SuperObject();
        objs[i].name = "chest";
        objs[i].image = new BufferedImage[1];
        objs[i].worldX = 22 * gp.tileSize;
        objs[i].worldY = 9 * gp.tileSize;
        objs[i].solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        objs[i].solidAreaDefaultX = 0;
        objs[i].solidAreaDefaultY = 0;
        objs[i].msgShown = "You've opened a chest!";
        i++;

        objs[i] = new SuperObject();
        objs[i].name = "chest";
        objs[i].image = new BufferedImage[1];
        objs[i].worldX = 25 * gp.tileSize;
        objs[i].worldY = 9 * gp.tileSize;
        objs[i].solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        objs[i].solidAreaDefaultX = 0;
        objs[i].solidAreaDefaultY = 0;
        objs[i].msgShown = "You've opened a chest!";
        i++;

        // Enable collision for all objects
        for (SuperObject obj : objs) {
            if (obj != null) {
                obj.collision = true;
            }
        }
    }

    private void getObjectsImage()
    {
        for(int j = 0; j < objNum; j++)
        {
            if(objs[j] != null)
            {
                for(int i = 0; i < objs[j].image.length; i++)
                {
                    String fileName = String.format("/res/objects/%s%d.png/",objs[j].name,i);
                    try
                    {
                        objs[j].image[i] = ImageIO.read(getClass().getResourceAsStream(fileName));
                    }catch (IOException e)
                    {
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
                if (gp.player.life < gp.player.maxLife)
                    gp.player.life = gp.player.maxLife;
                gp.ui.currentDialogue = "You drank the potion ?\n It healed you!";
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

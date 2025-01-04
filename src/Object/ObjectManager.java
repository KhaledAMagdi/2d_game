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
    int objNum = 9;
    
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

        objs[i] = new SuperObject();
        objs[i].name = "heart";
        objs[i].image = new BufferedImage[1];
        objs[i].drawable = false;
        i++;

        objs[i] = new SuperObject();
        objs[i].name = "half_heart";
        objs[i].image = new BufferedImage[1];
        objs[i].drawable = false;
        i++;

        objs[i] = new SuperObject();
        objs[i].name = "empty_heart";
        objs[i].image = new BufferedImage[1];
        objs[i].drawable = false;
        i++;

        objs[i] = new SuperObject();
        objs[i].name = "key";
        objs[i].image = new BufferedImage[1];
        objs[i].drawable = false;
        objs[i].discription = "[" + objs[i].name + "]\nA key that can be used to open \nseveral things";
        i++;

        objs[i] = new SuperObject();
        objs[i].name = "sword";
        objs[i].image = new BufferedImage[1];
        objs[i].drawable = false;
        objs[i].attackValue = 1;
        objs[i].discription = "[" + objs[i].name + "]\nAn old sword but handy";
        i++;

        objs[i] = new SuperObject();
        objs[i].name = "shield";
        objs[i].image = new BufferedImage[1];
        objs[i].drawable = false;
        objs[i].defenseValue = 1;
        objs[i].discription = "[" + objs[i].name + "]\nAn old shield but handy";
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

    public void draw(Graphics2D g2) {
        for (SuperObject obj : objs) {
            if (obj != null) {
                if (obj.drawable) {
                    boolean flag1 = false;
                    boolean flag2 = false;

                    for (BufferedImage image : obj.image) {
                        int screenX = obj.worldX - gp.player.worldX + gp.player.screenX;
                        int screenY = obj.worldY - gp.player.worldY + gp.player.screenY;

                        if (flag1 && !flag2) {
                            screenX += gp.tileSize;
                            flag2 = true;
                            flag1 = false;
                        } else if (flag2 && !flag1) {
                            screenY += gp.tileSize;
                            flag1 = true;
                        } else if (flag1 && flag2) {
                            screenX += gp.tileSize;
                            screenY += gp.tileSize;
                        }

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

                        if (!flag1 && !flag2) {
                            flag1 = true;
                        }
                    }
                }
            }
        }
    }
}

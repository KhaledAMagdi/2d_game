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
    int objNum = 8;
    
    public ObjectManager(GamePanel gp)
    {
        this.gp = gp;
        
        objs = new SuperObject[objNum];
        
        initiateObjects();
        getObjectsImage();
    }
    
    private void initiateObjects()
    {   
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
//---------------Set objects for UI---------------//
        objs[0] = new SuperObject();
        objs[0].name = "heart";
        objs[0].image = new BufferedImage[1];
        objs[0].drawable = false;
        
        objs[1] = new SuperObject();
        objs[1].name = "half_heart";
        objs[1].image = new BufferedImage[1];
        objs[1].drawable = false;
        
        objs[2] = new SuperObject();
        objs[2].name = "empty_heart";
        objs[2].image = new BufferedImage[1];
        objs[2].drawable = false;

        objs[3] = new SuperObject();
        objs[3].name = "key";
        objs[3].image = new BufferedImage[1];
        objs[3].drawable = false;

        objs[4] = new SuperObject();
        objs[4].name = "sword";
        objs[4].image = new BufferedImage[1];
        objs[4].drawable = false;
        objs[4].attackValue = 1;

        objs[5] = new SuperObject();
        objs[5].name = "shield";
        objs[5].image = new BufferedImage[1];
        objs[5].drawable = false;
        objs[5].defenseValue = 1;
        
//---------------Custome made objects-----------------------------//
        objs[6] = new SuperObject();
        objs[6].name = "key";
        objs[6].image = new BufferedImage[1];
        objs[6].worldX = 16 * gp.tileSize;
        objs[6].worldY = 9 * gp.tileSize;
        objs[6].solidArea = new Rectangle(0,0,gp.tileSize,gp.tileSize);
        objs[6].solidAreaDefaultX = 0;
        objs[6].solidAreaDefaultY = 0;
        objs[6].msgShown = "You've gotten a key!";
        
        objs[7] = new SuperObject();
        objs[7].name = "chest";
        objs[7].image = new BufferedImage[1];
        objs[7].worldX = 22 * gp.tileSize;
        objs[7].worldY = 9 * gp.tileSize;
        objs[7].solidArea = new Rectangle(0,0,gp.tileSize,gp.tileSize);
        objs[7].solidAreaDefaultX = 0;
        objs[7].solidAreaDefaultY = 0;
        objs[7].msgShown = "You've opened a chest!";

        for(SuperObject obj : objs)
            if(obj != null)
                obj.collision = true;
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

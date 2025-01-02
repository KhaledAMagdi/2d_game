package entity;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class NPCManager {
    GamePanel gp;
    public Entity[] npcs;
    int numOfImages = 4;
    int npcNum = 2;

    public NPCManager(GamePanel gp) {
        this.gp = gp;

        npcs = new Entity[npcNum];

        initiateNPCs();
        getNPCImage();
    }

    private void initiateNPCs() {
//---------------Template---------------//
//        npcs[0] = new Entity(gp);
//        npcs[0].name = "name";
//        npcs[0].speed = speed;
//        npcs[0].up = new BufferedImage[numOfImages];
//        npcs[0].down = new BufferedImage[numOfImages];
//        npcs[0].right = new BufferedImage[numOfImages];
//        npcs[0].left = new BufferedImage[numOfImages];
//        npcs[0].worldX = x position * gp.tileSize;
//        npcs[0].worldY = y position * gp.tileSize;
//        npcs[0].solidArea = new Rectangle(0,0,gp.tileSize,gp.tileSize); <-- hitbox
//        npcs[0].solidAreaDefaultX = npcs[0].solidArea.x; <-- hitbox
//        npcs[0].solidAreaDefaultY = npcs[0].solidArea.y; <-- hitbox
//        npcs[0].dialogue = new String[number oof dialogues];
//        npcs[0].dialogue[0] = "dialogue";
//---------------Set objects for UI---------------//        
        npcs[0] = new Entity();
        npcs[0].type = 1;
        npcs[0].name = "farmer";
        npcs[0].speed = 3;
        npcs[0].up = new BufferedImage[numOfImages];
        npcs[0].down = new BufferedImage[numOfImages];
        npcs[0].right = new BufferedImage[numOfImages];
        npcs[0].left = new BufferedImage[numOfImages];
        npcs[0].worldX = 12 * gp.tileSize;
        npcs[0].worldY = 10 * gp.tileSize;
        npcs[0].solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        npcs[0].solidAreaDefaultX = 0;
        npcs[0].solidAreaDefaultY = 0;
        npcs[0].direction = "left";
        npcs[0].dialogue = new String[1];
        npcs[0].dialogue[0] = "Hello\nWelcome to the adventure island\nHope u dont die right away";

        npcs[1] = new Entity();
        npcs[1].type = 1;
        npcs[1].name = "chillguy";
        npcs[1].speed = 3;
        npcs[1].up = new BufferedImage[numOfImages];
        npcs[1].down = new BufferedImage[numOfImages];
        npcs[1].right = new BufferedImage[numOfImages];
        npcs[1].left = new BufferedImage[numOfImages];
        npcs[1].worldX = 10 * gp.tileSize;
        npcs[1].worldY = 10 * gp.tileSize;
        npcs[1].solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        npcs[1].solidAreaDefaultX = 0;
        npcs[1].solidAreaDefaultY = 0;
        npcs[1].direction = "right";
        npcs[1].dialogue = new String[1];
        npcs[1].dialogue[0] = "just a chill guy\ndrurururu\nrurururururu";
    }

    private void getNPCImage() {
        for (Entity npc : npcs) {
            try {
                if (!npc.idleOn) {
                    for (int i = 0; i < numOfImages; i++) {
                        String file = String.format("/res/npcs/%s_up%d.png/", npc.name, i);
                        npc.up[i] = ImageIO.read(getClass().getResourceAsStream(file));
                    }
                    for (int i = 0; i < numOfImages; i++) {
                        String file = String.format("/res/npcs/%s_down%d.png/", npc.name, i);
                        npc.down[i] = ImageIO.read(getClass().getResourceAsStream(file));
                    }
                    for (int i = 0; i < numOfImages; i++) {
                        String file = String.format("/res/npcs/%s_right%d.png/", npc.name, i);
                        npc.right[i] = ImageIO.read(getClass().getResourceAsStream(file));
                    }
                    for (int i = 0; i < numOfImages; i++) {
                        String file = String.format("/res/npcs/%s_left%d.png/", npc.name, i);
                        npc.left[i] = ImageIO.read(getClass().getResourceAsStream(file));
                    }
                } else {
                    for (int i = 0; i < numOfImages; i++) {
                        String file = String.format("/res/npcs/%s%d.png/", npc.name, i);
                        npc.idle[i] = ImageIO.read(getClass().getResourceAsStream(file));
                    }
                }
            } catch (IOException e) {
                System.out.println("Failed to load image");
            }
        }
    }

    public void speak(int index) {

        if (npcs[index].dialogueIndex >= npcs[index].dialogue.length) {
            npcs[index].dialogueIndex = 0;
        }
        gp.ui.currentDialogue = npcs[index].dialogue[npcs[index].dialogueIndex];
        npcs[index].dialogueIndex++;

        if (!npcs[index].idleOn) {
            switch (gp.player.direction) {
                case "up" -> npcs[index].direction = "down";
                case "down" -> npcs[index].direction = "up";
                case "right" -> npcs[index].direction = "left";
                case "left" -> npcs[index].direction = "right";
            }
        }
    }

    public void update() {
        for (Entity npc : npcs) {

            setAction();

            if (!npc.collisionOn) {
                switch (npc.direction) {
                    case "up" -> npc.worldY -= npc.speed;
                    case "down" -> npc.worldY += npc.speed;
                    case "left" -> npc.worldX -= npc.speed;
                    case "right" -> npc.worldX += npc.speed;
                }
            }


            //Check tile collision
            npc.collisionOn = false;
            gp.cChecker.checkTile(npc);
            gp.cChecker.checkPlayer(npc);
            gp.cChecker.checkEntity(npc, npcs);

            npc.spriteCounter++;

            if (npc.spriteCounter > 14) {
                if (npc.spriteNum < numOfImages)
                    npc.spriteNum++;
                else
                    npc.spriteNum = 1;

                npc.spriteCounter = 0;
            }
        }
    }

    public void setAction() {
        for (Entity npc : npcs) {
            if (!npc.idleOn) {
                Random random = new Random();
                int i = random.nextInt(100) + 1;
                npc.actionLock++;

                if (npc.actionLock == 120) {
                    if (i <= 25) {
                        npc.direction = "up";
                    }
                    if (i > 25 && i <= 50) {
                        npc.direction = "down";
                    }
                    if (i > 50 && i <= 75) {
                        npc.direction = "left";
                    }
                    if (i > 75 && i <= 100) {
                        npc.direction = "right";
                    }
                    npc.actionLock = 0;
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        for (Entity npc : npcs) {
            BufferedImage image = null;

            switch (npc.direction) {
                case "up" -> {
                    for (int i = 0; i < numOfImages; i++) {
                        if (npc.spriteNum == i + 1)
                            image = npc.up[i];
                    }
                }
                case "down" -> {
                    for (int i = 0; i < numOfImages; i++) {
                        if (npc.spriteNum == i + 1)
                            image = npc.down[i];
                    }
                }
                case "left" -> {
                    for (int i = 0; i < numOfImages; i++) {
                        if (npc.spriteNum == i + 1)
                            image = npc.left[i];
                    }
                }
                case "right" -> {
                    for (int i = 0; i < numOfImages; i++) {
                        if (npc.spriteNum == i + 1)
                            image = npc.right[i];
                    }
                }
                case "idle" -> {
                    for (int i = 0; i < numOfImages; i++) {
                        if (npc.spriteNum == i + 1)
                            image = npc.idle[i];
                    }
                }
            }

            int screenX = npc.worldX - gp.player.worldX + gp.player.screenX;
            int screenY = npc.worldY - gp.player.worldY + gp.player.screenY;

            g2.drawImage(image, screenX, screenY, gp.tileSize, (int) (gp.tileSize * 1.5), null);

            if (gp.devMode) {
                g2.setColor(Color.magenta);
                g2.drawRect(screenX + npc.solidArea.x, screenY + npc.solidArea.y, npc.solidArea.width, npc.solidArea.height);
            }
        }
    }
}


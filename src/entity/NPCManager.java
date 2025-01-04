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
    int npcNum = 2;

    public NPCManager(GamePanel gp) {
        this.gp = gp;

        npcs = new Entity[npcNum];

        initiateNPCs();
        getNPCImage();
    }

    private void initiateNPCs() {
//---------------Template---------------//
//        npcs[i] = new Entity(gp);
//        npcs[i].name = "name";
//        npcs[i].speed = speed;
//        npcs[i].up = new BufferedImage[numOfImages];
//        npcs[i].down = new BufferedImage[numOfImages];
//        npcs[i].right = new BufferedImage[numOfImages];
//        npcs[i].left = new BufferedImage[numOfImages];
//        npcs[i].worldX = x position * gp.tileSize;
//        npcs[i].worldY = y position * gp.tileSize;
//        npcs[i].solidArea = new Rectangle(0,0,gp.tileSize,gp.tileSize); <-- hitbox
//        npcs[i].solidAreaDefaultX = npcs[0].solidArea.x; <-- hitbox
//        npcs[i].solidAreaDefaultY = npcs[0].solidArea.y; <-- hitbox
//        npcs[i].dialogue = new String[number oof dialogues];
//        npcs[i].dialogue[0] = "dialogue";
//        i++;
// ---------------Custom made objects---------------//
//        int i = 0;
//
//        npcs[i] = new Entity(gp);
//        npcs[i].type = 1;
//        npcs[i].name = "farmer_";
//        npcs[i].speed = 3;
//        npcs[i].numOfImages = 4;
//        npcs[i].up = new BufferedImage[npcs[0].numOfImages];
//        npcs[i].down = new BufferedImage[npcs[0].numOfImages];
//        npcs[i].right = new BufferedImage[npcs[0].numOfImages];
//        npcs[i].left = new BufferedImage[npcs[0].numOfImages];
//        npcs[i].worldX = 12 * gp.tileSize;
//        npcs[i].worldY = 10 * gp.tileSize;
//        npcs[i].solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
//        npcs[i].solidAreaDefaultX = 0;
//        npcs[i].solidAreaDefaultY = 0;
//        npcs[i].direction = "left";
//        npcs[i].dialogue = new String[1];
//        npcs[i].dialogue[0] = "Hello\nWelcome to the adventure island\nHope u dont die right away";
//        i++;
//
//        npcs[i] = new Entity(gp);
//        npcs[i].type = 1;
//        npcs[i].name = "chillguy_";
//        npcs[i].speed = 3;
//        npcs[i].numOfImages = 4;
//        npcs[i].up = new BufferedImage[npcs[1].numOfImages];
//        npcs[i].down = new BufferedImage[npcs[1].numOfImages];
//        npcs[i].right = new BufferedImage[npcs[1].numOfImages];
//        npcs[i].left = new BufferedImage[npcs[1].numOfImages];
//        npcs[i].worldX = 10 * gp.tileSize;
//        npcs[i].worldY = 10 * gp.tileSize;
//        npcs[i].solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
//        npcs[i].solidAreaDefaultX = 0;
//        npcs[i].solidAreaDefaultY = 0;
//        npcs[i].direction = "right";
//        npcs[i].dialogue = new String[1];
//        npcs[i].dialogue[0] = "just a chill guy\ndrurururu\nrurururururu";
//        i++;
    }

    private void getNPCImage() {
        for (Entity npc : npcs) {
            if(npc != null)
                npc.getImage();
        }
    }

    public void speak(int index) {

        if (npcs[index].dialogueIndex >= npcs[index].dialogue.length) {
            npcs[index].dialogueIndex = 0;
        }
        gp.ui.currentDialogue = npcs[index].dialogue[npcs[index].dialogueIndex];
        npcs[index].dialogueIndex++;

            switch (gp.player.direction) {
                case "up" -> npcs[index].direction = "down";
                case "down" -> npcs[index].direction = "up";
                case "right" -> npcs[index].direction = "left";
                case "left" -> npcs[index].direction = "right";
            }
    }

    public void update() {
        for (Entity npc : npcs) {
            if (npc != null) {
                npc.update();

                npc.spriteCounter++;

                if (npc.spriteCounter > 14) {
                    if (npc.spriteNum < npc.numOfImages)
                        npc.spriteNum++;
                    else
                        npc.spriteNum = 1;

                    npc.spriteCounter = 0;
                }
            }
        }
    }

    public void setAction() {
        for (Entity npc : npcs) {
            npc.setAction();
        }
    }

    public void draw(Graphics2D g2)
    {
        for(Entity npc : npcs)
        {
            if(npc != null)
                npc.draw(g2);
        }
    }
}


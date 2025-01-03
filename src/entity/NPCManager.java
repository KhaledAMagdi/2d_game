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
//        npcs[0] = new Entity(gp);
//        npcs[0].type = 1;
//        npcs[0].name = "farmer_";
//        npcs[0].speed = 3;
//        npcs[0].numOfImages = 4;
//        npcs[0].up = new BufferedImage[npcs[0].numOfImages];
//        npcs[0].down = new BufferedImage[npcs[0].numOfImages];
//        npcs[0].right = new BufferedImage[npcs[0].numOfImages];
//        npcs[0].left = new BufferedImage[npcs[0].numOfImages];
//        npcs[0].worldX = 12 * gp.tileSize;
//        npcs[0].worldY = 10 * gp.tileSize;
//        npcs[0].solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
//        npcs[0].solidAreaDefaultX = 0;
//        npcs[0].solidAreaDefaultY = 0;
//        npcs[0].direction = "left";
//        npcs[0].dialogue = new String[1];
//        npcs[0].dialogue[0] = "Hello\nWelcome to the adventure island\nHope u dont die right away";
//
//        npcs[1] = new Entity(gp);
//        npcs[1].type = 1;
//        npcs[1].name = "chillguy_";
//        npcs[1].speed = 3;
//        npcs[1].numOfImages = 4;
//        npcs[1].up = new BufferedImage[npcs[1].numOfImages];
//        npcs[1].down = new BufferedImage[npcs[1].numOfImages];
//        npcs[1].right = new BufferedImage[npcs[1].numOfImages];
//        npcs[1].left = new BufferedImage[npcs[1].numOfImages];
//        npcs[1].worldX = 10 * gp.tileSize;
//        npcs[1].worldY = 10 * gp.tileSize;
//        npcs[1].solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
//        npcs[1].solidAreaDefaultX = 0;
//        npcs[1].solidAreaDefaultY = 0;
//        npcs[1].direction = "right";
//        npcs[1].dialogue = new String[1];
//        npcs[1].dialogue[0] = "just a chill guy\ndrurururu\nrurururururu";
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


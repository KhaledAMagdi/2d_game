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
    int npcNum = 6;

    public NPCManager(GamePanel gp) {
        this.gp = gp;

        npcs = new Entity[npcNum];

        initiateNPCs();
        getNPCImage();
        setItems();
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
        int j = 12;
        for (int i = 0; i < 3; i++) {
            npcs[i] = new Entity(gp);
            npcs[i].type = npcs[i].type_npc;
            npcs[i].name = "chillguy_";
            npcs[i].speed = 3;
            npcs[i].numOfImages = 4;
            npcs[i].up = new BufferedImage[npcs[i].numOfImages];
            npcs[i].down = new BufferedImage[npcs[i].numOfImages];
            npcs[i].right = new BufferedImage[npcs[i].numOfImages];
            npcs[i].left = new BufferedImage[npcs[i].numOfImages];
            npcs[i].worldX = j * gp.tileSize;
            npcs[i].worldY = 17 * gp.tileSize;
            npcs[i].solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
            npcs[i].solidAreaDefaultX = 0;
            npcs[i].solidAreaDefaultY = 0;
            npcs[i].direction = "down";
            npcs[i].dialogue = new String[1];
            npcs[i].dialogue[0] = "Do you want to trade";
            npcs[i].moveable = false;
            j += 4;
        }
        npcs[0].dialogue[0] = "Looking for better swords?\nWell you've found the right place!";
        npcs[1].dialogue[0] = "Looking for better shields?\nWell you've found the right place!";
        npcs[2].dialogue[0] = "Potions are a mysterious thing.\nYou should try them out.";
        j = 12;
        for (int i = 3; i < 6; i++) {
            npcs[i] = new Entity(gp);
            npcs[i].type = npcs[i].type_npc;
            npcs[i].name = "farmer_";
            npcs[i].speed = 3;
            npcs[i].numOfImages = 4;
            npcs[i].up = new BufferedImage[npcs[i].numOfImages];
            npcs[i].down = new BufferedImage[npcs[i].numOfImages];
            npcs[i].right = new BufferedImage[npcs[i].numOfImages];
            npcs[i].left = new BufferedImage[npcs[i].numOfImages];
            npcs[i].worldX = j * gp.tileSize;
            npcs[i].worldY = 20 * gp.tileSize;
            npcs[i].solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
            npcs[i].solidAreaDefaultX = 0;
            npcs[i].solidAreaDefaultY = 0;
            npcs[i].direction = "down";
            npcs[i].dialogue = new String[1];
            npcs[i].dialogue[0] = "Welcome to the island!";
            npcs[i].moveable = true;
            j += 4;
        }
        npcs[5].dialogue[0] = "Welcome to the island!";
        npcs[4].dialogue[0] = "Take care traveler!\nThis island is a dangerous place.";
        npcs[3].dialogue[0] = "I may look like just a farmer\nBut trust me I've seen days of rain.";
    }

    public void setItems() {

        npcs[0].inventory.add(gp.objectM.objs[9]);
        npcs[0].inventory.add(gp.objectM.objs[8]);
        npcs[0].inventory.add(gp.objectM.objs[7]);
        npcs[0].inventory.add(gp.objectM.objs[6]);

        npcs[1].inventory.add(gp.objectM.objs[13]);
        npcs[1].inventory.add(gp.objectM.objs[12]);
        npcs[1].inventory.add(gp.objectM.objs[11]);
        npcs[1].inventory.add(gp.objectM.objs[10]);

        npcs[2].inventory.add(gp.objectM.objs[0]);
        npcs[2].inventory.add(gp.objectM.objs[3]);
        npcs[2].inventory.add(gp.objectM.objs[14]);
        npcs[2].inventory.add(gp.objectM.objs[15]);
    }

    private void getNPCImage() {
        for (Entity npc : npcs) {
            if (npc != null)
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
        if (!npcs[index].moveable) {
            gp.gameState = gp.tradeState;
            gp.ui.npc = npcs[index];
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

    public void draw(Graphics2D g2) {
        for (Entity npc : npcs) {
            if (npc != null)
                npc.draw(g2);
        }
    }
}


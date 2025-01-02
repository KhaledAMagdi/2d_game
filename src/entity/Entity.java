package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import Main.GamePanel;

//Abstract Class
public class Entity 
{
    public int worldX, worldY;
    public int speed;

    public String name;
    public BufferedImage[] up = new BufferedImage[12];
    public BufferedImage[] down = new BufferedImage[12];
    public BufferedImage[] right = new BufferedImage[12];
    public BufferedImage[] left = new BufferedImage[12];
    public BufferedImage[] idle;

    public boolean idleOn = false;
    public String direction = "down";

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public int actionLock = 0;
    public Rectangle solidArea;

    public boolean collisionOn = false;
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    public String[] dialogue;

    public int dialogueIndex = 0;

    public int maxLife;
    public int life;

    public boolean invincible = false;
    public int invincibleTimer = 0;

    public int type = 0; // 0->player 1->npc 2->monster
}
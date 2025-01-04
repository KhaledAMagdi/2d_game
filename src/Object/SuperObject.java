package Object;

import Main.GamePanel;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class SuperObject 
{
    GamePanel gp;

    public SuperObject(GamePanel gp)
    {
        this.gp = gp;
    }

    public BufferedImage image[];
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0,0,0,0);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    public String msgShown = "";
    public boolean drawable = true;
    public int attackValue = 0;
    public int defenseValue = 0;
    public String discription = "";
    public int type;
    public final int consumable = 0;
    public final int shield = 1;
    public final int sword = 2;
    public final int pickable = 3;
    public int price = 0;

    public SuperObject(SuperObject obj)
    {
        this.image = obj.image;
        this.name = obj.name;
        this.collision = obj.collision;
        this.worldX = obj.worldX;
        this.worldY = obj.worldY;
        this.solidArea = obj.solidArea;
        this.solidAreaDefaultX = obj.solidAreaDefaultX;
        this.solidAreaDefaultY = obj.solidAreaDefaultY;
        this.msgShown = obj.msgShown;
        this.price = obj.price;
        this.drawable = obj.drawable;
        this.attackValue = obj.attackValue;
        this.defenseValue = obj.defenseValue;
        this.discription = obj.discription;
        this.type = obj.type;
    }
}
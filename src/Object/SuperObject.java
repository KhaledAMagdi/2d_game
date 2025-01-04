package Object;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class SuperObject 
{
    public BufferedImage image[];
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0,0,0,0);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    public String msgShown;
    public boolean drawable = true;
    public int attackValue = 0;
    public int defenseValue = 0;
    public String discription = "";
}
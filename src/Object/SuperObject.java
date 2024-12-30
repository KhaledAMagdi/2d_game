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
}
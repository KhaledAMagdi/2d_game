package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import Main.GamePanel;

//Abstract Class
public class Entity 
{
    public GamePanel gp;
    public int worldX, worldY;
    public int speed;

    public String name;
    public BufferedImage[] up = new BufferedImage[12];
    public BufferedImage[] down = new BufferedImage[12];
    public BufferedImage[] right = new BufferedImage[12];
    public BufferedImage[] left = new BufferedImage[12];
    public BufferedImage[] idle;
    public BufferedImage[] slashup = new BufferedImage[5];
    public BufferedImage[] slashdown = new BufferedImage[5];
    public BufferedImage[] slashright = new BufferedImage[5];
    public BufferedImage[] slashleft = new BufferedImage[5];
    public int numOfImages;
    public int numOfSlashImages;
    public boolean attacking = false;

    public boolean idleOn = false;
    public String direction = "down";

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public int actionLock = 0;
    public Rectangle solidArea;
    public Rectangle attackarea;

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

    public Entity(GamePanel gp)
    {
        this.gp = gp;
    }

    public void draw(Graphics2D g2)
    {
        BufferedImage image = null;

        switch(direction)
        {
            case "up" ->
            {
                if(!attacking)
                {
                    for(int i = 0; i < numOfImages; i++)
                    {
                        if(spriteNum == i+1)
                            image = up[i];
                    }
                }
                else if(attacking)
                {
                    for(int i = 0; i < numOfSlashImages; i++) //Screen adjustments may be needed if slash image changes players position (video 26 min 15)
                    {
                        if(spriteNum == i+1)
                            image = slashup[i];
                    }
                }

            }
            case "down" ->
            {
                if(!attacking)
                {
                    for(int i = 0; i < numOfImages; i++)
                    {
                        if(spriteNum == i+1)
                            image = down[i];
                    }
                }
                else if(attacking)
                {
                    for(int i = 0; i < numOfSlashImages; i++)
                    {
                        if(spriteNum == i+1)
                            image = slashdown[i];
                    }
                }
            }
            case "left" ->
            {
                if(!attacking)
                {
                    for(int i = 0; i < numOfImages; i++)
                    {
                        if(spriteNum == i+1)
                            image = left[i];
                    }
                }
                else if(attacking)
                {
                    for(int i = 0; i < numOfSlashImages; i++)
                    {
                        if(spriteNum == i+1)
                            image = slashleft[i];
                    }
                }
            }
            case "right" ->
            {
                if(!attacking)
                {
                    for(int i = 0; i < numOfImages; i++)
                    {
                        if(spriteNum == i+1)
                            image = right[i];
                    }
                }
                else if(attacking)
                {
                    for(int i = 0; i < numOfSlashImages; i++)
                    {
                        if(spriteNum == i+1)
                            image = slashright[i];
                    }
                }
            }
            case "idle" -> {
                for (int i = 0; i < numOfImages; i++) {
                    if (spriteNum == i + 1)
                        image = idle[i];
                }
            }
        }

        if(invincible)
        {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        if(gp.devMode)
        {
            System.out.println("invinsibletimer: "+invincibleTimer);

            g2.setColor(Color.magenta);
            g2.drawRect(screenX + solidArea.x,screenY + solidArea.y, solidArea.width, solidArea.height);

            String text = String.format(" -/= Speed = "+speed);
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,20));

            int x = 0;
            int y = (gp.tileSize * 5);
            g2.drawString(text,x,y);

            text = " K/L keys";
            y += gp.tileSize/4;
            g2.drawString(text,x,y);
        }

    }
}
/*



                    */
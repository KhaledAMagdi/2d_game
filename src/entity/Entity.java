package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import Main.GamePanel;

import javax.imageio.ImageIO;

//Abstract Class
public class Entity {
    public GamePanel gp;
    public int worldX, worldY;
    public int speed;

    public String name = "";
    public BufferedImage[] up = new BufferedImage[12];
    public BufferedImage[] down = new BufferedImage[12];
    public BufferedImage[] right = new BufferedImage[12];
    public BufferedImage[] left = new BufferedImage[12];
    public BufferedImage[] slashup = new BufferedImage[5];
    public BufferedImage[] slashdown = new BufferedImage[5];
    public BufferedImage[] slashright = new BufferedImage[5];
    public BufferedImage[] slashleft = new BufferedImage[5];
    public int numOfImages;
    public int numOfSlashImages = 0;
    public boolean attacking = false;

    public String direction = "down";

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public int actionLock = 0;
    public Rectangle solidArea;
    public Rectangle attackArea;

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
    String typeString = "";

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up" -> {
                if (!attacking) {
                    for (int i = 0; i < numOfImages; i++) {
                        if (spriteNum == i + 1)
                            image = up[i];
                    }
                } else if (attacking) {
                    for (int i = 0; i < numOfSlashImages; i++) //Screen adjustments may be needed if slash image changes players position (video 26 min 15)
                    {
                        if (spriteNum == i + 1)
                            image = slashup[i];
                    }
                }
            }
            case "down" -> {
                if (!attacking) {
                    for (int i = 0; i < numOfImages; i++) {
                        if (spriteNum == i + 1)
                            image = down[i];
                    }
                } else if (attacking) {
                    for (int i = 0; i < numOfSlashImages; i++) {
                        if (spriteNum == i + 1)
                            image = slashdown[i];
                    }
                }
            }
            case "left" -> {
                if (!attacking) {
                    for (int i = 0; i < numOfImages; i++) {
                        if (spriteNum == i + 1)
                            image = left[i];
                    }
                } else if (attacking) {
                    for (int i = 0; i < numOfSlashImages; i++) {
                        if (spriteNum == i + 1)
                            image = slashleft[i];
                    }
                }
            }
            case "right" -> {
                if (!attacking) {
                    for (int i = 0; i < numOfImages; i++) {
                        if (spriteNum == i + 1)
                            image = right[i];
                    }
                } else if (attacking) {
                    for (int i = 0; i < numOfSlashImages; i++) {
                        if (spriteNum == i + 1)
                            image = slashright[i];
                    }
                }
            }
        }

        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        g2.drawImage(image, screenX, screenY, (int)(gp.tileSize),(int)(gp.tileSize), null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        if (gp.devMode) {
            System.out.println("invinsibletimer: " + invincibleTimer);

            g2.setColor(Color.magenta);
            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);

            String text = String.format(" -/= Speed = " + speed);
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20));

            int x = 0;
            int y = (gp.tileSize * 5);
            g2.drawString(text, x, y);

            text = " K/L keys";
            y += gp.tileSize / 4;
            g2.drawString(text, x, y);
        }

    }

    public void getImage() {
        if (type == 0)
            typeString = "player";
        if (type == 1)
            typeString = "npcs";
        if (type == 2)
            typeString = "monsters";

        try {
            for (int i = 0; i < numOfImages; i++) {
                String file = String.format("/res/%s/%sup%d.png/", typeString, name, i);
                up[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
            for (int i = 0; i < numOfImages; i++) {
                String file = String.format("/res/%s/%sdown%d.png/", typeString, name, i);
                down[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
            for (int i = 0; i < numOfImages; i++) {
                String file = String.format("/res/%s/%sright%d.png/", typeString, name, i);
                right[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
            for (int i = 0; i < numOfImages; i++) {
                String file = String.format("/res/%s/%sleft%d.png/", typeString, name, i);
                left[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
            for (int i = 0; i < numOfSlashImages; i++) {
                String file = String.format("/res/%s/slashdown%d.png/", typeString, i);
                slashdown[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
            for (int i = 0; i < numOfSlashImages; i++) {
                String file = String.format("/res/%s/slashup%d.png/", typeString, i);
                slashup[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
            for (int i = 0; i < numOfSlashImages; i++) {
                String file = String.format("/res/%s/slashleft%d.png/", typeString, i);
                slashleft[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
            for (int i = 0; i < numOfSlashImages; i++) {
                String file = String.format("/res/%s/slashright%d.png/", typeString, i);
                slashright[i] = ImageIO.read(getClass().getResourceAsStream(file));
            }
        } catch (IOException e) {
            System.out.println("Failed to load image");
        }
    }

    public void setAction() {
        Random random = new Random();
        int i = random.nextInt(100) + 1;
        actionLock++;

        if (actionLock == 120) {
            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLock = 0;
        }
    }

    public void update() {

        setAction();

        if (!collisionOn) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkPlayer(this);
        gp.cChecker.checkEntity(this, gp.npcM.npcs);
        gp.cChecker.checkEntity(this, gp.monsterM.monsters);
    }
}
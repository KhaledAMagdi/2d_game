package Object;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ProjectileManager {
    GamePanel gp;
    public Projectiles[] projs;
    int projNum = 100;

    public ProjectileManager(GamePanel gp) {
        this.gp = gp;

        projs = new Projectiles[projNum];

        initiateObjects();
        getObjectsImage();
    }

    private void initiateObjects() {
        int i = 0;

        projs[i] = new Projectiles(gp); //0
        projs[i].name = "fireball";
        projs[i].speed = 5;
        projs[i].maxLife = 80;
        projs[i].life = projs[i].maxLife;
        projs[i].attackValue = 3;
        projs[i].cost = 1;
        projs[i].solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        projs[i].solidAreaDefaultX = 0;
        projs[i].solidAreaDefaultY = 0;
        projs[i].image = new BufferedImage[2];
        i++;

        projs[i] = new Projectiles(gp); //1
        projs[i].name = "egg";
        projs[i].speed = 8;
        projs[i].maxLife = 80;
        projs[i].life = projs[i].maxLife;
        projs[i].attackValue = 1;
        projs[i].cost = 1;
        projs[i].solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        projs[i].solidAreaDefaultX = 0;
        projs[i].solidAreaDefaultY = 0;
        projs[i].image = new BufferedImage[2];
        i++;

        projs[i] = new Projectiles(gp); //2
        projs[i].name = "rock";
        projs[i].speed = 2;
        projs[i].maxLife = 80;
        projs[i].life = projs[i].maxLife;
        projs[i].attackValue = 3;
        projs[i].cost = 2;
        projs[i].solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        projs[i].solidAreaDefaultX = 0;
        projs[i].solidAreaDefaultY = 0;
        projs[i].image = new BufferedImage[2];
        i++;
    }

    private void getObjectsImage() {
        for (Projectiles proj : projs) {
            if (proj != null) {
                try {
                    for (int i = 0; i < proj.image.length; i++) {
                        String file = String.format("/res/projectiles/%s%d.png/", proj.name, i);
                        proj.image[i] = ImageIO.read(getClass().getResourceAsStream(file));
                    }
                } catch (IOException e) {
                    System.out.println("Failed to load image");
                }
            }
        }
    }

    public void update() {
        for (Projectiles proj : projs) {
            if (proj != null)
                if (proj.alive)
                    proj.update();
        }
    }

    public void draw(Graphics2D g2) {
        for (Projectiles proj : projs) {
            if (proj != null) {
                if (proj.alive) {
                    BufferedImage image = null;

                    switch (proj.direction) {
                        case "up" -> {
                            for (int i = 0; i < proj.image.length; i++) //Screen adjustments may be needed if slash image changes players position (video 26 min 15)
                            {
                                if (proj.spriteNum == i + 1)
                                    image = proj.image[i];
                            }
                        }
                        case "down" -> {
                            for (int i = 0; i < proj.image.length; i++) {
                                if (proj.spriteNum == i + 1)
                                    image = proj.image[i];
                            }
                        }
                        case "left" -> {
                            for (int i = 0; i < proj.image.length; i++) {
                                if (proj.spriteNum == i + 1)
                                    image = proj.image[i];
                            }
                        }
                        case "right" -> {
                            for (int i = 0; i < proj.image.length; i++) {
                                if (proj.spriteNum == i + 1)
                                    image = proj.image[i];
                            }
                        }
                    }

                    int screenX = proj.worldX - gp.player.worldX + gp.player.screenX;
                    int screenY = proj.worldY - gp.player.worldY + gp.player.screenY;

                    g2.drawImage(image, screenX, screenY, (int) (gp.tileSize / 2), (int) (gp.tileSize / 2), null);

                    if (gp.devMode) {
                        g2.setColor(Color.magenta);
                        g2.drawRect(screenX + proj.solidArea.x, screenY + proj.solidArea.y, proj.solidArea.width, proj.solidArea.height);

                    }
                }
            }
        }
    }
}

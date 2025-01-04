package Object;

import Main.GamePanel;
import entity.Entity;

public class Projectiles extends SuperObject {

    public int speed;
    public int maxLife;
    public int life;
    public int cost;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public boolean alive = false;
    public boolean collisionOn = false;

    Entity user;
    public Projectiles(GamePanel gp)
    {
        super(gp);
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user)
    {
        this.worldX = worldX;
        this.worldY = worldY;
        this.user = user;
        this.direction = direction;
        this.alive = alive;
        life = maxLife;
    }

    public void update() {

        if(user == gp.player){
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monsterM.monsters);
            if(monsterIndex != 999)
            {
                gp.player.damageMonster(monsterIndex, attackValue);
                alive = false;
            }
        }else
        {
            boolean contact = gp.cChecker.checkPlayer(this);
            if(contact)
            {
                gp.player.contactMonster(1);
                alive = false;
            }
        }

        switch (direction) {
            case "up" -> worldY -= speed;
            case "down" -> worldY += speed;
            case "left" -> worldX -= speed;
            case "right" -> worldX += speed;
        }

        life--;
        if(life <= 0)
        {
            alive = false;
        }

        spriteCounter++;
        if (spriteCounter > 11) {
            if (spriteNum < image.length)
                spriteNum++;
            else
                spriteNum = 1;

        }
        spriteCounter = 0;
    }
}

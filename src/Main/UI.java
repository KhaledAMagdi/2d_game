package Main;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class UI {
    //-------variables needed-------//
    GamePanel gp; //game panel
    Graphics2D g2; //graphics used to draw
    Font arial; //font
    Font Comic; //font
    Font Impact; //font
    ArrayList<String> message = new ArrayList<>(); //object message
    ArrayList<Integer> messageCounter = new ArrayList<>(); //message coounter
    public String currentDialogue = ""; //current dialogue to be drawn
    public int commandNum = 0; //game select
    BufferedImage heart, halfHeart, emptyHeart, keyImage, mana, emptyMana; //images to be used in UI
    public int slotCol = 0;
    public int slotRow = 0;

    //-------constructor-------//
    public UI(GamePanel gp) {
        this.gp = gp; //refer to game panel
        arial = new Font("Arial", Font.PLAIN, 30); //font
        Comic = new Font("Comic Sans MS", Font.PLAIN, 30); //font
        Impact = new Font("Palatino Linotype", Font.PLAIN, 30); //font

        heart = gp.objectM.objs[0].image[0]; //load image from object
        halfHeart = gp.objectM.objs[1].image[0]; //load image from object
        emptyHeart = gp.objectM.objs[2].image[0]; //load image from object
        keyImage = gp.objectM.objs[3].image[0]; //load image from object
        mana = gp.objectM.objs[15].image[0];
        emptyMana = gp.objectM.objs[14].image[0];
    }

    //-------message to be displayed-------//
    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    //-------main draw method-------//
    public void draw(Graphics2D g2) {
        this.g2 = g2; //graphics used
        g2.setFont(arial); //font to be used
        g2.setColor(Color.white); //colour of said thing to be drawn

        if (gp.gameState == gp.titleState)//check game state
        {
            drawTitleScreen(); //title screen draw method
        }
        if (gp.gameState == gp.playState) {
            //checks collision with npc or object to display player prompt
            if (gp.player.interactNPC(gp.cChecker.checkEntity(gp.player, gp.npcM.npcs)) || gp.player.pickUpObject(gp.cChecker.checkObject(gp.player, true)))
                drawPlayerPrompt(); //player prompt draw method

            //drawKeys(); //keys in inventory draw method
            drawPlayerHUD(); //health bar draw method
            drawMessage();
        }
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen(); //pause menu draw method
            //drawKeys(); //keys in inventory draw method
            drawPlayerHUD(); //health bar draw method
        }
        if (gp.gameState == gp.dialogueState) {
            drawPlayerHUD(); //health bar draw method
            drawDialogueScreen(); //dialogue draw method
        }
        if (gp.gameState == gp.characterState) {
            drawCharacterScreen();
            drawInventory();
        }
    }

    public void drawMessage()
    {
        int messageX = gp.tileSize/2;
        int messageY= gp.tileSize * 4;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,16F));
        for(int i = 0; i < message.size(); i++) {
            if (messageCounter.get(i) != null) {
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX+2, messageY+2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;

                if(messageCounter.get(i) > 180)
                {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    //-------player health bar-------//
    public void drawPlayerHUD() {
        int x = gp.tileSize / 5; //x position
        int y = gp.tileSize / 5; //y position
        int i = 0; //control variable
        int xAfter;

        while (i < gp.player.maxLife / 2) //every 2 maxlife = 1 heart, while loop to draw all empty hearts
        {
            g2.drawImage(emptyHeart, x, y, gp.tileSize, gp.tileSize, null); //draw empty heart
            i++; //increment control

            x += gp.tileSize + 10; //increment x position to draw hearts next to each other
        }

        xAfter = x;
        i = 0;

        while (i < gp.player.maxMana) //every 2 maxlife = 1 heart, while loop to draw all empty hearts
        {
            g2.drawImage(emptyMana, x, y, gp.tileSize, gp.tileSize, null); //draw empty heart
            i++; //increment control

            x += gp.tileSize/2 + 10; //increment x position to draw hearts next to each other
        }

        x = gp.tileSize / 5; //rest x position
        y = gp.tileSize / 5; //rest y position
        i = 0; //rest control variable

        while (i < gp.player.life) //draw actual heart
        {
            g2.drawImage(halfHeart, x, y, gp.tileSize, gp.tileSize, null); //draw half heart as 1 maxlife = half heart
            i++;

            if (i < gp.player.life) //if life is still larger means player has a full heart
            {
                g2.drawImage(heart, x, y, gp.tileSize, gp.tileSize, null); //draw full heart
            }
            i++;//increment control
            x += gp.tileSize + 10;//increment x position to draw heart next to each other
        }

        i = 0;

        while (i < gp.player.mana) //draw actual heart
        {
            g2.drawImage(mana, xAfter, y, gp.tileSize, gp.tileSize, null); //draw full heart

            i++;//increment control
            xAfter += gp.tileSize/2 + 10;//increment x position to draw heart next to each other
        }
    }

    //-------title screen-------//
    public void drawTitleScreen() {

        g2.drawImage(gp.tileM.tile[116].image, 0, 0, gp.screenWidth, gp.screenHeight, null);//backgropund image

        g2.setFont(Comic);//font used
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 100f));//font settings
        String text = "Mini Swords Man";//text to be drawn
        int x = getXforMidtext(text);//x position
        int y = gp.tileSize * 3;//y position
        g2.setColor(Color.BLACK);//colour of shadow
        g2.drawString(text, x + 4, y + 3);//draw text shadow
        g2.setColor(Color.WHITE);//colour of text
        g2.drawString(text, x, y);//draw actual text

        x += (gp.tileSize * 4);//x positon
        y += (gp.tileSize * 2);//y position
        g2.drawImage(gp.player.down[0], x, y, gp.tileSize * 2, gp.tileSize * 2, null);//draw player in the middle as manzar ya3ny

        g2.setFont(Impact);//font used
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48));//font settings
        text = "New Game";//text to be drawn
        x = getXforMidtext(text);//x position
        y += (gp.tileSize * 4);//y position
        g2.drawString(text, x, y); //draws selection

        if (commandNum == 0)//checks which selection the user is on
        {
            g2.drawString(">", x - gp.tileSize / 2, y);//draw select thingy example : "> new game"
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48));//font settings
        text = "Load Game";//text to be drawn
        x = getXforMidtext(text);//x position
        y += (gp.tileSize);//y position
        g2.drawString(text, x, y);//draw selection

        if (commandNum == 1)//checks which selection the user is on
        {
            g2.drawString(">", x - gp.tileSize / 2, y);//draw select thingy
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48));//font settings
        text = "Quit";//text to be drawn
        x = getXforMidtext(text);//x position
        y += (gp.tileSize);//y position
        g2.drawString(text, x, y);//draw selection

        if (commandNum == 2)//checks which selection the user is on
        {
            g2.drawString(">", x - gp.tileSize / 2, y);//draw select thingy
        }
    }

    //-------pause screen-------//
    public void drawPauseScreen() {
        String text = "PAUSED"; //text to be drawn
        int x = getXforMidtext(text);//x position
        int y = gp.screenHeight / 2;//y position
        g2.drawString(text, x, y);//draw text
    }

    //-------dialogue screen-------//
    public void drawDialogueScreen() {
        final int frameX = gp.tileSize * 2;
        final int frameY = gp.tileSize / 2;
        final int frameW = gp.screenWidth - (gp.tileSize * 4);
        final int frameH = gp.tileSize * 2;

        RoundRectangle2D window = new RoundRectangle2D.Double(); //dialogue window
        window.setRoundRect(frameX, frameY, frameW, frameH, 35, 35); //dialoge window specifications

        drawSubWindow(window); //draw window

        int x = (int) window.getX() + gp.tileSize / 2; //x position
        int y = (int) window.getY() + gp.tileSize / 2; //y position

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F)); //font settings
        for (String line : currentDialogue.split("\n")) //checks \n in text to be drawn as new line
        {
            g2.drawString(line, x, y);//draw string
            y += 30; //increment y when \n to appear new line
        }
    }

    //-------character screen-------//
    public void drawCharacterScreen() {
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameW = gp.tileSize * 5;
        final int frameH = gp.tileSize * 10;

        RoundRectangle2D window = new RoundRectangle2D.Double();
        window.setRoundRect(frameX, frameY, frameW, frameH, 35, 35);

        drawSubWindow(window);

        g2.setFont(Comic);
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32f));

        final int textStartX = frameX + gp.tileSize / 2;
        final int valueTailX = (frameX + frameW) - 30;
        final int startTextY = frameY + gp.tileSize;
        final int lineHeight = 60;

        String[][] attributes = {
                {"Level", String.valueOf(gp.player.level)},
                {"Life", gp.player.life + "/" + gp.player.maxLife},
                {"Strength", String.valueOf(gp.player.strength)},
                {"Dexterity", String.valueOf(gp.player.dexterity)},
                {"Attack", String.valueOf(gp.player.attack)},
                {"Defense", String.valueOf(gp.player.defense)},
                {"EXP", String.valueOf(gp.player.exp)},
                {"Next level", String.valueOf(gp.player.nextLevelExp)},
                {"Coin", String.valueOf(gp.player.coin)}
        };

        // Draw each attribute using the helper method
        int textY = startTextY;

        for (String[] attribute : attributes) {
            g2.drawString(attribute[0], textStartX, textY);
            int valueX = getXforAlignToRightText(attribute[1], valueTailX);
            g2.drawString(attribute[1], valueX, textY);
            textY += lineHeight;
        }

        // Draw weapon and shield images
        g2.drawString("Weapon", textStartX, textY);
        g2.drawImage(gp.player.currentWeapon.image[0], valueTailX - gp.tileSize / 2, textY - lineHeight + 10, gp.tileSize, gp.tileSize, null);
        textY += lineHeight;

        g2.drawString("Shield", textStartX, textY);
        g2.drawImage(gp.player.currentShield.image[0], valueTailX - gp.tileSize /2, textY - lineHeight + 15, gp.tileSize/2+20, gp.tileSize/2+20, null);
    }

    //-------inventory screen-------//
    public void drawInventory()
    {
        //window
        final int frameX = gp.tileSize * 9;
        final int frameY = gp.tileSize;
        final int frameW = (int)(gp.tileSize * 6.5);
        final int frameH = (int)(gp.tileSize * 5.5);

        RoundRectangle2D window = new RoundRectangle2D.Double(); //dialogue window
        window.setRoundRect(frameX, frameY, frameW, frameH, 35, 35);
        drawSubWindow(window);

        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        final int slotW = gp.tileSize;
        final int slotH = gp.tileSize;

        //draw player items
        for(int i = 0; i < gp.player.inventory.size(); i++)
        {
            if(gp.player.inventory.get(i) != null)
            {
                if(i == 6 || i == 12 || i == 18 || i == 24 || i == 30)
                {
                    slotY += slotH;
                    slotX = slotXstart;
                }

                if(gp.player.inventory.get(i) == gp.player.currentWeapon || gp.player.inventory.get(i) == gp.player.currentShield)
                {
                    g2.setColor(new Color(255, 201, 99, 214));
                    g2.fillRoundRect(slotX + 5, slotY + 5, gp.tileSize - 10, gp.tileSize - 10, 20, 20);
                }

                g2.drawImage(gp.player.inventory.get(i).image[0], slotX, slotY,slotW,slotH, null);
                slotX += slotW;
            }
        }

        //cursoor
        int cursorX = slotXstart + (gp.tileSize * slotCol);
        int cursorY = slotYstart + (gp.tileSize * slotRow);
        int cursorW = gp.tileSize;
        int cursorH = gp.tileSize;

        //draw cursor
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorW, cursorH, 10, 10);

        //description
        int dframx = frameX;
        int dframy = frameY + frameH + 10;
        int dframew = frameW;
        int dframeh = frameH/2;

        RoundRectangle2D windowF = new RoundRectangle2D.Double(); //dialogue window
        windowF.setRoundRect(dframx, dframy, dframew, dframeh, 35, 35);


        //draw description text
        int textX = dframx + 20;
        int textY = dframy + gp.tileSize/2;

        g2.setFont(g2.getFont().deriveFont(28f));

        int itemIndex = getItemIndex();

        if(itemIndex < gp.player.inventory.size()) {

            drawSubWindow(windowF);

            for(String line : gp.player.inventory.get(itemIndex).discription.split("\n")) {
                g2.drawString(line, textX, textY);
                textY += 32;
            }
        }
    }

    //-------player prompt screen-------//
    public void drawPlayerPrompt() {
        int x = gp.player.screenX * 2 - (gp.tileSize * 3); //x position
        int y = gp.player.screenY * 2 + (gp.tileSize / 2); //y position

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F)); //font settings
        g2.drawString("Press Enter to Interact", x, y); //draw text

    }

    //-------dialogue box-------//
    public void drawSubWindow(RoundRectangle2D window) {
        Color c = new Color(0, 0, 0, 200); //colour of box
        g2.setColor(c);//colour to be drawn
        g2.fill(window);//fill window
        g2.draw(window);//draw window

        c = new Color(255, 255, 255);//colour of the stroke
        g2.setColor(c);//colour to be drawn
        g2.setStroke(new BasicStroke(5));//stroke size
        g2.draw(window);//draw stroke
    }

    //-------middle of screen-------//
    public int getXforMidtext(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();//length of text
        return gp.screenWidth / 2 - length / 2;//middle of screen with respect to text length
    }

    //-------alignRight of screen-------//
    public int getXforAlignToRightText(String text, int tailX)
    {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();//length of text
        return tailX - length;
    }

    //-------item index-------//
    public int getItemIndex()
    {
        return slotCol + (slotRow * 6);
    }


}

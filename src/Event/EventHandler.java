package Event;

import Main.GamePanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class EventHandler 
{
    //-------varaibles needed-------//
    GamePanel gp; //gamepanel
    Rectangle eventRect; //hitbox
    int eventRectDefaultX,eventRectDefaultY; //default x y values
    EventTile[] events; //events
    int eventNum = 3; //number of events
    
    //-------constructor-------//
    public EventHandler(GamePanel gp)
    {
        this.gp = gp; //gamepanel
        eventRect = new Rectangle(); //hotbox
        eventRect.x = 23; //hitbox x position with refernce to tile
        eventRect.y = 23; //hitbox x position with refernce to tile
        eventRect.width = 2; //hitbox width
        eventRect.height = 2; //hitbox y
        eventRectDefaultX = eventRect.x; //default x position
        eventRectDefaultY = eventRect.y; //default y position
        events = new EventTile[eventNum]; //number of events
        
        initiateEvents(); //initiate events
    }
    
    //-------set event tiles-------//
    private void initiateEvents()
    {
//---------------Template-----------------------------//
//        events[] = new EventTile();
//        events[].x = x position; <- int
//        events[].y = y position; <- int
//        events[].direction = "direction"; <- string
//        events[].gameState =game state; <- gp.___State, example -> (diaogueState, puaseState, etc..)
//        events[].eventToDo = "event itself"; <- events, example -> (damage, heal, tp, etc..)
//        events[].dialogue = "message";  <- string 
//        events[].oneTime = onetime event; <- boolean
        
//---------------custome made events-----------------------------//       
        events[0] = new EventTile();
        events[0].x = 31;
        events[0].y = 16;
        events[0].direction = "any";
        events[0].gameState =gp.dialogueState;
        events[0].eventToDo = "damage";
        events[0].dialogue = "you've fallen";
        
        events[1] = new EventTile();
        events[1].x = 31;
        events[1].y = 18;
        events[1].direction = "any";
        events[1].gameState =gp.dialogueState;
        events[1].eventToDo = "heal";
        events[1].dialogue = "you've healed";
        
        events[2] = new EventTile();
        events[2].x = 29;
        events[2].y = 17;
        events[2].direction = "any";
        events[2].gameState =gp.dialogueState;
        events[2].eventToDo = "tp";
        events[2].dialogue = "you've teleported";
        events[2].oneTime = true;
    }
    
    //-------check event collision-------//
    public void checkEvent()
    {
        for(EventTile event : events) //loop all events
        {
            if(hit(event.x, event.y, event.direction))//if collisde with eventtile 
            {
                if(event.oneTime)
                {
                    if(event.doneTimes < 1)
                    {
                        event(event);//actual event
                    }
                }
                else
                {
                    event(event);  
                }
                
            } 
        }
    }
    
    //-------check collision with event block-------//
    public boolean hit(int eventCol, int eventRow, String reqDirection)
    {
        boolean hit = false;
        
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x ; //player position x
        gp.player.solidArea.y = gp.player.worldY +gp.player.solidArea.y; //player poosition y
        eventRect.x = eventCol*gp.tileSize + eventRect.x; //event tile position x
        eventRect.y = eventRow*gp.tileSize+ eventRect.y; //evnt tile position y
        
        if(gp.player.solidArea.intersects(eventRect)) //player collide with event tile
        {
            if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) //collide in the correct direction
            {
                hit = true; //collide true
            }
        }
        
        gp.player.solidArea.x = gp.player.solidAreaDefaultX; //rest value
        gp.player.solidArea.y = gp.player.solidAreaDefaultY; //rest value
        eventRect.x = eventRectDefaultX; //rest value
        eventRect.y = eventRectDefaultY; //rest value
        
        return hit; //return collisioon detectioon
    }
    
    //-------event iitself -------//
    public void event(EventTile event)
    {
        switch(event.eventToDo) //check event to do
        {
            case "damage" -> //if event is damage
            {
                event.doneTimes++;
                gp.gameState = event.gameState; //change game state
                gp.ui.currentDialogue = event.dialogue; //draw dialogue
                gp.player.life--; //decrease health
            }
            case "heal" -> //if event is heal
            {
                if(gp.player.life < gp.player.maxLife) //is player life less than the max life 
                {
                    event.doneTimes++;
                    gp.gameState = event.gameState; //change game state
                    gp.ui.currentDialogue = event.dialogue; //draw dialogue
                    gp.player.life++; //increase player life
                }
            }
            case "tp" -> //if event is teleporot
            {
                event.doneTimes++;
                gp.gameState = event.gameState; //change game state
                gp.ui.currentDialogue = event.dialogue; //draw dialogue
                gp.player.worldX = 10 * gp.tileSize; //teleport player too x position
                gp.player.worldY = 9 * gp.tileSize; //teleport player top y position
            }
        } 
    }
    
    //-------dev draw -------//
    public void draw(Graphics g2)
    {
        if(gp.devMode)
        {
            for(EventTile event:events)
            {
                int screenX = (event.x*gp.tileSize) - gp.player.worldX + gp.player.screenX + eventRect.x;
                int screenY = (event.y*gp.tileSize) - gp.player.worldY + gp.player.screenY + eventRect.y;
        
                g2.setColor(Color.magenta);
                g2.drawRect(screenX, screenY, eventRect.width, eventRect.height);
                
                int x = 0;
                int y = (gp.tileSize * 5) + (2 * (gp.tileSize/4));
                    
                for(EventTile eventt:events)
                {
                    String text = String.format(" Event "+eventt.eventToDo+" done:"+eventt.doneTimes);
                    g2.setColor(Color.white);
                    g2.setFont(g2.getFont().deriveFont(Font.BOLD,20));
                    
                    g2.drawString(text,x,y); 
                    
                    y += gp.tileSize/4;
                }
            }
        }
    }
}

package Event;

public class EventTile 
{
    int x; //x position of eventtile
    int y; //y position of eventile
    String direction = "any"; //required direction for event
    int gameState; //what gamestate the event toggles
    String eventToDo; //event to do when intereacted
    String dialogue = ""; //dialogue to be displayed
    boolean oneTime = false; //if event is a one time event
    int doneTimes = 0; //how many times the event has been triggered
}

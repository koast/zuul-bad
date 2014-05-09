import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    private String description;
    private HashMap<String,Room> exit;
    private ArrayList<Item> itemList;
    private Item currentItem;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exit = new HashMap<>();
        itemList = new ArrayList<>();
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */

    public void exit(String description,Room room)
    {
        exit.put(description,room);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    public Room getExit(String direction)
    { 
        return exit.get(direction);
    }

    public String getExitString()
    {
        String exits = "";
        Set<String> directions = exit.keySet();

        for(String addDirections: directions)
        {
            exits += addDirections + " ";
        }
        exits += "\n";
        return exits;
    }

    /**
     * Return a long description of this room, of the form:
     *     You are in the 'name of room'
     *     Exits: north west southwest
     * @return A description of the room, including exits.
     */
    public String getLongDescription()
    {
        String exits = "";
        Set<String> directions = exit.keySet();
        exits += "Estas  " + getDescription() +"\n";
        exits += "Tus salidas son: \n";
        for(String addDirections: directions)
        {
            exits += addDirections + " ";
        }
        exits += "\n";

        return exits;
    }

    public void addItem(String description, double size,boolean canTakeIt)
    {
        itemList.add(new Item(description,size,canTakeIt)); 
    }

    public void getItems()
    {
        for(Item getItem: itemList)
        {
            System.out.println("Hay un/una " + getItem.getDescription());
            System.out.println("Pesa " + getItem.getSize() + " KG\n");
        }
    }

    public Item takeItem(String itemDescription)
    {
        Item getItem = null;
        for(Item searchItem: itemList)
        {
            if (itemDescription.equals(searchItem.getDescription()))
            {
                getItem = searchItem;
            }
        }
        currentItem = getItem;
        return getItem;
    }
    
    public void deleteCurrentItem()
    {
        itemList.remove(currentItem);
    }
}

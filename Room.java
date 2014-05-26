import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;

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
    private HashMap<String, Room> exits;
    private ArrayList<Item> items;
    private Door door;
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description, Door door) 
    {
        this.description = description;
        this.door = door;
        exits = new HashMap<>();
        items = new ArrayList<>();
    }

    public void setExit(String direction, Room nextRoom)
    {
        exits.put(direction, nextRoom);
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
        return exits.get(direction);
    }

    /**
     * Return a description of the room's exits.
     * For example: "Exits: north east west"
     *
     * @ return A description of the available exits.
     */
    public String getExitString()    
    {
        Set<String> namesOfDirections = exits.keySet();
        String exitsDescription = "Exit ";

        for (String direction : namesOfDirections) {
            exitsDescription += direction + " ";
        }

        return exitsDescription;
    }

    /**
     * Return a long description of this room, of the form:
     *     You are in the 'name of room'
     *     Exits: north west southwest
     * @return A description of the room, including exits.
     */
    public String getLongDescription()
    {
        String longDescription = "You are " + description + ".\n" + getExitString() + "\n";
        longDescription += "There are " + items.size() + " items: \n";
        for (Item item : items) {
            longDescription += "- " + item.getLongDescription() + "\n";
        }
        return longDescription;
    }
    
    /**
     * Add an item to the room
     * 
     * @param item An item to be added to the room
     */
    public void addItem(Item item)
    {
        items.add(item);
    }

    
    /**
     * Return the item with the given id
     * 
     * @return item the item with the given id. null otherwise
     */
    public Item getItem(String id)
    {      
        int index = 0;
        Item item = null;
        while((item == null) && (index < items.size())) { 
            Item currentItem = items.get(index);
            if (currentItem.getDescription().equals(id)) {
                item = currentItem;
            }
            index++;
        }
        return item;
    }

    
    /**
     * Remove the given item
     * 
     * @param id the id of the item to remove
     */
    public void removeItem(String id)
    {
        int index = 0;
        boolean found = false;
        while(index < items.size() && !found){
            if(items.get(index).getDescription().equals(id)){
            items.remove(index);
            found = true;
            }
            index++;
        }
    }
    
    public Door getDoor()
    {
        return door;
    }
}    

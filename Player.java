import java.util.Stack;
import java.util.ArrayList;

/**
 * A player of the game
 *
 */
public class Player
{

    private Room currentRoom;
    private Stack<Room> visitedRooms;
    private ArrayList<Item> mochila;
    private double cargaMaxima;
    private static final double CARGA_MAXIMA_POR_DEFECTO=5;

    public Player()
    {
        currentRoom = null;
        visitedRooms = new Stack<>();
        mochila = new ArrayList<Item>();
        cargaMaxima = CARGA_MAXIMA_POR_DEFECTO;
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    public void goRoom(Command command) 
    {

        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.      
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else if (!currentRoom.getDoor().getOpen() && direction.equals(currentRoom.getDoor().getPosition()))
        {
            System.out.println("Esta cerrada la puerta"); 
        }
        else
        {
            visitedRooms.push(currentRoom);          
            currentRoom = nextRoom;
            printLocationInfo();
        }
    }

    /** 
     * Print the room's long description 
     */   
    public void look()
    {
        printLocationInfo();
    }

    /**
     * The player eats
     */  
    public void eat() 
    {
        System.out.println("You have eaten now and you are not hungry any more");
    }

    public Item getItem(String id)
    {      
        int index = 0;
        Item item = null;
        while((item == null) && (index < mochila.size())) { 
            Item currentItem = mochila.get(index);
            if (currentItem.getDescription().equals(id)) {
                item = currentItem;
            }
            index++;
        }
        return item;
    }

    /**
     * Return to the previous room
     */
    public void back()
    {
        if (!visitedRooms.empty()) {
            currentRoom = visitedRooms.pop();
            printLocationInfo();
        }
        else {
            System.out.println("You are at the beggining of the game");
            System.out.println();
        }
    }

    public void printLocationInfo()
    {
        System.out.println(currentRoom.getLongDescription());      
    }

    /**
     * Modifica la habitacion en la que esta el jugador
     * 
     * @param nuevaRoom la nueva habitacion
     */
    public void setCurrentRoom(Room nuevaRoom)
    {
        currentRoom = nuevaRoom;
    }

    /**
     * Imprime por pantalla los items que tiene ese jugador
     * 
     */
    public void items()
    {
        System.out.println("En la mochila hay " + mochila.size() + " objetos:");
        for(Item item : mochila)
        {
            System.out.println(item.getLongDescription());
        }

    }

    /**
     * Calculate the total weight for player's items.  
     * 
     * @return the total weight for the player's items
     */
    public double getTotalWeightItems()
    {
        double peso = 0D;
        for(Item item : mochila){
            peso += item.getWeight();
        }    
        return peso;
    }

    /**
     * Take de item contained in the given command
     */ 
    public void take(Command command){
        if (!command.hasSecondWord()){
            System.out.println("take what?");
            return;
        }

        String id = command.getSecondWord();
        Item item = currentRoom.getItem(id);
        if(item != null)
        {
            if(item.canBeTaken()){
                if(item.getWeight() +  getTotalWeightItems() <= cargaMaxima) {
                    System.out.println("You add a new item to your bag");
                    mochila.add(item);
                    currentRoom.removeItem(id);
                }
                else {
                    System.out.println("No hay espacio para este objeto");
                }
            }else{
                System.out.println("El Objeto no se puede coger");
            }
        }
        else
        {
            System.out.println("You don't select a item");
        }
    }

    /**
     * Drop an item of the player 
     * 
     */
    public void drop(Command command)    
    {
        if (!command.hasSecondWord()){
            System.out.println("drop what?");
            return;
        }

        String id = command.getSecondWord();
        int index = 0;
        boolean searching = true;
        while( searching && index < mochila.size()){
            Item item = mochila.get(index);
            if(item.getDescription().equals(id)){
                currentRoom.addItem(item);
                mochila.remove(index);
                searching = false;
                System.out.println("El objeto se ha dejado en la habitacion");
            }
            index++;
        }

        if (searching)
        {
            System.out.println("No estas llevando el objeto que has indicado");
        }   
    }

    public void use(Command command){
        if (!command.hasSecondWord()){
            System.out.println("use what?");
            return;
        }

        String id = command.getSecondWord();
        Item item = getItem(id);
        boolean open = false;

        if(item != null)
        {
            if(item.canBeUse()){
                if(item == currentRoom.getDoor().llave()) {
                    if(currentRoom.getDoor() != new Door(true,null,""))
                    {
                        System.out.println("Has abierto la puerta del ascensor");
                        currentRoom.getDoor().open();
                    }
                    else
                    {
                        System.out.println("No hay ninguna puerta que se pueda abrir");
                    }
                }
                else 
                
                if(item == currentRoom.getDoor().llave()) {
                    if(currentRoom.getDoor() != new Door(true,null,""))
                    {
                        System.out.println("Has usado la llave de la salida");
                        currentRoom.getDoor().open();
                    }
                    else
                    {
                        System.out.println("No hay ninguna puerta que se pueda abrir");
                    }
                }
                else{
                    System.out.println("No estas en el lugar adecuado");
                }
            }
            else{
                System.out.println("Ese item no se puede utilizar");
            }
        }
        else
        {
            System.out.println("Tu no tienes ese item");
        }
    } 
}


import java.util.ArrayList;
import java.util.Stack;

public class Player
{

    private ArrayList<Item> itemList;
    private final static double MAX_SIZE = 5;
    private double currentSize;
    private Room currentRoom;
    private Stack<Room> lastRoom;

    public Player()
    {
        itemList = new ArrayList<>();
        lastRoom = new Stack<>();
        
    }

    public void addItem(Item item)
    {
        itemList.add(item);
    }

    public void showItemInfo()
    {
        if (itemList.size() == 0)
        {
            System.out.println("No tienes nada en la mochila\n");
        }
        else
        {
            for(Item getItem: itemList)
            {
                System.out.println("\nTienes un/una " + getItem.getDescription());
                System.out.println("Pesa " + getItem.getSize());
            }
        }
    }

    public double getCurrentSize()
    {
        currentSize = MAX_SIZE;

        for(Item itemSize: itemList)
        {
            currentSize -= itemSize.getSize();
        }
        return currentSize;
    }

    public void getSizeInfo()
    {
        System.out.println("Puedes llevar: " + MAX_SIZE + " KG");
        System.out.println("Puedes llevar todavia: " + getCurrentSize() + " KG\n");
    }

    public void dropItem(Command command)
    { 
        if (itemList.size() != 0)
        {
            if(!command.hasSecondWord()) {
                System.out.println("¿Tirar el que?\n");
            }
            String itemTaked = command.getSecondWord();
            Item itemRemove = null;

            for(Item searchItem: itemList)
            {
                if (itemTaked.equals(searchItem.getDescription()))
                {
                    itemRemove = searchItem;
                }
            }
            currentSize +=itemRemove.getSize();
            itemList.remove(itemRemove);

            if (itemTaked == null)
            {
                System.out.println("Tu no tienes ese objeto");
            }
            else
            {
                System.out.println("Has tirado: " + itemTaked);
                getSizeInfo();
            }
        }
        else
        {
            System.out.println("No tienes nada");
        }
    }

    public void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("¿Ir donde?");
            return;
        }
        String direction = command.getSecondWord();

        // Try to leave current room.

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("!No hay puerta!");
        }
        else {
            lastRoom.push(currentRoom);
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    public void goLastRoom()
    {
        if (!lastRoom.empty())
        {
            currentRoom = lastRoom.pop();
            System.out.println(currentRoom.getLongDescription());
        }
        else
        {
            System.out.println("Estas al principo ya");
        }
    }

    public void takeItem(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("¿Cojer el que?\n");
            return;
        }
        String itemTaked = command.getSecondWord();
        if(currentRoom.takeItem(itemTaked) != null)
        {
            String itemDescription = currentRoom.takeItem(itemTaked).getDescription();
            double itemSize = currentRoom.takeItem(itemTaked).getSize();
            boolean itemCanTakeIt = currentRoom.takeItem(itemTaked).getCanTakeIt();
            if(itemCanTakeIt)
            {
                if(itemSize <= getCurrentSize())
                {
                    addItem(new Item(itemDescription,itemSize,itemCanTakeIt));
                    System.out.println("Obtuvistes un/una " + itemDescription +"\n");
                    getSizeInfo();
                    currentRoom.deleteCurrentItem();
                }
                else 
                {
                    System.out.println("no tienes suficiente espacio en la mochila para llevar esto.\n");
                }
            }
            else 
            {
                System.out.println("No puedes cojer eso\n");
            }
        }
        else
        {
            System.out.println("Es objeto no existe\n");
        }
    }

    public void setRoom(Room startRoom)
    {
        currentRoom = startRoom;
    }

    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    public void eat()
    {
        System.out.println("You have eaten now and you are not hungry any more");
    }
}

import java.util.Set;
import java.util.ArrayList;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private ArrayList<Room> lastRoom;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        lastRoom = new ArrayList<>();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room plantaDos,restaurante,cine,recreativos,escaleras,plantaUno,tiendaRopa,zapateria,salida,supermercado;

        // create the rooms
        plantaDos = new Room("en la planta 2");
        restaurante = new Room("en una zona con restaurantes");
        cine = new Room("en la puerta del cine");
        recreativos = new Room("en la zona recreativos");
        escaleras = new Room("en las escaleras");
        plantaUno = new Room("en el primer piso (Planta 1)");
        tiendaRopa = new Room("en una tienda de ropa");
        zapateria = new Room("en la zapateria");
        salida = new Room("en la salida principal");
        supermercado = new Room("en el supermercado");
        
        //Pisos Planta Dos
        plantaDos.exit("north",restaurante);
        plantaDos.exit("south",cine);
        plantaDos.exit("west",recreativos);
        plantaDos.exit("northEast",supermercado);
        plantaDos.exit("east",escaleras);
        plantaDos.addItem(" una papelera",5);
        
        //Salidas Cine
        cine.exit("north",plantaDos);
        cine.addItem(" una bolsa de palomitas",0.5);
        
        //Salidas recreativos
        recreativos.exit("east",plantaDos);
        recreativos.addItem(" una moneda",0.05);
        
        //Salidas supermercado
        supermercado.exit("southWest",plantaDos);
        supermercado.addItem(" un carro de la compra",20);
        
        //Salidas restaurante 
        restaurante.exit("south",plantaDos);
        restaurante.addItem(" un plato lleno de comida",1);
        
        //Salida escaleras
        escaleras.exit("downStairs",plantaUno);
        escaleras.exit("upStairs",plantaDos);
        escaleras.addItem(" una cartera",1);
        
        //Salidas Piso 1
        plantaUno.exit("north",tiendaRopa);
        plantaUno.exit("south",zapateria);
        plantaUno.exit("west",escaleras);
        plantaUno.exit("east",salida);
        plantaUno.addItem(" una planta",5);
        
        //Salidas tienda ropa
        tiendaRopa.exit("south",plantaUno);
        tiendaRopa.addItem(" una camiseta azul", 0.2);
        
        //salida salida
        salida.exit("west",plantaUno);
        salida.addItem(" una puerta",5);
        
        //salida zapateria
        zapateria.exit("north",plantaUno);
        zapateria.addItem(" un par de deportivas",0.25);
        
        currentRoom = plantaDos;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Gracias por jugar. Adios");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Bienvenido al mundo de Zuul!");
        System.out.println("el mundo de Zuul, es un increible y aburrido juego de aventuras.");
        System.out.println("Escribe 'help' si necesitas ayuda.");
        System.out.println();
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("No entiendo lo que quieres decir...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            printLocationInfo();
            currentRoom.getItems();
        }
        else if (commandWord.equals("eat")) {
            System.out.println("You have eaten now and you are not hungry any more");
        }
        else if (commandWord.equals("back")) {
            goLastRoom();
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("Estas perdido y solo");
        System.out.println("en un enorme centro comercial.");
        System.out.println();
        System.out.println("Tus comandos son:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
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
            lastRoom.add(currentRoom);
            currentRoom = nextRoom;
            printLocationInfo();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("¿Quitar que?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    private void printLocationInfo()
    {
       System.out.println(currentRoom.getLongDescription());
    }
    
    private void goLastRoom()
    {
        currentRoom = lastRoom.get(lastRoom.size() - 1);
        printLocationInfo();
    }
}












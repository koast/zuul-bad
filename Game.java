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
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
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

        // initialise room exits
        plantaDos.setExits(restaurante,escaleras,recreativos,cine,supermercado,null);
        restaurante.setExits(null,null,plantaDos,null,null,null);
        cine.setExits(null, plantaDos,null,null,null,null);
        recreativos.setExits(plantaDos,null,null,null,null,null);
        escaleras.setExits(null,plantaUno,null,plantaDos,null,null);
        plantaUno.setExits(tiendaRopa,salida,zapateria,escaleras,null,null);
        tiendaRopa.setExits(null,null,plantaUno,null,null,null);
        zapateria.setExits(plantaUno,null,null,null,null,null);
        salida.setExits(null,null,null,plantaUno,null,null);
        supermercado.setExits(null,null,null,null,null,plantaDos);

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
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
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
            System.out.println("I don't know what you mean...");
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
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   go quit help");
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }
        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;
        if(direction.equals("north")) {
            nextRoom = currentRoom.getExit("north");
        }
        if(direction.equals("east")) {
            nextRoom = currentRoom.getExit("east");
        }
        if(direction.equals("south")) {
            nextRoom = currentRoom.getExit("south");
        }
        if(direction.equals("west")) {
            nextRoom = currentRoom.getExit("south");
        } 
        if(direction.equals("southEast")) {
            nextRoom = currentRoom.getExit("southEast");
        } 
        if(direction.equals("northEast")) {
            nextRoom = currentRoom.getExit("northEast");
        } 

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
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
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    private void printLocationInfo()
    {
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Exits: ");
        System.out.print(getExitString());
    }

    public String getExitString()
    {
        String Exits = "";

        if(currentRoom.getExit("north") != null) {
            Exits += "north ";
        }
        if(currentRoom.getExit("east") != null) {
            Exits += "east ";
        }
        if(currentRoom.getExit("south") != null) {
            Exits += "south ";
        }
        if(currentRoom.getExit("west") != null) {
            Exits += "west ";
        }
        if(currentRoom.getExit("southEast") != null) {
            Exits += "southEast ";
        }
        if(currentRoom.getExit("northEast") != null) {
            Exits += "northEast ";
        }
        Exits += "\n";

        return Exits;
    }
}
import java.util.Set;
import java.util.Stack;

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
    private Player player;
    private int siguienteIDAAsignar;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        player = new Player();
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room plantaDos,restaurante,cine,recreativos,ascensor,plantaUno,tiendaRopa,zapateria,salida,supermercado,salaAscensor,calle;

        //Objetos

        Item llaveAscensor = new Item("llaveAscensor",0.5,true,true);
        Item papelera = new Item("papelera",3,true,false);
        Item moneda = new Item("moneda",0.05,true,false);
        Item carro = new Item("carro",20,false,false);
        Item ascensorObject = new Item("ascensor",500,false,false);
        Item comida = new Item("comida",1,true,false);
        Item cartera = new Item("cartera",1,true,false);
        Item planta = new Item("planta",5,false,false);
        Item camiseta = new Item("camiseta", 0.2,true,false);
        Item puerta = new Item("puerta",5,false,false);
        Item flor = new Item("flor",0.5,false,false);
        Item deportivas = new Item("deportivas",0.25,true,false);
        Item llavePuerta = new Item("llavePuerta",0.5,true,true);

        //puertas
        
        Door ascensorDoor = new Door(false,llaveAscensor,"east");
        Door calleDoor = new Door(false,llavePuerta,"east");
        Door openedDoor = new Door(true,null,"");
        
        // create the rooms
        
        plantaDos = new Room("en la planta 2",openedDoor);
        restaurante = new Room("en una zona con restaurantes",openedDoor);
        cine = new Room("en la puerta del cine",openedDoor);
        recreativos = new Room("en la zona recreativos",openedDoor);
        salaAscensor = new Room("habitacion con ascensor",ascensorDoor);
        ascensor = new Room("en el ascensor",openedDoor);
        plantaUno = new Room("en el primer piso (Planta 1)",openedDoor);
        tiendaRopa = new Room("en una tienda de ropa",openedDoor);
        zapateria = new Room("en la zapateria",openedDoor);
        salida = new Room("en la salida principal",calleDoor);
        calle = new Room("en la calle",openedDoor);
        supermercado = new Room("en el supermercado",openedDoor);

        //Pisos Planta Dos
        plantaDos.setExit("north",restaurante);
        plantaDos.setExit("south",cine);
        plantaDos.setExit("west",recreativos);
        plantaDos.setExit("northEast",supermercado);
        plantaDos.setExit("east",salaAscensor);
        plantaDos.addItem(papelera);

        //Salidas Cine
        cine.setExit("north",plantaDos);
        cine.addItem(new Item("palomitas",0.5,true,false));
        cine.addItem(moneda);
        cine.addItem(llaveAscensor);

        //Salidas recreativos
        recreativos.setExit("east",plantaDos);
        recreativos.addItem(carro);

        //Salidas supermercado
        supermercado.setExit("southWest",plantaDos);
        supermercado.addItem(comida);

        //Salidas restaurante 
        restaurante.setExit("south",plantaDos);
        restaurante.addItem(planta);

        //Salida sala ascensor
        salaAscensor.setExit("west",plantaDos);
        salaAscensor.setExit("east",ascensor);
        salaAscensor.addItem(ascensorObject);

        //Salida ascensor
        ascensor.setExit("stayHere",plantaUno);
        ascensor.setExit("down",plantaDos);
        ascensor.addItem(camiseta);

        //Salidas Piso 1
        plantaUno.setExit("north",tiendaRopa);
        plantaUno.setExit("south",zapateria);
        plantaUno.setExit("west",ascensor);
        plantaUno.setExit("east",salida);
        plantaUno.addItem(puerta);

        //Salidas tienda ropa
        tiendaRopa.setExit("south",plantaUno);
        tiendaRopa.addItem(flor);

        //salida salida
        salida.setExit("west",plantaUno);
        salida.setExit("east",calle);
        salida.addItem(deportivas);

        //salida calle
        calle.setExit("west",salida);
        calle.addItem(carro);

        //salida zapateria
        zapateria.setExit("north",plantaUno);
        zapateria.addItem(planta); 
        zapateria.addItem(llavePuerta);

        player.setCurrentRoom(plantaDos);

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
        player.printLocationInfo();
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

        Option commandWord = command.getCommandWord();
        switch (commandWord){
            case HELP:
            printHelp();
            break;
            case GO:
            player.goRoom(command);
            break;
            case LOOK:
            player.look();
            break;
            case EAT:
            player.eat();
            break;
            case BACK:
            player.back();
            break;
            case ITEMS:
            player.items();
            break;
            case TAKE:
            player.take(command);
            break;
            case QUIT:
            wantToQuit = quit(command);
            break;
            case DROP:
            player.drop(command);
            break;
            case USE:
            player.use(command);
            break;
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
        System.out.println("around at the comercial center.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.getCommands();
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

}
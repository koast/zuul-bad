import java.util.HashMap;
 
 
/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class CommandWords
{     
	private Option[] commands;

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
	commands = Option.values();	
    }

    /**
     * Check whether a given String is a valid command word. 
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String aString)
    {	
    	boolean command = false;
		int index= 0; 
		while (index<commands.length && !command){
			Option key = commands[index];
			if (key != Option.UNKNOWN){
				if (key.getOptionString().equals(aString)){
					command= true;
				}
				
			}
			index++;
		}

    	return command;

    }

    /**
     * Print all valid commands to System.out
     */    
    public void showAll()
    {
		for(Option option: commands){
		if (option != Option.UNKNOWN){
			System.out.print(option.getOptionString()+ " ");
			}
		}
    }
		
	/**
	 * Return the Option associated with a word.
	 * @param commandWord The word to look up (as a string).
	 * @return The Option correspondng to commandWord, or UNKNOWN
	 *         if it is not a valid command word.
	 */
	public Option getCommandWord(String commandWord)
	{
	  	Option comando= Option.UNKNOWN;
	  	boolean encontrado = false;
	  	int index=0;
                while(index<commands.length && !encontrado) {
                	Option key = commands[index];
					if (key != Option.UNKNOWN){
						if (key.getOptionString().equals(commandWord)){
							encontrado = true;
							comando = key;
						}
					}
				    index++;
                }	  
               	return comando;
	}      
}










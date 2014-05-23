public enum Option
{
	GO("ir"), HELP("ayuda"), QUIT("salir"), TAKE("coger"), 
	DROP("dejar"), BACK("volver"), EAT("comer"), LOOK("mirar"), 
	ITEMS("objetos"), USE("usar"), UNKNOWN("");
	
	private String optionString;

	Option(String optionString)
	{
		this.optionString = optionString;
	}

	/**
	 *
	 *@return 
	 */
	 public String getOptionString(){
		return this.optionString;
	 }
}
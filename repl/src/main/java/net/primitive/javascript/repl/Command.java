package net.primitive.javascript.repl;

public interface Command {

	/**
	 * Executes command
	 * @param commandLine
	 * @return 
	 */
	CommandContinue execute(String commandLine);
	
	String getHelp();
	
	
	
}

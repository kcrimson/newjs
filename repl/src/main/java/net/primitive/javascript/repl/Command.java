package net.primitive.javascript.repl;

public interface Command {

	/**
	 * Executes command
	 * @param commandLine
	 * @return 
	 */
	void execute(REPLRuntime runtime, String[] args);
	
	String getHelp();
	
	
	
}

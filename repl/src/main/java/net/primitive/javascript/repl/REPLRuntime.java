package net.primitive.javascript.repl;

import java.io.IOException;


/**
 * This interace is a helper, which holds all REPL runtime passed to REPL commands at execution.
 * 
 * @author jpalka@gmail.com
 * 
 */
public interface REPLRuntime {

	void exit();

	void println(String string) throws IOException;


}

package net.primitive.javascript.repl;

import static java.lang.System.out;

import java.io.IOException;

import jline.Terminal;
import jline.console.ConsoleReader;
import net.primitive.javascript.interpreter.RuntimeContext;

public class DefaultREPLRuntime implements REPLRuntime {

	private final Terminal terminal;
	private final ConsoleReader reader;

	public DefaultREPLRuntime(Terminal terminal,ConsoleReader reader) {
		super();
		this.terminal = terminal;
		this.reader = reader;
	}

	@Override
	public void exit() {
		// exiting script runtime
		RuntimeContext.exitContext();
		try {
			// restoring terminal
			terminal.restore();
		} catch (Exception e) {
			out.println("failed to restore terminal");
		}
		System.exit(0);
	}

	@Override
	public void println(String message) throws IOException{
		reader.println(message);
	}

}

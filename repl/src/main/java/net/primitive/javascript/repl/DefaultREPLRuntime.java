package net.primitive.javascript.repl;

import static java.lang.System.out;
import static java.util.Collections.unmodifiableMap;

import java.io.IOException;
import java.util.Map;

import jline.Terminal;
import jline.console.ConsoleReader;
import net.primitive.javascript.interpreter.RuntimeContext;

public class DefaultREPLRuntime implements REPLRuntime {

	private final Terminal terminal;
	private final ConsoleReader reader;
	private final CommandParser commandParser;

	public DefaultREPLRuntime(Terminal terminal, ConsoleReader reader,
			CommandParser commandParser) {
		super();
		this.terminal = terminal;
		this.reader = reader;
		this.commandParser = commandParser;
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
	public void println(String message) throws IOException {
		reader.println(message);
	}

	@Override
	public Map<String, Command> getCommands() {
		return unmodifiableMap(commandParser.getAvailableCommands());
	}

}

package net.primitive.javascript.repl;

import java.util.HashMap;
import java.util.Map;

public class CommandParser {

	private final Map<String, Command> availableCommands = new HashMap<String, Command>();

	public CommandParser() {
		super();
		registerAvailableCommands();
	}

	private void registerAvailableCommands() {
		availableCommands.put("/x", new ExitCommand());
	}

	public CommandMatcher matcher(String cmd) {

		String trimmedCmd = cmd.trim();

		Command command = availableCommands.get(trimmedCmd);

		if (command != null) {
			return new CommandMatcher(command);
		}

		return null;
	}

}

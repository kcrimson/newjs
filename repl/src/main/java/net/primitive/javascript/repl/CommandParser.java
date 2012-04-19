package net.primitive.javascript.repl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author jpalka@gmail.com
 * 
 */
public class CommandParser {

	private static final Pattern COMMAND_PATTERN = Pattern
			.compile("(/[a-z])((\\s+(\\w+))*)?");

	private final Map<String, Command> availableCommands = new HashMap<String, Command>();

	public CommandParser() {
		super();
		registerAvailableCommands();
	}

	private void registerAvailableCommands() {
		availableCommands.put("/x", new ExitCommand());
		availableCommands.put("/g", new GlobalsCommand());
		availableCommands.put("/h", new HelpCommand());
	}

	public CommandMatcher matcher(String cmd) {

		Matcher matcher = COMMAND_PATTERN.matcher(cmd);

		if (matcher.matches()) {

			String trimmedCmd = matcher.group(1);

			Command command = availableCommands.get(trimmedCmd);
			String[] args = matcher.group(2).trim().split("\\s+");

			if (command != null) {
				return new CommandMatcher(command,args);
			}

			// throw new CommandNotFound();
		}

		return null;
	}

	public Map<String, Command> getAvailableCommands() {
		return availableCommands;
	}
	
}

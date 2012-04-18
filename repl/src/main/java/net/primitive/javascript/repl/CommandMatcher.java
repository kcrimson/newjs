package net.primitive.javascript.repl;

public class CommandMatcher {

	private final Command command;

	public CommandMatcher(Command command) {
		this.command = command;
	}

	public boolean matches() {
		return command!=null;
	}

	public Command command() {
		return command;
	}

}

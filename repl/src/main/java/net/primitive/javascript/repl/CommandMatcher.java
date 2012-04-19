package net.primitive.javascript.repl;

public class CommandMatcher {

	private final Command command;
	private final String[] args;

	public CommandMatcher(Command command, String[] args) {
		this.command = command;
		this.args = args;
	}

	public boolean matches() {
		return command != null;
	}

	public void execute(REPLRuntime runtime) {
		command.execute(runtime, args);
	}

}

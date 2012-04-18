package net.primitive.javascript.repl;

public class ExitCommand implements Command {

	@Override
	public CommandContinue execute(String commandLine) {
		return CommandContinue.Break;
	}

	@Override
	public String getHelp() {
		return null;
	}

}

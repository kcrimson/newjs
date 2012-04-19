package net.primitive.javascript.repl;

public class ExitCommand implements Command {

	@Override
	public void execute(REPLRuntime runtime,String[] args) {
		runtime.exit();
	}

	@Override
	public String getHelp() {
		return "exits ECMAScript console";
	}

}

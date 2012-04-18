package net.primitive.javascript.repl;

public class GlobalsCommand implements Command {

	@Override
	public void execute(REPLRuntime runtime, String commandLine) {
		runtime.println("Hello");
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return null;
	}

}

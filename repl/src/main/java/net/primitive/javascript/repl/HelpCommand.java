package net.primitive.javascript.repl;

import java.io.IOException;
import java.util.Map;

public class HelpCommand implements Command {

	@Override
	public void execute(REPLRuntime runtime, String[] args) {
		Map<String, Command> commands = runtime.getCommands();
		for (Map.Entry<String, Command> entry : commands.entrySet()) {
			try {
				runtime.println(String.format("%s - %s", entry.getKey(), entry
						.getValue().getHelp()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getHelp() {
		return "prints this help message";
	}

}

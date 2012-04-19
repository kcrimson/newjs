package net.primitive.javascript.repl;

import java.io.IOException;
import java.util.Map;

import net.primitive.javascript.core.Convertions;
import net.primitive.javascript.core.PropertyDescriptor;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.interpreter.RuntimeContext;

public class GlobalsCommand implements Command {

	@Override
	public void execute(REPLRuntime runtime, String commandLine) {
		try {
			Scriptable globalObject = RuntimeContext.currentContext()
					.getGlobalObject();
			for (Map.Entry<String, PropertyDescriptor> property : globalObject
					.getOwnProperties().entrySet()) {
				runtime.println(property.getKey() + "=>"
						+ Convertions.toString(property.getValue().getValue()));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return null;
	}

}

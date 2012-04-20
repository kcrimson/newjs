package net.primitive.javascript.repl;

import static java.lang.String.format;

import java.io.IOException;
import java.util.Map;

import net.primitive.javascript.core.Convertions;
import net.primitive.javascript.core.PropertyDescriptor;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.interpreter.RuntimeContext;

public class GlobalsCommand implements Command {

	@Override
	public void execute(REPLRuntime runtime, String[] args) {
		try {
			Scriptable globalObject = RuntimeContext.currentContext()
					.getGlobalObject();
			for (Map.Entry<String, PropertyDescriptor> property : globalObject
					.getOwnProperties().entrySet()) {
				runtime.println(format(" %s => %s", property.getKey(),
						Convertions.toString(property.getValue().getValue())));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getHelp() {
		return "prints out objects available in global scope";
	}

}

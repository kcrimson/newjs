package net.primitive.javascript.compiler;

import net.primitive.javascript.core.Convertions;
import net.primitive.javascript.core.Scriptable;

public class ExampleScript {

	public Object execute(Scriptable scope) {
		if (Convertions.toBoolean(true)) {
			scope.put("a", 5);
		}
		return null;
	}

}

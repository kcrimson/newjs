package net.primitive.javascript.compiler;

import net.primitive.javascript.core.Scriptable;

public interface Script {

	void exec(Scriptable globalObject);

}

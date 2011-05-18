package net.primitive.javascript.tests.functional;

import net.primitive.javascript.core.Script;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.jdk.Console;
import net.primitive.javascript.core.jdk.JDKHost;
import net.primitive.javascript.core.natives.StandardObjects;
import net.primitive.javascript.interpreter.Interpreter;

import org.junit.Test;

public class ConsoleTest {

	@Test
	public void should_have_log_function() throws Exception {
		Scriptable stdObjs = StandardObjects.init();

		Console console = new Console();

		stdObjs.put("console", JDKHost.wrapJavaObject(console));

		Interpreter interpreter = new Interpreter();

		Script script = interpreter.interpret("console.log(\"Hello\");");

		script.execute(stdObjs);

	}

}

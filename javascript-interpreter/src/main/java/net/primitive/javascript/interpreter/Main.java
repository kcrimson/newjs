package net.primitive.javascript.interpreter;

import java.io.File;
import java.io.FileReader;

import net.primitive.javascript.core.JavaScriptLexer;
import net.primitive.javascript.core.ScriptableObject;
import sun.org.mozilla.javascript.internal.Script;

/**
 * Test driver program for the ANTLR3 Maven Architype demo
 * 
 * @author Jim Idle (jimi@temporal-wave.com)
 */
class Main {

	static JavaScriptLexer lexer;

	/**
	 * Just a simple test driver for the ASP parser to show how to call it.
	 */

	public static void main(String[] args) throws Exception {

		// Create the lexer, which we can keep reusing if we like
		//

		Interpreter interpreter = new Interpreter();

		interpreter
				.interpret(new File("src/test/resources/return-statement.js"));

		long time = System.currentTimeMillis();

		for (int i = 0; i < 1000000; i++) {
			net.primitive.javascript.core.ScriptableObject scriptableObject = new net.primitive.javascript.core.ScriptableObject();

			interpreter.execute(scriptableObject);
		}
		System.out.println(System.currentTimeMillis() - time);

		// time = System.currentTimeMillis();
		// for (int i = 0; i < 1000000; i++) {
		// int a = 1;
		// while (a < 5) {
		// a = a + 1;
		// }
		// }
		// System.out.println(System.currentTimeMillis() - time);
		//
		sun.org.mozilla.javascript.internal.Context context = sun.org.mozilla.javascript.internal.Context
				.enter();
		sun.org.mozilla.javascript.internal.ScriptableObject standardObjects = context
				.initStandardObjects();
		Script script = context.compileReader(new FileReader(
				"src/test/resources/return-statement.js"), "", 0, null);
		time = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			script.exec(context, standardObjects);
		}
		System.out.println(System.currentTimeMillis() - time);
	}

}

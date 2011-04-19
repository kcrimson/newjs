package net.primitive.javascript.utils;

import java.io.File;
import java.io.FileReader;

import net.primitive.javascript.core.JavaScriptLexer;
import net.primitive.javascript.interpreter.Interpreter;

import org.mozilla.javascript.Context;

/**
 * Test driver program for the ANTLR3 Maven Architype demo
 * 
 * @author Jim Idle (jimi@temporal-wave.com)
 */
class BenchmarkTestDriver {

	static JavaScriptLexer lexer;

	/**
	 * Just a simple test driver for the ASP parser to show how to call it.
	 */

	public static void main(String[] args) throws Exception {

		// Create the lexer, which we can keep reusing if we like
		//

		Interpreter interpreter = new Interpreter();

		interpreter.interpret(new File("src/test/resources/while.js"));

		long time = System.currentTimeMillis();

		for (int i = 0; i < 3000000; i++) {
			net.primitive.javascript.core.ScriptableObject scriptableObject = new net.primitive.javascript.core.ScriptableObject();

			interpreter.execute(scriptableObject);
		}
		System.out.println(System.currentTimeMillis() - time);

		Context context = Context.enter();
		org.mozilla.javascript.ScriptableObject standardObjects = context
				.initStandardObjects();
		org.mozilla.javascript.Script script = context.compileReader(
				new FileReader("src/test/resources/while.js"), "", 0, null);
		time = System.currentTimeMillis();
		for (int i = 0; i < 3000000; i++) {
			script.exec(context, standardObjects);
		}
		System.out.println(System.currentTimeMillis() - time);
	}

}

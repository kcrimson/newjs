package net.primitive.javascript.core;

import net.primitive.javascript.core.ast.Program;
import net.primitive.javascript.interpreter.ProgramVisitorImpl;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;

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

		long time = System.currentTimeMillis();
		lexer = new JavaScriptLexer(new ANTLRFileStream(
				"src/test/resources/function-decl.js"));

		CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);

		JavaScriptParser javaScriptParser = new JavaScriptParser(
				commonTokenStream);
		Program result = javaScriptParser.program().result;

		for (int i = 0; i < 1000000; i++) {
			net.primitive.javascript.core.ScriptableObject scriptableObject = new net.primitive.javascript.core.ScriptableObject();
			ProgramVisitorImpl visitor = new ProgramVisitorImpl(
					scriptableObject);
			result.accept(visitor);
		}
		System.out.println(System.currentTimeMillis() - time);

//		time = System.currentTimeMillis();
//		for (int i = 0; i < 1000000; i++) {
//			int a = 1;
//			while (a < 5) {
//				a = a + 1;
//			}
//		}
//		System.out.println(System.currentTimeMillis() - time);
//
//		time = System.currentTimeMillis();
//		Context context = sun.org.mozilla.javascript.internal.Context.enter();
//		ScriptableObject standardObjects = context.initStandardObjects();
//		Script script = context.compileReader(new FileReader("src/test/resources/while.js"), "", 0, null);
//		for (int i = 0; i < 1000000; i++) {
//			script.exec(context, standardObjects);
//		}
//		System.out.println(System.currentTimeMillis() - time);
	}

}

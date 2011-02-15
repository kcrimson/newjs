package net.primitive.javascript.core;

import net.primitive.javascript.core.ast.Program;
import net.primitive.javascript.interpreter.ProgramVisitorImpl;

import org.antlr.runtime.ANTLRStringStream;
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

		lexer = new JavaScriptLexer(new ANTLRStringStream(
				"var a=((1 + 3) - 1)*24;"));

		CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);

		JavaScriptParser javaScriptParser = new JavaScriptParser(
				commonTokenStream);
		Program result = javaScriptParser.program().result;

		ScriptableObject scriptableObject = new ScriptableObject();
		ProgramVisitorImpl visitor = new ProgramVisitorImpl(scriptableObject);
		result.accept(visitor);
		System.out.println(scriptableObject.get("a", scriptableObject));

		// List<SourceElement> sourceElements = result.getSourceElements();
		// VariableStatement sourceElement = (VariableStatement)
		// sourceElements.get(0);
		// Object evaluate =
		// sourceElement.getVariableDeclarations().get(0).getExpression().evaluate();
		// System.out.println(evaluate);
	}
}

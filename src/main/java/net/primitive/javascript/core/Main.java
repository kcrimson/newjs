package net.primitive.javascript.core;

import java.util.List;

import net.primitive.javascript.core.ast.Literal;
import net.primitive.javascript.core.ast.Program;
import net.primitive.javascript.core.ast.SourceElement;
import net.primitive.javascript.core.ast.VariableStatement;

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
				"var a=1 + 1 - 1;"));

		CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);

		JavaScriptParser javaScriptParser = new JavaScriptParser(
				commonTokenStream);
		Program result = javaScriptParser.program().result;
		List<SourceElement> sourceElements = result.getSourceElements();
		VariableStatement sourceElement = (VariableStatement) sourceElements.get(0);
		Object evaluate = sourceElement.getVariableDeclarations().get(0).getExpression().evaluate();
		System.out.println(evaluate);
	}
}

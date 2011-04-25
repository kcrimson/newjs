package net.primitive.javascript.interpreter;

import static net.primitive.javascript.interpreter.RuntimeContext.enterContext;
import static net.primitive.javascript.interpreter.RuntimeContext.exitContext;

import java.io.File;
import java.io.IOException;

import net.primitive.javascript.core.JavaScriptLexer;
import net.primitive.javascript.core.JavaScriptParser;
import net.primitive.javascript.core.ScopeBindings;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ast.Program;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

public class Interpreter {

	private Program program;

	public Interpreter() {
	}

	public void interpret(File file) throws IOException, RecognitionException {
		JavaScriptLexer lexer = new JavaScriptLexer(new ANTLRFileStream(
				file.getAbsolutePath()));

		CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);

		JavaScriptParser javaScriptParser = new JavaScriptParser(
				commonTokenStream);
		program = javaScriptParser.program().result;

	}

	public ScopeBindings execute(Scriptable globalObject) {
		RuntimeContext currentContext = enterContext(globalObject);

		ProgramVisitorImpl visitor = new ProgramVisitorImpl(currentContext);

		program.accept(visitor);

		exitContext();

		return currentContext.getVariables();
	}

}

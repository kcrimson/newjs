package net.primitive.javascript.interpreter;

import java.io.File;
import java.io.IOException;

import net.primitive.javascript.core.JavaScriptLexer;
import net.primitive.javascript.core.JavaScriptParser;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ast.Program;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

public class Interpreter {

	private final Scriptable scope;

	public Interpreter(Scriptable scope) {
		this.scope = scope;
	}

	public void interpret(File file) throws IOException, RecognitionException {
		JavaScriptLexer lexer = new JavaScriptLexer(new ANTLRFileStream(
				file.getAbsolutePath()));

		CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);

		JavaScriptParser javaScriptParser = new JavaScriptParser(
				commonTokenStream);
		Program result = javaScriptParser.program().result;

		ProgramVisitorImpl visitor = new ProgramVisitorImpl(scope);

		result.accept(visitor);

	}

}

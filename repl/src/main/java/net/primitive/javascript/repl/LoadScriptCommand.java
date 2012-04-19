package net.primitive.javascript.repl;

import net.primitive.javascript.core.ast.Program;
import net.primitive.javascript.core.parser.JavaScriptLexer;
import net.primitive.javascript.core.parser.JavaScriptParser;
import net.primitive.javascript.interpreter.ProgramVisitorImpl;
import net.primitive.javascript.interpreter.RuntimeContext;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;

public class LoadScriptCommand implements Command {

	@Override
	public void execute(REPLRuntime runtime, String[] args) {

		String filename = args[0];

		try {
			final JavaScriptLexer lexer = new JavaScriptLexer(
					new ANTLRFileStream(filename));
			final CommonTokenStream commonTokenStream = new CommonTokenStream(
					lexer);
			final JavaScriptParser javaScriptParser = new JavaScriptParser(
					commonTokenStream);
			final Program program = javaScriptParser.program().result;
			ProgramVisitorImpl visitor = new ProgramVisitorImpl(
					RuntimeContext.currentContext());
			program.accept(visitor);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

	}

	@Override
	public String getHelp() {
		return "load script from file";
	}

}

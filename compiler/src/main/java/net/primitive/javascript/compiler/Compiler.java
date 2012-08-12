package net.primitive.javascript.compiler;

import java.io.File;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;

import net.primitive.javascript.classgen.ClassGen;
import net.primitive.javascript.core.Script;
import net.primitive.javascript.core.ast.Program;
import net.primitive.javascript.core.parser.JavaScriptLexer;
import net.primitive.javascript.core.parser.JavaScriptParser;

public class Compiler {

	private final ClassGen classGen;

	public Compiler() {
		classGen = new ClassGen();
	}

	public Script compile(File file) throws Exception {

		JavaScriptLexer lexer = new JavaScriptLexer(new ANTLRFileStream(
				file.getAbsolutePath()));

		CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);

		JavaScriptParser javaScriptParser = new JavaScriptParser(
				commonTokenStream);
		final Program program = javaScriptParser.program().result;

		Class<?> generatedClass = classGen.generateClass("ScriptImpl", program,
				Compiler.class.getClassLoader());
		return (Script) generatedClass.newInstance();
	}

}

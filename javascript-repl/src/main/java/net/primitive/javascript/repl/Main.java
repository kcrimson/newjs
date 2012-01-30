package net.primitive.javascript.repl;

import static net.primitive.javascript.interpreter.RuntimeContext.enterContext;
import static net.primitive.javascript.interpreter.RuntimeContext.exitContext;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import jline.Terminal;
import jline.TerminalFactory;
import jline.console.ConsoleReader;
import jline.console.completer.Completer;
import net.primitive.javascript.core.PropertyDescriptor;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;
import net.primitive.javascript.core.ast.Program;
import net.primitive.javascript.core.natives.StandardObjects;
import net.primitive.javascript.core.parser.JavaScriptLexer;
import net.primitive.javascript.core.parser.JavaScriptParser;
import net.primitive.javascript.interpreter.ProgramVisitorImpl;
import net.primitive.javascript.interpreter.RuntimeContext;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;

public class Main {

	public static void main(String[] args) throws Exception {

		Terminal terminal = TerminalFactory.create();
		terminal.init();

		ConsoleReader consoleReader = new ConsoleReader("newjs shell",
				System.in, System.out, terminal);
		consoleReader.setPrompt("js> ");

		consoleReader
				.println("ECMAScript 5 \"strict mode\". Use /? for mode details");

		String line;
		final Scriptable globalObject = new ScriptableObject();
		StandardObjects standardObjects = StandardObjects
				.createStandardObjects(globalObject);
		net.primitive.javascript.core.jdk.Console.init(globalObject);

		consoleReader.addCompleter(new Completer() {

			@Override
			public int complete(String buffer, int cursor,
					List<CharSequence> candidates) {
				Enumeration<String> enumeration = globalObject.enumeration();
				while (enumeration.hasMoreElements()) {
					String nextElement = enumeration.nextElement();
					if(nextElement.startsWith(buffer)){
						candidates.add(nextElement);
					}
				}

				return 0;
			}
		});

		RuntimeContext currentContext = enterContext(standardObjects,
				globalObject);
		while ((line = consoleReader.readLine()) != null) {

			if ("/g".equals(line)) {
				for (Map.Entry<String, PropertyDescriptor> property : globalObject
						.getOwnProperties().entrySet()) {
					consoleReader.println(property.getKey() + "=>"
							+ property.getValue().getValue());
				}
				continue;
			}

			if ("/x".equals(line)) {
				break;
			}

			if ("/?".equals(line)) {
				consoleReader
						.println("This is help for ECMAScript 5 \"strict mode\" shell");
				consoleReader.println("/? - prints this help message");
				consoleReader.println("/g - prints all global objects");
				consoleReader.println("/e - exits shell");
				continue;
			}
			
			if("/l".equals(line)){
				continue;
			}

			JavaScriptLexer lexer = new JavaScriptLexer(new ANTLRStringStream(
					line));

			CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);

			JavaScriptParser javaScriptParser = new JavaScriptParser(
					commonTokenStream);
			final Program program = javaScriptParser.program().result;

			ProgramVisitorImpl visitor = new ProgramVisitorImpl(currentContext);

			program.accept(visitor);
		}

		exitContext();
		terminal.restore();

	}

}

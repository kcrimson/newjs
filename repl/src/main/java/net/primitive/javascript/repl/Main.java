/**
 * Copyright (C) 2012 Primitive Team <jpalka@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.primitive.javascript.repl;

import static java.lang.System.exit;
import static net.primitive.javascript.interpreter.RuntimeContext.enterContext;
import static net.primitive.javascript.interpreter.RuntimeContext.exitContext;

import java.io.File;
import java.util.Enumeration;
import java.util.List;

import jline.Terminal;
import jline.TerminalFactory;
import jline.console.ConsoleReader;
import jline.console.completer.Completer;
import net.primitive.javascript.core.Script;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;
import net.primitive.javascript.core.ast.Program;
import net.primitive.javascript.core.natives.StandardObjects;
import net.primitive.javascript.core.parser.JavaScriptLexer;
import net.primitive.javascript.core.parser.JavaScriptParser;
import net.primitive.javascript.interpreter.Interpreter;
import net.primitive.javascript.interpreter.ProgramVisitorImpl;
import net.primitive.javascript.interpreter.RuntimeContext;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;

public class Main {

	public static void main(String[] args) throws Exception {

		// checking command line options
		if (args.length > 0) {
			String scriptfile = args[0];
			Interpreter interpreter = new Interpreter();
			Script script = interpreter.interpret(new File(scriptfile));
			final Scriptable globalObject = new ScriptableObject();
			net.primitive.javascript.core.jdk.Console.init(globalObject);
			script.execute(globalObject);
			exit(0);
		}

		// setting up command line terminal
		Terminal terminal = TerminalFactory.create();
		terminal.init();

		ConsoleReader consoleReader = new ConsoleReader("newjs shell",
				System.in, System.out, terminal);
		consoleReader.setPrompt("js> ");

		consoleReader
				.println("ECMAScript 5 \"strict mode\". Use /h for mode details");

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
					if (nextElement.startsWith(buffer)) {
						candidates.add(nextElement);
					}
				}

				return 0;
			}
		});

		RuntimeContext currentContext = enterContext(standardObjects,
				globalObject);

		CommandParser parser = new CommandParser();
		REPLRuntime runtime = new DefaultREPLRuntime(terminal, consoleReader,
				parser);

		String line;
		while ((line = consoleReader.readLine()) != null) {

			CommandMatcher matcher = parser.matcher(line);

			if (matcher != null && matcher.matches()) {
				matcher.execute(runtime);
			} else {

				// wrap parsing, so we don't exit in a most unexpected moment
				try {
					final JavaScriptLexer lexer = new JavaScriptLexer(
							new ANTLRStringStream(line));
					final CommonTokenStream commonTokenStream = new CommonTokenStream(
							lexer);
					final JavaScriptParser javaScriptParser = new JavaScriptParser(
							commonTokenStream);
					final Program program = javaScriptParser.program().result;
					ProgramVisitorImpl visitor = new ProgramVisitorImpl(
							currentContext);
					program.accept(visitor);
				} catch (Exception e) {
					e.printStackTrace();
					String msg = e.getMessage();
					if (msg == null) {
						msg = "something unexpected happend";
					}
					consoleReader.println(msg);
				}
			}

		}

		exitContext();
		terminal.restore();

	}

}

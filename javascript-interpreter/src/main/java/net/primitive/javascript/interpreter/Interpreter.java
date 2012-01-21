/**
 * Copyright (C) 2011 Primitive Team <jpalka@gmail.com>
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

package net.primitive.javascript.interpreter;

import static net.primitive.javascript.interpreter.RuntimeContext.enterContext;
import static net.primitive.javascript.interpreter.RuntimeContext.exitContext;

import java.io.File;
import java.io.IOException;

import net.primitive.javascript.core.Script;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ast.Program;
import net.primitive.javascript.core.parser.JavaScriptLexer;
import net.primitive.javascript.core.parser.JavaScriptParser;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

public class Interpreter {

	public Interpreter() {
	}

	public Script interpret(File file) throws IOException, RecognitionException {
		JavaScriptLexer lexer = new JavaScriptLexer(new ANTLRFileStream(
				file.getAbsolutePath()));

		CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);

		JavaScriptParser javaScriptParser = new JavaScriptParser(
				commonTokenStream);
		final Program program = javaScriptParser.program().result;

		return new Script() {

			@Override
			public void execute(Scriptable globalObject) {
				RuntimeContext currentContext = enterContext(globalObject);

				ProgramVisitorImpl visitor = new ProgramVisitorImpl(
						currentContext);

				program.accept(visitor);

				exitContext();

			}
		};

	}

	public Script interpret(String script) throws IOException,
			RecognitionException {
		JavaScriptLexer lexer = new JavaScriptLexer(new ANTLRStringStream(
				script));

		CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);

		JavaScriptParser javaScriptParser = new JavaScriptParser(
				commonTokenStream);
		final Program program = javaScriptParser.program().result;

		return new Script() {

			@Override
			public void execute(Scriptable globalObject) {
				RuntimeContext currentContext = enterContext(globalObject);

				ProgramVisitorImpl visitor = new ProgramVisitorImpl(
						currentContext);

				program.accept(visitor);

				exitContext();

			}
		};

	}

}

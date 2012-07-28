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
package net.primitive.javascript.tests.performance;

import static net.primitive.javascript.core.natives.StandardObjects.createStandardObjects;

import java.io.File;
import java.io.IOException;

import net.primitive.javascript.core.Script;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;
import net.primitive.javascript.interpreter.Interpreter;

import org.antlr.runtime.RecognitionException;
import org.apache.commons.lang3.time.StopWatch;

public class NewJSBenchmarkDriver implements BenchmarkDriver {

	/* (non-Javadoc)
	 * @see net.primitive.javascript.tests.performance.BenchmarkDriver#benchmark(int, java.io.File)
	 */
	@Override
	public void benchmark(int repCount, File testScript) throws IOException,
			RecognitionException {
		Interpreter interpreter = new Interpreter();
		Script script = interpreter.interpret(testScript);

		Scriptable globalObject = null;
		
		StopWatch stopwatch = new StopWatch();
		stopwatch.start();
		for (int i = 0; i < repCount; i++) {

			globalObject = new ScriptableObject();
			createStandardObjects(globalObject);

			script.execute(globalObject);

		}
		stopwatch.stop();
	}

}

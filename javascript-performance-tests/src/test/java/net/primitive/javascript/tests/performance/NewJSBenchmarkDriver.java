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
		System.out.println(stopwatch.getTime());
	}

}

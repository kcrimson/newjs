package net.primitive.javascript.tests.performance;

import java.io.File;
import java.io.FileReader;

import org.apache.commons.lang.time.StopWatch;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptableObject;

public class RhinoBenchmarkDriver implements BenchmarkDriver {

	@Override
	public void benchmark(int repCount, File testScript) throws Exception {
		Context context = Context.enter();

		Script script = context.compileReader(new FileReader(testScript), "",
				0, null);

		ScriptableObject standardObjects = null;
		StopWatch stopwatch = new StopWatch();
		stopwatch.start();

		for (int i = 0; i < repCount; i++) {
			standardObjects = context.initStandardObjects();
			script.exec(context, standardObjects);
		}
		stopwatch.stop();
		System.out.println(stopwatch.getTime());
		// System.out.println(ScriptableObject.getProperty(standardObjects,
		// "assertResult"));

	}

}

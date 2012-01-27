package net.primitive.javascript.tests.performance;

import java.io.File;
import java.io.FileReader;

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
		for (int i = 0; i < repCount; i++) {
			standardObjects = context.initStandardObjects();
			script.exec(context, standardObjects);
		}
		// System.out.println(ScriptableObject.getProperty(standardObjects,
		// "assertResult"));

	}

}

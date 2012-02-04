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

import java.io.File;
import java.io.FileReader;

import org.apache.commons.lang3.time.StopWatch;
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

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

package net.primitive.javascript.tests.performance;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import net.primitive.javascript.tests.utils.ResourceList;

import org.apache.commons.lang.time.StopWatch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class BenchmarkTestDriver {

	private String javaScriptFile;

	public BenchmarkTestDriver(String javaScriptFile) {
		this.javaScriptFile = javaScriptFile;
	}

	@Parameters
	public static List<Object[]> getParameters() {
		Collection<String> scripts = ResourceList.getResources(Pattern
				.compile(".*\\.js"));
		List<Object[]> parameters = new ArrayList<Object[]>();

		for (String script : scripts) {
			parameters.add(new Object[] { script });
		}

		return parameters;
	}

	@Test
	public void run_benchmark() throws Exception {

		StopWatch stopWatch = new StopWatch();
		int repCount = 30000;
		File testScript = new File(javaScriptFile);
		System.out
				.println("Running benchmark test for " + testScript.getPath());
		BenchmarkDriver newjsDriver = new NewJSBenchmarkDriver();

		System.gc();
		stopWatch.start();
		newjsDriver.benchmark(repCount, testScript);
		stopWatch.stop();

		System.out.println("NewJS times: " + stopWatch.getTime());

		stopWatch.reset();

		System.gc();
		RhinoBenchmarkDriver rhinoBenchmarkDriver = new RhinoBenchmarkDriver();
		stopWatch.start();
		rhinoBenchmarkDriver.benchmark(repCount, testScript);
		stopWatch.stop();

		System.out.println("Rhino: " + stopWatch.getTime());
	}

	public static void main(String[] argv) throws Exception {
		BenchmarkTestDriver testDriver = new BenchmarkTestDriver(
				"/home/palkaj01/projects/newjs/javascript-functional-tests/target/classes/array/large-array.js");
		testDriver.run_benchmark();
	}
}

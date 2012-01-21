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
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import net.primitive.javascript.interpreter.Interpreter;
import net.primitive.javascript.tests.utils.ResourceList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptableObject;

/**
 * Test driver program for the ANTLR3 Maven Architype demo
 * 
 * @author Jim Idle (jimi@temporal-wave.com)
 */
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

	/**
	 * Just a simple test driver for the ASP parser to show how to call it.
	 */
	@Test
	public void run_benchmark() throws Exception {

		 System.gc();
		long time = System.currentTimeMillis();

		Interpreter interpreter = new Interpreter();
		File testScript = new File(javaScriptFile);
		net.primitive.javascript.core.Script script2 = interpreter
				.interpret(testScript);

		System.out
				.println("Running benchmark test for " + testScript.getPath());

		int repCount = 30000;
		net.primitive.javascript.core.ScriptableObject scriptableObject = null;
		for (int i = 0; i < repCount; i++) {

			scriptableObject = new net.primitive.javascript.core.ScriptableObject();

			script2.execute(scriptableObject);
			
		}
		System.out.println(scriptableObject.get("assertResult"));
		System.out.println("NewJS times: "
				+ (System.currentTimeMillis() - time));
		System.gc();
		Context context = Context.enter();
		// , "", 0, null);
		Script script = context.compileReader(new FileReader(testScript), "",
				0, null);
		
		time = System.currentTimeMillis();
		org.mozilla.javascript.ScriptableObject standardObjects = null;
		for (int i = 0; i < repCount; i++) {
			// context.evaluateReader(standardObjects, new
			// FileReader(testScript), "", 0, null);
			standardObjects = context
					.initStandardObjects();	
			script.exec(context, standardObjects);
		}
		System.out.println(ScriptableObject.getProperty(standardObjects, "assertResult"));
		System.out.println("Rhino: " + (System.currentTimeMillis() - time));
	}

	public static void main(String[] argv) throws Exception {
		BenchmarkTestDriver testDriver = new BenchmarkTestDriver(
				"../javascript-functional-tests/src/main/resources/nested-object-literal.js");
		testDriver.run_benchmark();

	}
}

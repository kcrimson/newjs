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

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import net.primitive.javascript.core.Convertions;
import net.primitive.javascript.core.ScopeBindings;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.natives.StandardObjects;
import net.primitive.javascript.utils.ResourceList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class InterpreterTest {

	private String javaScriptFile;

	public InterpreterTest(String javaScriptFile) {
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
	public void drive_javascript_test() throws Exception {

		System.out.println(javaScriptFile);

		Scriptable scope = StandardObjects.init();

		Interpreter interpreter = new Interpreter();

		interpreter.interpret(new File(javaScriptFile));
		ScopeBindings records = interpreter.execute(scope);
		Object object = records.getBinding("assertResult").getValue();
		assertTrue(Convertions.toBoolean(object));

	}
}

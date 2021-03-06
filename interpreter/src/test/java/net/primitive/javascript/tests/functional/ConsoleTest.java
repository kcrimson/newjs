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
package net.primitive.javascript.tests.functional;

import net.primitive.javascript.core.Script;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;
import net.primitive.javascript.core.jdk.Console;
import net.primitive.javascript.core.jdk.JavaHost;
import net.primitive.javascript.core.natives.StandardObjects;
import net.primitive.javascript.interpreter.Interpreter;

import org.junit.Test;

public class ConsoleTest {

	@Test
	public void should_have_log_function() throws Exception {
		Scriptable globalObject = new ScriptableObject();
		StandardObjects stdObjs = StandardObjects.createStandardObjects(globalObject);

		Console console = new Console();

		globalObject.put("console", JavaHost.wrapJavaObject(console));

		Interpreter interpreter = new Interpreter();

		Script script = interpreter.interpret("console.log(\"Hello\");");

		script.execute(globalObject);

	}

}

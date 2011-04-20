package net.primitive.javascript.interpreter;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import net.primitive.javascript.core.Convertions;
import net.primitive.javascript.core.ScriptableObject;

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

		ScriptableObject scope = new ScriptableObject();

		Interpreter interpreter = new Interpreter();

		interpreter.interpret(new File(javaScriptFile));
		EnvironmentRecords records = interpreter.execute(scope);
		Object object = records.getBinding("assertResult").getValue();
		System.out.println(object);
		assertTrue(Convertions.toBoolean(object));

	}
}

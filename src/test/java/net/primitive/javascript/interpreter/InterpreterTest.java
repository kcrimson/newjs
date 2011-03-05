package net.primitive.javascript.interpreter;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.primitive.javascript.core.Convertions;
import net.primitive.javascript.core.ScriptableObject;

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
		File resources = new File("src/test/resources");
		File[] files = resources.listFiles();

		List<Object[]> parameters = new ArrayList<Object[]>();
		for (File file : files) {
			parameters.add(new Object[] { file.getAbsolutePath() });
		}
		return parameters;
	}

	@Test
	public void drive_javascript_test() throws Exception {

		System.out.println(javaScriptFile);

		ScriptableObject scope = new ScriptableObject();

		Interpreter interpreter = new Interpreter();

		interpreter.interpret(new File(javaScriptFile));
		interpreter.execute(scope);
		Object object = scope.get("assertResult", null);
		System.out.println(object);
		assertTrue(Convertions.toBoolean(object));

	}
}

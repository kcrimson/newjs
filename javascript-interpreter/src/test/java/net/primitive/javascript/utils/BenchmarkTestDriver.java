package net.primitive.javascript.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import net.primitive.javascript.interpreter.Interpreter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

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
		interpreter.interpret(testScript);

		System.out
				.println("Running benchmark test for " + testScript.getPath());

		int repCount = 300000;
		for (int i = 0; i < repCount; i++) {

			net.primitive.javascript.core.ScriptableObject scriptableObject = new net.primitive.javascript.core.ScriptableObject();

			interpreter.execute(scriptableObject);
		}
		System.out.println("NewJS times: "
				+ (System.currentTimeMillis() - time));
//		System.gc();
//		Context context = Context.enter();
////				, "", 0, null);
//		time = System.currentTimeMillis();
//		org.mozilla.javascript.ScriptableObject standardObjects = context.initStandardObjects();
//		for (int i = 0; i < repCount; i++) {
//			context.evaluateReader(standardObjects, new FileReader(testScript), "", 0, null);
////			script.exec(context, standardObjects);
//		}
//		System.out.println("Rhino: " + (System.currentTimeMillis() - time));
	}

	public static void main(String[] argv) throws Exception {
		BenchmarkTestDriver testDriver = new BenchmarkTestDriver(
				"src/test/resources/var-decl.js");
		testDriver.run_benchmark();

	}
}

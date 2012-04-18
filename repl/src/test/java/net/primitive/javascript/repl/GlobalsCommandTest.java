package net.primitive.javascript.repl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

public class GlobalsCommandTest {

	@Test
	public void should_print_global_objects() throws Exception {

		REPLRuntime runtime = mock(REPLRuntime.class);
		GlobalsCommand command = new GlobalsCommand();
		String commandLine = null;
		command.execute(runtime, commandLine);

		verify(runtime).println("Hello");
	}

}

package net.primitive.javascript.repl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

public class ExitCommandTest {

	@Test
	public void should_exit_repl() {

		REPLRuntime runtime = mock(REPLRuntime.class);
		
		ExitCommand command = new ExitCommand();

		command.execute(runtime,"");

		verify(runtime).exit();
	}

}

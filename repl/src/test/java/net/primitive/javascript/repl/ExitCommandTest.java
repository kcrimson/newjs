package net.primitive.javascript.repl;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExitCommandTest {

	@Test
	public void should_exit_repl() {

		ExitCommand command = new ExitCommand();

		CommandContinue cc = command.execute("");

		assertEquals(CommandContinue.Break, cc);
	}

}

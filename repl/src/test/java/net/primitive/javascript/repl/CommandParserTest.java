package net.primitive.javascript.repl;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommandParserTest {

	@Test
	public void should_parse_quit_cmd() {

		CommandParser parser = new CommandParser();
		String cmd = "/x wewewew";

		CommandMatcher matcher = parser.matcher(cmd);
		assertTrue(matcher.matches());
		assertEquals(ExitCommand.class, matcher.command().getClass());

	}

}

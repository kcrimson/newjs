package net.primitive.javascript.repl;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommandParserTest {

	@Test
	public void should_parse_quit_cmd() {

		CommandParser parser = new CommandParser();
		String cmd = "/x pierwszy drugi";

		CommandMatcher matcher = parser.matcher(cmd);
		assertTrue(matcher.matches());

	}

}

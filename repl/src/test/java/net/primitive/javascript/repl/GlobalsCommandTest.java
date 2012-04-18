package net.primitive.javascript.repl;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import jline.Terminal;
import jline.TerminalFactory;
import jline.console.ConsoleReader;

import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;
import net.primitive.javascript.core.natives.StandardObjects;
import net.primitive.javascript.interpreter.RuntimeContext;

import org.junit.Test;
import org.mockito.Mockito;

public class GlobalsCommandTest {

	@Test
	public void should_print_global_objects() throws Exception {

		REPLRuntime runtime = Mockito.mock(REPLRuntime.class);
		GlobalsCommand command = new GlobalsCommand();
		String commandLine = null;
		command.execute( commandLine);
	}

}

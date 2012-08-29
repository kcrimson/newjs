package net.primitive.javascript.interpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import net.primitive.javascript.core.Scope;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.Undefined;
import net.primitive.javascript.core.ast.Statement;
import net.primitive.javascript.interpreter.utils.FastStack;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RuntimeContextTest {

	private RuntimeContext context;
	
	@Mock
	private FastStack<StatementExecutionContext> stack;
	
	@Before
	public void setup() {
	    MockitoAnnotations.initMocks(this);
	    
    }
	
	@Test
	public void shouldExitOnNormalWithEmptyStack() {
		// given 
		context = new RuntimeContext(stack);
		
		// when
		when(stack.peek()).thenReturn(new StatementExecutionContext(mock(Scope.class), mock(Scope.class), 
				mock(Scriptable.class), mock(Statement.class), new Completion(CompletionType.Normal, Undefined.Value, null)));
		when(stack.isEmpty()).thenReturn(true);

		boolean actual = context.exit();
		// then
		assertTrue("exit not found", actual);
	}
	
	
	@Test
    public void shouldSetCompletionValueOnNormalWithNonEmptyStack() throws Exception {
	    // given
		context = new RuntimeContext(stack);
		// when
		StatementExecutionContext previousStatementContext = mock(StatementExecutionContext.class);
		StatementExecutionContext currentStatementExecutionContext = new StatementExecutionContext(mock(Scope.class), mock(Scope.class), 
				mock(Scriptable.class), mock(Statement.class), new Completion(CompletionType.Normal, "abcd", null));

		when(stack.peek()).thenReturn(currentStatementExecutionContext).thenReturn(previousStatementContext);
		when(stack.isEmpty()).thenReturn(false);
		
		Completion previousCompletion = new Completion(CompletionType.Normal, Undefined.Value, null);
		when(previousStatementContext.getCompletion()).thenReturn(previousCompletion);

		context.exit();
		
		// then
		assertEquals("abcd", previousCompletion.getValue());
    }

}

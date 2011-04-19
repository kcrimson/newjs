package net.primitive.javascript.interpreter;

import static net.primitive.javascript.interpreter.LexicalEnvironment.newDeclarativeEnvironment;
import static net.primitive.javascript.interpreter.LexicalEnvironment.newObjectEnvironment;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ast.Statement;
import net.primitive.javascript.interpreter.utils.FastStack;

public class RuntimeContext {

	private static final ThreadLocal<RuntimeContext> CONTEXT_LOCAL = new ThreadLocal<RuntimeContext>();

	private final FastStack<ExecutionContext> callStack = new FastStack<ExecutionContext>();

	private final ExpressionVisitorImpl expressionVisitor = new ExpressionVisitorImpl(
			this);

	private final StatementVisitorImpl statementVisitor = new StatementVisitorImpl(
			this);

	private final Scriptable globalObject;

	private final LexicalEnvironment globalEnvironment;

	private final LexicalEnvironment variableEnvironment;

	private LexicalEnvironment lexicalEnvironment;

	private RuntimeContext(final Scriptable globalObject) {
		this.globalObject = globalObject;
		this.globalEnvironment = newObjectEnvironment(globalObject, null);
		this.lexicalEnvironment = newDeclarativeEnvironment(globalEnvironment);
		this.variableEnvironment = lexicalEnvironment;
	}

	/**
	 * @return the expressionVisitor
	 */
	public ExpressionVisitorImpl getExpressionVisitor() {
		return expressionVisitor;
	}

	/**
	 * @return the statementVisitor
	 */
	public StatementVisitorImpl getStatementVisitor() {
		return statementVisitor;
	}

	public ExecutionContext enter(Statement statement) {
		LexicalEnvironment varEnv;
		LexicalEnvironment lexEnv;
		Scriptable thisObj;
		if (callStack.isEmpty()) {
			varEnv = variableEnvironment;
			lexEnv = lexicalEnvironment;
			thisObj = globalObject;
		} else {
			ExecutionContext currentContext = callStack.peek();
			lexEnv = currentContext.getLexicalEnvironment();
			varEnv = currentContext.getVariableEnvironment();
			thisObj = currentContext.getThisBinding();
		}

		final ExecutionContext newContext = new ExecutionContext(lexEnv,
				varEnv, thisObj, statement);
		callStack.push(newContext);
		return newContext;
	}

	/**
	 * This method creates new runtime context for script execution
	 * 
	 * @param globalObject
	 *            instance of global object
	 * 
	 * @return
	 */
	public static RuntimeContext enterContext(Scriptable globalObject) {
		RuntimeContext context = new RuntimeContext(globalObject);
		CONTEXT_LOCAL.set(context);
		return context;
	}

	public static RuntimeContext currentContext() {
		RuntimeContext context = CONTEXT_LOCAL.get();
		return context;
	}

	public static void exitContext() {
		CONTEXT_LOCAL.set(null);
	}

	public ExecutionContext currentExecutionContext() {
		return callStack.peek();
	}

	public void exit() {
		callStack.pop();
	}

	public EnvironmentRecords getVariables() {
		// TODO Auto-generated method stub
		return variableEnvironment.getEnvironmentRecords();
	}

}

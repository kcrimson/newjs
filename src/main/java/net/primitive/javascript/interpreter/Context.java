package net.primitive.javascript.interpreter;

import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ast.Program;
import net.primitive.javascript.core.ast.Statement;

public class Context {

	private final FastStack<StatementFrame> callStack = new FastStack<StatementFrame>();

	private final FastStack<Scriptable> scopeStack = new FastStack<Scriptable>();

	private final ExpressionVisitorImpl expressionVisitor = new ExpressionVisitorImpl(
			this);

	private final StatementVisitorImpl statementVisitor = new StatementVisitorImpl(
			this);

	private static final ThreadLocal<Context> CONTEXT_LOCAL = new ThreadLocal<Context>();

	public static Context currentContext() {
		Context context = CONTEXT_LOCAL.get();
		return context;
	}

	public void enter(Statement statement) {
		StatementFrame frame = new StatementFrame();
		frame.setStatement(statement);
		callStack.push(frame);
	}

	public void exitReturn(Object result) {
		exit();
		StatementFrame frame = new StatementFrame();
		frame.setReturnValue(result);
		callStack.push(frame);
	}

	public Object currentReturnValue() {
		return ((StatementFrame) callStack.peek()).getReturnValue();
	}

	public void exit() {
		callStack.pop();
	}

	public void enter(Scriptable scope) {
		scopeStack.push(scope);
	}

	public Scriptable currentScope() {
		return scopeStack.peek();
	}

	public void exitScope() {
		scopeStack.pop();
	}

	public void enter(Program result) {
		callStack.push(new StatementFrame());
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

	public static void exitContext() {
		CONTEXT_LOCAL.set(null);
	}

	public static Context enterContext() {
		Context context = new Context();
		CONTEXT_LOCAL.set(context);
		return context;
	}
}

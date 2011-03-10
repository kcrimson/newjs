package net.primitive.javascript.interpreter;

import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;
import net.primitive.javascript.core.ast.CatchClause;
import net.primitive.javascript.core.ast.Program;
import net.primitive.javascript.core.ast.Statement;
import net.primitive.javascript.core.ast.TryStatement;

public class ExecutionContext {

	private static final ThreadLocal<ExecutionContext> CONTEXT_LOCAL = new ThreadLocal<ExecutionContext>();

	private final FastStack<StatementFrame> callStack = new FastStack<StatementFrame>();

	private final FastStack<Scriptable> scopeStack = new FastStack<Scriptable>();

	private final FastStack<Scriptable> thisStack = new FastStack<Scriptable>();

	private final ExpressionVisitorImpl expressionVisitor = new ExpressionVisitorImpl(
			this);

	private final StatementVisitorImpl statementVisitor = new StatementVisitorImpl(
			this);

	/**
	 * Enter statement execution
	 * 
	 * @param statement
	 */
	public void enter(Statement statement) {
		// System.out.println(statement);
		StatementFrame frame = new StatementFrame();
		frame.setStatement(statement);
		frame.setScope(currentScope());
		callStack.push(frame);
	}

	/**
	 * Exit with return value
	 * 
	 * @param result
	 */
	public void exitReturn(Object result) {
		exitStatement();
		StatementFrame frame = new StatementFrame();
		frame.setReturnValue(result);
		callStack.push(frame);
	}

	public Object currentReturnValue() {
		return ((StatementFrame) callStack.peek()).getReturnValue();
	}

	public void exitStatement() {
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

	public void enter(Program program) {
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

	public static ExecutionContext enterContext() {
		ExecutionContext context = new ExecutionContext();
		CONTEXT_LOCAL.set(context);
		return context;
	}

	public static ExecutionContext currentContext() {
		ExecutionContext context = CONTEXT_LOCAL.get();
		return context;
	}

	public static void exitContext() {
		CONTEXT_LOCAL.set(null);
	}

	public void enter(Scriptable scope, Scriptable thisObj) {
		enter(scope);
		thisStack.push(thisObj);
	}

	public void exitCall() {
		exitScope();
		thisStack.pop();
	}

	public Scriptable currentThis() {
		return thisStack.peek();
	}

	public void handleException(Object exceptionObject) {
		StatementFrame frame;
		// traverse up the statement stack
		while ((frame = callStack.pop()) != null) {
			Statement statement = frame.getStatement();
			if (statement instanceof TryStatement) {
				// push new scope on stack wi
				CatchClause catchStatement = (CatchClause) ((TryStatement) statement)
						.getCatchStatement();
				ScriptableObject scope = new ScriptableObject();
				scope.setParentScope(frame.getScope());
				scope.put(catchStatement.getIdentifier(), exceptionObject);
				enter(scope);
				catchStatement.accept(getStatementVisitor());
				exitScope();
				break;
			}
		}
	}

}

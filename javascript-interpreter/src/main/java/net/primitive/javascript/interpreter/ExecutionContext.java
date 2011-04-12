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

	private final FastStack<Scriptable> thisStack = new FastStack<Scriptable>();

	private final ExpressionVisitorImpl expressionVisitor = new ExpressionVisitorImpl(
			this);

	private final StatementVisitorImpl statementVisitor = new StatementVisitorImpl(
			this);

//	/**
//	 * Enter statement execution
//	 * 
//	 * @param statement
//	 */
//	public void enter(Statement statement) {
//		System.out.println("enter statement: " + this + "@" + statement);
//		StatementFrame frame = new StatementFrame();
//		frame.setStatement(statement);
//		frame.setScope(currentStatementScope());
//		callStack.push(frame);
//	}

	public void enter(Statement statement, Scriptable scope) {
		System.out.println("enter statement: " + this + "@" + statement);
		StatementFrame frame = new StatementFrame();
		frame.setStatement(statement);
		frame.setScope(scope);
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

	public Object returnValue() {
		return ((StatementFrame) callStack.peek()).getReturnValue();
	}

	public void exitStatement() {
		StatementFrame statementFrame = callStack.pop();
		System.out.println("exit statement: " + this + "@"
				+ statementFrame.getStatement());
	}

	public Scriptable currentStatementScope() {
		StatementFrame currentFrame = callStack.peek();
		return currentFrame.getScope();
	}

	public Scriptable currentScope() {
		return thisStack.peek();
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
				// enter(scope);
				catchStatement.accept(getStatementVisitor());
				// exitScope();
				break;
			}
		}
	}

	public Statement currentStatement() {
		StatementFrame frame = callStack.peek();
		return frame != null ? frame.getStatement() : null;
	}

	public void enter(Scriptable value) {
		thisStack.push(value);
	}

	public void exitScope() {
		thisStack.pop();
	}

}

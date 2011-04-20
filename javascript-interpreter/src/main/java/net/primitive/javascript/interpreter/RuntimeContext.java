package net.primitive.javascript.interpreter;

import static net.primitive.javascript.interpreter.LexicalEnvironment.newObjectEnvironment;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ast.CatchClause;
import net.primitive.javascript.core.ast.Statement;
import net.primitive.javascript.core.ast.TryStatement;
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
		this.lexicalEnvironment = globalEnvironment;// newDeclarativeEnvironment(globalEnvironment);
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
	 * Enters new execution context for function call
	 * 
	 * @param statement
	 * @param lexEnv
	 * @param thisObj
	 * @return
	 */
	public ExecutionContext enter(Statement statement,
			LexicalEnvironment lexEnv, Scriptable thisObj) {
		final ExecutionContext newContext = new ExecutionContext(lexEnv,
				lexEnv, thisObj, statement);
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

	public boolean exit() {
		ExecutionContext current = callStack.peek();

		Completion completion = current.getCompletion();
		CompletionType completionType = completion.getType();
		if (CompletionType.Normal.equals(completionType)
				|| CompletionType.Return.equals(completionType)) {
			callStack.pop();
			if (!callStack.isEmpty()) {
				// rewrite return completion to previous statement on stack
				ExecutionContext previous = callStack.peek();
				if (CompletionType.Return.equals(completionType)) {
					previous.getCompletion().setValue(completion.getValue());
				}
			}
			return CompletionType.Normal.equals(completionType);
		}

		if (CompletionType.Throw.equals(completionType)) {
			Statement statement = current.getStatement();
			if (TryStatement.class.equals(statement.getClass())) {
				TryStatement tryStatement = (TryStatement) statement;
				CatchClause catchStatement = (CatchClause) tryStatement
						.getCatchStatement();
				if (catchStatement != null) {
					LexicalEnvironment newDeclarativeEnvironment = LexicalEnvironment
							.newDeclarativeEnvironment(current
									.getLexicalEnvironment());

					Reference mutableBinding = newDeclarativeEnvironment.getEnvironmentRecords()
							.createMutableBinding(
									catchStatement.getIdentifier(), false);
					Reference.putValue(mutableBinding, completion.getValue());

					enter(catchStatement,newDeclarativeEnvironment,current.getThisBinding());
					catchStatement.accept(statementVisitor);
					boolean exitStatus = exit();
					// callStack.pop();
					current.normalCompletion();
					return exitStatus;
				}
			} else if (!callStack.isEmpty()) {
				callStack.pop();
				ExecutionContext previous = callStack.peek();
				previous.throwException(completion.getValue());
				return false;
			}
		}

		return false;
	}

//	private void enter(CatchClause catchStatement,
//			LexicalEnvironment newDeclarativeEnvironment) {
//		final ExecutionContext newContext = new ExecutionContext(newDeclarativeEnvironment,
//				newDeclarativeEnvironment, thisObj, catchStatement);
//		callStack.push(newContext);
//		return newContext;
//		
//	}

	public EnvironmentRecords getVariables() {
		return variableEnvironment.getEnvironmentRecords();
	}

}

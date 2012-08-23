/**
 * Copyright (C) 2012 Primitive Team <jpalka@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.primitive.javascript.interpreter;

import static net.primitive.javascript.interpreter.LexicalEnvironment.newObjectEnvironment;
import net.primitive.javascript.core.Reference;
import net.primitive.javascript.core.Scope;
import net.primitive.javascript.core.ScopeBindings;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.Undefined;
import net.primitive.javascript.core.ast.CatchClause;
import net.primitive.javascript.core.ast.DoWhileStatement;
import net.primitive.javascript.core.ast.ForStatement;
import net.primitive.javascript.core.ast.Statement;
import net.primitive.javascript.core.ast.TryStatement;
import net.primitive.javascript.core.ast.WhileStatement;
import net.primitive.javascript.core.natives.StandardObjects;
import net.primitive.javascript.interpreter.utils.FastStack;

/**
 * Represents ECMAScript interpreter context in which script is executed. It
 * holds handles to global (top-level) {@link Scriptable} object, standard
 * objects, variable and lexical scope. It is bound to execution thread by
 * calling {@link RuntimeContext#enterContext(StandardObjects, Scriptable)},
 * which later is aquired by calling {@link RuntimeContext#currentContext()}.
 * 
 * <blockquote> <strong>WARNING</strong><br/>
 * At the moment there is no way to share same RuntimeContext between multiple
 * threads, as this implementation is not thread-safe at the moment.
 * </blockquote>
 * 
 * @author jpalka@gmail.com
 * 
 */
public final class RuntimeContext {

	private static final ThreadLocal<RuntimeContext> CONTEXT_LOCAL = new ThreadLocal<RuntimeContext>();

	private final FastStack<StatementExecutionContext> callStack;

	private final ExpressionVisitorImpl expressionVisitor = new ExpressionVisitorImpl(this);

	private final StatementVisitorImpl statementVisitor = new StatementVisitorImpl(this);

	private final Scriptable globalObject;

	private final Scope globalEnvironment;

	private final Scope variableEnvironment;

	private Scope lexicalEnvironment;

	private final StandardObjects standardObjects;

	private RuntimeContext(final StandardObjects standardObjects, final Scriptable globalObject) {
		this.standardObjects = standardObjects;
		this.globalObject = globalObject;
		this.globalEnvironment = newObjectEnvironment(standardObjects, globalObject);
		this.lexicalEnvironment = globalEnvironment;
		this.variableEnvironment = lexicalEnvironment;
		callStack = new FastStack<StatementExecutionContext>();
	}
	
	/**
	 * TEST only
	 * @param globalObject
	 * @param globalEnvironment
	 * @param variableEnvironment
	 * @param lexicalEnvironment
	 * @param standardObjects
	 */
	RuntimeContext(FastStack<StatementExecutionContext> callStack) {
	    super();
		this.callStack = callStack;
		globalObject = null;
		globalEnvironment = null;
		variableEnvironment = null;
		lexicalEnvironment = null;
		standardObjects = null;
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

	/**
	 * Used by {@link Interpreter} to push next statement on the call stack.
	 * <blockquote><strong>Internal use only.</strong></blockquote>
	 * 
	 * @param statement
	 * @return
	 */
	public StatementExecutionContext enter(Statement statement) {
		Scope varEnv;
		Scope lexEnv;
		Scriptable thisObj;
		if (callStack.isEmpty()) {
			varEnv = variableEnvironment;
			lexEnv = lexicalEnvironment;
			thisObj = globalObject;
		} else {
			StatementExecutionContext currentContext = callStack.peek();
			lexEnv = currentContext.getLexicalEnvironment();
			varEnv = currentContext.getVariableEnvironment();
			thisObj = currentContext.getThisBinding();
		}

		final StatementExecutionContext newContext = new StatementExecutionContext(lexEnv, varEnv, thisObj, statement, new Completion(CompletionType.Normal, Undefined.Value, null));
		callStack.push(newContext);
		return newContext;
	}

	/**
	 * Enters new execution context for function call
	 * <blockquote><strong>Internal use only.</strong></blockquote>
	 * 
	 * @param statement
	 * @param lexEnv
	 * @param thisObj
	 * @return
	 */
	public StatementExecutionContext enter(Statement statement, Scope lexEnv, Scriptable thisObj) {
		final StatementExecutionContext newContext = new StatementExecutionContext(lexEnv, lexEnv, thisObj, statement, new Completion(CompletionType.Normal, Undefined.Value, null));
		callStack.push(newContext);
		return newContext;
	}

	/**
	 * This method creates new runtime context for script execution and binds it
	 * to current thread.
	 * 
	 * @param globalObject
	 *            instance of global object
	 * 
	 * @return
	 */
	public static RuntimeContext enterContext(StandardObjects standardObjects, Scriptable globalObject) {
		RuntimeContext context = new RuntimeContext(standardObjects, globalObject);
		CONTEXT_LOCAL.set(context);
		return context;
	}

	/**
	 * Returns context bound to current thread,
	 * 
	 * @return
	 * @throws RuntimeException
	 *             when there is no context bound to current thread
	 */
	public static RuntimeContext currentContext() {
		RuntimeContext context = CONTEXT_LOCAL.get();

		if (context != null) {
			return context;
		}

		throw new RuntimeException("no RuntimeContext bound to current thread");
	}

	/**
	 * Unbinds context from current thread. Once this methods is called you will
	 * be not able to execute scripts in current thread.
	 */
	public static void exitContext() {
		CONTEXT_LOCAL.set(null);
	}

	public StatementExecutionContext currentExecutionContext() {
		return callStack.peek();
	}

	/**
	 * Removes statement from call stack. 
	 * <blockquote><strong>Internal use only.</strong></blockquote>
	 * 
	 * @return
	 */
	public boolean exit() {
		StatementExecutionContext current = callStack.peek();

		Completion completion = current.getCompletion();
		CompletionType completionType = completion.getType();

		if (CompletionType.Normal.equals(completionType) || CompletionType.Return.equals(completionType)) {
			callStack.pop();
			if (!callStack.isEmpty()) {
				// rewrite return completion to previous statement on stack
				StatementExecutionContext previous = callStack.peek();
				previous.getCompletion().setValue(completion.getValue());
			}
			return CompletionType.Normal.equals(completionType);
		}

		if (CompletionType.Throw.equals(completionType)) {
			Statement statement = current.getStatement();
			// if this is TryStatement handle exception with catch and finally
			if (TryStatement.class.equals(statement.getClass())) {
				TryStatement tryStatement = (TryStatement) statement;
				CatchClause catchStatement = (CatchClause) tryStatement.getCatchStatement();
				if (catchStatement != null) {
					Scope newDeclarativeEnvironment = LexicalEnvironment.newDeclarativeEnvironment(current.getLexicalEnvironment());

					Reference mutableBinding = newDeclarativeEnvironment.getBindings().createMutableBinding(catchStatement.getIdentifier(), false);
					Reference.putValue(mutableBinding, completion.getValue());

					enter(catchStatement, newDeclarativeEnvironment, current.getThisBinding());
					catchStatement.accept(statementVisitor);
					boolean exitStatus = exit();
					callStack.pop();
					current.normalCompletion();
					return exitStatus;
				}
			} else if (!callStack.isEmpty()) {
				// fold exception to previous statement
				callStack.pop();
				StatementExecutionContext previous = callStack.peek();
				previous.throwException(completion.getValue());
				return false;
			}
		}

		if (CompletionType.Break.equals(completionType)) {
			Statement currentStatement = current.getStatement();
			if (isIterationStatement(currentStatement)) {
				callStack.pop();
				return true;
			} else {
				callStack.pop();
				final StatementExecutionContext previous = callStack.peek();
				previous.breakStatement("");
				return false;
			}
		}

		return false;
	}

	private static boolean isIterationStatement(Statement currentStatement) {
		Class<? extends Statement> clazz = currentStatement.getClass();
		return WhileStatement.class.equals(clazz) || DoWhileStatement.class.equals(clazz) || ForStatement.class.equals(clazz);
	}

	public ScopeBindings getVariables() {
		return variableEnvironment.getBindings();
	}

	public Scriptable getGlobalObject() {
		return globalObject;
	}

	public Scriptable newArray() {
		return standardObjects.newArray();
	}

	public Scriptable newObject() {
		return standardObjects.newObject();
	}

	public Scriptable getObjectPrototype() {
		return standardObjects.getObjectPrototype();
	}

	public StandardObjects getStandardObjects() {
		return standardObjects;
	}

}

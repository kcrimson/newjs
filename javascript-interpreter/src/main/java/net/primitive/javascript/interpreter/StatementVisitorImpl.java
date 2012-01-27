/**
 * Copyright (C) 2011 Primitive Team <jpalka@gmail.com>
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

import static java.util.Collections.emptyList;
import static net.primitive.javascript.core.Convertions.toBoolean;
import static net.primitive.javascript.core.Convertions.toObject;
import static net.primitive.javascript.core.Reference.getValue;
import static net.primitive.javascript.core.Reference.putValue;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import net.primitive.javascript.core.Convertions;
import net.primitive.javascript.core.Reference;
import net.primitive.javascript.core.Scope;
import net.primitive.javascript.core.ScopeBindings;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.Undefined;
import net.primitive.javascript.core.ast.AstNode;
import net.primitive.javascript.core.ast.AstNodeList;
import net.primitive.javascript.core.ast.BreakStatement;
import net.primitive.javascript.core.ast.CatchClause;
import net.primitive.javascript.core.ast.DoWhileStatement;
import net.primitive.javascript.core.ast.Expression;
import net.primitive.javascript.core.ast.ExpressionStatement;
import net.primitive.javascript.core.ast.ForInStatement;
import net.primitive.javascript.core.ast.ForStatement;
import net.primitive.javascript.core.ast.FunctionDeclaration;
import net.primitive.javascript.core.ast.IfStatement;
import net.primitive.javascript.core.ast.ReturnStatement;
import net.primitive.javascript.core.ast.Statement;
import net.primitive.javascript.core.ast.StatementVisitor;
import net.primitive.javascript.core.ast.ThrowStatement;
import net.primitive.javascript.core.ast.TryStatement;
import net.primitive.javascript.core.ast.VariableDeclaration;
import net.primitive.javascript.core.ast.WhileStatement;

public class StatementVisitorImpl implements StatementVisitor {

	private final RuntimeContext runtimeContext;
	private final ExpressionVisitorImpl expressionVisitor;

	protected StatementVisitorImpl(RuntimeContext context) {
		this.runtimeContext = context;
		this.expressionVisitor = context.getExpressionVisitor();
	}

	@Override
	public void visitVariableDeclaration(VariableDeclaration variableDeclaration) {
		StatementExecutionContext executionContext = runtimeContext
				.currentExecutionContext();
		Scope env = executionContext.getVariableEnvironment();
		ScopeBindings envrec = env.getBindings();
		String varname = variableDeclaration.getVariableName();
		if (envrec.hasBinding(varname)) {

		} else {

			Reference mutableBinding = envrec.createMutableBinding(varname,
					false);

			Expression expression = variableDeclaration.getExpression();
			expression.accept(expressionVisitor);
			Object value = expressionVisitor.getResult();

			mutableBinding.setValue(getValue(value));

		}
	}

	@Override
	public void visitFunctionDeclaration(FunctionDeclaration functionDeclaration) {
		StatementExecutionContext executionContext = runtimeContext
				.currentExecutionContext();
		Scope varenv = executionContext.getVariableEnvironment();
		String functionName = functionDeclaration.getFunctionName();
		JSNativeFunction jsFunction = new JSNativeFunction(varenv,
				functionName, functionDeclaration.getParameterList(),
				functionDeclaration.getFunctionBody());
		ScopeBindings envrecs = varenv.getBindings();
		Reference mutableBinding = envrecs.createMutableBinding(functionName,
				false);
		mutableBinding.setValue(jsFunction);
	}

	@Override
	public void visitExpressionStatement(ExpressionStatement expressionStatement) {
		expressionStatement.getExpression().accept(
				runtimeContext.getExpressionVisitor());
	}

	@Override
	public void visitIfStatement(IfStatement ifStatement) {
		Expression expression = ifStatement.getExpression();
		expression.accept(expressionVisitor);
		boolean expressionResult = toBoolean(Reference
				.getValue(expressionVisitor.getResult()));

		if (expressionResult) {

			List<AstNode> astNodes = ifStatement.getIfStatement().getAstNodes();
			for (AstNode astNode : astNodes) {
				if (!executeStatement((Statement) astNode)) {
					return;
				}
			}
		} else if (ifStatement.getElseStatement() != null) {
			List<AstNode> astNodes = ifStatement.getElseStatement()
					.getAstNodes();
			for (AstNode astNode : astNodes) {
				if (!executeStatement((Statement) astNode)) {
					return;
				}
			}
		}
	}

	private boolean executeStatement(Statement statement) {
		runtimeContext.enter(statement);
		try {
			statement.accept(this);
		} catch (Exception e) {
			runtimeContext.currentExecutionContext().throwException(e);
		}
		return runtimeContext.exit();
	}

	@Override
	public void visitWhileStatement(WhileStatement whileStatement) {
		Expression expression = whileStatement.getExpression();
		Statement[] statements = whileStatement.getStatements();
		int len = statements != null ? statements.length : 0;

		boolean continues = true;
		for (expression.accept(expressionVisitor); continues
				&& toBoolean(expressionVisitor.getResult()); expression
				.accept(expressionVisitor)) {

			for (int i = 0; i < len; i++) {
				Statement statement = statements[i];
				if (!executeStatement(statement)) {
					continues = false;
					break;
				}
			}
		}
	}

	@Override
	public void visitForStatement(ForStatement forStatement) {
		AstNode initializeExpression = forStatement.getInitializeExpression();

		if (initializeExpression instanceof Statement) {
			((Statement) initializeExpression).accept(this);
		} else if (initializeExpression instanceof AstNodeList) {
			List<AstNode> astNodes = ((AstNodeList) initializeExpression)
					.getAstNodes();
			for (AstNode astNode : astNodes) {
				executeStatement((Statement) astNode);
			}
		}

		Expression testExpression = forStatement.getTestExpression();
		Expression incrementExpression = forStatement.getIncrementExpression();
		AstNodeList statements = (AstNodeList) forStatement.getStatements();

		List<AstNode> astNodes = Collections.emptyList();
		if (statements != null) {
			astNodes = statements.getAstNodes();
		}

		for (;;) {
			if (testExpression != null) {
				testExpression.accept(expressionVisitor);
				if (!Convertions.toBoolean(expressionVisitor.getResult())) {
					return;
				}
			}

			for (AstNode astNode : astNodes) {
				if (!executeStatement((Statement) astNode)) {
					return;
				}
			}

			if (incrementExpression != null) {
				incrementExpression.accept(expressionVisitor);
			}
		}
	}

	@Override
	public void visitReturnStatement(ReturnStatement returnStatement) {
		Expression expression = returnStatement.getExpression();
		expression.accept(expressionVisitor);
		runtimeContext.currentExecutionContext().returnValue(
				expressionVisitor.getResult());
	}

	@Override
	public void visitThrowStatement(ThrowStatement throwStatement) {
		StatementExecutionContext executionContext = runtimeContext
				.currentExecutionContext();
		Expression expression = throwStatement.getExpression();
		expression.accept(expressionVisitor);
		Object exceptionObject = expressionVisitor.getResult();
		executionContext.throwException(exceptionObject);
	}

	@Override
	public void visitCatchClause(CatchClause catchClause) {
		Statement[] astNodes = catchClause.getStatements();
		for (AstNode astNode : astNodes) {
			Statement statement = (Statement) astNode;
			if (!executeStatement(statement)) {
				return;
			}

		}
	}

	@Override
	public void visitTryStatement(TryStatement tryStatement) {
		AstNodeList blockStatement = tryStatement.getBlockStatement();
		List<AstNode> astNodes = blockStatement.getAstNodes();
		for (AstNode astNode : astNodes) {
			Statement statement = (Statement) astNode;
			if (!executeStatement(statement)) {
				break;
			}
		}
	}

	@Override
	public void visitBreakStatement(BreakStatement breakStatement) {
		StatementExecutionContext executionContext = runtimeContext
				.currentExecutionContext();
		executionContext.breakStatement(breakStatement.getIdentifier());
	}

	@Override
	public void visitDoWhileStatement(DoWhileStatement doWhileStatement) {
		Expression expression = doWhileStatement.getExpression();
		List<AstNode> astNodes = doWhileStatement.getStatements().getAstNodes();

		boolean iterating = true;
		for (;;) {
			if (iterating) {
				for (AstNode astNode : astNodes) {
					Statement statement = (Statement) astNode;
					iterating = executeStatement(statement);
					if (!iterating) {
						break;
					}
				}
			}
			if (!iterating) {
				break;
			}
			expression.accept(expressionVisitor);
			iterating = Convertions.toBoolean(expressionVisitor.getResult());
		}
	}

	@Override
	public void visitForInStatement(ForInStatement forInStatement) {
		// TODO this needs to be carefully reviewed, as it look like
		// VariableDeclaration
		// ((Statement)forInStatement.getInitializerExpression()).accept(this);
		String varName = ((VariableDeclaration) forInStatement
				.getInitializerExpression()).getVariableName();

		forInStatement.getExpression().accept(expressionVisitor);
		Object exprRef = expressionVisitor.getResult();
		Object exprValue = Reference.getValue(exprRef);

		if (exprValue == null || Undefined.Value.equals(exprValue)) {
			return;
		}

		Reference varRef = runtimeContext.getVariables().getBinding(varName);
		Scriptable obj = toObject(exprValue);
		Enumeration<String> enumeration = obj.enumeration();
		List<AstNode> astNodes = emptyList();
		AstNodeList statements = (AstNodeList) forInStatement.getStatement();
		if (statements != null) {
			astNodes = statements.getAstNodes();
		}
		while (enumeration.hasMoreElements()) {
			putValue(varRef, enumeration.nextElement());

			for (AstNode astNode : astNodes) {
				if (!executeStatement((Statement) astNode)) {
					return;
				}
			}
		}

	}
}

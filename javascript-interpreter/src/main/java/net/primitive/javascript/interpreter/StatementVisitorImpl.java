package net.primitive.javascript.interpreter;

import static net.primitive.javascript.core.Convertions.toBoolean;

import java.util.List;

import net.primitive.javascript.core.PropertyDescriptor;
import net.primitive.javascript.core.ast.AstNode;
import net.primitive.javascript.core.ast.AstNodeList;
import net.primitive.javascript.core.ast.BreakStatement;
import net.primitive.javascript.core.ast.CatchClause;
import net.primitive.javascript.core.ast.Expression;
import net.primitive.javascript.core.ast.ExpressionStatement;
import net.primitive.javascript.core.ast.ForStatement;
import net.primitive.javascript.core.ast.FunctionDeclaration;
import net.primitive.javascript.core.ast.IfStatement;
import net.primitive.javascript.core.ast.ReturnStatement;
import net.primitive.javascript.core.ast.Statement;
import net.primitive.javascript.core.ast.ThrowStatement;
import net.primitive.javascript.core.ast.TryStatement;
import net.primitive.javascript.core.ast.VariableDeclaration;
import net.primitive.javascript.core.ast.WhileStatement;
import net.primitive.javascript.core.visitors.StatementVisitor;

public class StatementVisitorImpl implements StatementVisitor {

	private final RuntimeContext context;

	protected StatementVisitorImpl(RuntimeContext context) {
		this.context = context;
	}

	@Override
	public void visitVariableDeclaration(VariableDeclaration variableDeclaration) {
		ExecutionContext executionContext = context.currentExecutionContext();
		LexicalEnvironment env = executionContext.getVariableEnvironment();
		EnvironmentRecords environmentRecords = env.getEnvironmentRecords();
		if (environmentRecords
				.hasBinding(variableDeclaration.getVariableName())) {

		} else {
			environmentRecords.createMutableBinding(
					variableDeclaration.getVariableName(), false);
		}
		Expression expression = variableDeclaration.getExpression();
		expression.accept(context.getExpressionVisitor());
		Object result = context.getExpressionVisitor().getResult();
		environmentRecords.setMutableBinding(
				variableDeclaration.getVariableName(),
				Reference.getValue(result));
	}

	@Override
	public void visitFunctionDeclaration(FunctionDeclaration functionDeclaration) {
		JSNativeFunction jsFunction = new JSNativeFunction(
				functionDeclaration.getFunctionName(),
				functionDeclaration.getParameterList(),
				functionDeclaration.getFunctionBody());
		EnvironmentRecords environmentRecords = context
				.currentExecutionContext().getVariableEnvironment()
				.getEnvironmentRecords();
		environmentRecords.createMutableBinding(
				functionDeclaration.getFunctionName(), false);
		environmentRecords.setMutableBinding(
				functionDeclaration.getFunctionName(), jsFunction);

		// lexigetScope().put(functionDeclaration.getFunctionName(),
		// jsFunction);
	}

	@Override
	public void visitExpressionStatement(ExpressionStatement expressionStatement) {
		// context.enter(expressionStatement);
		expressionStatement.getExpression().accept(
				context.getExpressionVisitor());
		// context.exitStatement();
	}

	@Override
	public void visitIfStatement(IfStatement ifStatement) {
		Expression expression = ifStatement.getExpression();
		expression.accept(context.getExpressionVisitor());
		boolean expressionResult = toBoolean(Reference.getValue(context
				.getExpressionVisitor().getResult()));

		if (expressionResult) {

			List<AstNode> astNodes = ifStatement.getIfStatement().getAstNodes();
			for (AstNode astNode : astNodes) {
				context.enter((Statement) astNode);
				((Statement) astNode).accept(this);
				context.exit();
			}
		} else if (ifStatement.getElseStatement() != null) {
			List<AstNode> astNodes = ifStatement.getElseStatement()
					.getAstNodes();
			for (AstNode astNode : astNodes) {
				context.enter((Statement) astNode);
				((Statement) astNode).accept(this);
				context.exit();
			}
		}
	}

	@Override
	public void visitWhileStatement(WhileStatement whileStatement) {
		Expression expression = whileStatement.getExpression();
		AstNodeList statements = whileStatement.getStatements();

		for (expression.accept(context.getExpressionVisitor()); toBoolean(context
				.getExpressionVisitor().getResult()); expression.accept(context
				.getExpressionVisitor())) {

			for (AstNode astNode : statements.getAstNodes()) {
				context.enter((Statement) astNode);
				((Statement) astNode).accept(this);
				context.exit();
			}
		}
	}

	@Override
	public void visitForStatement(ForStatement forStatement) {
		throw new UnsupportedOperationException("visitForStatement");
	}

	@Override
	public void visitReturnStatement(ReturnStatement returnStatement) {
		Expression expression = returnStatement.getExpression();
		expression.accept(context.getExpressionVisitor());
	}

	@Override
	public void visitThrowStatement(ThrowStatement throwStatement) {
		Expression expression = throwStatement.getExpression();
		expression.accept(context.getExpressionVisitor());
		Object exceptionObject = context.getExpressionVisitor().getResult();
		// context.handleException(exceptionObject);
	}

	@Override
	public void visitCatchClause(CatchClause catchClause) {
		// context.enter(catchClause);
		// catchClause.getStatement().accept(context.getStatementVisitor());
		// context.exitStatement();
	}

	@Override
	public void visitTryStatement(TryStatement tryStatement) {
		// context.enter(tryStatement);
		// Statement blockStatement = tryStatement.getBlockStatement();
		// blockStatement.accept(context.getStatementVisitor());
		// context.exitStatement();
	}

	@Override
	public void visitBreakStatement(BreakStatement breakStatement) {
		throw new UnsupportedOperationException("visitBreakStatement");
	}

}

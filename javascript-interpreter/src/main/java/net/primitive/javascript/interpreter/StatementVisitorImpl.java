package net.primitive.javascript.interpreter;

import static net.primitive.javascript.core.Convertions.toBoolean;

import java.util.List;

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

	private final RuntimeContext runtimeContext;

	protected StatementVisitorImpl(RuntimeContext context) {
		this.runtimeContext = context;
	}

	@Override
	public void visitVariableDeclaration(VariableDeclaration variableDeclaration) {
		ExecutionContext executionContext = runtimeContext
				.currentExecutionContext();
		LexicalEnvironment env = executionContext.getVariableEnvironment();
		EnvironmentRecords envrec = env.getEnvironmentRecords();
		String varname = variableDeclaration.getVariableName();
		if (envrec.hasBinding(varname)) {

		} else {

			Reference mutableBinding = envrec.createMutableBinding(varname,
					false);

			Expression expression = variableDeclaration.getExpression();
			ExpressionVisitorImpl expressionVisitor = runtimeContext
					.getExpressionVisitor();
			expression.accept(expressionVisitor);
			Object value = expressionVisitor.getResult();

			mutableBinding.setValue(Reference.getValue(value));

		}
	}

	@Override
	public void visitFunctionDeclaration(FunctionDeclaration functionDeclaration) {
		ExecutionContext executionContext = runtimeContext
				.currentExecutionContext();
		String functionName = functionDeclaration.getFunctionName();
		JSNativeFunction jsFunction = new JSNativeFunction(functionName,
				functionDeclaration.getParameterList(),
				functionDeclaration.getFunctionBody());
		EnvironmentRecords envrecs = executionContext.getVariableEnvironment()
				.getEnvironmentRecords();
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
		ExpressionVisitorImpl expressionVisitor = runtimeContext
				.getExpressionVisitor();
		expression.accept(expressionVisitor);
		boolean expressionResult = toBoolean(Reference
				.getValue(expressionVisitor.getResult()));

		if (expressionResult) {

			List<AstNode> astNodes = ifStatement.getIfStatement().getAstNodes();
			for (AstNode astNode : astNodes) {
				runtimeContext.enter((Statement) astNode);
				((Statement) astNode).accept(this);
				runtimeContext.exit();
			}
		} else if (ifStatement.getElseStatement() != null) {
			List<AstNode> astNodes = ifStatement.getElseStatement()
					.getAstNodes();
			for (AstNode astNode : astNodes) {
				runtimeContext.enter((Statement) astNode);
				((Statement) astNode).accept(this);
				runtimeContext.exit();
			}
		}
	}

	@Override
	public void visitWhileStatement(WhileStatement whileStatement) {
		Expression expression = whileStatement.getExpression();
		AstNodeList statements = whileStatement.getStatements();

		for (expression.accept(runtimeContext.getExpressionVisitor()); toBoolean(runtimeContext
				.getExpressionVisitor().getResult()); expression
				.accept(runtimeContext.getExpressionVisitor())) {

			for (AstNode astNode : statements.getAstNodes()) {
				runtimeContext.enter((Statement) astNode);
				((Statement) astNode).accept(this);
				runtimeContext.exit();
			}
		}
	}

	@Override
	public void visitForStatement(ForStatement forStatement) {
		throw new UnsupportedOperationException("visitForStatement");
	}

	@Override
	public void visitReturnStatement(ReturnStatement returnStatement) {
		ExpressionVisitorImpl expressionVisitor = runtimeContext
				.getExpressionVisitor();
		Expression expression = returnStatement.getExpression();
		expression.accept(expressionVisitor);
		runtimeContext.currentExecutionContext().returnValue(
				expressionVisitor.getResult());
	}

	@Override
	public void visitThrowStatement(ThrowStatement throwStatement) {
		ExecutionContext executionContext = runtimeContext
				.currentExecutionContext();
		Expression expression = throwStatement.getExpression();
		ExpressionVisitorImpl expressionVisitor = runtimeContext
				.getExpressionVisitor();
		expression.accept(expressionVisitor);
		Object exceptionObject = expressionVisitor.getResult();
		executionContext.throwException(exceptionObject);
	}

	@Override
	public void visitCatchClause(CatchClause catchClause) {
		List<AstNode> astNodes = catchClause.getStatement().getAstNodes();
		for (AstNode astNode : astNodes) {
			Statement statement = (Statement) astNode;
			runtimeContext.enter(statement);
			statement.accept(this);
			runtimeContext.exit();
		}
	}

	@Override
	public void visitTryStatement(TryStatement tryStatement) {
		AstNodeList blockStatement = tryStatement.getBlockStatement();
		List<AstNode> astNodes = blockStatement.getAstNodes();
		for (AstNode astNode : astNodes) {
			Statement statement = (Statement) astNode;
			statement.accept(this);
			if (!runtimeContext.exit()) {
				break;
			}
		}
	}

	@Override
	public void visitBreakStatement(BreakStatement breakStatement) {
		throw new UnsupportedOperationException("visitBreakStatement");
	}

}

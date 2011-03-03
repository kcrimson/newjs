package net.primitive.javascript.interpreter;

import static net.primitive.javascript.core.Convertions.toBoolean;
import net.primitive.javascript.core.Convertions;
import net.primitive.javascript.core.Function;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.annotations.JSFunction;
import net.primitive.javascript.core.ast.Expression;
import net.primitive.javascript.core.ast.ExpressionStatement;
import net.primitive.javascript.core.ast.ForStatement;
import net.primitive.javascript.core.ast.FunctionDeclaration;
import net.primitive.javascript.core.ast.IfStatement;
import net.primitive.javascript.core.ast.ReturnStatement;
import net.primitive.javascript.core.ast.Statement;
import net.primitive.javascript.core.ast.StatementBlock;
import net.primitive.javascript.core.ast.VariableDeclaration;
import net.primitive.javascript.core.ast.WhileStatement;
import net.primitive.javascript.core.visitors.StatementVisitor;

public class StatementVisitorImpl extends AbstractSourceElementVisitor
		implements StatementVisitor {

	protected StatementVisitorImpl(Scriptable scope) {
		super(scope);
	}

	@Override
	public void visitVariableDeclaration(VariableDeclaration variableDeclaration) {
		Expression expression = variableDeclaration.getExpression();
		ExpressionVisitorImpl visitor = new ExpressionVisitorImpl(getScope());
		expression.accept(visitor);
		Object result = visitor.getResult();
		getScope().put(variableDeclaration.getVariableName(), getScope(),
				result);
	}

	@Override
	public void visitFunctionDeclaration(FunctionDeclaration functionDeclaration) {
		JSNativeFunction jsFunction = new JSNativeFunction(
				functionDeclaration.getFunctionName(),
				functionDeclaration.getParameterList(),
				functionDeclaration.getSourceElements());
		getScope().put(functionDeclaration.getFunctionName(), getScope(),
				jsFunction);
	}

	@Override
	public void visitExpressionStatement(ExpressionStatement expressionStatement) {
		ExpressionVisitorImpl visitor = new ExpressionVisitorImpl(getScope());
		expressionStatement.getExpression().accept(visitor);
		// result = visitor.getResult();
	}

	@Override
	public void visitIfStatement(IfStatement ifStatement) {
		Expression expression = ifStatement.getExpression();
		ExpressionVisitorImpl visitor = new ExpressionVisitorImpl(getScope());
		expression.accept(visitor);
		boolean expressionResult = Convertions.toBoolean(visitor.getResult());

		if (expressionResult) {
			ifStatement.getIfStatement().accept(this);
		} else if (ifStatement.getElseStatement() != null) {
			ifStatement.getElseStatement().accept(this);
		}
	}

	@Override
	public void visitStatementBlock(StatementBlock statementBlock) {
		for (Statement statement : statementBlock.getStatements()) {
			statement.accept(this);
		}
	}

	@Override
	public void visitWhileStatement(WhileStatement whileStatement) {
		Expression expression = whileStatement.getExpression();

		ExpressionVisitorImpl visitor = new ExpressionVisitorImpl(getScope());
		for (expression.accept(visitor); toBoolean(visitor.getResult()); expression
				.accept(visitor)) {
			whileStatement.getStatement().accept(this);
		}
	}

	@Override
	public void visitForStatement(ForStatement forStatement) {
		throw new UnsupportedOperationException("visitForStatement");
	}

	@Override
	public void visitReturnStatement(ReturnStatement returnStatement) {
		Expression expression = returnStatement.getExpression();
		ExpressionVisitorImpl visitor = new ExpressionVisitorImpl(getScope());
		expression.accept(visitor);

	}
}

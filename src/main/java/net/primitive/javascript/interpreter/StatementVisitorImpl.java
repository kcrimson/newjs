package net.primitive.javascript.interpreter;

import static net.primitive.javascript.core.Convertions.toBoolean;
import static net.primitive.javascript.interpreter.Context.currentContext;
import net.primitive.javascript.core.Scriptable;
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

public class StatementVisitorImpl implements StatementVisitor {

	private final Context context;

	protected StatementVisitorImpl(Context context) {
		this.context = context;
	}

	@Override
	public void visitVariableDeclaration(VariableDeclaration variableDeclaration) {
		currentContext().enter(variableDeclaration);
		Expression expression = variableDeclaration.getExpression();
		expression.accept(context.getExpressionVisitor());
		Object result = context.getExpressionVisitor().getResult();
		getScope().put(variableDeclaration.getVariableName(), getScope(),
				result);
		currentContext().exit();
	}

	@Override
	public void visitFunctionDeclaration(FunctionDeclaration functionDeclaration) {
		currentContext().enter(functionDeclaration);
		JSNativeFunction jsFunction = new JSNativeFunction(
				functionDeclaration.getFunctionName(),
				functionDeclaration.getParameterList(),
				functionDeclaration.getSourceElements());
		getScope().put(functionDeclaration.getFunctionName(), getScope(),
				jsFunction);
		currentContext().exit();
	}

	@Override
	public void visitExpressionStatement(ExpressionStatement expressionStatement) {
		currentContext().enter(expressionStatement);
		expressionStatement.getExpression().accept(
				context.getExpressionVisitor());
		currentContext().exit();
	}

	@Override
	public void visitIfStatement(IfStatement ifStatement) {
		currentContext().enter(ifStatement);
		Expression expression = ifStatement.getExpression();
		expression.accept(context.getExpressionVisitor());
		boolean expressionResult = toBoolean(context.getExpressionVisitor()
				.getResult());

		if (expressionResult) {
			ifStatement.getIfStatement().accept(this);
		} else if (ifStatement.getElseStatement() != null) {
			ifStatement.getElseStatement().accept(this);
		}
		currentContext().exit();
	}

	@Override
	public void visitStatementBlock(StatementBlock statementBlock) {
		currentContext().enter(statementBlock);
		Statement[] statements = statementBlock.getStatements();
		for (int i = 0; i < statements.length; i++) {
			statements[i].accept(this);
		}
		currentContext().exit();
	}

	@Override
	public void visitWhileStatement(WhileStatement whileStatement) {
		currentContext().enter(whileStatement);
		Expression expression = whileStatement.getExpression();

		for (expression.accept(context.getExpressionVisitor()); toBoolean(context
				.getExpressionVisitor().getResult()); expression.accept(context
				.getExpressionVisitor())) {
			whileStatement.getStatement().accept(this);
		}
		currentContext().exit();
	}

	@Override
	public void visitForStatement(ForStatement forStatement) {
		throw new UnsupportedOperationException("visitForStatement");
	}

	@Override
	public void visitReturnStatement(ReturnStatement returnStatement) {
		currentContext().enter(returnStatement);
		Expression expression = returnStatement.getExpression();
		expression.accept(context.getExpressionVisitor());
		currentContext().exitReturn(context.getExpressionVisitor().getResult());
	}

	@Override
	public Scriptable getScope() {
		return context.currentScope();
	}
}

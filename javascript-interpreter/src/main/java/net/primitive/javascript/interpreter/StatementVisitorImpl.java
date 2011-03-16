package net.primitive.javascript.interpreter;

import static net.primitive.javascript.core.Convertions.toBoolean;
import net.primitive.javascript.core.JavaScriptParser.statement_return;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObjectProperty;
import net.primitive.javascript.core.ast.CatchClause;
import net.primitive.javascript.core.ast.Expression;
import net.primitive.javascript.core.ast.ExpressionStatement;
import net.primitive.javascript.core.ast.ForStatement;
import net.primitive.javascript.core.ast.FunctionDeclaration;
import net.primitive.javascript.core.ast.IfStatement;
import net.primitive.javascript.core.ast.ReturnStatement;
import net.primitive.javascript.core.ast.Statement;
import net.primitive.javascript.core.ast.StatementBlock;
import net.primitive.javascript.core.ast.ThrowStatement;
import net.primitive.javascript.core.ast.TryStatement;
import net.primitive.javascript.core.ast.VariableDeclaration;
import net.primitive.javascript.core.ast.WhileStatement;
import net.primitive.javascript.core.visitors.StatementVisitor;

public class StatementVisitorImpl implements StatementVisitor {

	private final ExecutionContext context;

	protected StatementVisitorImpl(ExecutionContext context) {
		this.context = context;
	}

	@Override
	public void visitVariableDeclaration(VariableDeclaration variableDeclaration) {
		context.enter(variableDeclaration);
		Expression expression = variableDeclaration.getExpression();
		expression.accept(context.getExpressionVisitor());
		Object result = context.getExpressionVisitor().getResult();
		getScope().put(variableDeclaration.getVariableName(), result);
		context.exitStatement();
	}

	@Override
	public void visitFunctionDeclaration(FunctionDeclaration functionDeclaration) {
		JSNativeFunction jsFunction = new JSNativeFunction(
				functionDeclaration.getFunctionName(),
				functionDeclaration.getParameterList(),
				functionDeclaration.getSourceElements());
		getScope().put(functionDeclaration.getFunctionName(), jsFunction);
	}

	@Override
	public void visitExpressionStatement(ExpressionStatement expressionStatement) {
		context.enter(expressionStatement);
		expressionStatement.getExpression().accept(
				context.getExpressionVisitor());
		context.exitStatement();
	}

	@Override
	public void visitIfStatement(IfStatement ifStatement) {
		context.enter(ifStatement);
		Expression expression = ifStatement.getExpression();
		expression.accept(context.getExpressionVisitor());
		boolean expressionResult = toBoolean(context.getExpressionVisitor()
				.getResult());

		if (expressionResult) {
			ifStatement.getIfStatement().accept(this);
		} else if (ifStatement.getElseStatement() != null) {
			ifStatement.getElseStatement().accept(this);
		}
		context.exitStatement();
	}

	@Override
	public void visitStatementBlock(StatementBlock statementBlock) {
		context.enter(statementBlock);
		Statement[] statements = statementBlock.getStatements();
		for (int i = 0; i < statements.length; i++) {
			statements[i].accept(this);
		}
		context.exitStatement();
	}

	@Override
	public void visitWhileStatement(WhileStatement whileStatement) {
		context.enter(whileStatement);
		Expression expression = whileStatement.getExpression();

		for (expression.accept(context.getExpressionVisitor()); toBoolean(context
				.getExpressionVisitor().getResult()); expression.accept(context
				.getExpressionVisitor())) {
			whileStatement.getStatement().accept(this);
		}
		context.exitStatement();
	}

	@Override
	public void visitForStatement(ForStatement forStatement) {
		throw new UnsupportedOperationException("visitForStatement");
	}

	@Override
	public void visitReturnStatement(ReturnStatement returnStatement) {
		context.enter(returnStatement);
		Expression expression = returnStatement.getExpression();
		expression.accept(context.getExpressionVisitor());
		context.exitReturn(getValue(context.getExpressionVisitor().getResult()));
	}

	@Override
	public Scriptable getScope() {
		return context.currentScope();
	}

	private static Object getValue(Object object) {
		if (object instanceof ScriptableObjectProperty) {
			return ((ScriptableObjectProperty) object).getValue();
		}
		return object;
	}

	@Override
	public void visitThrowStatement(ThrowStatement throwStatement) {
		Expression expression = throwStatement.getExpression();
		expression.accept(context.getExpressionVisitor());
		Object exceptionObject = context.getExpressionVisitor().getResult();
		context.handleException(exceptionObject);
	}

	@Override
	public void visitCatchClause(CatchClause catchClause) {
		context.enter(catchClause);
		catchClause.getStatement().accept(context.getStatementVisitor());
		context.exitStatement();
	}

	@Override
	public void visitTryStatement(TryStatement tryStatement) {
		context.enter(tryStatement);
		Statement blockStatement = tryStatement.getBlockStatement();
		blockStatement.accept(context.getStatementVisitor());
		context.exitStatement();
	}

}

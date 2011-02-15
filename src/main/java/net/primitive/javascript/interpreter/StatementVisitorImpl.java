package net.primitive.javascript.interpreter;

import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ast.Expression;
import net.primitive.javascript.core.ast.FunctionDeclaration;
import net.primitive.javascript.core.ast.VariableDeclaration;
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

	}

}

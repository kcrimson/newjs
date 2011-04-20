package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.ExpressionVisitor;

public class NewExpression extends Expression {

	private final Expression expression;

	public NewExpression(Expression expression) {
		super();
		this.expression = expression;
	}

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitNewExpression(this);
	}

	public Expression getExpression() {
		return expression;
	}

}

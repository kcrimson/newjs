package net.primitive.javascript.core.ast;

import net.primitive.javascript.interpreter.ExpressionVisitorImpl;

public class UnaryExpression extends Expression {

	private final UnaryOperator operator;
	private final Expression expression;

	public UnaryExpression(UnaryOperator operator, Expression expression) {
		this.operator = operator;
		this.expression = expression;
	}

	@Override
	public void accept(ExpressionVisitorImpl visitor) {

	}

}

package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.ExpressionVisitor;

public class CallExpression extends Expression {

	private final Expression memberExpression;
	private final Expression arguments;

	public CallExpression(Expression memberExpression, Expression arguments) {
		this.memberExpression = memberExpression;
		this.arguments = arguments;
	}

	/**
	 * @return the memberExpression
	 */
	public Expression getMemberExpression() {
		return memberExpression;
	}

	/**
	 * @return the arguments
	 */
	public Expression getArguments() {
		return arguments;
	}

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitCallExpression(this);
	}

}

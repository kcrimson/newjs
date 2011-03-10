package net.primitive.javascript.core.ast;

import java.util.List;

import net.primitive.javascript.core.visitors.ExpressionVisitor;

public class MemberExpression extends Expression {

	private final Expression expression;
	private final List<Expression> expresionSuffixes;

	public MemberExpression(Expression expression,
			List<Expression> expresionSuffixes) {
		this.expression = expression;
		this.expresionSuffixes = expresionSuffixes;
	}

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitMemberExpression(this);
	}

	/**
	 * @return the expression
	 */
	public Expression getExpression() {
		return expression;
	}

	/**
	 * @return the expresionSuffixes
	 */
	public List<Expression> getExpresionSuffixes() {
		return expresionSuffixes;
	}

}

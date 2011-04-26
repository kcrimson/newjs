package net.primitive.javascript.core.ast;

import java.util.List;

import lombok.Getter;
import net.primitive.javascript.core.visitors.ExpressionVisitor;

public class MemberExpression extends Expression {

	@Getter
	private final Expression expression;
	@Getter
	private final Expression[] expresionSuffixes;

	public MemberExpression(Expression expression,
			List<Expression> expresionSuffixes) {
		super();
		this.expression = expression;
		this.expresionSuffixes = expresionSuffixes.toArray(new Expression[] {});
	}

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitMemberExpression(this);
	}

}

package net.primitive.javascript.core.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.primitive.javascript.core.visitors.ExpressionVisitor;

@AllArgsConstructor
public class WrappedExpression extends Expression {

	@Getter private final Expression expression1;
	@Getter private final Expression expression2;

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitWrappedExpression(this);
	}

}

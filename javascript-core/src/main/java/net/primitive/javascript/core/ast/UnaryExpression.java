package net.primitive.javascript.core.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.primitive.javascript.core.UnaryOperator;
import net.primitive.javascript.core.visitors.ExpressionVisitor;

@AllArgsConstructor
public class UnaryExpression extends Expression {

	@Getter private final UnaryOperator operator;
	@Getter private final Expression operand;

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitUnaryExpression(this);
	}

}

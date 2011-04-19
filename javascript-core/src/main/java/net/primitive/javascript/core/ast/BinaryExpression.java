package net.primitive.javascript.core.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.primitive.javascript.core.BinaryOperator;
import net.primitive.javascript.core.visitors.ExpressionVisitor;

@AllArgsConstructor
public final class BinaryExpression extends Expression {

	@Getter private final BinaryOperator operator;
	@Getter private final Expression op1;
	@Getter private final Expression op2;

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitBinaryExpression(this);
	}
}

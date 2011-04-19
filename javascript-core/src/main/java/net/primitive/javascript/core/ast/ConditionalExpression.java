package net.primitive.javascript.core.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.primitive.javascript.core.visitors.ExpressionVisitor;

@AllArgsConstructor
public class ConditionalExpression extends Expression {

	@Getter private final Expression op1;
	@Getter private final Expression op2;
	@Getter private final Expression op3;

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitConditionalExpression(this);
	}

}

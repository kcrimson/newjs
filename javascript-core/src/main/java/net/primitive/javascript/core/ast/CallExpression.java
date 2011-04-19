package net.primitive.javascript.core.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.primitive.javascript.core.visitors.ExpressionVisitor;

@AllArgsConstructor
public class CallExpression extends Expression {

	@Getter private final Expression memberExpression;
	@Getter private final Expression arguments;

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitCallExpression(this);
	}

}

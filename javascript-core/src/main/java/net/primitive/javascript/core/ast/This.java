package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.ExpressionVisitor;

public class This extends Expression {

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitThis(this);
	}

}

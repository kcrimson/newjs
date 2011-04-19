package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.ExpressionVisitor;

public abstract class Expression {

	public Expression() {
		super();
	}

	public abstract void accept(ExpressionVisitor visitor);

}

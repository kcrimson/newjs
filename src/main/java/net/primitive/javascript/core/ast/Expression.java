package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.ExpressionVisitor;

public abstract class Expression extends SourceElement {

	public Expression() {
	}

	public abstract void accept(ExpressionVisitor visitor);

}

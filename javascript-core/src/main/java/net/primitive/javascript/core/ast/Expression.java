package net.primitive.javascript.core.ast;

import lombok.AllArgsConstructor;
import net.primitive.javascript.core.visitors.ExpressionVisitor;

@AllArgsConstructor
public abstract class Expression {
	public abstract void accept(ExpressionVisitor visitor);
}

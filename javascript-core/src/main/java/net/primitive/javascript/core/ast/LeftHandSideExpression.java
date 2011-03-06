package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.LeftHandSideExpressionVisitor;


public abstract class LeftHandSideExpression extends Expression {

	public abstract void accept(LeftHandSideExpressionVisitor visitor);

}

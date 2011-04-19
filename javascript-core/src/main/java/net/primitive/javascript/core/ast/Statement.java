package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.StatementVisitor;

public abstract class Statement extends AstNode {

	public abstract void accept(StatementVisitor visitor);

}

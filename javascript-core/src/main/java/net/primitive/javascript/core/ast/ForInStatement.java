package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class ForInStatement extends Statement {

	public ForInStatement(Expression expression, Expression expression2,
			AstNode astNode) {
	}

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visitForInStatement(this);
	}

}

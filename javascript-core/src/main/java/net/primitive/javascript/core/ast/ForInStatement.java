package net.primitive.javascript.core.ast;

import lombok.Getter;

public class ForInStatement extends Statement {

	@Getter
	private final Object initializerExpression;
	@Getter
	private final Expression expression;
	@Getter
	private final AstNode statement;

	public ForInStatement(Object initializerExpression,
			Expression expression, AstNode statement) {
		this.initializerExpression = initializerExpression;
		this.expression = expression;
		this.statement = statement;
	}

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visitForInStatement(this);
	}

}

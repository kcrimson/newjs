package net.primitive.javascript.core.ast;

public class IfStatement extends Statement {
	private final Expression expression;
	private final Statement ifStatement;
	private final Statement elseStatement;

	public IfStatement(Expression expression, Statement ifStatement,
			Statement elseStatement) {
		this.expression = expression;
		this.ifStatement = ifStatement;
		this.elseStatement = elseStatement;
	}
}

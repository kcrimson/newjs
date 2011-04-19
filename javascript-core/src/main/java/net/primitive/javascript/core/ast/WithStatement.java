package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class WithStatement extends Statement {

	private final Expression expression;
	private final AstNodeList statement;

	public WithStatement(Expression expression, AstNode astNode) {
		this.expression = expression;
		this.statement = AstNodeList.wrapAstNode(astNode);
	}

	@Override
	public void accept(StatementVisitor visitor) {
		throw new UnsupportedOperationException("acceptWithStatement");
	}

	/**
	 * @return the expression
	 */
	public Expression getExpression() {
		return expression;
	}

	/**
	 * @return the statement
	 */
	public AstNodeList getStatement() {
		return statement;
	}

}

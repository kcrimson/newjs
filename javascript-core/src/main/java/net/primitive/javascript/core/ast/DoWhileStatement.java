package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class DoWhileStatement extends Statement {

	private final Statement statement;
	private final Expression expression;

	public DoWhileStatement(Statement statement, Expression expression) {
		this.statement = statement;
		this.expression = expression;
		statement.setParentAstNode(this);
	}

	@Override
	public void accept(StatementVisitor visitor) {
		throw new UnsupportedOperationException("acceptDoWhileStatement");
	}

	/**
	 * @return the statement
	 */
	public Statement getStatement() {
		return statement;
	}

	/**
	 * @return the expression
	 */
	public Expression getExpression() {
		return expression;
	}

}

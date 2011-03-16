package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class WhileStatement extends Statement {

	private final Expression expression;
	private final Statement statement;

	public WhileStatement(Expression expression, Statement statement) {
		this.expression = expression;
		this.statement = statement;
		statement.setParentAstNode(this);
	}

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visitWhileStatement(this);
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
	public Statement getStatement() {
		return statement;
	}

}

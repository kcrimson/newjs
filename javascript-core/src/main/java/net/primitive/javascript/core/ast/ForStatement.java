package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class ForStatement extends Statement {

	private final Statement initializeStatement;
	private final Expression incrementExpression;
	private final Expression testExpression;
	private final Statement statement;

	public ForStatement(Statement initializeStatement,
			Expression incrementExpression, Expression testExpression,
			Statement statement) {
		this.initializeStatement = initializeStatement;
		initializeStatement.setParentAstNode(this);
		this.incrementExpression = incrementExpression;
		this.testExpression = testExpression;
		this.statement = statement;
		statement.setParentAstNode(this);
	}

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visitForStatement(this);
	}

	/**
	 * @return the initializeStatement
	 */
	public Statement getInitializeStatement() {
		return initializeStatement;
	}

	/**
	 * @return the incrementExpression
	 */
	public Expression getIncrementExpression() {
		return incrementExpression;
	}

	/**
	 * @return the testExpression
	 */
	public Expression getTestExpression() {
		return testExpression;
	}

	/**
	 * @return the statement
	 */
	public Statement getStatement() {
		return statement;
	}

}

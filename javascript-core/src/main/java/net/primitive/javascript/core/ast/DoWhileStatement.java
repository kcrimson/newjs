package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class DoWhileStatement extends Statement {

	private final AstNodeList statements;
	private final Expression expression;

	public DoWhileStatement(AstNode statements, Expression expression) {
		this.statements = AstNodeList.wrapAstNode(statements);
		this.expression = expression;
		statements.setParentNode(this);
	}

	@Override
	public void accept(StatementVisitor visitor) {
		throw new UnsupportedOperationException("acceptDoWhileStatement");
	}

	/**
	 * @return the statement
	 */
	public AstNodeList getStatements() {
		return statements;
	}

	/**
	 * @return the expression
	 */
	public Expression getExpression() {
		return expression;
	}

}

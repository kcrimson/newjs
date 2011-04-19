package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class WhileStatement extends Statement {

	private final Expression expression;
	private final AstNodeList statements;

	public WhileStatement(Expression expression, AstNodeList astNodeList) {
		this.expression = expression;
		this.statements = astNodeList;
		astNodeList.setParentNode(this);
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
	public AstNodeList getStatements() {
		return statements;
	}

}

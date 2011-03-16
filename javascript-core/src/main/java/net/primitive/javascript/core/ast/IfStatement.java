package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class IfStatement extends Statement {

	private final Expression expression;
	private final Statement ifStatement;
	private final Statement elseStatement;

	public IfStatement(Expression expression, Statement ifStatement,
			Statement elseStatement) {
		this.expression = expression;
		this.ifStatement = ifStatement;
		ifStatement.setParentAstNode(this);
		this.elseStatement = elseStatement;
		if (elseStatement != null) {
			elseStatement.setParentAstNode(this);
		}
	}

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visitIfStatement(this);
	}

	/**
	 * @return the expression
	 */
	public Expression getExpression() {
		return expression;
	}

	/**
	 * @return the ifStatement
	 */
	public Statement getIfStatement() {
		return ifStatement;
	}

	/**
	 * @return the elseStatement
	 */
	public Statement getElseStatement() {
		return elseStatement;
	}

}

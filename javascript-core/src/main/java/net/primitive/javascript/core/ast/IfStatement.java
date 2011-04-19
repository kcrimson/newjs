package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class IfStatement extends Statement {

	private final Expression expression;
	private final AstNodeList ifStatement;
	private final AstNodeList elseStatement;

	public IfStatement(Expression expression, AstNodeList ifStatement,
			AstNodeList elseStatement) {
		this.expression = expression;
		this.ifStatement = ifStatement;
		ifStatement.setParentNode(this);
		this.elseStatement = elseStatement;
		if (elseStatement != null) {
			elseStatement.setParentNode(this);
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
	public AstNodeList getIfStatement() {
		return ifStatement;
	}

	/**
	 * @return the elseStatement
	 */
	public AstNodeList getElseStatement() {
		return elseStatement;
	}

}

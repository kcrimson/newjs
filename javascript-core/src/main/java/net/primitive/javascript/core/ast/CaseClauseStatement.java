package net.primitive.javascript.core.ast;

import java.util.List;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class CaseClauseStatement extends Statement {

	private final Expression expression;
	private final AstNodeList statements;

	public CaseClauseStatement(Expression expression, AstNodeList astNodeList) {
		this.expression = expression;
		this.statements = astNodeList;
	}

	/**
	 * @return the expression
	 */
	public Expression getExpression() {
		return expression;
	}

	/**
	 * @return the statements
	 */
	public AstNodeList getStatements() {
		return statements;
	}

	@Override
	public void accept(StatementVisitor visitor) {
		throw new UnsupportedOperationException("acceptCaseClauseStatement");
	}
}

package net.primitive.javascript.core.ast;

import java.util.List;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class SwitchStatement extends Statement {

	private final Expression expression;
	private final List<CaseClauseStatement> clauses;
	private final List<Statement> defaultClause;

	public SwitchStatement(Expression expression,
			List<CaseClauseStatement> clauses, List<Statement> defaultClause) {
		this.expression = expression;
		this.clauses = clauses;
		for (Statement statement : clauses) {
			statement.setParentAstNode(this);
		}
		this.defaultClause = defaultClause;
		for (Statement statement : defaultClause) {
			statement.setParentAstNode(this);
		}
	}

	@Override
	public void accept(StatementVisitor visitor) {
		throw new UnsupportedOperationException("acceptSwitchStatement");
	}

	/**
	 * @return the expression
	 */
	public Expression getExpression() {
		return expression;
	}

	/**
	 * @return the clauses
	 */
	public List<CaseClauseStatement> getClauses() {
		return clauses;
	}

	/**
	 * @return the defaultClause
	 */
	public List<Statement> getDefaultClause() {
		return defaultClause;
	}

}

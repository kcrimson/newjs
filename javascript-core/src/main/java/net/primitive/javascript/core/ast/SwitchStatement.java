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
		this.defaultClause = defaultClause;
	}

	@Override
	public void accept(StatementVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

}

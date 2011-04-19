package net.primitive.javascript.core.ast;

import java.util.List;

import lombok.Getter;
import net.primitive.javascript.core.visitors.StatementVisitor;

public class SwitchStatement extends Statement {

	@Getter private final Expression expression;
	@Getter private final List<CaseClauseStatement> clauses;
	@Getter private final List<Statement> defaultClause;

	public SwitchStatement(Expression expression,
			List<CaseClauseStatement> clauses, List<Statement> defaultClause) {
		this.expression = expression;
		this.clauses = clauses;
		for (Statement statement : clauses) {
			statement.setParentNode(this);
		}
		this.defaultClause = defaultClause;
		for (Statement statement : defaultClause) {
			statement.setParentNode(this);
		}
	}

	@Override
	public void accept(StatementVisitor visitor) {
		throw new UnsupportedOperationException("acceptSwitchStatement");
	}

}

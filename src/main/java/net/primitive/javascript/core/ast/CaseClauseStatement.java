package net.primitive.javascript.core.ast;

import java.util.List;

public class CaseClauseStatement extends Statement {

	private final Expression expression;
	private final List<Statement> statements;

	public CaseClauseStatement(Expression expression, List<Statement> statements) {
		this.expression = expression;
		this.statements = statements;
	}

}

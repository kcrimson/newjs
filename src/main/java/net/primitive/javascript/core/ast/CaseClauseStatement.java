package net.primitive.javascript.core.ast;

import java.util.List;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class CaseClauseStatement extends Statement {

	private final Expression expression;
	private final List<Statement> statements;

	public CaseClauseStatement(Expression expression, List<Statement> statements) {
		this.expression = expression;
		this.statements = statements;
	}

	@Override
	public void accept(StatementVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

}

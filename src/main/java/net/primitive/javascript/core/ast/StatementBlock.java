package net.primitive.javascript.core.ast;

import java.util.List;

public class StatementBlock extends Statement {

	private final List<Statement> statements;

	public StatementBlock(List<Statement> statements) {
		this.statements = statements;
	}

}

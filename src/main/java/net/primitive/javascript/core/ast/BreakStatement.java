package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class BreakStatement extends Statement {

	private final String identifier;

	public BreakStatement(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public void accept(StatementVisitor visitor) {

	}

}

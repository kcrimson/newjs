package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class ContinueStatement extends Statement {

	private final String identifier;

	public ContinueStatement(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public void accept(StatementVisitor visitor) {
		throw new UnsupportedOperationException("acceptContinueStatement");
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

}

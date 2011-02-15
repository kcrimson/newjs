package net.primitive.javascript.core.ast;

public class CatchClause extends Statement {

	private final String identifier;
	private final Statement statement;

	public CatchClause(String identifier, Statement statement) {
		this.identifier = identifier;
		this.statement = statement;
	}

}

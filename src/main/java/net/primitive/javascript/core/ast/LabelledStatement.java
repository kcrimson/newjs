package net.primitive.javascript.core.ast;

public class LabelledStatement extends Statement {

	private final String identifier;
	private final Statement statement;

	public LabelledStatement(String identifier, Statement statement) {
		this.identifier = identifier;
		this.statement = statement;
	}

}

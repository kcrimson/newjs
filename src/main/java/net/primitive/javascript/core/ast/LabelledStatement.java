package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class LabelledStatement extends Statement {

	private final String identifier;
	private final Statement statement;

	public LabelledStatement(String identifier, Statement statement) {
		this.identifier = identifier;
		this.statement = statement;
	}

	@Override
	public void accept(StatementVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

}

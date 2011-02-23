package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.StatementVisitor;


public class ContinueStatement extends Statement {

	private final String identifier;

	public ContinueStatement(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public void accept(StatementVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

}

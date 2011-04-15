package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.ProgramVisitor;

public class Program extends AstNode {

	public void accept(ProgramVisitor visitor) {
		visitor.visit(this);
	}

}

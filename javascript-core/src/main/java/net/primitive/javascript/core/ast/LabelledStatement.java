package net.primitive.javascript.core.ast;

import lombok.Getter;
import net.primitive.javascript.core.visitors.StatementVisitor;

public class LabelledStatement extends Statement {

	@Getter private final String identifier;
	@Getter private final AstNode statement;

	public LabelledStatement(String identifier, AstNode astNode) {
		this.identifier = identifier;
		this.statement = astNode;
		astNode.setParentNode(this);
	}

	@Override
	public void accept(StatementVisitor visitor) {
		throw new UnsupportedOperationException("acceptLabelledStatement");
	}

}

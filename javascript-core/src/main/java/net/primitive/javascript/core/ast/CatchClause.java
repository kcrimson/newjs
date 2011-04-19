package net.primitive.javascript.core.ast;

import lombok.Getter;
import net.primitive.javascript.core.visitors.StatementVisitor;

public class CatchClause extends Statement {

	@Getter private final String identifier;
	@Getter private final AstNodeList statements;

	public CatchClause(String identifier, AstNodeList astNode) {
		this.identifier = identifier;
		this.statements = astNode;
		astNode.setParentNode(this);
	}

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visitCatchClause(this);
	}

}

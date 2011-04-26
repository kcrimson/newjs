package net.primitive.javascript.core.ast;

import lombok.Getter;
import net.primitive.javascript.core.visitors.StatementVisitor;

public class WhileStatement extends Statement {

	@Getter
	private final Expression expression;
	@Getter
	private final Statement[] statements;

	public WhileStatement(Expression expression, AstNodeList astNodeList) {
		this.expression = expression;
		this.statements = astNodeList.getAstNodes().toArray(new Statement[] {});
		astNodeList.setParentNode(this);
	}

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visitWhileStatement(this);
	}

}

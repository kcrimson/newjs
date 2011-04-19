package net.primitive.javascript.core.ast;

import lombok.Getter;
import net.primitive.javascript.core.visitors.StatementVisitor;

public class DoWhileStatement extends Statement {

	@Getter private final AstNodeList statements;
	@Getter private final Expression expression;

	public DoWhileStatement(AstNode statements, Expression expression) {
		this.statements = AstNodeList.wrapAstNode(statements);
		this.expression = expression;
		statements.setParentNode(this);
	}

	@Override
	public void accept(StatementVisitor visitor) {
		throw new UnsupportedOperationException("acceptDoWhileStatement");
	}

}

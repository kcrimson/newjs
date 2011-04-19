package net.primitive.javascript.core.ast;

import lombok.Getter;
import net.primitive.javascript.core.visitors.StatementVisitor;

public class IfStatement extends Statement {

	@Getter private final Expression expression;
	@Getter private final AstNodeList ifStatement;
	@Getter private final AstNodeList elseStatement;

	public IfStatement(Expression expression, AstNodeList ifStatement,
			AstNodeList elseStatement) {
		this.expression = expression;
		this.ifStatement = ifStatement;
		ifStatement.setParentNode(this);
		this.elseStatement = elseStatement;
		if (elseStatement != null) {
			elseStatement.setParentNode(this);
		}
	}

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visitIfStatement(this);
	}

}

package net.primitive.javascript.core.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.primitive.javascript.core.visitors.StatementVisitor;

@AllArgsConstructor
public class CaseClauseStatement extends Statement {

	@Getter private final Expression expression;
	@Getter private final AstNodeList statements;

	@Override
	public void accept(StatementVisitor visitor) {
		throw new UnsupportedOperationException("acceptCaseClauseStatement");
	}
}

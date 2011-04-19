package net.primitive.javascript.core.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.primitive.javascript.core.visitors.StatementVisitor;

@AllArgsConstructor
public class ThrowStatement extends Statement {

	@Getter private final Expression expression;

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visitThrowStatement(this);
	}

}

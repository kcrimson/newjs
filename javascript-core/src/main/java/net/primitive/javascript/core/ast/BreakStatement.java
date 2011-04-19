package net.primitive.javascript.core.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.primitive.javascript.core.visitors.StatementVisitor;

@AllArgsConstructor
public class BreakStatement extends Statement {

	@Getter private final String identifier;

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visitBreakStatement(this);
	}

}

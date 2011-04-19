package net.primitive.javascript.core.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.primitive.javascript.core.visitors.StatementVisitor;

@AllArgsConstructor
public class ContinueStatement extends Statement {

	@Getter private final String identifier;

	@Override
	public void accept(StatementVisitor visitor) {
		throw new UnsupportedOperationException("acceptContinueStatement");
	}
}

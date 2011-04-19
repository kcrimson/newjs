package net.primitive.javascript.core.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.primitive.javascript.core.visitors.ExpressionVisitor;

@AllArgsConstructor
public class Identifier extends Expression {

	@Getter private final String identfierName;

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitIdentifier(this);
	}

}

package net.primitive.javascript.core.ast;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.primitive.javascript.core.visitors.ExpressionVisitor;

@AllArgsConstructor
public class ArrayLiteral extends Expression {
	
	@Getter
	private final List values;
	
	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitArrayLiteral(this);
	}

}

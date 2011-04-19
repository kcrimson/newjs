package net.primitive.javascript.core.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.primitive.javascript.core.visitors.ExpressionVisitor;

@AllArgsConstructor
public class Literal extends Expression {

	@Getter private final Object value;

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitLiteral(this);
	}

	public static String unwrapString(String string) {
		return string.substring(1, string.length() - 1);
	}

}

package net.primitive.javascript.core.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.primitive.javascript.core.BinaryOperator;
import net.primitive.javascript.core.visitors.ExpressionVisitor;

@AllArgsConstructor
public class CompoundAssignment extends Expression {

	@Getter
	private final Expression leftExpression;
	
	@Getter
	private final BinaryOperator operator;

	@Getter
	private final Expression rightExpression;

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitCompoundAssignment(this);
	}

}

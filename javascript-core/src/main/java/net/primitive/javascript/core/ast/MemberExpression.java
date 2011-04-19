package net.primitive.javascript.core.ast;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

import net.primitive.javascript.core.visitors.ExpressionVisitor;

@AllArgsConstructor
public class MemberExpression extends Expression {

	@Getter private final Expression expression;
	@Getter private final List<Expression> expresionSuffixes;

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitMemberExpression(this);
	}

}

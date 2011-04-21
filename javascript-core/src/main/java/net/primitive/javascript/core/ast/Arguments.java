package net.primitive.javascript.core.ast;

import java.util.List;

import net.primitive.javascript.core.visitors.ExpressionVisitor;

public class Arguments extends Expression {

	private final List<Expression> argumentsList;

	public Arguments(List<Expression> argumentsList) {
		this.argumentsList = argumentsList;
	}

	/**
	 * @return the argumentsList
	 */
	public List<Expression> getArgumentsList() {
		return argumentsList;
	}

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitArguments(this);
	}

}

package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.ExpressionVisitor;
import net.primitive.javascript.core.visitors.LeftHandSideExpressionVisitor;

public class Identifier extends LeftHandSideExpression {

	private final String identfierName;

	public Identifier(String identfierName) {
		this.identfierName = identfierName;
	}

	/**
	 * @return the identfierName
	 */
	public String getIdentfierName() {
		return identfierName;
	}

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitIdentifier(this);
	}

	@Override
	public void accept(LeftHandSideExpressionVisitor visitor) {
		visitor.visitIdentifier(this);
	}

}

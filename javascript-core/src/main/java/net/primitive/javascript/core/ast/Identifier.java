package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.ExpressionVisitor;

public class Identifier extends Expression {

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

}

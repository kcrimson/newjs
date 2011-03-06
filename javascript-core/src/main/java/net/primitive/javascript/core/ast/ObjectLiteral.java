package net.primitive.javascript.core.ast;

import java.util.List;

import net.primitive.javascript.core.visitors.ExpressionVisitor;

public class ObjectLiteral extends Expression {

	private final List<NameValuePair> nameValuePairs;

	public ObjectLiteral(List<NameValuePair> nameValuePairs) {
		this.nameValuePairs = nameValuePairs;
	}

	/**
	 * @return the nameValuePairs
	 */
	public List<NameValuePair> getNameValuePairs() {
		return nameValuePairs;
	}

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitObjectLiteral(this);
	}

}

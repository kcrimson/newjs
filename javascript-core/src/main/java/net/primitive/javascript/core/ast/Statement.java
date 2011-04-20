package net.primitive.javascript.core.ast;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import net.primitive.javascript.core.visitors.StatementVisitor;

public abstract class Statement extends AstNode {

	public abstract void accept(StatementVisitor visitor);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		ToStringBuilder stringBuilder = new ToStringBuilder(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
		return stringBuilder.toString();
	}

}

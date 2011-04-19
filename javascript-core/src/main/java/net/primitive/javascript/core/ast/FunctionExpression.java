package net.primitive.javascript.core.ast;

import static java.util.Collections.unmodifiableList;

import java.util.List;

import lombok.Getter;

import net.primitive.javascript.core.visitors.ExpressionVisitor;

public class FunctionExpression extends Expression {

	@Getter private final String functionName;
	@Getter private final List parameterList;
	@Getter private final AstNodeList functionBody;

	public FunctionExpression(String functionName, List parameterList,
			AstNodeList functionBody) {
		this.functionName = functionName;
		this.parameterList = unmodifiableList(parameterList);
		this.functionBody = functionBody;
	}

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitFunctionExpression(this);
	}

}

package net.primitive.javascript.core.ast;

import static java.util.Collections.unmodifiableList;

import java.util.List;

import net.primitive.javascript.core.visitors.ExpressionVisitor;

public class FunctionExpression extends Expression {

	private final String functionName;
	private final List parameterList;
	private final SourceElement[] sourceElements;

	public FunctionExpression(String functionName, List parameterList,
			List<SourceElement> sourceElements) {
		this.functionName = functionName;
		this.parameterList = unmodifiableList(parameterList);
		this.sourceElements = sourceElements.toArray(new SourceElement[] {});
	}

	/**
	 * @return the functionName
	 */
	public String getFunctionName() {
		return functionName;
	}

	/**
	 * @return the parameterList
	 */
	public List getParameterList() {
		return parameterList;
	}

	/**
	 * @return the sourceElements
	 */
	public SourceElement[] getSourceElements() {
		return sourceElements;
	}

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitFunctionExpression(this);
	}

}

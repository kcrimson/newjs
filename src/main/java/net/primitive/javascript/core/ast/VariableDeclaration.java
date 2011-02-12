package net.primitive.javascript.core.ast;

public class VariableDeclaration extends Statement {

	private final String variableName;
	private final Expression expression;

	public VariableDeclaration(String variableName, Expression expression) {
		this.variableName = variableName;
		this.expression = expression;
	}

	/**
	 * @return the variableName
	 */
	public String getVariableName() {
		return variableName;
	}

	/**
	 * @return the expression
	 */
	public Expression getExpression() {
		return expression;
	}

}

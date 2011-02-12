package net.primitive.javascript.core.ast;

import static java.util.Collections.unmodifiableList;

import java.util.List;

public class VariableStatement extends Statement {

	private final List<VariableDeclaration> variableDeclarations;

	public VariableStatement(List<VariableDeclaration> variableDeclarations) {
		this.variableDeclarations = unmodifiableList(variableDeclarations);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the variableDeclarations
	 */
	public List<VariableDeclaration> getVariableDeclarations() {
		return variableDeclarations;
	}

}

package net.primitive.javascript.core.ast;

import static java.util.Collections.unmodifiableList;

import java.util.List;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class VariableStatement extends Statement {

	private final List<VariableDeclaration> variableDeclarations;

	public VariableStatement(List<VariableDeclaration> variableDeclarations) {
		this.variableDeclarations = unmodifiableList(variableDeclarations);
		for (Statement statement : variableDeclarations) {
			statement.setParentAstNode(this);
		}
	}

	/**
	 * @return the variableDeclarations
	 */
	public List<VariableDeclaration> getVariableDeclarations() {
		return variableDeclarations;
	}

	@Override
	public void accept(StatementVisitor visitor) {
		for (VariableDeclaration variableDeclaration : variableDeclarations) {
			visitor.visitVariableDeclaration(variableDeclaration);
		}
	}

}

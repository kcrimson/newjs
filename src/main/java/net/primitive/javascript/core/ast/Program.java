package net.primitive.javascript.core.ast;

import java.util.List;

import net.primitive.javascript.core.visitors.ProgramVisitor;

public class Program {

	private List<Statement> sourceElements;

	public void setSourceElements(List<Statement> sourceElements) {
		this.sourceElements = sourceElements;
	}

	/**
	 * @return the sourceElements
	 */
	public List<Statement> getSourceElements() {
		return sourceElements;
	}

	public void accept(ProgramVisitor visitor) {
		for (Statement statement : sourceElements) {
			visitor.visit(statement);
		}		
	}

}

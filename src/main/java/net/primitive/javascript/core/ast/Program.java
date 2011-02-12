package net.primitive.javascript.core.ast;

import java.util.List;

public class Program {

	private List<SourceElement> sourceElements;

	public void addSourceElement(List<SourceElement> sourceElements) {
		this.sourceElements = sourceElements;
	}

	/**
	 * @return the sourceElements
	 */
	public List<SourceElement> getSourceElements() {
		return sourceElements;
	}

}

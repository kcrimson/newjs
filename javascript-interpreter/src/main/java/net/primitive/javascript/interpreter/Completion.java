package net.primitive.javascript.interpreter;

import net.primitive.javascript.core.ast.AstNode;

public class Completion {

	private final CompletionType type;

	private Object value;

	private final AstNode target;

	public Completion(CompletionType type, Object value, AstNode target) {
		super();
		this.type = type;
		this.value = value;
		this.target = target;
	}

	/**
	 * @return the type
	 */
	public CompletionType getType() {
		return type;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @return the target
	 */
	public AstNode getTarget() {
		return target;
	}

	public void setValue(Object value2) {
		value = value2;
		// TODO Auto-generated method stub

	}

}

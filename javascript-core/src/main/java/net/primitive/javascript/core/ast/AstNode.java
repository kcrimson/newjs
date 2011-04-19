package net.primitive.javascript.core.ast;

/**
 * Represents AST node
 * 
 * @author jpalka@gmail.com
 * 
 */
public abstract class AstNode {

	private AstNode parentNode;

	public AstNode() {
		super();
	}

	/**
	 * @return the parent
	 */
	public AstNode getParentNode() {
		return parentNode;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParentNode(AstNode parent) {
		this.parentNode = parent;
	}

}

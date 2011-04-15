package net.primitive.javascript.core.ast;

import java.util.ArrayList;
import java.util.List;

public class AstNode {

	private final List<AstNode> childNodes = new ArrayList<AstNode>();

	public AstNode() {
		super();
	}

	public void addAstNode(AstNode astNode) {
		childNodes.add(astNode);
	}

	public List<AstNode> getAstNodes() {
		return childNodes;
	}

}

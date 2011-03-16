package net.primitive.javascript.core.ast;

import java.util.ArrayList;
import java.util.List;

public class AstNode {

	private AstNode parentAstNode;

	private final List<AstNode> childNodes = new ArrayList<AstNode>();

	public AstNode() {
		super();
	}

	public void setParentAstNode(AstNode node) {
		parentAstNode = node;
		parentAstNode.addChildAstNode(this);
	}

	public void addChildAstNode(AstNode astNode) {
		childNodes.add(astNode);
	}

}

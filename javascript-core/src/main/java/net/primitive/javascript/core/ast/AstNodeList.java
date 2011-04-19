package net.primitive.javascript.core.ast;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents fragment of AST.
 * 
 * @author jpalka@gmail.com
 * 
 */
public class AstNodeList extends AstNode {

	private final List<AstNode> childNodes = new ArrayList<AstNode>();

	public void addAstNode(AstNode astNode) {
		if (astNode instanceof AstNodeList) {
			AstNodeList nodelist = (AstNodeList) astNode;
			for (AstNode node : nodelist.getAstNodes()) {
				appendAstNode(node);
			}
			return;
		}
		if (astNode != null) {
			appendAstNode(astNode);
		}
	}

	public List<AstNode> getAstNodes() {
		return childNodes;
	}

	private void appendAstNode(AstNode astNode) {
		astNode.setParentNode(this);
		childNodes.add(astNode);
	}

	public static AstNodeList wrapAstNode(AstNode node) {
		if (node instanceof AstNodeList) {
			return (AstNodeList) node;
		}
		AstNodeList nodeList = new AstNodeList();
		nodeList.addAstNode(node);
		return nodeList;
	}

}

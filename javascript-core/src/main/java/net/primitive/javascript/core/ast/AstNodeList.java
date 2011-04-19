package net.primitive.javascript.core.ast;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Represents fragment of AST.
 * 
 * @author jpalka@gmail.com
 * 
 */
public class AstNodeList extends AstNode {

	@Getter private final List<AstNode> astNodes = new ArrayList<AstNode>();

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

	private void appendAstNode(AstNode astNode) {
		astNode.setParentNode(this);
		astNodes.add(astNode);
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

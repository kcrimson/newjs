/**
 * Copyright (C) 2012 Primitive Team <jpalka@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

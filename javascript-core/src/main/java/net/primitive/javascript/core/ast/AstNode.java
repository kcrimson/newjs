package net.primitive.javascript.core.ast;

import java.util.ArrayList;
import java.util.List;

public class AstNode {

	//private final AstNode parentNode;

	private final List<AstNode> childNodes = new ArrayList<AstNode>();

	public AstNode() {
		super();
		//this.parentNode = parentNode;
	}

}

package net.primitive.javascript.core.ast;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents AST node
 * 
 * @author jpalka@gmail.com
 * 
 */
@NoArgsConstructor
public abstract class AstNode {

	@Getter @Setter private AstNode parentNode;

}

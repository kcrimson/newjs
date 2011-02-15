package net.primitive.javascript.core.visitors;

import net.primitive.javascript.core.ast.Statement;

public interface ProgramVisitor extends SourceElementVisitor {

	void visit(Statement program);

}

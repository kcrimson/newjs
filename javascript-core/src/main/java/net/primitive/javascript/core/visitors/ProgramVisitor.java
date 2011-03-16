package net.primitive.javascript.core.visitors;

import net.primitive.javascript.core.ast.Program;

public interface ProgramVisitor extends SourceElementVisitor {

	void visit(Program program);

}

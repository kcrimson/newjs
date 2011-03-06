package net.primitive.javascript.compiler;

import org.objectweb.asm.ClassVisitor;

import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ast.Statement;
import net.primitive.javascript.core.visitors.ProgramVisitor;

public class ProgramVisitorImpl implements ProgramVisitor {

	private final ClassVisitor visitor;

	public ProgramVisitorImpl(ClassVisitor visitor) {
		this.visitor = visitor;
	}

	@Override
	public Scriptable getScope() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void visit(Statement program) {

	}

}

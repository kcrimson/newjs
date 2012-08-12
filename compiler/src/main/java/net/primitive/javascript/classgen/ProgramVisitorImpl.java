package net.primitive.javascript.classgen;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_SUPER;

import java.util.List;

import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ast.AstNode;
import net.primitive.javascript.core.ast.Program;
import net.primitive.javascript.core.ast.ProgramVisitor;
import net.primitive.javascript.core.ast.Statement;
import net.primitive.javascript.core.ast.StatementVisitor;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ProgramVisitorImpl implements ProgramVisitor {

	private final ClassWriter classWriter;
	private StatementVisitor statementVisitor;

	public ProgramVisitorImpl(ClassWriter classWriter) {
		super();
		this.classWriter = classWriter;
		this.statementVisitor = new StatementVisitorImpl();
	}

	@Override
	public void visit(Program program) {

		MethodVisitor mv;
		// Class header
		classWriter.visit(Opcodes.V1_6, ACC_PUBLIC + ACC_SUPER, "ScriptImpl",
				null, "java/lang/Object",
				new String[] { "net/primitive/javascript/core/Script" });

		// Default constructor
		{
			mv = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null,
					null);
			mv.visitCode();
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object",
					"<init>", "()V");
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}

		// "execute" method
		{
			mv = classWriter.visitMethod(
					ACC_PUBLIC,
					"execute",
					Type.getMethodDescriptor(Type.VOID_TYPE,
							Type.getType(Scriptable.class)), null, null);
			mv.visitCode();

			// method body

			if (program != null) {
				List<AstNode> nodes = program.getAstNodes();

				for (AstNode node : nodes) {
					((Statement) node).accept(statementVisitor);
				}
			}

			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(2, 2);
			mv.visitEnd();
		}

		classWriter.visitEnd();
	}

}

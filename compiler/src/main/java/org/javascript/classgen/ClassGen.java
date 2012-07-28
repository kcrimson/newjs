package org.javascript.classgen;

import net.primitive.javascript.core.Script;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * Hello world!
 * 
 */
public class ClassGen {
	public byte[] generateClass(String classname) throws Exception {
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES
				| ClassWriter.COMPUTE_MAXS);

		writer.visit(Opcodes.V1_6, Opcodes.ACC_PUBLIC, "ScriptImpl", null,
				"java/lang/Object",
				new String[] { "net/primitive/javascript/core/Script" });

		MethodVisitor methodVisitor = writer.visitMethod(
				Opcodes.ACC_PUBLIC,
				"execute",
				Type.getMethodDescriptor(Type.VOID_TYPE,
						Type.getType(Script.class)), null, // exceptions
				null);

		methodVisitor.visitEnd();

		writer.visitEnd();

		byte[] bs = writer.toByteArray();
		return bs;
	}
}

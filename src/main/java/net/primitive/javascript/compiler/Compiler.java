package net.primitive.javascript.compiler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.security.Signature;

import net.primitive.javascript.core.JavaScriptLexer;
import net.primitive.javascript.core.JavaScriptParser;
import net.primitive.javascript.core.ast.Program;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.signature.SignatureWriter;

public class Compiler {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		
		Class.forName("Script").newInstance();
		
		File file = new File("src/test/resources/while.js");

		JavaScriptLexer lexer = new JavaScriptLexer(new ANTLRFileStream(
				file.getAbsolutePath()));

		CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);

		JavaScriptParser javaScriptParser = new JavaScriptParser(
				commonTokenStream);
		Program result = javaScriptParser.program().result;

		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		ProgramVisitorImpl visitorImpl = new ProgramVisitorImpl(writer);

		SignatureWriter sw = new SignatureWriter();
		sw.visitParameterType().visitInterface().visitClassType("net/primitive/javascript/core/Scriptable");
		sw.visitEnd();
		sw.visitReturnType().visitBaseType('V');
		//sw.visitEnd();
		String sig = sw.toString();
		System.out.println(sig);

		writer.visit(Opcodes.V1_5, Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL,
				"Script", null, "java/lang/Object", null/*
														 * new String[]{
														 * "net/primitive/javascript/Script"
														 * }
														 */);
		MethodVisitor method = writer.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
		method.visitCode();
		method.visitVarInsn(Opcodes.ALOAD, 0);
		method.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
		method.visitInsn(Opcodes.RETURN);
		method.visitMaxs(1, 1);
		method.visitEnd();
		MethodVisitor methodVisitor = writer.visitMethod(Opcodes.ACC_PUBLIC,
				"execute", sig, null, null);
		methodVisitor.visitCode();
		methodVisitor.visitInsn(Opcodes.RETURN);
		methodVisitor.visitMaxs(1, 1);
		methodVisitor.visitEnd();

		writer.visitEnd();

		byte[] byteArray = writer.toByteArray();
		FileOutputStream w = new FileOutputStream("Script.class");
		w.write(byteArray);
		w.close();
	}

}

package org.javascript.classgen;

import java.io.FileOutputStream;

import net.primitive.javascript.core.Script;
import net.primitive.javascript.core.ast.Program;

import org.objectweb.asm.ClassWriter;

/**
 * Hello world!
 * 
 */
public class ClassGen {
	public byte[] generateCode(String classname, Program program)
			throws Exception {

		ClassWriter cw = new ClassWriter(0);

		ProgramVisitorImpl programVisitor = new ProgramVisitorImpl(cw);
		programVisitor.visit(program);

		byte[] bs = cw.toByteArray();
		new FileOutputStream("ScriptImpl.class").write(bs);
		return bs;
	}

	public Class<?> generateClass(String classname, Program program,
			ClassLoader parentClassLoader) throws Exception {
		byte[] bs = generateCode(classname, program);
		Class<?> class1 = new DefiningClassLoader(parentClassLoader)
				.defineClass(classname, bs);
		return class1;
	}

	public static void main(String[] argv) throws Exception {
		Class<?> generateClass = new ClassGen().generateClass("ScriptImpl",
				null, null);
		((Script) generateClass.newInstance()).execute(null);
		System.out.println(generateClass.newInstance());
	}

}

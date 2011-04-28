package net.primitive.javascript.compiler;

import java.io.PrintWriter;
import java.io.StringWriter;

import net.primitive.javascript.core.Scriptable;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JFormatter;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JVar;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

public class Compiler {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		JCodeModel codeModel = new JCodeModel();

		JClass ref = codeModel.ref(Scriptable.class);

		JBlock block = new JBlock(true, true);

		JVar decl = block.decl(ref, "jsVar");//.init(init);
		//block.invoke("make");
		// decl.assign();
	//	block._return(decl);

		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		JFormatter f = new JFormatter(writer);
		block.generate(f);
		writer.flush();
		String string = stringWriter.toString();
		System.out.println(string);

		ClassPool cp = new ClassPool();
		cp.appendSystemPath();

		CtClass returnType = cp.get(Scriptable.class.getName());

		CtClass superclass = cp.get(AbstractScript.class.getName());

		CtClass makeClass = cp.makeClass("Script", superclass);

		CtMethod ctMethod = CtNewMethod.make(CtClass.voidType, "exec",
				new CtClass[] { returnType }, new CtClass[] {}, string,
				makeClass);

		makeClass.addMethod(ctMethod);

		makeClass.writeFile();

		Script newInstance = (Script) makeClass.toClass().newInstance();
		newInstance.exec(null);
	}

}

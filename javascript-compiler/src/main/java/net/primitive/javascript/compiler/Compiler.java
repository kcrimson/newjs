package net.primitive.javascript.compiler;

import static me.qmx.jitescript.CodeBlock.newCodeBlock;
import static me.qmx.jitescript.util.CodegenUtils.p;
import static me.qmx.jitescript.util.CodegenUtils.sig;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Modifier;

import me.qmx.jitescript.CodeBlock;
import me.qmx.jitescript.JDKVersion;
import me.qmx.jitescript.JiteClass;
import me.qmx.jitescript.util.CodegenUtils;
import net.primitive.javascript.core.Convertions;
import net.primitive.javascript.core.Script;
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

	private static final class ModClassLoader extends ClassLoader {
		public Class<?> define(String name,byte[] bytes){
			return defineClass(name, bytes, 0, bytes.length);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		JiteClass clazz = new JiteClass("Sample",
				new String[] { p(Script.class)});
		clazz.defineDefaultConstructor();
		
		clazz.defineMethod("execute", Modifier.PUBLIC,
				sig(void.class, Scriptable.class), 
				newCodeBlock().
					aload(0).
					invokestatic(p(Convertions.class), "toObject", sig(Object.class,Object.class)).
					voidreturn());
		
		byte[] bytes = clazz.toBytes(JDKVersion.V1_6);
		FileOutputStream w = new FileOutputStream("Sample.class");
		w.write(bytes);
		w.close();

		ModClassLoader classLoader = new ModClassLoader();
		Class<?> class1 = classLoader.define("Sample", bytes);
		Script instance = (Script) class1.newInstance();
		instance.execute(null);
		

		// JCodeModel codeModel = new JCodeModel();
		//
		// JClass ref = codeModel.ref(Scriptable.class);
		//
		// JBlock block = new JBlock(true, true);
		//
		// JVar decl = block.decl(ref, "jsVar");//.init(init);
		// //block.invoke("make");
		// // decl.assign();
		// // block._return(decl);
		//
		// StringWriter stringWriter = new StringWriter();
		// PrintWriter writer = new PrintWriter(stringWriter);
		// JFormatter f = new JFormatter(writer);
		// block.generate(f);
		// writer.flush();
		// String string = stringWriter.toString();
		// System.out.println(string);
		//
		// ClassPool cp = new ClassPool();
		// cp.appendSystemPath();
		//
		// CtClass returnType = cp.get(Scriptable.class.getName());
		//
		// CtClass superclass = cp.get(AbstractScript.class.getName());
		//
		// CtClass makeClass = cp.makeClass("Script", superclass);
		//
		// CtMethod ctMethod = CtNewMethod.make(CtClass.voidType, "exec",
		// new CtClass[] { returnType }, new CtClass[] {}, string,
		// makeClass);
		//
		// makeClass.addMethod(ctMethod);
		//
		// makeClass.writeFile();
		//
		// Script newInstance = (Script) makeClass.toClass().newInstance();
		// newInstance.exec(null);
	}

}

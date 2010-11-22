package javaapplication1;

import java.dyn.BootstrapMethod;
import java.dyn.CallSite;
import java.dyn.InvokeDynamic;
import java.dyn.Linkage;
import java.dyn.MethodHandle;
import java.dyn.MethodHandles.Lookup;
import java.dyn.MethodType;
import java.dyn.MethodHandles;
import java.util.Date;

public class InvokeDynamicStatic {

    static {
        Linkage.registerBootstrapMethod("bootstrap");
    }

    public static void main(String... av) throws Throwable {

        for (String a : av) {
            InvokeDynamic.call(a);
        }
        InvokeDynamic.call(new Date());
    }

    public static void foo(String x) {
        System.out.println("Hello, " + x);
    }

    public static void foo(Date x) {
        System.out.println("Today is " + x);
    }

    private static CallSite bootstrap(Class caller, String name, MethodType type) throws Exception {
        System.out.println("linking:" + name+"("+type+")");
        final Lookup lookup = MethodHandles.lookup();
        MethodHandles.
        Class<?> pType = type.parameterType(0);
        final MethodType methodType = MethodType.methodType(void.class, pType);
        final MethodHandle handle = lookup.findStatic(InvokeDynamicStatic.class, "foo", methodType);
        CallSite c = new CallSite(handle);
        return c;
    }
}

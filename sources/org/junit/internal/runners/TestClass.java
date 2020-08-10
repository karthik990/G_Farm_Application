package org.junit.internal.runners;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.internal.MethodSorter;

@Deprecated
public class TestClass {
    private final Class<?> klass;

    public TestClass(Class<?> cls) {
        this.klass = cls;
    }

    public List<Method> getTestMethods() {
        return getAnnotatedMethods(Test.class);
    }

    /* access modifiers changed from: 0000 */
    public List<Method> getBefores() {
        return getAnnotatedMethods(BeforeClass.class);
    }

    /* access modifiers changed from: 0000 */
    public List<Method> getAfters() {
        return getAnnotatedMethods(AfterClass.class);
    }

    public List<Method> getAnnotatedMethods(Class<? extends Annotation> cls) {
        Method[] declaredMethods;
        ArrayList arrayList = new ArrayList();
        for (Class declaredMethods2 : getSuperClasses(this.klass)) {
            for (Method method : MethodSorter.getDeclaredMethods(declaredMethods2)) {
                if (method.getAnnotation(cls) != null && !isShadowed(method, (List<Method>) arrayList)) {
                    arrayList.add(method);
                }
            }
        }
        if (runsTopToBottom(cls)) {
            Collections.reverse(arrayList);
        }
        return arrayList;
    }

    private boolean runsTopToBottom(Class<? extends Annotation> cls) {
        return cls.equals(Before.class) || cls.equals(BeforeClass.class);
    }

    private boolean isShadowed(Method method, List<Method> list) {
        for (Method isShadowed : list) {
            if (isShadowed(method, isShadowed)) {
                return true;
            }
        }
        return false;
    }

    private boolean isShadowed(Method method, Method method2) {
        if (!method2.getName().equals(method.getName()) || method2.getParameterTypes().length != method.getParameterTypes().length) {
            return false;
        }
        for (int i = 0; i < method2.getParameterTypes().length; i++) {
            if (!method2.getParameterTypes()[i].equals(method.getParameterTypes()[i])) {
                return false;
            }
        }
        return true;
    }

    private List<Class<?>> getSuperClasses(Class<?> cls) {
        ArrayList arrayList = new ArrayList();
        while (cls != null) {
            arrayList.add(cls);
            cls = cls.getSuperclass();
        }
        return arrayList;
    }

    public Constructor<?> getConstructor() throws SecurityException, NoSuchMethodException {
        return this.klass.getConstructor(new Class[0]);
    }

    public Class<?> getJavaClass() {
        return this.klass;
    }

    public String getName() {
        return this.klass.getName();
    }
}

package org.junit.internal.runners;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@Deprecated
public class MethodValidator {
    private final List<Throwable> errors = new ArrayList();
    private TestClass testClass;

    public MethodValidator(TestClass testClass2) {
        this.testClass = testClass2;
    }

    public void validateInstanceMethods() {
        validateTestMethods(After.class, false);
        validateTestMethods(Before.class, false);
        validateTestMethods(Test.class, false);
        if (this.testClass.getAnnotatedMethods(Test.class).size() == 0) {
            this.errors.add(new Exception("No runnable methods"));
        }
    }

    public void validateStaticMethods() {
        validateTestMethods(BeforeClass.class, true);
        validateTestMethods(AfterClass.class, true);
    }

    public List<Throwable> validateMethodsForDefaultRunner() {
        validateNoArgConstructor();
        validateStaticMethods();
        validateInstanceMethods();
        return this.errors;
    }

    public void assertValid() throws InitializationError {
        if (!this.errors.isEmpty()) {
            throw new InitializationError(this.errors);
        }
    }

    public void validateNoArgConstructor() {
        try {
            this.testClass.getConstructor();
        } catch (Exception e) {
            this.errors.add(new Exception("Test class should have public zero-argument constructor", e));
        }
    }

    private void validateTestMethods(Class<? extends Annotation> cls, boolean z) {
        for (Method method : this.testClass.getAnnotatedMethods(cls)) {
            String str = "Method ";
            if (Modifier.isStatic(method.getModifiers()) != z) {
                String str2 = z ? "should" : "should not";
                List<Throwable> list = this.errors;
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(method.getName());
                sb.append("() ");
                sb.append(str2);
                sb.append(" be static");
                list.add(new Exception(sb.toString()));
            }
            String str3 = " should be public";
            if (!Modifier.isPublic(method.getDeclaringClass().getModifiers())) {
                List<Throwable> list2 = this.errors;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Class ");
                sb2.append(method.getDeclaringClass().getName());
                sb2.append(str3);
                list2.add(new Exception(sb2.toString()));
            }
            if (!Modifier.isPublic(method.getModifiers())) {
                List<Throwable> list3 = this.errors;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str);
                sb3.append(method.getName());
                sb3.append(str3);
                list3.add(new Exception(sb3.toString()));
            }
            if (method.getReturnType() != Void.TYPE) {
                List<Throwable> list4 = this.errors;
                StringBuilder sb4 = new StringBuilder();
                sb4.append(str);
                sb4.append(method.getName());
                sb4.append(" should be void");
                list4.add(new Exception(sb4.toString()));
            }
            if (method.getParameterTypes().length != 0) {
                List<Throwable> list5 = this.errors;
                StringBuilder sb5 = new StringBuilder();
                sb5.append(str);
                sb5.append(method.getName());
                sb5.append(" should have no parameters");
                list5.add(new Exception(sb5.toString()));
            }
        }
    }
}

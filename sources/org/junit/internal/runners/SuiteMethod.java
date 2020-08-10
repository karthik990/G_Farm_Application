package org.junit.internal.runners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import junit.framework.Test;
import junit.runner.BaseTestRunner;

public class SuiteMethod extends JUnit38ClassRunner {
    public SuiteMethod(Class<?> cls) throws Throwable {
        super(testFromSuiteMethod(cls));
    }

    public static Test testFromSuiteMethod(Class<?> cls) throws Throwable {
        try {
            Method method = cls.getMethod(BaseTestRunner.SUITE_METHODNAME, new Class[0]);
            if (Modifier.isStatic(method.getModifiers())) {
                return (Test) method.invoke(null, new Object[0]);
            }
            StringBuilder sb = new StringBuilder();
            sb.append(cls.getName());
            sb.append(".suite() must be static");
            throw new Exception(sb.toString());
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}

package org.junit.internal.runners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.Test.None;

@Deprecated
public class TestMethod {
    private final Method method;
    private TestClass testClass;

    public TestMethod(Method method2, TestClass testClass2) {
        this.method = method2;
        this.testClass = testClass2;
    }

    public boolean isIgnored() {
        return this.method.getAnnotation(Ignore.class) != null;
    }

    public long getTimeout() {
        Test test = (Test) this.method.getAnnotation(Test.class);
        if (test == null) {
            return 0;
        }
        return test.timeout();
    }

    /* access modifiers changed from: protected */
    public Class<? extends Throwable> getExpectedException() {
        Test test = (Test) this.method.getAnnotation(Test.class);
        if (test == null || test.expected() == None.class) {
            return null;
        }
        return test.expected();
    }

    /* access modifiers changed from: 0000 */
    public boolean isUnexpected(Throwable th) {
        return !getExpectedException().isAssignableFrom(th.getClass());
    }

    /* access modifiers changed from: 0000 */
    public boolean expectsException() {
        return getExpectedException() != null;
    }

    /* access modifiers changed from: 0000 */
    public List<Method> getBefores() {
        return this.testClass.getAnnotatedMethods(Before.class);
    }

    /* access modifiers changed from: 0000 */
    public List<Method> getAfters() {
        return this.testClass.getAnnotatedMethods(After.class);
    }

    public void invoke(Object obj) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        this.method.invoke(obj, new Object[0]);
    }
}

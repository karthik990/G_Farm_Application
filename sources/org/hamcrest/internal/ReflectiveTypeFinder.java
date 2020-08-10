package org.hamcrest.internal;

import java.lang.reflect.Method;

public class ReflectiveTypeFinder {
    private final int expectedNumberOfParameters;
    private final String methodName;
    private final int typedParameter;

    public ReflectiveTypeFinder(String str, int i, int i2) {
        this.methodName = str;
        this.expectedNumberOfParameters = i;
        this.typedParameter = i2;
    }

    public Class<?> findExpectedType(Class<?> cls) {
        Method[] declaredMethods;
        while (cls != Object.class) {
            for (Method method : cls.getDeclaredMethods()) {
                if (canObtainExpectedTypeFrom(method)) {
                    return expectedTypeFrom(method);
                }
            }
            cls = cls.getSuperclass();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Cannot determine correct type for ");
        sb.append(this.methodName);
        sb.append("() method.");
        throw new Error(sb.toString());
    }

    /* access modifiers changed from: protected */
    public boolean canObtainExpectedTypeFrom(Method method) {
        return method.getName().equals(this.methodName) && method.getParameterTypes().length == this.expectedNumberOfParameters && !method.isSynthetic();
    }

    /* access modifiers changed from: protected */
    public Class<?> expectedTypeFrom(Method method) {
        return method.getParameterTypes()[this.typedParameter];
    }
}

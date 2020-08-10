package org.junit.internal;

public class Classes {
    public static Class<?> getClass(String str) throws ClassNotFoundException {
        return Class.forName(str, true, Thread.currentThread().getContextClassLoader());
    }
}

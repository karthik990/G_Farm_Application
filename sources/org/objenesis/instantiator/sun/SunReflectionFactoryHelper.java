package org.objenesis.instantiator.sun;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.objenesis.ObjenesisException;

class SunReflectionFactoryHelper {
    SunReflectionFactoryHelper() {
    }

    public static <T> Constructor<T> newConstructorForSerialization(Class<T> cls, Constructor<?> constructor) {
        Class reflectionFactoryClass = getReflectionFactoryClass();
        Object createReflectionFactory = createReflectionFactory(reflectionFactoryClass);
        try {
            return (Constructor) getNewConstructorForSerializationMethod(reflectionFactoryClass).invoke(createReflectionFactory, new Object[]{cls, constructor});
        } catch (IllegalArgumentException e) {
            throw new ObjenesisException((Throwable) e);
        } catch (IllegalAccessException e2) {
            throw new ObjenesisException((Throwable) e2);
        } catch (InvocationTargetException e3) {
            throw new ObjenesisException((Throwable) e3);
        }
    }

    private static Class<?> getReflectionFactoryClass() {
        try {
            return Class.forName("sun.reflect.ReflectionFactory");
        } catch (ClassNotFoundException e) {
            throw new ObjenesisException((Throwable) e);
        }
    }

    private static Object createReflectionFactory(Class<?> cls) {
        try {
            return cls.getDeclaredMethod("getReflectionFactory", new Class[0]).invoke(null, new Object[0]);
        } catch (NoSuchMethodException e) {
            throw new ObjenesisException((Throwable) e);
        } catch (IllegalAccessException e2) {
            throw new ObjenesisException((Throwable) e2);
        } catch (IllegalArgumentException e3) {
            throw new ObjenesisException((Throwable) e3);
        } catch (InvocationTargetException e4) {
            throw new ObjenesisException((Throwable) e4);
        }
    }

    private static Method getNewConstructorForSerializationMethod(Class<?> cls) {
        try {
            return cls.getDeclaredMethod("newConstructorForSerialization", new Class[]{Class.class, Constructor.class});
        } catch (NoSuchMethodException e) {
            throw new ObjenesisException((Throwable) e);
        }
    }
}

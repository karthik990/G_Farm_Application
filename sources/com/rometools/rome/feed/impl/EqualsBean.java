package com.rometools.rome.feed.impl;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Method;

public class EqualsBean implements Serializable {
    private static final Object[] NO_PARAMS = new Object[0];
    private static final long serialVersionUID = 1;
    private final Class<?> beanClass;
    private final Object obj;

    protected EqualsBean(Class<?> cls) {
        this.beanClass = cls;
        this.obj = this;
    }

    public EqualsBean(Class<?> cls, Object obj2) {
        if (cls.isInstance(obj2)) {
            this.beanClass = cls;
            this.obj = obj2;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(obj2.getClass());
        sb.append(" is not instance of ");
        sb.append(cls);
        throw new IllegalArgumentException(sb.toString());
    }

    public boolean equals(Object obj2) {
        return beanEquals(obj2);
    }

    public boolean beanEquals(Object obj2) {
        Object obj3 = this.obj;
        boolean z = true;
        if (obj3 == null && obj2 == null) {
            return true;
        }
        if (obj3 == null || obj2 == null || !this.beanClass.isInstance(obj2)) {
            return false;
        }
        try {
            for (PropertyDescriptor readMethod : BeanIntrospector.getPropertyDescriptorsWithGetters(this.beanClass)) {
                Method readMethod2 = readMethod.getReadMethod();
                z = doEquals(readMethod2.invoke(obj3, NO_PARAMS), readMethod2.invoke(obj2, NO_PARAMS));
                if (!z) {
                    return z;
                }
            }
            return z;
        } catch (Exception e) {
            throw new RuntimeException("Could not execute equals()", e);
        }
    }

    public int hashCode() {
        return beanHashCode();
    }

    public int beanHashCode() {
        return this.obj.toString().hashCode();
    }

    private boolean doEquals(Object obj2, Object obj3) {
        boolean z = obj2 == obj3;
        if (z || obj2 == null || obj3 == null) {
            return z;
        }
        Class cls = obj2.getClass();
        Class cls2 = obj3.getClass();
        if (!cls.isArray() || !cls2.isArray()) {
            return obj2.equals(obj3);
        }
        return equalsArray(obj2, obj3);
    }

    private boolean equalsArray(Object obj2, Object obj3) {
        int length = Array.getLength(obj2);
        boolean z = false;
        if (length == Array.getLength(obj3)) {
            int i = 0;
            z = true;
            while (z && i < length) {
                z = doEquals(Array.get(obj2, i), Array.get(obj3, i));
                i++;
            }
        }
        return z;
    }
}

package com.rometools.rome.feed.impl;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class PropertyDescriptor {
    private final Method getter;
    private final String name;
    private final Method setter;

    public PropertyDescriptor(String str, Method method, Method method2) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Bad property name");
        }
        this.name = str;
        this.getter = checkGetter(method);
        this.setter = checkSetter(method2);
    }

    public String getName() {
        return this.name;
    }

    public Method getReadMethod() {
        return this.getter;
    }

    public Method getWriteMethod() {
        return this.setter;
    }

    public Class<?> getPropertyType() {
        Method method = this.getter;
        if (method != null) {
            return method.getReturnType();
        }
        Method method2 = this.setter;
        if (method2 != null) {
            return method2.getParameterTypes()[0];
        }
        return null;
    }

    private Method checkGetter(Method method) {
        if (method != null) {
            if (!Modifier.isPublic(method.getModifiers())) {
                throw new IllegalArgumentException("Modifier for getter method should be public");
            } else if (method.getParameterTypes().length == 0) {
                Class returnType = method.getReturnType();
                if (!returnType.equals(Void.TYPE)) {
                    Class propertyType = getPropertyType();
                    if (propertyType != null && !returnType.equals(propertyType)) {
                        throw new IllegalArgumentException("Parameter type in getter does not correspond to setter");
                    }
                } else {
                    throw new IllegalArgumentException("Getter has return type void");
                }
            } else {
                throw new IllegalArgumentException("Number of parameters in getter method is not equal to 0");
            }
        }
        return method;
    }

    private Method checkSetter(Method method) {
        if (method != null) {
            if (Modifier.isPublic(method.getModifiers())) {
                Class[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 1) {
                    Class cls = parameterTypes[0];
                    Class propertyType = getPropertyType();
                    if (propertyType != null && !propertyType.equals(cls)) {
                        throw new IllegalArgumentException("Parameter type in setter does not correspond to getter");
                    }
                } else {
                    throw new IllegalArgumentException("Number of parameters in setter method is not equal to 1");
                }
            } else {
                throw new IllegalArgumentException("Modifier for setter method should be public");
            }
        }
        return method;
    }
}

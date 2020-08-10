package org.junit.runners.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import org.junit.internal.runners.model.ReflectiveCallable;

public class FrameworkMethod extends FrameworkMember<FrameworkMethod> {
    /* access modifiers changed from: private */
    public final Method method;

    public FrameworkMethod(Method method2) {
        if (method2 != null) {
            this.method = method2;
            return;
        }
        throw new NullPointerException("FrameworkMethod cannot be created without an underlying method.");
    }

    public Method getMethod() {
        return this.method;
    }

    public Object invokeExplosively(final Object obj, final Object... objArr) throws Throwable {
        return new ReflectiveCallable() {
            /* access modifiers changed from: protected */
            public Object runReflectiveCall() throws Throwable {
                return FrameworkMethod.this.method.invoke(obj, objArr);
            }
        }.run();
    }

    public String getName() {
        return this.method.getName();
    }

    public void validatePublicVoidNoArg(boolean z, List<Throwable> list) {
        validatePublicVoid(z, list);
        if (this.method.getParameterTypes().length != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Method ");
            sb.append(this.method.getName());
            sb.append(" should have no parameters");
            list.add(new Exception(sb.toString()));
        }
    }

    public void validatePublicVoid(boolean z, List<Throwable> list) {
        String str = "Method ";
        if (isStatic() != z) {
            String str2 = z ? "should" : "should not";
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(this.method.getName());
            sb.append("() ");
            sb.append(str2);
            sb.append(" be static");
            list.add(new Exception(sb.toString()));
        }
        if (!isPublic()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(this.method.getName());
            sb2.append("() should be public");
            list.add(new Exception(sb2.toString()));
        }
        if (this.method.getReturnType() != Void.TYPE) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(this.method.getName());
            sb3.append("() should be void");
            list.add(new Exception(sb3.toString()));
        }
    }

    /* access modifiers changed from: protected */
    public int getModifiers() {
        return this.method.getModifiers();
    }

    public Class<?> getReturnType() {
        return this.method.getReturnType();
    }

    public Class<?> getType() {
        return getReturnType();
    }

    public Class<?> getDeclaringClass() {
        return this.method.getDeclaringClass();
    }

    public void validateNoTypeParametersOnArgs(List<Throwable> list) {
        new NoGenericTypeParametersValidator(this.method).validate(list);
    }

    public boolean isShadowedBy(FrameworkMethod frameworkMethod) {
        if (!frameworkMethod.getName().equals(getName()) || frameworkMethod.getParameterTypes().length != getParameterTypes().length) {
            return false;
        }
        for (int i = 0; i < frameworkMethod.getParameterTypes().length; i++) {
            if (!frameworkMethod.getParameterTypes()[i].equals(getParameterTypes()[i])) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object obj) {
        if (!FrameworkMethod.class.isInstance(obj)) {
            return false;
        }
        return ((FrameworkMethod) obj).method.equals(this.method);
    }

    public int hashCode() {
        return this.method.hashCode();
    }

    @Deprecated
    public boolean producesType(Type type) {
        return getParameterTypes().length == 0 && (type instanceof Class) && ((Class) type).isAssignableFrom(this.method.getReturnType());
    }

    private Class<?>[] getParameterTypes() {
        return this.method.getParameterTypes();
    }

    public Annotation[] getAnnotations() {
        return this.method.getAnnotations();
    }

    public <T extends Annotation> T getAnnotation(Class<T> cls) {
        return this.method.getAnnotation(cls);
    }

    public String toString() {
        return this.method.toString();
    }
}

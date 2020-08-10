package com.startapp.common.p042c;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* renamed from: com.startapp.common.c.c */
/* compiled from: StartAppSDK */
public class C5181c {
    /* renamed from: a */
    public static String m3912a(Field field) {
        Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
        if (declaredAnnotations != null && declaredAnnotations.length > 0) {
            Annotation annotation = field.getDeclaredAnnotations()[0];
            if (annotation.annotationType().equals(C2362f.class)) {
                C2362f fVar = (C2362f) annotation;
                if (!"".equals(fVar.mo20790f())) {
                    return fVar.mo20790f();
                }
            }
        }
        return field.getName();
    }

    /* renamed from: b */
    public static boolean m3914b(Field field) {
        return Map.class.isAssignableFrom(field.getType());
    }

    /* renamed from: c */
    public static boolean m3915c(Field field) {
        return List.class.isAssignableFrom(field.getType());
    }

    /* renamed from: d */
    public static boolean m3916d(Field field) {
        return Set.class.isAssignableFrom(field.getType());
    }

    /* renamed from: e */
    public static boolean m3917e(Field field) {
        Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
        if (declaredAnnotations == null || declaredAnnotations.length == 0) {
            return false;
        }
        Annotation annotation = field.getDeclaredAnnotations()[0];
        if (!annotation.annotationType().equals(C2362f.class)) {
            return false;
        }
        return ((C2362f) annotation).mo20785a();
    }

    /* renamed from: a */
    public static boolean m3913a(Object obj) {
        Class cls = obj.getClass();
        return cls.equals(Boolean.class) || cls.equals(Integer.class) || cls.equals(Character.class) || cls.equals(Byte.class) || cls.equals(Short.class) || cls.equals(Double.class) || cls.equals(Long.class) || cls.equals(Float.class) || cls.equals(String.class);
    }
}

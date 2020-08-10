package org.jdom2.internal;

import java.lang.reflect.InvocationTargetException;

public class ReflectionConstructor {
    public static final <E> E construct(String str, Class<E> cls) {
        String str2 = "Unable to access class constructor '";
        String str3 = "'.";
        try {
            Class cls2 = Class.forName(str);
            if (cls.isAssignableFrom(cls2)) {
                return cls.cast(cls2.getConstructor(new Class[0]).newInstance(new Object[0]));
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Class '");
            sb.append(str);
            sb.append("' is not assignable to '");
            sb.append(cls.getName());
            sb.append(str3);
            throw new ClassCastException(sb.toString());
        } catch (ClassNotFoundException e) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Unable to locate class '");
            sb2.append(str);
            sb2.append(str3);
            throw new IllegalArgumentException(sb2.toString(), e);
        } catch (NoSuchMethodException e2) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Unable to locate class no-arg constructor '");
            sb3.append(str);
            sb3.append(str3);
            throw new IllegalArgumentException(sb3.toString(), e2);
        } catch (SecurityException e3) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(str2);
            sb4.append(str);
            sb4.append(str3);
            throw new IllegalStateException(sb4.toString(), e3);
        } catch (IllegalAccessException e4) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(str2);
            sb5.append(str);
            sb5.append(str3);
            throw new IllegalStateException(sb5.toString(), e4);
        } catch (InstantiationException e5) {
            StringBuilder sb6 = new StringBuilder();
            sb6.append("Unable to instantiate class '");
            sb6.append(str);
            sb6.append(str3);
            throw new IllegalStateException(sb6.toString(), e5);
        } catch (InvocationTargetException e6) {
            StringBuilder sb7 = new StringBuilder();
            sb7.append("Unable to call class constructor '");
            sb7.append(str);
            sb7.append(str3);
            throw new IllegalStateException(sb7.toString(), e6);
        }
    }
}

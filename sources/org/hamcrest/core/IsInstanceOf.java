package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsInstanceOf extends DiagnosingMatcher<Object> {
    private final Class<?> expectedClass;
    private final Class<?> matchableClass;

    public IsInstanceOf(Class<?> cls) {
        this.expectedClass = cls;
        this.matchableClass = matchableClass(cls);
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Class<?>, code=java.lang.Class, for r1v0, types: [java.lang.Class<?>, java.lang.Class, java.lang.Object] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.Class<?> matchableClass(java.lang.Class r1) {
        /*
            java.lang.Class r0 = java.lang.Boolean.TYPE
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000b
            java.lang.Class<java.lang.Boolean> r1 = java.lang.Boolean.class
            return r1
        L_0x000b:
            java.lang.Class r0 = java.lang.Byte.TYPE
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0016
            java.lang.Class<java.lang.Byte> r1 = java.lang.Byte.class
            return r1
        L_0x0016:
            java.lang.Class r0 = java.lang.Character.TYPE
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0021
            java.lang.Class<java.lang.Character> r1 = java.lang.Character.class
            return r1
        L_0x0021:
            java.lang.Class r0 = java.lang.Double.TYPE
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x002c
            java.lang.Class<java.lang.Double> r1 = java.lang.Double.class
            return r1
        L_0x002c:
            java.lang.Class r0 = java.lang.Float.TYPE
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0037
            java.lang.Class<java.lang.Float> r1 = java.lang.Float.class
            return r1
        L_0x0037:
            java.lang.Class r0 = java.lang.Integer.TYPE
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0042
            java.lang.Class<java.lang.Integer> r1 = java.lang.Integer.class
            return r1
        L_0x0042:
            java.lang.Class r0 = java.lang.Long.TYPE
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x004d
            java.lang.Class<java.lang.Long> r1 = java.lang.Long.class
            return r1
        L_0x004d:
            java.lang.Class r0 = java.lang.Short.TYPE
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0057
            java.lang.Class<java.lang.Short> r1 = java.lang.Short.class
        L_0x0057:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.hamcrest.core.IsInstanceOf.matchableClass(java.lang.Class):java.lang.Class");
    }

    /* access modifiers changed from: protected */
    public boolean matches(Object obj, Description description) {
        if (obj == null) {
            description.appendText("null");
            return false;
        } else if (this.matchableClass.isInstance(obj)) {
            return true;
        } else {
            Description appendValue = description.appendValue(obj);
            StringBuilder sb = new StringBuilder();
            sb.append(" is a ");
            sb.append(obj.getClass().getName());
            appendValue.appendText(sb.toString());
            return false;
        }
    }

    public void describeTo(Description description) {
        description.appendText("an instance of ").appendText(this.expectedClass.getName());
    }

    @Factory
    public static <T> Matcher<T> instanceOf(Class<?> cls) {
        return new IsInstanceOf(cls);
    }

    @Factory
    public static <T> Matcher<T> any(Class<T> cls) {
        return new IsInstanceOf(cls);
    }
}

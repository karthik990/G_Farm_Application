package com.nineoldandroids.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ReflectiveProperty<T, V> extends Property<T, V> {
    private static final String PREFIX_GET = "get";
    private static final String PREFIX_IS = "is";
    private static final String PREFIX_SET = "set";
    private Field mField;
    private Method mGetter;
    private Method mSetter;

    /* JADX WARNING: Can't wrap try/catch for region: R(3:22|23|(1:25)(2:26|27)) */
    /* JADX WARNING: Can't wrap try/catch for region: R(6:8|7|9|10|11|12) */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r10.mField = r11.getField(r13);
        r11 = r10.mField.getType();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00d9, code lost:
        if (typesMatch(r12, r11) != false) goto L_0x00db;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00db, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00dc, code lost:
        r5 = new java.lang.StringBuilder();
        r5.append(r3);
        r5.append(r11);
        r5.append(r2);
        r5.append(r1);
        r5.append(r12);
        r5.append(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00fc, code lost:
        throw new com.nineoldandroids.util.NoSuchPropertyException(r5.toString());
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x006e */
    /* JADX WARNING: Missing exception handler attribute for start block: B:22:0x00c9 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ReflectiveProperty(java.lang.Class<T> r11, java.lang.Class<V> r12, java.lang.String r13) {
        /*
            r10 = this;
            java.lang.String r0 = ")"
            java.lang.String r1 = "does not match Property type ("
            java.lang.String r2 = ") "
            java.lang.String r3 = "Underlying type ("
            r10.<init>(r12, r13)
            r4 = 0
            char r5 = r13.charAt(r4)
            char r5 = java.lang.Character.toUpperCase(r5)
            r6 = 1
            java.lang.String r7 = r13.substring(r6)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r5)
            r8.append(r7)
            java.lang.String r5 = r8.toString()
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "get"
            r7.append(r8)
            r7.append(r5)
            java.lang.String r7 = r7.toString()
            r8 = 0
            r9 = r8
            java.lang.Class[] r9 = (java.lang.Class[]) r9     // Catch:{ NoSuchMethodException -> 0x0044 }
            java.lang.reflect.Method r9 = r11.getMethod(r7, r9)     // Catch:{ NoSuchMethodException -> 0x0044 }
            r10.mGetter = r9     // Catch:{ NoSuchMethodException -> 0x0044 }
            goto L_0x007b
        L_0x0044:
            r9 = r8
            java.lang.Class[] r9 = (java.lang.Class[]) r9     // Catch:{ NoSuchMethodException -> 0x0053 }
            java.lang.reflect.Method r7 = r11.getDeclaredMethod(r7, r9)     // Catch:{ NoSuchMethodException -> 0x0053 }
            r10.mGetter = r7     // Catch:{ NoSuchMethodException -> 0x0053 }
            java.lang.reflect.Method r7 = r10.mGetter     // Catch:{ NoSuchMethodException -> 0x0053 }
            r7.setAccessible(r6)     // Catch:{ NoSuchMethodException -> 0x0053 }
            goto L_0x007b
        L_0x0053:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r9 = "is"
            r7.append(r9)
            r7.append(r5)
            java.lang.String r7 = r7.toString()
            r9 = r8
            java.lang.Class[] r9 = (java.lang.Class[]) r9     // Catch:{ NoSuchMethodException -> 0x006e }
            java.lang.reflect.Method r9 = r11.getMethod(r7, r9)     // Catch:{ NoSuchMethodException -> 0x006e }
            r10.mGetter = r9     // Catch:{ NoSuchMethodException -> 0x006e }
            goto L_0x007b
        L_0x006e:
            java.lang.Class[] r8 = (java.lang.Class[]) r8     // Catch:{ NoSuchMethodException -> 0x00c9 }
            java.lang.reflect.Method r7 = r11.getDeclaredMethod(r7, r8)     // Catch:{ NoSuchMethodException -> 0x00c9 }
            r10.mGetter = r7     // Catch:{ NoSuchMethodException -> 0x00c9 }
            java.lang.reflect.Method r7 = r10.mGetter     // Catch:{ NoSuchMethodException -> 0x00c9 }
            r7.setAccessible(r6)     // Catch:{ NoSuchMethodException -> 0x00c9 }
        L_0x007b:
            java.lang.reflect.Method r13 = r10.mGetter
            java.lang.Class r13 = r13.getReturnType()
            boolean r7 = r10.typesMatch(r12, r13)
            if (r7 == 0) goto L_0x00a8
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r0 = "set"
            r12.append(r0)
            r12.append(r5)
            java.lang.String r12 = r12.toString()
            java.lang.Class[] r0 = new java.lang.Class[r6]     // Catch:{ NoSuchMethodException -> 0x00a7 }
            r0[r4] = r13     // Catch:{ NoSuchMethodException -> 0x00a7 }
            java.lang.reflect.Method r11 = r11.getDeclaredMethod(r12, r0)     // Catch:{ NoSuchMethodException -> 0x00a7 }
            r10.mSetter = r11     // Catch:{ NoSuchMethodException -> 0x00a7 }
            java.lang.reflect.Method r11 = r10.mSetter     // Catch:{ NoSuchMethodException -> 0x00a7 }
            r11.setAccessible(r6)     // Catch:{ NoSuchMethodException -> 0x00a7 }
        L_0x00a7:
            return
        L_0x00a8:
            com.nineoldandroids.util.NoSuchPropertyException r11 = new com.nineoldandroids.util.NoSuchPropertyException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r3)
            r4.append(r13)
            r4.append(r2)
            r4.append(r1)
            r4.append(r12)
            r4.append(r0)
            java.lang.String r12 = r4.toString()
            r11.<init>(r12)
            throw r11
        L_0x00c9:
            java.lang.reflect.Field r11 = r11.getField(r13)     // Catch:{ NoSuchFieldException -> 0x00fd }
            r10.mField = r11     // Catch:{ NoSuchFieldException -> 0x00fd }
            java.lang.reflect.Field r11 = r10.mField     // Catch:{ NoSuchFieldException -> 0x00fd }
            java.lang.Class r11 = r11.getType()     // Catch:{ NoSuchFieldException -> 0x00fd }
            boolean r4 = r10.typesMatch(r12, r11)     // Catch:{ NoSuchFieldException -> 0x00fd }
            if (r4 == 0) goto L_0x00dc
            return
        L_0x00dc:
            com.nineoldandroids.util.NoSuchPropertyException r4 = new com.nineoldandroids.util.NoSuchPropertyException     // Catch:{ NoSuchFieldException -> 0x00fd }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ NoSuchFieldException -> 0x00fd }
            r5.<init>()     // Catch:{ NoSuchFieldException -> 0x00fd }
            r5.append(r3)     // Catch:{ NoSuchFieldException -> 0x00fd }
            r5.append(r11)     // Catch:{ NoSuchFieldException -> 0x00fd }
            r5.append(r2)     // Catch:{ NoSuchFieldException -> 0x00fd }
            r5.append(r1)     // Catch:{ NoSuchFieldException -> 0x00fd }
            r5.append(r12)     // Catch:{ NoSuchFieldException -> 0x00fd }
            r5.append(r0)     // Catch:{ NoSuchFieldException -> 0x00fd }
            java.lang.String r11 = r5.toString()     // Catch:{ NoSuchFieldException -> 0x00fd }
            r4.<init>(r11)     // Catch:{ NoSuchFieldException -> 0x00fd }
            throw r4     // Catch:{ NoSuchFieldException -> 0x00fd }
        L_0x00fd:
            com.nineoldandroids.util.NoSuchPropertyException r11 = new com.nineoldandroids.util.NoSuchPropertyException
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r0 = "No accessor method or field found for property with name "
            r12.append(r0)
            r12.append(r13)
            java.lang.String r12 = r12.toString()
            r11.<init>(r12)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nineoldandroids.util.ReflectiveProperty.<init>(java.lang.Class, java.lang.Class, java.lang.String):void");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Class, code=java.lang.Class<V>, for r5v0, types: [java.lang.Class<V>, java.lang.Class] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean typesMatch(java.lang.Class<V> r4, java.lang.Class<V> r5) {
        /*
            r3 = this;
            r0 = 1
            if (r5 == r4) goto L_0x004e
            boolean r1 = r5.isPrimitive()
            r2 = 0
            if (r1 == 0) goto L_0x004d
            java.lang.Class r1 = java.lang.Float.TYPE
            if (r5 != r1) goto L_0x0012
            java.lang.Class<java.lang.Float> r1 = java.lang.Float.class
            if (r4 == r1) goto L_0x004c
        L_0x0012:
            java.lang.Class r1 = java.lang.Integer.TYPE
            if (r5 != r1) goto L_0x001a
            java.lang.Class<java.lang.Integer> r1 = java.lang.Integer.class
            if (r4 == r1) goto L_0x004c
        L_0x001a:
            java.lang.Class r1 = java.lang.Boolean.TYPE
            if (r5 != r1) goto L_0x0022
            java.lang.Class<java.lang.Boolean> r1 = java.lang.Boolean.class
            if (r4 == r1) goto L_0x004c
        L_0x0022:
            java.lang.Class r1 = java.lang.Long.TYPE
            if (r5 != r1) goto L_0x002a
            java.lang.Class<java.lang.Long> r1 = java.lang.Long.class
            if (r4 == r1) goto L_0x004c
        L_0x002a:
            java.lang.Class r1 = java.lang.Double.TYPE
            if (r5 != r1) goto L_0x0032
            java.lang.Class<java.lang.Double> r1 = java.lang.Double.class
            if (r4 == r1) goto L_0x004c
        L_0x0032:
            java.lang.Class r1 = java.lang.Short.TYPE
            if (r5 != r1) goto L_0x003a
            java.lang.Class<java.lang.Short> r1 = java.lang.Short.class
            if (r4 == r1) goto L_0x004c
        L_0x003a:
            java.lang.Class r1 = java.lang.Byte.TYPE
            if (r5 != r1) goto L_0x0042
            java.lang.Class<java.lang.Byte> r1 = java.lang.Byte.class
            if (r4 == r1) goto L_0x004c
        L_0x0042:
            java.lang.Class r1 = java.lang.Character.TYPE
            if (r5 != r1) goto L_0x004b
            java.lang.Class<java.lang.Character> r5 = java.lang.Character.class
            if (r4 != r5) goto L_0x004b
            goto L_0x004c
        L_0x004b:
            r0 = 0
        L_0x004c:
            return r0
        L_0x004d:
            return r2
        L_0x004e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nineoldandroids.util.ReflectiveProperty.typesMatch(java.lang.Class, java.lang.Class):boolean");
    }

    public void set(T t, V v) {
        Method method = this.mSetter;
        if (method != null) {
            try {
                method.invoke(t, new Object[]{v});
            } catch (IllegalAccessException unused) {
                throw new AssertionError();
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e.getCause());
            }
        } else {
            Field field = this.mField;
            if (field != null) {
                try {
                    field.set(t, v);
                } catch (IllegalAccessException unused2) {
                    throw new AssertionError();
                }
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Property ");
                sb.append(getName());
                sb.append(" is read-only");
                throw new UnsupportedOperationException(sb.toString());
            }
        }
    }

    public V get(T t) {
        Method method = this.mGetter;
        if (method != null) {
            try {
                return method.invoke(t, null);
            } catch (IllegalAccessException unused) {
                throw new AssertionError();
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e.getCause());
            }
        } else {
            Field field = this.mField;
            if (field != null) {
                try {
                    return field.get(t);
                } catch (IllegalAccessException unused2) {
                    throw new AssertionError();
                }
            } else {
                throw new AssertionError();
            }
        }
    }

    public boolean isReadOnly() {
        return this.mSetter == null && this.mField == null;
    }
}

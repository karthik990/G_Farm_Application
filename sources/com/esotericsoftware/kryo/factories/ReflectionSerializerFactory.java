package com.esotericsoftware.kryo.factories;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;

public class ReflectionSerializerFactory implements SerializerFactory {
    private final Class<? extends Serializer> serializerClass;

    public ReflectionSerializerFactory(Class<? extends Serializer> cls) {
        this.serializerClass = cls;
    }

    public Serializer makeSerializer(Kryo kryo, Class<?> cls) {
        return makeSerializer(kryo, this.serializerClass, cls);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(3:5|6|7) */
    /* JADX WARNING: Can't wrap try/catch for region: R(3:8|9|10) */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0049, code lost:
        return (com.esotericsoftware.kryo.Serializer) r6.getConstructor(new java.lang.Class[]{java.lang.Class.class}).newInstance(new java.lang.Object[]{r7});
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0050, code lost:
        return (com.esotericsoftware.kryo.Serializer) r6.newInstance();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0051, code lost:
        r1 = new java.lang.StringBuilder();
        r1.append("Unable to create serializer \"");
        r1.append(r6.getName());
        r1.append("\" for class: ");
        r1.append(com.esotericsoftware.kryo.util.Util.className(r7));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0077, code lost:
        throw new java.lang.IllegalArgumentException(r1.toString(), r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:4:0x001e, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0034, code lost:
        return (com.esotericsoftware.kryo.Serializer) r6.getConstructor(new java.lang.Class[]{com.esotericsoftware.kryo.Kryo.class}).newInstance(new java.lang.Object[]{r5});
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x004a */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x0020 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x0035 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.esotericsoftware.kryo.Serializer makeSerializer(com.esotericsoftware.kryo.Kryo r5, java.lang.Class<? extends com.esotericsoftware.kryo.Serializer> r6, java.lang.Class<?> r7) {
        /*
            r0 = 2
            r1 = 1
            r2 = 0
            java.lang.Class[] r3 = new java.lang.Class[r0]     // Catch:{ NoSuchMethodException -> 0x0020 }
            java.lang.Class<com.esotericsoftware.kryo.Kryo> r4 = com.esotericsoftware.kryo.Kryo.class
            r3[r2] = r4     // Catch:{ NoSuchMethodException -> 0x0020 }
            java.lang.Class<java.lang.Class> r4 = java.lang.Class.class
            r3[r1] = r4     // Catch:{ NoSuchMethodException -> 0x0020 }
            java.lang.reflect.Constructor r3 = r6.getConstructor(r3)     // Catch:{ NoSuchMethodException -> 0x0020 }
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ NoSuchMethodException -> 0x0020 }
            r0[r2] = r5     // Catch:{ NoSuchMethodException -> 0x0020 }
            r0[r1] = r7     // Catch:{ NoSuchMethodException -> 0x0020 }
            java.lang.Object r0 = r3.newInstance(r0)     // Catch:{ NoSuchMethodException -> 0x0020 }
            com.esotericsoftware.kryo.Serializer r0 = (com.esotericsoftware.kryo.Serializer) r0     // Catch:{ NoSuchMethodException -> 0x0020 }
            return r0
        L_0x001e:
            r5 = move-exception
            goto L_0x0051
        L_0x0020:
            java.lang.Class[] r0 = new java.lang.Class[r1]     // Catch:{ NoSuchMethodException -> 0x0035 }
            java.lang.Class<com.esotericsoftware.kryo.Kryo> r3 = com.esotericsoftware.kryo.Kryo.class
            r0[r2] = r3     // Catch:{ NoSuchMethodException -> 0x0035 }
            java.lang.reflect.Constructor r0 = r6.getConstructor(r0)     // Catch:{ NoSuchMethodException -> 0x0035 }
            java.lang.Object[] r3 = new java.lang.Object[r1]     // Catch:{ NoSuchMethodException -> 0x0035 }
            r3[r2] = r5     // Catch:{ NoSuchMethodException -> 0x0035 }
            java.lang.Object r5 = r0.newInstance(r3)     // Catch:{ NoSuchMethodException -> 0x0035 }
            com.esotericsoftware.kryo.Serializer r5 = (com.esotericsoftware.kryo.Serializer) r5     // Catch:{ NoSuchMethodException -> 0x0035 }
            return r5
        L_0x0035:
            java.lang.Class[] r5 = new java.lang.Class[r1]     // Catch:{ NoSuchMethodException -> 0x004a }
            java.lang.Class<java.lang.Class> r0 = java.lang.Class.class
            r5[r2] = r0     // Catch:{ NoSuchMethodException -> 0x004a }
            java.lang.reflect.Constructor r5 = r6.getConstructor(r5)     // Catch:{ NoSuchMethodException -> 0x004a }
            java.lang.Object[] r0 = new java.lang.Object[r1]     // Catch:{ NoSuchMethodException -> 0x004a }
            r0[r2] = r7     // Catch:{ NoSuchMethodException -> 0x004a }
            java.lang.Object r5 = r5.newInstance(r0)     // Catch:{ NoSuchMethodException -> 0x004a }
            com.esotericsoftware.kryo.Serializer r5 = (com.esotericsoftware.kryo.Serializer) r5     // Catch:{ NoSuchMethodException -> 0x004a }
            return r5
        L_0x004a:
            java.lang.Object r5 = r6.newInstance()     // Catch:{ Exception -> 0x001e }
            com.esotericsoftware.kryo.Serializer r5 = (com.esotericsoftware.kryo.Serializer) r5     // Catch:{ Exception -> 0x001e }
            return r5
        L_0x0051:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Unable to create serializer \""
            r1.append(r2)
            java.lang.String r6 = r6.getName()
            r1.append(r6)
            java.lang.String r6 = "\" for class: "
            r1.append(r6)
            java.lang.String r6 = com.esotericsoftware.kryo.util.Util.className(r7)
            r1.append(r6)
            java.lang.String r6 = r1.toString()
            r0.<init>(r6, r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.esotericsoftware.kryo.factories.ReflectionSerializerFactory.makeSerializer(com.esotericsoftware.kryo.Kryo, java.lang.Class, java.lang.Class):com.esotericsoftware.kryo.Serializer");
    }
}

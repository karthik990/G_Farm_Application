package com.esotericsoftware.kryo.util;

import com.esotericsoftware.minlog.Log;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import sun.misc.Cleaner;
import sun.misc.Unsafe;
import sun.nio.ch.DirectBuffer;

public class UnsafeUtil {
    private static final Unsafe _unsafe;
    public static final long byteArrayBaseOffset;
    public static final long charArrayBaseOffset;
    static Constructor<? extends ByteBuffer> directByteBufferConstr;
    public static final long doubleArrayBaseOffset;
    public static final long floatArrayBaseOffset;
    public static final long intArrayBaseOffset;
    public static final long longArrayBaseOffset;
    public static final long shortArrayBaseOffset;

    /* JADX WARNING: Removed duplicated region for block: B:54:0x0091  */
    static {
        /*
            java.lang.String r0 = "kryo"
            r1 = 0
            r2 = 1
            boolean r5 = com.esotericsoftware.kryo.util.Util.IS_ANDROID     // Catch:{ Exception -> 0x0080 }
            if (r5 != 0) goto L_0x0067
            java.lang.Class<sun.misc.Unsafe> r5 = sun.misc.Unsafe.class
            java.lang.String r6 = "theUnsafe"
            java.lang.reflect.Field r5 = r5.getDeclaredField(r6)     // Catch:{ Exception -> 0x0080 }
            r5.setAccessible(r2)     // Catch:{ Exception -> 0x0080 }
            java.lang.Object r5 = r5.get(r1)     // Catch:{ Exception -> 0x0080 }
            sun.misc.Unsafe r5 = (sun.misc.Unsafe) r5     // Catch:{ Exception -> 0x0080 }
            java.lang.Class<byte[]> r6 = byte[].class
            int r6 = r5.arrayBaseOffset(r6)     // Catch:{ Exception -> 0x0064 }
            long r6 = (long) r6
            java.lang.Class<char[]> r8 = char[].class
            int r8 = r5.arrayBaseOffset(r8)     // Catch:{ Exception -> 0x0061 }
            long r8 = (long) r8
            java.lang.Class<short[]> r10 = short[].class
            int r10 = r5.arrayBaseOffset(r10)     // Catch:{ Exception -> 0x005e }
            long r10 = (long) r10
            java.lang.Class<int[]> r12 = int[].class
            int r12 = r5.arrayBaseOffset(r12)     // Catch:{ Exception -> 0x005b }
            long r12 = (long) r12
            java.lang.Class<float[]> r14 = float[].class
            int r14 = r5.arrayBaseOffset(r14)     // Catch:{ Exception -> 0x0058 }
            long r14 = (long) r14
            java.lang.Class<long[]> r3 = long[].class
            int r3 = r5.arrayBaseOffset(r3)     // Catch:{ Exception -> 0x0055 }
            long r3 = (long) r3
            java.lang.Class<double[]> r1 = double[].class
            int r0 = r5.arrayBaseOffset(r1)     // Catch:{ Exception -> 0x0053 }
            long r0 = (long) r0
            r16 = r6
            r18 = r0
            r0 = r3
            r3 = r18
            goto L_0x009c
        L_0x0053:
            goto L_0x008d
        L_0x0055:
            r3 = 0
            goto L_0x008d
        L_0x0058:
            r3 = 0
            goto L_0x008b
        L_0x005b:
            r3 = 0
            goto L_0x0089
        L_0x005e:
            r3 = 0
            goto L_0x0087
        L_0x0061:
            r3 = 0
            goto L_0x0085
        L_0x0064:
            r3 = 0
            goto L_0x0083
        L_0x0067:
            boolean r1 = com.esotericsoftware.minlog.Log.TRACE     // Catch:{ Exception -> 0x0080 }
            if (r1 == 0) goto L_0x0070
            java.lang.String r1 = "Running on Android platform. Use of sun.misc.Unsafe should be disabled"
            com.esotericsoftware.minlog.Log.trace(r0, r1)     // Catch:{ Exception -> 0x0080 }
        L_0x0070:
            r0 = 0
            r3 = 0
            r5 = 0
            r8 = 0
            r10 = 0
            r12 = 0
            r14 = 0
            r16 = 0
            goto L_0x009c
        L_0x0080:
            r3 = 0
            r5 = 0
        L_0x0083:
            r6 = 0
        L_0x0085:
            r8 = 0
        L_0x0087:
            r10 = 0
        L_0x0089:
            r12 = 0
        L_0x008b:
            r14 = 0
        L_0x008d:
            boolean r1 = com.esotericsoftware.minlog.Log.TRACE
            if (r1 == 0) goto L_0x0097
            java.lang.String r1 = "sun.misc.Unsafe is not accessible or not available. Use of sun.misc.Unsafe should be disabled"
            com.esotericsoftware.minlog.Log.trace(r0, r1)
        L_0x0097:
            r0 = r3
            r16 = r6
            r3 = 0
        L_0x009c:
            byteArrayBaseOffset = r16
            charArrayBaseOffset = r8
            shortArrayBaseOffset = r10
            intArrayBaseOffset = r12
            floatArrayBaseOffset = r14
            longArrayBaseOffset = r0
            doubleArrayBaseOffset = r3
            _unsafe = r5
            java.nio.ByteBuffer r0 = java.nio.ByteBuffer.allocateDirect(r2)
            java.lang.Class r0 = r0.getClass()     // Catch:{ Exception -> 0x00d1 }
            r1 = 3
            java.lang.Class[] r1 = new java.lang.Class[r1]     // Catch:{ Exception -> 0x00d1 }
            r3 = 0
            java.lang.Class r4 = java.lang.Long.TYPE     // Catch:{ Exception -> 0x00d1 }
            r1[r3] = r4     // Catch:{ Exception -> 0x00d1 }
            java.lang.Class r3 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x00d1 }
            r1[r2] = r3     // Catch:{ Exception -> 0x00d1 }
            r3 = 2
            java.lang.Class<java.lang.Object> r4 = java.lang.Object.class
            r1[r3] = r4     // Catch:{ Exception -> 0x00d1 }
            java.lang.reflect.Constructor r0 = r0.getDeclaredConstructor(r1)     // Catch:{ Exception -> 0x00d1 }
            directByteBufferConstr = r0     // Catch:{ Exception -> 0x00d1 }
            java.lang.reflect.Constructor<? extends java.nio.ByteBuffer> r0 = directByteBufferConstr     // Catch:{ Exception -> 0x00d1 }
            r0.setAccessible(r2)     // Catch:{ Exception -> 0x00d1 }
            goto L_0x00d4
        L_0x00d1:
            r0 = 0
            directByteBufferConstr = r0
        L_0x00d4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.esotericsoftware.kryo.util.UnsafeUtil.<clinit>():void");
    }

    public static final Unsafe unsafe() {
        return _unsafe;
    }

    public static Field[] sortFieldsByOffset(List<Field> list) {
        Field[] fieldArr = (Field[]) list.toArray(new Field[0]);
        Arrays.sort(fieldArr, new Comparator<Field>() {
            public int compare(Field field, Field field2) {
                long objectFieldOffset = UnsafeUtil.unsafe().objectFieldOffset(field);
                long objectFieldOffset2 = UnsafeUtil.unsafe().objectFieldOffset(field2);
                if (objectFieldOffset < objectFieldOffset2) {
                    return -1;
                }
                return objectFieldOffset == objectFieldOffset2 ? 0 : 1;
            }
        });
        for (Field field : list) {
            if (Log.TRACE) {
                StringBuilder sb = new StringBuilder();
                sb.append("Field '");
                sb.append(field.getName());
                sb.append("' at offset ");
                sb.append(unsafe().objectFieldOffset(field));
                Log.trace("kryo", sb.toString());
            }
        }
        return fieldArr;
    }

    public static final ByteBuffer getDirectBufferAt(long j, int i) {
        Constructor<? extends ByteBuffer> constructor = directByteBufferConstr;
        if (constructor == null) {
            return null;
        }
        try {
            return (ByteBuffer) constructor.newInstance(new Object[]{Long.valueOf(j), Integer.valueOf(i), null});
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Cannot allocate ByteBuffer at a given address: ");
            sb.append(j);
            throw new RuntimeException(sb.toString(), e);
        }
    }

    public static void releaseBuffer(ByteBuffer byteBuffer) {
        if (byteBuffer != null && byteBuffer.isDirect()) {
            Cleaner cleaner = ((DirectBuffer) byteBuffer).cleaner();
            if (cleaner != null) {
                cleaner.clean();
            }
        }
    }
}

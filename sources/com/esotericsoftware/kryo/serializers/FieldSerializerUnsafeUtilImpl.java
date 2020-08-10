package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.util.UnsafeUtil;
import java.lang.reflect.Field;

final class FieldSerializerUnsafeUtilImpl implements FieldSerializerUnsafeUtil {
    private FieldSerializer serializer;

    public FieldSerializerUnsafeUtilImpl(FieldSerializer fieldSerializer) {
        this.serializer = fieldSerializer;
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x00fa  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0108  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void createUnsafeCacheFieldsAndRegions(java.util.List<java.lang.reflect.Field> r25, java.util.List<com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField> r26, int r27, com.esotericsoftware.kryo.util.IntArray r28) {
        /*
            r24 = this;
            r0 = r24
            r1 = r26
            int r2 = r25.size()
            r5 = 0
            r7 = 0
            r8 = -1
            r12 = r5
            r10 = r7
            r5 = 0
            r6 = 0
            r7 = 0
            r11 = -1
        L_0x0013:
            java.lang.String r14 = " endOffset="
            java.lang.String r15 = " Start offset = "
            java.lang.String r3 = ". Byte length = "
            java.lang.String r4 = ". Found a set of consecutive primitive fields. Number of fields = "
            r16 = r11
            java.lang.String r11 = "Class "
            java.lang.String r1 = "kryo"
            r17 = r10
            if (r5 >= r2) goto L_0x011c
            r10 = r25
            java.lang.Object r18 = r10.get(r5)
            r19 = r2
            r2 = r18
            java.lang.reflect.Field r2 = (java.lang.reflect.Field) r2
            com.esotericsoftware.kryo.serializers.FieldSerializer r10 = r0.serializer
            java.lang.Object r10 = r10.access
            if (r10 == 0) goto L_0x0053
            int r10 = r27 + r5
            r18 = r5
            r5 = r28
            int r10 = r5.get(r10)
            r5 = 1
            if (r10 != r5) goto L_0x0055
            com.esotericsoftware.kryo.serializers.FieldSerializer r5 = r0.serializer
            java.lang.Object r5 = r5.access
            com.esotericsoftware.reflectasm.FieldAccess r5 = (com.esotericsoftware.reflectasm.FieldAccess) r5
            java.lang.String r10 = r2.getName()
            int r5 = r5.getIndex(r10)
            goto L_0x0056
        L_0x0053:
            r18 = r5
        L_0x0055:
            r5 = -1
        L_0x0056:
            sun.misc.Unsafe r10 = com.esotericsoftware.kryo.util.UnsafeUtil.unsafe()
            long r20 = r10.objectFieldOffset(r2)
            java.lang.Class r10 = r2.getType()
            int r10 = r0.fieldSizeOf(r10)
            r22 = r14
            r23 = r15
            long r14 = (long) r10
            long r14 = r20 + r14
            java.lang.Class r10 = r2.getType()
            boolean r10 = r10.isPrimitive()
            if (r10 != 0) goto L_0x00ee
            if (r6 == 0) goto L_0x00ee
            r10 = 1
            if (r7 <= r10) goto L_0x00ca
            boolean r6 = com.esotericsoftware.minlog.Log.TRACE
            if (r6 == 0) goto L_0x00ba
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r11)
            com.esotericsoftware.kryo.serializers.FieldSerializer r10 = r0.serializer
            java.lang.Class r10 = r10.getType()
            java.lang.String r10 = r10.getName()
            r6.append(r10)
            r6.append(r4)
            r6.append(r7)
            r6.append(r3)
            long r3 = r8 - r12
            r6.append(r3)
            r10 = r23
            r6.append(r10)
            r6.append(r12)
            r3 = r22
            r6.append(r3)
            r6.append(r8)
            java.lang.String r3 = r6.toString()
            com.esotericsoftware.minlog.Log.trace(r1, r3)
        L_0x00ba:
            com.esotericsoftware.kryo.serializers.UnsafeCacheFields$UnsafeRegionField r1 = new com.esotericsoftware.kryo.serializers.UnsafeCacheFields$UnsafeRegionField
            long r8 = r8 - r12
            r1.<init>(r12, r8)
            r3 = r17
            r1.field = r3
            r4 = r26
            r4.add(r1)
            goto L_0x00df
        L_0x00ca:
            r4 = r26
            r3 = r17
            if (r3 == 0) goto L_0x00df
            com.esotericsoftware.kryo.serializers.FieldSerializer r1 = r0.serializer
            int r6 = r26.size()
            r8 = r16
            com.esotericsoftware.kryo.serializers.FieldSerializer$CachedField r1 = r1.newCachedField(r3, r6, r8)
            r4.add(r1)
        L_0x00df:
            com.esotericsoftware.kryo.serializers.FieldSerializer r1 = r0.serializer
            int r3 = r26.size()
            com.esotericsoftware.kryo.serializers.FieldSerializer$CachedField r1 = r1.newCachedField(r2, r3, r5)
            r4.add(r1)
            r6 = 0
            goto L_0x0111
        L_0x00ee:
            r4 = r26
            java.lang.Class r1 = r2.getType()
            boolean r1 = r1.isPrimitive()
            if (r1 != 0) goto L_0x0108
            com.esotericsoftware.kryo.serializers.FieldSerializer r1 = r0.serializer
            int r3 = r26.size()
            com.esotericsoftware.kryo.serializers.FieldSerializer$CachedField r1 = r1.newCachedField(r2, r3, r5)
            r4.add(r1)
            goto L_0x0111
        L_0x0108:
            if (r6 != 0) goto L_0x010f
            r12 = r20
            r6 = 1
            r7 = 1
            goto L_0x0111
        L_0x010f:
            int r7 = r7 + 1
        L_0x0111:
            int r1 = r18 + 1
            r10 = r2
            r11 = r5
            r8 = r14
            r2 = r19
            r5 = r1
            r1 = r4
            goto L_0x0013
        L_0x011c:
            r10 = r15
            r2 = r17
            r15 = r1
            r1 = r26
            com.esotericsoftware.kryo.serializers.FieldSerializer r5 = r0.serializer
            boolean r5 = r5.getUseAsmEnabled()
            if (r5 != 0) goto L_0x018e
            com.esotericsoftware.kryo.serializers.FieldSerializer r5 = r0.serializer
            boolean r5 = r5.getUseMemRegions()
            if (r5 == 0) goto L_0x018e
            if (r6 == 0) goto L_0x018e
            r5 = 1
            if (r7 <= r5) goto L_0x017d
            boolean r5 = com.esotericsoftware.minlog.Log.TRACE
            if (r5 == 0) goto L_0x0171
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r11)
            com.esotericsoftware.kryo.serializers.FieldSerializer r6 = r0.serializer
            java.lang.Class r6 = r6.getType()
            java.lang.String r6 = r6.getName()
            r5.append(r6)
            r5.append(r4)
            r5.append(r7)
            r5.append(r3)
            long r3 = r8 - r12
            r5.append(r3)
            r5.append(r10)
            r5.append(r12)
            r5.append(r14)
            r5.append(r8)
            java.lang.String r3 = r5.toString()
            com.esotericsoftware.minlog.Log.trace(r15, r3)
        L_0x0171:
            com.esotericsoftware.kryo.serializers.UnsafeCacheFields$UnsafeRegionField r3 = new com.esotericsoftware.kryo.serializers.UnsafeCacheFields$UnsafeRegionField
            long r8 = r8 - r12
            r3.<init>(r12, r8)
            r3.field = r2
            r1.add(r3)
            goto L_0x018e
        L_0x017d:
            if (r2 == 0) goto L_0x018e
            com.esotericsoftware.kryo.serializers.FieldSerializer r3 = r0.serializer
            int r4 = r26.size()
            r5 = r16
            com.esotericsoftware.kryo.serializers.FieldSerializer$CachedField r2 = r3.newCachedField(r2, r4, r5)
            r1.add(r2)
        L_0x018e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.esotericsoftware.kryo.serializers.FieldSerializerUnsafeUtilImpl.createUnsafeCacheFieldsAndRegions(java.util.List, java.util.List, int, com.esotericsoftware.kryo.util.IntArray):void");
    }

    private int fieldSizeOf(Class<?> cls) {
        if (cls == Integer.TYPE || cls == Float.TYPE) {
            return 4;
        }
        if (cls == Long.TYPE || cls == Double.TYPE) {
            return 8;
        }
        if (cls == Byte.TYPE || cls == Boolean.TYPE) {
            return 1;
        }
        if (cls == Short.TYPE || cls == Character.TYPE) {
            return 2;
        }
        return UnsafeUtil.unsafe().addressSize();
    }

    public long getObjectFieldOffset(Field field) {
        return UnsafeUtil.unsafe().objectFieldOffset(field);
    }
}

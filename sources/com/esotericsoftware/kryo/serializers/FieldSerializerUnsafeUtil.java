package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField;
import com.esotericsoftware.kryo.util.IntArray;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

interface FieldSerializerUnsafeUtil {

    public static class Factory {
        static Constructor<FieldSerializerUnsafeUtil> fieldSerializerUnsafeUtilConstructor;

        static {
            try {
                fieldSerializerUnsafeUtilConstructor = FieldSerializer.class.getClassLoader().loadClass("com.esotericsoftware.kryo.serializers.FieldSerializerUnsafeUtilImpl").getConstructor(new Class[]{FieldSerializer.class});
            } catch (Throwable unused) {
            }
        }

        static FieldSerializerUnsafeUtil getInstance(FieldSerializer fieldSerializer) {
            Constructor<FieldSerializerUnsafeUtil> constructor = fieldSerializerUnsafeUtilConstructor;
            if (constructor != null) {
                try {
                    return (FieldSerializerUnsafeUtil) constructor.newInstance(new Object[]{fieldSerializer});
                } catch (Exception unused) {
                }
            }
            return null;
        }
    }

    void createUnsafeCacheFieldsAndRegions(List<Field> list, List<CachedField> list2, int i, IntArray intArray);

    long getObjectFieldOffset(Field field);
}

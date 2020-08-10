package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField;
import com.esotericsoftware.kryo.serializers.FieldSerializer.CachedFieldFactory;
import java.lang.reflect.Field;

class AsmCachedFieldFactory implements CachedFieldFactory {
    AsmCachedFieldFactory() {
    }

    public CachedField createCachedField(Class cls, Field field, FieldSerializer fieldSerializer) {
        if (cls.isPrimitive()) {
            if (cls == Boolean.TYPE) {
                return new AsmBooleanField();
            }
            if (cls == Byte.TYPE) {
                return new AsmByteField();
            }
            if (cls == Character.TYPE) {
                return new AsmCharField();
            }
            if (cls == Short.TYPE) {
                return new AsmShortField();
            }
            if (cls == Integer.TYPE) {
                return new AsmIntField();
            }
            if (cls == Long.TYPE) {
                return new AsmLongField();
            }
            if (cls == Float.TYPE) {
                return new AsmFloatField();
            }
            if (cls == Double.TYPE) {
                return new AsmDoubleField();
            }
            return new AsmObjectField(fieldSerializer);
        } else if (cls != String.class || (fieldSerializer.kryo.getReferences() && fieldSerializer.kryo.getReferenceResolver().useReferences(String.class))) {
            return new AsmObjectField(fieldSerializer);
        } else {
            return new AsmStringField();
        }
    }
}

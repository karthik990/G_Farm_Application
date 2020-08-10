package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import java.util.Map.Entry;

public class MapSerializer extends Serializer<Map> {
    private Class keyClass;
    private Class keyGenericType;
    private Serializer keySerializer;
    private boolean keysCanBeNull = true;
    private Class valueClass;
    private Class valueGenericType;
    private Serializer valueSerializer;
    private boolean valuesCanBeNull = true;

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface BindMap {
        Class<?> keyClass() default Object.class;

        Class<? extends Serializer> keySerializer() default Serializer.class;

        boolean keysCanBeNull() default true;

        Class<?> valueClass() default Object.class;

        Class<? extends Serializer> valueSerializer() default Serializer.class;

        boolean valuesCanBeNull() default true;
    }

    public void setKeysCanBeNull(boolean z) {
        this.keysCanBeNull = z;
    }

    public void setKeyClass(Class cls, Serializer serializer) {
        this.keyClass = cls;
        this.keySerializer = serializer;
    }

    public void setValueClass(Class cls, Serializer serializer) {
        this.valueClass = cls;
        this.valueSerializer = serializer;
    }

    public void setValuesCanBeNull(boolean z) {
        this.valuesCanBeNull = z;
    }

    public void setGenerics(Kryo kryo, Class[] clsArr) {
        this.keyGenericType = null;
        this.valueGenericType = null;
        if (clsArr != null && clsArr.length > 0) {
            if (clsArr[0] != null && kryo.isFinal(clsArr[0])) {
                this.keyGenericType = clsArr[0];
            }
            if (clsArr.length > 1 && clsArr[1] != null && kryo.isFinal(clsArr[1])) {
                this.valueGenericType = clsArr[1];
            }
        }
    }

    public void write(Kryo kryo, Output output, Map map) {
        output.writeInt(map.size(), true);
        Serializer serializer = this.keySerializer;
        Class cls = this.keyGenericType;
        if (cls != null) {
            if (serializer == null) {
                serializer = kryo.getSerializer(cls);
            }
            this.keyGenericType = null;
        }
        Serializer serializer2 = this.valueSerializer;
        Class cls2 = this.valueGenericType;
        if (cls2 != null) {
            if (serializer2 == null) {
                serializer2 = kryo.getSerializer(cls2);
            }
            this.valueGenericType = null;
        }
        for (Entry entry : map.entrySet()) {
            if (serializer == null) {
                kryo.writeClassAndObject(output, entry.getKey());
            } else if (this.keysCanBeNull) {
                kryo.writeObjectOrNull(output, entry.getKey(), serializer);
            } else {
                kryo.writeObject(output, entry.getKey(), serializer);
            }
            if (serializer2 == null) {
                kryo.writeClassAndObject(output, entry.getValue());
            } else if (this.valuesCanBeNull) {
                kryo.writeObjectOrNull(output, entry.getValue(), serializer2);
            } else {
                kryo.writeObject(output, entry.getValue(), serializer2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public Map create(Kryo kryo, Input input, Class<Map> cls) {
        return (Map) kryo.newInstance(cls);
    }

    public Map read(Kryo kryo, Input input, Class<Map> cls) {
        Object obj;
        Object obj2;
        Map create = create(kryo, input, cls);
        int readInt = input.readInt(true);
        Class cls2 = this.keyClass;
        Class cls3 = this.valueClass;
        Serializer serializer = this.keySerializer;
        Class cls4 = this.keyGenericType;
        if (cls4 != null) {
            if (serializer == null) {
                serializer = kryo.getSerializer(cls4);
            }
            this.keyGenericType = null;
            cls2 = cls4;
        }
        Serializer serializer2 = this.valueSerializer;
        Class cls5 = this.valueGenericType;
        if (cls5 != null) {
            if (serializer2 == null) {
                serializer2 = kryo.getSerializer(cls5);
            }
            this.valueGenericType = null;
            cls3 = cls5;
        }
        kryo.reference(create);
        for (int i = 0; i < readInt; i++) {
            if (serializer == null) {
                obj = kryo.readClassAndObject(input);
            } else if (this.keysCanBeNull) {
                obj = kryo.readObjectOrNull(input, cls2, serializer);
            } else {
                obj = kryo.readObject(input, cls2, serializer);
            }
            if (serializer2 == null) {
                obj2 = kryo.readClassAndObject(input);
            } else if (this.valuesCanBeNull) {
                obj2 = kryo.readObjectOrNull(input, cls3, serializer2);
            } else {
                obj2 = kryo.readObject(input, cls3, serializer2);
            }
            create.put(obj, obj2);
        }
        return create;
    }

    /* access modifiers changed from: protected */
    public Map createCopy(Kryo kryo, Map map) {
        return (Map) kryo.newInstance(map.getClass());
    }

    public Map copy(Kryo kryo, Map map) {
        Map createCopy = createCopy(kryo, map);
        for (Entry entry : map.entrySet()) {
            createCopy.put(kryo.copy(entry.getKey()), kryo.copy(entry.getValue()));
        }
        return createCopy;
    }
}

package p097de.javakaffee.kryoserializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import java.lang.reflect.Field;
import java.util.List;

/* renamed from: de.javakaffee.kryoserializers.SubListSerializers */
public class SubListSerializers {
    /* access modifiers changed from: private */
    public static final Object FAKE_REFERENCE = new Object();

    /* renamed from: de.javakaffee.kryoserializers.SubListSerializers$ArrayListSubListSerializer */
    public static class ArrayListSubListSerializer extends Serializer<List<?>> {
        public static final Class<?> SUBLIST_CLASS = SubListSerializers.getClassOrNull("java.util.ArrayList$SubList");
        private Field _parentField;
        private Field _parentOffsetField;
        private Field _sizeField;

        public ArrayListSubListSerializer() {
            try {
                Class cls = Class.forName("java.util.ArrayList$SubList");
                this._parentField = cls.getDeclaredField("parent");
                this._parentOffsetField = cls.getDeclaredField("parentOffset");
                this._sizeField = cls.getDeclaredField("size");
                this._parentField.setAccessible(true);
                this._parentOffsetField.setAccessible(true);
                this._sizeField.setAccessible(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public static boolean canSerialize(Class<?> cls) {
            Class<?> cls2 = SUBLIST_CLASS;
            return cls2 != null && cls2.isAssignableFrom(cls);
        }

        public static Kryo addDefaultSerializer(Kryo kryo) {
            Class<?> cls = SUBLIST_CLASS;
            if (cls != null) {
                kryo.addDefaultSerializer((Class) cls, (Serializer) new ArrayListSubListSerializer());
            }
            return kryo;
        }

        public List<?> read(Kryo kryo, Input input, Class<List<?>> cls) {
            kryo.reference(SubListSerializers.FAKE_REFERENCE);
            return ((List) kryo.readClassAndObject(input)).subList(input.readInt(true), input.readInt(true));
        }

        public void write(Kryo kryo, Output output, List<?> list) {
            try {
                kryo.writeClassAndObject(output, this._parentField.get(list));
                int i = this._parentOffsetField.getInt(list);
                output.writeInt(i, true);
                output.writeInt(i + this._sizeField.getInt(list), true);
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e2) {
                throw new RuntimeException(e2);
            }
        }

        public List<?> copy(Kryo kryo, List<?> list) {
            kryo.reference(SubListSerializers.FAKE_REFERENCE);
            try {
                List list2 = (List) this._parentField.get(list);
                int i = this._parentOffsetField.getInt(list);
                return ((List) kryo.copy(list2)).subList(i, this._sizeField.getInt(list) + i);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /* renamed from: de.javakaffee.kryoserializers.SubListSerializers$JavaUtilSubListSerializer */
    public static class JavaUtilSubListSerializer extends Serializer<List<?>> {
        public static final Class<?> SUBLIST_CLASS = SubListSerializers.getClassOrNull("java.util.SubList");
        private Field _listField;
        private Field _offsetField;
        private Field _sizeField;

        public JavaUtilSubListSerializer() {
            try {
                Class cls = Class.forName("java.util.SubList");
                this._listField = cls.getDeclaredField("l");
                this._offsetField = cls.getDeclaredField("offset");
                this._sizeField = cls.getDeclaredField("size");
                this._listField.setAccessible(true);
                this._offsetField.setAccessible(true);
                this._sizeField.setAccessible(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public static boolean canSerialize(Class<?> cls) {
            Class<?> cls2 = SUBLIST_CLASS;
            return cls2 != null && cls2.isAssignableFrom(cls);
        }

        public static Kryo addDefaultSerializer(Kryo kryo) {
            Class<?> cls = SUBLIST_CLASS;
            if (cls != null) {
                kryo.addDefaultSerializer((Class) cls, (Serializer) new JavaUtilSubListSerializer());
            }
            return kryo;
        }

        public List<?> read(Kryo kryo, Input input, Class<List<?>> cls) {
            kryo.reference(SubListSerializers.FAKE_REFERENCE);
            return ((List) kryo.readClassAndObject(input)).subList(input.readInt(true), input.readInt(true));
        }

        public void write(Kryo kryo, Output output, List<?> list) {
            try {
                kryo.writeClassAndObject(output, this._listField.get(list));
                int i = this._offsetField.getInt(list);
                output.writeInt(i, true);
                output.writeInt(i + this._sizeField.getInt(list), true);
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e2) {
                throw new RuntimeException(e2);
            }
        }

        public List<?> copy(Kryo kryo, List<?> list) {
            kryo.reference(SubListSerializers.FAKE_REFERENCE);
            try {
                List list2 = (List) this._listField.get(list);
                int i = this._offsetField.getInt(list);
                return ((List) kryo.copy(list2)).subList(i, this._sizeField.getInt(list) + i);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    static Class<?> getClass(String str) {
        try {
            return Class.forName(str);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static Class<?> getClassOrNull(String str) {
        try {
            return Class.forName(str);
        } catch (Exception unused) {
            return null;
        }
    }

    public static Serializer<List<?>> createFor(Class cls) {
        if (ArrayListSubListSerializer.canSerialize(cls)) {
            return new ArrayListSubListSerializer();
        }
        if (JavaUtilSubListSerializer.canSerialize(cls)) {
            return new JavaUtilSubListSerializer();
        }
        return null;
    }

    public static Kryo addDefaultSerializers(Kryo kryo) {
        ArrayListSubListSerializer.addDefaultSerializer(kryo);
        JavaUtilSubListSerializer.addDefaultSerializer(kryo);
        return kryo;
    }
}

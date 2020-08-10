package p097de.javakaffee.kryoserializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/* renamed from: de.javakaffee.kryoserializers.ArraysAsListSerializer */
public class ArraysAsListSerializer extends Serializer<List<?>> {
    private Field _arrayField;

    public ArraysAsListSerializer() {
        try {
            this._arrayField = Class.forName("java.util.Arrays$ArrayList").getDeclaredField("a");
            this._arrayField.setAccessible(true);
            setImmutable(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<?> read(Kryo kryo, Input input, Class<List<?>> cls) {
        int readInt = input.readInt(true);
        Class type = kryo.readClass(input).getType();
        if (type.isPrimitive()) {
            type = getPrimitiveWrapperClass(type);
        }
        try {
            Object newInstance = Array.newInstance(type, readInt);
            for (int i = 0; i < readInt; i++) {
                Array.set(newInstance, i, kryo.readClassAndObject(input));
            }
            return Arrays.asList((Object[]) newInstance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void write(Kryo kryo, Output output, List<?> list) {
        try {
            Object[] objArr = (Object[]) this._arrayField.get(list);
            output.writeInt(objArr.length, true);
            kryo.writeClass(output, objArr.getClass().getComponentType());
            for (Object writeClassAndObject : objArr) {
                kryo.writeClassAndObject(output, writeClassAndObject);
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Class<?>, code=java.lang.Class, for r1v0, types: [java.lang.Class<?>, java.lang.Class, java.lang.Object] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.Class<?> getPrimitiveWrapperClass(java.lang.Class r1) {
        /*
            boolean r0 = r1.isPrimitive()
            if (r0 == 0) goto L_0x005d
            java.lang.Class r0 = java.lang.Long.TYPE
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0011
            java.lang.Class<java.lang.Long> r1 = java.lang.Long.class
            return r1
        L_0x0011:
            java.lang.Class r0 = java.lang.Integer.TYPE
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x001c
            java.lang.Class<java.lang.Integer> r1 = java.lang.Integer.class
            return r1
        L_0x001c:
            java.lang.Class r0 = java.lang.Double.TYPE
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0027
            java.lang.Class<java.lang.Double> r1 = java.lang.Double.class
            return r1
        L_0x0027:
            java.lang.Class r0 = java.lang.Float.TYPE
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0032
            java.lang.Class<java.lang.Float> r1 = java.lang.Float.class
            return r1
        L_0x0032:
            java.lang.Class r0 = java.lang.Boolean.TYPE
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x003d
            java.lang.Class<java.lang.Boolean> r1 = java.lang.Boolean.class
            return r1
        L_0x003d:
            java.lang.Class r0 = java.lang.Character.TYPE
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0048
            java.lang.Class<java.lang.Character> r1 = java.lang.Character.class
            return r1
        L_0x0048:
            java.lang.Class r0 = java.lang.Short.TYPE
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0053
            java.lang.Class<java.lang.Short> r1 = java.lang.Short.class
            return r1
        L_0x0053:
            java.lang.Class r0 = java.lang.Byte.TYPE
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x005d
            java.lang.Class<java.lang.Byte> r1 = java.lang.Byte.class
        L_0x005d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: p097de.javakaffee.kryoserializers.ArraysAsListSerializer.getPrimitiveWrapperClass(java.lang.Class):java.lang.Class");
    }
}

package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.esotericsoftware.minlog.Log;
import java.lang.reflect.Array;
import java.lang.reflect.Modifier;

public class DefaultArraySerializers {

    public static class BooleanArraySerializer extends Serializer<boolean[]> {
        public BooleanArraySerializer() {
            setAcceptsNull(true);
        }

        public void write(Kryo kryo, Output output, boolean[] zArr) {
            if (zArr == null) {
                output.writeVarInt(0, true);
                return;
            }
            output.writeVarInt(zArr.length + 1, true);
            for (boolean writeBoolean : zArr) {
                output.writeBoolean(writeBoolean);
            }
        }

        public boolean[] read(Kryo kryo, Input input, Class<boolean[]> cls) {
            int readVarInt = input.readVarInt(true);
            if (readVarInt == 0) {
                return null;
            }
            int i = readVarInt - 1;
            boolean[] zArr = new boolean[i];
            for (int i2 = 0; i2 < i; i2++) {
                zArr[i2] = input.readBoolean();
            }
            return zArr;
        }

        public boolean[] copy(Kryo kryo, boolean[] zArr) {
            boolean[] zArr2 = new boolean[zArr.length];
            System.arraycopy(zArr, 0, zArr2, 0, zArr2.length);
            return zArr2;
        }
    }

    public static class ByteArraySerializer extends Serializer<byte[]> {
        public ByteArraySerializer() {
            setAcceptsNull(true);
        }

        public void write(Kryo kryo, Output output, byte[] bArr) {
            if (bArr == null) {
                output.writeVarInt(0, true);
                return;
            }
            output.writeVarInt(bArr.length + 1, true);
            output.writeBytes(bArr);
        }

        public byte[] read(Kryo kryo, Input input, Class<byte[]> cls) {
            int readVarInt = input.readVarInt(true);
            if (readVarInt == 0) {
                return null;
            }
            return input.readBytes(readVarInt - 1);
        }

        public byte[] copy(Kryo kryo, byte[] bArr) {
            byte[] bArr2 = new byte[bArr.length];
            System.arraycopy(bArr, 0, bArr2, 0, bArr2.length);
            return bArr2;
        }
    }

    public static class CharArraySerializer extends Serializer<char[]> {
        public CharArraySerializer() {
            setAcceptsNull(true);
        }

        public void write(Kryo kryo, Output output, char[] cArr) {
            if (cArr == null) {
                output.writeVarInt(0, true);
                return;
            }
            output.writeVarInt(cArr.length + 1, true);
            output.writeChars(cArr);
        }

        public char[] read(Kryo kryo, Input input, Class<char[]> cls) {
            int readVarInt = input.readVarInt(true);
            if (readVarInt == 0) {
                return null;
            }
            return input.readChars(readVarInt - 1);
        }

        public char[] copy(Kryo kryo, char[] cArr) {
            char[] cArr2 = new char[cArr.length];
            System.arraycopy(cArr, 0, cArr2, 0, cArr2.length);
            return cArr2;
        }
    }

    public static class DoubleArraySerializer extends Serializer<double[]> {
        public DoubleArraySerializer() {
            setAcceptsNull(true);
        }

        public void write(Kryo kryo, Output output, double[] dArr) {
            if (dArr == null) {
                output.writeVarInt(0, true);
                return;
            }
            output.writeVarInt(dArr.length + 1, true);
            output.writeDoubles(dArr);
        }

        public double[] read(Kryo kryo, Input input, Class<double[]> cls) {
            int readVarInt = input.readVarInt(true);
            if (readVarInt == 0) {
                return null;
            }
            return input.readDoubles(readVarInt - 1);
        }

        public double[] copy(Kryo kryo, double[] dArr) {
            double[] dArr2 = new double[dArr.length];
            System.arraycopy(dArr, 0, dArr2, 0, dArr2.length);
            return dArr2;
        }
    }

    public static class FloatArraySerializer extends Serializer<float[]> {
        public FloatArraySerializer() {
            setAcceptsNull(true);
        }

        public void write(Kryo kryo, Output output, float[] fArr) {
            if (fArr == null) {
                output.writeVarInt(0, true);
                return;
            }
            output.writeVarInt(fArr.length + 1, true);
            output.writeFloats(fArr);
        }

        public float[] read(Kryo kryo, Input input, Class<float[]> cls) {
            int readVarInt = input.readVarInt(true);
            if (readVarInt == 0) {
                return null;
            }
            return input.readFloats(readVarInt - 1);
        }

        public float[] copy(Kryo kryo, float[] fArr) {
            float[] fArr2 = new float[fArr.length];
            System.arraycopy(fArr, 0, fArr2, 0, fArr2.length);
            return fArr2;
        }
    }

    public static class IntArraySerializer extends Serializer<int[]> {
        public IntArraySerializer() {
            setAcceptsNull(true);
        }

        public void write(Kryo kryo, Output output, int[] iArr) {
            if (iArr == null) {
                output.writeVarInt(0, true);
                return;
            }
            output.writeVarInt(iArr.length + 1, true);
            output.writeInts(iArr, false);
        }

        public int[] read(Kryo kryo, Input input, Class<int[]> cls) {
            int readVarInt = input.readVarInt(true);
            if (readVarInt == 0) {
                return null;
            }
            return input.readInts(readVarInt - 1, false);
        }

        public int[] copy(Kryo kryo, int[] iArr) {
            int[] iArr2 = new int[iArr.length];
            System.arraycopy(iArr, 0, iArr2, 0, iArr2.length);
            return iArr2;
        }
    }

    public static class LongArraySerializer extends Serializer<long[]> {
        public LongArraySerializer() {
            setAcceptsNull(true);
        }

        public void write(Kryo kryo, Output output, long[] jArr) {
            if (jArr == null) {
                output.writeVarInt(0, true);
                return;
            }
            output.writeVarInt(jArr.length + 1, true);
            output.writeLongs(jArr, false);
        }

        public long[] read(Kryo kryo, Input input, Class<long[]> cls) {
            int readVarInt = input.readVarInt(true);
            if (readVarInt == 0) {
                return null;
            }
            return input.readLongs(readVarInt - 1, false);
        }

        public long[] copy(Kryo kryo, long[] jArr) {
            long[] jArr2 = new long[jArr.length];
            System.arraycopy(jArr, 0, jArr2, 0, jArr2.length);
            return jArr2;
        }
    }

    public static class ObjectArraySerializer extends Serializer<Object[]> {
        private boolean elementsAreSameType;
        private boolean elementsCanBeNull = true;
        private Class[] generics;
        private final Class type;

        public ObjectArraySerializer(Kryo kryo, Class cls) {
            setAcceptsNull(true);
            this.type = cls;
            if ((cls.getComponentType().getModifiers() & 16) != 0) {
                setElementsAreSameType(true);
            }
        }

        public void write(Kryo kryo, Output output, Object[] objArr) {
            int i = 0;
            if (objArr == null) {
                output.writeVarInt(0, true);
                return;
            }
            output.writeVarInt(objArr.length + 1, true);
            Class componentType = objArr.getClass().getComponentType();
            if (this.elementsAreSameType || Modifier.isFinal(componentType.getModifiers())) {
                Serializer serializer = kryo.getSerializer(componentType);
                serializer.setGenerics(kryo, this.generics);
                int length = objArr.length;
                while (i < length) {
                    if (this.elementsCanBeNull) {
                        kryo.writeObjectOrNull(output, objArr[i], serializer);
                    } else {
                        kryo.writeObject(output, objArr[i], serializer);
                    }
                    i++;
                }
            } else {
                int length2 = objArr.length;
                while (i < length2) {
                    if (objArr[i] != null) {
                        kryo.getSerializer(objArr[i].getClass()).setGenerics(kryo, this.generics);
                    }
                    kryo.writeClassAndObject(output, objArr[i]);
                    i++;
                }
            }
        }

        public Object[] read(Kryo kryo, Input input, Class<Object[]> cls) {
            int readVarInt = input.readVarInt(true);
            if (readVarInt == 0) {
                return null;
            }
            Object[] objArr = (Object[]) Array.newInstance(cls.getComponentType(), readVarInt - 1);
            kryo.reference(objArr);
            Class componentType = objArr.getClass().getComponentType();
            int i = 0;
            if (this.elementsAreSameType || Modifier.isFinal(componentType.getModifiers())) {
                Serializer serializer = kryo.getSerializer(componentType);
                serializer.setGenerics(kryo, this.generics);
                int length = objArr.length;
                while (i < length) {
                    if (this.elementsCanBeNull) {
                        objArr[i] = kryo.readObjectOrNull(input, componentType, serializer);
                    } else {
                        objArr[i] = kryo.readObject(input, componentType, serializer);
                    }
                    i++;
                }
            } else {
                int length2 = objArr.length;
                while (i < length2) {
                    Registration readClass = kryo.readClass(input);
                    if (readClass != null) {
                        readClass.getSerializer().setGenerics(kryo, this.generics);
                        objArr[i] = kryo.readObject(input, readClass.getType(), readClass.getSerializer());
                    } else {
                        objArr[i] = null;
                    }
                    i++;
                }
            }
            return objArr;
        }

        public Object[] copy(Kryo kryo, Object[] objArr) {
            Object[] objArr2 = (Object[]) Array.newInstance(objArr.getClass().getComponentType(), objArr.length);
            int length = objArr.length;
            for (int i = 0; i < length; i++) {
                objArr2[i] = kryo.copy(objArr[i]);
            }
            return objArr2;
        }

        public void setElementsCanBeNull(boolean z) {
            this.elementsCanBeNull = z;
        }

        public void setElementsAreSameType(boolean z) {
            this.elementsAreSameType = z;
        }

        public void setGenerics(Kryo kryo, Class[] clsArr) {
            if (Log.TRACE) {
                Log.trace("kryo", "setting generics for ObjectArraySerializer");
            }
            this.generics = clsArr;
        }
    }

    public static class ShortArraySerializer extends Serializer<short[]> {
        public ShortArraySerializer() {
            setAcceptsNull(true);
        }

        public void write(Kryo kryo, Output output, short[] sArr) {
            if (sArr == null) {
                output.writeVarInt(0, true);
                return;
            }
            output.writeVarInt(sArr.length + 1, true);
            output.writeShorts(sArr);
        }

        public short[] read(Kryo kryo, Input input, Class<short[]> cls) {
            int readVarInt = input.readVarInt(true);
            if (readVarInt == 0) {
                return null;
            }
            return input.readShorts(readVarInt - 1);
        }

        public short[] copy(Kryo kryo, short[] sArr) {
            short[] sArr2 = new short[sArr.length];
            System.arraycopy(sArr, 0, sArr2, 0, sArr2.length);
            return sArr2;
        }
    }

    public static class StringArraySerializer extends Serializer<String[]> {
        public StringArraySerializer() {
            setAcceptsNull(true);
        }

        public void write(Kryo kryo, Output output, String[] strArr) {
            int i = 0;
            if (strArr == null) {
                output.writeVarInt(0, true);
                return;
            }
            output.writeVarInt(strArr.length + 1, true);
            if (!kryo.getReferences() || !kryo.getReferenceResolver().useReferences(String.class)) {
                int length = strArr.length;
                while (i < length) {
                    output.writeString(strArr[i]);
                    i++;
                }
            } else {
                Serializer serializer = kryo.getSerializer(String.class);
                int length2 = strArr.length;
                while (i < length2) {
                    kryo.writeObjectOrNull(output, (Object) strArr[i], serializer);
                    i++;
                }
            }
        }

        public String[] read(Kryo kryo, Input input, Class<String[]> cls) {
            int readVarInt = input.readVarInt(true);
            if (readVarInt == 0) {
                return null;
            }
            int i = readVarInt - 1;
            String[] strArr = new String[i];
            int i2 = 0;
            if (!kryo.getReferences() || !kryo.getReferenceResolver().useReferences(String.class)) {
                while (i2 < i) {
                    strArr[i2] = input.readString();
                    i2++;
                }
            } else {
                Serializer serializer = kryo.getSerializer(String.class);
                while (i2 < i) {
                    strArr[i2] = (String) kryo.readObjectOrNull(input, String.class, serializer);
                    i2++;
                }
            }
            return strArr;
        }

        public String[] copy(Kryo kryo, String[] strArr) {
            String[] strArr2 = new String[strArr.length];
            System.arraycopy(strArr, 0, strArr2, 0, strArr2.length);
            return strArr2;
        }
    }
}

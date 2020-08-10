package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

public class ClosureSerializer extends Serializer {
    private static Method readResolve;
    private static Class serializedLambda = SerializedLambda.class;

    public static class Closure {
    }

    static {
        try {
            readResolve = serializedLambda.getDeclaredMethod("readResolve", new Class[0]);
            readResolve.setAccessible(true);
        } catch (Exception e) {
            throw new RuntimeException("Could not obtain SerializedLambda or its methods via reflection", e);
        }
    }

    public void write(Kryo kryo, Output output, Object obj) {
        String str = "Could not serialize lambda";
        try {
            Method declaredMethod = obj.getClass().getDeclaredMethod("writeReplace", new Class[0]);
            declaredMethod.setAccessible(true);
            Object invoke = declaredMethod.invoke(obj, new Object[0]);
            if (serializedLambda.isInstance(invoke)) {
                kryo.writeObject(output, invoke);
                return;
            }
            throw new RuntimeException(str);
        } catch (Exception e) {
            throw new RuntimeException(str, e);
        }
    }

    public Object read(Kryo kryo, Input input, Class cls) {
        try {
            return readResolve.invoke(kryo.readObject(input, serializedLambda), new Object[0]);
        } catch (Exception e) {
            throw new RuntimeException("Could not serialize lambda", e);
        }
    }

    public Object copy(Kryo kryo, Object obj) {
        String str = "Could not serialize lambda";
        try {
            Method declaredMethod = obj.getClass().getDeclaredMethod("writeReplace", new Class[0]);
            declaredMethod.setAccessible(true);
            Object invoke = declaredMethod.invoke(obj, new Object[0]);
            if (serializedLambda.isInstance(invoke)) {
                return readResolve.invoke(invoke, new Object[0]);
            }
            throw new RuntimeException(str);
        } catch (Exception e) {
            throw new RuntimeException(str, e);
        }
    }
}

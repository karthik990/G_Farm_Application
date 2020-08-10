package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.KryoObjectInput;
import com.esotericsoftware.kryo.p035io.KryoObjectOutput;
import com.esotericsoftware.kryo.p035io.Output;
import com.esotericsoftware.kryo.util.ObjectMap;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Method;

public class ExternalizableSerializer extends Serializer {
    private ObjectMap<Class, JavaSerializer> javaSerializerByType;
    private KryoObjectInput objectInput = null;
    private KryoObjectOutput objectOutput = null;

    public void write(Kryo kryo, Output output, Object obj) {
        JavaSerializer javaSerializerIfRequired = getJavaSerializerIfRequired(obj.getClass());
        if (javaSerializerIfRequired == null) {
            writeExternal(kryo, output, obj);
        } else {
            javaSerializerIfRequired.write(kryo, output, obj);
        }
    }

    public Object read(Kryo kryo, Input input, Class cls) {
        JavaSerializer javaSerializerIfRequired = getJavaSerializerIfRequired(cls);
        if (javaSerializerIfRequired == null) {
            return readExternal(kryo, input, cls);
        }
        return javaSerializerIfRequired.read(kryo, input, cls);
    }

    private void writeExternal(Kryo kryo, Output output, Object obj) {
        try {
            ((Externalizable) obj).writeExternal(getObjectOutput(kryo, output));
        } catch (ClassCastException e) {
            throw new KryoException((Throwable) e);
        } catch (IOException e2) {
            throw new KryoException((Throwable) e2);
        }
    }

    private Object readExternal(Kryo kryo, Input input, Class cls) {
        try {
            Externalizable externalizable = (Externalizable) kryo.newInstance(cls);
            externalizable.readExternal(getObjectInput(kryo, input));
            return externalizable;
        } catch (ClassCastException e) {
            throw new KryoException((Throwable) e);
        } catch (ClassNotFoundException e2) {
            throw new KryoException((Throwable) e2);
        } catch (IOException e3) {
            throw new KryoException((Throwable) e3);
        }
    }

    private ObjectOutput getObjectOutput(Kryo kryo, Output output) {
        KryoObjectOutput kryoObjectOutput = this.objectOutput;
        if (kryoObjectOutput == null) {
            this.objectOutput = new KryoObjectOutput(kryo, output);
        } else {
            kryoObjectOutput.setOutput(output);
        }
        return this.objectOutput;
    }

    private ObjectInput getObjectInput(Kryo kryo, Input input) {
        KryoObjectInput kryoObjectInput = this.objectInput;
        if (kryoObjectInput == null) {
            this.objectInput = new KryoObjectInput(kryo, input);
        } else {
            kryoObjectInput.setInput(input);
        }
        return this.objectInput;
    }

    private JavaSerializer getJavaSerializerIfRequired(Class cls) {
        JavaSerializer cachedSerializer = getCachedSerializer(cls);
        return (cachedSerializer != null || !isJavaSerializerRequired(cls)) ? cachedSerializer : new JavaSerializer();
    }

    private JavaSerializer getCachedSerializer(Class cls) {
        ObjectMap<Class, JavaSerializer> objectMap = this.javaSerializerByType;
        if (objectMap != null) {
            return (JavaSerializer) objectMap.get(cls);
        }
        this.javaSerializerByType = new ObjectMap<>();
        return null;
    }

    private boolean isJavaSerializerRequired(Class cls) {
        return hasInheritableReplaceMethod(cls, "writeReplace") || hasInheritableReplaceMethod(cls, "readResolve");
    }

    private static boolean hasInheritableReplaceMethod(Class cls, String str) {
        Method method;
        while (true) {
            if (cls == null) {
                method = null;
                break;
            }
            try {
                method = cls.getDeclaredMethod(str, new Class[0]);
                break;
            } catch (NoSuchMethodException unused) {
                cls = cls.getSuperclass();
            }
        }
        if (method == null || method.getReturnType() != Object.class) {
            return false;
        }
        return true;
    }
}

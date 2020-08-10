package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.esotericsoftware.kryo.util.ObjectMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;

public class JavaSerializer extends Serializer {

    private static class ObjectInputStreamWithKryoClassLoader extends ObjectInputStream {
        private final ClassLoader loader;

        ObjectInputStreamWithKryoClassLoader(InputStream inputStream, Kryo kryo) throws IOException {
            super(inputStream);
            this.loader = kryo.getClassLoader();
        }

        /* access modifiers changed from: protected */
        public Class<?> resolveClass(ObjectStreamClass objectStreamClass) {
            try {
                return Class.forName(objectStreamClass.getName(), false, this.loader);
            } catch (ClassNotFoundException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Class not found: ");
                sb.append(objectStreamClass.getName());
                throw new RuntimeException(sb.toString(), e);
            }
        }
    }

    public void write(Kryo kryo, Output output, Object obj) {
        try {
            ObjectMap graphContext = kryo.getGraphContext();
            ObjectOutputStream objectOutputStream = (ObjectOutputStream) graphContext.get(this);
            if (objectOutputStream == null) {
                objectOutputStream = new ObjectOutputStream(output);
                graphContext.put(this, objectOutputStream);
            }
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
        } catch (Exception e) {
            throw new KryoException("Error during Java serialization.", e);
        }
    }

    public Object read(Kryo kryo, Input input, Class cls) {
        try {
            ObjectMap graphContext = kryo.getGraphContext();
            ObjectInputStream objectInputStream = (ObjectInputStream) graphContext.get(this);
            if (objectInputStream == null) {
                objectInputStream = new ObjectInputStreamWithKryoClassLoader(input, kryo);
                graphContext.put(this, objectInputStream);
            }
            return objectInputStream.readObject();
        } catch (Exception e) {
            throw new KryoException("Error during Java deserialization.", e);
        }
    }
}

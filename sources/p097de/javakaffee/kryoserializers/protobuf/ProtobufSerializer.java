package p097de.javakaffee.kryoserializers.protobuf;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.google.protobuf.GeneratedMessage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* renamed from: de.javakaffee.kryoserializers.protobuf.ProtobufSerializer */
public class ProtobufSerializer<T extends GeneratedMessage> extends Serializer<T> {
    private Method parseFromMethod = null;

    public boolean getAcceptsNull() {
        return true;
    }

    public void write(Kryo kryo, Output output, T t) {
        if (t == null) {
            output.writeByte(0);
            output.flush();
            return;
        }
        byte[] byteArray = t.toByteArray();
        output.writeInt(byteArray.length + 1, true);
        output.writeBytes(byteArray);
        output.flush();
    }

    public T read(Kryo kryo, Input input, Class<T> cls) {
        String str = "Unable to deserialize protobuf ";
        int readInt = input.readInt(true);
        if (readInt == 0) {
            return null;
        }
        byte[] readBytes = input.readBytes(readInt - 1);
        try {
            return (GeneratedMessage) getParseFromMethod(cls).invoke(cls, new Object[]{readBytes});
        } catch (NoSuchMethodException e) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(e.getMessage());
            throw new RuntimeException(sb.toString(), e);
        } catch (InvocationTargetException e2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(e2.getMessage());
            throw new RuntimeException(sb2.toString(), e2);
        } catch (IllegalAccessException e3) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(e3.getMessage());
            throw new RuntimeException(sb3.toString(), e3);
        }
    }

    private Method getParseFromMethod(Class<T> cls) throws NoSuchMethodException {
        if (this.parseFromMethod == null) {
            this.parseFromMethod = cls.getMethod("parseFrom", new Class[]{byte[].class});
        }
        return this.parseFromMethod;
    }
}

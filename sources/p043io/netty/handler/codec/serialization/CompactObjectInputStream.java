package p043io.netty.handler.codec.serialization;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.StreamCorruptedException;

/* renamed from: io.netty.handler.codec.serialization.CompactObjectInputStream */
class CompactObjectInputStream extends ObjectInputStream {
    private final ClassResolver classResolver;

    CompactObjectInputStream(InputStream inputStream, ClassResolver classResolver2) throws IOException {
        super(inputStream);
        this.classResolver = classResolver2;
    }

    /* access modifiers changed from: protected */
    public void readStreamHeader() throws IOException {
        byte readByte = readByte() & 255;
        if (readByte != 5) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unsupported version: ");
            sb.append(readByte);
            throw new StreamCorruptedException(sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
        int read = read();
        if (read < 0) {
            throw new EOFException();
        } else if (read == 0) {
            return super.readClassDescriptor();
        } else {
            if (read == 1) {
                return ObjectStreamClass.lookupAny(this.classResolver.resolve(readUTF()));
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Unexpected class descriptor type: ");
            sb.append(read);
            throw new StreamCorruptedException(sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
        try {
            return this.classResolver.resolve(objectStreamClass.getName());
        } catch (ClassNotFoundException unused) {
            return super.resolveClass(objectStreamClass);
        }
    }
}

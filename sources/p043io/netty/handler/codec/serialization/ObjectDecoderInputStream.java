package p043io.netty.handler.codec.serialization;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.StreamCorruptedException;

/* renamed from: io.netty.handler.codec.serialization.ObjectDecoderInputStream */
public class ObjectDecoderInputStream extends InputStream implements ObjectInput {
    private final ClassResolver classResolver;

    /* renamed from: in */
    private final DataInputStream f3727in;
    private final int maxObjectSize;

    public ObjectDecoderInputStream(InputStream inputStream) {
        this(inputStream, (ClassLoader) null);
    }

    public ObjectDecoderInputStream(InputStream inputStream, ClassLoader classLoader) {
        this(inputStream, classLoader, 1048576);
    }

    public ObjectDecoderInputStream(InputStream inputStream, int i) {
        this(inputStream, null, i);
    }

    public ObjectDecoderInputStream(InputStream inputStream, ClassLoader classLoader, int i) {
        if (inputStream == null) {
            throw new NullPointerException("in");
        } else if (i > 0) {
            if (inputStream instanceof DataInputStream) {
                this.f3727in = (DataInputStream) inputStream;
            } else {
                this.f3727in = new DataInputStream(inputStream);
            }
            this.classResolver = ClassResolvers.weakCachingResolver(classLoader);
            this.maxObjectSize = i;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("maxObjectSize: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public Object readObject() throws ClassNotFoundException, IOException {
        int readInt = readInt();
        if (readInt <= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("invalid data length: ");
            sb.append(readInt);
            throw new StreamCorruptedException(sb.toString());
        } else if (readInt <= this.maxObjectSize) {
            return new CompactObjectInputStream(this.f3727in, this.classResolver).readObject();
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("data length too big: ");
            sb2.append(readInt);
            sb2.append(" (max: ");
            sb2.append(this.maxObjectSize);
            sb2.append(')');
            throw new StreamCorruptedException(sb2.toString());
        }
    }

    public int available() throws IOException {
        return this.f3727in.available();
    }

    public void close() throws IOException {
        this.f3727in.close();
    }

    public void mark(int i) {
        this.f3727in.mark(i);
    }

    public boolean markSupported() {
        return this.f3727in.markSupported();
    }

    public int read() throws IOException {
        return this.f3727in.read();
    }

    public final int read(byte[] bArr, int i, int i2) throws IOException {
        return this.f3727in.read(bArr, i, i2);
    }

    public final int read(byte[] bArr) throws IOException {
        return this.f3727in.read(bArr);
    }

    public final boolean readBoolean() throws IOException {
        return this.f3727in.readBoolean();
    }

    public final byte readByte() throws IOException {
        return this.f3727in.readByte();
    }

    public final char readChar() throws IOException {
        return this.f3727in.readChar();
    }

    public final double readDouble() throws IOException {
        return this.f3727in.readDouble();
    }

    public final float readFloat() throws IOException {
        return this.f3727in.readFloat();
    }

    public final void readFully(byte[] bArr, int i, int i2) throws IOException {
        this.f3727in.readFully(bArr, i, i2);
    }

    public final void readFully(byte[] bArr) throws IOException {
        this.f3727in.readFully(bArr);
    }

    public final int readInt() throws IOException {
        return this.f3727in.readInt();
    }

    @Deprecated
    public final String readLine() throws IOException {
        return this.f3727in.readLine();
    }

    public final long readLong() throws IOException {
        return this.f3727in.readLong();
    }

    public final short readShort() throws IOException {
        return this.f3727in.readShort();
    }

    public final int readUnsignedByte() throws IOException {
        return this.f3727in.readUnsignedByte();
    }

    public final int readUnsignedShort() throws IOException {
        return this.f3727in.readUnsignedShort();
    }

    public final String readUTF() throws IOException {
        return this.f3727in.readUTF();
    }

    public void reset() throws IOException {
        this.f3727in.reset();
    }

    public long skip(long j) throws IOException {
        return this.f3727in.skip(j);
    }

    public final int skipBytes(int i) throws IOException {
        return this.f3727in.skipBytes(i);
    }
}

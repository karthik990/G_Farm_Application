package p043io.netty.buffer;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import kotlin.UShort;

/* renamed from: io.netty.buffer.ByteBufInputStream */
public class ByteBufInputStream extends InputStream implements DataInput {
    private final ByteBuf buffer;
    private boolean closed;
    private final int endIndex;
    private final StringBuilder lineBuf;
    private final boolean releaseOnClose;
    private final int startIndex;

    public boolean markSupported() {
        return true;
    }

    public ByteBufInputStream(ByteBuf byteBuf) {
        this(byteBuf, byteBuf.readableBytes());
    }

    public ByteBufInputStream(ByteBuf byteBuf, int i) {
        this(byteBuf, i, false);
    }

    public ByteBufInputStream(ByteBuf byteBuf, boolean z) {
        this(byteBuf, byteBuf.readableBytes(), z);
    }

    public ByteBufInputStream(ByteBuf byteBuf, int i, boolean z) {
        this.lineBuf = new StringBuilder();
        if (byteBuf == null) {
            throw new NullPointerException("buffer");
        } else if (i < 0) {
            if (z) {
                byteBuf.release();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("length: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        } else if (i > byteBuf.readableBytes()) {
            if (z) {
                byteBuf.release();
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Too many bytes to be read - Needs ");
            sb2.append(i);
            sb2.append(", maximum is ");
            sb2.append(byteBuf.readableBytes());
            throw new IndexOutOfBoundsException(sb2.toString());
        } else {
            this.releaseOnClose = z;
            this.buffer = byteBuf;
            this.startIndex = byteBuf.readerIndex();
            this.endIndex = this.startIndex + i;
            byteBuf.markReaderIndex();
        }
    }

    public int readBytes() {
        return this.buffer.readerIndex() - this.startIndex;
    }

    public void close() throws IOException {
        try {
            super.close();
        } finally {
            if (this.releaseOnClose && !this.closed) {
                this.closed = true;
                this.buffer.release();
            }
        }
    }

    public int available() throws IOException {
        return this.endIndex - this.buffer.readerIndex();
    }

    public void mark(int i) {
        this.buffer.markReaderIndex();
    }

    public int read() throws IOException {
        if (!this.buffer.isReadable()) {
            return -1;
        }
        return this.buffer.readByte() & 255;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        int available = available();
        if (available == 0) {
            return -1;
        }
        int min = Math.min(available, i2);
        this.buffer.readBytes(bArr, i, min);
        return min;
    }

    public void reset() throws IOException {
        this.buffer.resetReaderIndex();
    }

    public long skip(long j) throws IOException {
        int skipBytes;
        if (j > 2147483647L) {
            skipBytes = skipBytes(Integer.MAX_VALUE);
        } else {
            skipBytes = skipBytes((int) j);
        }
        return (long) skipBytes;
    }

    public boolean readBoolean() throws IOException {
        checkAvailable(1);
        if (read() != 0) {
            return true;
        }
        return false;
    }

    public byte readByte() throws IOException {
        if (this.buffer.isReadable()) {
            return this.buffer.readByte();
        }
        throw new EOFException();
    }

    public char readChar() throws IOException {
        return (char) readShort();
    }

    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    public void readFully(byte[] bArr) throws IOException {
        readFully(bArr, 0, bArr.length);
    }

    public void readFully(byte[] bArr, int i, int i2) throws IOException {
        checkAvailable(i2);
        this.buffer.readBytes(bArr, i, i2);
    }

    public int readInt() throws IOException {
        checkAvailable(4);
        return this.buffer.readInt();
    }

    public String readLine() throws IOException {
        this.lineBuf.setLength(0);
        while (this.buffer.isReadable()) {
            short readUnsignedByte = this.buffer.readUnsignedByte();
            if (readUnsignedByte != 10) {
                if (readUnsignedByte != 13) {
                    this.lineBuf.append((char) readUnsignedByte);
                } else if (this.buffer.isReadable()) {
                    ByteBuf byteBuf = this.buffer;
                    if (((char) byteBuf.getUnsignedByte(byteBuf.readerIndex())) == 10) {
                        this.buffer.skipBytes(1);
                    }
                }
            }
            return this.lineBuf.toString();
        }
        return this.lineBuf.length() > 0 ? this.lineBuf.toString() : null;
    }

    public long readLong() throws IOException {
        checkAvailable(8);
        return this.buffer.readLong();
    }

    public short readShort() throws IOException {
        checkAvailable(2);
        return this.buffer.readShort();
    }

    public String readUTF() throws IOException {
        return DataInputStream.readUTF(this);
    }

    public int readUnsignedByte() throws IOException {
        return readByte() & 255;
    }

    public int readUnsignedShort() throws IOException {
        return readShort() & UShort.MAX_VALUE;
    }

    public int skipBytes(int i) throws IOException {
        int min = Math.min(available(), i);
        this.buffer.skipBytes(min);
        return min;
    }

    private void checkAvailable(int i) throws IOException {
        if (i < 0) {
            throw new IndexOutOfBoundsException("fieldSize cannot be a negative number");
        } else if (i > available()) {
            StringBuilder sb = new StringBuilder();
            sb.append("fieldSize is too long! Length is ");
            sb.append(i);
            sb.append(", but maximum is ");
            sb.append(available());
            throw new EOFException(sb.toString());
        }
    }
}

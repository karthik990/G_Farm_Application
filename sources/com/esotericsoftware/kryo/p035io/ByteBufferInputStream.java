package com.esotericsoftware.kryo.p035io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* renamed from: com.esotericsoftware.kryo.io.ByteBufferInputStream */
public class ByteBufferInputStream extends InputStream {
    private ByteBuffer byteBuffer;

    public ByteBufferInputStream() {
    }

    public ByteBufferInputStream(int i) {
        this(ByteBuffer.allocate(i));
        this.byteBuffer.flip();
    }

    public ByteBufferInputStream(ByteBuffer byteBuffer2) {
        this.byteBuffer = byteBuffer2;
    }

    public ByteBuffer getByteBuffer() {
        return this.byteBuffer;
    }

    public void setByteBuffer(ByteBuffer byteBuffer2) {
        this.byteBuffer = byteBuffer2;
    }

    public int read() throws IOException {
        if (!this.byteBuffer.hasRemaining()) {
            return -1;
        }
        return this.byteBuffer.get() & 255;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (i2 == 0) {
            return 0;
        }
        int min = Math.min(this.byteBuffer.remaining(), i2);
        if (min == 0) {
            return -1;
        }
        this.byteBuffer.get(bArr, i, min);
        return min;
    }

    public int available() throws IOException {
        return this.byteBuffer.remaining();
    }
}

package p043io.grpc.okhttp;

import okio.Buffer;
import p043io.grpc.internal.WritableBuffer;

/* renamed from: io.grpc.okhttp.OkHttpWritableBuffer */
class OkHttpWritableBuffer implements WritableBuffer {
    private final Buffer buffer;
    private int readableBytes;
    private int writableBytes;

    public void release() {
    }

    OkHttpWritableBuffer(Buffer buffer2, int i) {
        this.buffer = buffer2;
        this.writableBytes = i;
    }

    public void write(byte[] bArr, int i, int i2) {
        this.buffer.write(bArr, i, i2);
        this.writableBytes -= i2;
        this.readableBytes += i2;
    }

    public void write(byte b) {
        this.buffer.writeByte((int) b);
        this.writableBytes--;
        this.readableBytes++;
    }

    public int writableBytes() {
        return this.writableBytes;
    }

    public int readableBytes() {
        return this.readableBytes;
    }

    /* access modifiers changed from: 0000 */
    public Buffer buffer() {
        return this.buffer;
    }
}

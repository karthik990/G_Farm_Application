package p043io.grpc.internal;

/* renamed from: io.grpc.internal.AbstractReadableBuffer */
public abstract class AbstractReadableBuffer implements ReadableBuffer {
    public void close() {
    }

    public boolean hasArray() {
        return false;
    }

    public final int readInt() {
        checkReadable(4);
        return (readUnsignedByte() << 24) | (readUnsignedByte() << 16) | (readUnsignedByte() << 8) | readUnsignedByte();
    }

    public byte[] array() {
        throw new UnsupportedOperationException();
    }

    public int arrayOffset() {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public final void checkReadable(int i) {
        if (readableBytes() < i) {
            throw new IndexOutOfBoundsException();
        }
    }
}

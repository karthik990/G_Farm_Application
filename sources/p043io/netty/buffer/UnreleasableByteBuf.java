package p043io.netty.buffer;

import java.nio.ByteOrder;

/* renamed from: io.netty.buffer.UnreleasableByteBuf */
final class UnreleasableByteBuf extends WrappedByteBuf {
    private SwappedByteBuf swappedBuf;

    public boolean release() {
        return false;
    }

    public boolean release(int i) {
        return false;
    }

    public ByteBuf retain() {
        return this;
    }

    public ByteBuf retain(int i) {
        return this;
    }

    public ByteBuf touch() {
        return this;
    }

    public ByteBuf touch(Object obj) {
        return this;
    }

    UnreleasableByteBuf(ByteBuf byteBuf) {
        if (byteBuf instanceof UnreleasableByteBuf) {
            byteBuf = byteBuf.unwrap();
        }
        super(byteBuf);
    }

    public ByteBuf order(ByteOrder byteOrder) {
        if (byteOrder == null) {
            throw new NullPointerException("endianness");
        } else if (byteOrder == order()) {
            return this;
        } else {
            SwappedByteBuf swappedByteBuf = this.swappedBuf;
            if (swappedByteBuf == null) {
                swappedByteBuf = new SwappedByteBuf(this);
                this.swappedBuf = swappedByteBuf;
            }
            return swappedByteBuf;
        }
    }

    public ByteBuf asReadOnly() {
        return this.buf.isReadOnly() ? this : new UnreleasableByteBuf(this.buf.asReadOnly());
    }

    public ByteBuf readSlice(int i) {
        return new UnreleasableByteBuf(this.buf.readSlice(i));
    }

    public ByteBuf readRetainedSlice(int i) {
        return readSlice(i);
    }

    public ByteBuf slice() {
        return new UnreleasableByteBuf(this.buf.slice());
    }

    public ByteBuf retainedSlice() {
        return slice();
    }

    public ByteBuf slice(int i, int i2) {
        return new UnreleasableByteBuf(this.buf.slice(i, i2));
    }

    public ByteBuf retainedSlice(int i, int i2) {
        return slice(i, i2);
    }

    public ByteBuf duplicate() {
        return new UnreleasableByteBuf(this.buf.duplicate());
    }

    public ByteBuf retainedDuplicate() {
        return duplicate();
    }
}

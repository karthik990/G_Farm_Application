package p043io.netty.buffer;

import p043io.netty.util.internal.PlatformDependent;

/* renamed from: io.netty.buffer.UnsafeHeapSwappedByteBuf */
final class UnsafeHeapSwappedByteBuf extends AbstractUnsafeSwappedByteBuf {
    UnsafeHeapSwappedByteBuf(AbstractByteBuf abstractByteBuf) {
        super(abstractByteBuf);
    }

    private static int idx(ByteBuf byteBuf, int i) {
        return byteBuf.arrayOffset() + i;
    }

    /* access modifiers changed from: protected */
    public long _getLong(AbstractByteBuf abstractByteBuf, int i) {
        return PlatformDependent.getLong(abstractByteBuf.array(), idx(abstractByteBuf, i));
    }

    /* access modifiers changed from: protected */
    public int _getInt(AbstractByteBuf abstractByteBuf, int i) {
        return PlatformDependent.getInt(abstractByteBuf.array(), idx(abstractByteBuf, i));
    }

    /* access modifiers changed from: protected */
    public short _getShort(AbstractByteBuf abstractByteBuf, int i) {
        return PlatformDependent.getShort(abstractByteBuf.array(), idx(abstractByteBuf, i));
    }

    /* access modifiers changed from: protected */
    public void _setShort(AbstractByteBuf abstractByteBuf, int i, short s) {
        PlatformDependent.putShort(abstractByteBuf.array(), idx(abstractByteBuf, i), s);
    }

    /* access modifiers changed from: protected */
    public void _setInt(AbstractByteBuf abstractByteBuf, int i, int i2) {
        PlatformDependent.putInt(abstractByteBuf.array(), idx(abstractByteBuf, i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setLong(AbstractByteBuf abstractByteBuf, int i, long j) {
        PlatformDependent.putLong(abstractByteBuf.array(), idx(abstractByteBuf, i), j);
    }
}

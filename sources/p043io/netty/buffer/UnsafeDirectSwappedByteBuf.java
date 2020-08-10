package p043io.netty.buffer;

import p043io.netty.util.internal.PlatformDependent;

/* renamed from: io.netty.buffer.UnsafeDirectSwappedByteBuf */
final class UnsafeDirectSwappedByteBuf extends AbstractUnsafeSwappedByteBuf {
    UnsafeDirectSwappedByteBuf(AbstractByteBuf abstractByteBuf) {
        super(abstractByteBuf);
    }

    private static long addr(AbstractByteBuf abstractByteBuf, int i) {
        return abstractByteBuf.memoryAddress() + ((long) i);
    }

    /* access modifiers changed from: protected */
    public long _getLong(AbstractByteBuf abstractByteBuf, int i) {
        return PlatformDependent.getLong(addr(abstractByteBuf, i));
    }

    /* access modifiers changed from: protected */
    public int _getInt(AbstractByteBuf abstractByteBuf, int i) {
        return PlatformDependent.getInt(addr(abstractByteBuf, i));
    }

    /* access modifiers changed from: protected */
    public short _getShort(AbstractByteBuf abstractByteBuf, int i) {
        return PlatformDependent.getShort(addr(abstractByteBuf, i));
    }

    /* access modifiers changed from: protected */
    public void _setShort(AbstractByteBuf abstractByteBuf, int i, short s) {
        PlatformDependent.putShort(addr(abstractByteBuf, i), s);
    }

    /* access modifiers changed from: protected */
    public void _setInt(AbstractByteBuf abstractByteBuf, int i, int i2) {
        PlatformDependent.putInt(addr(abstractByteBuf, i), i2);
    }

    /* access modifiers changed from: protected */
    public void _setLong(AbstractByteBuf abstractByteBuf, int i, long j) {
        PlatformDependent.putLong(addr(abstractByteBuf, i), j);
    }
}

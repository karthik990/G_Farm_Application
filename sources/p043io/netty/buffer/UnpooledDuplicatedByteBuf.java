package p043io.netty.buffer;

/* renamed from: io.netty.buffer.UnpooledDuplicatedByteBuf */
class UnpooledDuplicatedByteBuf extends DuplicatedByteBuf {
    UnpooledDuplicatedByteBuf(AbstractByteBuf abstractByteBuf) {
        super(abstractByteBuf);
    }

    public AbstractByteBuf unwrap() {
        return (AbstractByteBuf) super.unwrap();
    }

    /* access modifiers changed from: protected */
    public byte _getByte(int i) {
        return unwrap()._getByte(i);
    }

    /* access modifiers changed from: protected */
    public short _getShort(int i) {
        return unwrap()._getShort(i);
    }

    /* access modifiers changed from: protected */
    public short _getShortLE(int i) {
        return unwrap()._getShortLE(i);
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int i) {
        return unwrap()._getUnsignedMedium(i);
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMediumLE(int i) {
        return unwrap()._getUnsignedMediumLE(i);
    }

    /* access modifiers changed from: protected */
    public int _getInt(int i) {
        return unwrap()._getInt(i);
    }

    /* access modifiers changed from: protected */
    public int _getIntLE(int i) {
        return unwrap()._getIntLE(i);
    }

    /* access modifiers changed from: protected */
    public long _getLong(int i) {
        return unwrap()._getLong(i);
    }

    /* access modifiers changed from: protected */
    public long _getLongLE(int i) {
        return unwrap()._getLongLE(i);
    }

    /* access modifiers changed from: protected */
    public void _setByte(int i, int i2) {
        unwrap()._setByte(i, i2);
    }

    /* access modifiers changed from: protected */
    public void _setShort(int i, int i2) {
        unwrap()._setShort(i, i2);
    }

    /* access modifiers changed from: protected */
    public void _setShortLE(int i, int i2) {
        unwrap()._setShortLE(i, i2);
    }

    /* access modifiers changed from: protected */
    public void _setMedium(int i, int i2) {
        unwrap()._setMedium(i, i2);
    }

    /* access modifiers changed from: protected */
    public void _setMediumLE(int i, int i2) {
        unwrap()._setMediumLE(i, i2);
    }

    /* access modifiers changed from: protected */
    public void _setInt(int i, int i2) {
        unwrap()._setInt(i, i2);
    }

    /* access modifiers changed from: protected */
    public void _setIntLE(int i, int i2) {
        unwrap()._setIntLE(i, i2);
    }

    /* access modifiers changed from: protected */
    public void _setLong(int i, long j) {
        unwrap()._setLong(i, j);
    }

    /* access modifiers changed from: protected */
    public void _setLongLE(int i, long j) {
        unwrap()._setLongLE(i, j);
    }
}

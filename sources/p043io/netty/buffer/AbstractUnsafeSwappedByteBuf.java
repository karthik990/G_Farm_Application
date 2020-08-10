package p043io.netty.buffer;

import java.nio.ByteOrder;
import kotlin.UShort;
import p043io.netty.util.internal.PlatformDependent;

/* renamed from: io.netty.buffer.AbstractUnsafeSwappedByteBuf */
abstract class AbstractUnsafeSwappedByteBuf extends SwappedByteBuf {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final boolean nativeByteOrder;
    private final AbstractByteBuf wrapped;

    /* access modifiers changed from: protected */
    public abstract int _getInt(AbstractByteBuf abstractByteBuf, int i);

    /* access modifiers changed from: protected */
    public abstract long _getLong(AbstractByteBuf abstractByteBuf, int i);

    /* access modifiers changed from: protected */
    public abstract short _getShort(AbstractByteBuf abstractByteBuf, int i);

    /* access modifiers changed from: protected */
    public abstract void _setInt(AbstractByteBuf abstractByteBuf, int i, int i2);

    /* access modifiers changed from: protected */
    public abstract void _setLong(AbstractByteBuf abstractByteBuf, int i, long j);

    /* access modifiers changed from: protected */
    public abstract void _setShort(AbstractByteBuf abstractByteBuf, int i, short s);

    AbstractUnsafeSwappedByteBuf(AbstractByteBuf abstractByteBuf) {
        super(abstractByteBuf);
        this.wrapped = abstractByteBuf;
        boolean z = true;
        if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER != (order() == ByteOrder.BIG_ENDIAN)) {
            z = false;
        }
        this.nativeByteOrder = z;
    }

    public final long getLong(int i) {
        this.wrapped.checkIndex(i, 8);
        long _getLong = _getLong(this.wrapped, i);
        return this.nativeByteOrder ? _getLong : Long.reverseBytes(_getLong);
    }

    public final float getFloat(int i) {
        return Float.intBitsToFloat(getInt(i));
    }

    public final double getDouble(int i) {
        return Double.longBitsToDouble(getLong(i));
    }

    public final char getChar(int i) {
        return (char) getShort(i);
    }

    public final long getUnsignedInt(int i) {
        return ((long) getInt(i)) & 4294967295L;
    }

    public final int getInt(int i) {
        this.wrapped.checkIndex0(i, 4);
        int _getInt = _getInt(this.wrapped, i);
        return this.nativeByteOrder ? _getInt : Integer.reverseBytes(_getInt);
    }

    public final int getUnsignedShort(int i) {
        return getShort(i) & UShort.MAX_VALUE;
    }

    public final short getShort(int i) {
        this.wrapped.checkIndex0(i, 2);
        short _getShort = _getShort(this.wrapped, i);
        return this.nativeByteOrder ? _getShort : Short.reverseBytes(_getShort);
    }

    public final ByteBuf setShort(int i, int i2) {
        this.wrapped.checkIndex0(i, 2);
        AbstractByteBuf abstractByteBuf = this.wrapped;
        short s = (short) i2;
        if (!this.nativeByteOrder) {
            s = Short.reverseBytes(s);
        }
        _setShort(abstractByteBuf, i, s);
        return this;
    }

    public final ByteBuf setInt(int i, int i2) {
        this.wrapped.checkIndex0(i, 4);
        AbstractByteBuf abstractByteBuf = this.wrapped;
        if (!this.nativeByteOrder) {
            i2 = Integer.reverseBytes(i2);
        }
        _setInt(abstractByteBuf, i, i2);
        return this;
    }

    public final ByteBuf setLong(int i, long j) {
        this.wrapped.checkIndex(i, 8);
        AbstractByteBuf abstractByteBuf = this.wrapped;
        if (!this.nativeByteOrder) {
            j = Long.reverseBytes(j);
        }
        _setLong(abstractByteBuf, i, j);
        return this;
    }

    public final ByteBuf setChar(int i, int i2) {
        setShort(i, i2);
        return this;
    }

    public final ByteBuf setFloat(int i, float f) {
        setInt(i, Float.floatToRawIntBits(f));
        return this;
    }

    public final ByteBuf setDouble(int i, double d) {
        setLong(i, Double.doubleToRawLongBits(d));
        return this;
    }

    public final ByteBuf writeShort(int i) {
        this.wrapped.ensureWritable(2);
        AbstractByteBuf abstractByteBuf = this.wrapped;
        int i2 = abstractByteBuf.writerIndex;
        short s = (short) i;
        if (!this.nativeByteOrder) {
            s = Short.reverseBytes(s);
        }
        _setShort(abstractByteBuf, i2, s);
        this.wrapped.writerIndex += 2;
        return this;
    }

    public final ByteBuf writeInt(int i) {
        this.wrapped.ensureWritable(4);
        AbstractByteBuf abstractByteBuf = this.wrapped;
        int i2 = abstractByteBuf.writerIndex;
        if (!this.nativeByteOrder) {
            i = Integer.reverseBytes(i);
        }
        _setInt(abstractByteBuf, i2, i);
        this.wrapped.writerIndex += 4;
        return this;
    }

    public final ByteBuf writeLong(long j) {
        this.wrapped.ensureWritable(8);
        AbstractByteBuf abstractByteBuf = this.wrapped;
        int i = abstractByteBuf.writerIndex;
        if (!this.nativeByteOrder) {
            j = Long.reverseBytes(j);
        }
        _setLong(abstractByteBuf, i, j);
        this.wrapped.writerIndex += 8;
        return this;
    }

    public final ByteBuf writeChar(int i) {
        writeShort(i);
        return this;
    }

    public final ByteBuf writeFloat(float f) {
        writeInt(Float.floatToRawIntBits(f));
        return this;
    }

    public final ByteBuf writeDouble(double d) {
        writeLong(Double.doubleToRawLongBits(d));
        return this;
    }
}

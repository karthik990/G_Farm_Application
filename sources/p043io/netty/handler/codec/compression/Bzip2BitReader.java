package p043io.netty.handler.codec.compression;

import p043io.netty.buffer.ByteBuf;

/* renamed from: io.netty.handler.codec.compression.Bzip2BitReader */
class Bzip2BitReader {
    private static final int MAX_COUNT_OF_READABLE_BYTES = 268435455;
    private long bitBuffer;
    private int bitCount;

    /* renamed from: in */
    private ByteBuf f3713in;

    Bzip2BitReader() {
    }

    /* access modifiers changed from: 0000 */
    public void setByteBuf(ByteBuf byteBuf) {
        this.f3713in = byteBuf;
    }

    /* access modifiers changed from: 0000 */
    public int readBits(int i) {
        int i2;
        long j;
        if (i < 0 || i > 32) {
            StringBuilder sb = new StringBuilder();
            sb.append("count: ");
            sb.append(i);
            sb.append(" (expected: 0-32 )");
            throw new IllegalArgumentException(sb.toString());
        }
        int i3 = this.bitCount;
        long j2 = this.bitBuffer;
        if (i3 < i) {
            int readableBytes = this.f3713in.readableBytes();
            if (readableBytes == 1) {
                j = (long) this.f3713in.readUnsignedByte();
                i2 = 8;
            } else if (readableBytes == 2) {
                j = (long) this.f3713in.readUnsignedShort();
                i2 = 16;
            } else if (readableBytes != 3) {
                j = this.f3713in.readUnsignedInt();
                i2 = 32;
            } else {
                j = (long) this.f3713in.readUnsignedMedium();
                i2 = 24;
            }
            j2 = (j2 << i2) | j;
            i3 += i2;
            this.bitBuffer = j2;
        }
        int i4 = i3 - i;
        this.bitCount = i4;
        return (int) ((j2 >>> i4) & (i != 32 ? (long) ((1 << i) - 1) : 4294967295L));
    }

    /* access modifiers changed from: 0000 */
    public boolean readBoolean() {
        return readBits(1) != 0;
    }

    /* access modifiers changed from: 0000 */
    public int readInt() {
        return readBits(32);
    }

    /* access modifiers changed from: 0000 */
    public void refill() {
        this.bitBuffer = (this.bitBuffer << 8) | ((long) this.f3713in.readUnsignedByte());
        this.bitCount += 8;
    }

    /* access modifiers changed from: 0000 */
    public boolean isReadable() {
        return this.bitCount > 0 || this.f3713in.isReadable();
    }

    /* access modifiers changed from: 0000 */
    public boolean hasReadableBits(int i) {
        if (i >= 0) {
            return this.bitCount >= i || ((this.f3713in.readableBytes() << 3) & Integer.MAX_VALUE) >= i - this.bitCount;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("count: ");
        sb.append(i);
        sb.append(" (expected value greater than 0)");
        throw new IllegalArgumentException(sb.toString());
    }

    /* access modifiers changed from: 0000 */
    public boolean hasReadableBytes(int i) {
        if (i >= 0 && i <= MAX_COUNT_OF_READABLE_BYTES) {
            return hasReadableBits(i << 3);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("count: ");
        sb.append(i);
        sb.append(" (expected: 0-");
        sb.append(MAX_COUNT_OF_READABLE_BYTES);
        sb.append(')');
        throw new IllegalArgumentException(sb.toString());
    }
}

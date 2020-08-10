package p043io.netty.handler.codec.compression;

import p043io.netty.buffer.ByteBuf;

/* renamed from: io.netty.handler.codec.compression.Bzip2BitWriter */
final class Bzip2BitWriter {
    private long bitBuffer;
    private int bitCount;

    Bzip2BitWriter() {
    }

    /* access modifiers changed from: 0000 */
    public void writeBits(ByteBuf byteBuf, int i, long j) {
        if (i < 0 || i > 32) {
            StringBuilder sb = new StringBuilder();
            sb.append("count: ");
            sb.append(i);
            sb.append(" (expected: 0-32)");
            throw new IllegalArgumentException(sb.toString());
        }
        int i2 = this.bitCount;
        long j2 = ((j << (64 - i)) >>> i2) | this.bitBuffer;
        int i3 = i2 + i;
        if (i3 >= 32) {
            byteBuf.writeInt((int) (j2 >>> 32));
            j2 <<= 32;
            i3 -= 32;
        }
        this.bitBuffer = j2;
        this.bitCount = i3;
    }

    /* access modifiers changed from: 0000 */
    public void writeBoolean(ByteBuf byteBuf, boolean z) {
        int i = this.bitCount + 1;
        long j = this.bitBuffer | (z ? 1 << (64 - i) : 0);
        if (i == 32) {
            byteBuf.writeInt((int) (j >>> 32));
            i = 0;
            j = 0;
        }
        this.bitBuffer = j;
        this.bitCount = i;
    }

    /* access modifiers changed from: 0000 */
    public void writeUnary(ByteBuf byteBuf, int i) {
        if (i >= 0) {
            while (true) {
                int i2 = i - 1;
                if (i > 0) {
                    writeBoolean(byteBuf, true);
                    i = i2;
                } else {
                    writeBoolean(byteBuf, false);
                    return;
                }
            }
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("value: ");
            sb.append(i);
            sb.append(" (expected 0 or more)");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public void writeInt(ByteBuf byteBuf, int i) {
        writeBits(byteBuf, 32, (long) i);
    }

    /* access modifiers changed from: 0000 */
    public void flush(ByteBuf byteBuf) {
        int i = this.bitCount;
        if (i > 0) {
            long j = this.bitBuffer;
            int i2 = 64 - i;
            if (i <= 8) {
                byteBuf.writeByte((int) ((j >>> i2) << (8 - i)));
            } else if (i <= 16) {
                byteBuf.writeShort((int) ((j >>> i2) << (16 - i)));
            } else if (i <= 24) {
                byteBuf.writeMedium((int) ((j >>> i2) << (24 - i)));
            } else {
                byteBuf.writeInt((int) ((j >>> i2) << (32 - i)));
            }
        }
    }
}

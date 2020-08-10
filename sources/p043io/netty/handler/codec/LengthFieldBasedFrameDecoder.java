package p043io.netty.handler.codec;

import java.nio.ByteOrder;
import java.util.List;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelHandlerContext;

/* renamed from: io.netty.handler.codec.LengthFieldBasedFrameDecoder */
public class LengthFieldBasedFrameDecoder extends ByteToMessageDecoder {
    private final ByteOrder byteOrder;
    private long bytesToDiscard;
    private boolean discardingTooLongFrame;
    private final boolean failFast;
    private final int initialBytesToStrip;
    private final int lengthAdjustment;
    private final int lengthFieldEndOffset;
    private final int lengthFieldLength;
    private final int lengthFieldOffset;
    private final int maxFrameLength;
    private long tooLongFrameLength;

    public LengthFieldBasedFrameDecoder(int i, int i2, int i3) {
        this(i, i2, i3, 0, 0);
    }

    public LengthFieldBasedFrameDecoder(int i, int i2, int i3, int i4, int i5) {
        this(i, i2, i3, i4, i5, true);
    }

    public LengthFieldBasedFrameDecoder(int i, int i2, int i3, int i4, int i5, boolean z) {
        this(ByteOrder.BIG_ENDIAN, i, i2, i3, i4, i5, z);
    }

    public LengthFieldBasedFrameDecoder(ByteOrder byteOrder2, int i, int i2, int i3, int i4, int i5, boolean z) {
        if (byteOrder2 == null) {
            throw new NullPointerException("byteOrder");
        } else if (i <= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("maxFrameLength must be a positive integer: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        } else if (i2 < 0) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("lengthFieldOffset must be a non-negative integer: ");
            sb2.append(i2);
            throw new IllegalArgumentException(sb2.toString());
        } else if (i5 < 0) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("initialBytesToStrip must be a non-negative integer: ");
            sb3.append(i5);
            throw new IllegalArgumentException(sb3.toString());
        } else if (i2 <= i - i3) {
            this.byteOrder = byteOrder2;
            this.maxFrameLength = i;
            this.lengthFieldOffset = i2;
            this.lengthFieldLength = i3;
            this.lengthAdjustment = i4;
            this.lengthFieldEndOffset = i2 + i3;
            this.initialBytesToStrip = i5;
            this.failFast = z;
        } else {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("maxFrameLength (");
            sb4.append(i);
            sb4.append(") must be equal to or greater than lengthFieldOffset (");
            sb4.append(i2);
            sb4.append(") + lengthFieldLength (");
            sb4.append(i3);
            sb4.append(").");
            throw new IllegalArgumentException(sb4.toString());
        }
    }

    /* access modifiers changed from: protected */
    public final void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        Object decode = decode(channelHandlerContext, byteBuf);
        if (decode != null) {
            list.add(decode);
        }
    }

    /* access modifiers changed from: protected */
    public Object decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        if (this.discardingTooLongFrame) {
            long j = this.bytesToDiscard;
            int min = (int) Math.min(j, (long) byteBuf.readableBytes());
            byteBuf.skipBytes(min);
            this.bytesToDiscard = j - ((long) min);
            failIfNecessary(false);
        }
        if (byteBuf.readableBytes() < this.lengthFieldEndOffset) {
            return null;
        }
        long unadjustedFrameLength = getUnadjustedFrameLength(byteBuf, byteBuf.readerIndex() + this.lengthFieldOffset, this.lengthFieldLength, this.byteOrder);
        if (unadjustedFrameLength >= 0) {
            int i = this.lengthAdjustment;
            int i2 = this.lengthFieldEndOffset;
            long j2 = unadjustedFrameLength + ((long) (i + i2));
            String str = "Adjusted frame length (";
            if (j2 < ((long) i2)) {
                byteBuf.skipBytes(i2);
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(j2);
                sb.append(") is less than lengthFieldEndOffset: ");
                sb.append(this.lengthFieldEndOffset);
                throw new CorruptedFrameException(sb.toString());
            } else if (j2 > ((long) this.maxFrameLength)) {
                long readableBytes = j2 - ((long) byteBuf.readableBytes());
                this.tooLongFrameLength = j2;
                if (readableBytes < 0) {
                    byteBuf.skipBytes((int) j2);
                } else {
                    this.discardingTooLongFrame = true;
                    this.bytesToDiscard = readableBytes;
                    byteBuf.skipBytes(byteBuf.readableBytes());
                }
                failIfNecessary(true);
                return null;
            } else {
                int i3 = (int) j2;
                if (byteBuf.readableBytes() < i3) {
                    return null;
                }
                int i4 = this.initialBytesToStrip;
                if (i4 <= i3) {
                    byteBuf.skipBytes(i4);
                    int readerIndex = byteBuf.readerIndex();
                    int i5 = i3 - this.initialBytesToStrip;
                    ByteBuf extractFrame = extractFrame(channelHandlerContext, byteBuf, readerIndex, i5);
                    byteBuf.readerIndex(readerIndex + i5);
                    return extractFrame;
                }
                byteBuf.skipBytes(i3);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append(j2);
                sb2.append(") is less than initialBytesToStrip: ");
                sb2.append(this.initialBytesToStrip);
                throw new CorruptedFrameException(sb2.toString());
            }
        } else {
            byteBuf.skipBytes(this.lengthFieldEndOffset);
            StringBuilder sb3 = new StringBuilder();
            sb3.append("negative pre-adjustment length field: ");
            sb3.append(unadjustedFrameLength);
            throw new CorruptedFrameException(sb3.toString());
        }
    }

    /* access modifiers changed from: protected */
    public long getUnadjustedFrameLength(ByteBuf byteBuf, int i, int i2, ByteOrder byteOrder2) {
        int i3;
        ByteBuf order = byteBuf.order(byteOrder2);
        if (i2 == 1) {
            i3 = order.getUnsignedByte(i);
        } else if (i2 == 2) {
            i3 = order.getUnsignedShort(i);
        } else if (i2 == 3) {
            i3 = order.getUnsignedMedium(i);
        } else if (i2 == 4) {
            return order.getUnsignedInt(i);
        } else {
            if (i2 == 8) {
                return order.getLong(i);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("unsupported lengthFieldLength: ");
            sb.append(this.lengthFieldLength);
            sb.append(" (expected: 1, 2, 3, 4, or 8)");
            throw new DecoderException(sb.toString());
        }
        return (long) i3;
    }

    private void failIfNecessary(boolean z) {
        if (this.bytesToDiscard == 0) {
            long j = this.tooLongFrameLength;
            this.tooLongFrameLength = 0;
            this.discardingTooLongFrame = false;
            if (!this.failFast || z) {
                fail(j);
            }
        } else if (this.failFast && z) {
            fail(this.tooLongFrameLength);
        }
    }

    /* access modifiers changed from: protected */
    public ByteBuf extractFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, int i, int i2) {
        return byteBuf.retainedSlice(i, i2);
    }

    private void fail(long j) {
        String str = "Adjusted frame length exceeds ";
        if (j > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(this.maxFrameLength);
            sb.append(": ");
            sb.append(j);
            sb.append(" - discarded");
            throw new TooLongFrameException(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(this.maxFrameLength);
        sb2.append(" - discarding");
        throw new TooLongFrameException(sb2.toString());
    }
}

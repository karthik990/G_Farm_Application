package p043io.netty.handler.codec;

import java.nio.ByteOrder;
import java.util.List;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelHandler.Sharable;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.util.internal.ObjectUtil;

@Sharable
/* renamed from: io.netty.handler.codec.LengthFieldPrepender */
public class LengthFieldPrepender extends MessageToMessageEncoder<ByteBuf> {
    private final ByteOrder byteOrder;
    private final int lengthAdjustment;
    private final int lengthFieldLength;
    private final boolean lengthIncludesLengthFieldLength;

    public LengthFieldPrepender(int i) {
        this(i, false);
    }

    public LengthFieldPrepender(int i, boolean z) {
        this(i, 0, z);
    }

    public LengthFieldPrepender(int i, int i2) {
        this(i, i2, false);
    }

    public LengthFieldPrepender(int i, int i2, boolean z) {
        this(ByteOrder.BIG_ENDIAN, i, i2, z);
    }

    public LengthFieldPrepender(ByteOrder byteOrder2, int i, int i2, boolean z) {
        if (i == 1 || i == 2 || i == 3 || i == 4 || i == 8) {
            ObjectUtil.checkNotNull(byteOrder2, "byteOrder");
            this.byteOrder = byteOrder2;
            this.lengthFieldLength = i;
            this.lengthIncludesLengthFieldLength = z;
            this.lengthAdjustment = i2;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("lengthFieldLength must be either 1, 2, 3, 4, or 8: ");
        sb.append(i);
        throw new IllegalArgumentException(sb.toString());
    }

    /* access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int readableBytes = byteBuf.readableBytes() + this.lengthAdjustment;
        if (this.lengthIncludesLengthFieldLength) {
            readableBytes += this.lengthFieldLength;
        }
        if (readableBytes >= 0) {
            int i = this.lengthFieldLength;
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i == 4) {
                            list.add(channelHandlerContext.alloc().buffer(4).order(this.byteOrder).writeInt(readableBytes));
                        } else if (i == 8) {
                            list.add(channelHandlerContext.alloc().buffer(8).order(this.byteOrder).writeLong((long) readableBytes));
                        } else {
                            throw new Error("should not reach here");
                        }
                    } else if (readableBytes < 16777216) {
                        list.add(channelHandlerContext.alloc().buffer(3).order(this.byteOrder).writeMedium(readableBytes));
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("length does not fit into a medium integer: ");
                        sb.append(readableBytes);
                        throw new IllegalArgumentException(sb.toString());
                    }
                } else if (readableBytes < 65536) {
                    list.add(channelHandlerContext.alloc().buffer(2).order(this.byteOrder).writeShort((short) readableBytes));
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("length does not fit into a short integer: ");
                    sb2.append(readableBytes);
                    throw new IllegalArgumentException(sb2.toString());
                }
            } else if (readableBytes < 256) {
                list.add(channelHandlerContext.alloc().buffer(1).order(this.byteOrder).writeByte((byte) readableBytes));
            } else {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("length does not fit into a byte: ");
                sb3.append(readableBytes);
                throw new IllegalArgumentException(sb3.toString());
            }
            list.add(byteBuf.retain());
            return;
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append("Adjusted frame length (");
        sb4.append(readableBytes);
        sb4.append(") is less than zero");
        throw new IllegalArgumentException(sb4.toString());
    }
}

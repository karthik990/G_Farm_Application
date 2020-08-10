package p043io.netty.handler.codec;

import java.util.List;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.util.ByteProcessor;

/* renamed from: io.netty.handler.codec.LineBasedFrameDecoder */
public class LineBasedFrameDecoder extends ByteToMessageDecoder {
    private int discardedBytes;
    private boolean discarding;
    private final boolean failFast;
    private final int maxLength;
    private final boolean stripDelimiter;

    public LineBasedFrameDecoder(int i) {
        this(i, true, false);
    }

    public LineBasedFrameDecoder(int i, boolean z, boolean z2) {
        this.maxLength = i;
        this.failFast = z2;
        this.stripDelimiter = z;
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
        ByteBuf byteBuf2;
        int findEndOfLine = findEndOfLine(byteBuf);
        int i = 2;
        if (this.discarding) {
            if (findEndOfLine >= 0) {
                int readerIndex = (this.discardedBytes + findEndOfLine) - byteBuf.readerIndex();
                if (byteBuf.getByte(findEndOfLine) != 13) {
                    i = 1;
                }
                byteBuf.readerIndex(findEndOfLine + i);
                this.discardedBytes = 0;
                this.discarding = false;
                if (!this.failFast) {
                    fail(channelHandlerContext, readerIndex);
                }
            } else {
                this.discardedBytes += byteBuf.readableBytes();
                byteBuf.readerIndex(byteBuf.writerIndex());
            }
            return null;
        } else if (findEndOfLine >= 0) {
            int readerIndex2 = findEndOfLine - byteBuf.readerIndex();
            if (byteBuf.getByte(findEndOfLine) != 13) {
                i = 1;
            }
            if (readerIndex2 > this.maxLength) {
                byteBuf.readerIndex(findEndOfLine + i);
                fail(channelHandlerContext, readerIndex2);
                return null;
            }
            if (this.stripDelimiter) {
                byteBuf2 = byteBuf.readRetainedSlice(readerIndex2);
                byteBuf.skipBytes(i);
            } else {
                byteBuf2 = byteBuf.readRetainedSlice(readerIndex2 + i);
            }
            return byteBuf2;
        } else {
            int readableBytes = byteBuf.readableBytes();
            if (readableBytes > this.maxLength) {
                this.discardedBytes = readableBytes;
                byteBuf.readerIndex(byteBuf.writerIndex());
                this.discarding = true;
                if (this.failFast) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("over ");
                    sb.append(this.discardedBytes);
                    fail(channelHandlerContext, sb.toString());
                }
            }
            return null;
        }
    }

    private void fail(ChannelHandlerContext channelHandlerContext, int i) {
        fail(channelHandlerContext, String.valueOf(i));
    }

    private void fail(ChannelHandlerContext channelHandlerContext, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("frame length (");
        sb.append(str);
        sb.append(") exceeds the allowed maximum (");
        sb.append(this.maxLength);
        sb.append(')');
        channelHandlerContext.fireExceptionCaught(new TooLongFrameException(sb.toString()));
    }

    private static int findEndOfLine(ByteBuf byteBuf) {
        int forEachByte = byteBuf.forEachByte(ByteProcessor.FIND_LF);
        return (forEachByte <= 0 || byteBuf.getByte(forEachByte + -1) != 13) ? forEachByte : forEachByte - 1;
    }
}

package p043io.netty.handler.codec;

import java.util.List;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelHandlerContext;

/* renamed from: io.netty.handler.codec.DelimiterBasedFrameDecoder */
public class DelimiterBasedFrameDecoder extends ByteToMessageDecoder {
    private final ByteBuf[] delimiters;
    private boolean discardingTooLongFrame;
    private final boolean failFast;
    private final LineBasedFrameDecoder lineBasedDecoder;
    private final int maxFrameLength;
    private final boolean stripDelimiter;
    private int tooLongFrameLength;

    public DelimiterBasedFrameDecoder(int i, ByteBuf byteBuf) {
        this(i, true, byteBuf);
    }

    public DelimiterBasedFrameDecoder(int i, boolean z, ByteBuf byteBuf) {
        this(i, z, true, byteBuf);
    }

    public DelimiterBasedFrameDecoder(int i, boolean z, boolean z2, ByteBuf byteBuf) {
        this(i, z, z2, byteBuf.slice(byteBuf.readerIndex(), byteBuf.readableBytes()));
    }

    public DelimiterBasedFrameDecoder(int i, ByteBuf... byteBufArr) {
        this(i, true, byteBufArr);
    }

    public DelimiterBasedFrameDecoder(int i, boolean z, ByteBuf... byteBufArr) {
        this(i, z, true, byteBufArr);
    }

    public DelimiterBasedFrameDecoder(int i, boolean z, boolean z2, ByteBuf... byteBufArr) {
        validateMaxFrameLength(i);
        if (byteBufArr == null) {
            throw new NullPointerException("delimiters");
        } else if (byteBufArr.length != 0) {
            if (!isLineBased(byteBufArr) || isSubclass()) {
                this.delimiters = new ByteBuf[byteBufArr.length];
                for (int i2 = 0; i2 < byteBufArr.length; i2++) {
                    ByteBuf byteBuf = byteBufArr[i2];
                    validateDelimiter(byteBuf);
                    this.delimiters[i2] = byteBuf.slice(byteBuf.readerIndex(), byteBuf.readableBytes());
                }
                this.lineBasedDecoder = null;
            } else {
                this.lineBasedDecoder = new LineBasedFrameDecoder(i, z, z2);
                this.delimiters = null;
            }
            this.maxFrameLength = i;
            this.stripDelimiter = z;
            this.failFast = z2;
        } else {
            throw new IllegalArgumentException("empty delimiters");
        }
    }

    private static boolean isLineBased(ByteBuf[] byteBufArr) {
        boolean z = false;
        if (byteBufArr.length != 2) {
            return false;
        }
        ByteBuf byteBuf = byteBufArr[0];
        ByteBuf byteBuf2 = byteBufArr[1];
        if (byteBuf.capacity() < byteBuf2.capacity()) {
            byteBuf = byteBufArr[1];
            byteBuf2 = byteBufArr[0];
        }
        if (byteBuf.capacity() == 2 && byteBuf2.capacity() == 1 && byteBuf.getByte(0) == 13 && byteBuf.getByte(1) == 10 && byteBuf2.getByte(0) == 10) {
            z = true;
        }
        return z;
    }

    private boolean isSubclass() {
        return getClass() != DelimiterBasedFrameDecoder.class;
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
        ByteBuf[] byteBufArr;
        ByteBuf byteBuf2;
        LineBasedFrameDecoder lineBasedFrameDecoder = this.lineBasedDecoder;
        if (lineBasedFrameDecoder != null) {
            return lineBasedFrameDecoder.decode(channelHandlerContext, byteBuf);
        }
        ByteBuf byteBuf3 = null;
        int i = Integer.MAX_VALUE;
        for (ByteBuf byteBuf4 : this.delimiters) {
            int indexOf = indexOf(byteBuf, byteBuf4);
            if (indexOf >= 0 && indexOf < i) {
                byteBuf3 = byteBuf4;
                i = indexOf;
            }
        }
        if (byteBuf3 != null) {
            int capacity = byteBuf3.capacity();
            if (this.discardingTooLongFrame) {
                this.discardingTooLongFrame = false;
                byteBuf.skipBytes(i + capacity);
                int i2 = this.tooLongFrameLength;
                this.tooLongFrameLength = 0;
                if (!this.failFast) {
                    fail((long) i2);
                }
                return null;
            } else if (i > this.maxFrameLength) {
                byteBuf.skipBytes(capacity + i);
                fail((long) i);
                return null;
            } else {
                if (this.stripDelimiter) {
                    byteBuf2 = byteBuf.readRetainedSlice(i);
                    byteBuf.skipBytes(capacity);
                } else {
                    byteBuf2 = byteBuf.readRetainedSlice(i + capacity);
                }
                return byteBuf2;
            }
        } else {
            if (this.discardingTooLongFrame) {
                this.tooLongFrameLength += byteBuf.readableBytes();
                byteBuf.skipBytes(byteBuf.readableBytes());
            } else if (byteBuf.readableBytes() > this.maxFrameLength) {
                this.tooLongFrameLength = byteBuf.readableBytes();
                byteBuf.skipBytes(byteBuf.readableBytes());
                this.discardingTooLongFrame = true;
                if (this.failFast) {
                    fail((long) this.tooLongFrameLength);
                }
            }
            return null;
        }
    }

    private void fail(long j) {
        String str = "frame length exceeds ";
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

    private static int indexOf(ByteBuf byteBuf, ByteBuf byteBuf2) {
        for (int readerIndex = byteBuf.readerIndex(); readerIndex < byteBuf.writerIndex(); readerIndex++) {
            int i = 0;
            int i2 = readerIndex;
            while (i < byteBuf2.capacity() && byteBuf.getByte(i2) == byteBuf2.getByte(i)) {
                i2++;
                if (i2 == byteBuf.writerIndex() && i != byteBuf2.capacity() - 1) {
                    return -1;
                }
                i++;
            }
            if (i == byteBuf2.capacity()) {
                return readerIndex - byteBuf.readerIndex();
            }
        }
        return -1;
    }

    private static void validateDelimiter(ByteBuf byteBuf) {
        if (byteBuf == null) {
            throw new NullPointerException("delimiter");
        } else if (!byteBuf.isReadable()) {
            throw new IllegalArgumentException("empty delimiter");
        }
    }

    private static void validateMaxFrameLength(int i) {
        if (i <= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("maxFrameLength must be a positive integer: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
    }
}

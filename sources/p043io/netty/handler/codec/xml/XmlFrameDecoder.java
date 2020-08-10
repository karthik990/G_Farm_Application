package p043io.netty.handler.codec.xml;

import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.handler.codec.ByteToMessageDecoder;
import p043io.netty.handler.codec.CorruptedFrameException;
import p043io.netty.handler.codec.TooLongFrameException;

/* renamed from: io.netty.handler.codec.xml.XmlFrameDecoder */
public class XmlFrameDecoder extends ByteToMessageDecoder {
    private final int maxFrameLength;

    private static boolean isValidStartCharForXmlElement(byte b) {
        return (b >= 97 && b <= 122) || (b >= 65 && b <= 90) || b == 58 || b == 95;
    }

    public XmlFrameDecoder(int i) {
        if (i >= 1) {
            this.maxFrameLength = i;
            return;
        }
        throw new IllegalArgumentException("maxFrameLength must be a positive int");
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x008d, code lost:
        if (r7 == 63) goto L_0x008f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00c6, code lost:
        if (r1.getByte(r4) == 45) goto L_0x00b7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void decode(p043io.netty.channel.ChannelHandlerContext r20, p043io.netty.buffer.ByteBuf r21, java.util.List<java.lang.Object> r22) throws java.lang.Exception {
        /*
            r19 = this;
            r0 = r19
            r1 = r21
            int r2 = r21.writerIndex()
            int r3 = r0.maxFrameLength
            if (r2 <= r3) goto L_0x0018
            int r3 = r21.readableBytes()
            r1.skipBytes(r3)
            long r1 = (long) r2
            r0.fail(r1)
            return
        L_0x0018:
            int r3 = r21.readerIndex()
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r12 = 0
            r13 = 0
        L_0x0023:
            if (r3 >= r2) goto L_0x00e7
            byte r14 = r1.getByte(r3)
            if (r7 != 0) goto L_0x0035
            boolean r16 = java.lang.Character.isWhitespace(r14)
            if (r16 == 0) goto L_0x0035
            int r8 = r8 + 1
            goto L_0x00e3
        L_0x0035:
            r6 = 60
            if (r7 != 0) goto L_0x0046
            if (r14 == r6) goto L_0x0046
            fail(r20)
            int r2 = r21.readableBytes()
            r1.skipBytes(r2)
            return
        L_0x0046:
            r15 = 63
            r4 = 47
            r5 = 62
            r17 = 1
            if (r9 != 0) goto L_0x0093
            if (r14 != r6) goto L_0x0093
            int r6 = r2 + -1
            if (r3 >= r6) goto L_0x0091
            int r7 = r3 + 1
            byte r7 = r1.getByte(r7)
            if (r7 != r4) goto L_0x006e
            int r4 = r3 + 2
        L_0x0060:
            if (r4 > r6) goto L_0x0091
            byte r7 = r1.getByte(r4)
            if (r7 != r5) goto L_0x006b
            long r10 = r10 - r17
            goto L_0x0091
        L_0x006b:
            int r4 = r4 + 1
            goto L_0x0060
        L_0x006e:
            boolean r4 = isValidStartCharForXmlElement(r7)
            if (r4 == 0) goto L_0x0078
            long r10 = r10 + r17
            r13 = 1
            goto L_0x0091
        L_0x0078:
            r4 = 33
            if (r7 != r4) goto L_0x008d
            boolean r4 = isCommentBlockStart(r1, r3)
            if (r4 == 0) goto L_0x0083
            goto L_0x008f
        L_0x0083:
            boolean r4 = isCDATABlockStart(r1, r3)
            if (r4 == 0) goto L_0x0091
            long r10 = r10 + r17
            r9 = 1
            goto L_0x0091
        L_0x008d:
            if (r7 != r15) goto L_0x0091
        L_0x008f:
            long r10 = r10 + r17
        L_0x0091:
            r7 = 1
            goto L_0x00e3
        L_0x0093:
            if (r9 != 0) goto L_0x00a6
            if (r14 != r4) goto L_0x00a6
            int r4 = r2 + -1
            if (r3 >= r4) goto L_0x00e3
            int r4 = r3 + 1
            byte r4 = r1.getByte(r4)
            if (r4 != r5) goto L_0x00e3
            long r10 = r10 - r17
            goto L_0x00e3
        L_0x00a6:
            if (r14 != r5) goto L_0x00e3
            int r12 = r3 + 1
            int r4 = r3 + -1
            r5 = -1
            if (r4 <= r5) goto L_0x00da
            byte r4 = r1.getByte(r4)
            if (r9 != 0) goto L_0x00c9
            if (r4 != r15) goto L_0x00ba
        L_0x00b7:
            long r10 = r10 - r17
            goto L_0x00da
        L_0x00ba:
            r6 = 45
            if (r4 != r6) goto L_0x00da
            int r4 = r3 + -2
            if (r4 <= r5) goto L_0x00da
            byte r4 = r1.getByte(r4)
            if (r4 != r6) goto L_0x00da
            goto L_0x00b7
        L_0x00c9:
            r6 = 93
            if (r4 != r6) goto L_0x00da
            int r4 = r3 + -2
            if (r4 <= r5) goto L_0x00da
            byte r4 = r1.getByte(r4)
            if (r4 != r6) goto L_0x00da
            long r10 = r10 - r17
            r9 = 0
        L_0x00da:
            if (r13 == 0) goto L_0x00e3
            r4 = 0
            int r6 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r6 != 0) goto L_0x00e3
            goto L_0x00e7
        L_0x00e3:
            int r3 = r3 + 1
            goto L_0x0023
        L_0x00e7:
            int r3 = r21.readerIndex()
            int r12 = r12 - r3
            r4 = 0
            int r6 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r6 != 0) goto L_0x010b
            if (r12 <= 0) goto L_0x010b
            int r4 = r3 + r12
            if (r4 < r2) goto L_0x00fc
            int r12 = r21.readableBytes()
        L_0x00fc:
            int r3 = r3 + r8
            int r2 = r12 - r8
            io.netty.buffer.ByteBuf r2 = extractFrame(r1, r3, r2)
            r1.skipBytes(r12)
            r1 = r22
            r1.add(r2)
        L_0x010b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.xml.XmlFrameDecoder.decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List):void");
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

    private static void fail(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.fireExceptionCaught(new CorruptedFrameException("frame contains content before the xml starts"));
    }

    private static ByteBuf extractFrame(ByteBuf byteBuf, int i, int i2) {
        return byteBuf.copy(i, i2);
    }

    private static boolean isCommentBlockStart(ByteBuf byteBuf, int i) {
        return i < byteBuf.writerIndex() + -3 && byteBuf.getByte(i + 2) == 45 && byteBuf.getByte(i + 3) == 45;
    }

    private static boolean isCDATABlockStart(ByteBuf byteBuf, int i) {
        return i < byteBuf.writerIndex() + -8 && byteBuf.getByte(i + 2) == 91 && byteBuf.getByte(i + 3) == 67 && byteBuf.getByte(i + 4) == 68 && byteBuf.getByte(i + 5) == 65 && byteBuf.getByte(i + 6) == 84 && byteBuf.getByte(i + 7) == 65 && byteBuf.getByte(i + 8) == 91;
    }
}

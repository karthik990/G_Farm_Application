package p043io.netty.handler.ssl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.net.ssl.SSLHandshakeException;
import kotlin.UShort;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.buffer.ByteBufUtil;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.handler.codec.base64.Base64;
import p043io.netty.handler.codec.base64.Base64Dialect;

/* renamed from: io.netty.handler.ssl.SslUtils */
final class SslUtils {
    static final int NOT_ENCRYPTED = -2;
    static final int NOT_ENOUGH_DATA = -1;
    static final int SSL_CONTENT_TYPE_ALERT = 21;
    static final int SSL_CONTENT_TYPE_APPLICATION_DATA = 23;
    static final int SSL_CONTENT_TYPE_CHANGE_CIPHER_SPEC = 20;
    static final int SSL_CONTENT_TYPE_EXTENSION_HEARTBEAT = 24;
    static final int SSL_CONTENT_TYPE_HANDSHAKE = 22;
    static final int SSL_RECORD_HEADER_LENGTH = 5;

    private static short unsignedByte(byte b) {
        return (short) (b & 255);
    }

    static SSLHandshakeException toSSLHandshakeException(Throwable th) {
        if (th instanceof SSLHandshakeException) {
            return (SSLHandshakeException) th;
        }
        return (SSLHandshakeException) new SSLHandshakeException(th.getMessage()).initCause(th);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0020, code lost:
        if (r4 <= 5) goto L_0x0022;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static int getEncryptedPacketLength(p043io.netty.buffer.ByteBuf r6, int r7) {
        /*
            short r0 = r6.getUnsignedByte(r7)
            r1 = 1
            r2 = 0
            switch(r0) {
                case 20: goto L_0x000b;
                case 21: goto L_0x000b;
                case 22: goto L_0x000b;
                case 23: goto L_0x000b;
                case 24: goto L_0x000b;
                default: goto L_0x0009;
            }
        L_0x0009:
            r0 = 0
            goto L_0x000c
        L_0x000b:
            r0 = 1
        L_0x000c:
            r3 = 3
            if (r0 == 0) goto L_0x0023
            int r4 = r7 + 1
            short r4 = r6.getUnsignedByte(r4)
            if (r4 != r3) goto L_0x0022
            int r4 = r7 + 3
            int r4 = unsignedShortBE(r6, r4)
            r5 = 5
            int r4 = r4 + r5
            r2 = r4
            if (r4 > r5) goto L_0x0023
        L_0x0022:
            r0 = 0
        L_0x0023:
            if (r0 != 0) goto L_0x0055
            short r0 = r6.getUnsignedByte(r7)
            r0 = r0 & 128(0x80, float:1.794E-43)
            r2 = 2
            if (r0 == 0) goto L_0x0030
            r0 = 2
            goto L_0x0031
        L_0x0030:
            r0 = 3
        L_0x0031:
            int r4 = r7 + r0
            int r4 = r4 + r1
            short r1 = r6.getUnsignedByte(r4)
            if (r1 == r2) goto L_0x003f
            if (r1 != r3) goto L_0x003d
            goto L_0x003f
        L_0x003d:
            r6 = -2
            return r6
        L_0x003f:
            if (r0 != r2) goto L_0x0049
            short r6 = shortBE(r6, r7)
            r6 = r6 & 32767(0x7fff, float:4.5916E-41)
            int r6 = r6 + r2
            goto L_0x0050
        L_0x0049:
            short r6 = shortBE(r6, r7)
            r6 = r6 & 16383(0x3fff, float:2.2957E-41)
            int r6 = r6 + r3
        L_0x0050:
            r2 = r6
            if (r2 > r0) goto L_0x0055
            r6 = -1
            return r6
        L_0x0055:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.SslUtils.getEncryptedPacketLength(io.netty.buffer.ByteBuf, int):int");
    }

    private static int unsignedShortBE(ByteBuf byteBuf, int i) {
        return byteBuf.order() == ByteOrder.BIG_ENDIAN ? byteBuf.getUnsignedShort(i) : byteBuf.getUnsignedShortLE(i);
    }

    private static short shortBE(ByteBuf byteBuf, int i) {
        return byteBuf.order() == ByteOrder.BIG_ENDIAN ? byteBuf.getShort(i) : byteBuf.getShortLE(i);
    }

    private static int unsignedShortBE(ByteBuffer byteBuffer, int i) {
        return shortBE(byteBuffer, i) & UShort.MAX_VALUE;
    }

    private static short shortBE(ByteBuffer byteBuffer, int i) {
        return byteBuffer.order() == ByteOrder.BIG_ENDIAN ? byteBuffer.getShort(i) : ByteBufUtil.swapShort(byteBuffer.getShort(i));
    }

    static int getEncryptedPacketLength(ByteBuffer[] byteBufferArr, int i) {
        ByteBuffer byteBuffer = byteBufferArr[i];
        if (byteBuffer.remaining() >= 5) {
            return getEncryptedPacketLength(byteBuffer);
        }
        ByteBuffer allocate = ByteBuffer.allocate(5);
        while (true) {
            int i2 = i + 1;
            ByteBuffer duplicate = byteBufferArr[i].duplicate();
            if (duplicate.remaining() > allocate.remaining()) {
                duplicate.limit(duplicate.position() + allocate.remaining());
            }
            allocate.put(duplicate);
            if (!allocate.hasRemaining()) {
                allocate.flip();
                return getEncryptedPacketLength(allocate);
            }
            i = i2;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x002c, code lost:
        if (r5 <= 5) goto L_0x002e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int getEncryptedPacketLength(java.nio.ByteBuffer r7) {
        /*
            int r0 = r7.position()
            byte r1 = r7.get(r0)
            short r1 = unsignedByte(r1)
            r2 = 1
            r3 = 0
            switch(r1) {
                case 20: goto L_0x0013;
                case 21: goto L_0x0013;
                case 22: goto L_0x0013;
                case 23: goto L_0x0013;
                case 24: goto L_0x0013;
                default: goto L_0x0011;
            }
        L_0x0011:
            r1 = 0
            goto L_0x0014
        L_0x0013:
            r1 = 1
        L_0x0014:
            r4 = 3
            if (r1 == 0) goto L_0x002f
            int r5 = r0 + 1
            byte r5 = r7.get(r5)
            short r5 = unsignedByte(r5)
            if (r5 != r4) goto L_0x002e
            int r5 = r0 + 3
            int r5 = unsignedShortBE(r7, r5)
            r6 = 5
            int r5 = r5 + r6
            r3 = r5
            if (r5 > r6) goto L_0x002f
        L_0x002e:
            r1 = 0
        L_0x002f:
            if (r1 != 0) goto L_0x0069
            byte r1 = r7.get(r0)
            short r1 = unsignedByte(r1)
            r1 = r1 & 128(0x80, float:1.794E-43)
            r3 = 2
            if (r1 == 0) goto L_0x0040
            r1 = 2
            goto L_0x0041
        L_0x0040:
            r1 = 3
        L_0x0041:
            int r5 = r0 + r1
            int r5 = r5 + r2
            byte r2 = r7.get(r5)
            short r2 = unsignedByte(r2)
            if (r2 == r3) goto L_0x0053
            if (r2 != r4) goto L_0x0051
            goto L_0x0053
        L_0x0051:
            r7 = -2
            return r7
        L_0x0053:
            if (r1 != r3) goto L_0x005d
            short r7 = shortBE(r7, r0)
            r7 = r7 & 32767(0x7fff, float:4.5916E-41)
            int r7 = r7 + r3
            goto L_0x0064
        L_0x005d:
            short r7 = shortBE(r7, r0)
            r7 = r7 & 16383(0x3fff, float:2.2957E-41)
            int r7 = r7 + r4
        L_0x0064:
            r3 = r7
            if (r3 > r1) goto L_0x0069
            r7 = -1
            return r7
        L_0x0069:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.SslUtils.getEncryptedPacketLength(java.nio.ByteBuffer):int");
    }

    static void notifyHandshakeFailure(ChannelHandlerContext channelHandlerContext, Throwable th) {
        channelHandlerContext.flush();
        channelHandlerContext.fireUserEventTriggered(new SslHandshakeCompletionEvent(th));
        channelHandlerContext.close();
    }

    static void zeroout(ByteBuf byteBuf) {
        if (!byteBuf.isReadOnly()) {
            byteBuf.setZero(0, byteBuf.capacity());
        }
    }

    static void zerooutAndRelease(ByteBuf byteBuf) {
        zeroout(byteBuf);
        byteBuf.release();
    }

    static ByteBuf toBase64(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf) {
        ByteBuf encode = Base64.encode(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes(), true, Base64Dialect.STANDARD, byteBufAllocator);
        byteBuf.readerIndex(byteBuf.writerIndex());
        return encode;
    }

    private SslUtils() {
    }
}

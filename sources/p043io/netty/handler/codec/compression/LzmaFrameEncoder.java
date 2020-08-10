package p043io.netty.handler.codec.compression;

import lzma.sdk.lzma.Encoder;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.handler.codec.MessageToByteEncoder;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.handler.codec.compression.LzmaFrameEncoder */
public class LzmaFrameEncoder extends MessageToByteEncoder<ByteBuf> {
    private static final int DEFAULT_LC = 3;
    private static final int DEFAULT_LP = 0;
    private static final int DEFAULT_MATCH_FINDER = 1;
    private static final int DEFAULT_PB = 2;
    private static final int MAX_FAST_BYTES = 273;
    private static final int MEDIUM_DICTIONARY_SIZE = 65536;
    private static final int MEDIUM_FAST_BYTES = 32;
    private static final int MIN_FAST_BYTES = 5;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(LzmaFrameEncoder.class);
    private static boolean warningLogged;
    private final Encoder encoder;
    private final int littleEndianDictionarySize;
    private final byte properties;

    public LzmaFrameEncoder() {
        this(65536);
    }

    public LzmaFrameEncoder(int i, int i2, int i3) {
        this(i, i2, i3, 65536);
    }

    public LzmaFrameEncoder(int i) {
        this(3, 0, 2, i);
    }

    public LzmaFrameEncoder(int i, int i2, int i3, int i4) {
        this(i, i2, i3, i4, false, 32);
    }

    public LzmaFrameEncoder(int i, int i2, int i3, int i4, boolean z, int i5) {
        if (i < 0 || i > 8) {
            StringBuilder sb = new StringBuilder();
            sb.append("lc: ");
            sb.append(i);
            sb.append(" (expected: 0-8)");
            throw new IllegalArgumentException(sb.toString());
        }
        String str = " (expected: 0-4)";
        if (i2 < 0 || i2 > 4) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("lp: ");
            sb2.append(i2);
            sb2.append(str);
            throw new IllegalArgumentException(sb2.toString());
        } else if (i3 < 0 || i3 > 4) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("pb: ");
            sb3.append(i3);
            sb3.append(str);
            throw new IllegalArgumentException(sb3.toString());
        } else {
            if (i + i2 > 4 && !warningLogged) {
                logger.warn("The latest versions of LZMA libraries (for example, XZ Utils) has an additional requirement: lc + lp <= 4. Data which don't follow this requirement cannot be decompressed with this libraries.");
                warningLogged = true;
            }
            if (i4 < 0) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append("dictionarySize: ");
                sb4.append(i4);
                sb4.append(" (expected: 0+)");
                throw new IllegalArgumentException(sb4.toString());
            } else if (i5 < 5 || i5 > MAX_FAST_BYTES) {
                throw new IllegalArgumentException(String.format("numFastBytes: %d (expected: %d-%d)", new Object[]{Integer.valueOf(i5), Integer.valueOf(5), Integer.valueOf(MAX_FAST_BYTES)}));
            } else {
                this.encoder = new Encoder();
                this.encoder.setDictionarySize(i4);
                this.encoder.setEndMarkerMode(z);
                this.encoder.setMatchFinder(1);
                this.encoder.setNumFastBytes(i5);
                this.encoder.setLcLpPb(i, i2, i3);
                this.properties = (byte) ((((i3 * 5) + i2) * 9) + i);
                this.littleEndianDictionarySize = Integer.reverseBytes(i4);
            }
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0043  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void encode(p043io.netty.channel.ChannelHandlerContext r11, p043io.netty.buffer.ByteBuf r12, p043io.netty.buffer.ByteBuf r13) throws java.lang.Exception {
        /*
            r10 = this;
            int r11 = r12.readableBytes()
            r0 = 0
            io.netty.buffer.ByteBufInputStream r9 = new io.netty.buffer.ByteBufInputStream     // Catch:{ all -> 0x0039 }
            r9.<init>(r12)     // Catch:{ all -> 0x0039 }
            io.netty.buffer.ByteBufOutputStream r12 = new io.netty.buffer.ByteBufOutputStream     // Catch:{ all -> 0x0036 }
            r12.<init>(r13)     // Catch:{ all -> 0x0036 }
            byte r13 = r10.properties     // Catch:{ all -> 0x0034 }
            r12.writeByte(r13)     // Catch:{ all -> 0x0034 }
            int r13 = r10.littleEndianDictionarySize     // Catch:{ all -> 0x0034 }
            r12.writeInt(r13)     // Catch:{ all -> 0x0034 }
            long r0 = (long) r11     // Catch:{ all -> 0x0034 }
            long r0 = java.lang.Long.reverseBytes(r0)     // Catch:{ all -> 0x0034 }
            r12.writeLong(r0)     // Catch:{ all -> 0x0034 }
            lzma.sdk.lzma.Encoder r1 = r10.encoder     // Catch:{ all -> 0x0034 }
            r4 = -1
            r6 = -1
            r8 = 0
            r2 = r9
            r3 = r12
            r1.code(r2, r3, r4, r6, r8)     // Catch:{ all -> 0x0034 }
            r9.close()
            r12.close()
            return
        L_0x0034:
            r11 = move-exception
            goto L_0x003c
        L_0x0036:
            r11 = move-exception
            r12 = r0
            goto L_0x003c
        L_0x0039:
            r11 = move-exception
            r12 = r0
            r9 = r12
        L_0x003c:
            if (r9 == 0) goto L_0x0041
            r9.close()
        L_0x0041:
            if (r12 == 0) goto L_0x0046
            r12.close()
        L_0x0046:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.compression.LzmaFrameEncoder.encode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, io.netty.buffer.ByteBuf):void");
    }

    /* access modifiers changed from: protected */
    public ByteBuf allocateBuffer(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, boolean z) throws Exception {
        return channelHandlerContext.alloc().ioBuffer(maxOutputBufferLength(byteBuf.readableBytes()));
    }

    private static int maxOutputBufferLength(int i) {
        double d = i < 200 ? 1.5d : i < 500 ? 1.2d : i < 1000 ? 1.1d : i < 10000 ? 1.05d : 1.02d;
        double d2 = (double) i;
        Double.isNaN(d2);
        return ((int) (d2 * d)) + 13;
    }
}

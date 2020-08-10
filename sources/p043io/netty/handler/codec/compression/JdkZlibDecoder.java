package p043io.netty.handler.codec.compression;

import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelHandlerContext;

/* renamed from: io.netty.handler.codec.compression.JdkZlibDecoder */
public class JdkZlibDecoder extends ZlibDecoder {
    private static final int FCOMMENT = 16;
    private static final int FEXTRA = 4;
    private static final int FHCRC = 2;
    private static final int FNAME = 8;
    private static final int FRESERVED = 224;
    private final ByteBufChecksum crc;
    private boolean decideZlibOrNone;
    private final byte[] dictionary;
    private volatile boolean finished;
    private int flags;
    private GzipState gzipState;
    private Inflater inflater;
    private int xlen;

    /* renamed from: io.netty.handler.codec.compression.JdkZlibDecoder$1 */
    static /* synthetic */ class C56871 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper = new int[ZlibWrapper.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(24:0|(2:1|2)|3|(2:5|6)|7|(2:9|10)|11|13|14|15|16|17|18|19|20|(2:21|22)|23|25|26|27|28|29|30|(3:31|32|34)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(28:0|1|2|3|(2:5|6)|7|(2:9|10)|11|13|14|15|16|17|18|19|20|21|22|23|25|26|27|28|29|30|31|32|34) */
        /* JADX WARNING: Can't wrap try/catch for region: R(30:0|1|2|3|5|6|7|9|10|11|13|14|15|16|17|18|19|20|21|22|23|25|26|27|28|29|30|31|32|34) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0035 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x004b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0056 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x0075 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x007f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x0089 */
        static {
            /*
                io.netty.handler.codec.compression.JdkZlibDecoder$GzipState[] r0 = p043io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f3724x5be85941 = r0
                r0 = 1
                int[] r1 = f3724x5be85941     // Catch:{ NoSuchFieldError -> 0x0014 }
                io.netty.handler.codec.compression.JdkZlibDecoder$GzipState r2 = p043io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.FOOTER_START     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                r1 = 2
                int[] r2 = f3724x5be85941     // Catch:{ NoSuchFieldError -> 0x001f }
                io.netty.handler.codec.compression.JdkZlibDecoder$GzipState r3 = p043io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.HEADER_START     // Catch:{ NoSuchFieldError -> 0x001f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                r2 = 3
                int[] r3 = f3724x5be85941     // Catch:{ NoSuchFieldError -> 0x002a }
                io.netty.handler.codec.compression.JdkZlibDecoder$GzipState r4 = p043io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.FLG_READ     // Catch:{ NoSuchFieldError -> 0x002a }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r3[r4] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                r3 = 4
                int[] r4 = f3724x5be85941     // Catch:{ NoSuchFieldError -> 0x0035 }
                io.netty.handler.codec.compression.JdkZlibDecoder$GzipState r5 = p043io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.XLEN_READ     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r5 = r5.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r4[r5] = r3     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r4 = f3724x5be85941     // Catch:{ NoSuchFieldError -> 0x0040 }
                io.netty.handler.codec.compression.JdkZlibDecoder$GzipState r5 = p043io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.SKIP_FNAME     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r5 = r5.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r6 = 5
                r4[r5] = r6     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                int[] r4 = f3724x5be85941     // Catch:{ NoSuchFieldError -> 0x004b }
                io.netty.handler.codec.compression.JdkZlibDecoder$GzipState r5 = p043io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.SKIP_COMMENT     // Catch:{ NoSuchFieldError -> 0x004b }
                int r5 = r5.ordinal()     // Catch:{ NoSuchFieldError -> 0x004b }
                r6 = 6
                r4[r5] = r6     // Catch:{ NoSuchFieldError -> 0x004b }
            L_0x004b:
                int[] r4 = f3724x5be85941     // Catch:{ NoSuchFieldError -> 0x0056 }
                io.netty.handler.codec.compression.JdkZlibDecoder$GzipState r5 = p043io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.PROCESS_FHCRC     // Catch:{ NoSuchFieldError -> 0x0056 }
                int r5 = r5.ordinal()     // Catch:{ NoSuchFieldError -> 0x0056 }
                r6 = 7
                r4[r5] = r6     // Catch:{ NoSuchFieldError -> 0x0056 }
            L_0x0056:
                int[] r4 = f3724x5be85941     // Catch:{ NoSuchFieldError -> 0x0062 }
                io.netty.handler.codec.compression.JdkZlibDecoder$GzipState r5 = p043io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.HEADER_END     // Catch:{ NoSuchFieldError -> 0x0062 }
                int r5 = r5.ordinal()     // Catch:{ NoSuchFieldError -> 0x0062 }
                r6 = 8
                r4[r5] = r6     // Catch:{ NoSuchFieldError -> 0x0062 }
            L_0x0062:
                io.netty.handler.codec.compression.ZlibWrapper[] r4 = p043io.netty.handler.codec.compression.ZlibWrapper.values()
                int r4 = r4.length
                int[] r4 = new int[r4]
                $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper = r4
                int[] r4 = $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper     // Catch:{ NoSuchFieldError -> 0x0075 }
                io.netty.handler.codec.compression.ZlibWrapper r5 = p043io.netty.handler.codec.compression.ZlibWrapper.GZIP     // Catch:{ NoSuchFieldError -> 0x0075 }
                int r5 = r5.ordinal()     // Catch:{ NoSuchFieldError -> 0x0075 }
                r4[r5] = r0     // Catch:{ NoSuchFieldError -> 0x0075 }
            L_0x0075:
                int[] r0 = $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper     // Catch:{ NoSuchFieldError -> 0x007f }
                io.netty.handler.codec.compression.ZlibWrapper r4 = p043io.netty.handler.codec.compression.ZlibWrapper.NONE     // Catch:{ NoSuchFieldError -> 0x007f }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x007f }
                r0[r4] = r1     // Catch:{ NoSuchFieldError -> 0x007f }
            L_0x007f:
                int[] r0 = $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper     // Catch:{ NoSuchFieldError -> 0x0089 }
                io.netty.handler.codec.compression.ZlibWrapper r1 = p043io.netty.handler.codec.compression.ZlibWrapper.ZLIB     // Catch:{ NoSuchFieldError -> 0x0089 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0089 }
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0089 }
            L_0x0089:
                int[] r0 = $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper     // Catch:{ NoSuchFieldError -> 0x0093 }
                io.netty.handler.codec.compression.ZlibWrapper r1 = p043io.netty.handler.codec.compression.ZlibWrapper.ZLIB_OR_NONE     // Catch:{ NoSuchFieldError -> 0x0093 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0093 }
                r0[r1] = r3     // Catch:{ NoSuchFieldError -> 0x0093 }
            L_0x0093:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.compression.JdkZlibDecoder.C56871.<clinit>():void");
        }
    }

    /* renamed from: io.netty.handler.codec.compression.JdkZlibDecoder$GzipState */
    private enum GzipState {
        HEADER_START,
        HEADER_END,
        FLG_READ,
        XLEN_READ,
        SKIP_FNAME,
        SKIP_COMMENT,
        PROCESS_FHCRC,
        FOOTER_START
    }

    public JdkZlibDecoder() {
        this(ZlibWrapper.ZLIB, null);
    }

    public JdkZlibDecoder(byte[] bArr) {
        this(ZlibWrapper.ZLIB, bArr);
    }

    public JdkZlibDecoder(ZlibWrapper zlibWrapper) {
        this(zlibWrapper, null);
    }

    private JdkZlibDecoder(ZlibWrapper zlibWrapper, byte[] bArr) {
        this.gzipState = GzipState.HEADER_START;
        this.flags = -1;
        this.xlen = -1;
        if (zlibWrapper != null) {
            int i = C56871.$SwitchMap$io$netty$handler$codec$compression$ZlibWrapper[zlibWrapper.ordinal()];
            if (i == 1) {
                this.inflater = new Inflater(true);
                this.crc = ByteBufChecksum.wrapChecksum(new CRC32());
            } else if (i == 2) {
                this.inflater = new Inflater(true);
                this.crc = null;
            } else if (i == 3) {
                this.inflater = new Inflater();
                this.crc = null;
            } else if (i == 4) {
                this.decideZlibOrNone = true;
                this.crc = null;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Only GZIP or ZLIB is supported, but you used ");
                sb.append(zlibWrapper);
                throw new IllegalArgumentException(sb.toString());
            }
            this.dictionary = bArr;
            return;
        }
        throw new NullPointerException("wrapper");
    }

    public boolean isClosed() {
        return this.finished;
    }

    /* access modifiers changed from: protected */
    public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (this.finished) {
            byteBuf.skipBytes(byteBuf.readableBytes());
            return;
        }
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes != 0) {
            boolean z = false;
            if (this.decideZlibOrNone) {
                if (readableBytes >= 2) {
                    this.inflater = new Inflater(!looksLikeZlib(byteBuf.getShort(byteBuf.readerIndex())));
                    this.decideZlibOrNone = false;
                } else {
                    return;
                }
            }
            if (this.crc != null) {
                if (C56871.f3724x5be85941[this.gzipState.ordinal()] == 1) {
                    if (readGZIPFooter(byteBuf)) {
                        this.finished = true;
                    }
                    return;
                } else if (this.gzipState == GzipState.HEADER_END || readGZIPHeader(byteBuf)) {
                    readableBytes = byteBuf.readableBytes();
                } else {
                    return;
                }
            }
            if (byteBuf.hasArray()) {
                this.inflater.setInput(byteBuf.array(), byteBuf.arrayOffset() + byteBuf.readerIndex(), readableBytes);
            } else {
                byte[] bArr = new byte[readableBytes];
                byteBuf.getBytes(byteBuf.readerIndex(), bArr);
                this.inflater.setInput(bArr);
            }
            ByteBuf heapBuffer = channelHandlerContext.alloc().heapBuffer(this.inflater.getRemaining() << 1);
            while (true) {
                try {
                    if (this.inflater.needsInput()) {
                        break;
                    }
                    byte[] array = heapBuffer.array();
                    int writerIndex = heapBuffer.writerIndex();
                    int arrayOffset = heapBuffer.arrayOffset() + writerIndex;
                    int inflate = this.inflater.inflate(array, arrayOffset, heapBuffer.writableBytes());
                    if (inflate > 0) {
                        heapBuffer.writerIndex(writerIndex + inflate);
                        if (this.crc != null) {
                            this.crc.update(array, arrayOffset, inflate);
                        }
                    } else if (this.inflater.needsDictionary()) {
                        if (this.dictionary != null) {
                            this.inflater.setDictionary(this.dictionary);
                        } else {
                            throw new DecompressionException("decompression failure, unable to set dictionary as non was specified");
                        }
                    }
                    if (!this.inflater.finished()) {
                        heapBuffer.ensureWritable(this.inflater.getRemaining() << 1);
                    } else if (this.crc == null) {
                        this.finished = true;
                    } else {
                        z = true;
                    }
                } catch (DataFormatException e) {
                    throw new DecompressionException("decompression failure", e);
                } catch (Throwable th) {
                    if (heapBuffer.isReadable()) {
                        list.add(heapBuffer);
                    } else {
                        heapBuffer.release();
                    }
                    throw th;
                }
            }
            byteBuf.skipBytes(readableBytes - this.inflater.getRemaining());
            if (z) {
                this.gzipState = GzipState.FOOTER_START;
                if (readGZIPFooter(byteBuf)) {
                    this.finished = true;
                }
            }
            if (heapBuffer.isReadable()) {
                list.add(heapBuffer);
            } else {
                heapBuffer.release();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void handlerRemoved0(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.handlerRemoved0(channelHandlerContext);
        Inflater inflater2 = this.inflater;
        if (inflater2 != null) {
            inflater2.end();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x007a, code lost:
        if ((r7.flags & 4) == 0) goto L_0x009c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0080, code lost:
        if (r8.readableBytes() >= 2) goto L_0x0083;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0082, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0083, code lost:
        r0 = r8.readUnsignedByte();
        r5 = r8.readUnsignedByte();
        r7.crc.update(r0);
        r7.crc.update(r5);
        r7.xlen = ((r0 << 8) | r5) | r7.xlen;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x009c, code lost:
        r7.gzipState = p043io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.XLEN_READ;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00a3, code lost:
        if (r7.xlen == -1) goto L_0x00be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00ab, code lost:
        if (r8.readableBytes() >= r7.xlen) goto L_0x00ae;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00ad, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00ae, code lost:
        r7.crc.update(r8, r8.readerIndex(), r7.xlen);
        r8.skipBytes(r7.xlen);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00be, code lost:
        r7.gzipState = p043io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.SKIP_FNAME;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00c5, code lost:
        if ((r7.flags & 8) == 0) goto L_0x00e0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00cb, code lost:
        if (r8.isReadable() != false) goto L_0x00ce;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00cd, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00ce, code lost:
        r0 = r8.readUnsignedByte();
        r7.crc.update(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00d7, code lost:
        if (r0 != 0) goto L_0x00da;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00de, code lost:
        if (r8.isReadable() != false) goto L_0x00ce;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00e0, code lost:
        r7.gzipState = p043io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.SKIP_COMMENT;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00e8, code lost:
        if ((r7.flags & 16) == 0) goto L_0x0103;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00ee, code lost:
        if (r8.isReadable() != false) goto L_0x00f1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00f0, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00f1, code lost:
        r0 = r8.readUnsignedByte();
        r7.crc.update(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00fa, code lost:
        if (r0 != 0) goto L_0x00fd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0101, code lost:
        if (r8.isReadable() != false) goto L_0x00f1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0103, code lost:
        r7.gzipState = p043io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.PROCESS_FHCRC;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x010a, code lost:
        if ((r7.flags & 2) == 0) goto L_0x0116;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0110, code lost:
        if (r8.readableBytes() >= 4) goto L_0x0113;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0112, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0113, code lost:
        verifyCrc(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0116, code lost:
        r7.crc.reset();
        r7.gzipState = p043io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.HEADER_END;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0120, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean readGZIPHeader(p043io.netty.buffer.ByteBuf r8) {
        /*
            r7 = this;
            int[] r0 = p043io.netty.handler.codec.compression.JdkZlibDecoder.C56871.f3724x5be85941
            io.netty.handler.codec.compression.JdkZlibDecoder$GzipState r1 = r7.gzipState
            int r1 = r1.ordinal()
            r0 = r0[r1]
            r1 = 2
            r2 = 8
            r3 = 4
            r4 = 0
            switch(r0) {
                case 2: goto L_0x0018;
                case 3: goto L_0x0077;
                case 4: goto L_0x00a0;
                case 5: goto L_0x00c2;
                case 6: goto L_0x00e4;
                case 7: goto L_0x0107;
                case 8: goto L_0x011f;
                default: goto L_0x0012;
            }
        L_0x0012:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            r8.<init>()
            throw r8
        L_0x0018:
            int r0 = r8.readableBytes()
            r5 = 10
            if (r0 >= r5) goto L_0x0021
            return r4
        L_0x0021:
            byte r0 = r8.readByte()
            byte r5 = r8.readByte()
            r6 = 31
            if (r0 != r6) goto L_0x0145
            io.netty.handler.codec.compression.ByteBufChecksum r6 = r7.crc
            r6.update(r0)
            io.netty.handler.codec.compression.ByteBufChecksum r0 = r7.crc
            r0.update(r5)
            short r0 = r8.readUnsignedByte()
            if (r0 != r2) goto L_0x0129
            io.netty.handler.codec.compression.ByteBufChecksum r5 = r7.crc
            r5.update(r0)
            short r0 = r8.readUnsignedByte()
            r7.flags = r0
            io.netty.handler.codec.compression.ByteBufChecksum r0 = r7.crc
            int r5 = r7.flags
            r0.update(r5)
            int r0 = r7.flags
            r0 = r0 & 224(0xe0, float:3.14E-43)
            if (r0 != 0) goto L_0x0121
            io.netty.handler.codec.compression.ByteBufChecksum r0 = r7.crc
            int r5 = r8.readerIndex()
            r0.update(r8, r5, r3)
            r8.skipBytes(r3)
            io.netty.handler.codec.compression.ByteBufChecksum r0 = r7.crc
            short r5 = r8.readUnsignedByte()
            r0.update(r5)
            io.netty.handler.codec.compression.ByteBufChecksum r0 = r7.crc
            short r5 = r8.readUnsignedByte()
            r0.update(r5)
            io.netty.handler.codec.compression.JdkZlibDecoder$GzipState r0 = p043io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.FLG_READ
            r7.gzipState = r0
        L_0x0077:
            int r0 = r7.flags
            r0 = r0 & r3
            if (r0 == 0) goto L_0x009c
            int r0 = r8.readableBytes()
            if (r0 >= r1) goto L_0x0083
            return r4
        L_0x0083:
            short r0 = r8.readUnsignedByte()
            short r5 = r8.readUnsignedByte()
            io.netty.handler.codec.compression.ByteBufChecksum r6 = r7.crc
            r6.update(r0)
            io.netty.handler.codec.compression.ByteBufChecksum r6 = r7.crc
            r6.update(r5)
            int r6 = r7.xlen
            int r0 = r0 << r2
            r0 = r0 | r5
            r0 = r0 | r6
            r7.xlen = r0
        L_0x009c:
            io.netty.handler.codec.compression.JdkZlibDecoder$GzipState r0 = p043io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.XLEN_READ
            r7.gzipState = r0
        L_0x00a0:
            int r0 = r7.xlen
            r5 = -1
            if (r0 == r5) goto L_0x00be
            int r0 = r8.readableBytes()
            int r5 = r7.xlen
            if (r0 >= r5) goto L_0x00ae
            return r4
        L_0x00ae:
            io.netty.handler.codec.compression.ByteBufChecksum r0 = r7.crc
            int r5 = r8.readerIndex()
            int r6 = r7.xlen
            r0.update(r8, r5, r6)
            int r0 = r7.xlen
            r8.skipBytes(r0)
        L_0x00be:
            io.netty.handler.codec.compression.JdkZlibDecoder$GzipState r0 = p043io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.SKIP_FNAME
            r7.gzipState = r0
        L_0x00c2:
            int r0 = r7.flags
            r0 = r0 & r2
            if (r0 == 0) goto L_0x00e0
            boolean r0 = r8.isReadable()
            if (r0 != 0) goto L_0x00ce
            return r4
        L_0x00ce:
            short r0 = r8.readUnsignedByte()
            io.netty.handler.codec.compression.ByteBufChecksum r2 = r7.crc
            r2.update(r0)
            if (r0 != 0) goto L_0x00da
            goto L_0x00e0
        L_0x00da:
            boolean r0 = r8.isReadable()
            if (r0 != 0) goto L_0x00ce
        L_0x00e0:
            io.netty.handler.codec.compression.JdkZlibDecoder$GzipState r0 = p043io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.SKIP_COMMENT
            r7.gzipState = r0
        L_0x00e4:
            int r0 = r7.flags
            r0 = r0 & 16
            if (r0 == 0) goto L_0x0103
            boolean r0 = r8.isReadable()
            if (r0 != 0) goto L_0x00f1
            return r4
        L_0x00f1:
            short r0 = r8.readUnsignedByte()
            io.netty.handler.codec.compression.ByteBufChecksum r2 = r7.crc
            r2.update(r0)
            if (r0 != 0) goto L_0x00fd
            goto L_0x0103
        L_0x00fd:
            boolean r0 = r8.isReadable()
            if (r0 != 0) goto L_0x00f1
        L_0x0103:
            io.netty.handler.codec.compression.JdkZlibDecoder$GzipState r0 = p043io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.PROCESS_FHCRC
            r7.gzipState = r0
        L_0x0107:
            int r0 = r7.flags
            r0 = r0 & r1
            if (r0 == 0) goto L_0x0116
            int r0 = r8.readableBytes()
            if (r0 >= r3) goto L_0x0113
            return r4
        L_0x0113:
            r7.verifyCrc(r8)
        L_0x0116:
            io.netty.handler.codec.compression.ByteBufChecksum r8 = r7.crc
            r8.reset()
            io.netty.handler.codec.compression.JdkZlibDecoder$GzipState r8 = p043io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.HEADER_END
            r7.gzipState = r8
        L_0x011f:
            r8 = 1
            return r8
        L_0x0121:
            io.netty.handler.codec.compression.DecompressionException r8 = new io.netty.handler.codec.compression.DecompressionException
            java.lang.String r0 = "Reserved flags are set in the GZIP header"
            r8.<init>(r0)
            throw r8
        L_0x0129:
            io.netty.handler.codec.compression.DecompressionException r8 = new io.netty.handler.codec.compression.DecompressionException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Unsupported compression method "
            r1.append(r2)
            r1.append(r0)
            java.lang.String r0 = " in the GZIP header"
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            r8.<init>(r0)
            throw r8
        L_0x0145:
            io.netty.handler.codec.compression.DecompressionException r8 = new io.netty.handler.codec.compression.DecompressionException
            java.lang.String r0 = "Input is not in the GZIP format"
            r8.<init>(r0)
            goto L_0x014e
        L_0x014d:
            throw r8
        L_0x014e:
            goto L_0x014d
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.compression.JdkZlibDecoder.readGZIPHeader(io.netty.buffer.ByteBuf):boolean");
    }

    private boolean readGZIPFooter(ByteBuf byteBuf) {
        if (byteBuf.readableBytes() < 8) {
            return false;
        }
        verifyCrc(byteBuf);
        int i = 0;
        for (int i2 = 0; i2 < 4; i2++) {
            i |= byteBuf.readUnsignedByte() << (i2 * 8);
        }
        int totalOut = this.inflater.getTotalOut();
        if (i == totalOut) {
            return true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Number of bytes mismatch. Expected: ");
        sb.append(i);
        sb.append(", Got: ");
        sb.append(totalOut);
        throw new DecompressionException(sb.toString());
    }

    private void verifyCrc(ByteBuf byteBuf) {
        long j = 0;
        for (int i = 0; i < 4; i++) {
            j |= ((long) byteBuf.readUnsignedByte()) << (i * 8);
        }
        long value = this.crc.getValue();
        if (j != value) {
            StringBuilder sb = new StringBuilder();
            sb.append("CRC value mismatch. Expected: ");
            sb.append(j);
            sb.append(", Got: ");
            sb.append(value);
            throw new DecompressionException(sb.toString());
        }
    }

    private static boolean looksLikeZlib(short s) {
        return (s & 30720) == 30720 && s % 31 == 0;
    }
}

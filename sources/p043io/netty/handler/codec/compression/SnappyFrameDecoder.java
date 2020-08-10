package p043io.netty.handler.codec.compression;

import java.util.List;
import org.objenesis.instantiator.basic.ClassDefinitionUtils;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.handler.codec.ByteToMessageDecoder;

/* renamed from: io.netty.handler.codec.compression.SnappyFrameDecoder */
public class SnappyFrameDecoder extends ByteToMessageDecoder {
    private static final int MAX_UNCOMPRESSED_DATA_SIZE = 65540;
    private static final int SNAPPY_IDENTIFIER_LEN = 6;
    private boolean corrupted;
    private final Snappy snappy;
    private boolean started;
    private final boolean validateChecksums;

    /* renamed from: io.netty.handler.codec.compression.SnappyFrameDecoder$1 */
    static /* synthetic */ class C56981 {

        /* renamed from: $SwitchMap$io$netty$handler$codec$compression$SnappyFrameDecoder$ChunkType */
        static final /* synthetic */ int[] f3726xce0a49f3 = new int[ChunkType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                io.netty.handler.codec.compression.SnappyFrameDecoder$ChunkType[] r0 = p043io.netty.handler.codec.compression.SnappyFrameDecoder.ChunkType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f3726xce0a49f3 = r0
                int[] r0 = f3726xce0a49f3     // Catch:{ NoSuchFieldError -> 0x0014 }
                io.netty.handler.codec.compression.SnappyFrameDecoder$ChunkType r1 = p043io.netty.handler.codec.compression.SnappyFrameDecoder.ChunkType.STREAM_IDENTIFIER     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f3726xce0a49f3     // Catch:{ NoSuchFieldError -> 0x001f }
                io.netty.handler.codec.compression.SnappyFrameDecoder$ChunkType r1 = p043io.netty.handler.codec.compression.SnappyFrameDecoder.ChunkType.RESERVED_SKIPPABLE     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f3726xce0a49f3     // Catch:{ NoSuchFieldError -> 0x002a }
                io.netty.handler.codec.compression.SnappyFrameDecoder$ChunkType r1 = p043io.netty.handler.codec.compression.SnappyFrameDecoder.ChunkType.RESERVED_UNSKIPPABLE     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = f3726xce0a49f3     // Catch:{ NoSuchFieldError -> 0x0035 }
                io.netty.handler.codec.compression.SnappyFrameDecoder$ChunkType r1 = p043io.netty.handler.codec.compression.SnappyFrameDecoder.ChunkType.UNCOMPRESSED_DATA     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = f3726xce0a49f3     // Catch:{ NoSuchFieldError -> 0x0040 }
                io.netty.handler.codec.compression.SnappyFrameDecoder$ChunkType r1 = p043io.netty.handler.codec.compression.SnappyFrameDecoder.ChunkType.COMPRESSED_DATA     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.compression.SnappyFrameDecoder.C56981.<clinit>():void");
        }
    }

    /* renamed from: io.netty.handler.codec.compression.SnappyFrameDecoder$ChunkType */
    private enum ChunkType {
        STREAM_IDENTIFIER,
        COMPRESSED_DATA,
        UNCOMPRESSED_DATA,
        RESERVED_UNSKIPPABLE,
        RESERVED_SKIPPABLE
    }

    public SnappyFrameDecoder() {
        this(false);
    }

    public SnappyFrameDecoder(boolean z) {
        this.snappy = new Snappy();
        this.validateChecksums = z;
    }

    /* access modifiers changed from: protected */
    public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int writerIndex;
        if (this.corrupted) {
            byteBuf.skipBytes(byteBuf.readableBytes());
            return;
        }
        try {
            int readerIndex = byteBuf.readerIndex();
            int readableBytes = byteBuf.readableBytes();
            if (readableBytes >= 4) {
                short unsignedByte = byteBuf.getUnsignedByte(readerIndex);
                ChunkType mapChunkType = mapChunkType((byte) unsignedByte);
                int unsignedMediumLE = byteBuf.getUnsignedMediumLE(readerIndex + 1);
                int i = C56981.f3726xce0a49f3[mapChunkType.ordinal()];
                if (i != 1) {
                    if (i != 2) {
                        if (i == 3) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Found reserved unskippable chunk type: 0x");
                            sb.append(Integer.toHexString(unsignedByte));
                            throw new DecompressionException(sb.toString());
                        } else if (i != 4) {
                            if (i == 5) {
                                if (!this.started) {
                                    throw new DecompressionException("Received COMPRESSED_DATA tag before STREAM_IDENTIFIER");
                                } else if (readableBytes >= unsignedMediumLE + 4) {
                                    byteBuf.skipBytes(4);
                                    int readIntLE = byteBuf.readIntLE();
                                    ByteBuf buffer = channelHandlerContext.alloc().buffer();
                                    try {
                                        if (this.validateChecksums) {
                                            writerIndex = byteBuf.writerIndex();
                                            byteBuf.writerIndex((byteBuf.readerIndex() + unsignedMediumLE) - 4);
                                            this.snappy.decode(byteBuf, buffer);
                                            byteBuf.writerIndex(writerIndex);
                                            Snappy.validateChecksum(readIntLE, buffer, 0, buffer.writerIndex());
                                        } else {
                                            this.snappy.decode(byteBuf.readSlice(unsignedMediumLE - 4), buffer);
                                        }
                                        list.add(buffer);
                                        this.snappy.reset();
                                    } catch (Throwable th) {
                                        if (buffer != null) {
                                            buffer.release();
                                        }
                                        throw th;
                                    }
                                }
                            }
                        } else if (!this.started) {
                            throw new DecompressionException("Received UNCOMPRESSED_DATA tag before STREAM_IDENTIFIER");
                        } else if (unsignedMediumLE > 65540) {
                            throw new DecompressionException("Received UNCOMPRESSED_DATA larger than 65540 bytes");
                        } else if (readableBytes >= unsignedMediumLE + 4) {
                            byteBuf.skipBytes(4);
                            if (this.validateChecksums) {
                                Snappy.validateChecksum(byteBuf.readIntLE(), byteBuf, byteBuf.readerIndex(), unsignedMediumLE - 4);
                            } else {
                                byteBuf.skipBytes(4);
                            }
                            list.add(byteBuf.readRetainedSlice(unsignedMediumLE - 4));
                        }
                    } else if (this.started) {
                        int i2 = unsignedMediumLE + 4;
                        if (readableBytes >= i2) {
                            byteBuf.skipBytes(i2);
                        }
                    } else {
                        throw new DecompressionException("Received RESERVED_SKIPPABLE tag before STREAM_IDENTIFIER");
                    }
                } else if (unsignedMediumLE != 6) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Unexpected length of stream identifier: ");
                    sb2.append(unsignedMediumLE);
                    throw new DecompressionException(sb2.toString());
                } else if (readableBytes >= 10) {
                    byteBuf.skipBytes(4);
                    int readerIndex2 = byteBuf.readerIndex();
                    byteBuf.skipBytes(6);
                    int i3 = readerIndex2 + 1;
                    checkByte(byteBuf.getByte(readerIndex2), 115);
                    int i4 = i3 + 1;
                    checkByte(byteBuf.getByte(i3), 78);
                    int i5 = i4 + 1;
                    checkByte(byteBuf.getByte(i4), 97);
                    int i6 = i5 + 1;
                    checkByte(byteBuf.getByte(i5), 80);
                    int i7 = i6 + 1;
                    checkByte(byteBuf.getByte(i6), 112);
                    checkByte(byteBuf.getByte(i7), ClassDefinitionUtils.OPS_dup);
                    this.started = true;
                }
            }
        } catch (Exception e) {
            this.corrupted = true;
            throw e;
        }
    }

    private static void checkByte(byte b, byte b2) {
        if (b != b2) {
            throw new DecompressionException("Unexpected stream identifier contents. Mismatched snappy protocol version?");
        }
    }

    private static ChunkType mapChunkType(byte b) {
        if (b == 0) {
            return ChunkType.COMPRESSED_DATA;
        }
        if (b == 1) {
            return ChunkType.UNCOMPRESSED_DATA;
        }
        if (b == -1) {
            return ChunkType.STREAM_IDENTIFIER;
        }
        if ((b & 128) == 128) {
            return ChunkType.RESERVED_SKIPPABLE;
        }
        return ChunkType.RESERVED_UNSKIPPABLE;
    }
}

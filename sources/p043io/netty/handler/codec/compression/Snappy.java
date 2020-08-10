package p043io.netty.handler.codec.compression;

import com.google.common.base.Ascii;
import p043io.netty.buffer.ByteBuf;

/* renamed from: io.netty.handler.codec.compression.Snappy */
public final class Snappy {
    private static final int COPY_1_BYTE_OFFSET = 1;
    private static final int COPY_2_BYTE_OFFSET = 2;
    private static final int COPY_4_BYTE_OFFSET = 3;
    private static final int LITERAL = 0;
    private static final int MAX_HT_SIZE = 16384;
    private static final int MIN_COMPRESSIBLE_BYTES = 15;
    private static final int NOT_ENOUGH_INPUT = -1;
    private static final int PREAMBLE_NOT_FULL = -1;
    private State state = State.READY;
    private byte tag;
    private int written;

    /* renamed from: io.netty.handler.codec.compression.Snappy$1 */
    static /* synthetic */ class C56971 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$codec$compression$Snappy$State = new int[State.values().length];

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
                io.netty.handler.codec.compression.Snappy$State[] r0 = p043io.netty.handler.codec.compression.Snappy.State.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$io$netty$handler$codec$compression$Snappy$State = r0
                int[] r0 = $SwitchMap$io$netty$handler$codec$compression$Snappy$State     // Catch:{ NoSuchFieldError -> 0x0014 }
                io.netty.handler.codec.compression.Snappy$State r1 = p043io.netty.handler.codec.compression.Snappy.State.READY     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$io$netty$handler$codec$compression$Snappy$State     // Catch:{ NoSuchFieldError -> 0x001f }
                io.netty.handler.codec.compression.Snappy$State r1 = p043io.netty.handler.codec.compression.Snappy.State.READING_PREAMBLE     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$io$netty$handler$codec$compression$Snappy$State     // Catch:{ NoSuchFieldError -> 0x002a }
                io.netty.handler.codec.compression.Snappy$State r1 = p043io.netty.handler.codec.compression.Snappy.State.READING_TAG     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$io$netty$handler$codec$compression$Snappy$State     // Catch:{ NoSuchFieldError -> 0x0035 }
                io.netty.handler.codec.compression.Snappy$State r1 = p043io.netty.handler.codec.compression.Snappy.State.READING_LITERAL     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = $SwitchMap$io$netty$handler$codec$compression$Snappy$State     // Catch:{ NoSuchFieldError -> 0x0040 }
                io.netty.handler.codec.compression.Snappy$State r1 = p043io.netty.handler.codec.compression.Snappy.State.READING_COPY     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.compression.Snappy.C56971.<clinit>():void");
        }
    }

    /* renamed from: io.netty.handler.codec.compression.Snappy$State */
    private enum State {
        READY,
        READING_PREAMBLE,
        READING_TAG,
        READING_LITERAL,
        READING_COPY
    }

    private static short[] getHashTable(int i) {
        int i2 = 256;
        while (i2 < 16384 && i2 < i) {
            i2 <<= 1;
        }
        return i2 <= 256 ? new short[256] : new short[16384];
    }

    static int maskChecksum(int i) {
        return ((i << 17) | (i >> 15)) - 1568478504;
    }

    public void reset() {
        this.state = State.READY;
        this.tag = 0;
        this.written = 0;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x006a, code lost:
        encodeLiteral(r0, r1, r7 - r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x006f, code lost:
        r8 = findMatchingLength(r0, r14 + 4, r7 + 4, r2) + 4;
        r9 = r7 + r8;
        encodeCopy(r1, r7 - r14, r8);
        r0.readerIndex(r17.readerIndex() + r8);
        r7 = r9 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0089, code lost:
        if (r9 < r12) goto L_0x008c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x008c, code lost:
        r10 = r9 - r3;
        r4[hash(r0, r7, r5)] = (short) (r10 - 1);
        r8 = r7 + 1;
        r11 = hash(r0, r8, r5);
        r14 = r3 + r4[r11];
        r4[r11] = (short) r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x00ac, code lost:
        if (r0.getInt(r8) == r0.getInt(r14)) goto L_0x00b7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x00b7, code lost:
        r7 = r9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void encode(p043io.netty.buffer.ByteBuf r17, p043io.netty.buffer.ByteBuf r18, int r19) {
        /*
            r16 = this;
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = 0
        L_0x0007:
            int r4 = r3 * 7
            int r4 = r2 >>> r4
            r5 = r4 & -128(0xffffffffffffff80, float:NaN)
            if (r5 == 0) goto L_0x0019
            r4 = r4 & 127(0x7f, float:1.78E-43)
            r4 = r4 | 128(0x80, float:1.794E-43)
            r1.writeByte(r4)
            int r3 = r3 + 1
            goto L_0x0007
        L_0x0019:
            r1.writeByte(r4)
            int r3 = r17.readerIndex()
            short[] r4 = getHashTable(r19)
            int r5 = r4.length
            double r5 = (double) r5
            double r5 = java.lang.Math.log(r5)
            r7 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r7 = java.lang.Math.log(r7)
            double r5 = r5 / r7
            double r5 = java.lang.Math.floor(r5)
            int r5 = (int) r5
            r6 = 32
            int r5 = 32 - r5
            int r7 = r2 - r3
            r8 = 15
            if (r7 < r8) goto L_0x00bd
            int r7 = r3 + 1
            int r8 = hash(r0, r7, r5)
            r9 = r3
        L_0x0047:
            r10 = 32
        L_0x0049:
            int r11 = r10 + 1
            int r10 = r10 >> 5
            int r10 = r10 + r7
            int r12 = r2 + -4
            if (r10 <= r12) goto L_0x0054
            goto L_0x00be
        L_0x0054:
            int r13 = hash(r0, r10, r5)
            short r14 = r4[r8]
            int r14 = r14 + r3
            int r15 = r7 - r3
            short r15 = (short) r15
            r4[r8] = r15
            int r8 = r0.getInt(r7)
            int r15 = r0.getInt(r14)
            if (r8 != r15) goto L_0x00b9
            int r8 = r7 - r9
            encodeLiteral(r0, r1, r8)
        L_0x006f:
            int r8 = r14 + 4
            int r9 = r7 + 4
            int r8 = findMatchingLength(r0, r8, r9, r2)
            int r8 = r8 + 4
            int r9 = r7 + r8
            int r7 = r7 - r14
            encodeCopy(r1, r7, r8)
            int r7 = r17.readerIndex()
            int r7 = r7 + r8
            r0.readerIndex(r7)
            int r7 = r9 + -1
            if (r9 < r12) goto L_0x008c
            goto L_0x00be
        L_0x008c:
            int r8 = hash(r0, r7, r5)
            int r10 = r9 - r3
            int r11 = r10 + -1
            short r11 = (short) r11
            r4[r8] = r11
            int r8 = r7 + 1
            int r11 = hash(r0, r8, r5)
            short r13 = r4[r11]
            int r14 = r3 + r13
            short r10 = (short) r10
            r4[r11] = r10
            int r8 = r0.getInt(r8)
            int r10 = r0.getInt(r14)
            if (r8 == r10) goto L_0x00b7
            int r7 = r7 + 2
            int r8 = hash(r0, r7, r5)
            int r7 = r9 + 1
            goto L_0x0047
        L_0x00b7:
            r7 = r9
            goto L_0x006f
        L_0x00b9:
            r7 = r10
            r10 = r11
            r8 = r13
            goto L_0x0049
        L_0x00bd:
            r9 = r3
        L_0x00be:
            if (r9 >= r2) goto L_0x00c4
            int r2 = r2 - r9
            encodeLiteral(r0, r1, r2)
        L_0x00c4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.compression.Snappy.encode(io.netty.buffer.ByteBuf, io.netty.buffer.ByteBuf, int):void");
    }

    private static int hash(ByteBuf byteBuf, int i, int i2) {
        return (byteBuf.getInt(i) + 506832829) >>> i2;
    }

    private static int findMatchingLength(ByteBuf byteBuf, int i, int i2, int i3) {
        int i4 = 0;
        while (i2 <= i3 - 4 && byteBuf.getInt(i2) == byteBuf.getInt(i + i4)) {
            i2 += 4;
            i4 += 4;
        }
        while (i2 < i3 && byteBuf.getByte(i + i4) == byteBuf.getByte(i2)) {
            i2++;
            i4++;
        }
        return i4;
    }

    private static int bitsToEncode(int i) {
        int highestOneBit = Integer.highestOneBit(i);
        int i2 = 0;
        while (true) {
            highestOneBit >>= 1;
            if (highestOneBit == 0) {
                return i2;
            }
            i2++;
        }
    }

    static void encodeLiteral(ByteBuf byteBuf, ByteBuf byteBuf2, int i) {
        if (i < 61) {
            byteBuf2.writeByte((i - 1) << 2);
        } else {
            int i2 = i - 1;
            int bitsToEncode = (bitsToEncode(i2) / 8) + 1;
            byteBuf2.writeByte((bitsToEncode + 59) << 2);
            for (int i3 = 0; i3 < bitsToEncode; i3++) {
                byteBuf2.writeByte((i2 >> (i3 * 8)) & 255);
            }
        }
        byteBuf2.writeBytes(byteBuf, i);
    }

    private static void encodeCopyWithOffset(ByteBuf byteBuf, int i, int i2) {
        if (i2 >= 12 || i >= 2048) {
            byteBuf.writeByte(((i2 - 1) << 2) | 2);
            byteBuf.writeByte(i & 255);
            byteBuf.writeByte((i >> 8) & 255);
            return;
        }
        byteBuf.writeByte(((i2 - 4) << 2) | 1 | ((i >> 8) << 5));
        byteBuf.writeByte(i & 255);
    }

    private static void encodeCopy(ByteBuf byteBuf, int i, int i2) {
        while (i2 >= 68) {
            encodeCopyWithOffset(byteBuf, i, 64);
            i2 -= 64;
        }
        if (i2 > 64) {
            encodeCopyWithOffset(byteBuf, i, 60);
            i2 -= 60;
        }
        encodeCopyWithOffset(byteBuf, i, i2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:41:0x0098  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0097 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void decode(p043io.netty.buffer.ByteBuf r7, p043io.netty.buffer.ByteBuf r8) {
        /*
            r6 = this;
        L_0x0000:
            boolean r0 = r7.isReadable()
            if (r0 == 0) goto L_0x00b7
            int[] r0 = p043io.netty.handler.codec.compression.Snappy.C56971.$SwitchMap$io$netty$handler$codec$compression$Snappy$State
            io.netty.handler.codec.compression.Snappy$State r1 = r6.state
            int r1 = r1.ordinal()
            r0 = r0[r1]
            r1 = 2
            r2 = 1
            r3 = -1
            r4 = 3
            if (r0 == r2) goto L_0x0078
            if (r0 == r1) goto L_0x007c
            if (r0 == r4) goto L_0x0091
            r5 = 4
            if (r0 == r5) goto L_0x0065
            r5 = 5
            if (r0 == r5) goto L_0x0021
            goto L_0x0000
        L_0x0021:
            byte r0 = r6.tag
            r5 = r0 & 3
            if (r5 == r2) goto L_0x0052
            if (r5 == r1) goto L_0x003f
            if (r5 == r4) goto L_0x002c
            goto L_0x0000
        L_0x002c:
            int r1 = r6.written
            int r0 = decodeCopyWith4ByteOffset(r0, r7, r8, r1)
            if (r0 == r3) goto L_0x003e
            io.netty.handler.codec.compression.Snappy$State r1 = p043io.netty.handler.codec.compression.Snappy.State.READING_TAG
            r6.state = r1
            int r1 = r6.written
            int r1 = r1 + r0
            r6.written = r1
            goto L_0x0000
        L_0x003e:
            return
        L_0x003f:
            int r1 = r6.written
            int r0 = decodeCopyWith2ByteOffset(r0, r7, r8, r1)
            if (r0 == r3) goto L_0x0051
            io.netty.handler.codec.compression.Snappy$State r1 = p043io.netty.handler.codec.compression.Snappy.State.READING_TAG
            r6.state = r1
            int r1 = r6.written
            int r1 = r1 + r0
            r6.written = r1
            goto L_0x0000
        L_0x0051:
            return
        L_0x0052:
            int r1 = r6.written
            int r0 = decodeCopyWith1ByteOffset(r0, r7, r8, r1)
            if (r0 == r3) goto L_0x0064
            io.netty.handler.codec.compression.Snappy$State r1 = p043io.netty.handler.codec.compression.Snappy.State.READING_TAG
            r6.state = r1
            int r1 = r6.written
            int r1 = r1 + r0
            r6.written = r1
            goto L_0x0000
        L_0x0064:
            return
        L_0x0065:
            byte r0 = r6.tag
            int r0 = decodeLiteral(r0, r7, r8)
            if (r0 == r3) goto L_0x0077
            io.netty.handler.codec.compression.Snappy$State r1 = p043io.netty.handler.codec.compression.Snappy.State.READING_TAG
            r6.state = r1
            int r1 = r6.written
            int r1 = r1 + r0
            r6.written = r1
            goto L_0x0000
        L_0x0077:
            return
        L_0x0078:
            io.netty.handler.codec.compression.Snappy$State r0 = p043io.netty.handler.codec.compression.Snappy.State.READING_PREAMBLE
            r6.state = r0
        L_0x007c:
            int r0 = readPreamble(r7)
            if (r0 != r3) goto L_0x0083
            return
        L_0x0083:
            if (r0 != 0) goto L_0x008a
            io.netty.handler.codec.compression.Snappy$State r7 = p043io.netty.handler.codec.compression.Snappy.State.READY
            r6.state = r7
            return
        L_0x008a:
            r8.ensureWritable(r0)
            io.netty.handler.codec.compression.Snappy$State r0 = p043io.netty.handler.codec.compression.Snappy.State.READING_TAG
            r6.state = r0
        L_0x0091:
            boolean r0 = r7.isReadable()
            if (r0 != 0) goto L_0x0098
            return
        L_0x0098:
            byte r0 = r7.readByte()
            r6.tag = r0
            byte r0 = r6.tag
            r0 = r0 & r4
            if (r0 == 0) goto L_0x00b1
            if (r0 == r2) goto L_0x00ab
            if (r0 == r1) goto L_0x00ab
            if (r0 == r4) goto L_0x00ab
            goto L_0x0000
        L_0x00ab:
            io.netty.handler.codec.compression.Snappy$State r0 = p043io.netty.handler.codec.compression.Snappy.State.READING_COPY
            r6.state = r0
            goto L_0x0000
        L_0x00b1:
            io.netty.handler.codec.compression.Snappy$State r0 = p043io.netty.handler.codec.compression.Snappy.State.READING_LITERAL
            r6.state = r0
            goto L_0x0000
        L_0x00b7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.compression.Snappy.decode(io.netty.buffer.ByteBuf, io.netty.buffer.ByteBuf):void");
    }

    private static int readPreamble(ByteBuf byteBuf) {
        int i = 0;
        int i2 = 0;
        while (byteBuf.isReadable()) {
            short readUnsignedByte = byteBuf.readUnsignedByte();
            int i3 = i2 + 1;
            i |= (readUnsignedByte & 127) << (i2 * 7);
            if ((readUnsignedByte & 128) == 0) {
                return i;
            }
            if (i3 < 4) {
                i2 = i3;
            } else {
                throw new DecompressionException("Preamble is greater than 4 bytes");
            }
        }
        return 0;
    }

    static int decodeLiteral(byte b, ByteBuf byteBuf, ByteBuf byteBuf2) {
        byteBuf.markReaderIndex();
        int i = (b >> 2) & 63;
        switch (i) {
            case 60:
                if (byteBuf.isReadable()) {
                    i = byteBuf.readUnsignedByte();
                    break;
                } else {
                    return -1;
                }
            case 61:
                if (byteBuf.readableBytes() >= 2) {
                    i = byteBuf.readShortLE();
                    break;
                } else {
                    return -1;
                }
            case 62:
                if (byteBuf.readableBytes() >= 3) {
                    i = byteBuf.readUnsignedMediumLE();
                    break;
                } else {
                    return -1;
                }
            case 63:
                if (byteBuf.readableBytes() >= 4) {
                    i = byteBuf.readIntLE();
                    break;
                } else {
                    return -1;
                }
        }
        int i2 = i + 1;
        if (byteBuf.readableBytes() < i2) {
            byteBuf.resetReaderIndex();
            return -1;
        }
        byteBuf2.writeBytes(byteBuf, i2);
        return i2;
    }

    private static int decodeCopyWith1ByteOffset(byte b, ByteBuf byteBuf, ByteBuf byteBuf2, int i) {
        if (!byteBuf.isReadable()) {
            return -1;
        }
        int writerIndex = byteBuf2.writerIndex();
        int i2 = ((b & Ascii.f1869FS) >> 2) + 4;
        short readUnsignedByte = (((b & 224) << 8) >> 5) | byteBuf.readUnsignedByte();
        validateOffset(readUnsignedByte, i);
        byteBuf2.markReaderIndex();
        if (readUnsignedByte < i2) {
            for (int i3 = i2 / readUnsignedByte; i3 > 0; i3--) {
                byteBuf2.readerIndex(writerIndex - readUnsignedByte);
                byteBuf2.readBytes(byteBuf2, (int) readUnsignedByte);
            }
            int i4 = i2 % readUnsignedByte;
            if (i4 != 0) {
                byteBuf2.readerIndex(writerIndex - readUnsignedByte);
                byteBuf2.readBytes(byteBuf2, i4);
            }
        } else {
            byteBuf2.readerIndex(writerIndex - readUnsignedByte);
            byteBuf2.readBytes(byteBuf2, i2);
        }
        byteBuf2.resetReaderIndex();
        return i2;
    }

    private static int decodeCopyWith2ByteOffset(byte b, ByteBuf byteBuf, ByteBuf byteBuf2, int i) {
        if (byteBuf.readableBytes() < 2) {
            return -1;
        }
        int writerIndex = byteBuf2.writerIndex();
        int i2 = ((b >> 2) & 63) + 1;
        short readShortLE = byteBuf.readShortLE();
        validateOffset(readShortLE, i);
        byteBuf2.markReaderIndex();
        if (readShortLE < i2) {
            for (int i3 = i2 / readShortLE; i3 > 0; i3--) {
                byteBuf2.readerIndex(writerIndex - readShortLE);
                byteBuf2.readBytes(byteBuf2, (int) readShortLE);
            }
            int i4 = i2 % readShortLE;
            if (i4 != 0) {
                byteBuf2.readerIndex(writerIndex - readShortLE);
                byteBuf2.readBytes(byteBuf2, i4);
            }
        } else {
            byteBuf2.readerIndex(writerIndex - readShortLE);
            byteBuf2.readBytes(byteBuf2, i2);
        }
        byteBuf2.resetReaderIndex();
        return i2;
    }

    private static int decodeCopyWith4ByteOffset(byte b, ByteBuf byteBuf, ByteBuf byteBuf2, int i) {
        if (byteBuf.readableBytes() < 4) {
            return -1;
        }
        int writerIndex = byteBuf2.writerIndex();
        int i2 = ((b >> 2) & 63) + 1;
        int readIntLE = byteBuf.readIntLE();
        validateOffset(readIntLE, i);
        byteBuf2.markReaderIndex();
        if (readIntLE < i2) {
            for (int i3 = i2 / readIntLE; i3 > 0; i3--) {
                byteBuf2.readerIndex(writerIndex - readIntLE);
                byteBuf2.readBytes(byteBuf2, readIntLE);
            }
            int i4 = i2 % readIntLE;
            if (i4 != 0) {
                byteBuf2.readerIndex(writerIndex - readIntLE);
                byteBuf2.readBytes(byteBuf2, i4);
            }
        } else {
            byteBuf2.readerIndex(writerIndex - readIntLE);
            byteBuf2.readBytes(byteBuf2, i2);
        }
        byteBuf2.resetReaderIndex();
        return i2;
    }

    private static void validateOffset(int i, int i2) {
        if (i > 32767) {
            throw new DecompressionException("Offset exceeds maximum permissible value");
        } else if (i <= 0) {
            throw new DecompressionException("Offset is less than minimum permissible value");
        } else if (i > i2) {
            throw new DecompressionException("Offset exceeds size of chunk");
        }
    }

    static int calculateChecksum(ByteBuf byteBuf) {
        return calculateChecksum(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes());
    }

    static int calculateChecksum(ByteBuf byteBuf, int i, int i2) {
        Crc32c crc32c = new Crc32c();
        try {
            crc32c.update(byteBuf, i, i2);
            return maskChecksum((int) crc32c.getValue());
        } finally {
            crc32c.reset();
        }
    }

    static void validateChecksum(int i, ByteBuf byteBuf) {
        validateChecksum(i, byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes());
    }

    static void validateChecksum(int i, ByteBuf byteBuf, int i2, int i3) {
        int calculateChecksum = calculateChecksum(byteBuf, i2, i3);
        if (calculateChecksum != i) {
            StringBuilder sb = new StringBuilder();
            sb.append("mismatching checksum: ");
            sb.append(Integer.toHexString(calculateChecksum));
            sb.append(" (expected: ");
            sb.append(Integer.toHexString(i));
            sb.append(')');
            throw new DecompressionException(sb.toString());
        }
    }
}

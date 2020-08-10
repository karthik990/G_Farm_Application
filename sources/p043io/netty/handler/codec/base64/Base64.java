package p043io.netty.handler.codec.base64;

import com.google.common.base.Ascii;
import java.nio.ByteOrder;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.util.ByteProcessor;
import p043io.netty.util.internal.PlatformDependent;

/* renamed from: io.netty.handler.codec.base64.Base64 */
public final class Base64 {
    private static final byte EQUALS_SIGN = 61;
    private static final byte EQUALS_SIGN_ENC = -1;
    private static final int MAX_LINE_LENGTH = 76;
    private static final byte NEW_LINE = 10;
    private static final byte WHITE_SPACE_ENC = -5;

    /* renamed from: io.netty.handler.codec.base64.Base64$Decoder */
    private static final class Decoder implements ByteProcessor {

        /* renamed from: b4 */
        private final byte[] f3712b4;
        private int b4Posn;
        private byte[] decodabet;
        private ByteBuf dest;
        private int outBuffPosn;
        private byte sbiCrop;
        private byte sbiDecode;

        private Decoder() {
            this.f3712b4 = new byte[4];
        }

        /* access modifiers changed from: 0000 */
        public ByteBuf decode(ByteBuf byteBuf, int i, int i2, ByteBufAllocator byteBufAllocator, Base64Dialect base64Dialect) {
            this.dest = byteBufAllocator.buffer(Base64.decodedBufferSize(i2)).order(byteBuf.order());
            this.decodabet = Base64.decodabet(base64Dialect);
            try {
                byteBuf.forEachByte(i, i2, this);
                return this.dest.slice(0, this.outBuffPosn);
            } catch (Throwable th) {
                this.dest.release();
                PlatformDependent.throwException(th);
                return null;
            }
        }

        public boolean process(byte b) throws Exception {
            this.sbiCrop = (byte) (b & Byte.MAX_VALUE);
            byte[] bArr = this.decodabet;
            byte b2 = this.sbiCrop;
            this.sbiDecode = bArr[b2];
            byte b3 = this.sbiDecode;
            if (b3 >= -5) {
                if (b3 >= -1) {
                    byte[] bArr2 = this.f3712b4;
                    int i = this.b4Posn;
                    this.b4Posn = i + 1;
                    bArr2[i] = b2;
                    if (this.b4Posn > 3) {
                        int i2 = this.outBuffPosn;
                        this.outBuffPosn = i2 + decode4to3(bArr2, this.dest, i2, bArr);
                        this.b4Posn = 0;
                        if (this.sbiCrop == 61) {
                            return false;
                        }
                    }
                }
                return true;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("invalid bad Base64 input character: ");
            sb.append((short) (b & 255));
            sb.append(" (decimal)");
            throw new IllegalArgumentException(sb.toString());
        }

        private static int decode4to3(byte[] bArr, ByteBuf byteBuf, int i, byte[] bArr2) {
            int i2;
            int i3;
            int i4;
            byte b = bArr[0];
            byte b2 = bArr[1];
            byte b3 = bArr[2];
            String str = "not encoded in Base64";
            if (b3 == 61) {
                try {
                    byteBuf.setByte(i, ((bArr2[b] & 255) << 2) | ((bArr2[b2] & 255) >>> 4));
                    return 1;
                } catch (IndexOutOfBoundsException unused) {
                    throw new IllegalArgumentException(str);
                }
            } else {
                byte b4 = bArr[3];
                if (b4 == 61) {
                    byte b5 = bArr2[b2];
                    try {
                        if (byteBuf.order() == ByteOrder.BIG_ENDIAN) {
                            i4 = ((b5 & Ascii.f1875SI) << 4) | ((((bArr2[b] & 63) << 2) | ((b5 & 240) >> 4)) << 8) | ((bArr2[b3] & 252) >>> 2);
                        } else {
                            i4 = ((((b5 & Ascii.f1875SI) << 4) | ((bArr2[b3] & 252) >>> 2)) << 8) | ((bArr2[b] & 63) << 2) | ((b5 & 240) >> 4);
                        }
                        byteBuf.setShort(i, i4);
                        return 2;
                    } catch (IndexOutOfBoundsException unused2) {
                        throw new IllegalArgumentException(str);
                    }
                } else {
                    try {
                        if (byteBuf.order() == ByteOrder.BIG_ENDIAN) {
                            i3 = ((bArr2[b] & 63) << Ascii.DC2) | ((bArr2[b2] & 255) << Ascii.f1868FF) | ((bArr2[b3] & 255) << 6);
                            i2 = bArr2[b4] & 255;
                        } else {
                            byte b6 = bArr2[b2];
                            byte b7 = bArr2[b3];
                            i3 = ((bArr2[b] & 63) << 2) | ((b6 & Ascii.f1875SI) << Ascii.f1868FF) | ((b6 & 240) >>> 4) | ((b7 & 3) << Ascii.SYN) | ((b7 & 252) << 6);
                            i2 = (bArr2[b4] & 255) << Ascii.DLE;
                        }
                        byteBuf.setMedium(i, i2 | i3);
                        return 3;
                    } catch (IndexOutOfBoundsException unused3) {
                        throw new IllegalArgumentException(str);
                    }
                }
            }
        }
    }

    static int decodedBufferSize(int i) {
        return i - (i >>> 2);
    }

    private static int toInt(byte b) {
        return (b & 255) << Ascii.DLE;
    }

    private static int toIntBE(int i) {
        return (i & 255) | (16711680 & i) | (65280 & i);
    }

    private static int toIntBE(short s) {
        return ((s & 255) << 8) | ((65280 & s) << 8);
    }

    private static int toIntLE(int i) {
        return ((i & 16711680) >>> 16) | ((i & 255) << 16) | (65280 & i);
    }

    private static int toIntLE(short s) {
        return (s & 65280) | ((s & 255) << 16);
    }

    private static byte[] alphabet(Base64Dialect base64Dialect) {
        if (base64Dialect != null) {
            return base64Dialect.alphabet;
        }
        throw new NullPointerException("dialect");
    }

    /* access modifiers changed from: private */
    public static byte[] decodabet(Base64Dialect base64Dialect) {
        if (base64Dialect != null) {
            return base64Dialect.decodabet;
        }
        throw new NullPointerException("dialect");
    }

    private static boolean breakLines(Base64Dialect base64Dialect) {
        if (base64Dialect != null) {
            return base64Dialect.breakLinesByDefault;
        }
        throw new NullPointerException("dialect");
    }

    public static ByteBuf encode(ByteBuf byteBuf) {
        return encode(byteBuf, Base64Dialect.STANDARD);
    }

    public static ByteBuf encode(ByteBuf byteBuf, Base64Dialect base64Dialect) {
        return encode(byteBuf, breakLines(base64Dialect), base64Dialect);
    }

    public static ByteBuf encode(ByteBuf byteBuf, boolean z) {
        return encode(byteBuf, z, Base64Dialect.STANDARD);
    }

    public static ByteBuf encode(ByteBuf byteBuf, boolean z, Base64Dialect base64Dialect) {
        if (byteBuf != null) {
            ByteBuf encode = encode(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes(), z, base64Dialect);
            byteBuf.readerIndex(byteBuf.writerIndex());
            return encode;
        }
        throw new NullPointerException("src");
    }

    public static ByteBuf encode(ByteBuf byteBuf, int i, int i2) {
        return encode(byteBuf, i, i2, Base64Dialect.STANDARD);
    }

    public static ByteBuf encode(ByteBuf byteBuf, int i, int i2, Base64Dialect base64Dialect) {
        return encode(byteBuf, i, i2, breakLines(base64Dialect), base64Dialect);
    }

    public static ByteBuf encode(ByteBuf byteBuf, int i, int i2, boolean z) {
        return encode(byteBuf, i, i2, z, Base64Dialect.STANDARD);
    }

    public static ByteBuf encode(ByteBuf byteBuf, int i, int i2, boolean z, Base64Dialect base64Dialect) {
        return encode(byteBuf, i, i2, z, base64Dialect, byteBuf.alloc());
    }

    public static ByteBuf encode(ByteBuf byteBuf, int i, int i2, boolean z, Base64Dialect base64Dialect, ByteBufAllocator byteBufAllocator) {
        int i3 = i2;
        if (byteBuf == null) {
            throw new NullPointerException("src");
        } else if (base64Dialect != null) {
            ByteBuf order = byteBufAllocator.buffer(encodedBufferSize(i2, z)).order(byteBuf.order());
            byte[] alphabet = alphabet(base64Dialect);
            int i4 = i3 - 2;
            int i5 = 0;
            int i6 = 0;
            int i7 = 0;
            while (i5 < i4) {
                encode3to4(byteBuf, i5 + i, 3, order, i6, alphabet);
                i7 += 4;
                if (z && i7 == 76) {
                    order.setByte(i6 + 4, 10);
                    i6++;
                    i7 = 0;
                }
                i5 += 3;
                i6 += 4;
            }
            if (i5 < i3) {
                encode3to4(byteBuf, i5 + i, i3 - i5, order, i6, alphabet);
                i6 += 4;
            }
            if (i6 > 1 && order.getByte(i6 - 1) == 10) {
                i6--;
            }
            return order.slice(0, i6);
        } else {
            throw new NullPointerException("dialect");
        }
    }

    private static void encode3to4(ByteBuf byteBuf, int i, int i2, ByteBuf byteBuf2, int i3, byte[] bArr) {
        int i4 = 0;
        if (byteBuf.order() == ByteOrder.BIG_ENDIAN) {
            if (i2 == 1) {
                i4 = toInt(byteBuf.getByte(i));
            } else if (i2 == 2) {
                i4 = toIntBE(byteBuf.getShort(i));
            } else if (i2 > 0) {
                i4 = toIntBE(byteBuf.getMedium(i));
            }
            encode3to4BigEndian(i4, i2, byteBuf2, i3, bArr);
            return;
        }
        if (i2 == 1) {
            i4 = toInt(byteBuf.getByte(i));
        } else if (i2 == 2) {
            i4 = toIntLE(byteBuf.getShort(i));
        } else if (i2 > 0) {
            i4 = toIntLE(byteBuf.getMedium(i));
        }
        encode3to4LittleEndian(i4, i2, byteBuf2, i3, bArr);
    }

    static int encodedBufferSize(int i, boolean z) {
        long j = (((long) i) << 2) / 3;
        long j2 = (3 + j) & -4;
        if (z) {
            j2 += j / 76;
        }
        if (j2 < 2147483647L) {
            return (int) j2;
        }
        return Integer.MAX_VALUE;
    }

    private static void encode3to4BigEndian(int i, int i2, ByteBuf byteBuf, int i3, byte[] bArr) {
        if (i2 == 1) {
            byteBuf.setInt(i3, (bArr[(i >>> 12) & 63] << Ascii.DLE) | (bArr[i >>> 18] << Ascii.CAN) | 15616 | 61);
        } else if (i2 == 2) {
            byteBuf.setInt(i3, (bArr[(i >>> 6) & 63] << 8) | (bArr[i >>> 18] << Ascii.CAN) | (bArr[(i >>> 12) & 63] << Ascii.DLE) | 61);
        } else if (i2 == 3) {
            byteBuf.setInt(i3, bArr[i & 63] | (bArr[i >>> 18] << Ascii.CAN) | (bArr[(i >>> 12) & 63] << Ascii.DLE) | (bArr[(i >>> 6) & 63] << 8));
        }
    }

    private static void encode3to4LittleEndian(int i, int i2, ByteBuf byteBuf, int i3, byte[] bArr) {
        if (i2 == 1) {
            byteBuf.setInt(i3, (bArr[(i >>> 12) & 63] << 8) | bArr[i >>> 18] | 3997696 | 1023410176);
        } else if (i2 == 2) {
            byteBuf.setInt(i3, (bArr[(i >>> 6) & 63] << Ascii.DLE) | bArr[i >>> 18] | (bArr[(i >>> 12) & 63] << 8) | 1023410176);
        } else if (i2 == 3) {
            byteBuf.setInt(i3, (bArr[i & 63] << Ascii.CAN) | bArr[i >>> 18] | (bArr[(i >>> 12) & 63] << 8) | (bArr[(i >>> 6) & 63] << Ascii.DLE));
        }
    }

    public static ByteBuf decode(ByteBuf byteBuf) {
        return decode(byteBuf, Base64Dialect.STANDARD);
    }

    public static ByteBuf decode(ByteBuf byteBuf, Base64Dialect base64Dialect) {
        if (byteBuf != null) {
            ByteBuf decode = decode(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes(), base64Dialect);
            byteBuf.readerIndex(byteBuf.writerIndex());
            return decode;
        }
        throw new NullPointerException("src");
    }

    public static ByteBuf decode(ByteBuf byteBuf, int i, int i2) {
        return decode(byteBuf, i, i2, Base64Dialect.STANDARD);
    }

    public static ByteBuf decode(ByteBuf byteBuf, int i, int i2, Base64Dialect base64Dialect) {
        return decode(byteBuf, i, i2, base64Dialect, byteBuf.alloc());
    }

    public static ByteBuf decode(ByteBuf byteBuf, int i, int i2, Base64Dialect base64Dialect, ByteBufAllocator byteBufAllocator) {
        if (byteBuf == null) {
            throw new NullPointerException("src");
        } else if (base64Dialect != null) {
            return new Decoder().decode(byteBuf, i, i2, byteBufAllocator, base64Dialect);
        } else {
            throw new NullPointerException("dialect");
        }
    }

    private Base64() {
    }
}

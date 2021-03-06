package com.google.protobuf;

import com.google.android.exoplayer2.extractor.p040ts.PsExtractor;
import com.google.common.base.Ascii;
import java.nio.ByteBuffer;

final class Utf8 {
    private static final long ASCII_MASK_LONG = -9187201950435737472L;
    public static final int COMPLETE = 0;
    public static final int MALFORMED = -1;
    static final int MAX_BYTES_PER_CHAR = 3;
    private static final int UNSAFE_COUNT_ASCII_THRESHOLD = 16;
    private static final Processor processor = (UnsafeProcessor.isAvailable() ? new UnsafeProcessor() : new SafeProcessor());

    static abstract class Processor {
        /* access modifiers changed from: 0000 */
        public abstract int encodeUtf8(CharSequence charSequence, byte[] bArr, int i, int i2);

        /* access modifiers changed from: 0000 */
        public abstract void encodeUtf8Direct(CharSequence charSequence, ByteBuffer byteBuffer);

        /* access modifiers changed from: 0000 */
        public abstract int partialIsValidUtf8(int i, byte[] bArr, int i2, int i3);

        /* access modifiers changed from: 0000 */
        public abstract int partialIsValidUtf8Direct(int i, ByteBuffer byteBuffer, int i2, int i3);

        Processor() {
        }

        /* access modifiers changed from: 0000 */
        public final boolean isValidUtf8(byte[] bArr, int i, int i2) {
            return partialIsValidUtf8(0, bArr, i, i2) == 0;
        }

        /* access modifiers changed from: 0000 */
        public final boolean isValidUtf8(ByteBuffer byteBuffer, int i, int i2) {
            return partialIsValidUtf8(0, byteBuffer, i, i2) == 0;
        }

        /* access modifiers changed from: 0000 */
        public final int partialIsValidUtf8(int i, ByteBuffer byteBuffer, int i2, int i3) {
            if (byteBuffer.hasArray()) {
                int arrayOffset = byteBuffer.arrayOffset();
                return partialIsValidUtf8(i, byteBuffer.array(), i2 + arrayOffset, arrayOffset + i3);
            } else if (byteBuffer.isDirect()) {
                return partialIsValidUtf8Direct(i, byteBuffer, i2, i3);
            } else {
                return partialIsValidUtf8Default(i, byteBuffer, i2, i3);
            }
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0048, code lost:
            if (r8.get(r9) > -65) goto L_0x004a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:0x0086, code lost:
            if (r8.get(r7) > -65) goto L_0x0088;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x0017, code lost:
            if (r8.get(r9) > -65) goto L_0x0019;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final int partialIsValidUtf8Default(int r7, java.nio.ByteBuffer r8, int r9, int r10) {
            /*
                r6 = this;
                if (r7 == 0) goto L_0x0089
                if (r9 < r10) goto L_0x0005
                return r7
            L_0x0005:
                byte r0 = (byte) r7
                r1 = -32
                r2 = -1
                r3 = -65
                if (r0 >= r1) goto L_0x001a
                r7 = -62
                if (r0 < r7) goto L_0x0019
                int r7 = r9 + 1
                byte r9 = r8.get(r9)
                if (r9 <= r3) goto L_0x008a
            L_0x0019:
                return r2
            L_0x001a:
                r4 = -16
                if (r0 >= r4) goto L_0x004b
                int r7 = r7 >> 8
                r7 = r7 ^ r2
                byte r7 = (byte) r7
                if (r7 != 0) goto L_0x0034
                int r7 = r9 + 1
                byte r9 = r8.get(r9)
                if (r7 < r10) goto L_0x0031
                int r7 = com.google.protobuf.Utf8.incompleteStateFor(r0, r9)
                return r7
            L_0x0031:
                r5 = r9
                r9 = r7
                r7 = r5
            L_0x0034:
                if (r7 > r3) goto L_0x004a
                r4 = -96
                if (r0 != r1) goto L_0x003c
                if (r7 < r4) goto L_0x004a
            L_0x003c:
                r1 = -19
                if (r0 != r1) goto L_0x0042
                if (r7 >= r4) goto L_0x004a
            L_0x0042:
                int r7 = r9 + 1
                byte r9 = r8.get(r9)
                if (r9 <= r3) goto L_0x008a
            L_0x004a:
                return r2
            L_0x004b:
                int r1 = r7 >> 8
                r1 = r1 ^ r2
                byte r1 = (byte) r1
                r4 = 0
                if (r1 != 0) goto L_0x005f
                int r7 = r9 + 1
                byte r1 = r8.get(r9)
                if (r7 < r10) goto L_0x0063
                int r7 = com.google.protobuf.Utf8.incompleteStateFor(r0, r1)
                return r7
            L_0x005f:
                int r7 = r7 >> 16
                byte r4 = (byte) r7
                r7 = r9
            L_0x0063:
                if (r4 != 0) goto L_0x0073
                int r9 = r7 + 1
                byte r4 = r8.get(r7)
                if (r9 < r10) goto L_0x0072
                int r7 = com.google.protobuf.Utf8.incompleteStateFor(r0, r1, r4)
                return r7
            L_0x0072:
                r7 = r9
            L_0x0073:
                if (r1 > r3) goto L_0x0088
                int r9 = r0 << 28
                int r1 = r1 + 112
                int r9 = r9 + r1
                int r9 = r9 >> 30
                if (r9 != 0) goto L_0x0088
                if (r4 > r3) goto L_0x0088
                int r9 = r7 + 1
                byte r7 = r8.get(r7)
                if (r7 <= r3) goto L_0x0089
            L_0x0088:
                return r2
            L_0x0089:
                r7 = r9
            L_0x008a:
                int r7 = partialIsValidUtf8(r8, r7, r10)
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.Utf8.Processor.partialIsValidUtf8Default(int, java.nio.ByteBuffer, int, int):int");
        }

        private static int partialIsValidUtf8(ByteBuffer byteBuffer, int i, int i2) {
            int access$200 = i + Utf8.estimateConsecutiveAscii(byteBuffer, i, i2);
            while (access$200 < i2) {
                int i3 = access$200 + 1;
                byte b = byteBuffer.get(access$200);
                if (b < 0) {
                    if (b < -32) {
                        if (i3 >= i2) {
                            return b;
                        }
                        if (b < -62 || byteBuffer.get(i3) > -65) {
                            return -1;
                        }
                        i3++;
                    } else if (b < -16) {
                        if (i3 >= i2 - 1) {
                            return Utf8.incompleteStateFor(byteBuffer, b, i3, i2 - i3);
                        }
                        int i4 = i3 + 1;
                        byte b2 = byteBuffer.get(i3);
                        if (b2 > -65 || ((b == -32 && b2 < -96) || ((b == -19 && b2 >= -96) || byteBuffer.get(i4) > -65))) {
                            return -1;
                        }
                        access$200 = i4 + 1;
                    } else if (i3 >= i2 - 2) {
                        return Utf8.incompleteStateFor(byteBuffer, b, i3, i2 - i3);
                    } else {
                        int i5 = i3 + 1;
                        byte b3 = byteBuffer.get(i3);
                        if (b3 <= -65 && (((b << Ascii.f1869FS) + (b3 + 112)) >> 30) == 0) {
                            int i6 = i5 + 1;
                            if (byteBuffer.get(i5) <= -65) {
                                i3 = i6 + 1;
                                if (byteBuffer.get(i6) > -65) {
                                }
                            }
                        }
                        return -1;
                    }
                }
                access$200 = i3;
            }
            return 0;
        }

        /* access modifiers changed from: 0000 */
        public final void encodeUtf8(CharSequence charSequence, ByteBuffer byteBuffer) {
            if (byteBuffer.hasArray()) {
                int arrayOffset = byteBuffer.arrayOffset();
                byteBuffer.position(Utf8.encode(charSequence, byteBuffer.array(), byteBuffer.position() + arrayOffset, byteBuffer.remaining()) - arrayOffset);
            } else if (byteBuffer.isDirect()) {
                encodeUtf8Direct(charSequence, byteBuffer);
            } else {
                encodeUtf8Default(charSequence, byteBuffer);
            }
        }

        /* access modifiers changed from: 0000 */
        public final void encodeUtf8Default(CharSequence charSequence, ByteBuffer byteBuffer) {
            int i;
            int length = charSequence.length();
            int position = byteBuffer.position();
            int i2 = 0;
            while (i2 < length) {
                try {
                    char charAt = charSequence.charAt(i2);
                    if (charAt >= 128) {
                        break;
                    }
                    byteBuffer.put(position + i2, (byte) charAt);
                    i2++;
                } catch (IndexOutOfBoundsException unused) {
                    int position2 = byteBuffer.position() + Math.max(i2, (position - byteBuffer.position()) + 1);
                    StringBuilder sb = new StringBuilder();
                    sb.append("Failed writing ");
                    sb.append(charSequence.charAt(i2));
                    sb.append(" at index ");
                    sb.append(position2);
                    throw new ArrayIndexOutOfBoundsException(sb.toString());
                }
            }
            if (i2 == length) {
                byteBuffer.position(position + i2);
                return;
            }
            position += i2;
            while (i2 < length) {
                char charAt2 = charSequence.charAt(i2);
                if (charAt2 < 128) {
                    byteBuffer.put(position, (byte) charAt2);
                } else if (charAt2 < 2048) {
                    i = position + 1;
                    try {
                        byteBuffer.put(position, (byte) ((charAt2 >>> 6) | 192));
                        byteBuffer.put(i, (byte) ((charAt2 & '?') | 128));
                        position = i;
                    } catch (IndexOutOfBoundsException unused2) {
                        position = i;
                        int position22 = byteBuffer.position() + Math.max(i2, (position - byteBuffer.position()) + 1);
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Failed writing ");
                        sb2.append(charSequence.charAt(i2));
                        sb2.append(" at index ");
                        sb2.append(position22);
                        throw new ArrayIndexOutOfBoundsException(sb2.toString());
                    }
                } else if (charAt2 < 55296 || 57343 < charAt2) {
                    i = position + 1;
                    byteBuffer.put(position, (byte) ((charAt2 >>> 12) | 224));
                    position = i + 1;
                    byteBuffer.put(i, (byte) (((charAt2 >>> 6) & 63) | 128));
                    byteBuffer.put(position, (byte) ((charAt2 & '?') | 128));
                } else {
                    int i3 = i2 + 1;
                    if (i3 != length) {
                        try {
                            char charAt3 = charSequence.charAt(i3);
                            if (Character.isSurrogatePair(charAt2, charAt3)) {
                                int codePoint = Character.toCodePoint(charAt2, charAt3);
                                int i4 = position + 1;
                                try {
                                    byteBuffer.put(position, (byte) ((codePoint >>> 18) | PsExtractor.VIDEO_STREAM_MASK));
                                    position = i4 + 1;
                                    byteBuffer.put(i4, (byte) (((codePoint >>> 12) & 63) | 128));
                                    i4 = position + 1;
                                    byteBuffer.put(position, (byte) (((codePoint >>> 6) & 63) | 128));
                                    byteBuffer.put(i4, (byte) ((codePoint & 63) | 128));
                                    position = i4;
                                    i2 = i3;
                                } catch (IndexOutOfBoundsException unused3) {
                                    position = i4;
                                    i2 = i3;
                                    int position222 = byteBuffer.position() + Math.max(i2, (position - byteBuffer.position()) + 1);
                                    StringBuilder sb22 = new StringBuilder();
                                    sb22.append("Failed writing ");
                                    sb22.append(charSequence.charAt(i2));
                                    sb22.append(" at index ");
                                    sb22.append(position222);
                                    throw new ArrayIndexOutOfBoundsException(sb22.toString());
                                }
                            } else {
                                i2 = i3;
                            }
                        } catch (IndexOutOfBoundsException unused4) {
                            i2 = i3;
                            int position2222 = byteBuffer.position() + Math.max(i2, (position - byteBuffer.position()) + 1);
                            StringBuilder sb222 = new StringBuilder();
                            sb222.append("Failed writing ");
                            sb222.append(charSequence.charAt(i2));
                            sb222.append(" at index ");
                            sb222.append(position2222);
                            throw new ArrayIndexOutOfBoundsException(sb222.toString());
                        }
                    }
                    throw new UnpairedSurrogateException(i2, length);
                }
                i2++;
                position++;
            }
            byteBuffer.position(position);
        }
    }

    static final class SafeProcessor extends Processor {
        SafeProcessor() {
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0042, code lost:
            if (r8[r9] > -65) goto L_0x0044;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:0x007a, code lost:
            if (r8[r7] > -65) goto L_0x007c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x0015, code lost:
            if (r8[r9] > -65) goto L_0x0017;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int partialIsValidUtf8(int r7, byte[] r8, int r9, int r10) {
            /*
                r6 = this;
                if (r7 == 0) goto L_0x007d
                if (r9 < r10) goto L_0x0005
                return r7
            L_0x0005:
                byte r0 = (byte) r7
                r1 = -32
                r2 = -1
                r3 = -65
                if (r0 >= r1) goto L_0x0018
                r7 = -62
                if (r0 < r7) goto L_0x0017
                int r7 = r9 + 1
                byte r9 = r8[r9]
                if (r9 <= r3) goto L_0x007e
            L_0x0017:
                return r2
            L_0x0018:
                r4 = -16
                if (r0 >= r4) goto L_0x0045
                int r7 = r7 >> 8
                r7 = r7 ^ r2
                byte r7 = (byte) r7
                if (r7 != 0) goto L_0x0030
                int r7 = r9 + 1
                byte r9 = r8[r9]
                if (r7 < r10) goto L_0x002d
                int r7 = com.google.protobuf.Utf8.incompleteStateFor(r0, r9)
                return r7
            L_0x002d:
                r5 = r9
                r9 = r7
                r7 = r5
            L_0x0030:
                if (r7 > r3) goto L_0x0044
                r4 = -96
                if (r0 != r1) goto L_0x0038
                if (r7 < r4) goto L_0x0044
            L_0x0038:
                r1 = -19
                if (r0 != r1) goto L_0x003e
                if (r7 >= r4) goto L_0x0044
            L_0x003e:
                int r7 = r9 + 1
                byte r9 = r8[r9]
                if (r9 <= r3) goto L_0x007e
            L_0x0044:
                return r2
            L_0x0045:
                int r1 = r7 >> 8
                r1 = r1 ^ r2
                byte r1 = (byte) r1
                r4 = 0
                if (r1 != 0) goto L_0x0057
                int r7 = r9 + 1
                byte r1 = r8[r9]
                if (r7 < r10) goto L_0x005b
                int r7 = com.google.protobuf.Utf8.incompleteStateFor(r0, r1)
                return r7
            L_0x0057:
                int r7 = r7 >> 16
                byte r4 = (byte) r7
                r7 = r9
            L_0x005b:
                if (r4 != 0) goto L_0x0069
                int r9 = r7 + 1
                byte r4 = r8[r7]
                if (r9 < r10) goto L_0x0068
                int r7 = com.google.protobuf.Utf8.incompleteStateFor(r0, r1, r4)
                return r7
            L_0x0068:
                r7 = r9
            L_0x0069:
                if (r1 > r3) goto L_0x007c
                int r9 = r0 << 28
                int r1 = r1 + 112
                int r9 = r9 + r1
                int r9 = r9 >> 30
                if (r9 != 0) goto L_0x007c
                if (r4 > r3) goto L_0x007c
                int r9 = r7 + 1
                byte r7 = r8[r7]
                if (r7 <= r3) goto L_0x007d
            L_0x007c:
                return r2
            L_0x007d:
                r7 = r9
            L_0x007e:
                int r7 = partialIsValidUtf8(r8, r7, r10)
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.Utf8.SafeProcessor.partialIsValidUtf8(int, byte[], int, int):int");
        }

        /* access modifiers changed from: 0000 */
        public int partialIsValidUtf8Direct(int i, ByteBuffer byteBuffer, int i2, int i3) {
            return partialIsValidUtf8Default(i, byteBuffer, i2, i3);
        }

        /* access modifiers changed from: 0000 */
        public int encodeUtf8(CharSequence charSequence, byte[] bArr, int i, int i2) {
            int i3;
            int length = charSequence.length();
            int i4 = i2 + i;
            int i5 = 0;
            while (i5 < length) {
                int i6 = i5 + i;
                if (i6 >= i4) {
                    break;
                }
                char charAt = charSequence.charAt(i5);
                if (charAt >= 128) {
                    break;
                }
                bArr[i6] = (byte) charAt;
                i5++;
            }
            if (i5 == length) {
                return i + length;
            }
            int i7 = i + i5;
            while (i5 < length) {
                char charAt2 = charSequence.charAt(i5);
                if (charAt2 < 128 && i7 < i4) {
                    i3 = i7 + 1;
                    bArr[i7] = (byte) charAt2;
                } else if (charAt2 < 2048 && i7 <= i4 - 2) {
                    int i8 = i7 + 1;
                    bArr[i7] = (byte) ((charAt2 >>> 6) | 960);
                    i7 = i8 + 1;
                    bArr[i8] = (byte) ((charAt2 & '?') | 128);
                    i5++;
                } else if ((charAt2 < 55296 || 57343 < charAt2) && i7 <= i4 - 3) {
                    int i9 = i7 + 1;
                    bArr[i7] = (byte) ((charAt2 >>> 12) | 480);
                    int i10 = i9 + 1;
                    bArr[i9] = (byte) (((charAt2 >>> 6) & 63) | 128);
                    i3 = i10 + 1;
                    bArr[i10] = (byte) ((charAt2 & '?') | 128);
                } else if (i7 <= i4 - 4) {
                    int i11 = i5 + 1;
                    if (i11 != charSequence.length()) {
                        char charAt3 = charSequence.charAt(i11);
                        if (Character.isSurrogatePair(charAt2, charAt3)) {
                            int codePoint = Character.toCodePoint(charAt2, charAt3);
                            int i12 = i7 + 1;
                            bArr[i7] = (byte) ((codePoint >>> 18) | PsExtractor.VIDEO_STREAM_MASK);
                            int i13 = i12 + 1;
                            bArr[i12] = (byte) (((codePoint >>> 12) & 63) | 128);
                            int i14 = i13 + 1;
                            bArr[i13] = (byte) (((codePoint >>> 6) & 63) | 128);
                            i7 = i14 + 1;
                            bArr[i14] = (byte) ((codePoint & 63) | 128);
                            i5 = i11;
                            i5++;
                        } else {
                            i5 = i11;
                        }
                    }
                    throw new UnpairedSurrogateException(i5 - 1, length);
                } else {
                    if (55296 <= charAt2 && charAt2 <= 57343) {
                        int i15 = i5 + 1;
                        if (i15 == charSequence.length() || !Character.isSurrogatePair(charAt2, charSequence.charAt(i15))) {
                            throw new UnpairedSurrogateException(i5, length);
                        }
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("Failed writing ");
                    sb.append(charAt2);
                    sb.append(" at index ");
                    sb.append(i7);
                    throw new ArrayIndexOutOfBoundsException(sb.toString());
                }
                i7 = i3;
                i5++;
            }
            return i7;
        }

        /* access modifiers changed from: 0000 */
        public void encodeUtf8Direct(CharSequence charSequence, ByteBuffer byteBuffer) {
            encodeUtf8Default(charSequence, byteBuffer);
        }

        private static int partialIsValidUtf8(byte[] bArr, int i, int i2) {
            while (i < i2 && bArr[i] >= 0) {
                i++;
            }
            if (i >= i2) {
                return 0;
            }
            return partialIsValidUtf8NonAscii(bArr, i, i2);
        }

        private static int partialIsValidUtf8NonAscii(byte[] bArr, int i, int i2) {
            while (i < i2) {
                int i3 = i + 1;
                byte b = bArr[i];
                if (b < 0) {
                    if (b < -32) {
                        if (i3 >= i2) {
                            return b;
                        }
                        if (b >= -62) {
                            i = i3 + 1;
                            if (bArr[i3] > -65) {
                            }
                        }
                        return -1;
                    } else if (b < -16) {
                        if (i3 >= i2 - 1) {
                            return Utf8.incompleteStateFor(bArr, i3, i2);
                        }
                        int i4 = i3 + 1;
                        byte b2 = bArr[i3];
                        if (b2 <= -65 && ((b != -32 || b2 >= -96) && (b != -19 || b2 < -96))) {
                            i = i4 + 1;
                            if (bArr[i4] > -65) {
                            }
                        }
                        return -1;
                    } else if (i3 >= i2 - 2) {
                        return Utf8.incompleteStateFor(bArr, i3, i2);
                    } else {
                        int i5 = i3 + 1;
                        byte b3 = bArr[i3];
                        if (b3 <= -65 && (((b << Ascii.f1869FS) + (b3 + 112)) >> 30) == 0) {
                            int i6 = i5 + 1;
                            if (bArr[i5] <= -65) {
                                i3 = i6 + 1;
                                if (bArr[i6] > -65) {
                                }
                            }
                        }
                        return -1;
                    }
                }
                i = i3;
            }
            return 0;
        }
    }

    static class UnpairedSurrogateException extends IllegalArgumentException {
        UnpairedSurrogateException(int i, int i2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unpaired surrogate at index ");
            sb.append(i);
            sb.append(" of ");
            sb.append(i2);
            super(sb.toString());
        }
    }

    static final class UnsafeProcessor extends Processor {
        UnsafeProcessor() {
        }

        static boolean isAvailable() {
            return UnsafeUtil.hasUnsafeArrayOperations() && UnsafeUtil.hasUnsafeByteBufferOperations();
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x002f, code lost:
            if (com.google.protobuf.UnsafeUtil.getByte(r13, r2) > -65) goto L_0x0031;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x0060, code lost:
            if (com.google.protobuf.UnsafeUtil.getByte(r13, r2) > -65) goto L_0x0062;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:0x00a2, code lost:
            if (com.google.protobuf.UnsafeUtil.getByte(r13, r2) > -65) goto L_0x00a4;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int partialIsValidUtf8(int r12, byte[] r13, int r14, int r15) {
            /*
                r11 = this;
                r0 = r14 | r15
                int r1 = r13.length
                int r1 = r1 - r15
                r0 = r0 | r1
                r1 = 0
                if (r0 < 0) goto L_0x00ad
                long r2 = com.google.protobuf.UnsafeUtil.getArrayBaseOffset()
                long r4 = (long) r14
                long r2 = r2 + r4
                long r4 = com.google.protobuf.UnsafeUtil.getArrayBaseOffset()
                long r14 = (long) r15
                long r4 = r4 + r14
                if (r12 == 0) goto L_0x00a5
                int r14 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r14 < 0) goto L_0x001b
                return r12
            L_0x001b:
                byte r14 = (byte) r12
                r15 = -32
                r0 = -1
                r6 = -65
                r7 = 1
                if (r14 >= r15) goto L_0x0032
                r12 = -62
                if (r14 < r12) goto L_0x0031
                long r14 = r2 + r7
                byte r12 = com.google.protobuf.UnsafeUtil.getByte(r13, r2)
                if (r12 <= r6) goto L_0x00a6
            L_0x0031:
                return r0
            L_0x0032:
                r9 = -16
                if (r14 >= r9) goto L_0x0063
                int r12 = r12 >> 8
                r12 = r12 ^ r0
                byte r12 = (byte) r12
                if (r12 != 0) goto L_0x004c
                long r9 = r2 + r7
                byte r12 = com.google.protobuf.UnsafeUtil.getByte(r13, r2)
                int r1 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
                if (r1 < 0) goto L_0x004b
                int r12 = com.google.protobuf.Utf8.incompleteStateFor(r14, r12)
                return r12
            L_0x004b:
                r2 = r9
            L_0x004c:
                if (r12 > r6) goto L_0x0062
                r1 = -96
                if (r14 != r15) goto L_0x0054
                if (r12 < r1) goto L_0x0062
            L_0x0054:
                r15 = -19
                if (r14 != r15) goto L_0x005a
                if (r12 >= r1) goto L_0x0062
            L_0x005a:
                long r14 = r2 + r7
                byte r12 = com.google.protobuf.UnsafeUtil.getByte(r13, r2)
                if (r12 <= r6) goto L_0x00a6
            L_0x0062:
                return r0
            L_0x0063:
                int r15 = r12 >> 8
                r15 = r15 ^ r0
                byte r15 = (byte) r15
                if (r15 != 0) goto L_0x007a
                long r9 = r2 + r7
                byte r15 = com.google.protobuf.UnsafeUtil.getByte(r13, r2)
                int r12 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
                if (r12 < 0) goto L_0x0078
                int r12 = com.google.protobuf.Utf8.incompleteStateFor(r14, r15)
                return r12
            L_0x0078:
                r2 = r9
                goto L_0x007d
            L_0x007a:
                int r12 = r12 >> 16
                byte r1 = (byte) r12
            L_0x007d:
                if (r1 != 0) goto L_0x008f
                long r9 = r2 + r7
                byte r1 = com.google.protobuf.UnsafeUtil.getByte(r13, r2)
                int r12 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
                if (r12 < 0) goto L_0x008e
                int r12 = com.google.protobuf.Utf8.incompleteStateFor(r14, r15, r1)
                return r12
            L_0x008e:
                r2 = r9
            L_0x008f:
                if (r15 > r6) goto L_0x00a4
                int r12 = r14 << 28
                int r15 = r15 + 112
                int r12 = r12 + r15
                int r12 = r12 >> 30
                if (r12 != 0) goto L_0x00a4
                if (r1 > r6) goto L_0x00a4
                long r14 = r2 + r7
                byte r12 = com.google.protobuf.UnsafeUtil.getByte(r13, r2)
                if (r12 <= r6) goto L_0x00a6
            L_0x00a4:
                return r0
            L_0x00a5:
                r14 = r2
            L_0x00a6:
                long r4 = r4 - r14
                int r12 = (int) r4
                int r12 = partialIsValidUtf8(r13, r14, r12)
                return r12
            L_0x00ad:
                java.lang.ArrayIndexOutOfBoundsException r12 = new java.lang.ArrayIndexOutOfBoundsException
                r0 = 3
                java.lang.Object[] r0 = new java.lang.Object[r0]
                int r13 = r13.length
                java.lang.Integer r13 = java.lang.Integer.valueOf(r13)
                r0[r1] = r13
                r13 = 1
                java.lang.Integer r14 = java.lang.Integer.valueOf(r14)
                r0[r13] = r14
                r13 = 2
                java.lang.Integer r14 = java.lang.Integer.valueOf(r15)
                r0[r13] = r14
                java.lang.String r13 = "Array length=%d, index=%d, limit=%d"
                java.lang.String r13 = java.lang.String.format(r13, r0)
                r12.<init>(r13)
                throw r12
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.Utf8.UnsafeProcessor.partialIsValidUtf8(int, byte[], int, int):int");
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x002f, code lost:
            if (com.google.protobuf.UnsafeUtil.getByte(r2) > -65) goto L_0x0031;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x0060, code lost:
            if (com.google.protobuf.UnsafeUtil.getByte(r2) > -65) goto L_0x0062;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:0x00a2, code lost:
            if (com.google.protobuf.UnsafeUtil.getByte(r2) > -65) goto L_0x00a4;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int partialIsValidUtf8Direct(int r11, java.nio.ByteBuffer r12, int r13, int r14) {
            /*
                r10 = this;
                r0 = r13 | r14
                int r1 = r12.limit()
                int r1 = r1 - r14
                r0 = r0 | r1
                r1 = 0
                if (r0 < 0) goto L_0x00ad
                long r2 = com.google.protobuf.UnsafeUtil.addressOffset(r12)
                long r4 = (long) r13
                long r2 = r2 + r4
                int r14 = r14 - r13
                long r12 = (long) r14
                long r12 = r12 + r2
                if (r11 == 0) goto L_0x00a5
                int r14 = (r2 > r12 ? 1 : (r2 == r12 ? 0 : -1))
                if (r14 < 0) goto L_0x001b
                return r11
            L_0x001b:
                byte r14 = (byte) r11
                r0 = -32
                r4 = -1
                r5 = -65
                r6 = 1
                if (r14 >= r0) goto L_0x0032
                r11 = -62
                if (r14 < r11) goto L_0x0031
                long r0 = r2 + r6
                byte r11 = com.google.protobuf.UnsafeUtil.getByte(r2)
                if (r11 <= r5) goto L_0x00a6
            L_0x0031:
                return r4
            L_0x0032:
                r8 = -16
                if (r14 >= r8) goto L_0x0063
                int r11 = r11 >> 8
                r11 = r11 ^ r4
                byte r11 = (byte) r11
                if (r11 != 0) goto L_0x004c
                long r8 = r2 + r6
                byte r11 = com.google.protobuf.UnsafeUtil.getByte(r2)
                int r1 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1))
                if (r1 < 0) goto L_0x004b
                int r11 = com.google.protobuf.Utf8.incompleteStateFor(r14, r11)
                return r11
            L_0x004b:
                r2 = r8
            L_0x004c:
                if (r11 > r5) goto L_0x0062
                r1 = -96
                if (r14 != r0) goto L_0x0054
                if (r11 < r1) goto L_0x0062
            L_0x0054:
                r0 = -19
                if (r14 != r0) goto L_0x005a
                if (r11 >= r1) goto L_0x0062
            L_0x005a:
                long r0 = r2 + r6
                byte r11 = com.google.protobuf.UnsafeUtil.getByte(r2)
                if (r11 <= r5) goto L_0x00a6
            L_0x0062:
                return r4
            L_0x0063:
                int r0 = r11 >> 8
                r0 = r0 ^ r4
                byte r0 = (byte) r0
                if (r0 != 0) goto L_0x007a
                long r8 = r2 + r6
                byte r0 = com.google.protobuf.UnsafeUtil.getByte(r2)
                int r11 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1))
                if (r11 < 0) goto L_0x0078
                int r11 = com.google.protobuf.Utf8.incompleteStateFor(r14, r0)
                return r11
            L_0x0078:
                r2 = r8
                goto L_0x007d
            L_0x007a:
                int r11 = r11 >> 16
                byte r1 = (byte) r11
            L_0x007d:
                if (r1 != 0) goto L_0x008f
                long r8 = r2 + r6
                byte r1 = com.google.protobuf.UnsafeUtil.getByte(r2)
                int r11 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1))
                if (r11 < 0) goto L_0x008e
                int r11 = com.google.protobuf.Utf8.incompleteStateFor(r14, r0, r1)
                return r11
            L_0x008e:
                r2 = r8
            L_0x008f:
                if (r0 > r5) goto L_0x00a4
                int r11 = r14 << 28
                int r0 = r0 + 112
                int r11 = r11 + r0
                int r11 = r11 >> 30
                if (r11 != 0) goto L_0x00a4
                if (r1 > r5) goto L_0x00a4
                long r0 = r2 + r6
                byte r11 = com.google.protobuf.UnsafeUtil.getByte(r2)
                if (r11 <= r5) goto L_0x00a6
            L_0x00a4:
                return r4
            L_0x00a5:
                r0 = r2
            L_0x00a6:
                long r12 = r12 - r0
                int r11 = (int) r12
                int r11 = partialIsValidUtf8(r0, r11)
                return r11
            L_0x00ad:
                java.lang.ArrayIndexOutOfBoundsException r11 = new java.lang.ArrayIndexOutOfBoundsException
                r0 = 3
                java.lang.Object[] r0 = new java.lang.Object[r0]
                int r12 = r12.limit()
                java.lang.Integer r12 = java.lang.Integer.valueOf(r12)
                r0[r1] = r12
                r12 = 1
                java.lang.Integer r13 = java.lang.Integer.valueOf(r13)
                r0[r12] = r13
                r12 = 2
                java.lang.Integer r13 = java.lang.Integer.valueOf(r14)
                r0[r12] = r13
                java.lang.String r12 = "buffer limit=%d, index=%d, limit=%d"
                java.lang.String r12 = java.lang.String.format(r12, r0)
                r11.<init>(r12)
                throw r11
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.Utf8.UnsafeProcessor.partialIsValidUtf8Direct(int, java.nio.ByteBuffer, int, int):int");
        }

        /* access modifiers changed from: 0000 */
        public int encodeUtf8(CharSequence charSequence, byte[] bArr, int i, int i2) {
            char c;
            long j;
            long arrayBaseOffset;
            long j2;
            long j3;
            CharSequence charSequence2 = charSequence;
            byte[] bArr2 = bArr;
            int i3 = i;
            int i4 = i2;
            long arrayBaseOffset2 = UnsafeUtil.getArrayBaseOffset() + ((long) i3);
            long j4 = ((long) i4) + arrayBaseOffset2;
            int length = charSequence.length();
            String str = " at index ";
            String str2 = "Failed writing ";
            if (length > i4 || bArr2.length - i4 < i3) {
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(charSequence2.charAt(length - 1));
                sb.append(str);
                sb.append(i3 + i4);
                throw new ArrayIndexOutOfBoundsException(sb.toString());
            }
            int i5 = 0;
            while (true) {
                c = 128;
                j = 1;
                if (i5 >= length) {
                    break;
                }
                char charAt = charSequence2.charAt(i5);
                if (charAt >= 128) {
                    break;
                }
                long j5 = 1 + arrayBaseOffset2;
                UnsafeUtil.putByte(bArr2, arrayBaseOffset2, (byte) charAt);
                i5++;
                arrayBaseOffset2 = j5;
            }
            if (i5 == length) {
                arrayBaseOffset = UnsafeUtil.getArrayBaseOffset();
            } else {
                while (i5 < length) {
                    char charAt2 = charSequence2.charAt(i5);
                    if (charAt2 < c && arrayBaseOffset2 < j4) {
                        long j6 = arrayBaseOffset2 + j;
                        UnsafeUtil.putByte(bArr2, arrayBaseOffset2, (byte) charAt2);
                        j3 = j;
                        j2 = j6;
                    } else if (charAt2 < 2048 && arrayBaseOffset2 <= j4 - 2) {
                        long j7 = arrayBaseOffset2 + j;
                        UnsafeUtil.putByte(bArr2, arrayBaseOffset2, (byte) ((charAt2 >>> 6) | 960));
                        long j8 = j7 + j;
                        UnsafeUtil.putByte(bArr2, j7, (byte) ((charAt2 & '?') | 128));
                        j2 = j8;
                        j3 = j;
                        i5++;
                        c = 128;
                        long j9 = j3;
                        arrayBaseOffset2 = j2;
                        j = j9;
                    } else if ((charAt2 < 55296 || 57343 < charAt2) && arrayBaseOffset2 <= j4 - 3) {
                        long j10 = arrayBaseOffset2 + j;
                        UnsafeUtil.putByte(bArr2, arrayBaseOffset2, (byte) ((charAt2 >>> 12) | 480));
                        long j11 = j10 + j;
                        UnsafeUtil.putByte(bArr2, j10, (byte) (((charAt2 >>> 6) & 63) | 128));
                        long j12 = j11 + 1;
                        UnsafeUtil.putByte(bArr2, j11, (byte) ((charAt2 & '?') | 128));
                        j2 = j12;
                        j3 = 1;
                    } else if (arrayBaseOffset2 <= j4 - 4) {
                        int i6 = i5 + 1;
                        if (i6 != length) {
                            char charAt3 = charSequence2.charAt(i6);
                            if (Character.isSurrogatePair(charAt2, charAt3)) {
                                int codePoint = Character.toCodePoint(charAt2, charAt3);
                                long j13 = arrayBaseOffset2 + 1;
                                UnsafeUtil.putByte(bArr2, arrayBaseOffset2, (byte) ((codePoint >>> 18) | PsExtractor.VIDEO_STREAM_MASK));
                                long j14 = j13 + 1;
                                UnsafeUtil.putByte(bArr2, j13, (byte) (((codePoint >>> 12) & 63) | 128));
                                long j15 = j14 + 1;
                                UnsafeUtil.putByte(bArr2, j14, (byte) (((codePoint >>> 6) & 63) | 128));
                                j3 = 1;
                                j2 = j15 + 1;
                                UnsafeUtil.putByte(bArr2, j15, (byte) ((codePoint & 63) | 128));
                                i5 = i6;
                                i5++;
                                c = 128;
                                long j92 = j3;
                                arrayBaseOffset2 = j2;
                                j = j92;
                            } else {
                                i5 = i6;
                            }
                        }
                        throw new UnpairedSurrogateException(i5 - 1, length);
                    } else {
                        if (55296 <= charAt2 && charAt2 <= 57343) {
                            int i7 = i5 + 1;
                            if (i7 == length || !Character.isSurrogatePair(charAt2, charSequence2.charAt(i7))) {
                                throw new UnpairedSurrogateException(i5, length);
                            }
                        }
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str2);
                        sb2.append(charAt2);
                        sb2.append(str);
                        sb2.append(arrayBaseOffset2);
                        throw new ArrayIndexOutOfBoundsException(sb2.toString());
                    }
                    i5++;
                    c = 128;
                    long j922 = j3;
                    arrayBaseOffset2 = j2;
                    j = j922;
                }
                arrayBaseOffset = UnsafeUtil.getArrayBaseOffset();
            }
            return (int) (arrayBaseOffset2 - arrayBaseOffset);
        }

        /* access modifiers changed from: 0000 */
        public void encodeUtf8Direct(CharSequence charSequence, ByteBuffer byteBuffer) {
            long j;
            char c;
            long j2;
            CharSequence charSequence2 = charSequence;
            ByteBuffer byteBuffer2 = byteBuffer;
            long addressOffset = UnsafeUtil.addressOffset(byteBuffer);
            long position = ((long) byteBuffer.position()) + addressOffset;
            long limit = ((long) byteBuffer.limit()) + addressOffset;
            int length = charSequence.length();
            String str = " at index ";
            String str2 = "Failed writing ";
            if (((long) length) <= limit - position) {
                int i = 0;
                while (true) {
                    c = 128;
                    if (i >= length) {
                        break;
                    }
                    char charAt = charSequence2.charAt(i);
                    if (charAt >= 128) {
                        break;
                    }
                    long j3 = 1 + j;
                    UnsafeUtil.putByte(j, (byte) charAt);
                    i++;
                    position = j3;
                }
                if (i == length) {
                    byteBuffer2.position((int) (j - addressOffset));
                    return;
                }
                while (i < length) {
                    char charAt2 = charSequence2.charAt(i);
                    if (charAt2 < c && j < limit) {
                        long j4 = j + 1;
                        UnsafeUtil.putByte(j, (byte) charAt2);
                        j = j4;
                        j2 = addressOffset;
                    } else if (charAt2 >= 2048 || j > limit - 2) {
                        j2 = addressOffset;
                        if ((charAt2 < 55296 || 57343 < charAt2) && j <= limit - 3) {
                            long j5 = j + 1;
                            UnsafeUtil.putByte(j, (byte) ((charAt2 >>> 12) | 480));
                            long j6 = j5 + 1;
                            UnsafeUtil.putByte(j5, (byte) (((charAt2 >>> 6) & 63) | 128));
                            long j7 = j6 + 1;
                            UnsafeUtil.putByte(j6, (byte) ((charAt2 & '?') | 128));
                            j = j7;
                        } else if (j <= limit - 4) {
                            int i2 = i + 1;
                            if (i2 != length) {
                                char charAt3 = charSequence2.charAt(i2);
                                if (Character.isSurrogatePair(charAt2, charAt3)) {
                                    int codePoint = Character.toCodePoint(charAt2, charAt3);
                                    long j8 = j + 1;
                                    UnsafeUtil.putByte(j, (byte) ((codePoint >>> 18) | PsExtractor.VIDEO_STREAM_MASK));
                                    long j9 = j8 + 1;
                                    UnsafeUtil.putByte(j8, (byte) (((codePoint >>> 12) & 63) | 128));
                                    long j10 = j9 + 1;
                                    UnsafeUtil.putByte(j9, (byte) (((codePoint >>> 6) & 63) | 128));
                                    long j11 = j10 + 1;
                                    UnsafeUtil.putByte(j10, (byte) ((codePoint & 63) | 128));
                                    i = i2;
                                    j = j11;
                                }
                            } else {
                                i2 = i;
                            }
                            throw new UnpairedSurrogateException(i2 - 1, length);
                        } else {
                            if (55296 <= charAt2 && charAt2 <= 57343) {
                                int i3 = i + 1;
                                if (i3 == length || !Character.isSurrogatePair(charAt2, charSequence2.charAt(i3))) {
                                    throw new UnpairedSurrogateException(i, length);
                                }
                            }
                            StringBuilder sb = new StringBuilder();
                            sb.append(str2);
                            sb.append(charAt2);
                            sb.append(str);
                            sb.append(j);
                            throw new ArrayIndexOutOfBoundsException(sb.toString());
                        }
                    } else {
                        j2 = addressOffset;
                        long j12 = j + 1;
                        UnsafeUtil.putByte(j, (byte) ((charAt2 >>> 6) | 960));
                        long j13 = j12 + 1;
                        UnsafeUtil.putByte(j12, (byte) ((charAt2 & '?') | 128));
                        j = j13;
                    }
                    i++;
                    ByteBuffer byteBuffer3 = byteBuffer;
                    addressOffset = j2;
                    c = 128;
                }
                byteBuffer.position((int) (j - addressOffset));
                return;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str2);
            sb2.append(charSequence2.charAt(length - 1));
            sb2.append(str);
            sb2.append(byteBuffer.limit());
            throw new ArrayIndexOutOfBoundsException(sb2.toString());
        }

        private static int unsafeEstimateConsecutiveAscii(byte[] bArr, long j, int i) {
            if (i < 16) {
                return 0;
            }
            int i2 = ((int) j) & 7;
            long j2 = j;
            int i3 = i2;
            while (i3 > 0) {
                long j3 = 1 + j2;
                if (UnsafeUtil.getByte(bArr, j2) < 0) {
                    return i2 - i3;
                }
                i3--;
                j2 = j3;
            }
            int i4 = i - i2;
            while (i4 >= 8 && (UnsafeUtil.getLong(bArr, j2) & Utf8.ASCII_MASK_LONG) == 0) {
                j2 += 8;
                i4 -= 8;
            }
            return i - i4;
        }

        private static int unsafeEstimateConsecutiveAscii(long j, int i) {
            if (i < 16) {
                return 0;
            }
            int i2 = ((int) j) & 7;
            long j2 = j;
            int i3 = i2;
            while (i3 > 0) {
                long j3 = 1 + j2;
                if (UnsafeUtil.getByte(j2) < 0) {
                    return i2 - i3;
                }
                i3--;
                j2 = j3;
            }
            int i4 = i - i2;
            while (i4 >= 8 && (UnsafeUtil.getLong(j2) & Utf8.ASCII_MASK_LONG) == 0) {
                j2 += 8;
                i4 -= 8;
            }
            return i - i4;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0039, code lost:
            return -1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x0063, code lost:
            return -1;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static int partialIsValidUtf8(byte[] r8, long r9, int r11) {
            /*
                int r0 = unsafeEstimateConsecutiveAscii(r8, r9, r11)
                int r11 = r11 - r0
                long r0 = (long) r0
                long r9 = r9 + r0
            L_0x0007:
                r0 = 0
                r1 = 0
            L_0x0009:
                r2 = 1
                if (r11 <= 0) goto L_0x001a
                long r4 = r9 + r2
                byte r1 = com.google.protobuf.UnsafeUtil.getByte(r8, r9)
                if (r1 < 0) goto L_0x0019
                int r11 = r11 + -1
                r9 = r4
                goto L_0x0009
            L_0x0019:
                r9 = r4
            L_0x001a:
                if (r11 != 0) goto L_0x001d
                return r0
            L_0x001d:
                int r11 = r11 + -1
                r0 = -32
                r4 = -65
                r5 = -1
                if (r1 >= r0) goto L_0x003a
                if (r11 != 0) goto L_0x0029
                return r1
            L_0x0029:
                int r11 = r11 + -1
                r0 = -62
                if (r1 < r0) goto L_0x0039
                long r2 = r2 + r9
                byte r9 = com.google.protobuf.UnsafeUtil.getByte(r8, r9)
                if (r9 <= r4) goto L_0x0037
                goto L_0x0039
            L_0x0037:
                r9 = r2
                goto L_0x0007
            L_0x0039:
                return r5
            L_0x003a:
                r6 = -16
                if (r1 >= r6) goto L_0x0064
                r6 = 2
                if (r11 >= r6) goto L_0x0046
                int r8 = unsafeIncompleteStateFor(r8, r1, r9, r11)
                return r8
            L_0x0046:
                int r11 = r11 + -2
                long r6 = r9 + r2
                byte r9 = com.google.protobuf.UnsafeUtil.getByte(r8, r9)
                if (r9 > r4) goto L_0x0063
                r10 = -96
                if (r1 != r0) goto L_0x0056
                if (r9 < r10) goto L_0x0063
            L_0x0056:
                r0 = -19
                if (r1 != r0) goto L_0x005c
                if (r9 >= r10) goto L_0x0063
            L_0x005c:
                long r2 = r2 + r6
                byte r9 = com.google.protobuf.UnsafeUtil.getByte(r8, r6)
                if (r9 <= r4) goto L_0x0037
            L_0x0063:
                return r5
            L_0x0064:
                r0 = 3
                if (r11 >= r0) goto L_0x006c
                int r8 = unsafeIncompleteStateFor(r8, r1, r9, r11)
                return r8
            L_0x006c:
                int r11 = r11 + -3
                long r6 = r9 + r2
                byte r9 = com.google.protobuf.UnsafeUtil.getByte(r8, r9)
                if (r9 > r4) goto L_0x008e
                int r10 = r1 << 28
                int r9 = r9 + 112
                int r10 = r10 + r9
                int r9 = r10 >> 30
                if (r9 != 0) goto L_0x008e
                long r9 = r6 + r2
                byte r0 = com.google.protobuf.UnsafeUtil.getByte(r8, r6)
                if (r0 > r4) goto L_0x008e
                long r2 = r2 + r9
                byte r9 = com.google.protobuf.UnsafeUtil.getByte(r8, r9)
                if (r9 <= r4) goto L_0x0037
            L_0x008e:
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.Utf8.UnsafeProcessor.partialIsValidUtf8(byte[], long, int):int");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0039, code lost:
            return -1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x0063, code lost:
            return -1;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static int partialIsValidUtf8(long r8, int r10) {
            /*
                int r0 = unsafeEstimateConsecutiveAscii(r8, r10)
                long r1 = (long) r0
                long r8 = r8 + r1
                int r10 = r10 - r0
            L_0x0007:
                r0 = 0
                r1 = 0
            L_0x0009:
                r2 = 1
                if (r10 <= 0) goto L_0x001a
                long r4 = r8 + r2
                byte r1 = com.google.protobuf.UnsafeUtil.getByte(r8)
                if (r1 < 0) goto L_0x0019
                int r10 = r10 + -1
                r8 = r4
                goto L_0x0009
            L_0x0019:
                r8 = r4
            L_0x001a:
                if (r10 != 0) goto L_0x001d
                return r0
            L_0x001d:
                int r10 = r10 + -1
                r0 = -32
                r4 = -65
                r5 = -1
                if (r1 >= r0) goto L_0x003a
                if (r10 != 0) goto L_0x0029
                return r1
            L_0x0029:
                int r10 = r10 + -1
                r0 = -62
                if (r1 < r0) goto L_0x0039
                long r2 = r2 + r8
                byte r8 = com.google.protobuf.UnsafeUtil.getByte(r8)
                if (r8 <= r4) goto L_0x0037
                goto L_0x0039
            L_0x0037:
                r8 = r2
                goto L_0x0007
            L_0x0039:
                return r5
            L_0x003a:
                r6 = -16
                if (r1 >= r6) goto L_0x0064
                r6 = 2
                if (r10 >= r6) goto L_0x0046
                int r8 = unsafeIncompleteStateFor(r8, r1, r10)
                return r8
            L_0x0046:
                int r10 = r10 + -2
                long r6 = r8 + r2
                byte r8 = com.google.protobuf.UnsafeUtil.getByte(r8)
                if (r8 > r4) goto L_0x0063
                r9 = -96
                if (r1 != r0) goto L_0x0056
                if (r8 < r9) goto L_0x0063
            L_0x0056:
                r0 = -19
                if (r1 != r0) goto L_0x005c
                if (r8 >= r9) goto L_0x0063
            L_0x005c:
                long r2 = r2 + r6
                byte r8 = com.google.protobuf.UnsafeUtil.getByte(r6)
                if (r8 <= r4) goto L_0x0037
            L_0x0063:
                return r5
            L_0x0064:
                r0 = 3
                if (r10 >= r0) goto L_0x006c
                int r8 = unsafeIncompleteStateFor(r8, r1, r10)
                return r8
            L_0x006c:
                int r10 = r10 + -3
                long r6 = r8 + r2
                byte r8 = com.google.protobuf.UnsafeUtil.getByte(r8)
                if (r8 > r4) goto L_0x008e
                int r9 = r1 << 28
                int r8 = r8 + 112
                int r9 = r9 + r8
                int r8 = r9 >> 30
                if (r8 != 0) goto L_0x008e
                long r8 = r6 + r2
                byte r0 = com.google.protobuf.UnsafeUtil.getByte(r6)
                if (r0 > r4) goto L_0x008e
                long r2 = r2 + r8
                byte r8 = com.google.protobuf.UnsafeUtil.getByte(r8)
                if (r8 <= r4) goto L_0x0037
            L_0x008e:
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.Utf8.UnsafeProcessor.partialIsValidUtf8(long, int):int");
        }

        private static int unsafeIncompleteStateFor(byte[] bArr, int i, long j, int i2) {
            if (i2 == 0) {
                return Utf8.incompleteStateFor(i);
            }
            if (i2 == 1) {
                return Utf8.incompleteStateFor(i, UnsafeUtil.getByte(bArr, j));
            }
            if (i2 == 2) {
                return Utf8.incompleteStateFor(i, (int) UnsafeUtil.getByte(bArr, j), (int) UnsafeUtil.getByte(bArr, j + 1));
            }
            throw new AssertionError();
        }

        private static int unsafeIncompleteStateFor(long j, int i, int i2) {
            if (i2 == 0) {
                return Utf8.incompleteStateFor(i);
            }
            if (i2 == 1) {
                return Utf8.incompleteStateFor(i, UnsafeUtil.getByte(j));
            }
            if (i2 == 2) {
                return Utf8.incompleteStateFor(i, (int) UnsafeUtil.getByte(j), (int) UnsafeUtil.getByte(j + 1));
            }
            throw new AssertionError();
        }
    }

    /* access modifiers changed from: private */
    public static int incompleteStateFor(int i) {
        if (i > -12) {
            return -1;
        }
        return i;
    }

    /* access modifiers changed from: private */
    public static int incompleteStateFor(int i, int i2) {
        if (i > -12 || i2 > -65) {
            return -1;
        }
        return i ^ (i2 << 8);
    }

    /* access modifiers changed from: private */
    public static int incompleteStateFor(int i, int i2, int i3) {
        if (i > -12 || i2 > -65 || i3 > -65) {
            return -1;
        }
        return (i ^ (i2 << 8)) ^ (i3 << 16);
    }

    public static boolean isValidUtf8(byte[] bArr) {
        return processor.isValidUtf8(bArr, 0, bArr.length);
    }

    public static boolean isValidUtf8(byte[] bArr, int i, int i2) {
        return processor.isValidUtf8(bArr, i, i2);
    }

    public static int partialIsValidUtf8(int i, byte[] bArr, int i2, int i3) {
        return processor.partialIsValidUtf8(i, bArr, i2, i3);
    }

    /* access modifiers changed from: private */
    public static int incompleteStateFor(byte[] bArr, int i, int i2) {
        byte b = bArr[i - 1];
        int i3 = i2 - i;
        if (i3 == 0) {
            return incompleteStateFor(b);
        }
        if (i3 == 1) {
            return incompleteStateFor(b, bArr[i]);
        }
        if (i3 == 2) {
            return incompleteStateFor((int) b, (int) bArr[i], (int) bArr[i + 1]);
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: private */
    public static int incompleteStateFor(ByteBuffer byteBuffer, int i, int i2, int i3) {
        if (i3 == 0) {
            return incompleteStateFor(i);
        }
        if (i3 == 1) {
            return incompleteStateFor(i, byteBuffer.get(i2));
        }
        if (i3 == 2) {
            return incompleteStateFor(i, (int) byteBuffer.get(i2), (int) byteBuffer.get(i2 + 1));
        }
        throw new AssertionError();
    }

    static int encodedLength(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        while (i < length && charSequence.charAt(i) < 128) {
            i++;
        }
        int i2 = length;
        while (true) {
            if (i >= length) {
                break;
            }
            char charAt = charSequence.charAt(i);
            if (charAt >= 2048) {
                i2 += encodedLengthGeneral(charSequence, i);
                break;
            }
            i2 += (127 - charAt) >>> 31;
            i++;
        }
        if (i2 >= length) {
            return i2;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("UTF-8 length does not fit in int: ");
        sb.append(((long) i2) + 4294967296L);
        throw new IllegalArgumentException(sb.toString());
    }

    private static int encodedLengthGeneral(CharSequence charSequence, int i) {
        int length = charSequence.length();
        int i2 = 0;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (charAt < 2048) {
                i2 += (127 - charAt) >>> 31;
            } else {
                i2 += 2;
                if (55296 <= charAt && charAt <= 57343) {
                    if (Character.codePointAt(charSequence, i) >= 65536) {
                        i++;
                    } else {
                        throw new UnpairedSurrogateException(i, length);
                    }
                }
            }
            i++;
        }
        return i2;
    }

    static int encode(CharSequence charSequence, byte[] bArr, int i, int i2) {
        return processor.encodeUtf8(charSequence, bArr, i, i2);
    }

    static boolean isValidUtf8(ByteBuffer byteBuffer) {
        return processor.isValidUtf8(byteBuffer, byteBuffer.position(), byteBuffer.remaining());
    }

    static int partialIsValidUtf8(int i, ByteBuffer byteBuffer, int i2, int i3) {
        return processor.partialIsValidUtf8(i, byteBuffer, i2, i3);
    }

    static void encodeUtf8(CharSequence charSequence, ByteBuffer byteBuffer) {
        processor.encodeUtf8(charSequence, byteBuffer);
    }

    /* access modifiers changed from: private */
    public static int estimateConsecutiveAscii(ByteBuffer byteBuffer, int i, int i2) {
        int i3 = i2 - 7;
        int i4 = i;
        while (i4 < i3 && (byteBuffer.getLong(i4) & ASCII_MASK_LONG) == 0) {
            i4 += 8;
        }
        return i4 - i;
    }

    private Utf8() {
    }
}

package p043io.opencensus.trace;

import java.util.Arrays;
import p043io.opencensus.internal.C5887Utils;

/* renamed from: io.opencensus.trace.BigendianEncoding */
final class BigendianEncoding {
    private static final String ALPHABET = "0123456789abcdef";
    private static final int ASCII_CHARACTERS = 128;
    static final int BYTE_BASE16 = 2;
    private static final byte[] DECODING = buildDecodingArray();
    private static final char[] ENCODING = buildEncodingArray();
    static final int LONG_BASE16 = 16;
    static final int LONG_BYTES = 8;

    private static char[] buildEncodingArray() {
        char[] cArr = new char[512];
        for (int i = 0; i < 256; i++) {
            int i2 = i >>> 4;
            String str = ALPHABET;
            cArr[i] = str.charAt(i2);
            cArr[i | 256] = str.charAt(i & 15);
        }
        return cArr;
    }

    private static byte[] buildDecodingArray() {
        byte[] bArr = new byte[128];
        Arrays.fill(bArr, -1);
        for (int i = 0; i < 16; i++) {
            bArr[ALPHABET.charAt(i)] = (byte) i;
        }
        return bArr;
    }

    static long longFromByteArray(byte[] bArr, int i) {
        C5887Utils.checkArgument(bArr.length >= i + 8, "array too small");
        return (((long) bArr[i + 7]) & 255) | ((((long) bArr[i]) & 255) << 56) | ((((long) bArr[i + 1]) & 255) << 48) | ((((long) bArr[i + 2]) & 255) << 40) | ((((long) bArr[i + 3]) & 255) << 32) | ((((long) bArr[i + 4]) & 255) << 24) | ((((long) bArr[i + 5]) & 255) << 16) | ((((long) bArr[i + 6]) & 255) << 8);
    }

    static void longToByteArray(long j, byte[] bArr, int i) {
        C5887Utils.checkArgument(bArr.length >= i + 8, "array too small");
        bArr[i + 7] = (byte) ((int) (j & 255));
        bArr[i + 6] = (byte) ((int) ((j >> 8) & 255));
        bArr[i + 5] = (byte) ((int) ((j >> 16) & 255));
        bArr[i + 4] = (byte) ((int) ((j >> 24) & 255));
        bArr[i + 3] = (byte) ((int) ((j >> 32) & 255));
        bArr[i + 2] = (byte) ((int) ((j >> 40) & 255));
        bArr[i + 1] = (byte) ((int) ((j >> 48) & 255));
        bArr[i] = (byte) ((int) ((j >> 56) & 255));
    }

    static long longFromBase16String(CharSequence charSequence, int i) {
        C5887Utils.checkArgument(charSequence.length() >= i + 16, "chars too small");
        return (((long) decodeByte(charSequence.charAt(i + 14), charSequence.charAt(i + 15))) & 255) | ((((long) decodeByte(charSequence.charAt(i), charSequence.charAt(i + 1))) & 255) << 56) | ((((long) decodeByte(charSequence.charAt(i + 2), charSequence.charAt(i + 3))) & 255) << 48) | ((((long) decodeByte(charSequence.charAt(i + 4), charSequence.charAt(i + 5))) & 255) << 40) | ((((long) decodeByte(charSequence.charAt(i + 6), charSequence.charAt(i + 7))) & 255) << 32) | ((((long) decodeByte(charSequence.charAt(i + 8), charSequence.charAt(i + 9))) & 255) << 24) | ((((long) decodeByte(charSequence.charAt(i + 10), charSequence.charAt(i + 11))) & 255) << 16) | ((((long) decodeByte(charSequence.charAt(i + 12), charSequence.charAt(i + 13))) & 255) << 8);
    }

    static void longToBase16String(long j, char[] cArr, int i) {
        byteToBase16((byte) ((int) ((j >> 56) & 255)), cArr, i);
        byteToBase16((byte) ((int) ((j >> 48) & 255)), cArr, i + 2);
        byteToBase16((byte) ((int) ((j >> 40) & 255)), cArr, i + 4);
        byteToBase16((byte) ((int) ((j >> 32) & 255)), cArr, i + 6);
        byteToBase16((byte) ((int) ((j >> 24) & 255)), cArr, i + 8);
        byteToBase16((byte) ((int) ((j >> 16) & 255)), cArr, i + 10);
        byteToBase16((byte) ((int) ((j >> 8) & 255)), cArr, i + 12);
        byteToBase16((byte) ((int) (j & 255)), cArr, i + 14);
    }

    static void byteToBase16String(byte b, char[] cArr, int i) {
        byteToBase16(b, cArr, i);
    }

    static byte byteFromBase16String(CharSequence charSequence, int i) {
        C5887Utils.checkArgument(charSequence.length() >= i + 2, "chars too small");
        return decodeByte(charSequence.charAt(i), charSequence.charAt(i + 1));
    }

    private static byte decodeByte(char c, char c2) {
        boolean z = true;
        boolean z2 = c2 < 128 && DECODING[c2] != -1;
        StringBuilder sb = new StringBuilder();
        String str = "invalid character ";
        sb.append(str);
        sb.append(c2);
        C5887Utils.checkArgument(z2, sb.toString());
        if (c >= 128 || DECODING[c] == -1) {
            z = false;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(c);
        C5887Utils.checkArgument(z, sb2.toString());
        byte[] bArr = DECODING;
        return (byte) ((bArr[c] << 4) | bArr[c2]);
    }

    private static void byteToBase16(byte b, char[] cArr, int i) {
        byte b2 = b & 255;
        char[] cArr2 = ENCODING;
        cArr[i] = cArr2[b2];
        cArr[i + 1] = cArr2[b2 | 256];
    }

    private BigendianEncoding() {
    }
}

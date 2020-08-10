package org.apache.commons.codec.binary;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.apache.commons.codec.Charsets;

public class StringUtils {
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002e, code lost:
        if (org.apache.commons.codec.binary.CharSequenceUtils.regionMatches(r10, false, 0, r11, 0, r10.length()) != false) goto L_0x0032;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean equals(java.lang.CharSequence r10, java.lang.CharSequence r11) {
        /*
            r0 = 1
            if (r10 != r11) goto L_0x0004
            return r0
        L_0x0004:
            r1 = 0
            if (r10 == 0) goto L_0x0033
            if (r11 != 0) goto L_0x000a
            goto L_0x0033
        L_0x000a:
            boolean r2 = r10 instanceof java.lang.String
            if (r2 == 0) goto L_0x0017
            boolean r2 = r11 instanceof java.lang.String
            if (r2 == 0) goto L_0x0017
            boolean r10 = r10.equals(r11)
            return r10
        L_0x0017:
            int r2 = r10.length()
            int r3 = r11.length()
            if (r2 != r3) goto L_0x0031
            r5 = 0
            r6 = 0
            r8 = 0
            int r9 = r10.length()
            r4 = r10
            r7 = r11
            boolean r10 = org.apache.commons.codec.binary.CharSequenceUtils.regionMatches(r4, r5, r6, r7, r8, r9)
            if (r10 == 0) goto L_0x0031
            goto L_0x0032
        L_0x0031:
            r0 = 0
        L_0x0032:
            return r0
        L_0x0033:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.binary.StringUtils.equals(java.lang.CharSequence, java.lang.CharSequence):boolean");
    }

    private static byte[] getBytes(String str, Charset charset) {
        if (str == null) {
            return null;
        }
        return str.getBytes(charset);
    }

    private static ByteBuffer getByteBuffer(String str, Charset charset) {
        if (str == null) {
            return null;
        }
        return ByteBuffer.wrap(str.getBytes(charset));
    }

    public static ByteBuffer getByteBufferUtf8(String str) {
        return getByteBuffer(str, Charsets.UTF_8);
    }

    public static byte[] getBytesIso8859_1(String str) {
        return getBytes(str, Charsets.ISO_8859_1);
    }

    public static byte[] getBytesUnchecked(String str, String str2) {
        if (str == null) {
            return null;
        }
        try {
            return str.getBytes(str2);
        } catch (UnsupportedEncodingException e) {
            throw newIllegalStateException(str2, e);
        }
    }

    public static byte[] getBytesUsAscii(String str) {
        return getBytes(str, Charsets.US_ASCII);
    }

    public static byte[] getBytesUtf16(String str) {
        return getBytes(str, Charsets.UTF_16);
    }

    public static byte[] getBytesUtf16Be(String str) {
        return getBytes(str, Charsets.UTF_16BE);
    }

    public static byte[] getBytesUtf16Le(String str) {
        return getBytes(str, Charsets.UTF_16LE);
    }

    public static byte[] getBytesUtf8(String str) {
        return getBytes(str, Charsets.UTF_8);
    }

    private static IllegalStateException newIllegalStateException(String str, UnsupportedEncodingException unsupportedEncodingException) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(": ");
        sb.append(unsupportedEncodingException);
        return new IllegalStateException(sb.toString());
    }

    private static String newString(byte[] bArr, Charset charset) {
        if (bArr == null) {
            return null;
        }
        return new String(bArr, charset);
    }

    public static String newString(byte[] bArr, String str) {
        if (bArr == null) {
            return null;
        }
        try {
            return new String(bArr, str);
        } catch (UnsupportedEncodingException e) {
            throw newIllegalStateException(str, e);
        }
    }

    public static String newStringIso8859_1(byte[] bArr) {
        return newString(bArr, Charsets.ISO_8859_1);
    }

    public static String newStringUsAscii(byte[] bArr) {
        return newString(bArr, Charsets.US_ASCII);
    }

    public static String newStringUtf16(byte[] bArr) {
        return newString(bArr, Charsets.UTF_16);
    }

    public static String newStringUtf16Be(byte[] bArr) {
        return newString(bArr, Charsets.UTF_16BE);
    }

    public static String newStringUtf16Le(byte[] bArr) {
        return newString(bArr, Charsets.UTF_16LE);
    }

    public static String newStringUtf8(byte[] bArr) {
        return newString(bArr, Charsets.UTF_8);
    }
}

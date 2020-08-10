package org.apache.commons.codec.net;

import org.apache.commons.codec.DecoderException;

/* renamed from: org.apache.commons.codec.net.Utils */
class C6065Utils {
    private static final int RADIX = 16;

    C6065Utils() {
    }

    static int digit16(byte b) throws DecoderException {
        int digit = Character.digit((char) b, 16);
        if (digit != -1) {
            return digit;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Invalid URL encoding: not a valid digit (radix 16): ");
        sb.append(b);
        throw new DecoderException(sb.toString());
    }

    static char hexDigit(int i) {
        return Character.toUpperCase(Character.forDigit(i & 15, 16));
    }
}

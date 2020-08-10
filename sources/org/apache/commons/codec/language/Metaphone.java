package org.apache.commons.codec.language;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

public class Metaphone implements StringEncoder {
    private static final String FRONTV = "EIY";
    private static final String VARSON = "CSPTG";
    private static final String VOWELS = "AEIOU";
    private int maxCodeLen = 4;

    private boolean isLastChar(int i, int i2) {
        return i2 + 1 == i;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:86:0x017d, code lost:
        if (VARSON.indexOf(r2.charAt(r5 - 1)) >= 0) goto L_0x0294;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String metaphone(java.lang.String r17) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            if (r1 == 0) goto L_0x02ae
            int r2 = r17.length()
            if (r2 != 0) goto L_0x000e
            goto L_0x02ae
        L_0x000e:
            r3 = 1
            if (r2 != r3) goto L_0x0018
            java.util.Locale r2 = java.util.Locale.ENGLISH
            java.lang.String r1 = r1.toUpperCase(r2)
            return r1
        L_0x0018:
            java.util.Locale r2 = java.util.Locale.ENGLISH
            java.lang.String r1 = r1.toUpperCase(r2)
            char[] r1 = r1.toCharArray()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r4 = 40
            r2.<init>(r4)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r5 = 10
            r4.<init>(r5)
            r5 = 0
            char r6 = r1[r5]
            r7 = 65
            r8 = 71
            r9 = 88
            r10 = 72
            r11 = 83
            r12 = 75
            if (r6 == r7) goto L_0x0086
            if (r6 == r8) goto L_0x0076
            if (r6 == r12) goto L_0x0076
            r7 = 80
            if (r6 == r7) goto L_0x0076
            r7 = 87
            if (r6 == r7) goto L_0x0059
            if (r6 == r9) goto L_0x0053
            r2.append(r1)
            goto L_0x0095
        L_0x0053:
            r1[r5] = r11
            r2.append(r1)
            goto L_0x0095
        L_0x0059:
            char r6 = r1[r3]
            r13 = 82
            if (r6 != r13) goto L_0x0065
            int r6 = r1.length
            int r6 = r6 - r3
            r2.append(r1, r3, r6)
            goto L_0x0095
        L_0x0065:
            char r6 = r1[r3]
            if (r6 != r10) goto L_0x0072
            int r6 = r1.length
            int r6 = r6 - r3
            r2.append(r1, r3, r6)
            r2.setCharAt(r5, r7)
            goto L_0x0095
        L_0x0072:
            r2.append(r1)
            goto L_0x0095
        L_0x0076:
            char r6 = r1[r3]
            r7 = 78
            if (r6 != r7) goto L_0x0082
            int r6 = r1.length
            int r6 = r6 - r3
            r2.append(r1, r3, r6)
            goto L_0x0095
        L_0x0082:
            r2.append(r1)
            goto L_0x0095
        L_0x0086:
            char r6 = r1[r3]
            r7 = 69
            if (r6 != r7) goto L_0x0092
            int r6 = r1.length
            int r6 = r6 - r3
            r2.append(r1, r3, r6)
            goto L_0x0095
        L_0x0092:
            r2.append(r1)
        L_0x0095:
            int r1 = r2.length()
        L_0x0099:
            int r6 = r4.length()
            int r7 = r16.getMaxCodeLen()
            if (r6 >= r7) goto L_0x02a9
            if (r5 >= r1) goto L_0x02a9
            char r6 = r2.charAt(r5)
            r7 = 67
            if (r6 == r7) goto L_0x00b7
            boolean r13 = r0.isPreviousChar(r2, r5, r6)
            if (r13 == 0) goto L_0x00b7
            int r5 = r5 + 1
            goto L_0x0296
        L_0x00b7:
            r13 = 74
            r14 = 84
            r15 = 70
            java.lang.String r3 = "EIY"
            switch(r6) {
                case 65: goto L_0x028f;
                case 66: goto L_0x027c;
                case 67: goto L_0x0212;
                case 68: goto L_0x01ed;
                case 69: goto L_0x028f;
                case 70: goto L_0x01e8;
                case 71: goto L_0x018e;
                case 72: goto L_0x0167;
                case 73: goto L_0x028f;
                case 74: goto L_0x01e8;
                case 75: goto L_0x0155;
                case 76: goto L_0x01e8;
                case 77: goto L_0x01e8;
                case 78: goto L_0x01e8;
                case 79: goto L_0x028f;
                case 80: goto L_0x0145;
                case 81: goto L_0x0140;
                case 82: goto L_0x01e8;
                case 83: goto L_0x011d;
                case 84: goto L_0x00e9;
                case 85: goto L_0x028f;
                case 86: goto L_0x00e4;
                case 87: goto L_0x00d1;
                case 88: goto L_0x00c9;
                case 89: goto L_0x00d1;
                case 90: goto L_0x00c4;
                default: goto L_0x00c2;
            }
        L_0x00c2:
            goto L_0x0294
        L_0x00c4:
            r4.append(r11)
            goto L_0x0294
        L_0x00c9:
            r4.append(r12)
            r4.append(r11)
            goto L_0x0294
        L_0x00d1:
            boolean r3 = r0.isLastChar(r1, r5)
            if (r3 != 0) goto L_0x0294
            int r3 = r5 + 1
            boolean r3 = r0.isVowel(r2, r3)
            if (r3 == 0) goto L_0x0294
            r4.append(r6)
            goto L_0x0294
        L_0x00e4:
            r4.append(r15)
            goto L_0x0294
        L_0x00e9:
            java.lang.String r3 = "TIA"
            boolean r3 = r0.regionMatch(r2, r5, r3)
            if (r3 != 0) goto L_0x0118
            java.lang.String r3 = "TIO"
            boolean r3 = r0.regionMatch(r2, r5, r3)
            if (r3 == 0) goto L_0x00fa
            goto L_0x0118
        L_0x00fa:
            java.lang.String r3 = "TCH"
            boolean r3 = r0.regionMatch(r2, r5, r3)
            if (r3 == 0) goto L_0x0104
            goto L_0x0294
        L_0x0104:
            java.lang.String r3 = "TH"
            boolean r3 = r0.regionMatch(r2, r5, r3)
            if (r3 == 0) goto L_0x0113
            r3 = 48
            r4.append(r3)
            goto L_0x0294
        L_0x0113:
            r4.append(r14)
            goto L_0x0294
        L_0x0118:
            r4.append(r9)
            goto L_0x0294
        L_0x011d:
            java.lang.String r3 = "SH"
            boolean r3 = r0.regionMatch(r2, r5, r3)
            if (r3 != 0) goto L_0x013b
            java.lang.String r3 = "SIO"
            boolean r3 = r0.regionMatch(r2, r5, r3)
            if (r3 != 0) goto L_0x013b
            java.lang.String r3 = "SIA"
            boolean r3 = r0.regionMatch(r2, r5, r3)
            if (r3 == 0) goto L_0x0136
            goto L_0x013b
        L_0x0136:
            r4.append(r11)
            goto L_0x0294
        L_0x013b:
            r4.append(r9)
            goto L_0x0294
        L_0x0140:
            r4.append(r12)
            goto L_0x0294
        L_0x0145:
            boolean r3 = r0.isNextChar(r2, r5, r10)
            if (r3 == 0) goto L_0x0150
            r4.append(r15)
            goto L_0x0294
        L_0x0150:
            r4.append(r6)
            goto L_0x0294
        L_0x0155:
            if (r5 <= 0) goto L_0x0162
            boolean r3 = r0.isPreviousChar(r2, r5, r7)
            if (r3 != 0) goto L_0x0294
            r4.append(r6)
            goto L_0x0294
        L_0x0162:
            r4.append(r6)
            goto L_0x0294
        L_0x0167:
            boolean r3 = r0.isLastChar(r1, r5)
            if (r3 == 0) goto L_0x016f
            goto L_0x0294
        L_0x016f:
            if (r5 <= 0) goto L_0x0181
            int r3 = r5 + -1
            char r3 = r2.charAt(r3)
            java.lang.String r6 = "CSPTG"
            int r3 = r6.indexOf(r3)
            if (r3 < 0) goto L_0x0181
            goto L_0x0294
        L_0x0181:
            int r3 = r5 + 1
            boolean r3 = r0.isVowel(r2, r3)
            if (r3 == 0) goto L_0x0294
            r4.append(r10)
            goto L_0x0294
        L_0x018e:
            int r6 = r5 + 1
            boolean r7 = r0.isLastChar(r1, r6)
            if (r7 == 0) goto L_0x019e
            boolean r7 = r0.isNextChar(r2, r5, r10)
            if (r7 == 0) goto L_0x019e
            goto L_0x0294
        L_0x019e:
            boolean r7 = r0.isLastChar(r1, r6)
            if (r7 != 0) goto L_0x01b4
            boolean r7 = r0.isNextChar(r2, r5, r10)
            if (r7 == 0) goto L_0x01b4
            int r7 = r5 + 2
            boolean r7 = r0.isVowel(r2, r7)
            if (r7 != 0) goto L_0x01b4
            goto L_0x0294
        L_0x01b4:
            if (r5 <= 0) goto L_0x01c8
            java.lang.String r7 = "GN"
            boolean r7 = r0.regionMatch(r2, r5, r7)
            if (r7 != 0) goto L_0x0294
            java.lang.String r7 = "GNED"
            boolean r7 = r0.regionMatch(r2, r5, r7)
            if (r7 == 0) goto L_0x01c8
            goto L_0x0294
        L_0x01c8:
            boolean r7 = r0.isPreviousChar(r2, r5, r8)
            boolean r14 = r0.isLastChar(r1, r5)
            if (r14 != 0) goto L_0x01e3
            char r6 = r2.charAt(r6)
            int r3 = r3.indexOf(r6)
            if (r3 < 0) goto L_0x01e3
            if (r7 != 0) goto L_0x01e3
            r4.append(r13)
            goto L_0x0294
        L_0x01e3:
            r4.append(r12)
            goto L_0x0294
        L_0x01e8:
            r4.append(r6)
            goto L_0x0294
        L_0x01ed:
            int r6 = r5 + 1
            boolean r6 = r0.isLastChar(r1, r6)
            if (r6 != 0) goto L_0x020d
            boolean r6 = r0.isNextChar(r2, r5, r8)
            if (r6 == 0) goto L_0x020d
            int r6 = r5 + 2
            char r7 = r2.charAt(r6)
            int r3 = r3.indexOf(r7)
            if (r3 < 0) goto L_0x020d
            r4.append(r13)
            r5 = r6
            goto L_0x0294
        L_0x020d:
            r4.append(r14)
            goto L_0x0294
        L_0x0212:
            boolean r6 = r0.isPreviousChar(r2, r5, r11)
            if (r6 == 0) goto L_0x022c
            boolean r6 = r0.isLastChar(r1, r5)
            if (r6 != 0) goto L_0x022c
            int r6 = r5 + 1
            char r6 = r2.charAt(r6)
            int r6 = r3.indexOf(r6)
            if (r6 < 0) goto L_0x022c
            goto L_0x0294
        L_0x022c:
            java.lang.String r6 = "CIA"
            boolean r6 = r0.regionMatch(r2, r5, r6)
            if (r6 == 0) goto L_0x0238
            r4.append(r9)
            goto L_0x0294
        L_0x0238:
            boolean r6 = r0.isLastChar(r1, r5)
            if (r6 != 0) goto L_0x024e
            int r6 = r5 + 1
            char r6 = r2.charAt(r6)
            int r3 = r3.indexOf(r6)
            if (r3 < 0) goto L_0x024e
            r4.append(r11)
            goto L_0x0294
        L_0x024e:
            boolean r3 = r0.isPreviousChar(r2, r5, r11)
            if (r3 == 0) goto L_0x025e
            boolean r3 = r0.isNextChar(r2, r5, r10)
            if (r3 == 0) goto L_0x025e
            r4.append(r12)
            goto L_0x0294
        L_0x025e:
            boolean r3 = r0.isNextChar(r2, r5, r10)
            if (r3 == 0) goto L_0x0278
            if (r5 != 0) goto L_0x0274
            r3 = 3
            if (r1 < r3) goto L_0x0274
            r3 = 2
            boolean r3 = r0.isVowel(r2, r3)
            if (r3 == 0) goto L_0x0274
            r4.append(r12)
            goto L_0x0294
        L_0x0274:
            r4.append(r9)
            goto L_0x0294
        L_0x0278:
            r4.append(r12)
            goto L_0x0294
        L_0x027c:
            r3 = 77
            boolean r3 = r0.isPreviousChar(r2, r5, r3)
            if (r3 == 0) goto L_0x028b
            boolean r3 = r0.isLastChar(r1, r5)
            if (r3 == 0) goto L_0x028b
            goto L_0x0294
        L_0x028b:
            r4.append(r6)
            goto L_0x0294
        L_0x028f:
            if (r5 != 0) goto L_0x0294
            r4.append(r6)
        L_0x0294:
            r3 = 1
            int r5 = r5 + r3
        L_0x0296:
            int r6 = r4.length()
            int r7 = r16.getMaxCodeLen()
            if (r6 <= r7) goto L_0x0099
            int r6 = r16.getMaxCodeLen()
            r4.setLength(r6)
            goto L_0x0099
        L_0x02a9:
            java.lang.String r1 = r4.toString()
            return r1
        L_0x02ae:
            java.lang.String r1 = ""
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.language.Metaphone.metaphone(java.lang.String):java.lang.String");
    }

    private boolean isVowel(StringBuilder sb, int i) {
        return VOWELS.indexOf(sb.charAt(i)) >= 0;
    }

    private boolean isPreviousChar(StringBuilder sb, int i, char c) {
        if (i <= 0 || i >= sb.length() || sb.charAt(i - 1) != c) {
            return false;
        }
        return true;
    }

    private boolean isNextChar(StringBuilder sb, int i, char c) {
        if (i < 0 || i >= sb.length() - 1 || sb.charAt(i + 1) != c) {
            return false;
        }
        return true;
    }

    private boolean regionMatch(StringBuilder sb, int i, String str) {
        if (i < 0 || (str.length() + i) - 1 >= sb.length()) {
            return false;
        }
        return sb.substring(i, str.length() + i).equals(str);
    }

    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof String) {
            return metaphone((String) obj);
        }
        throw new EncoderException("Parameter supplied to Metaphone encode is not of type java.lang.String");
    }

    public String encode(String str) {
        return metaphone(str);
    }

    public boolean isMetaphoneEqual(String str, String str2) {
        return metaphone(str).equals(metaphone(str2));
    }

    public int getMaxCodeLen() {
        return this.maxCodeLen;
    }

    public void setMaxCodeLen(int i) {
        this.maxCodeLen = i;
    }
}

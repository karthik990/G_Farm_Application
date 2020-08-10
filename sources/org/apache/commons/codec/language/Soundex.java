package org.apache.commons.codec.language;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

public class Soundex implements StringEncoder {
    public static final char SILENT_MARKER = '-';
    public static final Soundex US_ENGLISH = new Soundex();
    public static final Soundex US_ENGLISH_GENEALOGY = new Soundex("-123-12--22455-12623-1-2-2");
    private static final char[] US_ENGLISH_MAPPING;
    public static final String US_ENGLISH_MAPPING_STRING = "01230120022455012623010202";
    public static final Soundex US_ENGLISH_SIMPLIFIED;
    @Deprecated
    private int maxLength;
    private final char[] soundexMapping;
    private final boolean specialCaseHW;

    static {
        String str = US_ENGLISH_MAPPING_STRING;
        US_ENGLISH_MAPPING = str.toCharArray();
        US_ENGLISH_SIMPLIFIED = new Soundex(str, false);
    }

    public Soundex() {
        this.maxLength = 4;
        this.soundexMapping = US_ENGLISH_MAPPING;
        this.specialCaseHW = true;
    }

    public Soundex(char[] cArr) {
        this.maxLength = 4;
        this.soundexMapping = new char[cArr.length];
        System.arraycopy(cArr, 0, this.soundexMapping, 0, cArr.length);
        this.specialCaseHW = !hasMarker(this.soundexMapping);
    }

    private boolean hasMarker(char[] cArr) {
        for (char c : cArr) {
            if (c == '-') {
                return true;
            }
        }
        return false;
    }

    public Soundex(String str) {
        this.maxLength = 4;
        this.soundexMapping = str.toCharArray();
        this.specialCaseHW = !hasMarker(this.soundexMapping);
    }

    public Soundex(String str, boolean z) {
        this.maxLength = 4;
        this.soundexMapping = str.toCharArray();
        this.specialCaseHW = z;
    }

    public int difference(String str, String str2) throws EncoderException {
        return SoundexUtils.difference(this, str, str2);
    }

    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof String) {
            return soundex((String) obj);
        }
        throw new EncoderException("Parameter supplied to Soundex encode is not of type java.lang.String");
    }

    public String encode(String str) {
        return soundex(str);
    }

    @Deprecated
    public int getMaxLength() {
        return this.maxLength;
    }

    private char map(char c) {
        int i = c - 'A';
        if (i >= 0) {
            char[] cArr = this.soundexMapping;
            if (i < cArr.length) {
                return cArr[i];
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("The character is not mapped: ");
        sb.append(c);
        sb.append(" (index=");
        sb.append(i);
        sb.append(")");
        throw new IllegalArgumentException(sb.toString());
    }

    @Deprecated
    public void setMaxLength(int i) {
        this.maxLength = i;
    }

    public String soundex(String str) {
        if (str == null) {
            return null;
        }
        String clean = SoundexUtils.clean(str);
        if (clean.length() == 0) {
            return clean;
        }
        char[] cArr = {'0', '0', '0', '0'};
        char charAt = clean.charAt(0);
        cArr[0] = charAt;
        char map = map(charAt);
        char c = map;
        int i = 1;
        for (int i2 = 1; i2 < clean.length() && i < cArr.length; i2++) {
            char charAt2 = clean.charAt(i2);
            if (!this.specialCaseHW || !(charAt2 == 'H' || charAt2 == 'W')) {
                char map2 = map(charAt2);
                if (map2 != '-') {
                    if (!(map2 == '0' || map2 == c)) {
                        int i3 = i + 1;
                        cArr[i] = map2;
                        i = i3;
                    }
                    c = map2;
                }
            }
        }
        return new String(cArr);
    }
}

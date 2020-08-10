package p043io.netty.util.internal;

import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.mobiroller.constants.Constants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* renamed from: io.netty.util.internal.StringUtil */
public final class StringUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String[] BYTE2HEX_NOPAD = new String[256];
    private static final String[] BYTE2HEX_PAD = new String[256];
    public static final char CARRIAGE_RETURN = '\r';
    public static final char COMMA = ',';
    private static final int CSV_NUMBER_ESCAPE_CHARACTERS = 7;
    public static final char DOUBLE_QUOTE = '\"';
    public static final String EMPTY_STRING = "";
    public static final char LINE_FEED = '\n';
    public static final String NEWLINE = SystemPropertyUtil.get("line.separator", Constants.NEW_LINE);
    private static final char PACKAGE_SEPARATOR_CHAR = '.';
    public static final char SPACE = ' ';
    public static final char TAB = '\t';

    private static boolean isDoubleQuote(char c) {
        return c == '\"';
    }

    private static boolean isOws(char c) {
        return c == ' ' || c == 9;
    }

    public static boolean isSurrogate(char c) {
        return c >= 55296 && c <= 57343;
    }

    static {
        String str;
        int i = 0;
        while (true) {
            str = "0";
            if (i >= 10) {
                break;
            }
            String[] strArr = BYTE2HEX_PAD;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(i);
            strArr[i] = sb.toString();
            BYTE2HEX_NOPAD[i] = String.valueOf(i);
            i++;
        }
        while (i < 16) {
            char c = (char) ((i + 97) - 10);
            String[] strArr2 = BYTE2HEX_PAD;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(c);
            strArr2[i] = sb2.toString();
            BYTE2HEX_NOPAD[i] = String.valueOf(c);
            i++;
        }
        while (i < BYTE2HEX_PAD.length) {
            String hexString = Integer.toHexString(i);
            BYTE2HEX_PAD[i] = hexString;
            BYTE2HEX_NOPAD[i] = hexString;
            i++;
        }
    }

    private StringUtil() {
    }

    public static String substringAfter(String str, char c) {
        int indexOf = str.indexOf(c);
        if (indexOf >= 0) {
            return str.substring(indexOf + 1);
        }
        return null;
    }

    public static boolean commonSuffixOfLength(String str, String str2, int i) {
        return str != null && str2 != null && i >= 0 && str.regionMatches(str.length() - i, str2, str2.length() - i, i);
    }

    public static String byteToHexStringPadded(int i) {
        return BYTE2HEX_PAD[i & 255];
    }

    public static <T extends Appendable> T byteToHexStringPadded(T t, int i) {
        try {
            t.append(byteToHexStringPadded(i));
        } catch (IOException e) {
            PlatformDependent.throwException(e);
        }
        return t;
    }

    public static String toHexStringPadded(byte[] bArr) {
        return toHexStringPadded(bArr, 0, bArr.length);
    }

    public static String toHexStringPadded(byte[] bArr, int i, int i2) {
        return ((StringBuilder) toHexStringPadded(new StringBuilder(i2 << 1), bArr, i, i2)).toString();
    }

    public static <T extends Appendable> T toHexStringPadded(T t, byte[] bArr) {
        return toHexStringPadded(t, bArr, 0, bArr.length);
    }

    public static <T extends Appendable> T toHexStringPadded(T t, byte[] bArr, int i, int i2) {
        int i3 = i2 + i;
        while (i < i3) {
            byteToHexStringPadded(t, bArr[i]);
            i++;
        }
        return t;
    }

    public static String byteToHexString(int i) {
        return BYTE2HEX_NOPAD[i & 255];
    }

    public static <T extends Appendable> T byteToHexString(T t, int i) {
        try {
            t.append(byteToHexString(i));
        } catch (IOException e) {
            PlatformDependent.throwException(e);
        }
        return t;
    }

    public static String toHexString(byte[] bArr) {
        return toHexString(bArr, 0, bArr.length);
    }

    public static String toHexString(byte[] bArr, int i, int i2) {
        return ((StringBuilder) toHexString(new StringBuilder(i2 << 1), bArr, i, i2)).toString();
    }

    public static <T extends Appendable> T toHexString(T t, byte[] bArr) {
        return toHexString(t, bArr, 0, bArr.length);
    }

    public static <T extends Appendable> T toHexString(T t, byte[] bArr, int i, int i2) {
        if (i2 == 0) {
            return t;
        }
        int i3 = i2 + i;
        int i4 = i3 - 1;
        while (i < i4 && bArr[i] == 0) {
            i++;
        }
        int i5 = i + 1;
        byteToHexString(t, bArr[i]);
        toHexStringPadded(t, bArr, i5, i3 - i5);
        return t;
    }

    public static String simpleClassName(Object obj) {
        return obj == null ? "null_object" : simpleClassName(obj.getClass());
    }

    public static String simpleClassName(Class<?> cls) {
        String name = ((Class) ObjectUtil.checkNotNull(cls, "clazz")).getName();
        int lastIndexOf = name.lastIndexOf(46);
        return lastIndexOf > -1 ? name.substring(lastIndexOf + 1) : name;
    }

    public static CharSequence escapeCsv(CharSequence charSequence) {
        return escapeCsv(charSequence, false);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x006a, code lost:
        if (r8 != ',') goto L_0x0097;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.CharSequence escapeCsv(java.lang.CharSequence r13, boolean r14) {
        /*
            java.lang.String r0 = "value"
            java.lang.Object r0 = p043io.netty.util.internal.ObjectUtil.checkNotNull(r13, r0)
            java.lang.CharSequence r0 = (java.lang.CharSequence) r0
            int r0 = r0.length()
            if (r0 != 0) goto L_0x000f
            return r13
        L_0x000f:
            int r1 = r0 + -1
            r2 = 0
            r3 = 1
            if (r14 == 0) goto L_0x0030
            int r14 = indexOfFirstNonOwsChar(r13, r0)
            if (r14 != r0) goto L_0x001e
            java.lang.String r13 = ""
            return r13
        L_0x001e:
            int r4 = indexOfLastNonOwsChar(r13, r14, r0)
            if (r14 > 0) goto L_0x0029
            if (r4 >= r1) goto L_0x0027
            goto L_0x0029
        L_0x0027:
            r1 = 0
            goto L_0x002a
        L_0x0029:
            r1 = 1
        L_0x002a:
            if (r1 == 0) goto L_0x0033
            int r0 = r4 - r14
            int r0 = r0 + r3
            goto L_0x0033
        L_0x0030:
            r4 = r1
            r14 = 0
            r1 = 0
        L_0x0033:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            int r6 = r0 + 7
            r5.<init>(r6)
            char r6 = r13.charAt(r14)
            boolean r6 = isDoubleQuote(r6)
            if (r6 == 0) goto L_0x0052
            char r6 = r13.charAt(r4)
            boolean r6 = isDoubleQuote(r6)
            if (r6 == 0) goto L_0x0052
            if (r0 == r3) goto L_0x0052
            r0 = 1
            goto L_0x0053
        L_0x0052:
            r0 = 0
        L_0x0053:
            r2 = r14
            r6 = 0
            r7 = 0
        L_0x0056:
            if (r2 > r4) goto L_0x009d
            char r8 = r13.charAt(r2)
            r9 = 10
            if (r8 == r9) goto L_0x0096
            r9 = 13
            if (r8 == r9) goto L_0x0096
            r9 = 34
            if (r8 == r9) goto L_0x006d
            r9 = 44
            if (r8 == r9) goto L_0x0096
            goto L_0x0097
        L_0x006d:
            if (r2 == r14) goto L_0x0091
            if (r2 != r4) goto L_0x0072
            goto L_0x0091
        L_0x0072:
            int r10 = r2 + 1
            char r11 = r13.charAt(r10)
            boolean r11 = isDoubleQuote(r11)
            int r12 = r2 + -1
            char r12 = r13.charAt(r12)
            boolean r12 = isDoubleQuote(r12)
            if (r12 != 0) goto L_0x0097
            if (r11 == 0) goto L_0x008c
            if (r10 != r4) goto L_0x0097
        L_0x008c:
            r5.append(r9)
            r6 = 1
            goto L_0x0097
        L_0x0091:
            if (r0 != 0) goto L_0x009a
            r5.append(r9)
        L_0x0096:
            r7 = 1
        L_0x0097:
            r5.append(r8)
        L_0x009a:
            int r2 = r2 + 1
            goto L_0x0056
        L_0x009d:
            if (r6 != 0) goto L_0x00ae
            if (r7 == 0) goto L_0x00a4
            if (r0 != 0) goto L_0x00a4
            goto L_0x00ae
        L_0x00a4:
            if (r1 == 0) goto L_0x00ad
            if (r0 == 0) goto L_0x00ac
            java.lang.StringBuilder r5 = quote(r5)
        L_0x00ac:
            return r5
        L_0x00ad:
            return r13
        L_0x00ae:
            java.lang.StringBuilder r13 = quote(r5)
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.util.internal.StringUtil.escapeCsv(java.lang.CharSequence, boolean):java.lang.CharSequence");
    }

    private static StringBuilder quote(StringBuilder sb) {
        StringBuilder insert = sb.insert(0, '\"');
        insert.append('\"');
        return insert;
    }

    public static CharSequence unescapeCsv(CharSequence charSequence) {
        int length = ((CharSequence) ObjectUtil.checkNotNull(charSequence, Param.VALUE)).length();
        if (length == 0) {
            return charSequence;
        }
        int i = length - 1;
        boolean z = false;
        if (isDoubleQuote(charSequence.charAt(0)) && isDoubleQuote(charSequence.charAt(i)) && length != 1) {
            z = true;
        }
        if (!z) {
            validateCsvFormat(charSequence);
            return charSequence;
        }
        StringBuilder stringBuilder = InternalThreadLocalMap.get().stringBuilder();
        int i2 = 1;
        while (i2 < i) {
            char charAt = charSequence.charAt(i2);
            if (charAt == '\"') {
                int i3 = i2 + 1;
                if (!isDoubleQuote(charSequence.charAt(i3)) || i3 == i) {
                    throw newInvalidEscapedCsvFieldException(charSequence, i2);
                }
                i2 = i3;
            }
            stringBuilder.append(charAt);
            i2++;
        }
        return stringBuilder.toString();
    }

    public static List<CharSequence> unescapeCsvFields(CharSequence charSequence) {
        ArrayList arrayList = new ArrayList(2);
        StringBuilder stringBuilder = InternalThreadLocalMap.get().stringBuilder();
        int length = charSequence.length() - 1;
        int i = 0;
        boolean z = false;
        while (i <= length) {
            char charAt = charSequence.charAt(i);
            if (!z) {
                if (!(charAt == 10 || charAt == 13)) {
                    if (charAt != '\"') {
                        if (charAt != ',') {
                            stringBuilder.append(charAt);
                        } else {
                            arrayList.add(stringBuilder.toString());
                            stringBuilder.setLength(0);
                        }
                    } else if (stringBuilder.length() == 0) {
                        z = true;
                    }
                }
                throw newInvalidEscapedCsvFieldException(charSequence, i);
            } else if (charAt != '\"') {
                stringBuilder.append(charAt);
            } else if (i == length) {
                arrayList.add(stringBuilder.toString());
                return arrayList;
            } else {
                i++;
                char charAt2 = charSequence.charAt(i);
                if (charAt2 == '\"') {
                    stringBuilder.append('\"');
                } else if (charAt2 == ',') {
                    arrayList.add(stringBuilder.toString());
                    stringBuilder.setLength(0);
                    z = false;
                } else {
                    throw newInvalidEscapedCsvFieldException(charSequence, i - 1);
                }
            }
            i++;
        }
        if (!z) {
            arrayList.add(stringBuilder.toString());
            return arrayList;
        }
        throw newInvalidEscapedCsvFieldException(charSequence, length);
    }

    private static void validateCsvFormat(CharSequence charSequence) {
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            char charAt = charSequence.charAt(i);
            if (charAt == 10 || charAt == 13 || charAt == '\"' || charAt == ',') {
                throw newInvalidEscapedCsvFieldException(charSequence, i);
            }
        }
    }

    private static IllegalArgumentException newInvalidEscapedCsvFieldException(CharSequence charSequence, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("invalid escaped CSV field: ");
        sb.append(charSequence);
        sb.append(" index: ");
        sb.append(i);
        return new IllegalArgumentException(sb.toString());
    }

    public static int length(String str) {
        if (str == null) {
            return 0;
        }
        return str.length();
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static int indexOfNonWhiteSpace(CharSequence charSequence, int i) {
        while (i < charSequence.length()) {
            if (!Character.isWhitespace(charSequence.charAt(i))) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static boolean endsWith(CharSequence charSequence, char c) {
        int length = charSequence.length();
        if (length <= 0 || charSequence.charAt(length - 1) != c) {
            return false;
        }
        return true;
    }

    public static CharSequence trimOws(CharSequence charSequence) {
        int length = charSequence.length();
        if (length == 0) {
            return charSequence;
        }
        int indexOfFirstNonOwsChar = indexOfFirstNonOwsChar(charSequence, length);
        int indexOfLastNonOwsChar = indexOfLastNonOwsChar(charSequence, indexOfFirstNonOwsChar, length);
        if (!(indexOfFirstNonOwsChar == 0 && indexOfLastNonOwsChar == length - 1)) {
            charSequence = charSequence.subSequence(indexOfFirstNonOwsChar, indexOfLastNonOwsChar + 1);
        }
        return charSequence;
    }

    private static int indexOfFirstNonOwsChar(CharSequence charSequence, int i) {
        int i2 = 0;
        while (i2 < i && isOws(charSequence.charAt(i2))) {
            i2++;
        }
        return i2;
    }

    private static int indexOfLastNonOwsChar(CharSequence charSequence, int i, int i2) {
        int i3 = i2 - 1;
        while (i3 > i && isOws(charSequence.charAt(i3))) {
            i3--;
        }
        return i3;
    }
}

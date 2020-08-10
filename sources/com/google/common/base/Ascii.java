package com.google.common.base;

public final class Ascii {
    public static final byte ACK = 6;
    public static final byte BEL = 7;

    /* renamed from: BS */
    public static final byte f1865BS = 8;
    public static final byte CAN = 24;
    private static final char CASE_MASK = ' ';

    /* renamed from: CR */
    public static final byte f1866CR = 13;
    public static final byte DC1 = 17;
    public static final byte DC2 = 18;
    public static final byte DC3 = 19;
    public static final byte DC4 = 20;
    public static final byte DEL = Byte.MAX_VALUE;
    public static final byte DLE = 16;

    /* renamed from: EM */
    public static final byte f1867EM = 25;
    public static final byte ENQ = 5;
    public static final byte EOT = 4;
    public static final byte ESC = 27;
    public static final byte ETB = 23;
    public static final byte ETX = 3;

    /* renamed from: FF */
    public static final byte f1868FF = 12;

    /* renamed from: FS */
    public static final byte f1869FS = 28;

    /* renamed from: GS */
    public static final byte f1870GS = 29;

    /* renamed from: HT */
    public static final byte f1871HT = 9;

    /* renamed from: LF */
    public static final byte f1872LF = 10;
    public static final char MAX = '';
    public static final char MIN = '\u0000';
    public static final byte NAK = 21;

    /* renamed from: NL */
    public static final byte f1873NL = 10;
    public static final byte NUL = 0;

    /* renamed from: RS */
    public static final byte f1874RS = 30;

    /* renamed from: SI */
    public static final byte f1875SI = 15;

    /* renamed from: SO */
    public static final byte f1876SO = 14;
    public static final byte SOH = 1;

    /* renamed from: SP */
    public static final byte f1877SP = 32;
    public static final byte SPACE = 32;
    public static final byte STX = 2;
    public static final byte SUB = 26;
    public static final byte SYN = 22;

    /* renamed from: US */
    public static final byte f1878US = 31;

    /* renamed from: VT */
    public static final byte f1879VT = 11;
    public static final byte XOFF = 19;
    public static final byte XON = 17;

    private static int getAlphaIndex(char c) {
        return (char) ((c | ' ') - 'a');
    }

    public static boolean isLowerCase(char c) {
        return c >= 'a' && c <= 'z';
    }

    public static boolean isUpperCase(char c) {
        return c >= 'A' && c <= 'Z';
    }

    private Ascii() {
    }

    public static String toLowerCase(String str) {
        int length = str.length();
        int i = 0;
        while (i < length) {
            if (isUpperCase(str.charAt(i))) {
                char[] charArray = str.toCharArray();
                while (i < length) {
                    char c = charArray[i];
                    if (isUpperCase(c)) {
                        charArray[i] = (char) (c ^ ' ');
                    }
                    i++;
                }
                return String.valueOf(charArray);
            }
            i++;
        }
        return str;
    }

    public static String toLowerCase(CharSequence charSequence) {
        if (charSequence instanceof String) {
            return toLowerCase((String) charSequence);
        }
        char[] cArr = new char[charSequence.length()];
        for (int i = 0; i < cArr.length; i++) {
            cArr[i] = toLowerCase(charSequence.charAt(i));
        }
        return String.valueOf(cArr);
    }

    public static char toLowerCase(char c) {
        return isUpperCase(c) ? (char) (c ^ ' ') : c;
    }

    public static String toUpperCase(String str) {
        int length = str.length();
        int i = 0;
        while (i < length) {
            if (isLowerCase(str.charAt(i))) {
                char[] charArray = str.toCharArray();
                while (i < length) {
                    char c = charArray[i];
                    if (isLowerCase(c)) {
                        charArray[i] = (char) (c ^ ' ');
                    }
                    i++;
                }
                return String.valueOf(charArray);
            }
            i++;
        }
        return str;
    }

    public static String toUpperCase(CharSequence charSequence) {
        if (charSequence instanceof String) {
            return toUpperCase((String) charSequence);
        }
        char[] cArr = new char[charSequence.length()];
        for (int i = 0; i < cArr.length; i++) {
            cArr[i] = toUpperCase(charSequence.charAt(i));
        }
        return String.valueOf(cArr);
    }

    public static char toUpperCase(char c) {
        return isLowerCase(c) ? (char) (c ^ ' ') : c;
    }

    /* JADX WARNING: type inference failed for: r5v0, types: [java.lang.CharSequence, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r5v1, types: [java.lang.CharSequence] */
    /* JADX WARNING: type inference failed for: r5v3, types: [java.lang.String] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.CharSequence, code=null, for r5v0, types: [java.lang.CharSequence, java.lang.Object] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r5v3, types: [java.lang.String]
      assigns: [java.lang.String, java.lang.CharSequence]
      uses: [java.lang.String, java.lang.CharSequence, java.lang.Object]
      mth insns count: 20
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String truncate(java.lang.CharSequence r5, int r6, java.lang.String r7) {
        /*
            com.google.common.base.Preconditions.checkNotNull(r5)
            int r0 = r7.length()
            int r0 = r6 - r0
            r1 = 0
            if (r0 < 0) goto L_0x000e
            r2 = 1
            goto L_0x000f
        L_0x000e:
            r2 = 0
        L_0x000f:
            int r3 = r7.length()
            java.lang.String r4 = "maxLength (%s) must be >= length of the truncation indicator (%s)"
            com.google.common.base.Preconditions.checkArgument(r2, r4, r6, r3)
            int r2 = r5.length()
            if (r2 > r6) goto L_0x0029
            java.lang.String r5 = r5.toString()
            int r2 = r5.length()
            if (r2 > r6) goto L_0x0029
            return r5
        L_0x0029:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r6)
            r2.append(r5, r1, r0)
            r2.append(r7)
            java.lang.String r5 = r2.toString()
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.base.Ascii.truncate(java.lang.CharSequence, int, java.lang.String):java.lang.String");
    }

    public static boolean equalsIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        int length = charSequence.length();
        if (charSequence == charSequence2) {
            return true;
        }
        if (length != charSequence2.length()) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            char charAt = charSequence.charAt(i);
            char charAt2 = charSequence2.charAt(i);
            if (charAt != charAt2) {
                int alphaIndex = getAlphaIndex(charAt);
                if (alphaIndex >= 26 || alphaIndex != getAlphaIndex(charAt2)) {
                    return false;
                }
            }
        }
        return true;
    }
}

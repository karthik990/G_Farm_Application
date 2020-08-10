package com.google.android.exoplayer2.text.ssa;

import android.graphics.PointF;
import android.text.TextUtils;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class SsaStyle {
    private static final String TAG = "SsaStyle";
    public final int alignment;
    public final String name;

    static final class Format {
        public final int alignmentIndex;
        public final int length;
        public final int nameIndex;

        private Format(int i, int i2, int i3) {
            this.nameIndex = i;
            this.alignmentIndex = i2;
            this.length = i3;
        }

        /* JADX WARNING: Removed duplicated region for block: B:15:0x0044  */
        /* JADX WARNING: Removed duplicated region for block: B:17:0x0049  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static com.google.android.exoplayer2.text.ssa.SsaStyle.Format fromFormatLine(java.lang.String r9) {
            /*
                r0 = 7
                java.lang.String r9 = r9.substring(r0)
                java.lang.String r0 = ","
                java.lang.String[] r9 = android.text.TextUtils.split(r9, r0)
                r0 = 0
                r1 = -1
                r2 = 0
                r3 = -1
                r4 = -1
            L_0x0010:
                int r5 = r9.length
                if (r2 >= r5) goto L_0x004d
                r5 = r9[r2]
                java.lang.String r5 = r5.trim()
                java.lang.String r5 = com.google.android.exoplayer2.util.Util.toLowerInvariant(r5)
                int r6 = r5.hashCode()
                r7 = 3373707(0x337a8b, float:4.72757E-39)
                r8 = 1
                if (r6 == r7) goto L_0x0037
                r7 = 1767875043(0x695fa1e3, float:1.6897184E25)
                if (r6 == r7) goto L_0x002d
                goto L_0x0041
            L_0x002d:
                java.lang.String r6 = "alignment"
                boolean r5 = r5.equals(r6)
                if (r5 == 0) goto L_0x0041
                r5 = 1
                goto L_0x0042
            L_0x0037:
                java.lang.String r6 = "name"
                boolean r5 = r5.equals(r6)
                if (r5 == 0) goto L_0x0041
                r5 = 0
                goto L_0x0042
            L_0x0041:
                r5 = -1
            L_0x0042:
                if (r5 == 0) goto L_0x0049
                if (r5 == r8) goto L_0x0047
                goto L_0x004a
            L_0x0047:
                r4 = r2
                goto L_0x004a
            L_0x0049:
                r3 = r2
            L_0x004a:
                int r2 = r2 + 1
                goto L_0x0010
            L_0x004d:
                if (r3 == r1) goto L_0x0056
                com.google.android.exoplayer2.text.ssa.SsaStyle$Format r0 = new com.google.android.exoplayer2.text.ssa.SsaStyle$Format
                int r9 = r9.length
                r0.<init>(r3, r4, r9)
                goto L_0x0057
            L_0x0056:
                r0 = 0
            L_0x0057:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ssa.SsaStyle.Format.fromFormatLine(java.lang.String):com.google.android.exoplayer2.text.ssa.SsaStyle$Format");
        }
    }

    static final class Overrides {
        private static final Pattern ALIGNMENT_OVERRIDE_PATTERN = Pattern.compile("\\\\an(\\d+)");
        private static final Pattern BRACES_PATTERN = Pattern.compile("\\{([^}]*)\\}");
        private static final Pattern MOVE_PATTERN;
        private static final String PADDED_DECIMAL_PATTERN = "\\s*\\d+(?:\\.\\d+)?\\s*";
        private static final Pattern POSITION_PATTERN;
        private static final String TAG = "SsaStyle.Overrides";
        public final int alignment;
        public final PointF position;

        static {
            String str = PADDED_DECIMAL_PATTERN;
            POSITION_PATTERN = Pattern.compile(Util.formatInvariant("\\\\pos\\((%1$s),(%1$s)\\)", str));
            MOVE_PATTERN = Pattern.compile(Util.formatInvariant("\\\\move\\(%1$s,%1$s,(%1$s),(%1$s)(?:,%1$s,%1$s)?\\)", str));
        }

        private Overrides(int i, PointF pointF) {
            this.alignment = i;
            this.position = pointF;
        }

        /* JADX WARNING: Can't wrap try/catch for region: R(9:3|4|5|(1:7)|8|9|(2:11|18)(1:17)|15|1) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x001c */
        /* JADX WARNING: Removed duplicated region for block: B:11:0x0022  */
        /* JADX WARNING: Removed duplicated region for block: B:17:0x000a A[SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static com.google.android.exoplayer2.text.ssa.SsaStyle.Overrides parseFromDialogue(java.lang.String r5) {
            /*
                java.util.regex.Pattern r0 = BRACES_PATTERN
                java.util.regex.Matcher r5 = r0.matcher(r5)
                r0 = -1
                r1 = 0
                r2 = r1
                r1 = -1
            L_0x000a:
                boolean r3 = r5.find()
                if (r3 == 0) goto L_0x0026
                r3 = 1
                java.lang.String r3 = r5.group(r3)
                android.graphics.PointF r4 = parsePosition(r3)     // Catch:{ RuntimeException -> 0x001c }
                if (r4 == 0) goto L_0x001c
                r2 = r4
            L_0x001c:
                int r3 = parseAlignmentOverride(r3)     // Catch:{ RuntimeException -> 0x0024 }
                if (r3 == r0) goto L_0x000a
                r1 = r3
                goto L_0x000a
            L_0x0024:
                goto L_0x000a
            L_0x0026:
                com.google.android.exoplayer2.text.ssa.SsaStyle$Overrides r5 = new com.google.android.exoplayer2.text.ssa.SsaStyle$Overrides
                r5.<init>(r1, r2)
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ssa.SsaStyle.Overrides.parseFromDialogue(java.lang.String):com.google.android.exoplayer2.text.ssa.SsaStyle$Overrides");
        }

        public static String stripStyleOverrides(String str) {
            return BRACES_PATTERN.matcher(str).replaceAll("");
        }

        private static PointF parsePosition(String str) {
            String str2;
            String str3;
            Matcher matcher = POSITION_PATTERN.matcher(str);
            Matcher matcher2 = MOVE_PATTERN.matcher(str);
            boolean find = matcher.find();
            boolean find2 = matcher2.find();
            if (find) {
                if (find2) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Override has both \\pos(x,y) and \\move(x1,y1,x2,y2); using \\pos values. override='");
                    sb.append(str);
                    sb.append("'");
                    Log.m1394i(TAG, sb.toString());
                }
                str2 = matcher.group(1);
                str3 = matcher.group(2);
            } else if (!find2) {
                return null;
            } else {
                str2 = matcher2.group(1);
                str3 = matcher2.group(2);
            }
            return new PointF(Float.parseFloat(((String) Assertions.checkNotNull(str2)).trim()), Float.parseFloat(((String) Assertions.checkNotNull(str3)).trim()));
        }

        private static int parseAlignmentOverride(String str) {
            Matcher matcher = ALIGNMENT_OVERRIDE_PATTERN.matcher(str);
            if (matcher.find()) {
                return SsaStyle.parseAlignment(matcher.group(1));
            }
            return -1;
        }
    }

    private static boolean isValidAlignment(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return true;
            default:
                return false;
        }
    }

    private SsaStyle(String str, int i) {
        this.name = str;
        this.alignment = i;
    }

    public static SsaStyle fromStyleLine(String str, Format format) {
        Assertions.checkArgument(str.startsWith("Style:"));
        String[] split = TextUtils.split(str.substring(6), ",");
        int length = split.length;
        int i = format.length;
        String str2 = TAG;
        if (length != i) {
            Log.m1396w(str2, Util.formatInvariant("Skipping malformed 'Style:' line (expected %s values, found %s): '%s'", Integer.valueOf(format.length), Integer.valueOf(split.length), str));
            return null;
        }
        try {
            return new SsaStyle(split[format.nameIndex].trim(), parseAlignment(split[format.alignmentIndex]));
        } catch (RuntimeException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Skipping malformed 'Style:' line: '");
            sb.append(str);
            sb.append("'");
            Log.m1397w(str2, sb.toString(), e);
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static int parseAlignment(String str) {
        try {
            int parseInt = Integer.parseInt(str.trim());
            if (isValidAlignment(parseInt)) {
                return parseInt;
            }
        } catch (NumberFormatException unused) {
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Ignoring unknown alignment: ");
        sb.append(str);
        Log.m1396w(TAG, sb.toString());
        return -1;
    }
}

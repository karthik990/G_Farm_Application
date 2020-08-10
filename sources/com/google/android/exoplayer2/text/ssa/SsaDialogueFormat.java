package com.google.android.exoplayer2.text.ssa;

final class SsaDialogueFormat {
    public final int endTimeIndex;
    public final int length;
    public final int startTimeIndex;
    public final int styleIndex;
    public final int textIndex;

    private SsaDialogueFormat(int i, int i2, int i3, int i4, int i5) {
        this.startTimeIndex = i;
        this.endTimeIndex = i2;
        this.styleIndex = i3;
        this.textIndex = i4;
        this.length = i5;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.exoplayer2.text.ssa.SsaDialogueFormat fromFormatLine(java.lang.String r12) {
        /*
            java.lang.String r0 = "Format:"
            boolean r0 = r12.startsWith(r0)
            com.google.android.exoplayer2.util.Assertions.checkArgument(r0)
            r0 = 7
            java.lang.String r12 = r12.substring(r0)
            java.lang.String r0 = ","
            java.lang.String[] r12 = android.text.TextUtils.split(r12, r0)
            r0 = 0
            r1 = -1
            r2 = 0
            r4 = -1
            r5 = -1
            r6 = -1
            r7 = -1
        L_0x001b:
            int r3 = r12.length
            if (r2 >= r3) goto L_0x006f
            r3 = r12[r2]
            java.lang.String r3 = r3.trim()
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.toLowerInvariant(r3)
            int r8 = r3.hashCode()
            r9 = 3
            r10 = 2
            r11 = 1
            switch(r8) {
                case 100571: goto L_0x0051;
                case 3556653: goto L_0x0047;
                case 109757538: goto L_0x003d;
                case 109780401: goto L_0x0033;
                default: goto L_0x0032;
            }
        L_0x0032:
            goto L_0x005b
        L_0x0033:
            java.lang.String r8 = "style"
            boolean r3 = r3.equals(r8)
            if (r3 == 0) goto L_0x005b
            r3 = 2
            goto L_0x005c
        L_0x003d:
            java.lang.String r8 = "start"
            boolean r3 = r3.equals(r8)
            if (r3 == 0) goto L_0x005b
            r3 = 0
            goto L_0x005c
        L_0x0047:
            java.lang.String r8 = "text"
            boolean r3 = r3.equals(r8)
            if (r3 == 0) goto L_0x005b
            r3 = 3
            goto L_0x005c
        L_0x0051:
            java.lang.String r8 = "end"
            boolean r3 = r3.equals(r8)
            if (r3 == 0) goto L_0x005b
            r3 = 1
            goto L_0x005c
        L_0x005b:
            r3 = -1
        L_0x005c:
            if (r3 == 0) goto L_0x006b
            if (r3 == r11) goto L_0x0069
            if (r3 == r10) goto L_0x0067
            if (r3 == r9) goto L_0x0065
            goto L_0x006c
        L_0x0065:
            r7 = r2
            goto L_0x006c
        L_0x0067:
            r6 = r2
            goto L_0x006c
        L_0x0069:
            r5 = r2
            goto L_0x006c
        L_0x006b:
            r4 = r2
        L_0x006c:
            int r2 = r2 + 1
            goto L_0x001b
        L_0x006f:
            if (r4 == r1) goto L_0x007b
            if (r5 == r1) goto L_0x007b
            com.google.android.exoplayer2.text.ssa.SsaDialogueFormat r0 = new com.google.android.exoplayer2.text.ssa.SsaDialogueFormat
            int r8 = r12.length
            r3 = r0
            r3.<init>(r4, r5, r6, r7, r8)
            goto L_0x007c
        L_0x007b:
            r0 = 0
        L_0x007c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ssa.SsaDialogueFormat.fromFormatLine(java.lang.String):com.google.android.exoplayer2.text.ssa.SsaDialogueFormat");
    }
}

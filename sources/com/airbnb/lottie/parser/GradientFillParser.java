package com.airbnb.lottie.parser;

class GradientFillParser {
    private GradientFillParser() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x0078  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x007c  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0089  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0090  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0097  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00a5  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00ac  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00de  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00ea  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x00f4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.airbnb.lottie.model.content.GradientFill parse(android.util.JsonReader r14, com.airbnb.lottie.LottieComposition r15) throws java.io.IOException {
        /*
            r0 = 0
            r2 = r0
            r3 = r2
            r4 = r3
            r5 = r4
            r6 = r5
            r7 = r6
            r8 = r7
        L_0x0008:
            boolean r0 = r14.hasNext()
            if (r0 == 0) goto L_0x00fb
            java.lang.String r0 = r14.nextName()
            int r1 = r0.hashCode()
            r9 = 101(0x65, float:1.42E-43)
            r10 = 0
            r11 = -1
            r12 = 1
            if (r1 == r9) goto L_0x006a
            r9 = 103(0x67, float:1.44E-43)
            if (r1 == r9) goto L_0x0060
            r9 = 111(0x6f, float:1.56E-43)
            if (r1 == r9) goto L_0x0056
            r9 = 3519(0xdbf, float:4.931E-42)
            if (r1 == r9) goto L_0x004c
            switch(r1) {
                case 114: goto L_0x0042;
                case 115: goto L_0x0038;
                case 116: goto L_0x002d;
                default: goto L_0x002c;
            }
        L_0x002c:
            goto L_0x0074
        L_0x002d:
            java.lang.String r1 = "t"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0074
            r0 = 3
            goto L_0x0075
        L_0x0038:
            java.lang.String r1 = "s"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0074
            r0 = 4
            goto L_0x0075
        L_0x0042:
            java.lang.String r1 = "r"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0074
            r0 = 6
            goto L_0x0075
        L_0x004c:
            java.lang.String r1 = "nm"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0074
            r0 = 0
            goto L_0x0075
        L_0x0056:
            java.lang.String r1 = "o"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0074
            r0 = 2
            goto L_0x0075
        L_0x0060:
            java.lang.String r1 = "g"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0074
            r0 = 1
            goto L_0x0075
        L_0x006a:
            java.lang.String r1 = "e"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0074
            r0 = 5
            goto L_0x0075
        L_0x0074:
            r0 = -1
        L_0x0075:
            switch(r0) {
                case 0: goto L_0x00f4;
                case 1: goto L_0x00ac;
                case 2: goto L_0x00a5;
                case 3: goto L_0x0097;
                case 4: goto L_0x0090;
                case 5: goto L_0x0089;
                case 6: goto L_0x007c;
                default: goto L_0x0078;
            }
        L_0x0078:
            r14.skipValue()
            goto L_0x0008
        L_0x007c:
            int r0 = r14.nextInt()
            if (r0 != r12) goto L_0x0085
            android.graphics.Path$FillType r0 = android.graphics.Path.FillType.WINDING
            goto L_0x0087
        L_0x0085:
            android.graphics.Path$FillType r0 = android.graphics.Path.FillType.EVEN_ODD
        L_0x0087:
            r4 = r0
            goto L_0x0008
        L_0x0089:
            com.airbnb.lottie.model.animatable.AnimatablePointValue r0 = com.airbnb.lottie.parser.AnimatableValueParser.parsePoint(r14, r15)
            r8 = r0
            goto L_0x0008
        L_0x0090:
            com.airbnb.lottie.model.animatable.AnimatablePointValue r0 = com.airbnb.lottie.parser.AnimatableValueParser.parsePoint(r14, r15)
            r7 = r0
            goto L_0x0008
        L_0x0097:
            int r0 = r14.nextInt()
            if (r0 != r12) goto L_0x00a0
            com.airbnb.lottie.model.content.GradientType r0 = com.airbnb.lottie.model.content.GradientType.Linear
            goto L_0x00a2
        L_0x00a0:
            com.airbnb.lottie.model.content.GradientType r0 = com.airbnb.lottie.model.content.GradientType.Radial
        L_0x00a2:
            r3 = r0
            goto L_0x0008
        L_0x00a5:
            com.airbnb.lottie.model.animatable.AnimatableIntegerValue r0 = com.airbnb.lottie.parser.AnimatableValueParser.parseInteger(r14, r15)
            r6 = r0
            goto L_0x0008
        L_0x00ac:
            r14.beginObject()
            r0 = -1
        L_0x00b0:
            boolean r1 = r14.hasNext()
            if (r1 == 0) goto L_0x00ef
            java.lang.String r1 = r14.nextName()
            int r9 = r1.hashCode()
            r13 = 107(0x6b, float:1.5E-43)
            if (r9 == r13) goto L_0x00d1
            r13 = 112(0x70, float:1.57E-43)
            if (r9 == r13) goto L_0x00c7
            goto L_0x00db
        L_0x00c7:
            java.lang.String r9 = "p"
            boolean r1 = r1.equals(r9)
            if (r1 == 0) goto L_0x00db
            r1 = 0
            goto L_0x00dc
        L_0x00d1:
            java.lang.String r9 = "k"
            boolean r1 = r1.equals(r9)
            if (r1 == 0) goto L_0x00db
            r1 = 1
            goto L_0x00dc
        L_0x00db:
            r1 = -1
        L_0x00dc:
            if (r1 == 0) goto L_0x00ea
            if (r1 == r12) goto L_0x00e4
            r14.skipValue()
            goto L_0x00b0
        L_0x00e4:
            com.airbnb.lottie.model.animatable.AnimatableGradientColorValue r1 = com.airbnb.lottie.parser.AnimatableValueParser.parseGradientColor(r14, r15, r0)
            r5 = r1
            goto L_0x00b0
        L_0x00ea:
            int r0 = r14.nextInt()
            goto L_0x00b0
        L_0x00ef:
            r14.endObject()
            goto L_0x0008
        L_0x00f4:
            java.lang.String r0 = r14.nextString()
            r2 = r0
            goto L_0x0008
        L_0x00fb:
            com.airbnb.lottie.model.content.GradientFill r14 = new com.airbnb.lottie.model.content.GradientFill
            r9 = 0
            r10 = 0
            r1 = r14
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10)
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.GradientFillParser.parse(android.util.JsonReader, com.airbnb.lottie.LottieComposition):com.airbnb.lottie.model.content.GradientFill");
    }
}

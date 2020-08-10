package com.airbnb.lottie.parser;

class GradientStrokeParser {
    private GradientStrokeParser() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:106:0x01ce, code lost:
        r8 = r21;
        r12 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x0230, code lost:
        r11 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x0232, code lost:
        r9 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:0x0243, code lost:
        r8 = r20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00c2, code lost:
        r11 = r17;
     */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x01af  */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x01c6  */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x01d4  */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x020b  */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x021f  */
    /* JADX WARNING: Removed duplicated region for block: B:130:0x0235  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00b7  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00c6  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x010b  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x0118  */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x016b  */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x017a  */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x0194  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x019d  */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x01a6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.airbnb.lottie.model.content.GradientStroke parse(android.util.JsonReader r21, com.airbnb.lottie.LottieComposition r22) throws java.io.IOException {
        /*
            java.util.ArrayList r10 = new java.util.ArrayList
            r10.<init>()
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r11 = 0
        L_0x000f:
            boolean r12 = r21.hasNext()
            if (r12 == 0) goto L_0x0247
            java.lang.String r12 = r21.nextName()
            int r13 = r12.hashCode()
            java.lang.String r15 = "o"
            java.lang.String r0 = "g"
            java.lang.String r14 = "d"
            r17 = r11
            r18 = -1
            r11 = 100
            if (r13 == r11) goto L_0x00aa
            r11 = 101(0x65, float:1.42E-43)
            if (r13 == r11) goto L_0x00a0
            r11 = 103(0x67, float:1.44E-43)
            if (r13 == r11) goto L_0x0098
            r11 = 111(0x6f, float:1.56E-43)
            if (r13 == r11) goto L_0x0090
            r11 = 119(0x77, float:1.67E-43)
            if (r13 == r11) goto L_0x0085
            r11 = 3447(0xd77, float:4.83E-42)
            if (r13 == r11) goto L_0x007b
            r11 = 3454(0xd7e, float:4.84E-42)
            if (r13 == r11) goto L_0x0070
            r11 = 3519(0xdbf, float:4.931E-42)
            if (r13 == r11) goto L_0x0066
            r11 = 115(0x73, float:1.61E-43)
            if (r13 == r11) goto L_0x005c
            r11 = 116(0x74, float:1.63E-43)
            if (r13 == r11) goto L_0x0051
            goto L_0x00b3
        L_0x0051:
            java.lang.String r11 = "t"
            boolean r11 = r12.equals(r11)
            if (r11 == 0) goto L_0x00b3
            r11 = 3
            goto L_0x00b4
        L_0x005c:
            java.lang.String r11 = "s"
            boolean r11 = r12.equals(r11)
            if (r11 == 0) goto L_0x00b3
            r11 = 4
            goto L_0x00b4
        L_0x0066:
            java.lang.String r11 = "nm"
            boolean r11 = r12.equals(r11)
            if (r11 == 0) goto L_0x00b3
            r11 = 0
            goto L_0x00b4
        L_0x0070:
            java.lang.String r11 = "lj"
            boolean r11 = r12.equals(r11)
            if (r11 == 0) goto L_0x00b3
            r11 = 8
            goto L_0x00b4
        L_0x007b:
            java.lang.String r11 = "lc"
            boolean r11 = r12.equals(r11)
            if (r11 == 0) goto L_0x00b3
            r11 = 7
            goto L_0x00b4
        L_0x0085:
            java.lang.String r11 = "w"
            boolean r11 = r12.equals(r11)
            if (r11 == 0) goto L_0x00b3
            r11 = 6
            goto L_0x00b4
        L_0x0090:
            boolean r11 = r12.equals(r15)
            if (r11 == 0) goto L_0x00b3
            r11 = 2
            goto L_0x00b4
        L_0x0098:
            boolean r11 = r12.equals(r0)
            if (r11 == 0) goto L_0x00b3
            r11 = 1
            goto L_0x00b4
        L_0x00a0:
            java.lang.String r11 = "e"
            boolean r11 = r12.equals(r11)
            if (r11 == 0) goto L_0x00b3
            r11 = 5
            goto L_0x00b4
        L_0x00aa:
            boolean r11 = r12.equals(r14)
            if (r11 == 0) goto L_0x00b3
            r11 = 9
            goto L_0x00b4
        L_0x00b3:
            r11 = -1
        L_0x00b4:
            switch(r11) {
                case 0: goto L_0x0235;
                case 1: goto L_0x01d4;
                case 2: goto L_0x01c6;
                case 3: goto L_0x01af;
                case 4: goto L_0x01a6;
                case 5: goto L_0x019d;
                case 6: goto L_0x0194;
                case 7: goto L_0x017a;
                case 8: goto L_0x016b;
                case 9: goto L_0x00c6;
                default: goto L_0x00b7;
            }
        L_0x00b7:
            r12 = r22
            r20 = r8
            r19 = r9
            r8 = r21
            r21.skipValue()
        L_0x00c2:
            r11 = r17
            goto L_0x0243
        L_0x00c6:
            r21.beginArray()
            r11 = r17
        L_0x00cb:
            boolean r12 = r21.hasNext()
            if (r12 == 0) goto L_0x014b
            r21.beginObject()
            r12 = 0
            r13 = 0
        L_0x00d6:
            boolean r16 = r21.hasNext()
            if (r16 == 0) goto L_0x0124
            r16 = r11
            java.lang.String r11 = r21.nextName()
            r19 = r9
            int r9 = r11.hashCode()
            r20 = r8
            r8 = 110(0x6e, float:1.54E-43)
            if (r9 == r8) goto L_0x00fe
            r8 = 118(0x76, float:1.65E-43)
            if (r9 == r8) goto L_0x00f3
            goto L_0x0108
        L_0x00f3:
            java.lang.String r8 = "v"
            boolean r8 = r11.equals(r8)
            if (r8 == 0) goto L_0x0108
            r8 = 1
            goto L_0x0109
        L_0x00fe:
            java.lang.String r8 = "n"
            boolean r8 = r11.equals(r8)
            if (r8 == 0) goto L_0x0108
            r8 = 0
            goto L_0x0109
        L_0x0108:
            r8 = -1
        L_0x0109:
            if (r8 == 0) goto L_0x0118
            r9 = 1
            if (r8 == r9) goto L_0x0112
            r21.skipValue()
            goto L_0x011d
        L_0x0112:
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r8 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r21, r22)
            r13 = r8
            goto L_0x011d
        L_0x0118:
            java.lang.String r8 = r21.nextString()
            r12 = r8
        L_0x011d:
            r11 = r16
            r9 = r19
            r8 = r20
            goto L_0x00d6
        L_0x0124:
            r20 = r8
            r19 = r9
            r16 = r11
            r21.endObject()
            boolean r8 = r12.equals(r15)
            if (r8 == 0) goto L_0x0135
            r11 = r13
            goto L_0x0146
        L_0x0135:
            boolean r8 = r12.equals(r14)
            if (r8 != 0) goto L_0x0141
            boolean r8 = r12.equals(r0)
            if (r8 == 0) goto L_0x0144
        L_0x0141:
            r10.add(r13)
        L_0x0144:
            r11 = r16
        L_0x0146:
            r9 = r19
            r8 = r20
            goto L_0x00cb
        L_0x014b:
            r20 = r8
            r19 = r9
            r16 = r11
            r21.endArray()
            int r0 = r10.size()
            r8 = 1
            if (r0 != r8) goto L_0x0163
            r11 = 0
            java.lang.Object r0 = r10.get(r11)
            r10.add(r0)
        L_0x0163:
            r8 = r21
            r12 = r22
            r11 = r16
            goto L_0x0232
        L_0x016b:
            r20 = r8
            r8 = 1
            com.airbnb.lottie.model.content.ShapeStroke$LineJoinType[] r0 = com.airbnb.lottie.model.content.ShapeStroke.LineJoinType.values()
            int r9 = r21.nextInt()
            int r9 = r9 - r8
            r9 = r0[r9]
            goto L_0x01ce
        L_0x017a:
            r19 = r9
            r8 = 1
            com.airbnb.lottie.model.content.ShapeStroke$LineCapType[] r0 = com.airbnb.lottie.model.content.ShapeStroke.LineCapType.values()
            int r9 = r21.nextInt()
            int r9 = r9 - r8
            r8 = r0[r9]
            r12 = r22
            r20 = r8
            r11 = r17
            r9 = r19
            r8 = r21
            goto L_0x0243
        L_0x0194:
            r20 = r8
            r19 = r9
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r7 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r21, r22)
            goto L_0x01ce
        L_0x019d:
            r20 = r8
            r19 = r9
            com.airbnb.lottie.model.animatable.AnimatablePointValue r6 = com.airbnb.lottie.parser.AnimatableValueParser.parsePoint(r21, r22)
            goto L_0x01ce
        L_0x01a6:
            r20 = r8
            r19 = r9
            com.airbnb.lottie.model.animatable.AnimatablePointValue r5 = com.airbnb.lottie.parser.AnimatableValueParser.parsePoint(r21, r22)
            goto L_0x01ce
        L_0x01af:
            r20 = r8
            r19 = r9
            r8 = 1
            int r0 = r21.nextInt()
            if (r0 != r8) goto L_0x01bd
            com.airbnb.lottie.model.content.GradientType r0 = com.airbnb.lottie.model.content.GradientType.Linear
            goto L_0x01bf
        L_0x01bd:
            com.airbnb.lottie.model.content.GradientType r0 = com.airbnb.lottie.model.content.GradientType.Radial
        L_0x01bf:
            r2 = r0
            r8 = r21
            r12 = r22
            goto L_0x0230
        L_0x01c6:
            r20 = r8
            r19 = r9
            com.airbnb.lottie.model.animatable.AnimatableIntegerValue r4 = com.airbnb.lottie.parser.AnimatableValueParser.parseInteger(r21, r22)
        L_0x01ce:
            r8 = r21
            r12 = r22
            goto L_0x00c2
        L_0x01d4:
            r20 = r8
            r19 = r9
            r11 = 0
            r21.beginObject()
            r0 = -1
        L_0x01dd:
            boolean r8 = r21.hasNext()
            if (r8 == 0) goto L_0x0229
            java.lang.String r8 = r21.nextName()
            int r9 = r8.hashCode()
            r12 = 107(0x6b, float:1.5E-43)
            if (r9 == r12) goto L_0x01fe
            r12 = 112(0x70, float:1.57E-43)
            if (r9 == r12) goto L_0x01f4
            goto L_0x0208
        L_0x01f4:
            java.lang.String r9 = "p"
            boolean r8 = r8.equals(r9)
            if (r8 == 0) goto L_0x0208
            r8 = 0
            goto L_0x0209
        L_0x01fe:
            java.lang.String r9 = "k"
            boolean r8 = r8.equals(r9)
            if (r8 == 0) goto L_0x0208
            r8 = 1
            goto L_0x0209
        L_0x0208:
            r8 = -1
        L_0x0209:
            if (r8 == 0) goto L_0x021f
            r9 = 1
            if (r8 == r9) goto L_0x0216
            r21.skipValue()
            r8 = r21
            r12 = r22
            goto L_0x01dd
        L_0x0216:
            r8 = r21
            r12 = r22
            com.airbnb.lottie.model.animatable.AnimatableGradientColorValue r3 = com.airbnb.lottie.parser.AnimatableValueParser.parseGradientColor(r8, r12, r0)
            goto L_0x01dd
        L_0x021f:
            r9 = 1
            r8 = r21
            r12 = r22
            int r0 = r21.nextInt()
            goto L_0x01dd
        L_0x0229:
            r8 = r21
            r12 = r22
            r21.endObject()
        L_0x0230:
            r11 = r17
        L_0x0232:
            r9 = r19
            goto L_0x0243
        L_0x0235:
            r12 = r22
            r20 = r8
            r19 = r9
            r8 = r21
            java.lang.String r1 = r21.nextString()
            goto L_0x00c2
        L_0x0243:
            r8 = r20
            goto L_0x000f
        L_0x0247:
            r20 = r8
            r19 = r9
            r17 = r11
            com.airbnb.lottie.model.content.GradientStroke r12 = new com.airbnb.lottie.model.content.GradientStroke
            r0 = r12
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.GradientStrokeParser.parse(android.util.JsonReader, com.airbnb.lottie.LottieComposition):com.airbnb.lottie.model.content.GradientStroke");
    }
}

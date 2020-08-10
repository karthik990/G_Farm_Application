package com.airbnb.lottie.parser;

public class AnimatableTransformParser {
    private AnimatableTransformParser() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:51:0x009e  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00a3  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00aa  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00b1  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00b7  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00c3  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00c9  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00d0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.airbnb.lottie.model.animatable.AnimatableTransform parse(android.util.JsonReader r14, com.airbnb.lottie.LottieComposition r15) throws java.io.IOException {
        /*
            android.util.JsonToken r0 = r14.peek()
            android.util.JsonToken r1 = android.util.JsonToken.BEGIN_OBJECT
            r2 = 1
            r3 = 0
            if (r0 != r1) goto L_0x000c
            r0 = 1
            goto L_0x000d
        L_0x000c:
            r0 = 0
        L_0x000d:
            if (r0 == 0) goto L_0x0012
            r14.beginObject()
        L_0x0012:
            r1 = 0
            r4 = r1
            r5 = r4
            r8 = r5
            r10 = r8
            r12 = r10
            r13 = r12
        L_0x0019:
            boolean r6 = r14.hasNext()
            if (r6 == 0) goto L_0x00f3
            java.lang.String r6 = r14.nextName()
            r7 = -1
            int r9 = r6.hashCode()
            r11 = 97
            if (r9 == r11) goto L_0x0090
            r11 = 3242(0xcaa, float:4.543E-42)
            if (r9 == r11) goto L_0x0086
            r11 = 3656(0xe48, float:5.123E-42)
            if (r9 == r11) goto L_0x007c
            r11 = 3676(0xe5c, float:5.151E-42)
            if (r9 == r11) goto L_0x0071
            r11 = 111(0x6f, float:1.56E-43)
            if (r9 == r11) goto L_0x0067
            r11 = 112(0x70, float:1.57E-43)
            if (r9 == r11) goto L_0x005d
            r11 = 114(0x72, float:1.6E-43)
            if (r9 == r11) goto L_0x0053
            r11 = 115(0x73, float:1.61E-43)
            if (r9 == r11) goto L_0x0049
            goto L_0x009a
        L_0x0049:
            java.lang.String r9 = "s"
            boolean r6 = r6.equals(r9)
            if (r6 == 0) goto L_0x009a
            r6 = 2
            goto L_0x009b
        L_0x0053:
            java.lang.String r9 = "r"
            boolean r6 = r6.equals(r9)
            if (r6 == 0) goto L_0x009a
            r6 = 4
            goto L_0x009b
        L_0x005d:
            java.lang.String r9 = "p"
            boolean r6 = r6.equals(r9)
            if (r6 == 0) goto L_0x009a
            r6 = 1
            goto L_0x009b
        L_0x0067:
            java.lang.String r9 = "o"
            boolean r6 = r6.equals(r9)
            if (r6 == 0) goto L_0x009a
            r6 = 5
            goto L_0x009b
        L_0x0071:
            java.lang.String r9 = "so"
            boolean r6 = r6.equals(r9)
            if (r6 == 0) goto L_0x009a
            r6 = 6
            goto L_0x009b
        L_0x007c:
            java.lang.String r9 = "rz"
            boolean r6 = r6.equals(r9)
            if (r6 == 0) goto L_0x009a
            r6 = 3
            goto L_0x009b
        L_0x0086:
            java.lang.String r9 = "eo"
            boolean r6 = r6.equals(r9)
            if (r6 == 0) goto L_0x009a
            r6 = 7
            goto L_0x009b
        L_0x0090:
            java.lang.String r9 = "a"
            boolean r6 = r6.equals(r9)
            if (r6 == 0) goto L_0x009a
            r6 = 0
            goto L_0x009b
        L_0x009a:
            r6 = -1
        L_0x009b:
            switch(r6) {
                case 0: goto L_0x00d0;
                case 1: goto L_0x00c9;
                case 2: goto L_0x00c3;
                case 3: goto L_0x00b7;
                case 4: goto L_0x00bc;
                case 5: goto L_0x00b1;
                case 6: goto L_0x00aa;
                case 7: goto L_0x00a3;
                default: goto L_0x009e;
            }
        L_0x009e:
            r14.skipValue()
            goto L_0x0019
        L_0x00a3:
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r6 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r14, r15, r3)
            r13 = r6
            goto L_0x0019
        L_0x00aa:
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r6 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r14, r15, r3)
            r12 = r6
            goto L_0x0019
        L_0x00b1:
            com.airbnb.lottie.model.animatable.AnimatableIntegerValue r5 = com.airbnb.lottie.parser.AnimatableValueParser.parseInteger(r14, r15)
            goto L_0x0019
        L_0x00b7:
            java.lang.String r6 = "Lottie doesn't support 3D layers."
            r15.addWarning(r6)
        L_0x00bc:
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r6 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r14, r15, r3)
            r10 = r6
            goto L_0x0019
        L_0x00c3:
            com.airbnb.lottie.model.animatable.AnimatableScaleValue r4 = com.airbnb.lottie.parser.AnimatableValueParser.parseScale(r14, r15)
            goto L_0x0019
        L_0x00c9:
            com.airbnb.lottie.model.animatable.AnimatableValue r6 = com.airbnb.lottie.parser.AnimatablePathValueParser.parseSplitPath(r14, r15)
            r8 = r6
            goto L_0x0019
        L_0x00d0:
            r14.beginObject()
        L_0x00d3:
            boolean r6 = r14.hasNext()
            if (r6 == 0) goto L_0x00ee
            java.lang.String r6 = r14.nextName()
            java.lang.String r7 = "k"
            boolean r6 = r6.equals(r7)
            if (r6 == 0) goto L_0x00ea
            com.airbnb.lottie.model.animatable.AnimatablePathValue r1 = com.airbnb.lottie.parser.AnimatablePathValueParser.parse(r14, r15)
            goto L_0x00d3
        L_0x00ea:
            r14.skipValue()
            goto L_0x00d3
        L_0x00ee:
            r14.endObject()
            goto L_0x0019
        L_0x00f3:
            if (r0 == 0) goto L_0x00f8
            r14.endObject()
        L_0x00f8:
            if (r1 != 0) goto L_0x0106
            java.lang.String r14 = "LOTTIE"
            java.lang.String r15 = "Layer has no transform property. You may be using an unsupported layer type such as a camera."
            android.util.Log.w(r14, r15)
            com.airbnb.lottie.model.animatable.AnimatablePathValue r1 = new com.airbnb.lottie.model.animatable.AnimatablePathValue
            r1.<init>()
        L_0x0106:
            r7 = r1
            if (r4 != 0) goto L_0x0115
            com.airbnb.lottie.model.animatable.AnimatableScaleValue r4 = new com.airbnb.lottie.model.animatable.AnimatableScaleValue
            com.airbnb.lottie.value.ScaleXY r14 = new com.airbnb.lottie.value.ScaleXY
            r15 = 1065353216(0x3f800000, float:1.0)
            r14.<init>(r15, r15)
            r4.<init>(r14)
        L_0x0115:
            r9 = r4
            if (r5 != 0) goto L_0x011d
            com.airbnb.lottie.model.animatable.AnimatableIntegerValue r5 = new com.airbnb.lottie.model.animatable.AnimatableIntegerValue
            r5.<init>()
        L_0x011d:
            r11 = r5
            com.airbnb.lottie.model.animatable.AnimatableTransform r14 = new com.airbnb.lottie.model.animatable.AnimatableTransform
            r6 = r14
            r6.<init>(r7, r8, r9, r10, r11, r12, r13)
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.AnimatableTransformParser.parse(android.util.JsonReader, com.airbnb.lottie.LottieComposition):com.airbnb.lottie.model.animatable.AnimatableTransform");
    }
}

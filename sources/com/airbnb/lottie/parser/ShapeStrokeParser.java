package com.airbnb.lottie.parser;

class ShapeStrokeParser {
    private ShapeStrokeParser() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x0085  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0089  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00c6  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00d1  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x010f  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x0119  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x0134  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0141  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x014e  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x0154  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x015a  */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x0160  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.airbnb.lottie.model.content.ShapeStroke parse(android.util.JsonReader r17, com.airbnb.lottie.LottieComposition r18) throws java.io.IOException {
        /*
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r1 = 0
            r2 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
        L_0x000c:
            boolean r9 = r17.hasNext()
            if (r9 == 0) goto L_0x0166
            java.lang.String r9 = r17.nextName()
            int r10 = r9.hashCode()
            r11 = 99
            java.lang.String r12 = "o"
            java.lang.String r13 = "d"
            r14 = 111(0x6f, float:1.56E-43)
            r15 = 100
            r16 = -1
            r0 = 1
            if (r10 == r11) goto L_0x0077
            if (r10 == r15) goto L_0x006f
            if (r10 == r14) goto L_0x0067
            r11 = 119(0x77, float:1.67E-43)
            if (r10 == r11) goto L_0x005c
            r11 = 3447(0xd77, float:4.83E-42)
            if (r10 == r11) goto L_0x0052
            r11 = 3454(0xd7e, float:4.84E-42)
            if (r10 == r11) goto L_0x0048
            r11 = 3519(0xdbf, float:4.931E-42)
            if (r10 == r11) goto L_0x003e
            goto L_0x0081
        L_0x003e:
            java.lang.String r10 = "nm"
            boolean r9 = r9.equals(r10)
            if (r9 == 0) goto L_0x0081
            r9 = 0
            goto L_0x0082
        L_0x0048:
            java.lang.String r10 = "lj"
            boolean r9 = r9.equals(r10)
            if (r9 == 0) goto L_0x0081
            r9 = 5
            goto L_0x0082
        L_0x0052:
            java.lang.String r10 = "lc"
            boolean r9 = r9.equals(r10)
            if (r9 == 0) goto L_0x0081
            r9 = 4
            goto L_0x0082
        L_0x005c:
            java.lang.String r10 = "w"
            boolean r9 = r9.equals(r10)
            if (r9 == 0) goto L_0x0081
            r9 = 2
            goto L_0x0082
        L_0x0067:
            boolean r9 = r9.equals(r12)
            if (r9 == 0) goto L_0x0081
            r9 = 3
            goto L_0x0082
        L_0x006f:
            boolean r9 = r9.equals(r13)
            if (r9 == 0) goto L_0x0081
            r9 = 6
            goto L_0x0082
        L_0x0077:
            java.lang.String r10 = "c"
            boolean r9 = r9.equals(r10)
            if (r9 == 0) goto L_0x0081
            r9 = 1
            goto L_0x0082
        L_0x0081:
            r9 = -1
        L_0x0082:
            switch(r9) {
                case 0: goto L_0x0160;
                case 1: goto L_0x015a;
                case 2: goto L_0x0154;
                case 3: goto L_0x014e;
                case 4: goto L_0x0141;
                case 5: goto L_0x0134;
                case 6: goto L_0x0089;
                default: goto L_0x0085;
            }
        L_0x0085:
            r17.skipValue()
            goto L_0x000c
        L_0x0089:
            r17.beginArray()
        L_0x008c:
            boolean r9 = r17.hasNext()
            if (r9 == 0) goto L_0x0121
            r17.beginObject()
            r9 = 0
            r10 = 0
        L_0x0097:
            boolean r11 = r17.hasNext()
            if (r11 == 0) goto L_0x00da
            java.lang.String r11 = r17.nextName()
            int r14 = r11.hashCode()
            r15 = 110(0x6e, float:1.54E-43)
            if (r14 == r15) goto L_0x00b9
            r15 = 118(0x76, float:1.65E-43)
            if (r14 == r15) goto L_0x00ae
            goto L_0x00c3
        L_0x00ae:
            java.lang.String r14 = "v"
            boolean r11 = r11.equals(r14)
            if (r11 == 0) goto L_0x00c3
            r11 = 1
            goto L_0x00c4
        L_0x00b9:
            java.lang.String r14 = "n"
            boolean r11 = r11.equals(r14)
            if (r11 == 0) goto L_0x00c3
            r11 = 0
            goto L_0x00c4
        L_0x00c3:
            r11 = -1
        L_0x00c4:
            if (r11 == 0) goto L_0x00d1
            if (r11 == r0) goto L_0x00cc
            r17.skipValue()
            goto L_0x00d5
        L_0x00cc:
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r10 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r17, r18)
            goto L_0x00d5
        L_0x00d1:
            java.lang.String r9 = r17.nextString()
        L_0x00d5:
            r14 = 111(0x6f, float:1.56E-43)
            r15 = 100
            goto L_0x0097
        L_0x00da:
            r17.endObject()
            int r11 = r9.hashCode()
            r14 = 100
            if (r11 == r14) goto L_0x0102
            r15 = 103(0x67, float:1.44E-43)
            if (r11 == r15) goto L_0x00f6
            r15 = 111(0x6f, float:1.56E-43)
            if (r11 == r15) goto L_0x00ee
            goto L_0x010c
        L_0x00ee:
            boolean r9 = r9.equals(r12)
            if (r9 == 0) goto L_0x010c
            r9 = 0
            goto L_0x010d
        L_0x00f6:
            r15 = 111(0x6f, float:1.56E-43)
            java.lang.String r11 = "g"
            boolean r9 = r9.equals(r11)
            if (r9 == 0) goto L_0x010c
            r9 = 2
            goto L_0x010d
        L_0x0102:
            r15 = 111(0x6f, float:1.56E-43)
            boolean r9 = r9.equals(r13)
            if (r9 == 0) goto L_0x010c
            r9 = 1
            goto L_0x010d
        L_0x010c:
            r9 = -1
        L_0x010d:
            if (r9 == 0) goto L_0x0119
            r11 = 2
            if (r9 == r0) goto L_0x0115
            if (r9 == r11) goto L_0x0115
            goto L_0x011b
        L_0x0115:
            r3.add(r10)
            goto L_0x011b
        L_0x0119:
            r11 = 2
            r2 = r10
        L_0x011b:
            r14 = 111(0x6f, float:1.56E-43)
            r15 = 100
            goto L_0x008c
        L_0x0121:
            r17.endArray()
            int r9 = r3.size()
            if (r9 != r0) goto L_0x000c
            r0 = 0
            java.lang.Object r0 = r3.get(r0)
            r3.add(r0)
            goto L_0x000c
        L_0x0134:
            com.airbnb.lottie.model.content.ShapeStroke$LineJoinType[] r8 = com.airbnb.lottie.model.content.ShapeStroke.LineJoinType.values()
            int r9 = r17.nextInt()
            int r9 = r9 - r0
            r8 = r8[r9]
            goto L_0x000c
        L_0x0141:
            com.airbnb.lottie.model.content.ShapeStroke$LineCapType[] r7 = com.airbnb.lottie.model.content.ShapeStroke.LineCapType.values()
            int r9 = r17.nextInt()
            int r9 = r9 - r0
            r7 = r7[r9]
            goto L_0x000c
        L_0x014e:
            com.airbnb.lottie.model.animatable.AnimatableIntegerValue r5 = com.airbnb.lottie.parser.AnimatableValueParser.parseInteger(r17, r18)
            goto L_0x000c
        L_0x0154:
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r6 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r17, r18)
            goto L_0x000c
        L_0x015a:
            com.airbnb.lottie.model.animatable.AnimatableColorValue r4 = com.airbnb.lottie.parser.AnimatableValueParser.parseColor(r17, r18)
            goto L_0x000c
        L_0x0160:
            java.lang.String r1 = r17.nextString()
            goto L_0x000c
        L_0x0166:
            com.airbnb.lottie.model.content.ShapeStroke r9 = new com.airbnb.lottie.model.content.ShapeStroke
            r0 = r9
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.ShapeStrokeParser.parse(android.util.JsonReader, com.airbnb.lottie.LottieComposition):com.airbnb.lottie.model.content.ShapeStroke");
    }
}

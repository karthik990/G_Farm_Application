package com.airbnb.lottie.parser;

class FontCharacterParser {
    private FontCharacterParser() {
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.airbnb.lottie.model.FontCharacter parse(android.util.JsonReader r16, com.airbnb.lottie.LottieComposition r17) throws java.io.IOException {
        /*
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r16.beginObject()
            r0 = 0
            r2 = 0
            r4 = 0
            r9 = r0
            r10 = r9
            r5 = r2
            r7 = r5
            r2 = 0
        L_0x0011:
            boolean r0 = r16.hasNext()
            if (r0 == 0) goto L_0x00d0
            java.lang.String r0 = r16.nextName()
            int r11 = r0.hashCode()
            r12 = 5
            r13 = 4
            r14 = 3
            r15 = 2
            r3 = 1
            switch(r11) {
                case -1866931350: goto L_0x005d;
                case 119: goto L_0x0052;
                case 3173: goto L_0x0048;
                case 3076010: goto L_0x003e;
                case 3530753: goto L_0x0033;
                case 109780401: goto L_0x0028;
                default: goto L_0x0027;
            }
        L_0x0027:
            goto L_0x0067
        L_0x0028:
            java.lang.String r11 = "style"
            boolean r0 = r0.equals(r11)
            if (r0 == 0) goto L_0x0067
            r0 = 3
            goto L_0x0068
        L_0x0033:
            java.lang.String r11 = "size"
            boolean r0 = r0.equals(r11)
            if (r0 == 0) goto L_0x0067
            r0 = 1
            goto L_0x0068
        L_0x003e:
            java.lang.String r11 = "data"
            boolean r0 = r0.equals(r11)
            if (r0 == 0) goto L_0x0067
            r0 = 5
            goto L_0x0068
        L_0x0048:
            java.lang.String r11 = "ch"
            boolean r0 = r0.equals(r11)
            if (r0 == 0) goto L_0x0067
            r0 = 0
            goto L_0x0068
        L_0x0052:
            java.lang.String r11 = "w"
            boolean r0 = r0.equals(r11)
            if (r0 == 0) goto L_0x0067
            r0 = 2
            goto L_0x0068
        L_0x005d:
            java.lang.String r11 = "fFamily"
            boolean r0 = r0.equals(r11)
            if (r0 == 0) goto L_0x0067
            r0 = 4
            goto L_0x0068
        L_0x0067:
            r0 = -1
        L_0x0068:
            if (r0 == 0) goto L_0x00c6
            if (r0 == r3) goto L_0x00c0
            if (r0 == r15) goto L_0x00ba
            if (r0 == r14) goto L_0x00b4
            if (r0 == r13) goto L_0x00ae
            if (r0 == r12) goto L_0x0078
            r16.skipValue()
            goto L_0x0011
        L_0x0078:
            r16.beginObject()
        L_0x007b:
            boolean r0 = r16.hasNext()
            if (r0 == 0) goto L_0x00a9
            java.lang.String r0 = r16.nextName()
            java.lang.String r3 = "shapes"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x00a5
            r16.beginArray()
        L_0x0091:
            boolean r0 = r16.hasNext()
            if (r0 == 0) goto L_0x00a1
            com.airbnb.lottie.model.content.ContentModel r0 = com.airbnb.lottie.parser.ContentModelParser.parse(r16, r17)
            com.airbnb.lottie.model.content.ShapeGroup r0 = (com.airbnb.lottie.model.content.ShapeGroup) r0
            r1.add(r0)
            goto L_0x0091
        L_0x00a1:
            r16.endArray()
            goto L_0x007b
        L_0x00a5:
            r16.skipValue()
            goto L_0x007b
        L_0x00a9:
            r16.endObject()
            goto L_0x0011
        L_0x00ae:
            java.lang.String r10 = r16.nextString()
            goto L_0x0011
        L_0x00b4:
            java.lang.String r9 = r16.nextString()
            goto L_0x0011
        L_0x00ba:
            double r7 = r16.nextDouble()
            goto L_0x0011
        L_0x00c0:
            double r5 = r16.nextDouble()
            goto L_0x0011
        L_0x00c6:
            java.lang.String r0 = r16.nextString()
            char r2 = r0.charAt(r4)
            goto L_0x0011
        L_0x00d0:
            r16.endObject()
            com.airbnb.lottie.model.FontCharacter r11 = new com.airbnb.lottie.model.FontCharacter
            r0 = r11
            r3 = r5
            r5 = r7
            r7 = r9
            r8 = r10
            r0.<init>(r1, r2, r3, r5, r7, r8)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.FontCharacterParser.parse(android.util.JsonReader, com.airbnb.lottie.LottieComposition):com.airbnb.lottie.model.FontCharacter");
    }
}

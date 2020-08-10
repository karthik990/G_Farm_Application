package com.airbnb.lottie.parser;

class ContentModelParser {
    private ContentModelParser() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00b8, code lost:
        if (r2.equals("gs") != false) goto L_0x00e4;
     */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0043 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.airbnb.lottie.model.content.ContentModel parse(android.util.JsonReader r9, com.airbnb.lottie.LottieComposition r10) throws java.io.IOException {
        /*
            r9.beginObject()
            r0 = 2
            r1 = 2
        L_0x0005:
            boolean r2 = r9.hasNext()
            r3 = 0
            r4 = -1
            r5 = 1
            r6 = 0
            if (r2 == 0) goto L_0x0048
            java.lang.String r2 = r9.nextName()
            int r7 = r2.hashCode()
            r8 = 100
            if (r7 == r8) goto L_0x002b
            r8 = 3717(0xe85, float:5.209E-42)
            if (r7 == r8) goto L_0x0020
            goto L_0x0035
        L_0x0020:
            java.lang.String r7 = "ty"
            boolean r2 = r2.equals(r7)
            if (r2 == 0) goto L_0x0035
            r2 = 0
            goto L_0x0036
        L_0x002b:
            java.lang.String r7 = "d"
            boolean r2 = r2.equals(r7)
            if (r2 == 0) goto L_0x0035
            r2 = 1
            goto L_0x0036
        L_0x0035:
            r2 = -1
        L_0x0036:
            if (r2 == 0) goto L_0x0043
            if (r2 == r5) goto L_0x003e
            r9.skipValue()
            goto L_0x0005
        L_0x003e:
            int r1 = r9.nextInt()
            goto L_0x0005
        L_0x0043:
            java.lang.String r2 = r9.nextString()
            goto L_0x0049
        L_0x0048:
            r2 = r6
        L_0x0049:
            if (r2 != 0) goto L_0x004c
            return r6
        L_0x004c:
            int r7 = r2.hashCode()
            switch(r7) {
                case 3239: goto L_0x00d9;
                case 3270: goto L_0x00cf;
                case 3295: goto L_0x00c5;
                case 3307: goto L_0x00bb;
                case 3308: goto L_0x00b2;
                case 3488: goto L_0x00a7;
                case 3633: goto L_0x009c;
                case 3646: goto L_0x0091;
                case 3669: goto L_0x0086;
                case 3679: goto L_0x007a;
                case 3681: goto L_0x006e;
                case 3705: goto L_0x0061;
                case 3710: goto L_0x0055;
                default: goto L_0x0053;
            }
        L_0x0053:
            goto L_0x00e3
        L_0x0055:
            java.lang.String r0 = "tr"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 5
            goto L_0x00e4
        L_0x0061:
            java.lang.String r0 = "tm"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 9
            goto L_0x00e4
        L_0x006e:
            java.lang.String r0 = "st"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 1
            goto L_0x00e4
        L_0x007a:
            java.lang.String r0 = "sr"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 10
            goto L_0x00e4
        L_0x0086:
            java.lang.String r0 = "sh"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 6
            goto L_0x00e4
        L_0x0091:
            java.lang.String r0 = "rp"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 12
            goto L_0x00e4
        L_0x009c:
            java.lang.String r0 = "rc"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 8
            goto L_0x00e4
        L_0x00a7:
            java.lang.String r0 = "mm"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 11
            goto L_0x00e4
        L_0x00b2:
            java.lang.String r3 = "gs"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x00e3
            goto L_0x00e4
        L_0x00bb:
            java.lang.String r0 = "gr"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 0
            goto L_0x00e4
        L_0x00c5:
            java.lang.String r0 = "gf"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 4
            goto L_0x00e4
        L_0x00cf:
            java.lang.String r0 = "fl"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 3
            goto L_0x00e4
        L_0x00d9:
            java.lang.String r0 = "el"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 7
            goto L_0x00e4
        L_0x00e3:
            r0 = -1
        L_0x00e4:
            switch(r0) {
                case 0: goto L_0x013f;
                case 1: goto L_0x013a;
                case 2: goto L_0x0135;
                case 3: goto L_0x0130;
                case 4: goto L_0x012b;
                case 5: goto L_0x0126;
                case 6: goto L_0x0121;
                case 7: goto L_0x011c;
                case 8: goto L_0x0117;
                case 9: goto L_0x0112;
                case 10: goto L_0x010d;
                case 11: goto L_0x0103;
                case 12: goto L_0x00fe;
                default: goto L_0x00e7;
            }
        L_0x00e7:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r0 = "Unknown shape type "
            r10.append(r0)
            r10.append(r2)
            java.lang.String r10 = r10.toString()
            java.lang.String r0 = "LOTTIE"
            android.util.Log.w(r0, r10)
            goto L_0x0143
        L_0x00fe:
            com.airbnb.lottie.model.content.Repeater r6 = com.airbnb.lottie.parser.RepeaterParser.parse(r9, r10)
            goto L_0x0143
        L_0x0103:
            com.airbnb.lottie.model.content.MergePaths r6 = com.airbnb.lottie.parser.MergePathsParser.parse(r9)
            java.lang.String r0 = "Animation contains merge paths. Merge paths are only supported on KitKat+ and must be manually enabled by calling enableMergePathsForKitKatAndAbove()."
            r10.addWarning(r0)
            goto L_0x0143
        L_0x010d:
            com.airbnb.lottie.model.content.PolystarShape r6 = com.airbnb.lottie.parser.PolystarShapeParser.parse(r9, r10)
            goto L_0x0143
        L_0x0112:
            com.airbnb.lottie.model.content.ShapeTrimPath r6 = com.airbnb.lottie.parser.ShapeTrimPathParser.parse(r9, r10)
            goto L_0x0143
        L_0x0117:
            com.airbnb.lottie.model.content.RectangleShape r6 = com.airbnb.lottie.parser.RectangleShapeParser.parse(r9, r10)
            goto L_0x0143
        L_0x011c:
            com.airbnb.lottie.model.content.CircleShape r6 = com.airbnb.lottie.parser.CircleShapeParser.parse(r9, r10, r1)
            goto L_0x0143
        L_0x0121:
            com.airbnb.lottie.model.content.ShapePath r6 = com.airbnb.lottie.parser.ShapePathParser.parse(r9, r10)
            goto L_0x0143
        L_0x0126:
            com.airbnb.lottie.model.animatable.AnimatableTransform r6 = com.airbnb.lottie.parser.AnimatableTransformParser.parse(r9, r10)
            goto L_0x0143
        L_0x012b:
            com.airbnb.lottie.model.content.GradientFill r6 = com.airbnb.lottie.parser.GradientFillParser.parse(r9, r10)
            goto L_0x0143
        L_0x0130:
            com.airbnb.lottie.model.content.ShapeFill r6 = com.airbnb.lottie.parser.ShapeFillParser.parse(r9, r10)
            goto L_0x0143
        L_0x0135:
            com.airbnb.lottie.model.content.GradientStroke r6 = com.airbnb.lottie.parser.GradientStrokeParser.parse(r9, r10)
            goto L_0x0143
        L_0x013a:
            com.airbnb.lottie.model.content.ShapeStroke r6 = com.airbnb.lottie.parser.ShapeStrokeParser.parse(r9, r10)
            goto L_0x0143
        L_0x013f:
            com.airbnb.lottie.model.content.ShapeGroup r6 = com.airbnb.lottie.parser.ShapeGroupParser.parse(r9, r10)
        L_0x0143:
            boolean r10 = r9.hasNext()
            if (r10 == 0) goto L_0x014d
            r9.skipValue()
            goto L_0x0143
        L_0x014d:
            r9.endObject()
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.ContentModelParser.parse(android.util.JsonReader, com.airbnb.lottie.LottieComposition):com.airbnb.lottie.model.content.ContentModel");
    }
}

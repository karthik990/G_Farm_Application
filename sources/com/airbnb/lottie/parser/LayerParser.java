package com.airbnb.lottie.parser;

import android.graphics.Rect;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.model.layer.Layer.LayerType;
import com.airbnb.lottie.model.layer.Layer.MatteType;
import java.util.Collections;
import java.util.List;

public class LayerParser {
    private LayerParser() {
    }

    public static Layer parse(LottieComposition lottieComposition) {
        LottieComposition lottieComposition2 = lottieComposition;
        Rect bounds = lottieComposition.getBounds();
        List emptyList = Collections.emptyList();
        LayerType layerType = LayerType.PreComp;
        List emptyList2 = Collections.emptyList();
        AnimatableTransform animatableTransform = r4;
        AnimatableTransform animatableTransform2 = new AnimatableTransform();
        Layer layer = new Layer(emptyList, lottieComposition2, "__container", -1, layerType, -1, null, emptyList2, animatableTransform, 0, 0, 0, 0.0f, 0.0f, bounds.width(), bounds.height(), null, null, Collections.emptyList(), MatteType.None, null);
        return layer;
    }

    /* JADX WARNING: Removed duplicated region for block: B:115:0x0255  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0278  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.airbnb.lottie.model.layer.Layer parse(android.util.JsonReader r38, com.airbnb.lottie.LottieComposition r39) throws java.io.IOException {
        /*
            r7 = r39
            com.airbnb.lottie.model.layer.Layer$MatteType r0 = com.airbnb.lottie.model.layer.Layer.MatteType.None
            java.util.ArrayList r10 = new java.util.ArrayList
            r10.<init>()
            java.util.ArrayList r8 = new java.util.ArrayList
            r8.<init>()
            r38.beginObject()
            r1 = 1065353216(0x3f800000, float:1.0)
            java.lang.Float r9 = java.lang.Float.valueOf(r1)
            r2 = 0
            r11 = 0
            java.lang.Float r12 = java.lang.Float.valueOf(r11)
            r3 = 0
            r4 = 0
            r13 = -1
            r28 = r0
            r16 = r2
            r17 = r16
            r29 = r17
            r30 = r29
            r31 = r30
            r32 = r31
            r18 = r4
            r25 = r13
            r0 = 0
            r1 = 0
            r15 = 1065353216(0x3f800000, float:1.0)
            r20 = 0
            r21 = 0
            r22 = 0
            r23 = 0
            r24 = 0
            r27 = 0
            r13 = r32
            r14 = r13
        L_0x0047:
            boolean r2 = r38.hasNext()
            if (r2 == 0) goto L_0x0334
            java.lang.String r2 = r38.nextName()
            int r4 = r2.hashCode()
            java.lang.String r5 = "nm"
            r6 = 1
            r33 = -1
            switch(r4) {
                case -995424086: goto L_0x0154;
                case -903568142: goto L_0x0148;
                case 104: goto L_0x013d;
                case 116: goto L_0x0131;
                case 119: goto L_0x0125;
                case 3177: goto L_0x011a;
                case 3233: goto L_0x010f;
                case 3367: goto L_0x0104;
                case 3432: goto L_0x00f9;
                case 3519: goto L_0x00f0;
                case 3553: goto L_0x00e4;
                case 3664: goto L_0x00d9;
                case 3669: goto L_0x00cd;
                case 3679: goto L_0x00c0;
                case 3681: goto L_0x00b3;
                case 3684: goto L_0x00a7;
                case 3705: goto L_0x009a;
                case 3712: goto L_0x008d;
                case 3717: goto L_0x0081;
                case 104415: goto L_0x0076;
                case 108390670: goto L_0x006b;
                case 1441620890: goto L_0x005f;
                default: goto L_0x005d;
            }
        L_0x005d:
            goto L_0x015e
        L_0x005f:
            java.lang.String r4 = "masksProperties"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x015e
            r2 = 10
            goto L_0x015f
        L_0x006b:
            java.lang.String r4 = "refId"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x015e
            r2 = 2
            goto L_0x015f
        L_0x0076:
            java.lang.String r4 = "ind"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x015e
            r2 = 1
            goto L_0x015f
        L_0x0081:
            java.lang.String r4 = "ty"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x015e
            r2 = 3
            goto L_0x015f
        L_0x008d:
            java.lang.String r4 = "tt"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x015e
            r2 = 9
            goto L_0x015f
        L_0x009a:
            java.lang.String r4 = "tm"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x015e
            r2 = 20
            goto L_0x015f
        L_0x00a7:
            java.lang.String r4 = "sw"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x015e
            r2 = 5
            goto L_0x015f
        L_0x00b3:
            java.lang.String r4 = "st"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x015e
            r2 = 15
            goto L_0x015f
        L_0x00c0:
            java.lang.String r4 = "sr"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x015e
            r2 = 14
            goto L_0x015f
        L_0x00cd:
            java.lang.String r4 = "sh"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x015e
            r2 = 6
            goto L_0x015f
        L_0x00d9:
            java.lang.String r4 = "sc"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x015e
            r2 = 7
            goto L_0x015f
        L_0x00e4:
            java.lang.String r4 = "op"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x015e
            r2 = 19
            goto L_0x015f
        L_0x00f0:
            boolean r2 = r2.equals(r5)
            if (r2 == 0) goto L_0x015e
            r2 = 0
            goto L_0x015f
        L_0x00f9:
            java.lang.String r4 = "ks"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x015e
            r2 = 8
            goto L_0x015f
        L_0x0104:
            java.lang.String r4 = "ip"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x015e
            r2 = 18
            goto L_0x015f
        L_0x010f:
            java.lang.String r4 = "ef"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x015e
            r2 = 13
            goto L_0x015f
        L_0x011a:
            java.lang.String r4 = "cl"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x015e
            r2 = 21
            goto L_0x015f
        L_0x0125:
            java.lang.String r4 = "w"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x015e
            r2 = 16
            goto L_0x015f
        L_0x0131:
            java.lang.String r4 = "t"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x015e
            r2 = 12
            goto L_0x015f
        L_0x013d:
            java.lang.String r4 = "h"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x015e
            r2 = 17
            goto L_0x015f
        L_0x0148:
            java.lang.String r4 = "shapes"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x015e
            r2 = 11
            goto L_0x015f
        L_0x0154:
            java.lang.String r4 = "parent"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x015e
            r2 = 4
            goto L_0x015f
        L_0x015e:
            r2 = -1
        L_0x015f:
            switch(r2) {
                case 0: goto L_0x032a;
                case 1: goto L_0x0320;
                case 2: goto L_0x0319;
                case 3: goto L_0x0301;
                case 4: goto L_0x02f7;
                case 5: goto L_0x02e6;
                case 6: goto L_0x02d5;
                case 7: goto L_0x02ca;
                case 8: goto L_0x02c2;
                case 9: goto L_0x02b4;
                case 10: goto L_0x029c;
                case 11: goto L_0x0282;
                case 12: goto L_0x0222;
                case 13: goto L_0x01c4;
                case 14: goto L_0x01bb;
                case 15: goto L_0x01b0;
                case 16: goto L_0x019e;
                case 17: goto L_0x018c;
                case 18: goto L_0x0183;
                case 19: goto L_0x017a;
                case 20: goto L_0x0172;
                case 21: goto L_0x0169;
                default: goto L_0x0162;
            }
        L_0x0162:
            r2 = r38
            r38.skipValue()
            goto L_0x0330
        L_0x0169:
            java.lang.String r2 = r38.nextString()
            r14 = r2
            r2 = r38
            goto L_0x0330
        L_0x0172:
            r2 = r38
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r32 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r2, r7, r3)
            goto L_0x0330
        L_0x017a:
            r2 = r38
            double r4 = r38.nextDouble()
            float r1 = (float) r4
            goto L_0x0330
        L_0x0183:
            r2 = r38
            double r4 = r38.nextDouble()
            float r0 = (float) r4
            goto L_0x0330
        L_0x018c:
            r2 = r38
            int r4 = r38.nextInt()
            float r4 = (float) r4
            float r5 = com.airbnb.lottie.utils.C0894Utils.dpScale()
            float r4 = r4 * r5
            int r4 = (int) r4
            r24 = r4
            goto L_0x0330
        L_0x019e:
            r2 = r38
            int r4 = r38.nextInt()
            float r4 = (float) r4
            float r5 = com.airbnb.lottie.utils.C0894Utils.dpScale()
            float r4 = r4 * r5
            int r4 = (int) r4
            r23 = r4
            goto L_0x0330
        L_0x01b0:
            r2 = r38
            double r4 = r38.nextDouble()
            float r4 = (float) r4
            r27 = r4
            goto L_0x0330
        L_0x01bb:
            r2 = r38
            double r4 = r38.nextDouble()
            float r15 = (float) r4
            goto L_0x0330
        L_0x01c4:
            r2 = r38
            r38.beginArray()
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
        L_0x01ce:
            boolean r6 = r38.hasNext()
            if (r6 == 0) goto L_0x0209
            r38.beginObject()
        L_0x01d7:
            boolean r6 = r38.hasNext()
            if (r6 == 0) goto L_0x0203
            java.lang.String r6 = r38.nextName()
            int r3 = r6.hashCode()
            r11 = 3519(0xdbf, float:4.931E-42)
            if (r3 == r11) goto L_0x01ea
            goto L_0x01f2
        L_0x01ea:
            boolean r3 = r6.equals(r5)
            if (r3 == 0) goto L_0x01f2
            r3 = 0
            goto L_0x01f3
        L_0x01f2:
            r3 = -1
        L_0x01f3:
            if (r3 == 0) goto L_0x01f9
            r38.skipValue()
            goto L_0x0200
        L_0x01f9:
            java.lang.String r3 = r38.nextString()
            r4.add(r3)
        L_0x0200:
            r3 = 0
            r11 = 0
            goto L_0x01d7
        L_0x0203:
            r38.endObject()
            r3 = 0
            r11 = 0
            goto L_0x01ce
        L_0x0209:
            r38.endArray()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r5 = "Lottie doesn't support layer effects. If you are using them for  fills, strokes, trim paths etc. then try adding them directly as contents  in your shape. Found: "
            r3.append(r5)
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r7.addWarning(r3)
            goto L_0x0330
        L_0x0222:
            r2 = r38
            r38.beginObject()
        L_0x0227:
            boolean r3 = r38.hasNext()
            if (r3 == 0) goto L_0x027d
            java.lang.String r3 = r38.nextName()
            int r4 = r3.hashCode()
            r5 = 97
            if (r4 == r5) goto L_0x0248
            r5 = 100
            if (r4 == r5) goto L_0x023e
            goto L_0x0252
        L_0x023e:
            java.lang.String r4 = "d"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0252
            r3 = 0
            goto L_0x0253
        L_0x0248:
            java.lang.String r4 = "a"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0252
            r3 = 1
            goto L_0x0253
        L_0x0252:
            r3 = -1
        L_0x0253:
            if (r3 == 0) goto L_0x0278
            if (r3 == r6) goto L_0x025b
            r38.skipValue()
            goto L_0x0227
        L_0x025b:
            r38.beginArray()
            boolean r3 = r38.hasNext()
            if (r3 == 0) goto L_0x026a
            com.airbnb.lottie.model.animatable.AnimatableTextProperties r3 = com.airbnb.lottie.parser.AnimatableTextPropertiesParser.parse(r38, r39)
            r31 = r3
        L_0x026a:
            boolean r3 = r38.hasNext()
            if (r3 == 0) goto L_0x0274
            r38.skipValue()
            goto L_0x026a
        L_0x0274:
            r38.endArray()
            goto L_0x0227
        L_0x0278:
            com.airbnb.lottie.model.animatable.AnimatableTextFrame r30 = com.airbnb.lottie.parser.AnimatableValueParser.parseDocumentData(r38, r39)
            goto L_0x0227
        L_0x027d:
            r38.endObject()
            goto L_0x0330
        L_0x0282:
            r2 = r38
            r38.beginArray()
        L_0x0287:
            boolean r3 = r38.hasNext()
            if (r3 == 0) goto L_0x0297
            com.airbnb.lottie.model.content.ContentModel r3 = com.airbnb.lottie.parser.ContentModelParser.parse(r38, r39)
            if (r3 == 0) goto L_0x0287
            r8.add(r3)
            goto L_0x0287
        L_0x0297:
            r38.endArray()
            goto L_0x0330
        L_0x029c:
            r2 = r38
            r38.beginArray()
        L_0x02a1:
            boolean r3 = r38.hasNext()
            if (r3 == 0) goto L_0x02af
            com.airbnb.lottie.model.content.Mask r3 = com.airbnb.lottie.parser.MaskParser.parse(r38, r39)
            r10.add(r3)
            goto L_0x02a1
        L_0x02af:
            r38.endArray()
            goto L_0x0330
        L_0x02b4:
            r2 = r38
            com.airbnb.lottie.model.layer.Layer$MatteType[] r3 = com.airbnb.lottie.model.layer.Layer.MatteType.values()
            int r4 = r38.nextInt()
            r28 = r3[r4]
            goto L_0x0330
        L_0x02c2:
            r2 = r38
            com.airbnb.lottie.model.animatable.AnimatableTransform r29 = com.airbnb.lottie.parser.AnimatableTransformParser.parse(r38, r39)
            goto L_0x0330
        L_0x02ca:
            r2 = r38
            java.lang.String r3 = r38.nextString()
            int r22 = android.graphics.Color.parseColor(r3)
            goto L_0x0330
        L_0x02d5:
            r2 = r38
            int r3 = r38.nextInt()
            float r3 = (float) r3
            float r4 = com.airbnb.lottie.utils.C0894Utils.dpScale()
            float r3 = r3 * r4
            int r3 = (int) r3
            r21 = r3
            goto L_0x0330
        L_0x02e6:
            r2 = r38
            int r3 = r38.nextInt()
            float r3 = (float) r3
            float r4 = com.airbnb.lottie.utils.C0894Utils.dpScale()
            float r3 = r3 * r4
            int r3 = (int) r3
            r20 = r3
            goto L_0x0330
        L_0x02f7:
            r2 = r38
            int r3 = r38.nextInt()
            long r3 = (long) r3
            r25 = r3
            goto L_0x0330
        L_0x0301:
            r2 = r38
            int r3 = r38.nextInt()
            com.airbnb.lottie.model.layer.Layer$LayerType r4 = com.airbnb.lottie.model.layer.Layer.LayerType.Unknown
            int r4 = r4.ordinal()
            if (r3 >= r4) goto L_0x0316
            com.airbnb.lottie.model.layer.Layer$LayerType[] r4 = com.airbnb.lottie.model.layer.Layer.LayerType.values()
            r16 = r4[r3]
            goto L_0x0330
        L_0x0316:
            com.airbnb.lottie.model.layer.Layer$LayerType r16 = com.airbnb.lottie.model.layer.Layer.LayerType.Unknown
            goto L_0x0330
        L_0x0319:
            r2 = r38
            java.lang.String r17 = r38.nextString()
            goto L_0x0330
        L_0x0320:
            r2 = r38
            int r3 = r38.nextInt()
            long r3 = (long) r3
            r18 = r3
            goto L_0x0330
        L_0x032a:
            r2 = r38
            java.lang.String r13 = r38.nextString()
        L_0x0330:
            r3 = 0
            r11 = 0
            goto L_0x0047
        L_0x0334:
            r2 = r38
            r38.endObject()
            float r11 = r0 / r15
            float r33 = r1 / r15
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            r0 = 0
            int r1 = (r11 > r0 ? 1 : (r11 == r0 ? 0 : -1))
            if (r1 <= 0) goto L_0x0367
            com.airbnb.lottie.value.Keyframe r5 = new com.airbnb.lottie.value.Keyframe
            r4 = 0
            r34 = 0
            java.lang.Float r36 = java.lang.Float.valueOf(r11)
            r0 = r5
            r1 = r39
            r2 = r12
            r3 = r12
            r37 = r5
            r5 = r34
            r34 = r15
            r15 = r6
            r6 = r36
            r0.<init>(r1, r2, r3, r4, r5, r6)
            r0 = r37
            r15.add(r0)
            goto L_0x036a
        L_0x0367:
            r34 = r15
            r15 = r6
        L_0x036a:
            r0 = 0
            int r0 = (r33 > r0 ? 1 : (r33 == r0 ? 0 : -1))
            if (r0 <= 0) goto L_0x0370
            goto L_0x0376
        L_0x0370:
            float r0 = r39.getEndFrame()
            r33 = r0
        L_0x0376:
            com.airbnb.lottie.value.Keyframe r6 = new com.airbnb.lottie.value.Keyframe
            r4 = 0
            java.lang.Float r35 = java.lang.Float.valueOf(r33)
            r0 = r6
            r1 = r39
            r2 = r9
            r3 = r9
            r5 = r11
            r9 = r6
            r6 = r35
            r0.<init>(r1, r2, r3, r4, r5, r6)
            r15.add(r9)
            com.airbnb.lottie.value.Keyframe r9 = new com.airbnb.lottie.value.Keyframe
            r0 = 2139095039(0x7f7fffff, float:3.4028235E38)
            java.lang.Float r6 = java.lang.Float.valueOf(r0)
            r0 = r9
            r2 = r12
            r3 = r12
            r5 = r33
            r0.<init>(r1, r2, r3, r4, r5, r6)
            r15.add(r9)
            java.lang.String r0 = ".ai"
            boolean r0 = r13.endsWith(r0)
            if (r0 != 0) goto L_0x03b0
            java.lang.String r0 = "ai"
            boolean r0 = r0.equals(r14)
            if (r0 == 0) goto L_0x03b5
        L_0x03b0:
            java.lang.String r0 = "Convert your Illustrator layers to shape layers."
            r7.addWarning(r0)
        L_0x03b5:
            com.airbnb.lottie.model.layer.Layer r33 = new com.airbnb.lottie.model.layer.Layer
            r0 = r33
            r1 = r8
            r2 = r39
            r3 = r13
            r4 = r18
            r6 = r16
            r7 = r25
            r9 = r17
            r11 = r29
            r12 = r20
            r13 = r21
            r14 = r22
            r21 = r15
            r15 = r34
            r16 = r27
            r17 = r23
            r18 = r24
            r19 = r30
            r20 = r31
            r22 = r28
            r23 = r32
            r0.<init>(r1, r2, r3, r4, r6, r7, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23)
            return r33
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.LayerParser.parse(android.util.JsonReader, com.airbnb.lottie.LottieComposition):com.airbnb.lottie.model.layer.Layer");
    }
}

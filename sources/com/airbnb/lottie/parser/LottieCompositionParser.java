package com.airbnb.lottie.parser;

import android.util.JsonReader;
import androidx.collection.LongSparseArray;
import androidx.collection.SparseArrayCompat;
import com.airbnb.lottie.C0873L;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieImageAsset;
import com.airbnb.lottie.model.Font;
import com.airbnb.lottie.model.FontCharacter;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.model.layer.Layer.LayerType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LottieCompositionParser {
    private LottieCompositionParser() {
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.airbnb.lottie.LottieComposition parse(android.util.JsonReader r26) throws java.io.IOException {
        /*
            r0 = r26
            float r1 = com.airbnb.lottie.utils.C0894Utils.dpScale()
            androidx.collection.LongSparseArray r8 = new androidx.collection.LongSparseArray
            r8.<init>()
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            java.util.HashMap r9 = new java.util.HashMap
            r9.<init>()
            java.util.HashMap r10 = new java.util.HashMap
            r10.<init>()
            java.util.HashMap r12 = new java.util.HashMap
            r12.<init>()
            androidx.collection.SparseArrayCompat r11 = new androidx.collection.SparseArrayCompat
            r11.<init>()
            com.airbnb.lottie.LottieComposition r13 = new com.airbnb.lottie.LottieComposition
            r13.<init>()
            r26.beginObject()
            r2 = 0
            r2 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r14 = 0
        L_0x0032:
            boolean r15 = r26.hasNext()
            if (r15 == 0) goto L_0x0133
            java.lang.String r15 = r26.nextName()
            r16 = -1
            int r17 = r15.hashCode()
            r18 = 2
            r19 = 1
            switch(r17) {
                case -1408207997: goto L_0x00a9;
                case -1109732030: goto L_0x009f;
                case 104: goto L_0x0095;
                case 118: goto L_0x008a;
                case 119: goto L_0x007f;
                case 3276: goto L_0x0075;
                case 3367: goto L_0x006b;
                case 3553: goto L_0x0061;
                case 94623709: goto L_0x0056;
                case 97615364: goto L_0x004b;
                default: goto L_0x0049;
            }
        L_0x0049:
            goto L_0x00b3
        L_0x004b:
            java.lang.String r3 = "fonts"
            boolean r3 = r15.equals(r3)
            if (r3 == 0) goto L_0x00b3
            r3 = 8
            goto L_0x00b4
        L_0x0056:
            java.lang.String r3 = "chars"
            boolean r3 = r15.equals(r3)
            if (r3 == 0) goto L_0x00b3
            r3 = 9
            goto L_0x00b4
        L_0x0061:
            java.lang.String r3 = "op"
            boolean r3 = r15.equals(r3)
            if (r3 == 0) goto L_0x00b3
            r3 = 3
            goto L_0x00b4
        L_0x006b:
            java.lang.String r3 = "ip"
            boolean r3 = r15.equals(r3)
            if (r3 == 0) goto L_0x00b3
            r3 = 2
            goto L_0x00b4
        L_0x0075:
            java.lang.String r3 = "fr"
            boolean r3 = r15.equals(r3)
            if (r3 == 0) goto L_0x00b3
            r3 = 4
            goto L_0x00b4
        L_0x007f:
            java.lang.String r3 = "w"
            boolean r3 = r15.equals(r3)
            if (r3 == 0) goto L_0x00b3
            r3 = 0
            goto L_0x00b4
        L_0x008a:
            java.lang.String r3 = "v"
            boolean r3 = r15.equals(r3)
            if (r3 == 0) goto L_0x00b3
            r3 = 5
            goto L_0x00b4
        L_0x0095:
            java.lang.String r3 = "h"
            boolean r3 = r15.equals(r3)
            if (r3 == 0) goto L_0x00b3
            r3 = 1
            goto L_0x00b4
        L_0x009f:
            java.lang.String r3 = "layers"
            boolean r3 = r15.equals(r3)
            if (r3 == 0) goto L_0x00b3
            r3 = 6
            goto L_0x00b4
        L_0x00a9:
            java.lang.String r3 = "assets"
            boolean r3 = r15.equals(r3)
            if (r3 == 0) goto L_0x00b3
            r3 = 7
            goto L_0x00b4
        L_0x00b3:
            r3 = -1
        L_0x00b4:
            switch(r3) {
                case 0: goto L_0x0127;
                case 1: goto L_0x011e;
                case 2: goto L_0x0115;
                case 3: goto L_0x0107;
                case 4: goto L_0x00fe;
                case 5: goto L_0x00cf;
                case 6: goto L_0x00cb;
                case 7: goto L_0x00c7;
                case 8: goto L_0x00c3;
                case 9: goto L_0x00bf;
                default: goto L_0x00b7;
            }
        L_0x00b7:
            r16 = r11
            r15 = r12
            r26.skipValue()
            goto L_0x012e
        L_0x00bf:
            parseChars(r0, r13, r11)
            goto L_0x0103
        L_0x00c3:
            parseFonts(r0, r12)
            goto L_0x0103
        L_0x00c7:
            parseAssets(r0, r13, r9, r10)
            goto L_0x0103
        L_0x00cb:
            parseLayers(r0, r13, r7, r8)
            goto L_0x0103
        L_0x00cf:
            java.lang.String r3 = r26.nextString()
            java.lang.String r15 = "\\."
            java.lang.String[] r3 = r3.split(r15)
            r15 = 0
            r16 = r3[r15]
            int r20 = java.lang.Integer.parseInt(r16)
            r15 = r3[r19]
            int r21 = java.lang.Integer.parseInt(r15)
            r3 = r3[r18]
            int r22 = java.lang.Integer.parseInt(r3)
            r23 = 4
            r24 = 4
            r25 = 0
            boolean r3 = com.airbnb.lottie.utils.C0894Utils.isAtLeastVersion(r20, r21, r22, r23, r24, r25)
            if (r3 != 0) goto L_0x0103
            java.lang.String r3 = "Lottie only supports bodymovin >= 4.4.0"
            r13.addWarning(r3)
            goto L_0x0103
        L_0x00fe:
            double r14 = r26.nextDouble()
            float r14 = (float) r14
        L_0x0103:
            r16 = r11
            r15 = r12
            goto L_0x012e
        L_0x0107:
            r16 = r11
            r15 = r12
            double r11 = r26.nextDouble()
            float r3 = (float) r11
            r6 = 1008981770(0x3c23d70a, float:0.01)
            float r6 = r3 - r6
            goto L_0x012e
        L_0x0115:
            r16 = r11
            r15 = r12
            double r11 = r26.nextDouble()
            float r5 = (float) r11
            goto L_0x012e
        L_0x011e:
            r16 = r11
            r15 = r12
            int r3 = r26.nextInt()
            r4 = r3
            goto L_0x012e
        L_0x0127:
            r16 = r11
            r15 = r12
            int r2 = r26.nextInt()
        L_0x012e:
            r12 = r15
            r11 = r16
            goto L_0x0032
        L_0x0133:
            r16 = r11
            r15 = r12
            r26.endObject()
            float r0 = (float) r2
            float r0 = r0 * r1
            int r0 = (int) r0
            float r2 = (float) r4
            float r2 = r2 * r1
            int r1 = (int) r2
            android.graphics.Rect r3 = new android.graphics.Rect
            r2 = 0
            r3.<init>(r2, r2, r0, r1)
            r2 = r13
            r4 = r5
            r5 = r6
            r6 = r14
            r2.init(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.LottieCompositionParser.parse(android.util.JsonReader):com.airbnb.lottie.LottieComposition");
    }

    private static void parseLayers(JsonReader jsonReader, LottieComposition lottieComposition, List<Layer> list, LongSparseArray<Layer> longSparseArray) throws IOException {
        jsonReader.beginArray();
        int i = 0;
        while (jsonReader.hasNext()) {
            Layer parse = LayerParser.parse(jsonReader, lottieComposition);
            if (parse.getLayerType() == LayerType.Image) {
                i++;
            }
            list.add(parse);
            longSparseArray.put(parse.getId(), parse);
            if (i > 4) {
                StringBuilder sb = new StringBuilder();
                sb.append("You have ");
                sb.append(i);
                sb.append(" images. Lottie should primarily be used with shapes. If you are using Adobe Illustrator, convert the Illustrator layers to shape layers.");
                C0873L.warn(sb.toString());
            }
        }
        jsonReader.endArray();
    }

    private static void parseAssets(JsonReader jsonReader, LottieComposition lottieComposition, Map<String, List<Layer>> map, Map<String, LottieImageAsset> map2) throws IOException {
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            ArrayList arrayList = new ArrayList();
            LongSparseArray longSparseArray = new LongSparseArray();
            jsonReader.beginObject();
            String str = null;
            String str2 = null;
            String str3 = null;
            int i = 0;
            int i2 = 0;
            while (jsonReader.hasNext()) {
                String nextName = jsonReader.nextName();
                char c = 65535;
                int hashCode = nextName.hashCode();
                if (hashCode != -1109732030) {
                    if (hashCode != 104) {
                        if (hashCode != 112) {
                            if (hashCode != 117) {
                                if (hashCode != 119) {
                                    if (hashCode == 3355 && nextName.equals(TtmlNode.ATTR_ID)) {
                                        c = 0;
                                    }
                                } else if (nextName.equals("w")) {
                                    c = 2;
                                }
                            } else if (nextName.equals("u")) {
                                c = 5;
                            }
                        } else if (nextName.equals(TtmlNode.TAG_P)) {
                            c = 4;
                        }
                    } else if (nextName.equals("h")) {
                        c = 3;
                    }
                } else if (nextName.equals("layers")) {
                    c = 1;
                }
                if (c == 0) {
                    str = jsonReader.nextString();
                } else if (c == 1) {
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        Layer parse = LayerParser.parse(jsonReader, lottieComposition);
                        longSparseArray.put(parse.getId(), parse);
                        arrayList.add(parse);
                    }
                    jsonReader.endArray();
                } else if (c == 2) {
                    i = jsonReader.nextInt();
                } else if (c == 3) {
                    i2 = jsonReader.nextInt();
                } else if (c == 4) {
                    str2 = jsonReader.nextString();
                } else if (c != 5) {
                    jsonReader.skipValue();
                } else {
                    str3 = jsonReader.nextString();
                }
            }
            jsonReader.endObject();
            if (str2 != null) {
                LottieImageAsset lottieImageAsset = new LottieImageAsset(i, i2, str, str2, str3);
                map2.put(lottieImageAsset.getId(), lottieImageAsset);
                Map<String, List<Layer>> map3 = map;
            } else {
                Map<String, LottieImageAsset> map4 = map2;
                map.put(str, arrayList);
            }
        }
        jsonReader.endArray();
    }

    private static void parseFonts(JsonReader jsonReader, Map<String, Font> map) throws IOException {
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String nextName = jsonReader.nextName();
            char c = 65535;
            if (nextName.hashCode() == 3322014 && nextName.equals("list")) {
                c = 0;
            }
            if (c != 0) {
                jsonReader.skipValue();
            } else {
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    Font parse = FontParser.parse(jsonReader);
                    map.put(parse.getName(), parse);
                }
                jsonReader.endArray();
            }
        }
        jsonReader.endObject();
    }

    private static void parseChars(JsonReader jsonReader, LottieComposition lottieComposition, SparseArrayCompat<FontCharacter> sparseArrayCompat) throws IOException {
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            FontCharacter parse = FontCharacterParser.parse(jsonReader, lottieComposition);
            sparseArrayCompat.put(parse.hashCode(), parse);
        }
        jsonReader.endArray();
    }
}

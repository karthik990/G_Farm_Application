package com.airbnb.lottie.parser;

import android.util.JsonReader;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import androidx.collection.SparseArrayCompat;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.value.Keyframe;
import java.io.IOException;
import java.lang.ref.WeakReference;

class KeyframeParser {
    private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    private static final float MAX_CP_VALUE = 100.0f;
    private static SparseArrayCompat<WeakReference<Interpolator>> pathInterpolatorCache;

    KeyframeParser() {
    }

    private static SparseArrayCompat<WeakReference<Interpolator>> pathInterpolatorCache() {
        if (pathInterpolatorCache == null) {
            pathInterpolatorCache = new SparseArrayCompat<>();
        }
        return pathInterpolatorCache;
    }

    private static WeakReference<Interpolator> getInterpolator(int i) {
        WeakReference<Interpolator> weakReference;
        synchronized (KeyframeParser.class) {
            weakReference = (WeakReference) pathInterpolatorCache().get(i);
        }
        return weakReference;
    }

    private static void putInterpolator(int i, WeakReference<Interpolator> weakReference) {
        synchronized (KeyframeParser.class) {
            pathInterpolatorCache.put(i, weakReference);
        }
    }

    static <T> Keyframe<T> parse(JsonReader jsonReader, LottieComposition lottieComposition, float f, ValueParser<T> valueParser, boolean z) throws IOException {
        if (z) {
            return parseKeyframe(lottieComposition, jsonReader, f, valueParser);
        }
        return parseStaticValue(jsonReader, f, valueParser);
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x009a  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x009f  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00ad  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00b8  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00bf  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00c6  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00cd  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00d4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static <T> com.airbnb.lottie.value.Keyframe<T> parseKeyframe(com.airbnb.lottie.LottieComposition r17, android.util.JsonReader r18, float r19, com.airbnb.lottie.parser.ValueParser<T> r20) throws java.io.IOException {
        /*
            r0 = r18
            r1 = r19
            r2 = r20
            r18.beginObject()
            r5 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r12 = 0
            r14 = 0
            r15 = 0
        L_0x0012:
            boolean r10 = r18.hasNext()
            if (r10 == 0) goto L_0x00dc
            java.lang.String r10 = r18.nextName()
            r11 = -1
            int r13 = r10.hashCode()
            r3 = 101(0x65, float:1.42E-43)
            r4 = 1
            if (r13 == r3) goto L_0x008c
            r3 = 111(0x6f, float:1.56E-43)
            if (r13 == r3) goto L_0x0082
            r3 = 3701(0xe75, float:5.186E-42)
            if (r13 == r3) goto L_0x0077
            r3 = 3707(0xe7b, float:5.195E-42)
            if (r13 == r3) goto L_0x006c
            r3 = 104(0x68, float:1.46E-43)
            if (r13 == r3) goto L_0x0062
            r3 = 105(0x69, float:1.47E-43)
            if (r13 == r3) goto L_0x0058
            r3 = 115(0x73, float:1.61E-43)
            if (r13 == r3) goto L_0x004e
            r3 = 116(0x74, float:1.63E-43)
            if (r13 == r3) goto L_0x0043
            goto L_0x0096
        L_0x0043:
            java.lang.String r3 = "t"
            boolean r3 = r10.equals(r3)
            if (r3 == 0) goto L_0x0096
            r3 = 0
            goto L_0x0097
        L_0x004e:
            java.lang.String r3 = "s"
            boolean r3 = r10.equals(r3)
            if (r3 == 0) goto L_0x0096
            r3 = 1
            goto L_0x0097
        L_0x0058:
            java.lang.String r3 = "i"
            boolean r3 = r10.equals(r3)
            if (r3 == 0) goto L_0x0096
            r3 = 4
            goto L_0x0097
        L_0x0062:
            java.lang.String r3 = "h"
            boolean r3 = r10.equals(r3)
            if (r3 == 0) goto L_0x0096
            r3 = 5
            goto L_0x0097
        L_0x006c:
            java.lang.String r3 = "to"
            boolean r3 = r10.equals(r3)
            if (r3 == 0) goto L_0x0096
            r3 = 6
            goto L_0x0097
        L_0x0077:
            java.lang.String r3 = "ti"
            boolean r3 = r10.equals(r3)
            if (r3 == 0) goto L_0x0096
            r3 = 7
            goto L_0x0097
        L_0x0082:
            java.lang.String r3 = "o"
            boolean r3 = r10.equals(r3)
            if (r3 == 0) goto L_0x0096
            r3 = 3
            goto L_0x0097
        L_0x008c:
            java.lang.String r3 = "e"
            boolean r3 = r10.equals(r3)
            if (r3 == 0) goto L_0x0096
            r3 = 2
            goto L_0x0097
        L_0x0096:
            r3 = -1
        L_0x0097:
            switch(r3) {
                case 0: goto L_0x00d4;
                case 1: goto L_0x00cd;
                case 2: goto L_0x00c6;
                case 3: goto L_0x00bf;
                case 4: goto L_0x00b8;
                case 5: goto L_0x00ad;
                case 6: goto L_0x00a6;
                case 7: goto L_0x009f;
                default: goto L_0x009a;
            }
        L_0x009a:
            r18.skipValue()
            goto L_0x0012
        L_0x009f:
            android.graphics.PointF r3 = com.airbnb.lottie.parser.JsonUtils.jsonToPoint(r18, r19)
            r15 = r3
            goto L_0x0012
        L_0x00a6:
            android.graphics.PointF r3 = com.airbnb.lottie.parser.JsonUtils.jsonToPoint(r18, r19)
            r14 = r3
            goto L_0x0012
        L_0x00ad:
            int r3 = r18.nextInt()
            if (r3 != r4) goto L_0x00b4
            goto L_0x00b5
        L_0x00b4:
            r4 = 0
        L_0x00b5:
            r5 = r4
            goto L_0x0012
        L_0x00b8:
            android.graphics.PointF r3 = com.airbnb.lottie.parser.JsonUtils.jsonToPoint(r18, r19)
            r7 = r3
            goto L_0x0012
        L_0x00bf:
            android.graphics.PointF r3 = com.airbnb.lottie.parser.JsonUtils.jsonToPoint(r18, r19)
            r6 = r3
            goto L_0x0012
        L_0x00c6:
            java.lang.Object r3 = r2.parse(r0, r1)
            r8 = r3
            goto L_0x0012
        L_0x00cd:
            java.lang.Object r3 = r2.parse(r0, r1)
            r9 = r3
            goto L_0x0012
        L_0x00d4:
            double r3 = r18.nextDouble()
            float r3 = (float) r3
            r12 = r3
            goto L_0x0012
        L_0x00dc:
            r18.endObject()
            if (r5 == 0) goto L_0x00e7
            android.view.animation.Interpolator r0 = LINEAR_INTERPOLATOR
            r11 = r0
            r10 = r9
            goto L_0x0154
        L_0x00e7:
            if (r6 == 0) goto L_0x0150
            if (r7 == 0) goto L_0x0150
            float r0 = r6.x
            float r2 = -r1
            float r0 = com.airbnb.lottie.utils.MiscUtils.clamp(r0, r2, r1)
            r6.x = r0
            float r0 = r6.y
            r3 = 1120403456(0x42c80000, float:100.0)
            r4 = -1027080192(0xffffffffc2c80000, float:-100.0)
            float r0 = com.airbnb.lottie.utils.MiscUtils.clamp(r0, r4, r3)
            r6.y = r0
            float r0 = r7.x
            float r0 = com.airbnb.lottie.utils.MiscUtils.clamp(r0, r2, r1)
            r7.x = r0
            float r0 = r7.y
            float r0 = com.airbnb.lottie.utils.MiscUtils.clamp(r0, r4, r3)
            r7.y = r0
            float r0 = r6.x
            float r2 = r6.y
            float r3 = r7.x
            float r4 = r7.y
            int r0 = com.airbnb.lottie.utils.C0894Utils.hashFor(r0, r2, r3, r4)
            java.lang.ref.WeakReference r2 = getInterpolator(r0)
            if (r2 == 0) goto L_0x012c
            java.lang.Object r3 = r2.get()
            r4 = r3
            android.view.animation.Interpolator r4 = (android.view.animation.Interpolator) r4
            r16 = r4
            goto L_0x012e
        L_0x012c:
            r16 = 0
        L_0x012e:
            if (r2 == 0) goto L_0x0132
            if (r16 != 0) goto L_0x014c
        L_0x0132:
            float r2 = r6.x
            float r2 = r2 / r1
            float r3 = r6.y
            float r3 = r3 / r1
            float r4 = r7.x
            float r4 = r4 / r1
            float r5 = r7.y
            float r5 = r5 / r1
            android.view.animation.Interpolator r1 = androidx.core.view.animation.PathInterpolatorCompat.create(r2, r3, r4, r5)
            java.lang.ref.WeakReference r2 = new java.lang.ref.WeakReference     // Catch:{ ArrayIndexOutOfBoundsException -> 0x014a }
            r2.<init>(r1)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x014a }
            putInterpolator(r0, r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x014a }
        L_0x014a:
            r16 = r1
        L_0x014c:
            r10 = r8
            r11 = r16
            goto L_0x0154
        L_0x0150:
            android.view.animation.Interpolator r0 = LINEAR_INTERPOLATOR
            r11 = r0
            r10 = r8
        L_0x0154:
            com.airbnb.lottie.value.Keyframe r0 = new com.airbnb.lottie.value.Keyframe
            r13 = 0
            r7 = r0
            r8 = r17
            r7.<init>(r8, r9, r10, r11, r12, r13)
            r0.pathCp1 = r14
            r0.pathCp2 = r15
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.KeyframeParser.parseKeyframe(com.airbnb.lottie.LottieComposition, android.util.JsonReader, float, com.airbnb.lottie.parser.ValueParser):com.airbnb.lottie.value.Keyframe");
    }

    private static <T> Keyframe<T> parseStaticValue(JsonReader jsonReader, float f, ValueParser<T> valueParser) throws IOException {
        return new Keyframe<>(valueParser.parse(jsonReader, f));
    }
}

package com.nightonke.boommenu.Animation;

import android.animation.TypeEvaluator;

public class HideRgbEvaluator implements TypeEvaluator {
    private static final HideRgbEvaluator sInstance = new HideRgbEvaluator();

    private float speedMap(float f) {
        if (((double) f) <= 0.5d) {
            return 0.0f;
        }
        float f2 = (f - 0.5f) * 2.0f;
        if (f2 > 1.0f) {
            f2 = 1.0f;
        }
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        return f2;
    }

    public static HideRgbEvaluator getInstance() {
        return sInstance;
    }

    public Object evaluate(float f, Object obj, Object obj2) {
        int intValue = ((Integer) obj).intValue();
        int i = (intValue >> 24) & 255;
        int i2 = (intValue >> 16) & 255;
        int i3 = (intValue >> 8) & 255;
        int i4 = intValue & 255;
        int intValue2 = ((Integer) obj2).intValue();
        int i5 = (intValue2 >> 24) & 255;
        int i6 = (intValue2 >> 16) & 255;
        int i7 = (intValue2 >> 8) & 255;
        int i8 = intValue2 & 255;
        float speedMap = speedMap(f);
        return Integer.valueOf(((i + ((int) (((float) (i5 - i)) * speedMap))) << 24) | ((i2 + ((int) (((float) (i6 - i2)) * speedMap))) << 16) | ((i3 + ((int) (((float) (i7 - i3)) * speedMap))) << 8) | (i4 + ((int) (speedMap * ((float) (i8 - i4))))));
    }
}

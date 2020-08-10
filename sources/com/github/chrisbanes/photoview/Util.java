package com.github.chrisbanes.photoview;

import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import androidx.core.view.MotionEventCompat;

class Util {

    /* renamed from: com.github.chrisbanes.photoview.Util$1 */
    static /* synthetic */ class C19711 {
        static final /* synthetic */ int[] $SwitchMap$android$widget$ImageView$ScaleType = new int[ScaleType.values().length];

        static {
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ScaleType.MATRIX.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    static int getPointerIndex(int i) {
        return (i & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
    }

    Util() {
    }

    static void checkZoomLevels(float f, float f2, float f3) {
        if (f >= f2) {
            throw new IllegalArgumentException("Minimum zoom has to be less than Medium zoom. Call setMinimumZoom() with a more appropriate value");
        } else if (f2 >= f3) {
            throw new IllegalArgumentException("Medium zoom has to be less than Maximum zoom. Call setMaximumZoom() with a more appropriate value");
        }
    }

    static boolean hasDrawable(ImageView imageView) {
        return imageView.getDrawable() != null;
    }

    static boolean isSupportedScaleType(ScaleType scaleType) {
        if (scaleType == null) {
            return false;
        }
        if (C19711.$SwitchMap$android$widget$ImageView$ScaleType[scaleType.ordinal()] != 1) {
            return true;
        }
        throw new IllegalStateException("Matrix scale type is not supported");
    }
}

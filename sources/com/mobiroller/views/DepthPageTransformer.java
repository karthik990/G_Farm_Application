package com.mobiroller.views;

import android.view.View;
import androidx.viewpager.widget.ViewPager.PageTransformer;

public class DepthPageTransformer implements PageTransformer {
    private static final float MIN_SCALE = 0.75f;

    public void transformPage(View view, float f) {
        int width = view.getWidth();
        if (f < -1.0f) {
            view.setAlpha(0.0f);
        } else if (f <= 0.0f) {
            view.setAlpha(1.0f);
            view.setTranslationX(0.0f);
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
        } else if (f <= 1.0f) {
            view.setAlpha(1.0f - f);
            view.setTranslationX(((float) width) * (-f));
            float abs = ((1.0f - Math.abs(f)) * 0.25f) + 0.75f;
            view.setScaleX(abs);
            view.setScaleY(abs);
        } else {
            view.setAlpha(0.0f);
        }
    }
}

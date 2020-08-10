package com.google.android.material.transition;

import android.graphics.RectF;

class FitModeEvaluators {
    private static final FitModeEvaluator HEIGHT = new FitModeEvaluator() {
        public FitModeResult evaluate(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
            float lerp = TransitionUtils.lerp(f5, f7, f2, f3, f);
            float f8 = lerp / f5;
            float f9 = lerp / f7;
            FitModeResult fitModeResult = new FitModeResult(f8, f9, f4 * f8, lerp, f6 * f9, lerp);
            return fitModeResult;
        }

        public boolean shouldMaskStartBounds(FitModeResult fitModeResult) {
            return fitModeResult.currentStartWidth > fitModeResult.currentEndWidth;
        }

        public void applyMask(RectF rectF, float f, FitModeResult fitModeResult) {
            float abs = (Math.abs(fitModeResult.currentEndWidth - fitModeResult.currentStartWidth) / 2.0f) * f;
            rectF.left += abs;
            rectF.right -= abs;
        }
    };
    private static final FitModeEvaluator WIDTH = new FitModeEvaluator() {
        public FitModeResult evaluate(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
            float lerp = TransitionUtils.lerp(f4, f6, f2, f3, f);
            float f8 = lerp / f4;
            float f9 = lerp / f6;
            FitModeResult fitModeResult = new FitModeResult(f8, f9, lerp, f5 * f8, lerp, f7 * f9);
            return fitModeResult;
        }

        public boolean shouldMaskStartBounds(FitModeResult fitModeResult) {
            return fitModeResult.currentStartHeight > fitModeResult.currentEndHeight;
        }

        public void applyMask(RectF rectF, float f, FitModeResult fitModeResult) {
            rectF.bottom -= Math.abs(fitModeResult.currentEndHeight - fitModeResult.currentStartHeight) * f;
        }
    };

    static FitModeEvaluator get(int i, boolean z, RectF rectF, RectF rectF2) {
        if (i == 0) {
            return shouldAutoFitToWidth(z, rectF, rectF2) ? WIDTH : HEIGHT;
        } else if (i == 1) {
            return WIDTH;
        } else {
            if (i == 2) {
                return HEIGHT;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid fit mode: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    private static boolean shouldAutoFitToWidth(boolean z, RectF rectF, RectF rectF2) {
        float width = rectF.width();
        float height = rectF.height();
        float width2 = rectF2.width();
        float height2 = rectF2.height();
        float f = (height2 * width) / width2;
        float f2 = (width2 * height) / width;
        if (z) {
            if (f >= height) {
                return true;
            }
        } else if (f2 >= height2) {
            return true;
        }
        return false;
    }

    private FitModeEvaluators() {
    }
}

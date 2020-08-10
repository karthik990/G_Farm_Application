package com.google.android.material.transition;

class FadeModeEvaluators {
    private static final FadeModeEvaluator CROSS = new FadeModeEvaluator() {
        public FadeModeResult evaluate(float f, float f2, float f3) {
            return FadeModeResult.startOnTop(TransitionUtils.lerp(255, 0, f2, f3, f), TransitionUtils.lerp(0, 255, f2, f3, f));
        }
    };

    /* renamed from: IN */
    private static final FadeModeEvaluator f1611IN = new FadeModeEvaluator() {
        public FadeModeResult evaluate(float f, float f2, float f3) {
            return FadeModeResult.endOnTop(255, TransitionUtils.lerp(0, 255, f2, f3, f));
        }
    };
    private static final FadeModeEvaluator OUT = new FadeModeEvaluator() {
        public FadeModeResult evaluate(float f, float f2, float f3) {
            return FadeModeResult.startOnTop(TransitionUtils.lerp(255, 0, f2, f3, f), 255);
        }
    };
    private static final FadeModeEvaluator THROUGH = new FadeModeEvaluator() {
        public FadeModeResult evaluate(float f, float f2, float f3) {
            float f4 = ((f3 - f2) * 0.35f) + f2;
            return FadeModeResult.startOnTop(TransitionUtils.lerp(255, 0, f2, f4, f), TransitionUtils.lerp(0, 255, f4, f3, f));
        }
    };

    static FadeModeEvaluator get(int i, boolean z) {
        if (i == 0) {
            return z ? f1611IN : OUT;
        } else if (i == 1) {
            return z ? OUT : f1611IN;
        } else if (i == 2) {
            return CROSS;
        } else {
            if (i == 3) {
                return THROUGH;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid fade mode: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    private FadeModeEvaluators() {
    }
}

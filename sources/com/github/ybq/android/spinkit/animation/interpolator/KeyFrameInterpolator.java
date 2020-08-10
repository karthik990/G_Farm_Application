package com.github.ybq.android.spinkit.animation.interpolator;

import android.animation.TimeInterpolator;
import android.view.animation.Interpolator;

public class KeyFrameInterpolator implements Interpolator {
    private float[] fractions;
    private TimeInterpolator interpolator;

    public static KeyFrameInterpolator easeInOut(float... fArr) {
        KeyFrameInterpolator keyFrameInterpolator = new KeyFrameInterpolator(Ease.inOut(), new float[0]);
        keyFrameInterpolator.setFractions(fArr);
        return keyFrameInterpolator;
    }

    public static KeyFrameInterpolator pathInterpolator(float f, float f2, float f3, float f4, float... fArr) {
        KeyFrameInterpolator keyFrameInterpolator = new KeyFrameInterpolator(PathInterpolatorCompat.create(f, f2, f3, f4), new float[0]);
        keyFrameInterpolator.setFractions(fArr);
        return keyFrameInterpolator;
    }

    public KeyFrameInterpolator(TimeInterpolator timeInterpolator, float... fArr) {
        this.interpolator = timeInterpolator;
        this.fractions = fArr;
    }

    public void setFractions(float... fArr) {
        this.fractions = fArr;
    }

    public synchronized float getInterpolation(float f) {
        if (this.fractions.length > 1) {
            int i = 0;
            while (i < this.fractions.length - 1) {
                float f2 = this.fractions[i];
                i++;
                float f3 = this.fractions[i];
                float f4 = f3 - f2;
                if (f >= f2 && f <= f3) {
                    return f2 + (this.interpolator.getInterpolation((f - f2) / f4) * f4);
                }
            }
        }
        return this.interpolator.getInterpolation(f);
    }
}

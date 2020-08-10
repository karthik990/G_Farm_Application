package com.github.ybq.android.spinkit.animation.interpolator;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.animation.Interpolator;

class PathInterpolatorDonut implements Interpolator {
    private static final float PRECISION = 0.002f;

    /* renamed from: mX */
    private final float[] f1472mX;

    /* renamed from: mY */
    private final float[] f1473mY;

    public PathInterpolatorDonut(Path path) {
        PathMeasure pathMeasure = new PathMeasure(path, false);
        float length = pathMeasure.getLength();
        int i = ((int) (length / PRECISION)) + 1;
        this.f1472mX = new float[i];
        this.f1473mY = new float[i];
        float[] fArr = new float[2];
        for (int i2 = 0; i2 < i; i2++) {
            pathMeasure.getPosTan((((float) i2) * length) / ((float) (i - 1)), fArr, null);
            this.f1472mX[i2] = fArr[0];
            this.f1473mY[i2] = fArr[1];
        }
    }

    public PathInterpolatorDonut(float f, float f2) {
        this(createQuad(f, f2));
    }

    public PathInterpolatorDonut(float f, float f2, float f3, float f4) {
        this(createCubic(f, f2, f3, f4));
    }

    public float getInterpolation(float f) {
        if (f <= 0.0f) {
            return 0.0f;
        }
        if (f >= 1.0f) {
            return 1.0f;
        }
        int i = 0;
        int length = this.f1472mX.length - 1;
        while (length - i > 1) {
            int i2 = (i + length) / 2;
            if (f < this.f1472mX[i2]) {
                length = i2;
            } else {
                i = i2;
            }
        }
        float[] fArr = this.f1472mX;
        float f2 = fArr[length] - fArr[i];
        if (f2 == 0.0f) {
            return this.f1473mY[i];
        }
        float f3 = (f - fArr[i]) / f2;
        float[] fArr2 = this.f1473mY;
        float f4 = fArr2[i];
        return f4 + (f3 * (fArr2[length] - f4));
    }

    private static Path createQuad(float f, float f2) {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.quadTo(f, f2, 1.0f, 1.0f);
        return path;
    }

    private static Path createCubic(float f, float f2, float f3, float f4) {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.cubicTo(f, f2, f3, f4, 1.0f, 1.0f);
        return path;
    }
}

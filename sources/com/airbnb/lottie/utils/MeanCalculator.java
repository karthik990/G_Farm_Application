package com.airbnb.lottie.utils;

public class MeanCalculator {

    /* renamed from: n */
    private int f81n;
    private float sum;

    public void add(float f) {
        this.sum += f;
        this.f81n++;
        int i = this.f81n;
        if (i == Integer.MAX_VALUE) {
            this.sum /= 2.0f;
            this.f81n = i / 2;
        }
    }

    public float getMean() {
        int i = this.f81n;
        if (i == 0) {
            return 0.0f;
        }
        return this.sum / ((float) i);
    }
}

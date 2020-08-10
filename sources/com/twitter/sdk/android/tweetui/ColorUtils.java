package com.twitter.sdk.android.tweetui;

import android.graphics.Color;

final class ColorUtils {
    private ColorUtils() {
    }

    static int calculateOpacityTransform(double d, int i, int i2) {
        int red = Color.red(i2);
        int red2 = Color.red(i);
        int green = Color.green(i2);
        int green2 = Color.green(i);
        int blue = Color.blue(i2);
        int blue2 = Color.blue(i);
        double d2 = 1.0d - d;
        double d3 = (double) red;
        Double.isNaN(d3);
        double d4 = d3 * d2;
        double d5 = (double) red2;
        Double.isNaN(d5);
        int i3 = (int) (d4 + (d5 * d));
        double d6 = (double) green;
        Double.isNaN(d6);
        double d7 = d6 * d2;
        double d8 = (double) green2;
        Double.isNaN(d8);
        int i4 = (int) (d7 + (d8 * d));
        double d9 = (double) blue;
        Double.isNaN(d9);
        double d10 = d2 * d9;
        double d11 = (double) blue2;
        Double.isNaN(d11);
        return Color.rgb(i3, i4, (int) (d10 + (d * d11)));
    }

    static boolean isLightColor(int i) {
        int red = Color.red(i);
        int green = Color.green(i);
        int blue = Color.blue(i);
        double d = (double) red;
        Double.isNaN(d);
        double d2 = d * 0.21d;
        double d3 = (double) green;
        Double.isNaN(d3);
        double d4 = d2 + (d3 * 0.72d);
        double d5 = (double) blue;
        Double.isNaN(d5);
        return d4 + (d5 * 0.07d) > 128.0d;
    }
}

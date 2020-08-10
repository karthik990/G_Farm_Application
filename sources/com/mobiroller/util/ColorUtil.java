package com.mobiroller.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import androidx.core.internal.view.SupportMenu;

public class ColorUtil {
    public static int getDarkerColor(int i, float f) {
        return Color.argb(Color.alpha(i), Math.max((int) (((float) Color.red(i)) * f), 0), Math.max((int) (((float) Color.green(i)) * f), 0), Math.max((int) (((float) Color.blue(i)) * f), 0));
    }

    public static int getLighterColor(int i, float f) {
        float f2 = 1.0f - f;
        return Color.argb(Color.alpha(i), (int) ((((((float) Color.red(i)) * f2) / 255.0f) + f) * 255.0f), (int) ((((((float) Color.green(i)) * f2) / 255.0f) + f) * 255.0f), (int) ((((((float) Color.blue(i)) * f2) / 255.0f) + f) * 255.0f));
    }

    public static int getColorWithAlpha(int i, float f) {
        return Color.argb(Math.round(((float) Color.alpha(i)) * f), Color.red(i), Color.green(i), Color.blue(i));
    }

    public static Bitmap addGradient(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
        Paint paint = new Paint();
        float f = (float) height;
        LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, 0.0f, f, SupportMenu.CATEGORY_MASK, -16711936, TileMode.CLAMP);
        paint.setShader(linearGradient);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawRect(0.0f, 0.0f, (float) width, f, paint);
        return createBitmap;
    }

    public static Drawable addGradient(Drawable drawable, int i, int i2) {
        GradientDrawable gradientDrawable = (GradientDrawable) drawable;
        gradientDrawable.setColors(new int[]{i, i2});
        gradientDrawable.setGradientType(0);
        return gradientDrawable;
    }

    public static boolean isColorDark(int i) {
        double red = (double) Color.red(i);
        Double.isNaN(red);
        double d = red * 0.299d;
        double green = (double) Color.green(i);
        Double.isNaN(green);
        double d2 = d + (green * 0.587d);
        double blue = (double) Color.blue(i);
        Double.isNaN(blue);
        return 1.0d - ((d2 + (blue * 0.114d)) / 255.0d) >= 0.5d;
    }
}

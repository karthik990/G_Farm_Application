package com.mobiroller.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.util.DisplayMetrics;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.constants.Constants;
import com.mobiroller.models.ColorModel;
import p043io.fabric.sdk.android.services.common.AbstractSpiCall;

public class ScreenHelper extends MobiRollerApplication {
    private Context context;
    private SharedPrefHelper sharedPrefHelper;

    public ScreenHelper(Context context2, SharedPrefHelper sharedPrefHelper2) {
        this.sharedPrefHelper = sharedPrefHelper2;
        this.context = context2;
    }

    public ScreenHelper(Context context2) {
        this.sharedPrefHelper = UtilManager.sharedPrefHelper();
        this.context = context2;
    }

    public int getColorWithAlpha(int i, float f) {
        return Color.argb(Math.round(((float) Color.alpha(i)) * f), Color.red(i), Color.green(i), Color.blue(i));
    }

    public static int setColorUnselected(ColorModel colorModel) {
        float f;
        float f2;
        float f3;
        new Color();
        float f4 = 0.0f;
        try {
            f3 = Float.parseFloat(String.valueOf(colorModel.getAlpha())) * 255.0f;
            try {
                f2 = Float.parseFloat(String.valueOf(colorModel.getRed())) * 255.0f;
            } catch (Exception e) {
                e = e;
                f2 = 0.0f;
                f = 0.0f;
                e.printStackTrace();
                return Color.argb(Math.round(f3), Math.round(f2), Math.round(f), Math.round(f4));
            }
            try {
                f = Float.parseFloat(String.valueOf(colorModel.getGreen())) * 255.0f;
            } catch (Exception e2) {
                e = e2;
                f = 0.0f;
                e.printStackTrace();
                return Color.argb(Math.round(f3), Math.round(f2), Math.round(f), Math.round(f4));
            }
            try {
                f4 = Float.parseFloat(String.valueOf(colorModel.getBlue())) * 255.0f;
            } catch (Exception e3) {
                e = e3;
                e.printStackTrace();
                return Color.argb(Math.round(f3), Math.round(f2), Math.round(f), Math.round(f4));
            }
        } catch (Exception e4) {
            e = e4;
            f3 = 0.0f;
            f2 = 0.0f;
            f = 0.0f;
            e.printStackTrace();
            return Color.argb(Math.round(f3), Math.round(f2), Math.round(f), Math.round(f4));
        }
        return Color.argb(Math.round(f3), Math.round(f2), Math.round(f), Math.round(f4));
    }

    public static int setColorWithNoAlpha(ColorModel colorModel) {
        float f;
        float f2;
        new Color();
        float f3 = 0.0f;
        try {
            f2 = Float.parseFloat(String.valueOf(colorModel.getRed())) * 255.0f;
            try {
                f = Float.parseFloat(String.valueOf(colorModel.getGreen())) * 255.0f;
            } catch (Exception e) {
                e = e;
                f = 0.0f;
                e.printStackTrace();
                return Color.argb(Math.round(255.0f), Math.round(f2), Math.round(f), Math.round(f3));
            }
            try {
                f3 = Float.parseFloat(String.valueOf(colorModel.getBlue())) * 255.0f;
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                return Color.argb(Math.round(255.0f), Math.round(f2), Math.round(f), Math.round(f3));
            }
        } catch (Exception e3) {
            e = e3;
            f2 = 0.0f;
            f = 0.0f;
            e.printStackTrace();
            return Color.argb(Math.round(255.0f), Math.round(f2), Math.round(f), Math.round(f3));
        }
        return Color.argb(Math.round(255.0f), Math.round(f2), Math.round(f), Math.round(f3));
    }

    public int setColorSelected(ColorModel colorModel) {
        float f;
        float f2;
        float f3;
        new Color();
        float f4 = 0.0f;
        try {
            f3 = (Float.parseFloat(String.valueOf(colorModel.getAlpha())) * 255.0f) / 2.0f;
            try {
                f2 = Float.parseFloat(String.valueOf(colorModel.getRed())) * 255.0f;
                try {
                    f = Float.parseFloat(String.valueOf(colorModel.getGreen())) * 255.0f;
                } catch (NumberFormatException e) {
                    e = e;
                    f = 0.0f;
                    e.printStackTrace();
                    return Color.argb(Math.round(f3), Math.round(f2), Math.round(f), Math.round(f4));
                }
                try {
                    f4 = Float.parseFloat(String.valueOf(colorModel.getBlue())) * 255.0f;
                } catch (NumberFormatException e2) {
                    e = e2;
                    e.printStackTrace();
                    return Color.argb(Math.round(f3), Math.round(f2), Math.round(f), Math.round(f4));
                }
            } catch (NumberFormatException e3) {
                e = e3;
                f2 = 0.0f;
                f = 0.0f;
                e.printStackTrace();
                return Color.argb(Math.round(f3), Math.round(f2), Math.round(f), Math.round(f4));
            }
        } catch (NumberFormatException e4) {
            e = e4;
            f3 = 0.0f;
            f2 = 0.0f;
            f = 0.0f;
            e.printStackTrace();
            return Color.argb(Math.round(f3), Math.round(f2), Math.round(f), Math.round(f4));
        }
        return Color.argb(Math.round(f3), Math.round(f2), Math.round(f), Math.round(f4));
    }

    public int setColorHover() {
        new Color();
        return Color.argb(Math.round(51.0f), Math.round(255.0f), Math.round(255.0f), Math.round(255.0f));
    }

    public static int getDarkerColor(int i, float f) {
        return Color.argb(Color.alpha(i), Math.max((int) (((float) Color.red(i)) * f), 0), Math.max((int) (((float) Color.green(i)) * f), 0), Math.max((int) (((float) Color.blue(i)) * f), 0));
    }

    public static int getLighterColor(int i, float f) {
        float f2 = 1.0f - f;
        return Color.argb(Color.alpha(i), (int) ((((((float) Color.red(i)) * f2) / 255.0f) + f) * 255.0f), (int) ((((((float) Color.green(i)) * f2) / 255.0f) + f) * 255.0f), (int) ((((((float) Color.blue(i)) * f2) / 255.0f) + f) * 255.0f));
    }

    public static GradientDrawable getGradientBackground(int i, float f, float f2) {
        GradientDrawable gradientDrawable = new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{getLighterColor(i, 0.0f), getDarkerColor(i, 0.5f)});
        gradientDrawable.setCornerRadius(0.0f);
        return gradientDrawable;
    }

    public Typeface getFontFromAsset(String str) {
        String str2 = ".ttf";
        String str3 = "fonts/";
        try {
            AssetManager assets = this.context.getAssets();
            StringBuilder sb = new StringBuilder();
            sb.append(str3);
            sb.append(str);
            sb.append(str2);
            return Typeface.createFromAsset(assets, sb.toString());
        } catch (Exception unused) {
            AssetManager assets2 = this.context.getAssets();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str3);
            sb2.append("HelveticaNeue");
            sb2.append(str2);
            return Typeface.createFromAsset(assets2, sb2.toString());
        }
    }

    public static int getHeightForDevice(int i, Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int statusBarHeight = displayMetrics.heightPixels - UtilManager.sharedPrefHelper().getStatusBarHeight();
        if (UtilManager.sharedPrefHelper().getTabActive()) {
            return Math.round((float) ((i * (statusBarHeight - UtilManager.sharedPrefHelper().getTabHeight())) / Constants.MobiRoller_Preferences_StandardHeight));
        }
        return Math.round((float) ((i * statusBarHeight) / Constants.MobiRoller_Preferences_StandardHeight));
    }

    public static int getDeviceHeight(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels - UtilManager.sharedPrefHelper().getStatusBarHeight();
    }

    public static int pxToDp(int i) {
        return (int) (((float) i) / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(int i) {
        return (int) (((float) i) * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getStatusBarHeight(Context context2) {
        int identifier = context2.getResources().getIdentifier("status_bar_height", "dimen", AbstractSpiCall.ANDROID_CLIENT_TYPE);
        if (identifier > 0) {
            return context2.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }
}

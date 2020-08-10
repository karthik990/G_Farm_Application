package com.wdullaer.materialdatetimepicker;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.util.TypedValue;
import android.view.View;
import androidx.core.content.ContextCompat;
import java.util.Calendar;

/* renamed from: com.wdullaer.materialdatetimepicker.Utils */
public class C5272Utils {
    public static final int FULL_ALPHA = 255;
    public static final int PULSE_ANIMATOR_DURATION = 544;
    public static final int SELECTED_ALPHA = 255;
    public static final int SELECTED_ALPHA_THEME_DARK = 255;

    public static boolean isJellybeanOrLater() {
        return VERSION.SDK_INT >= 16;
    }

    public static void tryAccessibilityAnnounce(View view, CharSequence charSequence) {
        if (isJellybeanOrLater() && view != null && charSequence != null) {
            view.announceForAccessibility(charSequence);
        }
    }

    public static ObjectAnimator getPulseAnimator(View view, float f, float f2) {
        Keyframe ofFloat = Keyframe.ofFloat(0.0f, 1.0f);
        Keyframe ofFloat2 = Keyframe.ofFloat(0.275f, f);
        Keyframe ofFloat3 = Keyframe.ofFloat(0.69f, f2);
        Keyframe ofFloat4 = Keyframe.ofFloat(1.0f, 1.0f);
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view, new PropertyValuesHolder[]{PropertyValuesHolder.ofKeyframe("scaleX", new Keyframe[]{ofFloat, ofFloat2, ofFloat3, ofFloat4}), PropertyValuesHolder.ofKeyframe("scaleY", new Keyframe[]{ofFloat, ofFloat2, ofFloat3, ofFloat4})});
        ofPropertyValuesHolder.setDuration(544);
        return ofPropertyValuesHolder;
    }

    public static int dpToPx(float f, Resources resources) {
        return (int) TypedValue.applyDimension(1, f, resources.getDisplayMetrics());
    }

    public static int darkenColor(int i) {
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        fArr[2] = fArr[2] * 0.8f;
        return Color.HSVToColor(fArr);
    }

    public static int getAccentColorFromThemeIfAvailable(Context context) {
        TypedValue typedValue = new TypedValue();
        if (VERSION.SDK_INT >= 21) {
            context.getTheme().resolveAttribute(16843829, typedValue, true);
            return typedValue.data;
        }
        int identifier = context.getResources().getIdentifier("colorAccent", "attr", context.getPackageName());
        if (identifier == 0 || !context.getTheme().resolveAttribute(identifier, typedValue, true)) {
            return ContextCompat.getColor(context, C5266R.C5267color.mdtp_accent_color);
        }
        return typedValue.data;
    }

    public static boolean isDarkTheme(Context context, boolean z) {
        return resolveBoolean(context, C5266R.attr.mdtp_theme_dark, z);
    }

    private static boolean resolveBoolean(Context context, int i, boolean z) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{i});
        try {
            return obtainStyledAttributes.getBoolean(0, z);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public static Calendar trimToMidnight(Calendar calendar) {
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar;
    }
}

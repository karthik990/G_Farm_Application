package com.mobiroller.util;

import android.app.Activity;
import android.content.res.Resources;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.mobiroller.constants.Constants;

public class ScreenUtil {
    private static int screenHeigth;
    private static int screenWidth;

    public static void closeKeyboard(Activity activity) {
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus != null) {
            ((InputMethodManager) activity.getSystemService("input_method")).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    public static void hideKeyboard(View view, Activity activity) {
        ((InputMethodManager) activity.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyboard(Activity activity) {
        ((InputMethodManager) activity.getSystemService("input_method")).toggleSoftInput(2, 0);
    }

    public static int getScreenWidth() {
        if (screenWidth == 0) {
            screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        }
        return screenWidth;
    }

    public static int getScreenHeight() {
        if (screenHeigth == 0) {
            screenHeigth = Resources.getSystem().getDisplayMetrics().heightPixels;
        }
        return screenHeigth;
    }

    public static int getWidthForDevice(int i) {
        return Math.round((float) ((i * getScreenWidth()) / Constants.MobiRoller_Preferences_StandardWidth));
    }

    public static int getParamForColumns(int i) {
        return Math.round((float) (((((Constants.MobiRoller_Preferences_StandardWidth - (i * 10)) - 20) / i) * getScreenWidth()) / Constants.MobiRoller_Preferences_StandardWidth));
    }
}

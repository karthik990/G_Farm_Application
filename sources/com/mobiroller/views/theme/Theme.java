package com.mobiroller.views.theme;

import android.graphics.Color;
import androidx.core.view.ViewCompat;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.util.ColorUtil;

public class Theme {
    public static int antiColor;
    public static int buttonPrimaryColor = ViewCompat.MEASURED_STATE_MASK;
    public static int darkColor = Color.parseColor("#0039D1");
    public static int headOneTypeface = R.font.poppins_bold;
    public static int headTwoTypeface = R.font.poppins_bold;
    public static int headerColor = Color.parseColor("#0045FF");
    public static int headingOneTypeface = R.font.poppins_bold;
    public static int headingTwoTypeface = R.font.poppins_semibold;
    public static int imagePrimaryColor = ViewCompat.MEASURED_STATE_MASK;
    public static int paragraphColor;
    public static int paragraphTypeface = R.font.poppins_regular;
    public static int primaryColor;
    public static int secondaryColor = Color.parseColor("#245FFF");
    public static int spanTypeface = R.font.poppins_regular;
    public static int textColor = Color.parseColor("#969fa2");
    public static int textPrimaryColor = ViewCompat.MEASURED_STATE_MASK;
    public static int textSecondaryColor;

    static {
        String str = "#455154";
        paragraphColor = Color.parseColor(str);
        int i = ViewCompat.MEASURED_STATE_MASK;
        textSecondaryColor = Color.parseColor(str);
        if (ColorUtil.isColorDark(primaryColor)) {
            i = -1;
        }
        antiColor = i;
    }
}

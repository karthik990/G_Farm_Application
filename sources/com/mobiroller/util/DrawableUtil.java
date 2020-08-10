package com.mobiroller.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import com.mobiroller.mobi942763453128.R;

public class DrawableUtil {
    private static Drawable checkedCheckBoxDrawable;
    private static Drawable uncheckedCheckBoxDrawable;

    public static Drawable getUncheckedCheckboxDrawable(Context context) {
        if (uncheckedCheckBoxDrawable == null) {
            uncheckedCheckBoxDrawable = ContextCompat.getDrawable(context, R.drawable.unchecked_radio_button);
        }
        return uncheckedCheckBoxDrawable;
    }

    public static Drawable getCheckedCheckboxDrawable(Context context) {
        if (checkedCheckBoxDrawable == null) {
            checkedCheckBoxDrawable = ContextCompat.getDrawable(context, R.drawable.checked_radio_button);
        }
        return checkedCheckBoxDrawable;
    }
}

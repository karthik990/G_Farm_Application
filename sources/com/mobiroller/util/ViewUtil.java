package com.mobiroller.util;

import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import java.lang.reflect.Field;

public class ViewUtil {
    public static TextView getTitleTextView(Toolbar toolbar) {
        try {
            Field declaredField = Toolbar.class.getDeclaredField("mTitleTextView");
            declaredField.setAccessible(true);
            return (TextView) declaredField.get(toolbar);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

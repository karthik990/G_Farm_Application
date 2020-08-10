package com.mobiroller.helpers;

import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.widget.EditText;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import java.lang.reflect.Field;

public class EditTextHelper {
    public static void setCursorColor(EditText editText, int i) {
        try {
            Field declaredField = TextView.class.getDeclaredField("mCursorDrawableRes");
            declaredField.setAccessible(true);
            int i2 = declaredField.getInt(editText);
            Field declaredField2 = TextView.class.getDeclaredField("mEditor");
            declaredField2.setAccessible(true);
            Object obj = declaredField2.get(editText);
            Drawable drawable = ContextCompat.getDrawable(editText.getContext(), i2);
            drawable.setColorFilter(i, Mode.SRC_IN);
            Drawable[] drawableArr = {drawable, drawable};
            Field declaredField3 = obj.getClass().getDeclaredField("mCursorDrawable");
            declaredField3.setAccessible(true);
            declaredField3.set(obj, drawableArr);
        } catch (Exception unused) {
        }
    }

    public static void setUnderLineColor(EditText editText, int i) {
        editText.getBackground().setColorFilter(i, Mode.SRC_ATOP);
    }

    public static void setHintTextColor(EditText editText, int i) {
        editText.setHintTextColor(i);
    }

    public static void setAllColors(EditText editText, int i) {
        setHintTextColor(editText, i);
        setUnderLineColor(editText, i);
        setCursorColor(editText, i);
        editText.setTextColor(i);
    }
}

package p015br.com.simplepass.loading_button_lib;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;

/* renamed from: br.com.simplepass.loading_button_lib.UtilsJava */
public class UtilsJava {
    public static Drawable getDrawable(Context context, int i) {
        if (VERSION.SDK_INT >= 21) {
            return context.getDrawable(i);
        }
        return context.getResources().getDrawable(i);
    }
}

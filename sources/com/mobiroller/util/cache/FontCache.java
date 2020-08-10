package com.mobiroller.util.cache;

import android.graphics.Typeface;
import androidx.core.content.res.ResourcesCompat;
import com.mobiroller.MobiRollerApplication;
import java.util.HashMap;
import java.util.Map;

public class FontCache {
    private static Map<Integer, Typeface> sCachedFonts = new HashMap();

    public static Typeface getTypeface(int i) {
        if (!sCachedFonts.containsKey(Integer.valueOf(i))) {
            Typeface font = ResourcesCompat.getFont(MobiRollerApplication.app, i);
            if (font != null) {
                sCachedFonts.put(Integer.valueOf(i), font);
            }
        }
        return (Typeface) sCachedFonts.get(Integer.valueOf(i));
    }
}

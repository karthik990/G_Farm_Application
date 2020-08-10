package com.wdullaer.materialdatetimepicker;

import android.content.Context;
import android.graphics.Typeface;
import androidx.collection.SimpleArrayMap;

public class TypefaceHelper {
    private static final SimpleArrayMap<String, Typeface> cache = new SimpleArrayMap<>();

    public static Typeface get(Context context, String str) {
        synchronized (cache) {
            if (!cache.containsKey(str)) {
                Typeface createFromAsset = Typeface.createFromAsset(context.getAssets(), String.format("fonts/%s.ttf", new Object[]{str}));
                cache.put(str, createFromAsset);
                return createFromAsset;
            }
            Typeface typeface = (Typeface) cache.get(str);
            return typeface;
        }
    }
}

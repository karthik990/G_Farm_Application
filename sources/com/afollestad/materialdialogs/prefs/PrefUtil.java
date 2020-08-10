package com.afollestad.materialdialogs.prefs;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceManager.OnActivityDestroyListener;
import android.util.AttributeSet;
import com.afollestad.materialdialogs.commons.C0841R;
import java.lang.reflect.Method;

class PrefUtil {
    private PrefUtil() {
    }

    static void setLayoutResource(Context context, Preference preference, AttributeSet attributeSet) {
        boolean z;
        boolean z2 = false;
        if (attributeSet != null) {
            int i = 0;
            while (true) {
                if (i < attributeSet.getAttributeCount()) {
                    if (((XmlResourceParser) attributeSet).getAttributeNamespace(0).equals("http://schemas.android.com/apk/res/android") && attributeSet.getAttributeName(i).equals(TtmlNode.TAG_LAYOUT)) {
                        z = true;
                        break;
                    }
                    i++;
                } else {
                    break;
                }
            }
        }
        z = false;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, C0841R.styleable.Preference, 0, 0);
            try {
                z2 = obtainStyledAttributes.getBoolean(C0841R.styleable.Preference_useStockLayout, false);
            } finally {
                obtainStyledAttributes.recycle();
            }
        }
        if (!z && !z2) {
            preference.setLayoutResource(C0841R.layout.md_preference_custom);
        }
    }

    static void registerOnActivityDestroyListener(Preference preference, OnActivityDestroyListener onActivityDestroyListener) {
        try {
            PreferenceManager preferenceManager = preference.getPreferenceManager();
            Method declaredMethod = preferenceManager.getClass().getDeclaredMethod("registerOnActivityDestroyListener", new Class[]{OnActivityDestroyListener.class});
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(preferenceManager, new Object[]{onActivityDestroyListener});
        } catch (Exception unused) {
        }
    }

    static void unregisterOnActivityDestroyListener(Preference preference, OnActivityDestroyListener onActivityDestroyListener) {
        try {
            PreferenceManager preferenceManager = preference.getPreferenceManager();
            Method declaredMethod = preferenceManager.getClass().getDeclaredMethod("unregisterOnActivityDestroyListener", new Class[]{OnActivityDestroyListener.class});
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(preferenceManager, new Object[]{onActivityDestroyListener});
        } catch (Exception unused) {
        }
    }
}

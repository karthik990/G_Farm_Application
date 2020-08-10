package com.mobiroller.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.mobiroller.enums.FontStyle;

public class FontSizeHelper {
    private static final String CONTENT_FONT_STYLE = "CONTENT_FONT_STYLE";
    private static final String FONT_STYLE = "FONT_STYLE";
    private final Context context;

    public FontSizeHelper(Context context2) {
        this.context = context2;
    }

    /* access modifiers changed from: protected */
    public SharedPreferences open() {
        return this.context.getSharedPreferences("prefs", 0);
    }

    /* access modifiers changed from: protected */
    public Editor edit() {
        return open().edit();
    }

    public FontStyle getFontStyle() {
        return FontStyle.valueOf(open().getString(FONT_STYLE, FontStyle.Medium.name()));
    }

    public FontStyle getContentFontStyle() {
        return FontStyle.valueOf(open().getString(CONTENT_FONT_STYLE, FontStyle.Medium.name()));
    }

    public int getFontOrder() {
        String string = open().getString(FONT_STYLE, FontStyle.Medium.name());
        if (string.equalsIgnoreCase("Small")) {
            return 0;
        }
        if (string.equalsIgnoreCase("Medium")) {
            return 1;
        }
        if (string.equalsIgnoreCase("Large")) {
            return 2;
        }
        if (string.equalsIgnoreCase("XLarge")) {
            return 3;
        }
        return 1;
    }

    public int getContentFontOrder() {
        SharedPreferences open = open();
        String str = CONTENT_FONT_STYLE;
        if (!open.contains(str)) {
            return -1;
        }
        String string = open().getString(str, FontStyle.Medium.name());
        if (string.equalsIgnoreCase("Small")) {
            return 0;
        }
        if (string.equalsIgnoreCase("Medium")) {
            return 1;
        }
        if (string.equalsIgnoreCase("Large")) {
            return 2;
        }
        if (string.equalsIgnoreCase("XLarge")) {
            return 3;
        }
        return 1;
    }

    public void setFontStyle(FontStyle fontStyle) {
        edit().putString(FONT_STYLE, fontStyle.name()).commit();
    }

    public void setContentFontStyle(FontStyle fontStyle) {
        edit().putString(CONTENT_FONT_STYLE, fontStyle.name()).commit();
    }
}

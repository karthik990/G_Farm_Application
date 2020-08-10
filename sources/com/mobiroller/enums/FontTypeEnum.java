package com.mobiroller.enums;

import android.graphics.Typeface;
import com.mobiroller.util.cache.FontCache;
import com.mobiroller.views.theme.Theme;

public enum FontTypeEnum {
    HeadOne(Theme.headOneTypeface, 24.0f, 0),
    HeadTwo(Theme.headTwoTypeface, 18.0f, 1),
    HeadingOne(Theme.headingOneTypeface, 14.0f, 2),
    HeadingTwo(Theme.headingTwoTypeface, 14.0f, 3),
    Paragraph(Theme.paragraphTypeface, 14.0f, 4),
    Span(Theme.spanTypeface, 12.0f, 5),
    Badge(Theme.headingOneTypeface, 9.0f, 6),
    BadgeLarge(Theme.headingOneTypeface, 10.0f, 7);
    
    private float fontSize;
    private int resId;
    private int resOrder;

    private FontTypeEnum(int i, float f, int i2) {
        this.resId = i;
        this.fontSize = f;
        this.resOrder = i2;
    }

    public int getResId() {
        return this.resId;
    }

    public int getResOrder() {
        return this.resOrder;
    }

    public float getFontSize() {
        return this.fontSize;
    }

    public static Typeface getResIdByResOrder(int i) {
        FontTypeEnum[] values;
        for (FontTypeEnum fontTypeEnum : values()) {
            if (i == fontTypeEnum.resOrder) {
                return FontCache.getTypeface(fontTypeEnum.getResId());
            }
        }
        return null;
    }

    public static float getFontSizeByResOrder(int i) {
        FontTypeEnum[] values;
        for (FontTypeEnum fontTypeEnum : values()) {
            if (i == fontTypeEnum.resOrder) {
                return fontTypeEnum.getFontSize();
            }
        }
        return 14.0f;
    }
}

package com.mobiroller.enums;

import com.mobiroller.views.theme.Theme;

public enum ColorEnum {
    Primary(Theme.primaryColor, 0),
    Secondary(Theme.secondaryColor, 1),
    Dark(Theme.darkColor, 2),
    Header(Theme.headerColor, 3),
    Text(Theme.textColor, 4),
    Paragraph(Theme.paragraphColor, 5),
    ButtonPrimary(Theme.buttonPrimaryColor, 6),
    TextPrimary(Theme.textPrimaryColor, 7),
    ImageTintPrimary(Theme.imagePrimaryColor, 8),
    TextSecondary(Theme.textSecondaryColor, 9);
    
    private int resId;
    private int resOrder;

    private ColorEnum(int i, int i2) {
        this.resId = i;
        this.resOrder = i2;
    }

    public int getResId() {
        return this.resId;
    }

    public int getResOrder() {
        return this.resOrder;
    }

    public static int getResIdByResOrder(int i) {
        ColorEnum[] values;
        for (ColorEnum colorEnum : values()) {
            if (i == colorEnum.resOrder) {
                return colorEnum.getResId();
            }
        }
        return 2;
    }
}

package com.mobiroller.enums;

import com.mobiroller.preview.C4290R;

public enum FontStyle {
    Small(C4290R.C4297style.FontStyle_Small, r2),
    Medium(C4290R.C4297style.FontStyle_Medium, r3),
    Large(C4290R.C4297style.FontStyle_Large, r4),
    XLarge(C4290R.C4297style.FontStyle_XLarge, r5);
    
    private int resId;
    private String title;

    public int getResId() {
        return this.resId;
    }

    public String getTitle() {
        return this.title;
    }

    private FontStyle(int i, String str) {
        this.resId = i;
        this.title = str;
    }
}

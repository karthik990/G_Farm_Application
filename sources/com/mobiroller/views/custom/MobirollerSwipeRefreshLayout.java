package com.mobiroller.views.custom;

import android.content.Context;
import android.util.AttributeSet;
import androidx.core.view.ViewCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.mobiroller.util.ColorUtil;
import com.mobiroller.views.theme.Theme;

public class MobirollerSwipeRefreshLayout extends SwipeRefreshLayout {
    public MobirollerSwipeRefreshLayout(Context context) {
        super(context);
        init();
    }

    public MobirollerSwipeRefreshLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        setTheme();
    }

    private void setTheme() {
        int[] iArr = new int[1];
        iArr[0] = ColorUtil.isColorDark(Theme.primaryColor) ? Theme.primaryColor : ViewCompat.MEASURED_STATE_MASK;
        setColorSchemeColors(iArr);
    }
}

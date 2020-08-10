package com.mobiroller.views.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import com.mobiroller.preview.C4290R;
import com.mobiroller.util.ColorUtil;
import com.mobiroller.views.theme.Theme;

public class MobirollerToolbar extends Toolbar {
    private boolean isTransparent;

    public void setTitleTypeface() {
    }

    public MobirollerToolbar(Context context) {
        super(context);
        init(context, null, 0);
    }

    public MobirollerToolbar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet, 0);
    }

    public MobirollerToolbar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet, i);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, C4290R.styleable.MobirollerToolbar, 0, 0);
        try {
            this.isTransparent = obtainStyledAttributes.getBoolean(0, false);
        } finally {
            setTheme();
            obtainStyledAttributes.recycle();
        }
    }

    public void setTheme() {
        if (!this.isTransparent) {
            setBackgroundColor(Theme.primaryColor);
        }
        setTitleTextColor(ColorUtil.isColorDark(Theme.primaryColor) ? -1 : ViewCompat.MEASURED_STATE_MASK);
        setTitleTypeface();
        setContentInsetsAbsolute(0, getContentInsetRight());
        setContentInsetsRelative(0, getContentInsetEnd());
        setContentInsetStartWithNavigation(0);
    }

    public void setNavigationIcon(Drawable drawable) {
        super.setNavigationIcon(drawable);
        setNavigationIconTint();
    }

    public void setNavigationIcon(int i) {
        super.setNavigationIcon(i);
        setNavigationIconTint();
    }

    private void setNavigationIconTint() {
        if (getNavigationIcon() != null) {
            DrawableCompat.setTint(DrawableCompat.wrap(getNavigationIcon()), ColorUtil.isColorDark(Theme.primaryColor) ? -1 : ViewCompat.MEASURED_STATE_MASK);
        }
    }

    public void inflateMenu(int i) {
        int i2;
        super.inflateMenu(i);
        Menu menu = getMenu();
        int i3 = 0;
        while (true) {
            i2 = -1;
            if (i3 >= menu.size()) {
                break;
            }
            MenuItem item = menu.getItem(i3);
            if (item.getIcon() != null) {
                Drawable icon = item.getIcon();
                if (!ColorUtil.isColorDark(Theme.primaryColor)) {
                    i2 = ViewCompat.MEASURED_STATE_MASK;
                }
                DrawableCompat.setTint(icon, i2);
            }
            i3++;
        }
        Drawable overflowIcon = getOverflowIcon();
        if (overflowIcon != null && VERSION.SDK_INT >= 21) {
            if (!ColorUtil.isColorDark(Theme.primaryColor)) {
                i2 = ViewCompat.MEASURED_STATE_MASK;
            }
            overflowIcon.setTint(i2);
            setOverflowIcon(overflowIcon);
        }
    }
}

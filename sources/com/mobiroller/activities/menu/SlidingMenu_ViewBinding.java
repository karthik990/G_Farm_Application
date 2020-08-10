package com.mobiroller.activities.menu;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.google.android.material.navigation.NavigationView;
import com.infideap.drawerbehavior.Advance3DDrawerLayout;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.custom.MobirollerToolbar;

public class SlidingMenu_ViewBinding implements Unbinder {
    private SlidingMenu target;

    public SlidingMenu_ViewBinding(SlidingMenu slidingMenu) {
        this(slidingMenu, slidingMenu.getWindow().getDecorView());
    }

    public SlidingMenu_ViewBinding(SlidingMenu slidingMenu, View view) {
        this.target = slidingMenu;
        slidingMenu.toolbar = (MobirollerToolbar) C0812Utils.findRequiredViewAsType(view, R.id.toolbar, "field 'toolbar'", MobirollerToolbar.class);
        slidingMenu.mDrawerList = (NavigationView) C0812Utils.findRequiredViewAsType(view, R.id.list_slidermenu, "field 'mDrawerList'", NavigationView.class);
        slidingMenu.mDrawerLayout = (Advance3DDrawerLayout) C0812Utils.findRequiredViewAsType(view, R.id.drawer_layout, "field 'mDrawerLayout'", Advance3DDrawerLayout.class);
    }

    public void unbind() {
        SlidingMenu slidingMenu = this.target;
        if (slidingMenu != null) {
            this.target = null;
            slidingMenu.toolbar = null;
            slidingMenu.mDrawerList = null;
            slidingMenu.mDrawerLayout = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}

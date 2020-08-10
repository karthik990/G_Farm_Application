package com.mobiroller.activities.menu;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.PagedDragDropGrid;
import com.mobiroller.views.custom.MobirollerToolbar;
import com.mobiroller.widget.PageIndicator;

public class aveNavigationActivity_ViewBinding implements Unbinder {
    private aveNavigationActivity target;

    public aveNavigationActivity_ViewBinding(aveNavigationActivity avenavigationactivity) {
        this(avenavigationactivity, avenavigationactivity.getWindow().getDecorView());
    }

    public aveNavigationActivity_ViewBinding(aveNavigationActivity avenavigationactivity, View view) {
        this.target = avenavigationactivity;
        avenavigationactivity.imgView = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.gridImage, "field 'imgView'", ImageView.class);
        avenavigationactivity.mPageIndicatorOther = (PageIndicator) C0812Utils.findRequiredViewAsType(view, R.id.page_indicator_other, "field 'mPageIndicatorOther'", PageIndicator.class);
        avenavigationactivity.gridView = (PagedDragDropGrid) C0812Utils.findRequiredViewAsType(view, R.id.gridView, "field 'gridView'", PagedDragDropGrid.class);
        avenavigationactivity.navLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.nav_layout, "field 'navLayout'", RelativeLayout.class);
        avenavigationactivity.toolbar = (MobirollerToolbar) C0812Utils.findRequiredViewAsType(view, R.id.toolbar_top, "field 'toolbar'", MobirollerToolbar.class);
    }

    public void unbind() {
        aveNavigationActivity avenavigationactivity = this.target;
        if (avenavigationactivity != null) {
            this.target = null;
            avenavigationactivity.imgView = null;
            avenavigationactivity.mPageIndicatorOther = null;
            avenavigationactivity.gridView = null;
            avenavigationactivity.navLayout = null;
            avenavigationactivity.toolbar = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}

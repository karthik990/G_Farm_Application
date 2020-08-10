package com.mobiroller.activities.menu;

import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.viewpager.widget.ViewPager;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.custom.MobirollerToolbar;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class SlidingPanelActivity_ViewBinding implements Unbinder {
    private SlidingPanelActivity target;
    private View view7f0a0177;

    public SlidingPanelActivity_ViewBinding(SlidingPanelActivity slidingPanelActivity) {
        this(slidingPanelActivity, slidingPanelActivity.getWindow().getDecorView());
    }

    public SlidingPanelActivity_ViewBinding(final SlidingPanelActivity slidingPanelActivity, View view) {
        this.target = slidingPanelActivity;
        slidingPanelActivity.toolbar = (MobirollerToolbar) C0812Utils.findRequiredViewAsType(view, R.id.toolbar, "field 'toolbar'", MobirollerToolbar.class);
        slidingPanelActivity.slidingUpPanelLayout = (SlidingUpPanelLayout) C0812Utils.findRequiredViewAsType(view, R.id.list_slidermenu, "field 'slidingUpPanelLayout'", SlidingUpPanelLayout.class);
        slidingPanelActivity.gridViewLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.gridview_layout, "field 'gridViewLayout'", RelativeLayout.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.close_button, "field 'closeButton' and method 'closeSlidingPanel'");
        slidingPanelActivity.closeButton = (ImageView) C0812Utils.castView(findRequiredView, R.id.close_button, "field 'closeButton'", ImageView.class);
        this.view7f0a0177 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                slidingPanelActivity.closeSlidingPanel();
            }
        });
        slidingPanelActivity.slidingPanelViewPager = (ViewPager) C0812Utils.findRequiredViewAsType(view, R.id.sliding_panel_view_pager, "field 'slidingPanelViewPager'", ViewPager.class);
        slidingPanelActivity.mHeaderView = C0812Utils.findRequiredView(view, R.id.user_header, "field 'mHeaderView'");
        slidingPanelActivity.pager_indicator = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.viewPagerCountDots, "field 'pager_indicator'", LinearLayout.class);
        slidingPanelActivity.userGrid = (GridView) C0812Utils.findRequiredViewAsType(view, R.id.user_grid, "field 'userGrid'", GridView.class);
        slidingPanelActivity.userGridLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.user_grid_layout, "field 'userGridLayout'", RelativeLayout.class);
    }

    public void unbind() {
        SlidingPanelActivity slidingPanelActivity = this.target;
        if (slidingPanelActivity != null) {
            this.target = null;
            slidingPanelActivity.toolbar = null;
            slidingPanelActivity.slidingUpPanelLayout = null;
            slidingPanelActivity.gridViewLayout = null;
            slidingPanelActivity.closeButton = null;
            slidingPanelActivity.slidingPanelViewPager = null;
            slidingPanelActivity.mHeaderView = null;
            slidingPanelActivity.pager_indicator = null;
            slidingPanelActivity.userGrid = null;
            slidingPanelActivity.userGridLayout = null;
            this.view7f0a0177.setOnClickListener(null);
            this.view7f0a0177 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}

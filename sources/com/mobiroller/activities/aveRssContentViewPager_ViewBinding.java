package com.mobiroller.activities;

import android.view.View;
import android.widget.RelativeLayout;
import androidx.viewpager.widget.ViewPager;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class aveRssContentViewPager_ViewBinding implements Unbinder {
    private aveRssContentViewPager target;

    public aveRssContentViewPager_ViewBinding(aveRssContentViewPager aversscontentviewpager) {
        this(aversscontentviewpager, aversscontentviewpager.getWindow().getDecorView());
    }

    public aveRssContentViewPager_ViewBinding(aveRssContentViewPager aversscontentviewpager, View view) {
        this.target = aversscontentviewpager;
        aversscontentviewpager.viewPager = (ViewPager) C0812Utils.findRequiredViewAsType(view, R.id.viewPager, "field 'viewPager'", ViewPager.class);
        aversscontentviewpager.rss_content_rel_layout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.rss_content_rel_layout, "field 'rss_content_rel_layout'", RelativeLayout.class);
    }

    public void unbind() {
        aveRssContentViewPager aversscontentviewpager = this.target;
        if (aversscontentviewpager != null) {
            this.target = null;
            aversscontentviewpager.viewPager = null;
            aversscontentviewpager.rss_content_rel_layout = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}

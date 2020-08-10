package com.mobiroller.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.github.ybq.android.spinkit.SpinKitView;
import com.mobiroller.mobi942763453128.R;

public class aveRSSViewFragment_ViewBinding implements Unbinder {
    private aveRSSViewFragment target;

    public aveRSSViewFragment_ViewBinding(aveRSSViewFragment averssviewfragment, View view) {
        this.target = averssviewfragment;
        averssviewfragment.rssMainImg = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.rss_main_img, "field 'rssMainImg'", ImageView.class);
        averssviewfragment.recyclerView = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.postListView, "field 'recyclerView'", RecyclerView.class);
        averssviewfragment.mSwipeRefreshLayout = (SwipeRefreshLayout) C0812Utils.findRequiredViewAsType(view, R.id.swipeRefreshLayout, "field 'mSwipeRefreshLayout'", SwipeRefreshLayout.class);
        averssviewfragment.loadMoreProgressView = (SpinKitView) C0812Utils.findRequiredViewAsType(view, R.id.load_more_progress_view, "field 'loadMoreProgressView'", SpinKitView.class);
        averssviewfragment.rssListLayout = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.rss_list_layout, "field 'rssListLayout'", LinearLayout.class);
        averssviewfragment.rssLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.rss_layout, "field 'rssLayout'", RelativeLayout.class);
        averssviewfragment.refreshButton = (Button) C0812Utils.findRequiredViewAsType(view, R.id.refresh_button, "field 'refreshButton'", Button.class);
        averssviewfragment.rssLayoutOverlay = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.overlay_layout, "field 'rssLayoutOverlay'", RelativeLayout.class);
        averssviewfragment.mainLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.main_layout, "field 'mainLayout'", RelativeLayout.class);
    }

    public void unbind() {
        aveRSSViewFragment averssviewfragment = this.target;
        if (averssviewfragment != null) {
            this.target = null;
            averssviewfragment.rssMainImg = null;
            averssviewfragment.recyclerView = null;
            averssviewfragment.mSwipeRefreshLayout = null;
            averssviewfragment.loadMoreProgressView = null;
            averssviewfragment.rssListLayout = null;
            averssviewfragment.rssLayout = null;
            averssviewfragment.refreshButton = null;
            averssviewfragment.rssLayoutOverlay = null;
            averssviewfragment.mainLayout = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}

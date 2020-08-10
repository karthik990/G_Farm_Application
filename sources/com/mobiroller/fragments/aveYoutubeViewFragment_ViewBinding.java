package com.mobiroller.fragments;

import android.view.View;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.github.ybq.android.spinkit.SpinKitView;
import com.mobiroller.mobi942763453128.R;

public class aveYoutubeViewFragment_ViewBinding implements Unbinder {
    private aveYoutubeViewFragment target;

    public aveYoutubeViewFragment_ViewBinding(aveYoutubeViewFragment aveyoutubeviewfragment, View view) {
        this.target = aveyoutubeviewfragment;
        aveyoutubeviewfragment.list = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.video_list, "field 'list'", RecyclerView.class);
        aveyoutubeviewfragment.swipeRefreshLayout = (SwipeRefreshLayout) C0812Utils.findRequiredViewAsType(view, R.id.swipeRefreshLayout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
        aveyoutubeviewfragment.loadMoreProgress = (SpinKitView) C0812Utils.findRequiredViewAsType(view, R.id.load_more_progress_view, "field 'loadMoreProgress'", SpinKitView.class);
        aveyoutubeviewfragment.overlayLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.overlay_layout, "field 'overlayLayout'", RelativeLayout.class);
        aveyoutubeviewfragment.mainLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.main_layout, "field 'mainLayout'", RelativeLayout.class);
    }

    public void unbind() {
        aveYoutubeViewFragment aveyoutubeviewfragment = this.target;
        if (aveyoutubeviewfragment != null) {
            this.target = null;
            aveyoutubeviewfragment.list = null;
            aveyoutubeviewfragment.swipeRefreshLayout = null;
            aveyoutubeviewfragment.loadMoreProgress = null;
            aveyoutubeviewfragment.overlayLayout = null;
            aveyoutubeviewfragment.mainLayout = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}

package com.mobiroller.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.CircleImageView;
import com.mobiroller.views.custom.MobirollerToolbar;

public class aveYoutubeAdvancedViewFragment_ViewBinding implements Unbinder {
    private aveYoutubeAdvancedViewFragment target;
    private View view7f0a0211;
    private View view7f0a021a;
    private View view7f0a0670;

    public aveYoutubeAdvancedViewFragment_ViewBinding(final aveYoutubeAdvancedViewFragment aveyoutubeadvancedviewfragment, View view) {
        this.target = aveyoutubeadvancedviewfragment;
        aveyoutubeadvancedviewfragment.toolbarAppBar = (MobirollerToolbar) C0812Utils.findRequiredViewAsType(view, R.id.toolbarAppBar, "field 'toolbarAppBar'", MobirollerToolbar.class);
        aveyoutubeadvancedviewfragment.appBarLayout = (AppBarLayout) C0812Utils.findRequiredViewAsType(view, R.id.appBarLayout, "field 'appBarLayout'", AppBarLayout.class);
        aveyoutubeadvancedviewfragment.youtubeChannelImage = (CircleImageView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_channel_image, "field 'youtubeChannelImage'", CircleImageView.class);
        aveyoutubeadvancedviewfragment.youtubeChannelName = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_channel_name, "field 'youtubeChannelName'", TextView.class);
        aveyoutubeadvancedviewfragment.youtubeChannelSubscriberCount = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_channel_subscriber_count, "field 'youtubeChannelSubscriberCount'", TextView.class);
        aveyoutubeadvancedviewfragment.youtubeChannelNameTop = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_channel_top_name, "field 'youtubeChannelNameTop'", TextView.class);
        aveyoutubeadvancedviewfragment.youtubeTopLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.youtube_top_layout, "field 'youtubeTopLayout'", RelativeLayout.class);
        aveyoutubeadvancedviewfragment.youtubeHeaderTopLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.youtube_advance_header_layout, "field 'youtubeHeaderTopLayout'", RelativeLayout.class);
        aveyoutubeadvancedviewfragment.coordinatorLayout = (CoordinatorLayout) C0812Utils.findRequiredViewAsType(view, R.id.overview_coordinator_layout, "field 'coordinatorLayout'", CoordinatorLayout.class);
        aveyoutubeadvancedviewfragment.nestedScrollView = (NestedScrollView) C0812Utils.findRequiredViewAsType(view, R.id.nested_scroll_view, "field 'nestedScrollView'", NestedScrollView.class);
        aveyoutubeadvancedviewfragment.collapsingToolbarLayout = (CollapsingToolbarLayout) C0812Utils.findRequiredViewAsType(view, R.id.collapsing_toolbar, "field 'collapsingToolbarLayout'", CollapsingToolbarLayout.class);
        aveyoutubeadvancedviewfragment.youtubeHeaderImage = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_header_image, "field 'youtubeHeaderImage'", ImageView.class);
        aveyoutubeadvancedviewfragment.youtubeAvatarSpace = (Space) C0812Utils.findRequiredViewAsType(view, R.id.circle_collapsed_target, "field 'youtubeAvatarSpace'", Space.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.youtube_subscribe_button, "field 'youtubeSubscribeButton' and method 'subscribeChannel'");
        aveyoutubeadvancedviewfragment.youtubeSubscribeButton = (TextView) C0812Utils.castView(findRequiredView, R.id.youtube_subscribe_button, "field 'youtubeSubscribeButton'", TextView.class);
        this.view7f0a0670 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                aveyoutubeadvancedviewfragment.subscribeChannel();
            }
        });
        aveyoutubeadvancedviewfragment.youtubeSubscribeButtonBackground = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_subscribe_button_background, "field 'youtubeSubscribeButtonBackground'", ImageView.class);
        aveyoutubeadvancedviewfragment.youtubeSubscribeLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.youtube_subscribe_layout, "field 'youtubeSubscribeLayout'", RelativeLayout.class);
        View findRequiredView2 = C0812Utils.findRequiredView(view, R.id.empty, "field 'youtubeMenuSpace' and method 'onClickMenuEmptyMenu'");
        aveyoutubeadvancedviewfragment.youtubeMenuSpace = (AppCompatImageView) C0812Utils.castView(findRequiredView2, R.id.empty, "field 'youtubeMenuSpace'", AppCompatImageView.class);
        this.view7f0a0211 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                aveyoutubeadvancedviewfragment.onClickMenuEmptyMenu();
            }
        });
        aveyoutubeadvancedviewfragment.mainLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.main_layout, "field 'mainLayout'", RelativeLayout.class);
        aveyoutubeadvancedviewfragment.overlayLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.overlay_layout, "field 'overlayLayout'", RelativeLayout.class);
        View findRequiredView3 = C0812Utils.findRequiredView(view, R.id.empty_right, "method 'onClickChannelInfo'");
        this.view7f0a021a = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                aveyoutubeadvancedviewfragment.onClickChannelInfo();
            }
        });
    }

    public void unbind() {
        aveYoutubeAdvancedViewFragment aveyoutubeadvancedviewfragment = this.target;
        if (aveyoutubeadvancedviewfragment != null) {
            this.target = null;
            aveyoutubeadvancedviewfragment.toolbarAppBar = null;
            aveyoutubeadvancedviewfragment.appBarLayout = null;
            aveyoutubeadvancedviewfragment.youtubeChannelImage = null;
            aveyoutubeadvancedviewfragment.youtubeChannelName = null;
            aveyoutubeadvancedviewfragment.youtubeChannelSubscriberCount = null;
            aveyoutubeadvancedviewfragment.youtubeChannelNameTop = null;
            aveyoutubeadvancedviewfragment.youtubeTopLayout = null;
            aveyoutubeadvancedviewfragment.youtubeHeaderTopLayout = null;
            aveyoutubeadvancedviewfragment.coordinatorLayout = null;
            aveyoutubeadvancedviewfragment.nestedScrollView = null;
            aveyoutubeadvancedviewfragment.collapsingToolbarLayout = null;
            aveyoutubeadvancedviewfragment.youtubeHeaderImage = null;
            aveyoutubeadvancedviewfragment.youtubeAvatarSpace = null;
            aveyoutubeadvancedviewfragment.youtubeSubscribeButton = null;
            aveyoutubeadvancedviewfragment.youtubeSubscribeButtonBackground = null;
            aveyoutubeadvancedviewfragment.youtubeSubscribeLayout = null;
            aveyoutubeadvancedviewfragment.youtubeMenuSpace = null;
            aveyoutubeadvancedviewfragment.mainLayout = null;
            aveyoutubeadvancedviewfragment.overlayLayout = null;
            this.view7f0a0670.setOnClickListener(null);
            this.view7f0a0670 = null;
            this.view7f0a0211.setOnClickListener(null);
            this.view7f0a0211 = null;
            this.view7f0a021a.setOnClickListener(null);
            this.view7f0a021a = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}

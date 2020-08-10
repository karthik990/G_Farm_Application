package com.mobiroller.activities.youtubeadvanced;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.CircleImageView;

public class ChannelDetailActivity_ViewBinding implements Unbinder {
    private ChannelDetailActivity target;
    private View view7f0a0670;

    public ChannelDetailActivity_ViewBinding(ChannelDetailActivity channelDetailActivity) {
        this(channelDetailActivity, channelDetailActivity.getWindow().getDecorView());
    }

    public ChannelDetailActivity_ViewBinding(final ChannelDetailActivity channelDetailActivity, View view) {
        this.target = channelDetailActivity;
        channelDetailActivity.description = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.description_content_text_view, "field 'description'", TextView.class);
        channelDetailActivity.statistics = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.statistics_content_text_view, "field 'statistics'", TextView.class);
        channelDetailActivity.joinDate = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.statistics_join_date_content_text_view, "field 'joinDate'", TextView.class);
        channelDetailActivity.toolbar = (Toolbar) C0812Utils.findRequiredViewAsType(view, R.id.toolbar, "field 'toolbar'", Toolbar.class);
        channelDetailActivity.appBarLayout = (AppBarLayout) C0812Utils.findRequiredViewAsType(view, R.id.appBarLayout, "field 'appBarLayout'", AppBarLayout.class);
        channelDetailActivity.mainLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.content_layout, "field 'mainLayout'", RelativeLayout.class);
        channelDetailActivity.youtubeChannelImage = (CircleImageView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_channel_image, "field 'youtubeChannelImage'", CircleImageView.class);
        channelDetailActivity.youtubeChannelName = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_channel_name, "field 'youtubeChannelName'", TextView.class);
        channelDetailActivity.youtubeChannelSubscriberCount = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_channel_subscriber_count, "field 'youtubeChannelSubscriberCount'", TextView.class);
        channelDetailActivity.youtubeChannelNameTop = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_channel_top_name, "field 'youtubeChannelNameTop'", TextView.class);
        channelDetailActivity.youtubeTopLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.youtube_top_layout, "field 'youtubeTopLayout'", RelativeLayout.class);
        channelDetailActivity.youtubeHeaderTopLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.youtube_advance_header_layout, "field 'youtubeHeaderTopLayout'", RelativeLayout.class);
        channelDetailActivity.coordinatorLayout = (CoordinatorLayout) C0812Utils.findRequiredViewAsType(view, R.id.overview_coordinator_layout, "field 'coordinatorLayout'", CoordinatorLayout.class);
        channelDetailActivity.nestedScrollView = (NestedScrollView) C0812Utils.findRequiredViewAsType(view, R.id.nested_scroll_view, "field 'nestedScrollView'", NestedScrollView.class);
        channelDetailActivity.collapsingToolbarLayout = (CollapsingToolbarLayout) C0812Utils.findRequiredViewAsType(view, R.id.collapsing_toolbar, "field 'collapsingToolbarLayout'", CollapsingToolbarLayout.class);
        channelDetailActivity.youtubeHeaderImage = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_header_image, "field 'youtubeHeaderImage'", ImageView.class);
        channelDetailActivity.youtubeAvatarSpace = (Space) C0812Utils.findRequiredViewAsType(view, R.id.circle_collapsed_target, "field 'youtubeAvatarSpace'", Space.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.youtube_subscribe_button, "field 'youtubeSubscribeButton' and method 'subscribeChannel'");
        channelDetailActivity.youtubeSubscribeButton = (TextView) C0812Utils.castView(findRequiredView, R.id.youtube_subscribe_button, "field 'youtubeSubscribeButton'", TextView.class);
        this.view7f0a0670 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                channelDetailActivity.subscribeChannel();
            }
        });
        channelDetailActivity.youtubeSubscribeButtonBackground = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_subscribe_button_background, "field 'youtubeSubscribeButtonBackground'", ImageView.class);
        channelDetailActivity.youtubeSubscribeLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.youtube_subscribe_layout, "field 'youtubeSubscribeLayout'", RelativeLayout.class);
    }

    public void unbind() {
        ChannelDetailActivity channelDetailActivity = this.target;
        if (channelDetailActivity != null) {
            this.target = null;
            channelDetailActivity.description = null;
            channelDetailActivity.statistics = null;
            channelDetailActivity.joinDate = null;
            channelDetailActivity.toolbar = null;
            channelDetailActivity.appBarLayout = null;
            channelDetailActivity.mainLayout = null;
            channelDetailActivity.youtubeChannelImage = null;
            channelDetailActivity.youtubeChannelName = null;
            channelDetailActivity.youtubeChannelSubscriberCount = null;
            channelDetailActivity.youtubeChannelNameTop = null;
            channelDetailActivity.youtubeTopLayout = null;
            channelDetailActivity.youtubeHeaderTopLayout = null;
            channelDetailActivity.coordinatorLayout = null;
            channelDetailActivity.nestedScrollView = null;
            channelDetailActivity.collapsingToolbarLayout = null;
            channelDetailActivity.youtubeHeaderImage = null;
            channelDetailActivity.youtubeAvatarSpace = null;
            channelDetailActivity.youtubeSubscribeButton = null;
            channelDetailActivity.youtubeSubscribeButtonBackground = null;
            channelDetailActivity.youtubeSubscribeLayout = null;
            this.view7f0a0670.setOnClickListener(null);
            this.view7f0a0670 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}

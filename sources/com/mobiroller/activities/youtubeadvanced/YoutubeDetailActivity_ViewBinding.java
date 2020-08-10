package com.mobiroller.activities.youtubeadvanced;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.google.android.youtube.player.YouTubePlayerView;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.CircleImageView;
import com.mobiroller.views.PullBackLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class YoutubeDetailActivity_ViewBinding implements Unbinder {
    private YoutubeDetailActivity target;
    private View view7f0a065f;
    private View view7f0a0666;
    private View view7f0a066a;
    private View view7f0a066e;
    private View view7f0a0673;

    public YoutubeDetailActivity_ViewBinding(YoutubeDetailActivity youtubeDetailActivity) {
        this(youtubeDetailActivity, youtubeDetailActivity.getWindow().getDecorView());
    }

    public YoutubeDetailActivity_ViewBinding(final YoutubeDetailActivity youtubeDetailActivity, View view) {
        this.target = youtubeDetailActivity;
        youtubeDetailActivity.youTubePlayerView = (YouTubePlayerView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_player, "field 'youTubePlayerView'", YouTubePlayerView.class);
        youtubeDetailActivity.youtubeVideoTitle = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_video_title, "field 'youtubeVideoTitle'", TextView.class);
        youtubeDetailActivity.youtubeVideoViewCount = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_video_view_count, "field 'youtubeVideoViewCount'", TextView.class);
        youtubeDetailActivity.youtubeLikeText = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_like_text, "field 'youtubeLikeText'", TextView.class);
        youtubeDetailActivity.youtubeDislikeText = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_dislike_text, "field 'youtubeDislikeText'", TextView.class);
        youtubeDetailActivity.youtubeCommentText = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_comment_text, "field 'youtubeCommentText'", TextView.class);
        youtubeDetailActivity.youtubeChannelTitle = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_channel_title, "field 'youtubeChannelTitle'", TextView.class);
        youtubeDetailActivity.youtubeChannelSubscriberCount = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_channel_subscriber_count, "field 'youtubeChannelSubscriberCount'", TextView.class);
        youtubeDetailActivity.youtubeSubscribeText = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_subscribe_button, "field 'youtubeSubscribeText'", TextView.class);
        youtubeDetailActivity.youtubeVideoDescription = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_video_description, "field 'youtubeVideoDescription'", TextView.class);
        youtubeDetailActivity.youtubeCommentTextComment = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_comment_text_comment, "field 'youtubeCommentTextComment'", TextView.class);
        youtubeDetailActivity.youtubeLikeIcon = (AppCompatImageView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_like_icon, "field 'youtubeLikeIcon'", AppCompatImageView.class);
        youtubeDetailActivity.youtubeDislikeIcon = (AppCompatImageView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_dislike_icon, "field 'youtubeDislikeIcon'", AppCompatImageView.class);
        youtubeDetailActivity.youtubeSubscribeIcon = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_subscribe_icon, "field 'youtubeSubscribeIcon'", ImageView.class);
        youtubeDetailActivity.youtubeCommentIcon = (AppCompatImageView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_comment_icon, "field 'youtubeCommentIcon'", AppCompatImageView.class);
        youtubeDetailActivity.youtubeShareIcon = (AppCompatImageView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_share_icon, "field 'youtubeShareIcon'", AppCompatImageView.class);
        youtubeDetailActivity.youtubeChannelIcon = (CircleImageView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_channel_image_view, "field 'youtubeChannelIcon'", CircleImageView.class);
        youtubeDetailActivity.youtubeCommentList = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.youtube_comment_list, "field 'youtubeCommentList'", RecyclerView.class);
        youtubeDetailActivity.slidingUpPanelLayout = (SlidingUpPanelLayout) C0812Utils.findRequiredViewAsType(view, R.id.sliding_layout, "field 'slidingUpPanelLayout'", SlidingUpPanelLayout.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.youtube_subscribe_layout, "field 'youtubeSubscribeLayout' and method 'subscribeToChannel'");
        youtubeDetailActivity.youtubeSubscribeLayout = (RelativeLayout) C0812Utils.castView(findRequiredView, R.id.youtube_subscribe_layout, "field 'youtubeSubscribeLayout'", RelativeLayout.class);
        this.view7f0a0673 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                youtubeDetailActivity.subscribeToChannel();
            }
        });
        youtubeDetailActivity.mainLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.main_layout, "field 'mainLayout'", RelativeLayout.class);
        youtubeDetailActivity.puller = (PullBackLayout) C0812Utils.findRequiredViewAsType(view, R.id.puller, "field 'puller'", PullBackLayout.class);
        View findRequiredView2 = C0812Utils.findRequiredView(view, R.id.youtube_like_layout, "method 'onClickLike'");
        this.view7f0a066a = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                youtubeDetailActivity.onClickLike();
            }
        });
        View findRequiredView3 = C0812Utils.findRequiredView(view, R.id.youtube_dislike_layout, "method 'onClickDislike'");
        this.view7f0a0666 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                youtubeDetailActivity.onClickDislike();
            }
        });
        View findRequiredView4 = C0812Utils.findRequiredView(view, R.id.youtube_comment_layout, "method 'onClickComment'");
        this.view7f0a065f = findRequiredView4;
        findRequiredView4.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                youtubeDetailActivity.onClickComment();
            }
        });
        View findRequiredView5 = C0812Utils.findRequiredView(view, R.id.youtube_share_layout, "method 'shareLink'");
        this.view7f0a066e = findRequiredView5;
        findRequiredView5.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                youtubeDetailActivity.shareLink();
            }
        });
    }

    public void unbind() {
        YoutubeDetailActivity youtubeDetailActivity = this.target;
        if (youtubeDetailActivity != null) {
            this.target = null;
            youtubeDetailActivity.youTubePlayerView = null;
            youtubeDetailActivity.youtubeVideoTitle = null;
            youtubeDetailActivity.youtubeVideoViewCount = null;
            youtubeDetailActivity.youtubeLikeText = null;
            youtubeDetailActivity.youtubeDislikeText = null;
            youtubeDetailActivity.youtubeCommentText = null;
            youtubeDetailActivity.youtubeChannelTitle = null;
            youtubeDetailActivity.youtubeChannelSubscriberCount = null;
            youtubeDetailActivity.youtubeSubscribeText = null;
            youtubeDetailActivity.youtubeVideoDescription = null;
            youtubeDetailActivity.youtubeCommentTextComment = null;
            youtubeDetailActivity.youtubeLikeIcon = null;
            youtubeDetailActivity.youtubeDislikeIcon = null;
            youtubeDetailActivity.youtubeSubscribeIcon = null;
            youtubeDetailActivity.youtubeCommentIcon = null;
            youtubeDetailActivity.youtubeShareIcon = null;
            youtubeDetailActivity.youtubeChannelIcon = null;
            youtubeDetailActivity.youtubeCommentList = null;
            youtubeDetailActivity.slidingUpPanelLayout = null;
            youtubeDetailActivity.youtubeSubscribeLayout = null;
            youtubeDetailActivity.mainLayout = null;
            youtubeDetailActivity.puller = null;
            this.view7f0a0673.setOnClickListener(null);
            this.view7f0a0673 = null;
            this.view7f0a066a.setOnClickListener(null);
            this.view7f0a066a = null;
            this.view7f0a0666.setOnClickListener(null);
            this.view7f0a0666 = null;
            this.view7f0a065f.setOnClickListener(null);
            this.view7f0a065f = null;
            this.view7f0a066e.setOnClickListener(null);
            this.view7f0a066e = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}

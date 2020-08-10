package com.twitter.sdk.android.tweetui.internal;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.twitter.sdk.android.core.internal.VineCardUtils;
import com.twitter.sdk.android.core.models.Card;
import com.twitter.sdk.android.core.models.MediaEntity;
import com.twitter.sdk.android.tweetui.C5234R;

public class MediaBadgeView extends FrameLayout {
    ImageView badge;
    TextView videoDuration;

    public MediaBadgeView(Context context) {
        this(context, null);
    }

    public MediaBadgeView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MediaBadgeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initSubViews(context);
    }

    /* access modifiers changed from: 0000 */
    public void initSubViews(Context context) {
        View inflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(C5234R.layout.tw__media_badge, this, true);
        this.videoDuration = (TextView) inflate.findViewById(C5234R.C5237id.tw__video_duration);
        this.badge = (ImageView) inflate.findViewById(C5234R.C5237id.tw__gif_badge);
    }

    public void setMediaEntity(MediaEntity mediaEntity) {
        if (TweetMediaUtils.GIF_TYPE.equals(mediaEntity.type)) {
            setBadge(getResources().getDrawable(C5234R.C5236drawable.tw__gif_badge));
            return;
        }
        if ("video".equals(mediaEntity.type)) {
            setText(mediaEntity.videoInfo == null ? 0 : mediaEntity.videoInfo.durationMillis);
        } else {
            setEmpty();
        }
    }

    public void setCard(Card card) {
        if (VineCardUtils.isVine(card)) {
            setBadge(getResources().getDrawable(C5234R.C5236drawable.tw__vine_badge));
        } else {
            setEmpty();
        }
    }

    /* access modifiers changed from: 0000 */
    public void setText(long j) {
        this.videoDuration.setVisibility(0);
        this.badge.setVisibility(8);
        this.videoDuration.setText(MediaTimeUtils.getPlaybackTime(j));
    }

    /* access modifiers changed from: 0000 */
    public void setBadge(Drawable drawable) {
        this.badge.setVisibility(0);
        this.videoDuration.setVisibility(8);
        this.badge.setImageDrawable(drawable);
    }

    /* access modifiers changed from: 0000 */
    public void setEmpty() {
        this.videoDuration.setVisibility(8);
        this.badge.setVisibility(8);
    }
}

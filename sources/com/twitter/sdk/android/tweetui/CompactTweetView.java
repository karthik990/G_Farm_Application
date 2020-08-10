package com.twitter.sdk.android.tweetui;

import android.content.Context;
import android.util.AttributeSet;
import com.twitter.sdk.android.core.models.MediaEntity;
import com.twitter.sdk.android.core.models.Tweet;

public class CompactTweetView extends BaseTweetView {
    private static final double DEFAULT_ASPECT_RATIO_MEDIA_CONTAINER = 1.6d;
    private static final double MAX_LANDSCAPE_ASPECT_RATIO = 3.0d;
    private static final double MIN_LANDSCAPE_ASPECT_RATIO = 1.3333333333333333d;
    private static final double SQUARE_ASPECT_RATIO = 1.0d;

    /* access modifiers changed from: protected */
    public double getAspectRatioForPhotoEntity(int i) {
        return DEFAULT_ASPECT_RATIO_MEDIA_CONTAINER;
    }

    public CompactTweetView(Context context, Tweet tweet) {
        super(context, tweet);
    }

    public CompactTweetView(Context context, Tweet tweet, int i) {
        super(context, tweet, i);
    }

    CompactTweetView(Context context, Tweet tweet, int i, DependencyProvider dependencyProvider) {
        super(context, tweet, i, dependencyProvider);
    }

    public CompactTweetView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CompactTweetView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public int getLayout() {
        return C5234R.layout.tw__tweet_compact;
    }

    /* access modifiers changed from: 0000 */
    public void render() {
        super.render();
        this.screenNameView.requestLayout();
    }

    /* access modifiers changed from: protected */
    public void applyStyles() {
        super.applyStyles();
        setPadding(0, getResources().getDimensionPixelSize(C5234R.dimen.tw__compact_tweet_container_padding_top), 0, 0);
        int dimensionPixelSize = getResources().getDimensionPixelSize(C5234R.dimen.tw__media_view_radius);
        this.tweetMediaView.setRoundedCornersRadii(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
    }

    /* access modifiers changed from: protected */
    public double getAspectRatio(MediaEntity mediaEntity) {
        double aspectRatio = super.getAspectRatio(mediaEntity);
        if (aspectRatio <= SQUARE_ASPECT_RATIO) {
            return SQUARE_ASPECT_RATIO;
        }
        if (aspectRatio > MAX_LANDSCAPE_ASPECT_RATIO) {
            return MAX_LANDSCAPE_ASPECT_RATIO;
        }
        return aspectRatio < MIN_LANDSCAPE_ASPECT_RATIO ? MIN_LANDSCAPE_ASPECT_RATIO : aspectRatio;
    }
}

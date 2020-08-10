package com.twitter.sdk.android.tweetui;

import android.content.Context;
import android.util.AttributeSet;
import com.twitter.sdk.android.core.models.Tweet;

public class TweetView extends BaseTweetView {
    private static final double DEFAULT_ASPECT_RATIO_MEDIA_CONTAINER = 1.5d;
    private static final double SQUARE_ASPECT_RATIO = 1.0d;
    private static final String VIEW_TYPE_NAME = "default";

    /* access modifiers changed from: protected */
    public double getAspectRatioForPhotoEntity(int i) {
        if (i == 4) {
            return SQUARE_ASPECT_RATIO;
        }
        return 1.5d;
    }

    public TweetView(Context context, Tweet tweet) {
        super(context, tweet);
    }

    public TweetView(Context context, Tweet tweet, int i) {
        super(context, tweet, i);
    }

    TweetView(Context context, Tweet tweet, int i, DependencyProvider dependencyProvider) {
        super(context, tweet, i, dependencyProvider);
    }

    public TweetView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TweetView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public int getLayout() {
        return C5234R.layout.tw__tweet;
    }

    /* access modifiers changed from: 0000 */
    public void render() {
        super.render();
        setVerifiedCheck(this.tweet);
    }

    private void setVerifiedCheck(Tweet tweet) {
        if (tweet == null || tweet.user == null || !tweet.user.verified) {
            this.fullNameView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        } else {
            this.fullNameView.setCompoundDrawablesWithIntrinsicBounds(0, 0, C5234R.C5236drawable.tw__ic_tweet_verified, 0);
        }
    }
}

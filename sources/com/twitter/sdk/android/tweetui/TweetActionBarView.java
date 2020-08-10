package com.twitter.sdk.android.tweetui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.models.Tweet;

public class TweetActionBarView extends LinearLayout {
    Callback<Tweet> actionCallback;
    final DependencyProvider dependencyProvider;
    ToggleImageButton likeButton;
    ImageButton shareButton;

    static class DependencyProvider {
        DependencyProvider() {
        }

        /* access modifiers changed from: 0000 */
        public TweetUi getTweetUi() {
            return TweetUi.getInstance();
        }
    }

    public TweetActionBarView(Context context) {
        this(context, null, new DependencyProvider());
    }

    public TweetActionBarView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, new DependencyProvider());
    }

    TweetActionBarView(Context context, AttributeSet attributeSet, DependencyProvider dependencyProvider2) {
        super(context, attributeSet);
        this.dependencyProvider = dependencyProvider2;
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        findSubviews();
    }

    /* access modifiers changed from: 0000 */
    public void setOnActionCallback(Callback<Tweet> callback) {
        this.actionCallback = callback;
    }

    /* access modifiers changed from: 0000 */
    public void findSubviews() {
        this.likeButton = (ToggleImageButton) findViewById(C5234R.C5237id.tw__tweet_like_button);
        this.shareButton = (ImageButton) findViewById(C5234R.C5237id.tw__tweet_share_button);
    }

    /* access modifiers changed from: 0000 */
    public void setTweet(Tweet tweet) {
        setLike(tweet);
        setShare(tweet);
    }

    /* access modifiers changed from: 0000 */
    public void setLike(Tweet tweet) {
        TweetUi tweetUi = this.dependencyProvider.getTweetUi();
        if (tweet != null) {
            this.likeButton.setToggledOn(tweet.favorited);
            this.likeButton.setOnClickListener(new LikeTweetAction(tweet, tweetUi, this.actionCallback));
        }
    }

    /* access modifiers changed from: 0000 */
    public void setShare(Tweet tweet) {
        TweetUi tweetUi = this.dependencyProvider.getTweetUi();
        if (tweet != null) {
            this.shareButton.setOnClickListener(new ShareTweetAction(tweet, tweetUi));
        }
    }
}

package com.twitter.sdk.android.tweetui;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;

class ResetTweetCallback extends Callback<Tweet> {
    final BaseTweetView baseTweetView;

    /* renamed from: cb */
    final Callback<Tweet> f3671cb;
    final TweetRepository tweetRepository;

    ResetTweetCallback(BaseTweetView baseTweetView2, TweetRepository tweetRepository2, Callback<Tweet> callback) {
        this.baseTweetView = baseTweetView2;
        this.tweetRepository = tweetRepository2;
        this.f3671cb = callback;
    }

    public void success(Result<Tweet> result) {
        this.tweetRepository.updateCache((Tweet) result.data);
        this.baseTweetView.setTweet((Tweet) result.data);
        Callback<Tweet> callback = this.f3671cb;
        if (callback != null) {
            callback.success(result);
        }
    }

    public void failure(TwitterException twitterException) {
        Callback<Tweet> callback = this.f3671cb;
        if (callback != null) {
            callback.failure(twitterException);
        }
    }
}

package com.twitter.sdk.android.tweetui;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Logger;
import com.twitter.sdk.android.core.TwitterException;

abstract class LoggingCallback<T> extends Callback<T> {

    /* renamed from: cb */
    private final Callback f3670cb;
    private final Logger logger;

    LoggingCallback(Callback callback, Logger logger2) {
        this.f3670cb = callback;
        this.logger = logger2;
    }

    public void failure(TwitterException twitterException) {
        this.logger.mo20820e("TweetUi", twitterException.getMessage(), twitterException);
        Callback callback = this.f3670cb;
        if (callback != null) {
            callback.failure(twitterException);
        }
    }
}

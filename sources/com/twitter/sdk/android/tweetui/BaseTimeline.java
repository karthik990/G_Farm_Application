package com.twitter.sdk.android.tweetui;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import java.util.List;

abstract class BaseTimeline {

    static class TweetsCallback extends Callback<List<Tweet>> {

        /* renamed from: cb */
        final Callback<TimelineResult<Tweet>> f3667cb;

        TweetsCallback(Callback<TimelineResult<Tweet>> callback) {
            this.f3667cb = callback;
        }

        public void success(Result<List<Tweet>> result) {
            List list = (List) result.data;
            TimelineResult timelineResult = new TimelineResult(new TimelineCursor(list), list);
            Callback<TimelineResult<Tweet>> callback = this.f3667cb;
            if (callback != null) {
                callback.success(new Result(timelineResult, result.response));
            }
        }

        public void failure(TwitterException twitterException) {
            Callback<TimelineResult<Tweet>> callback = this.f3667cb;
            if (callback != null) {
                callback.failure(twitterException);
            }
        }
    }

    BaseTimeline() {
    }

    static Long decrementMaxId(Long l) {
        if (l == null) {
            return null;
        }
        return Long.valueOf(l.longValue() - 1);
    }
}

package com.twitter.sdk.android.tweetui;

import android.os.Handler;
import android.os.Looper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import java.util.List;
import java.util.concurrent.ExecutorService;

class FilterTimelineDelegate extends TimelineDelegate<Tweet> {
    static final String TOTAL_APPLIED_FILTERS_JSON_PROP = "total_filters";
    static final String TWEETS_COUNT_JSON_PROP = "tweet_count";
    static final String TWEETS_FILTERED_JSON_PROP = "tweets_filtered";
    final Gson gson = new Gson();
    final TimelineFilter timelineFilter;
    final TweetUi tweetUi;

    class TimelineFilterCallback extends Callback<TimelineResult<Tweet>> {
        final DefaultCallback callback;
        final ExecutorService executorService = Twitter.getInstance().getExecutorService();
        final Handler handler = new Handler(Looper.getMainLooper());
        final TimelineFilter timelineFilter;

        TimelineFilterCallback(DefaultCallback defaultCallback, TimelineFilter timelineFilter2) {
            this.callback = defaultCallback;
            this.timelineFilter = timelineFilter2;
        }

        public void success(Result<TimelineResult<Tweet>> result) {
            this.executorService.execute(new Runnable(result) {
                private final /* synthetic */ Result f$1;

                {
                    this.f$1 = r2;
                }

                public final void run() {
                    TimelineFilterCallback.this.lambda$success$1$FilterTimelineDelegate$TimelineFilterCallback(this.f$1);
                }
            });
        }

        public /* synthetic */ void lambda$success$1$FilterTimelineDelegate$TimelineFilterCallback(Result result) {
            this.handler.post(new Runnable(buildTimelineResult(((TimelineResult) result.data).timelineCursor, this.timelineFilter.filter(((TimelineResult) result.data).items)), result) {
                private final /* synthetic */ TimelineResult f$1;
                private final /* synthetic */ Result f$2;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                }

                public final void run() {
                    TimelineFilterCallback.this.lambda$null$0$FilterTimelineDelegate$TimelineFilterCallback(this.f$1, this.f$2);
                }
            });
        }

        public /* synthetic */ void lambda$null$0$FilterTimelineDelegate$TimelineFilterCallback(TimelineResult timelineResult, Result result) {
            this.callback.success(new Result(timelineResult, result.response));
        }

        public void failure(TwitterException twitterException) {
            DefaultCallback defaultCallback = this.callback;
            if (defaultCallback != null) {
                defaultCallback.failure(twitterException);
            }
        }

        /* access modifiers changed from: 0000 */
        public TimelineResult<Tweet> buildTimelineResult(TimelineCursor timelineCursor, List<Tweet> list) {
            return new TimelineResult<>(timelineCursor, list);
        }
    }

    FilterTimelineDelegate(Timeline<Tweet> timeline, TimelineFilter timelineFilter2) {
        super(timeline);
        this.timelineFilter = timelineFilter2;
        this.tweetUi = TweetUi.getInstance();
    }

    public void refresh(Callback<TimelineResult<Tweet>> callback) {
        this.timelineStateHolder.resetCursors();
        loadNext(this.timelineStateHolder.positionForNext(), new TimelineFilterCallback(new RefreshCallback(callback, this.timelineStateHolder), this.timelineFilter));
    }

    public void next(Callback<TimelineResult<Tweet>> callback) {
        loadNext(this.timelineStateHolder.positionForNext(), new TimelineFilterCallback(new NextCallback(callback, this.timelineStateHolder), this.timelineFilter));
    }

    public void previous() {
        loadPrevious(this.timelineStateHolder.positionForPrevious(), new TimelineFilterCallback(new PreviousCallback(this.timelineStateHolder), this.timelineFilter));
    }

    private String getJsonMessage(int i, int i2, int i3) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(TWEETS_COUNT_JSON_PROP, (Number) Integer.valueOf(i));
        jsonObject.addProperty(TWEETS_FILTERED_JSON_PROP, (Number) Integer.valueOf(i - i2));
        jsonObject.addProperty(TOTAL_APPLIED_FILTERS_JSON_PROP, (Number) Integer.valueOf(i3));
        return this.gson.toJson((JsonElement) jsonObject);
    }
}

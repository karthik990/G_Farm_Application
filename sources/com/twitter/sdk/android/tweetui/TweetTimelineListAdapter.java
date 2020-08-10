package com.twitter.sdk.android.tweetui;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Identifiable;
import com.twitter.sdk.android.core.models.Tweet;

public class TweetTimelineListAdapter extends TimelineListAdapter<Tweet> {
    protected Callback<Tweet> actionCallback;
    protected final int styleResId;
    protected TweetUi tweetUi;

    public static class Builder {
        private Callback<Tweet> actionCallback;
        private Context context;
        private int styleResId = C5234R.C5239style.tw__TweetLightStyle;
        private Timeline<Tweet> timeline;
        private TimelineFilter timelineFilter;

        public Builder(Context context2) {
            this.context = context2;
        }

        public Builder setTimeline(Timeline<Tweet> timeline2) {
            this.timeline = timeline2;
            return this;
        }

        public Builder setViewStyle(int i) {
            this.styleResId = i;
            return this;
        }

        public Builder setOnActionCallback(Callback<Tweet> callback) {
            this.actionCallback = callback;
            return this;
        }

        public Builder setTimelineFilter(TimelineFilter timelineFilter2) {
            this.timelineFilter = timelineFilter2;
            return this;
        }

        public TweetTimelineListAdapter build() {
            TimelineFilter timelineFilter2 = this.timelineFilter;
            if (timelineFilter2 == null) {
                return new TweetTimelineListAdapter(this.context, this.timeline, this.styleResId, this.actionCallback);
            }
            TweetTimelineListAdapter tweetTimelineListAdapter = new TweetTimelineListAdapter(this.context, new FilterTimelineDelegate(this.timeline, timelineFilter2), this.styleResId, this.actionCallback, TweetUi.getInstance());
            return tweetTimelineListAdapter;
        }
    }

    static class ReplaceTweetCallback extends Callback<Tweet> {

        /* renamed from: cb */
        Callback<Tweet> f3675cb;
        TimelineDelegate<Tweet> delegate;

        ReplaceTweetCallback(TimelineDelegate<Tweet> timelineDelegate, Callback<Tweet> callback) {
            this.delegate = timelineDelegate;
            this.f3675cb = callback;
        }

        public void success(Result<Tweet> result) {
            this.delegate.setItemById((Identifiable) result.data);
            Callback<Tweet> callback = this.f3675cb;
            if (callback != null) {
                callback.success(result);
            }
        }

        public void failure(TwitterException twitterException) {
            Callback<Tweet> callback = this.f3675cb;
            if (callback != null) {
                callback.failure(twitterException);
            }
        }
    }

    public /* bridge */ /* synthetic */ int getCount() {
        return super.getCount();
    }

    public /* bridge */ /* synthetic */ long getItemId(int i) {
        return super.getItemId(i);
    }

    public /* bridge */ /* synthetic */ void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public /* bridge */ /* synthetic */ void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
    }

    public /* bridge */ /* synthetic */ void refresh(Callback callback) {
        super.refresh(callback);
    }

    public /* bridge */ /* synthetic */ void registerDataSetObserver(DataSetObserver dataSetObserver) {
        super.registerDataSetObserver(dataSetObserver);
    }

    public /* bridge */ /* synthetic */ void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        super.unregisterDataSetObserver(dataSetObserver);
    }

    public TweetTimelineListAdapter(Context context, Timeline<Tweet> timeline) {
        this(context, timeline, C5234R.C5239style.tw__TweetLightStyle, null);
    }

    TweetTimelineListAdapter(Context context, Timeline<Tweet> timeline, int i, Callback<Tweet> callback) {
        this(context, new TimelineDelegate(timeline), i, callback, TweetUi.getInstance());
    }

    TweetTimelineListAdapter(Context context, TimelineDelegate<Tweet> timelineDelegate, int i, Callback<Tweet> callback, TweetUi tweetUi2) {
        super(context, timelineDelegate);
        this.styleResId = i;
        this.actionCallback = new ReplaceTweetCallback(timelineDelegate, callback);
        this.tweetUi = tweetUi2;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        Tweet tweet = (Tweet) getItem(i);
        if (view == null) {
            CompactTweetView compactTweetView = new CompactTweetView(this.context, tweet, this.styleResId);
            compactTweetView.setOnActionCallback(this.actionCallback);
            return compactTweetView;
        }
        ((BaseTweetView) view).setTweet(tweet);
        return view;
    }
}

package com.twitter.sdk.android.tweetui;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Identifiable;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.TweetBuilder;

public class TweetTimelineRecyclerViewAdapter extends Adapter<TweetViewHolder> {
    protected Callback<Tweet> actionCallback;
    protected final Context context;
    /* access modifiers changed from: private */
    public int previousCount;
    protected final int styleResId;
    protected final TimelineDelegate<Tweet> timelineDelegate;
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

        public TweetTimelineRecyclerViewAdapter build() {
            TimelineFilter timelineFilter2 = this.timelineFilter;
            if (timelineFilter2 == null) {
                return new TweetTimelineRecyclerViewAdapter(this.context, this.timeline, this.styleResId, this.actionCallback);
            }
            TweetTimelineRecyclerViewAdapter tweetTimelineRecyclerViewAdapter = new TweetTimelineRecyclerViewAdapter(this.context, new FilterTimelineDelegate(this.timeline, timelineFilter2), this.styleResId, this.actionCallback, TweetUi.getInstance());
            return tweetTimelineRecyclerViewAdapter;
        }
    }

    static class ReplaceTweetCallback extends Callback<Tweet> {

        /* renamed from: cb */
        Callback<Tweet> f3676cb;
        TimelineDelegate<Tweet> delegate;

        ReplaceTweetCallback(TimelineDelegate<Tweet> timelineDelegate, Callback<Tweet> callback) {
            this.delegate = timelineDelegate;
            this.f3676cb = callback;
        }

        public void success(Result<Tweet> result) {
            this.delegate.setItemById((Identifiable) result.data);
            Callback<Tweet> callback = this.f3676cb;
            if (callback != null) {
                callback.success(result);
            }
        }

        public void failure(TwitterException twitterException) {
            Callback<Tweet> callback = this.f3676cb;
            if (callback != null) {
                callback.failure(twitterException);
            }
        }
    }

    public static final class TweetViewHolder extends ViewHolder {
        public TweetViewHolder(CompactTweetView compactTweetView) {
            super(compactTweetView);
        }
    }

    public TweetTimelineRecyclerViewAdapter(Context context2, Timeline<Tweet> timeline) {
        this(context2, timeline, C5234R.C5239style.tw__TweetLightStyle, null);
    }

    protected TweetTimelineRecyclerViewAdapter(Context context2, Timeline<Tweet> timeline, int i, Callback<Tweet> callback) {
        this(context2, new TimelineDelegate(timeline), i, callback, TweetUi.getInstance());
    }

    TweetTimelineRecyclerViewAdapter(Context context2, TimelineDelegate<Tweet> timelineDelegate2, int i, Callback<Tweet> callback, TweetUi tweetUi2) {
        this(context2, timelineDelegate2, i);
        this.actionCallback = new ReplaceTweetCallback(timelineDelegate2, callback);
        this.tweetUi = tweetUi2;
    }

    TweetTimelineRecyclerViewAdapter(Context context2, TimelineDelegate<Tweet> timelineDelegate2, int i) {
        if (context2 != null) {
            this.context = context2;
            this.timelineDelegate = timelineDelegate2;
            this.styleResId = i;
            this.timelineDelegate.refresh(new Callback<TimelineResult<Tweet>>() {
                public void failure(TwitterException twitterException) {
                }

                public void success(Result<TimelineResult<Tweet>> result) {
                    TweetTimelineRecyclerViewAdapter.this.notifyDataSetChanged();
                    TweetTimelineRecyclerViewAdapter tweetTimelineRecyclerViewAdapter = TweetTimelineRecyclerViewAdapter.this;
                    tweetTimelineRecyclerViewAdapter.previousCount = tweetTimelineRecyclerViewAdapter.timelineDelegate.getCount();
                }
            });
            this.timelineDelegate.registerDataSetObserver(new DataSetObserver() {
                public void onChanged() {
                    super.onChanged();
                    if (TweetTimelineRecyclerViewAdapter.this.previousCount == 0) {
                        TweetTimelineRecyclerViewAdapter.this.notifyDataSetChanged();
                    } else {
                        TweetTimelineRecyclerViewAdapter tweetTimelineRecyclerViewAdapter = TweetTimelineRecyclerViewAdapter.this;
                        tweetTimelineRecyclerViewAdapter.notifyItemRangeInserted(tweetTimelineRecyclerViewAdapter.previousCount, TweetTimelineRecyclerViewAdapter.this.timelineDelegate.getCount() - TweetTimelineRecyclerViewAdapter.this.previousCount);
                    }
                    TweetTimelineRecyclerViewAdapter tweetTimelineRecyclerViewAdapter2 = TweetTimelineRecyclerViewAdapter.this;
                    tweetTimelineRecyclerViewAdapter2.previousCount = tweetTimelineRecyclerViewAdapter2.timelineDelegate.getCount();
                }

                public void onInvalidated() {
                    TweetTimelineRecyclerViewAdapter.this.notifyDataSetChanged();
                    super.onInvalidated();
                }
            });
            return;
        }
        throw new IllegalArgumentException("Context must not be null");
    }

    public void refresh(Callback<TimelineResult<Tweet>> callback) {
        this.timelineDelegate.refresh(callback);
        this.previousCount = 0;
    }

    public TweetViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        CompactTweetView compactTweetView = new CompactTweetView(this.context, new TweetBuilder().build(), this.styleResId);
        compactTweetView.setOnActionCallback(this.actionCallback);
        return new TweetViewHolder(compactTweetView);
    }

    public void onBindViewHolder(TweetViewHolder tweetViewHolder, int i) {
        ((CompactTweetView) tweetViewHolder.itemView).setTweet((Tweet) this.timelineDelegate.getItem(i));
    }

    public int getItemCount() {
        return this.timelineDelegate.getCount();
    }
}

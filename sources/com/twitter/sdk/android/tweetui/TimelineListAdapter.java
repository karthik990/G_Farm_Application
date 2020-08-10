package com.twitter.sdk.android.tweetui;

import android.content.Context;
import android.database.DataSetObserver;
import android.widget.BaseAdapter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.models.Identifiable;

abstract class TimelineListAdapter<T extends Identifiable> extends BaseAdapter {
    protected final Context context;
    protected final TimelineDelegate<T> delegate;

    TimelineListAdapter(Context context2, Timeline<T> timeline) {
        this(context2, new TimelineDelegate<>(timeline));
    }

    TimelineListAdapter(Context context2, TimelineDelegate<T> timelineDelegate) {
        if (context2 != null) {
            this.context = context2;
            this.delegate = timelineDelegate;
            timelineDelegate.refresh(null);
            return;
        }
        throw new IllegalArgumentException("Context must not be null");
    }

    public void refresh(Callback<TimelineResult<T>> callback) {
        this.delegate.refresh(callback);
    }

    public int getCount() {
        return this.delegate.getCount();
    }

    public T getItem(int i) {
        return this.delegate.getItem(i);
    }

    public long getItemId(int i) {
        return this.delegate.getItemId(i);
    }

    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        this.delegate.registerDataSetObserver(dataSetObserver);
    }

    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        this.delegate.unregisterDataSetObserver(dataSetObserver);
    }

    public void notifyDataSetChanged() {
        this.delegate.notifyDataSetChanged();
    }

    public void notifyDataSetInvalidated() {
        this.delegate.notifyDataSetInvalidated();
    }
}

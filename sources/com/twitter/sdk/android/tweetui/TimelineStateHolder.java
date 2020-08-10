package com.twitter.sdk.android.tweetui;

import java.util.concurrent.atomic.AtomicBoolean;

class TimelineStateHolder {
    TimelineCursor nextCursor;
    TimelineCursor previousCursor;
    public final AtomicBoolean requestInFlight = new AtomicBoolean(false);

    TimelineStateHolder() {
    }

    TimelineStateHolder(TimelineCursor timelineCursor, TimelineCursor timelineCursor2) {
        this.nextCursor = timelineCursor;
        this.previousCursor = timelineCursor2;
    }

    public void resetCursors() {
        this.nextCursor = null;
        this.previousCursor = null;
    }

    public Long positionForNext() {
        TimelineCursor timelineCursor = this.nextCursor;
        if (timelineCursor == null) {
            return null;
        }
        return timelineCursor.maxPosition;
    }

    public Long positionForPrevious() {
        TimelineCursor timelineCursor = this.previousCursor;
        if (timelineCursor == null) {
            return null;
        }
        return timelineCursor.minPosition;
    }

    public void setNextCursor(TimelineCursor timelineCursor) {
        this.nextCursor = timelineCursor;
        setCursorsIfNull(timelineCursor);
    }

    public void setPreviousCursor(TimelineCursor timelineCursor) {
        this.previousCursor = timelineCursor;
        setCursorsIfNull(timelineCursor);
    }

    public void setCursorsIfNull(TimelineCursor timelineCursor) {
        if (this.nextCursor == null) {
            this.nextCursor = timelineCursor;
        }
        if (this.previousCursor == null) {
            this.previousCursor = timelineCursor;
        }
    }

    public boolean startTimelineRequest() {
        return this.requestInFlight.compareAndSet(false, true);
    }

    public void finishTimelineRequest() {
        this.requestInFlight.set(false);
    }
}

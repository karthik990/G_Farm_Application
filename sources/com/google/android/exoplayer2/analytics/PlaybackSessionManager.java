package com.google.android.exoplayer2.analytics;

import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsListener.EventTime;
import com.google.android.exoplayer2.source.MediaSource.MediaPeriodId;

public interface PlaybackSessionManager {

    public interface Listener {
        void onAdPlaybackStarted(EventTime eventTime, String str, String str2);

        void onSessionActive(EventTime eventTime, String str);

        void onSessionCreated(EventTime eventTime, String str);

        void onSessionFinished(EventTime eventTime, String str, boolean z);
    }

    boolean belongsToSession(EventTime eventTime, String str);

    String getSessionForMediaPeriodId(Timeline timeline, MediaPeriodId mediaPeriodId);

    void handlePositionDiscontinuity(EventTime eventTime, int i);

    void handleTimelineUpdate(EventTime eventTime);

    void setListener(Listener listener);

    void updateSessions(EventTime eventTime);
}

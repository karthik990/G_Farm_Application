package com.google.android.exoplayer2.analytics;

import android.util.Base64;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.Timeline.Period;
import com.google.android.exoplayer2.Timeline.Window;
import com.google.android.exoplayer2.analytics.AnalyticsListener.EventTime;
import com.google.android.exoplayer2.analytics.PlaybackSessionManager.Listener;
import com.google.android.exoplayer2.source.MediaSource.MediaPeriodId;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

public final class DefaultPlaybackSessionManager implements PlaybackSessionManager {
    private static final Random RANDOM = new Random();
    private static final int SESSION_ID_LENGTH = 12;
    private String activeSessionId;
    private MediaPeriodId currentMediaPeriodId;
    private Timeline currentTimeline = Timeline.EMPTY;
    private Listener listener;
    /* access modifiers changed from: private */
    public final Period period = new Period();
    private final HashMap<String, SessionDescriptor> sessions = new HashMap<>();
    /* access modifiers changed from: private */
    public final Window window = new Window();

    private final class SessionDescriptor {
        /* access modifiers changed from: private */
        public MediaPeriodId adMediaPeriodId;
        /* access modifiers changed from: private */
        public boolean isActive;
        /* access modifiers changed from: private */
        public boolean isCreated;
        /* access modifiers changed from: private */
        public final String sessionId;
        private int windowIndex;
        /* access modifiers changed from: private */
        public long windowSequenceNumber;

        public SessionDescriptor(String str, int i, MediaPeriodId mediaPeriodId) {
            long j;
            this.sessionId = str;
            this.windowIndex = i;
            if (mediaPeriodId == null) {
                j = -1;
            } else {
                j = mediaPeriodId.windowSequenceNumber;
            }
            this.windowSequenceNumber = j;
            if (mediaPeriodId != null && mediaPeriodId.isAd()) {
                this.adMediaPeriodId = mediaPeriodId;
            }
        }

        public boolean tryResolvingToNewTimeline(Timeline timeline, Timeline timeline2) {
            this.windowIndex = resolveWindowIndexToNewTimeline(timeline, timeline2, this.windowIndex);
            boolean z = false;
            if (this.windowIndex == -1) {
                return false;
            }
            MediaPeriodId mediaPeriodId = this.adMediaPeriodId;
            if (mediaPeriodId == null) {
                return true;
            }
            if (timeline2.getIndexOfPeriod(mediaPeriodId.periodUid) != -1) {
                z = true;
            }
            return z;
        }

        public boolean belongsToSession(int i, MediaPeriodId mediaPeriodId) {
            boolean z = true;
            if (mediaPeriodId == null) {
                if (i != this.windowIndex) {
                    z = false;
                }
                return z;
            } else if (this.adMediaPeriodId == null) {
                if (mediaPeriodId.isAd() || mediaPeriodId.windowSequenceNumber != this.windowSequenceNumber) {
                    z = false;
                }
                return z;
            } else {
                if (!(mediaPeriodId.windowSequenceNumber == this.adMediaPeriodId.windowSequenceNumber && mediaPeriodId.adGroupIndex == this.adMediaPeriodId.adGroupIndex && mediaPeriodId.adIndexInAdGroup == this.adMediaPeriodId.adIndexInAdGroup)) {
                    z = false;
                }
                return z;
            }
        }

        public void maybeSetWindowSequenceNumber(int i, MediaPeriodId mediaPeriodId) {
            if (this.windowSequenceNumber == -1 && i == this.windowIndex && mediaPeriodId != null && !mediaPeriodId.isAd()) {
                this.windowSequenceNumber = mediaPeriodId.windowSequenceNumber;
            }
        }

        public boolean isFinishedAtEventTime(EventTime eventTime) {
            if (this.windowSequenceNumber == -1) {
                return false;
            }
            boolean z = true;
            if (eventTime.mediaPeriodId == null) {
                if (this.windowIndex == eventTime.windowIndex) {
                    z = false;
                }
                return z;
            } else if (eventTime.mediaPeriodId.windowSequenceNumber > this.windowSequenceNumber) {
                return true;
            } else {
                if (this.adMediaPeriodId == null) {
                    return false;
                }
                int indexOfPeriod = eventTime.timeline.getIndexOfPeriod(eventTime.mediaPeriodId.periodUid);
                int indexOfPeriod2 = eventTime.timeline.getIndexOfPeriod(this.adMediaPeriodId.periodUid);
                if (eventTime.mediaPeriodId.windowSequenceNumber < this.adMediaPeriodId.windowSequenceNumber || indexOfPeriod < indexOfPeriod2) {
                    return false;
                }
                if (indexOfPeriod > indexOfPeriod2) {
                    return true;
                }
                if (eventTime.mediaPeriodId.isAd()) {
                    int i = eventTime.mediaPeriodId.adGroupIndex;
                    int i2 = eventTime.mediaPeriodId.adIndexInAdGroup;
                    if (i <= this.adMediaPeriodId.adGroupIndex && (i != this.adMediaPeriodId.adGroupIndex || i2 <= this.adMediaPeriodId.adIndexInAdGroup)) {
                        z = false;
                    }
                    return z;
                }
                if (eventTime.mediaPeriodId.nextAdGroupIndex != -1 && eventTime.mediaPeriodId.nextAdGroupIndex <= this.adMediaPeriodId.adGroupIndex) {
                    z = false;
                }
                return z;
            }
        }

        private int resolveWindowIndexToNewTimeline(Timeline timeline, Timeline timeline2, int i) {
            if (i >= timeline.getWindowCount()) {
                if (i >= timeline2.getWindowCount()) {
                    i = -1;
                }
                return i;
            }
            timeline.getWindow(i, DefaultPlaybackSessionManager.this.window);
            for (int i2 = DefaultPlaybackSessionManager.this.window.firstPeriodIndex; i2 <= DefaultPlaybackSessionManager.this.window.lastPeriodIndex; i2++) {
                int indexOfPeriod = timeline2.getIndexOfPeriod(timeline.getUidOfPeriod(i2));
                if (indexOfPeriod != -1) {
                    return timeline2.getPeriod(indexOfPeriod, DefaultPlaybackSessionManager.this.period).windowIndex;
                }
            }
            return -1;
        }
    }

    public void setListener(Listener listener2) {
        this.listener = listener2;
    }

    public synchronized String getSessionForMediaPeriodId(Timeline timeline, MediaPeriodId mediaPeriodId) {
        return getOrAddSession(timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period).windowIndex, mediaPeriodId).sessionId;
    }

    public synchronized boolean belongsToSession(EventTime eventTime, String str) {
        SessionDescriptor sessionDescriptor = (SessionDescriptor) this.sessions.get(str);
        if (sessionDescriptor == null) {
            return false;
        }
        sessionDescriptor.maybeSetWindowSequenceNumber(eventTime.windowIndex, eventTime.mediaPeriodId);
        return sessionDescriptor.belongsToSession(eventTime.windowIndex, eventTime.mediaPeriodId);
    }

    public synchronized void updateSessions(EventTime eventTime) {
        if (!((eventTime.mediaPeriodId == null || this.currentMediaPeriodId == null || eventTime.mediaPeriodId.windowSequenceNumber >= this.currentMediaPeriodId.windowSequenceNumber) ? false : true)) {
            SessionDescriptor orAddSession = getOrAddSession(eventTime.windowIndex, eventTime.mediaPeriodId);
            if (!orAddSession.isCreated) {
                orAddSession.isCreated = true;
                ((Listener) Assertions.checkNotNull(this.listener)).onSessionCreated(eventTime, orAddSession.sessionId);
                if (this.activeSessionId == null) {
                    updateActiveSession(eventTime, orAddSession);
                }
            }
        }
    }

    public synchronized void handleTimelineUpdate(EventTime eventTime) {
        Assertions.checkNotNull(this.listener);
        Timeline timeline = this.currentTimeline;
        this.currentTimeline = eventTime.timeline;
        Iterator it = this.sessions.values().iterator();
        while (it.hasNext()) {
            SessionDescriptor sessionDescriptor = (SessionDescriptor) it.next();
            if (!sessionDescriptor.tryResolvingToNewTimeline(timeline, this.currentTimeline)) {
                it.remove();
                if (sessionDescriptor.isCreated) {
                    if (sessionDescriptor.sessionId.equals(this.activeSessionId)) {
                        this.activeSessionId = null;
                    }
                    this.listener.onSessionFinished(eventTime, sessionDescriptor.sessionId, false);
                }
            }
        }
        handlePositionDiscontinuity(eventTime, 4);
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0021  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void handlePositionDiscontinuity(com.google.android.exoplayer2.analytics.AnalyticsListener.EventTime r7, int r8) {
        /*
            r6 = this;
            monitor-enter(r6)
            com.google.android.exoplayer2.analytics.PlaybackSessionManager$Listener r0 = r6.listener     // Catch:{ all -> 0x00bf }
            com.google.android.exoplayer2.util.Assertions.checkNotNull(r0)     // Catch:{ all -> 0x00bf }
            r0 = 0
            r1 = 1
            if (r8 == 0) goto L_0x0010
            r2 = 3
            if (r8 != r2) goto L_0x000e
            goto L_0x0010
        L_0x000e:
            r8 = 0
            goto L_0x0011
        L_0x0010:
            r8 = 1
        L_0x0011:
            java.util.HashMap<java.lang.String, com.google.android.exoplayer2.analytics.DefaultPlaybackSessionManager$SessionDescriptor> r2 = r6.sessions     // Catch:{ all -> 0x00bf }
            java.util.Collection r2 = r2.values()     // Catch:{ all -> 0x00bf }
            java.util.Iterator r2 = r2.iterator()     // Catch:{ all -> 0x00bf }
        L_0x001b:
            boolean r3 = r2.hasNext()     // Catch:{ all -> 0x00bf }
            if (r3 == 0) goto L_0x0056
            java.lang.Object r3 = r2.next()     // Catch:{ all -> 0x00bf }
            com.google.android.exoplayer2.analytics.DefaultPlaybackSessionManager$SessionDescriptor r3 = (com.google.android.exoplayer2.analytics.DefaultPlaybackSessionManager.SessionDescriptor) r3     // Catch:{ all -> 0x00bf }
            boolean r4 = r3.isFinishedAtEventTime(r7)     // Catch:{ all -> 0x00bf }
            if (r4 == 0) goto L_0x001b
            r2.remove()     // Catch:{ all -> 0x00bf }
            boolean r4 = r3.isCreated     // Catch:{ all -> 0x00bf }
            if (r4 == 0) goto L_0x001b
            java.lang.String r4 = r3.sessionId     // Catch:{ all -> 0x00bf }
            java.lang.String r5 = r6.activeSessionId     // Catch:{ all -> 0x00bf }
            boolean r4 = r4.equals(r5)     // Catch:{ all -> 0x00bf }
            if (r8 == 0) goto L_0x0046
            if (r4 == 0) goto L_0x0046
            r5 = 1
            goto L_0x0047
        L_0x0046:
            r5 = 0
        L_0x0047:
            if (r4 == 0) goto L_0x004c
            r4 = 0
            r6.activeSessionId = r4     // Catch:{ all -> 0x00bf }
        L_0x004c:
            com.google.android.exoplayer2.analytics.PlaybackSessionManager$Listener r4 = r6.listener     // Catch:{ all -> 0x00bf }
            java.lang.String r3 = r3.sessionId     // Catch:{ all -> 0x00bf }
            r4.onSessionFinished(r7, r3, r5)     // Catch:{ all -> 0x00bf }
            goto L_0x001b
        L_0x0056:
            int r8 = r7.windowIndex     // Catch:{ all -> 0x00bf }
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r0 = r7.mediaPeriodId     // Catch:{ all -> 0x00bf }
            com.google.android.exoplayer2.analytics.DefaultPlaybackSessionManager$SessionDescriptor r8 = r6.getOrAddSession(r8, r0)     // Catch:{ all -> 0x00bf }
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r0 = r7.mediaPeriodId     // Catch:{ all -> 0x00bf }
            if (r0 == 0) goto L_0x00ba
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r0 = r7.mediaPeriodId     // Catch:{ all -> 0x00bf }
            boolean r0 = r0.isAd()     // Catch:{ all -> 0x00bf }
            if (r0 == 0) goto L_0x00ba
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r0 = r6.currentMediaPeriodId     // Catch:{ all -> 0x00bf }
            if (r0 == 0) goto L_0x008e
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r0 = r6.currentMediaPeriodId     // Catch:{ all -> 0x00bf }
            long r0 = r0.windowSequenceNumber     // Catch:{ all -> 0x00bf }
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r2 = r7.mediaPeriodId     // Catch:{ all -> 0x00bf }
            long r2 = r2.windowSequenceNumber     // Catch:{ all -> 0x00bf }
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 != 0) goto L_0x008e
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r0 = r6.currentMediaPeriodId     // Catch:{ all -> 0x00bf }
            int r0 = r0.adGroupIndex     // Catch:{ all -> 0x00bf }
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r1 = r7.mediaPeriodId     // Catch:{ all -> 0x00bf }
            int r1 = r1.adGroupIndex     // Catch:{ all -> 0x00bf }
            if (r0 != r1) goto L_0x008e
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r0 = r6.currentMediaPeriodId     // Catch:{ all -> 0x00bf }
            int r0 = r0.adIndexInAdGroup     // Catch:{ all -> 0x00bf }
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r1 = r7.mediaPeriodId     // Catch:{ all -> 0x00bf }
            int r1 = r1.adIndexInAdGroup     // Catch:{ all -> 0x00bf }
            if (r0 == r1) goto L_0x00ba
        L_0x008e:
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r0 = new com.google.android.exoplayer2.source.MediaSource$MediaPeriodId     // Catch:{ all -> 0x00bf }
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r1 = r7.mediaPeriodId     // Catch:{ all -> 0x00bf }
            java.lang.Object r1 = r1.periodUid     // Catch:{ all -> 0x00bf }
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r2 = r7.mediaPeriodId     // Catch:{ all -> 0x00bf }
            long r2 = r2.windowSequenceNumber     // Catch:{ all -> 0x00bf }
            r0.<init>(r1, r2)     // Catch:{ all -> 0x00bf }
            int r1 = r7.windowIndex     // Catch:{ all -> 0x00bf }
            com.google.android.exoplayer2.analytics.DefaultPlaybackSessionManager$SessionDescriptor r0 = r6.getOrAddSession(r1, r0)     // Catch:{ all -> 0x00bf }
            boolean r1 = r0.isCreated     // Catch:{ all -> 0x00bf }
            if (r1 == 0) goto L_0x00ba
            boolean r1 = r8.isCreated     // Catch:{ all -> 0x00bf }
            if (r1 == 0) goto L_0x00ba
            com.google.android.exoplayer2.analytics.PlaybackSessionManager$Listener r1 = r6.listener     // Catch:{ all -> 0x00bf }
            java.lang.String r0 = r0.sessionId     // Catch:{ all -> 0x00bf }
            java.lang.String r2 = r8.sessionId     // Catch:{ all -> 0x00bf }
            r1.onAdPlaybackStarted(r7, r0, r2)     // Catch:{ all -> 0x00bf }
        L_0x00ba:
            r6.updateActiveSession(r7, r8)     // Catch:{ all -> 0x00bf }
            monitor-exit(r6)
            return
        L_0x00bf:
            r7 = move-exception
            monitor-exit(r6)
            goto L_0x00c3
        L_0x00c2:
            throw r7
        L_0x00c3:
            goto L_0x00c2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.analytics.DefaultPlaybackSessionManager.handlePositionDiscontinuity(com.google.android.exoplayer2.analytics.AnalyticsListener$EventTime, int):void");
    }

    private SessionDescriptor getOrAddSession(int i, MediaPeriodId mediaPeriodId) {
        SessionDescriptor sessionDescriptor = null;
        long j = Long.MAX_VALUE;
        for (SessionDescriptor sessionDescriptor2 : this.sessions.values()) {
            sessionDescriptor2.maybeSetWindowSequenceNumber(i, mediaPeriodId);
            if (sessionDescriptor2.belongsToSession(i, mediaPeriodId)) {
                long access$200 = sessionDescriptor2.windowSequenceNumber;
                if (access$200 == -1 || access$200 < j) {
                    sessionDescriptor = sessionDescriptor2;
                    j = access$200;
                } else if (!(access$200 != j || ((SessionDescriptor) Util.castNonNull(sessionDescriptor)).adMediaPeriodId == null || sessionDescriptor2.adMediaPeriodId == null)) {
                    sessionDescriptor = sessionDescriptor2;
                }
            }
        }
        if (sessionDescriptor != null) {
            return sessionDescriptor;
        }
        String generateSessionId = generateSessionId();
        SessionDescriptor sessionDescriptor3 = new SessionDescriptor(generateSessionId, i, mediaPeriodId);
        this.sessions.put(generateSessionId, sessionDescriptor3);
        return sessionDescriptor3;
    }

    @RequiresNonNull({"listener"})
    private void updateActiveSession(EventTime eventTime, SessionDescriptor sessionDescriptor) {
        this.currentMediaPeriodId = eventTime.mediaPeriodId;
        if (sessionDescriptor.isCreated) {
            this.activeSessionId = sessionDescriptor.sessionId;
            if (!sessionDescriptor.isActive) {
                sessionDescriptor.isActive = true;
                this.listener.onSessionActive(eventTime, sessionDescriptor.sessionId);
            }
        }
    }

    private static String generateSessionId() {
        byte[] bArr = new byte[12];
        RANDOM.nextBytes(bArr);
        return Base64.encodeToString(bArr, 10);
    }
}

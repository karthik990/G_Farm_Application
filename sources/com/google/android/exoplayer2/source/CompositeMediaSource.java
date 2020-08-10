package com.google.android.exoplayer2.source;

import android.os.Handler;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource.MediaPeriodId;
import com.google.android.exoplayer2.source.MediaSource.MediaSourceCaller;
import com.google.android.exoplayer2.source.MediaSourceEventListener.EventDispatcher;
import com.google.android.exoplayer2.source.MediaSourceEventListener.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaSourceEventListener.MediaLoadData;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.HashMap;

public abstract class CompositeMediaSource<T> extends BaseMediaSource {
    private final HashMap<T, MediaSourceAndListener> childSources = new HashMap<>();
    private Handler eventHandler;
    private TransferListener mediaTransferListener;

    private final class ForwardingEventListener implements MediaSourceEventListener {
        private EventDispatcher eventDispatcher;

        /* renamed from: id */
        private final T f1492id;

        public ForwardingEventListener(T t) {
            this.eventDispatcher = CompositeMediaSource.this.createEventDispatcher(null);
            this.f1492id = t;
        }

        public void onMediaPeriodCreated(int i, MediaPeriodId mediaPeriodId) {
            if (maybeUpdateEventDispatcher(i, mediaPeriodId) && CompositeMediaSource.this.shouldDispatchCreateOrReleaseEvent((MediaPeriodId) Assertions.checkNotNull(this.eventDispatcher.mediaPeriodId))) {
                this.eventDispatcher.mediaPeriodCreated();
            }
        }

        public void onMediaPeriodReleased(int i, MediaPeriodId mediaPeriodId) {
            if (maybeUpdateEventDispatcher(i, mediaPeriodId) && CompositeMediaSource.this.shouldDispatchCreateOrReleaseEvent((MediaPeriodId) Assertions.checkNotNull(this.eventDispatcher.mediaPeriodId))) {
                this.eventDispatcher.mediaPeriodReleased();
            }
        }

        public void onLoadStarted(int i, MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            if (maybeUpdateEventDispatcher(i, mediaPeriodId)) {
                this.eventDispatcher.loadStarted(loadEventInfo, maybeUpdateMediaLoadData(mediaLoadData));
            }
        }

        public void onLoadCompleted(int i, MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            if (maybeUpdateEventDispatcher(i, mediaPeriodId)) {
                this.eventDispatcher.loadCompleted(loadEventInfo, maybeUpdateMediaLoadData(mediaLoadData));
            }
        }

        public void onLoadCanceled(int i, MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            if (maybeUpdateEventDispatcher(i, mediaPeriodId)) {
                this.eventDispatcher.loadCanceled(loadEventInfo, maybeUpdateMediaLoadData(mediaLoadData));
            }
        }

        public void onLoadError(int i, MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z) {
            if (maybeUpdateEventDispatcher(i, mediaPeriodId)) {
                this.eventDispatcher.loadError(loadEventInfo, maybeUpdateMediaLoadData(mediaLoadData), iOException, z);
            }
        }

        public void onReadingStarted(int i, MediaPeriodId mediaPeriodId) {
            if (maybeUpdateEventDispatcher(i, mediaPeriodId)) {
                this.eventDispatcher.readingStarted();
            }
        }

        public void onUpstreamDiscarded(int i, MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
            if (maybeUpdateEventDispatcher(i, mediaPeriodId)) {
                this.eventDispatcher.upstreamDiscarded(maybeUpdateMediaLoadData(mediaLoadData));
            }
        }

        public void onDownstreamFormatChanged(int i, MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
            if (maybeUpdateEventDispatcher(i, mediaPeriodId)) {
                this.eventDispatcher.downstreamFormatChanged(maybeUpdateMediaLoadData(mediaLoadData));
            }
        }

        private boolean maybeUpdateEventDispatcher(int i, MediaPeriodId mediaPeriodId) {
            MediaPeriodId mediaPeriodId2;
            if (mediaPeriodId != null) {
                mediaPeriodId2 = CompositeMediaSource.this.getMediaPeriodIdForChildMediaPeriodId(this.f1492id, mediaPeriodId);
                if (mediaPeriodId2 == null) {
                    return false;
                }
            } else {
                mediaPeriodId2 = null;
            }
            int windowIndexForChildWindowIndex = CompositeMediaSource.this.getWindowIndexForChildWindowIndex(this.f1492id, i);
            if (this.eventDispatcher.windowIndex != windowIndexForChildWindowIndex || !Util.areEqual(this.eventDispatcher.mediaPeriodId, mediaPeriodId2)) {
                this.eventDispatcher = CompositeMediaSource.this.createEventDispatcher(windowIndexForChildWindowIndex, mediaPeriodId2, 0);
            }
            return true;
        }

        private MediaLoadData maybeUpdateMediaLoadData(MediaLoadData mediaLoadData) {
            long mediaTimeForChildMediaTime = CompositeMediaSource.this.getMediaTimeForChildMediaTime(this.f1492id, mediaLoadData.mediaStartTimeMs);
            long mediaTimeForChildMediaTime2 = CompositeMediaSource.this.getMediaTimeForChildMediaTime(this.f1492id, mediaLoadData.mediaEndTimeMs);
            if (mediaTimeForChildMediaTime == mediaLoadData.mediaStartTimeMs && mediaTimeForChildMediaTime2 == mediaLoadData.mediaEndTimeMs) {
                return mediaLoadData;
            }
            MediaLoadData mediaLoadData2 = new MediaLoadData(mediaLoadData.dataType, mediaLoadData.trackType, mediaLoadData.trackFormat, mediaLoadData.trackSelectionReason, mediaLoadData.trackSelectionData, mediaTimeForChildMediaTime, mediaTimeForChildMediaTime2);
            return mediaLoadData2;
        }
    }

    private static final class MediaSourceAndListener {
        public final MediaSourceCaller caller;
        public final MediaSourceEventListener eventListener;
        public final MediaSource mediaSource;

        public MediaSourceAndListener(MediaSource mediaSource2, MediaSourceCaller mediaSourceCaller, MediaSourceEventListener mediaSourceEventListener) {
            this.mediaSource = mediaSource2;
            this.caller = mediaSourceCaller;
            this.eventListener = mediaSourceEventListener;
        }
    }

    /* access modifiers changed from: protected */
    public MediaPeriodId getMediaPeriodIdForChildMediaPeriodId(T t, MediaPeriodId mediaPeriodId) {
        return mediaPeriodId;
    }

    /* access modifiers changed from: protected */
    public long getMediaTimeForChildMediaTime(T t, long j) {
        return j;
    }

    /* access modifiers changed from: protected */
    public int getWindowIndexForChildWindowIndex(T t, int i) {
        return i;
    }

    /* access modifiers changed from: protected */
    /* renamed from: onChildSourceInfoRefreshed */
    public abstract void lambda$prepareChildSource$0$CompositeMediaSource(T t, MediaSource mediaSource, Timeline timeline);

    /* access modifiers changed from: protected */
    public boolean shouldDispatchCreateOrReleaseEvent(MediaPeriodId mediaPeriodId) {
        return true;
    }

    protected CompositeMediaSource() {
    }

    /* access modifiers changed from: protected */
    public void prepareSourceInternal(TransferListener transferListener) {
        this.mediaTransferListener = transferListener;
        this.eventHandler = new Handler();
    }

    public void maybeThrowSourceInfoRefreshError() throws IOException {
        for (MediaSourceAndListener mediaSourceAndListener : this.childSources.values()) {
            mediaSourceAndListener.mediaSource.maybeThrowSourceInfoRefreshError();
        }
    }

    /* access modifiers changed from: protected */
    public void enableInternal() {
        for (MediaSourceAndListener mediaSourceAndListener : this.childSources.values()) {
            mediaSourceAndListener.mediaSource.enable(mediaSourceAndListener.caller);
        }
    }

    /* access modifiers changed from: protected */
    public void disableInternal() {
        for (MediaSourceAndListener mediaSourceAndListener : this.childSources.values()) {
            mediaSourceAndListener.mediaSource.disable(mediaSourceAndListener.caller);
        }
    }

    /* access modifiers changed from: protected */
    public void releaseSourceInternal() {
        for (MediaSourceAndListener mediaSourceAndListener : this.childSources.values()) {
            mediaSourceAndListener.mediaSource.releaseSource(mediaSourceAndListener.caller);
            mediaSourceAndListener.mediaSource.removeEventListener(mediaSourceAndListener.eventListener);
        }
        this.childSources.clear();
    }

    /* access modifiers changed from: protected */
    public final void prepareChildSource(T t, MediaSource mediaSource) {
        Assertions.checkArgument(!this.childSources.containsKey(t));
        $$Lambda$CompositeMediaSource$XIMxMigUUqAS2WvqSyL5xh9sCFo r0 = new MediaSourceCaller(t) {
            private final /* synthetic */ Object f$1;

            {
                this.f$1 = r2;
            }

            public final void onSourceInfoRefreshed(MediaSource mediaSource, Timeline timeline) {
                CompositeMediaSource.this.lambda$prepareChildSource$0$CompositeMediaSource(this.f$1, mediaSource, timeline);
            }
        };
        ForwardingEventListener forwardingEventListener = new ForwardingEventListener(t);
        this.childSources.put(t, new MediaSourceAndListener(mediaSource, r0, forwardingEventListener));
        mediaSource.addEventListener((Handler) Assertions.checkNotNull(this.eventHandler), forwardingEventListener);
        mediaSource.prepareSource(r0, this.mediaTransferListener);
        if (!isEnabled()) {
            mediaSource.disable(r0);
        }
    }

    /* access modifiers changed from: protected */
    public final void enableChildSource(T t) {
        MediaSourceAndListener mediaSourceAndListener = (MediaSourceAndListener) Assertions.checkNotNull(this.childSources.get(t));
        mediaSourceAndListener.mediaSource.enable(mediaSourceAndListener.caller);
    }

    /* access modifiers changed from: protected */
    public final void disableChildSource(T t) {
        MediaSourceAndListener mediaSourceAndListener = (MediaSourceAndListener) Assertions.checkNotNull(this.childSources.get(t));
        mediaSourceAndListener.mediaSource.disable(mediaSourceAndListener.caller);
    }

    /* access modifiers changed from: protected */
    public final void releaseChildSource(T t) {
        MediaSourceAndListener mediaSourceAndListener = (MediaSourceAndListener) Assertions.checkNotNull(this.childSources.remove(t));
        mediaSourceAndListener.mediaSource.releaseSource(mediaSourceAndListener.caller);
        mediaSourceAndListener.mediaSource.removeEventListener(mediaSourceAndListener.eventListener);
    }
}

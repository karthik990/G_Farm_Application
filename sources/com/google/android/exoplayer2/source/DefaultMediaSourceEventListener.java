package com.google.android.exoplayer2.source;

import com.google.android.exoplayer2.source.MediaSource.MediaPeriodId;
import com.google.android.exoplayer2.source.MediaSourceEventListener.CC;
import com.google.android.exoplayer2.source.MediaSourceEventListener.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaSourceEventListener.MediaLoadData;
import java.io.IOException;

@Deprecated
public abstract class DefaultMediaSourceEventListener implements MediaSourceEventListener {
    public /* synthetic */ void onDownstreamFormatChanged(int i, MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
        CC.$default$onDownstreamFormatChanged(this, i, mediaPeriodId, mediaLoadData);
    }

    public /* synthetic */ void onLoadCanceled(int i, MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        CC.$default$onLoadCanceled(this, i, mediaPeriodId, loadEventInfo, mediaLoadData);
    }

    public /* synthetic */ void onLoadCompleted(int i, MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        CC.$default$onLoadCompleted(this, i, mediaPeriodId, loadEventInfo, mediaLoadData);
    }

    public /* synthetic */ void onLoadError(int i, MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z) {
        CC.$default$onLoadError(this, i, mediaPeriodId, loadEventInfo, mediaLoadData, iOException, z);
    }

    public /* synthetic */ void onLoadStarted(int i, MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        CC.$default$onLoadStarted(this, i, mediaPeriodId, loadEventInfo, mediaLoadData);
    }

    public /* synthetic */ void onMediaPeriodCreated(int i, MediaPeriodId mediaPeriodId) {
        CC.$default$onMediaPeriodCreated(this, i, mediaPeriodId);
    }

    public /* synthetic */ void onMediaPeriodReleased(int i, MediaPeriodId mediaPeriodId) {
        CC.$default$onMediaPeriodReleased(this, i, mediaPeriodId);
    }

    public /* synthetic */ void onReadingStarted(int i, MediaPeriodId mediaPeriodId) {
        CC.$default$onReadingStarted(this, i, mediaPeriodId);
    }

    public /* synthetic */ void onUpstreamDiscarded(int i, MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
        CC.$default$onUpstreamDiscarded(this, i, mediaPeriodId, mediaLoadData);
    }
}

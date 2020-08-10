package com.google.android.exoplayer2.source;

import android.os.Handler;
import android.os.Looper;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource.CC;
import com.google.android.exoplayer2.source.MediaSource.MediaPeriodId;
import com.google.android.exoplayer2.source.MediaSource.MediaSourceCaller;
import com.google.android.exoplayer2.source.MediaSourceEventListener.EventDispatcher;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public abstract class BaseMediaSource implements MediaSource {
    private final HashSet<MediaSourceCaller> enabledMediaSourceCallers = new HashSet<>(1);
    private final EventDispatcher eventDispatcher = new EventDispatcher();
    private Looper looper;
    private final ArrayList<MediaSourceCaller> mediaSourceCallers = new ArrayList<>(1);
    private Timeline timeline;

    /* access modifiers changed from: protected */
    public void disableInternal() {
    }

    /* access modifiers changed from: protected */
    public void enableInternal() {
    }

    public /* synthetic */ Object getTag() {
        return CC.$default$getTag(this);
    }

    /* access modifiers changed from: protected */
    public abstract void prepareSourceInternal(TransferListener transferListener);

    /* access modifiers changed from: protected */
    public abstract void releaseSourceInternal();

    /* access modifiers changed from: protected */
    public final void refreshSourceInfo(Timeline timeline2) {
        this.timeline = timeline2;
        Iterator it = this.mediaSourceCallers.iterator();
        while (it.hasNext()) {
            ((MediaSourceCaller) it.next()).onSourceInfoRefreshed(this, timeline2);
        }
    }

    /* access modifiers changed from: protected */
    public final EventDispatcher createEventDispatcher(MediaPeriodId mediaPeriodId) {
        return this.eventDispatcher.withParameters(0, mediaPeriodId, 0);
    }

    /* access modifiers changed from: protected */
    public final EventDispatcher createEventDispatcher(MediaPeriodId mediaPeriodId, long j) {
        Assertions.checkArgument(mediaPeriodId != null);
        return this.eventDispatcher.withParameters(0, mediaPeriodId, j);
    }

    /* access modifiers changed from: protected */
    public final EventDispatcher createEventDispatcher(int i, MediaPeriodId mediaPeriodId, long j) {
        return this.eventDispatcher.withParameters(i, mediaPeriodId, j);
    }

    /* access modifiers changed from: protected */
    public final boolean isEnabled() {
        return !this.enabledMediaSourceCallers.isEmpty();
    }

    public final void addEventListener(Handler handler, MediaSourceEventListener mediaSourceEventListener) {
        this.eventDispatcher.addEventListener(handler, mediaSourceEventListener);
    }

    public final void removeEventListener(MediaSourceEventListener mediaSourceEventListener) {
        this.eventDispatcher.removeEventListener(mediaSourceEventListener);
    }

    public final void prepareSource(MediaSourceCaller mediaSourceCaller, TransferListener transferListener) {
        Looper myLooper = Looper.myLooper();
        Looper looper2 = this.looper;
        Assertions.checkArgument(looper2 == null || looper2 == myLooper);
        Timeline timeline2 = this.timeline;
        this.mediaSourceCallers.add(mediaSourceCaller);
        if (this.looper == null) {
            this.looper = myLooper;
            this.enabledMediaSourceCallers.add(mediaSourceCaller);
            prepareSourceInternal(transferListener);
        } else if (timeline2 != null) {
            enable(mediaSourceCaller);
            mediaSourceCaller.onSourceInfoRefreshed(this, timeline2);
        }
    }

    public final void enable(MediaSourceCaller mediaSourceCaller) {
        Assertions.checkNotNull(this.looper);
        boolean isEmpty = this.enabledMediaSourceCallers.isEmpty();
        this.enabledMediaSourceCallers.add(mediaSourceCaller);
        if (isEmpty) {
            enableInternal();
        }
    }

    public final void disable(MediaSourceCaller mediaSourceCaller) {
        boolean z = !this.enabledMediaSourceCallers.isEmpty();
        this.enabledMediaSourceCallers.remove(mediaSourceCaller);
        if (z && this.enabledMediaSourceCallers.isEmpty()) {
            disableInternal();
        }
    }

    public final void releaseSource(MediaSourceCaller mediaSourceCaller) {
        this.mediaSourceCallers.remove(mediaSourceCaller);
        if (this.mediaSourceCallers.isEmpty()) {
            this.looper = null;
            this.timeline = null;
            this.enabledMediaSourceCallers.clear();
            releaseSourceInternal();
            return;
        }
        disable(mediaSourceCaller);
    }
}

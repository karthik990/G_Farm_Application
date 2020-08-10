package com.google.android.exoplayer2.source;

import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.MediaPeriod.CC;
import com.google.android.exoplayer2.source.MediaPeriod.Callback;
import com.google.android.exoplayer2.source.MediaSource.MediaPeriodId;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.List;

public final class MaskingMediaPeriod implements MediaPeriod, Callback {
    private final Allocator allocator;
    private Callback callback;

    /* renamed from: id */
    public final MediaPeriodId f1493id;
    private PrepareErrorListener listener;
    private MediaPeriod mediaPeriod;
    public final MediaSource mediaSource;
    private boolean notifiedPrepareError;
    private long preparePositionOverrideUs = C1996C.TIME_UNSET;
    private long preparePositionUs;

    public interface PrepareErrorListener {
        void onPrepareError(MediaPeriodId mediaPeriodId, IOException iOException);
    }

    public /* synthetic */ List<StreamKey> getStreamKeys(List<TrackSelection> list) {
        return CC.$default$getStreamKeys(this, list);
    }

    public MaskingMediaPeriod(MediaSource mediaSource2, MediaPeriodId mediaPeriodId, Allocator allocator2, long j) {
        this.f1493id = mediaPeriodId;
        this.allocator = allocator2;
        this.mediaSource = mediaSource2;
        this.preparePositionUs = j;
    }

    public void setPrepareErrorListener(PrepareErrorListener prepareErrorListener) {
        this.listener = prepareErrorListener;
    }

    public long getPreparePositionUs() {
        return this.preparePositionUs;
    }

    public void overridePreparePositionUs(long j) {
        this.preparePositionOverrideUs = j;
    }

    public void createPeriod(MediaPeriodId mediaPeriodId) {
        long preparePositionWithOverride = getPreparePositionWithOverride(this.preparePositionUs);
        this.mediaPeriod = this.mediaSource.createPeriod(mediaPeriodId, this.allocator, preparePositionWithOverride);
        if (this.callback != null) {
            this.mediaPeriod.prepare(this, preparePositionWithOverride);
        }
    }

    public void releasePeriod() {
        MediaPeriod mediaPeriod2 = this.mediaPeriod;
        if (mediaPeriod2 != null) {
            this.mediaSource.releasePeriod(mediaPeriod2);
        }
    }

    public void prepare(Callback callback2, long j) {
        this.callback = callback2;
        MediaPeriod mediaPeriod2 = this.mediaPeriod;
        if (mediaPeriod2 != null) {
            mediaPeriod2.prepare(this, getPreparePositionWithOverride(this.preparePositionUs));
        }
    }

    public void maybeThrowPrepareError() throws IOException {
        try {
            if (this.mediaPeriod != null) {
                this.mediaPeriod.maybeThrowPrepareError();
            } else {
                this.mediaSource.maybeThrowSourceInfoRefreshError();
            }
        } catch (IOException e) {
            PrepareErrorListener prepareErrorListener = this.listener;
            if (prepareErrorListener == null) {
                throw e;
            } else if (!this.notifiedPrepareError) {
                this.notifiedPrepareError = true;
                prepareErrorListener.onPrepareError(this.f1493id, e);
            }
        }
    }

    public TrackGroupArray getTrackGroups() {
        return ((MediaPeriod) Util.castNonNull(this.mediaPeriod)).getTrackGroups();
    }

    public long selectTracks(TrackSelection[] trackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
        long j2;
        long j3 = this.preparePositionOverrideUs;
        if (j3 == C1996C.TIME_UNSET || j != this.preparePositionUs) {
            j2 = j;
        } else {
            this.preparePositionOverrideUs = C1996C.TIME_UNSET;
            j2 = j3;
        }
        return ((MediaPeriod) Util.castNonNull(this.mediaPeriod)).selectTracks(trackSelectionArr, zArr, sampleStreamArr, zArr2, j2);
    }

    public void discardBuffer(long j, boolean z) {
        ((MediaPeriod) Util.castNonNull(this.mediaPeriod)).discardBuffer(j, z);
    }

    public long readDiscontinuity() {
        return ((MediaPeriod) Util.castNonNull(this.mediaPeriod)).readDiscontinuity();
    }

    public long getBufferedPositionUs() {
        return ((MediaPeriod) Util.castNonNull(this.mediaPeriod)).getBufferedPositionUs();
    }

    public long seekToUs(long j) {
        return ((MediaPeriod) Util.castNonNull(this.mediaPeriod)).seekToUs(j);
    }

    public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
        return ((MediaPeriod) Util.castNonNull(this.mediaPeriod)).getAdjustedSeekPositionUs(j, seekParameters);
    }

    public long getNextLoadPositionUs() {
        return ((MediaPeriod) Util.castNonNull(this.mediaPeriod)).getNextLoadPositionUs();
    }

    public void reevaluateBuffer(long j) {
        ((MediaPeriod) Util.castNonNull(this.mediaPeriod)).reevaluateBuffer(j);
    }

    public boolean continueLoading(long j) {
        MediaPeriod mediaPeriod2 = this.mediaPeriod;
        return mediaPeriod2 != null && mediaPeriod2.continueLoading(j);
    }

    public boolean isLoading() {
        MediaPeriod mediaPeriod2 = this.mediaPeriod;
        return mediaPeriod2 != null && mediaPeriod2.isLoading();
    }

    public void onContinueLoadingRequested(MediaPeriod mediaPeriod2) {
        ((Callback) Util.castNonNull(this.callback)).onContinueLoadingRequested(this);
    }

    public void onPrepared(MediaPeriod mediaPeriod2) {
        ((Callback) Util.castNonNull(this.callback)).onPrepared(this);
    }

    private long getPreparePositionWithOverride(long j) {
        long j2 = this.preparePositionOverrideUs;
        return j2 != C1996C.TIME_UNSET ? j2 : j;
    }
}

package com.google.android.exoplayer2.source.smoothstreaming;

import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSourceEventListener.EventDispatcher;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.SequenceableLoader;
import com.google.android.exoplayer2.source.SequenceableLoader.Callback;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.chunk.ChunkSampleStream;
import com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource.Factory;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifest;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.TransferListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

final class SsMediaPeriod implements MediaPeriod, Callback<ChunkSampleStream<SsChunkSource>> {
    private final Allocator allocator;
    private MediaPeriod.Callback callback;
    private final Factory chunkSourceFactory;
    private SequenceableLoader compositeSequenceableLoader;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private final DrmSessionManager<?> drmSessionManager;
    private final EventDispatcher eventDispatcher;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private SsManifest manifest;
    private final LoaderErrorThrower manifestLoaderErrorThrower;
    private boolean notifiedReadingStarted;
    private ChunkSampleStream<SsChunkSource>[] sampleStreams = newSampleStreamArray(0);
    private final TrackGroupArray trackGroups;
    private final TransferListener transferListener;

    public SsMediaPeriod(SsManifest ssManifest, Factory factory, TransferListener transferListener2, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory2, DrmSessionManager<?> drmSessionManager2, LoadErrorHandlingPolicy loadErrorHandlingPolicy2, EventDispatcher eventDispatcher2, LoaderErrorThrower loaderErrorThrower, Allocator allocator2) {
        this.manifest = ssManifest;
        this.chunkSourceFactory = factory;
        this.transferListener = transferListener2;
        this.manifestLoaderErrorThrower = loaderErrorThrower;
        this.drmSessionManager = drmSessionManager2;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy2;
        this.eventDispatcher = eventDispatcher2;
        this.allocator = allocator2;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory2;
        this.trackGroups = buildTrackGroups(ssManifest, drmSessionManager2);
        this.compositeSequenceableLoader = compositeSequenceableLoaderFactory2.createCompositeSequenceableLoader(this.sampleStreams);
        eventDispatcher2.mediaPeriodCreated();
    }

    public void updateManifest(SsManifest ssManifest) {
        this.manifest = ssManifest;
        for (ChunkSampleStream<SsChunkSource> chunkSource : this.sampleStreams) {
            ((SsChunkSource) chunkSource.getChunkSource()).updateManifest(ssManifest);
        }
        this.callback.onContinueLoadingRequested(this);
    }

    public void release() {
        for (ChunkSampleStream<SsChunkSource> release : this.sampleStreams) {
            release.release();
        }
        this.callback = null;
        this.eventDispatcher.mediaPeriodReleased();
    }

    public void prepare(MediaPeriod.Callback callback2, long j) {
        this.callback = callback2;
        callback2.onPrepared(this);
    }

    public void maybeThrowPrepareError() throws IOException {
        this.manifestLoaderErrorThrower.maybeThrowError();
    }

    public TrackGroupArray getTrackGroups() {
        return this.trackGroups;
    }

    public long selectTracks(TrackSelection[] trackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < trackSelectionArr.length; i++) {
            if (sampleStreamArr[i] != null) {
                ChunkSampleStream chunkSampleStream = sampleStreamArr[i];
                if (trackSelectionArr[i] == null || !zArr[i]) {
                    chunkSampleStream.release();
                    sampleStreamArr[i] = null;
                } else {
                    ((SsChunkSource) chunkSampleStream.getChunkSource()).updateTrackSelection(trackSelectionArr[i]);
                    arrayList.add(chunkSampleStream);
                }
            }
            if (sampleStreamArr[i] == null && trackSelectionArr[i] != null) {
                ChunkSampleStream buildSampleStream = buildSampleStream(trackSelectionArr[i], j);
                arrayList.add(buildSampleStream);
                sampleStreamArr[i] = buildSampleStream;
                zArr2[i] = true;
            }
        }
        this.sampleStreams = newSampleStreamArray(arrayList.size());
        arrayList.toArray(this.sampleStreams);
        this.compositeSequenceableLoader = this.compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(this.sampleStreams);
        return j;
    }

    public List<StreamKey> getStreamKeys(List<TrackSelection> list) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            TrackSelection trackSelection = (TrackSelection) list.get(i);
            int indexOf = this.trackGroups.indexOf(trackSelection.getTrackGroup());
            for (int i2 = 0; i2 < trackSelection.length(); i2++) {
                arrayList.add(new StreamKey(indexOf, trackSelection.getIndexInTrackGroup(i2)));
            }
        }
        return arrayList;
    }

    public void discardBuffer(long j, boolean z) {
        for (ChunkSampleStream<SsChunkSource> discardBuffer : this.sampleStreams) {
            discardBuffer.discardBuffer(j, z);
        }
    }

    public void reevaluateBuffer(long j) {
        this.compositeSequenceableLoader.reevaluateBuffer(j);
    }

    public boolean continueLoading(long j) {
        return this.compositeSequenceableLoader.continueLoading(j);
    }

    public boolean isLoading() {
        return this.compositeSequenceableLoader.isLoading();
    }

    public long getNextLoadPositionUs() {
        return this.compositeSequenceableLoader.getNextLoadPositionUs();
    }

    public long readDiscontinuity() {
        if (!this.notifiedReadingStarted) {
            this.eventDispatcher.readingStarted();
            this.notifiedReadingStarted = true;
        }
        return C1996C.TIME_UNSET;
    }

    public long getBufferedPositionUs() {
        return this.compositeSequenceableLoader.getBufferedPositionUs();
    }

    public long seekToUs(long j) {
        for (ChunkSampleStream<SsChunkSource> seekToUs : this.sampleStreams) {
            seekToUs.seekToUs(j);
        }
        return j;
    }

    public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
        ChunkSampleStream<SsChunkSource>[] chunkSampleStreamArr;
        for (ChunkSampleStream<SsChunkSource> chunkSampleStream : this.sampleStreams) {
            if (chunkSampleStream.primaryTrackType == 2) {
                return chunkSampleStream.getAdjustedSeekPositionUs(j, seekParameters);
            }
        }
        return j;
    }

    public void onContinueLoadingRequested(ChunkSampleStream<SsChunkSource> chunkSampleStream) {
        this.callback.onContinueLoadingRequested(this);
    }

    private ChunkSampleStream<SsChunkSource> buildSampleStream(TrackSelection trackSelection, long j) {
        int indexOf = this.trackGroups.indexOf(trackSelection.getTrackGroup());
        ChunkSampleStream chunkSampleStream = new ChunkSampleStream(this.manifest.streamElements[indexOf].type, null, null, this.chunkSourceFactory.createChunkSource(this.manifestLoaderErrorThrower, this.manifest, indexOf, trackSelection, this.transferListener), this, this.allocator, j, this.drmSessionManager, this.loadErrorHandlingPolicy, this.eventDispatcher);
        return chunkSampleStream;
    }

    private static TrackGroupArray buildTrackGroups(SsManifest ssManifest, DrmSessionManager<?> drmSessionManager2) {
        TrackGroup[] trackGroupArr = new TrackGroup[ssManifest.streamElements.length];
        for (int i = 0; i < ssManifest.streamElements.length; i++) {
            Format[] formatArr = ssManifest.streamElements[i].formats;
            Format[] formatArr2 = new Format[formatArr.length];
            for (int i2 = 0; i2 < formatArr.length; i2++) {
                Format format = formatArr[i2];
                if (format.drmInitData != null) {
                    format = format.copyWithExoMediaCryptoType(drmSessionManager2.getExoMediaCryptoType(format.drmInitData));
                }
                formatArr2[i2] = format;
            }
            trackGroupArr[i] = new TrackGroup(formatArr2);
        }
        return new TrackGroupArray(trackGroupArr);
    }

    private static ChunkSampleStream<SsChunkSource>[] newSampleStreamArray(int i) {
        return new ChunkSampleStream[i];
    }
}

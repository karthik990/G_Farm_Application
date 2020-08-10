package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSourceEventListener.EventDispatcher;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.SequenceableLoader;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper.Callback;
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist.Rendition;
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist.Variant;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker.PlaylistEventListener;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public final class HlsMediaPeriod implements MediaPeriod, Callback, PlaylistEventListener {
    private final Allocator allocator;
    private final boolean allowChunklessPreparation;
    private MediaPeriod.Callback callback;
    private SequenceableLoader compositeSequenceableLoader;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private final HlsDataSourceFactory dataSourceFactory;
    private final DrmSessionManager<?> drmSessionManager;
    private HlsSampleStreamWrapper[] enabledSampleStreamWrappers = new HlsSampleStreamWrapper[0];
    private final EventDispatcher eventDispatcher;
    private final HlsExtractorFactory extractorFactory;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private int[][] manifestUrlIndicesPerWrapper = new int[0][];
    private final TransferListener mediaTransferListener;
    private final int metadataType;
    private boolean notifiedReadingStarted;
    private int pendingPrepareCount;
    private final HlsPlaylistTracker playlistTracker;
    private HlsSampleStreamWrapper[] sampleStreamWrappers = new HlsSampleStreamWrapper[0];
    private final IdentityHashMap<SampleStream, Integer> streamWrapperIndices = new IdentityHashMap<>();
    private final TimestampAdjusterProvider timestampAdjusterProvider = new TimestampAdjusterProvider();
    private TrackGroupArray trackGroups;
    private final boolean useSessionKeys;

    public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
        return j;
    }

    public HlsMediaPeriod(HlsExtractorFactory hlsExtractorFactory, HlsPlaylistTracker hlsPlaylistTracker, HlsDataSourceFactory hlsDataSourceFactory, TransferListener transferListener, DrmSessionManager<?> drmSessionManager2, LoadErrorHandlingPolicy loadErrorHandlingPolicy2, EventDispatcher eventDispatcher2, Allocator allocator2, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory2, boolean z, int i, boolean z2) {
        this.extractorFactory = hlsExtractorFactory;
        this.playlistTracker = hlsPlaylistTracker;
        this.dataSourceFactory = hlsDataSourceFactory;
        this.mediaTransferListener = transferListener;
        this.drmSessionManager = drmSessionManager2;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy2;
        this.eventDispatcher = eventDispatcher2;
        this.allocator = allocator2;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory2;
        this.allowChunklessPreparation = z;
        this.metadataType = i;
        this.useSessionKeys = z2;
        this.compositeSequenceableLoader = compositeSequenceableLoaderFactory2.createCompositeSequenceableLoader(new SequenceableLoader[0]);
        eventDispatcher2.mediaPeriodCreated();
    }

    public void release() {
        this.playlistTracker.removeListener(this);
        for (HlsSampleStreamWrapper release : this.sampleStreamWrappers) {
            release.release();
        }
        this.callback = null;
        this.eventDispatcher.mediaPeriodReleased();
    }

    public void prepare(MediaPeriod.Callback callback2, long j) {
        this.callback = callback2;
        this.playlistTracker.addListener(this);
        buildAndPrepareSampleStreamWrappers(j);
    }

    public void maybeThrowPrepareError() throws IOException {
        for (HlsSampleStreamWrapper maybeThrowPrepareError : this.sampleStreamWrappers) {
            maybeThrowPrepareError.maybeThrowPrepareError();
        }
    }

    public TrackGroupArray getTrackGroups() {
        return (TrackGroupArray) Assertions.checkNotNull(this.trackGroups);
    }

    public List<StreamKey> getStreamKeys(List<TrackSelection> list) {
        TrackGroupArray trackGroupArray;
        int[] iArr;
        int i;
        HlsMediaPeriod hlsMediaPeriod = this;
        HlsMasterPlaylist hlsMasterPlaylist = (HlsMasterPlaylist) Assertions.checkNotNull(hlsMediaPeriod.playlistTracker.getMasterPlaylist());
        boolean z = !hlsMasterPlaylist.variants.isEmpty();
        int length = hlsMediaPeriod.sampleStreamWrappers.length - hlsMasterPlaylist.subtitles.size();
        int i2 = 0;
        if (z) {
            HlsSampleStreamWrapper hlsSampleStreamWrapper = hlsMediaPeriod.sampleStreamWrappers[0];
            iArr = hlsMediaPeriod.manifestUrlIndicesPerWrapper[0];
            trackGroupArray = hlsSampleStreamWrapper.getTrackGroups();
            i = hlsSampleStreamWrapper.getPrimaryTrackGroupIndex();
        } else {
            iArr = new int[0];
            trackGroupArray = TrackGroupArray.EMPTY;
            i = 0;
        }
        ArrayList arrayList = new ArrayList();
        boolean z2 = false;
        boolean z3 = false;
        for (TrackSelection trackSelection : list) {
            TrackGroup trackGroup = trackSelection.getTrackGroup();
            int indexOf = trackGroupArray.indexOf(trackGroup);
            if (indexOf == -1) {
                int i3 = z;
                while (true) {
                    HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr = hlsMediaPeriod.sampleStreamWrappers;
                    if (i3 >= hlsSampleStreamWrapperArr.length) {
                        break;
                    } else if (hlsSampleStreamWrapperArr[i3].getTrackGroups().indexOf(trackGroup) != -1) {
                        int i4 = i3 < length ? 1 : 2;
                        int[] iArr2 = hlsMediaPeriod.manifestUrlIndicesPerWrapper[i3];
                        int i5 = 0;
                        while (i5 < trackSelection.length()) {
                            arrayList.add(new StreamKey(i4, iArr2[trackSelection.getIndexInTrackGroup(i5)]));
                            i5++;
                        }
                    } else {
                        i3++;
                        hlsMediaPeriod = this;
                    }
                }
            } else if (indexOf == i) {
                for (int i6 = 0; i6 < trackSelection.length(); i6++) {
                    arrayList.add(new StreamKey(i2, iArr[trackSelection.getIndexInTrackGroup(i6)]));
                }
                z3 = true;
            } else {
                z2 = true;
            }
            i2 = 0;
            hlsMediaPeriod = this;
        }
        if (z2 && !z3) {
            int i7 = iArr[0];
            int i8 = ((Variant) hlsMasterPlaylist.variants.get(iArr[0])).format.bitrate;
            for (int i9 = 1; i9 < iArr.length; i9++) {
                int i10 = ((Variant) hlsMasterPlaylist.variants.get(iArr[i9])).format.bitrate;
                if (i10 < i8) {
                    i7 = iArr[i9];
                    i8 = i10;
                }
            }
            arrayList.add(new StreamKey(0, i7));
        }
        return arrayList;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00df, code lost:
        if (r5 != r8[0]) goto L_0x00e3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long selectTracks(com.google.android.exoplayer2.trackselection.TrackSelection[] r21, boolean[] r22, com.google.android.exoplayer2.source.SampleStream[] r23, boolean[] r24, long r25) {
        /*
            r20 = this;
            r0 = r20
            r1 = r21
            r2 = r23
            int r3 = r1.length
            int[] r3 = new int[r3]
            int r4 = r1.length
            int[] r4 = new int[r4]
            r6 = 0
        L_0x000d:
            int r7 = r1.length
            if (r6 >= r7) goto L_0x004e
            r7 = r2[r6]
            r8 = -1
            if (r7 != 0) goto L_0x0017
            r7 = -1
            goto L_0x0025
        L_0x0017:
            java.util.IdentityHashMap<com.google.android.exoplayer2.source.SampleStream, java.lang.Integer> r7 = r0.streamWrapperIndices
            r9 = r2[r6]
            java.lang.Object r7 = r7.get(r9)
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r7 = r7.intValue()
        L_0x0025:
            r3[r6] = r7
            r4[r6] = r8
            r7 = r1[r6]
            if (r7 == 0) goto L_0x004b
            r7 = r1[r6]
            com.google.android.exoplayer2.source.TrackGroup r7 = r7.getTrackGroup()
            r9 = 0
        L_0x0034:
            com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper[] r10 = r0.sampleStreamWrappers
            int r11 = r10.length
            if (r9 >= r11) goto L_0x004b
            r10 = r10[r9]
            com.google.android.exoplayer2.source.TrackGroupArray r10 = r10.getTrackGroups()
            int r10 = r10.indexOf(r7)
            if (r10 == r8) goto L_0x0048
            r4[r6] = r9
            goto L_0x004b
        L_0x0048:
            int r9 = r9 + 1
            goto L_0x0034
        L_0x004b:
            int r6 = r6 + 1
            goto L_0x000d
        L_0x004e:
            java.util.IdentityHashMap<com.google.android.exoplayer2.source.SampleStream, java.lang.Integer> r6 = r0.streamWrapperIndices
            r6.clear()
            int r6 = r1.length
            com.google.android.exoplayer2.source.SampleStream[] r6 = new com.google.android.exoplayer2.source.SampleStream[r6]
            int r7 = r1.length
            com.google.android.exoplayer2.source.SampleStream[] r7 = new com.google.android.exoplayer2.source.SampleStream[r7]
            int r8 = r1.length
            com.google.android.exoplayer2.trackselection.TrackSelection[] r15 = new com.google.android.exoplayer2.trackselection.TrackSelection[r8]
            com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper[] r8 = r0.sampleStreamWrappers
            int r8 = r8.length
            com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper[] r13 = new com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper[r8]
            r12 = 0
            r14 = 0
            r16 = 0
        L_0x0065:
            com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper[] r8 = r0.sampleStreamWrappers
            int r8 = r8.length
            if (r14 >= r8) goto L_0x00ff
            r8 = 0
        L_0x006b:
            int r9 = r1.length
            if (r8 >= r9) goto L_0x0084
            r9 = r3[r8]
            r10 = 0
            if (r9 != r14) goto L_0x0076
            r9 = r2[r8]
            goto L_0x0077
        L_0x0076:
            r9 = r10
        L_0x0077:
            r7[r8] = r9
            r9 = r4[r8]
            if (r9 != r14) goto L_0x007f
            r10 = r1[r8]
        L_0x007f:
            r15[r8] = r10
            int r8 = r8 + 1
            goto L_0x006b
        L_0x0084:
            com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper[] r8 = r0.sampleStreamWrappers
            r11 = r8[r14]
            r8 = r11
            r9 = r15
            r10 = r22
            r5 = r11
            r11 = r7
            r2 = r12
            r12 = r24
            r17 = r2
            r18 = r13
            r2 = r14
            r13 = r25
            r19 = r15
            r15 = r16
            boolean r8 = r8.selectTracks(r9, r10, r11, r12, r13, r15)
            r9 = 0
            r10 = 0
        L_0x00a2:
            int r11 = r1.length
            r12 = 1
            if (r9 >= r11) goto L_0x00ca
            r11 = r7[r9]
            r13 = r4[r9]
            if (r13 != r2) goto L_0x00bc
            com.google.android.exoplayer2.util.Assertions.checkNotNull(r11)
            r6[r9] = r11
            java.util.IdentityHashMap<com.google.android.exoplayer2.source.SampleStream, java.lang.Integer> r10 = r0.streamWrapperIndices
            java.lang.Integer r13 = java.lang.Integer.valueOf(r2)
            r10.put(r11, r13)
            r10 = 1
            goto L_0x00c7
        L_0x00bc:
            r13 = r3[r9]
            if (r13 != r2) goto L_0x00c7
            if (r11 != 0) goto L_0x00c3
            goto L_0x00c4
        L_0x00c3:
            r12 = 0
        L_0x00c4:
            com.google.android.exoplayer2.util.Assertions.checkState(r12)
        L_0x00c7:
            int r9 = r9 + 1
            goto L_0x00a2
        L_0x00ca:
            if (r10 == 0) goto L_0x00f2
            r18[r17] = r5
            int r9 = r17 + 1
            if (r17 != 0) goto L_0x00ec
            r5.setIsTimestampMaster(r12)
            if (r8 != 0) goto L_0x00e2
            com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper[] r8 = r0.enabledSampleStreamWrappers
            int r10 = r8.length
            if (r10 == 0) goto L_0x00e2
            r10 = 0
            r8 = r8[r10]
            if (r5 == r8) goto L_0x00f0
            goto L_0x00e3
        L_0x00e2:
            r10 = 0
        L_0x00e3:
            com.google.android.exoplayer2.source.hls.TimestampAdjusterProvider r5 = r0.timestampAdjusterProvider
            r5.reset()
            r12 = r9
            r16 = 1
            goto L_0x00f5
        L_0x00ec:
            r10 = 0
            r5.setIsTimestampMaster(r10)
        L_0x00f0:
            r12 = r9
            goto L_0x00f5
        L_0x00f2:
            r10 = 0
            r12 = r17
        L_0x00f5:
            int r14 = r2 + 1
            r2 = r23
            r13 = r18
            r15 = r19
            goto L_0x0065
        L_0x00ff:
            r17 = r12
            r18 = r13
            r10 = 0
            int r1 = r6.length
            r2 = r23
            java.lang.System.arraycopy(r6, r10, r2, r10, r1)
            r1 = r18
            java.lang.Object[] r1 = com.google.android.exoplayer2.util.Util.nullSafeArrayCopy(r1, r12)
            com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper[] r1 = (com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper[]) r1
            r0.enabledSampleStreamWrappers = r1
            com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory r1 = r0.compositeSequenceableLoaderFactory
            com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper[] r2 = r0.enabledSampleStreamWrappers
            com.google.android.exoplayer2.source.SequenceableLoader r1 = r1.createCompositeSequenceableLoader(r2)
            r0.compositeSequenceableLoader = r1
            return r25
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.hls.HlsMediaPeriod.selectTracks(com.google.android.exoplayer2.trackselection.TrackSelection[], boolean[], com.google.android.exoplayer2.source.SampleStream[], boolean[], long):long");
    }

    public void discardBuffer(long j, boolean z) {
        for (HlsSampleStreamWrapper discardBuffer : this.enabledSampleStreamWrappers) {
            discardBuffer.discardBuffer(j, z);
        }
    }

    public void reevaluateBuffer(long j) {
        this.compositeSequenceableLoader.reevaluateBuffer(j);
    }

    public boolean continueLoading(long j) {
        if (this.trackGroups != null) {
            return this.compositeSequenceableLoader.continueLoading(j);
        }
        for (HlsSampleStreamWrapper continuePreparing : this.sampleStreamWrappers) {
            continuePreparing.continuePreparing();
        }
        return false;
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
        HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr = this.enabledSampleStreamWrappers;
        if (hlsSampleStreamWrapperArr.length > 0) {
            boolean seekToUs = hlsSampleStreamWrapperArr[0].seekToUs(j, false);
            int i = 1;
            while (true) {
                HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr2 = this.enabledSampleStreamWrappers;
                if (i >= hlsSampleStreamWrapperArr2.length) {
                    break;
                }
                hlsSampleStreamWrapperArr2[i].seekToUs(j, seekToUs);
                i++;
            }
            if (seekToUs) {
                this.timestampAdjusterProvider.reset();
            }
        }
        return j;
    }

    public void onPrepared() {
        int i = this.pendingPrepareCount - 1;
        this.pendingPrepareCount = i;
        if (i <= 0) {
            int i2 = 0;
            for (HlsSampleStreamWrapper trackGroups2 : this.sampleStreamWrappers) {
                i2 += trackGroups2.getTrackGroups().length;
            }
            TrackGroup[] trackGroupArr = new TrackGroup[i2];
            HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr = this.sampleStreamWrappers;
            int length = hlsSampleStreamWrapperArr.length;
            int i3 = 0;
            int i4 = 0;
            while (i3 < length) {
                HlsSampleStreamWrapper hlsSampleStreamWrapper = hlsSampleStreamWrapperArr[i3];
                int i5 = hlsSampleStreamWrapper.getTrackGroups().length;
                int i6 = i4;
                int i7 = 0;
                while (i7 < i5) {
                    int i8 = i6 + 1;
                    trackGroupArr[i6] = hlsSampleStreamWrapper.getTrackGroups().get(i7);
                    i7++;
                    i6 = i8;
                }
                i3++;
                i4 = i6;
            }
            this.trackGroups = new TrackGroupArray(trackGroupArr);
            this.callback.onPrepared(this);
        }
    }

    public void onPlaylistRefreshRequired(Uri uri) {
        this.playlistTracker.refreshPlaylist(uri);
    }

    public void onContinueLoadingRequested(HlsSampleStreamWrapper hlsSampleStreamWrapper) {
        this.callback.onContinueLoadingRequested(this);
    }

    public void onPlaylistChanged() {
        this.callback.onContinueLoadingRequested(this);
    }

    public boolean onPlaylistError(Uri uri, long j) {
        boolean z = true;
        for (HlsSampleStreamWrapper onPlaylistError : this.sampleStreamWrappers) {
            z &= onPlaylistError.onPlaylistError(uri, j);
        }
        this.callback.onContinueLoadingRequested(this);
        return z;
    }

    private void buildAndPrepareSampleStreamWrappers(long j) {
        Map map;
        HlsMasterPlaylist hlsMasterPlaylist = (HlsMasterPlaylist) Assertions.checkNotNull(this.playlistTracker.getMasterPlaylist());
        if (this.useSessionKeys) {
            map = deriveOverridingDrmInitData(hlsMasterPlaylist.sessionKeyDrmInitData);
        } else {
            map = Collections.emptyMap();
        }
        Map map2 = map;
        boolean z = !hlsMasterPlaylist.variants.isEmpty();
        List<Rendition> list = hlsMasterPlaylist.audios;
        List<Rendition> list2 = hlsMasterPlaylist.subtitles;
        this.pendingPrepareCount = 0;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (z) {
            buildAndPrepareMainSampleStreamWrapper(hlsMasterPlaylist, j, arrayList, arrayList2, map2);
        }
        buildAndPrepareAudioSampleStreamWrappers(j, list, arrayList, arrayList2, map2);
        int i = 0;
        while (i < list2.size()) {
            Rendition rendition = (Rendition) list2.get(i);
            int i2 = i;
            Rendition rendition2 = rendition;
            HlsSampleStreamWrapper buildSampleStreamWrapper = buildSampleStreamWrapper(3, new Uri[]{rendition.url}, new Format[]{rendition.format}, null, Collections.emptyList(), map2, j);
            arrayList2.add(new int[]{i2});
            arrayList.add(buildSampleStreamWrapper);
            buildSampleStreamWrapper.prepareWithMasterPlaylistInfo(new TrackGroup[]{new TrackGroup(rendition2.format)}, 0, new int[0]);
            i = i2 + 1;
        }
        this.sampleStreamWrappers = (HlsSampleStreamWrapper[]) arrayList.toArray(new HlsSampleStreamWrapper[0]);
        this.manifestUrlIndicesPerWrapper = (int[][]) arrayList2.toArray(new int[0][]);
        HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr = this.sampleStreamWrappers;
        this.pendingPrepareCount = hlsSampleStreamWrapperArr.length;
        hlsSampleStreamWrapperArr[0].setIsTimestampMaster(true);
        for (HlsSampleStreamWrapper continuePreparing : this.sampleStreamWrappers) {
            continuePreparing.continuePreparing();
        }
        this.enabledSampleStreamWrappers = this.sampleStreamWrappers;
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0068 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void buildAndPrepareMainSampleStreamWrapper(com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist r20, long r21, java.util.List<com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper> r23, java.util.List<int[]> r24, java.util.Map<java.lang.String, com.google.android.exoplayer2.drm.DrmInitData> r25) {
        /*
            r19 = this;
            r0 = r20
            java.util.List<com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist$Variant> r1 = r0.variants
            int r1 = r1.size()
            int[] r1 = new int[r1]
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
        L_0x000e:
            java.util.List<com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist$Variant> r6 = r0.variants
            int r6 = r6.size()
            r7 = -1
            r8 = 2
            r9 = 1
            if (r3 >= r6) goto L_0x0047
            java.util.List<com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist$Variant> r6 = r0.variants
            java.lang.Object r6 = r6.get(r3)
            com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist$Variant r6 = (com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist.Variant) r6
            com.google.android.exoplayer2.Format r6 = r6.format
            int r10 = r6.height
            if (r10 > 0) goto L_0x0040
            java.lang.String r10 = r6.codecs
            java.lang.String r10 = com.google.android.exoplayer2.util.Util.getCodecsOfType(r10, r8)
            if (r10 == 0) goto L_0x0030
            goto L_0x0040
        L_0x0030:
            java.lang.String r6 = r6.codecs
            java.lang.String r6 = com.google.android.exoplayer2.util.Util.getCodecsOfType(r6, r9)
            if (r6 == 0) goto L_0x003d
            r1[r3] = r9
            int r5 = r5 + 1
            goto L_0x0044
        L_0x003d:
            r1[r3] = r7
            goto L_0x0044
        L_0x0040:
            r1[r3] = r8
            int r4 = r4 + 1
        L_0x0044:
            int r3 = r3 + 1
            goto L_0x000e
        L_0x0047:
            int r3 = r1.length
            if (r4 <= 0) goto L_0x004c
            r3 = 1
            goto L_0x0057
        L_0x004c:
            int r4 = r1.length
            if (r5 >= r4) goto L_0x0055
            int r3 = r1.length
            int r4 = r3 - r5
            r3 = 0
            r5 = 1
            goto L_0x0058
        L_0x0055:
            r4 = r3
            r3 = 0
        L_0x0057:
            r5 = 0
        L_0x0058:
            android.net.Uri[] r12 = new android.net.Uri[r4]
            com.google.android.exoplayer2.Format[] r6 = new com.google.android.exoplayer2.Format[r4]
            int[] r15 = new int[r4]
            r10 = 0
            r11 = 0
        L_0x0060:
            java.util.List<com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist$Variant> r13 = r0.variants
            int r13 = r13.size()
            if (r10 >= r13) goto L_0x008c
            if (r3 == 0) goto L_0x006e
            r13 = r1[r10]
            if (r13 != r8) goto L_0x0089
        L_0x006e:
            if (r5 == 0) goto L_0x0074
            r13 = r1[r10]
            if (r13 == r9) goto L_0x0089
        L_0x0074:
            java.util.List<com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist$Variant> r13 = r0.variants
            java.lang.Object r13 = r13.get(r10)
            com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist$Variant r13 = (com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist.Variant) r13
            android.net.Uri r14 = r13.url
            r12[r11] = r14
            com.google.android.exoplayer2.Format r13 = r13.format
            r6[r11] = r13
            int r13 = r11 + 1
            r15[r11] = r10
            r11 = r13
        L_0x0089:
            int r10 = r10 + 1
            goto L_0x0060
        L_0x008c:
            r1 = r6[r2]
            java.lang.String r1 = r1.codecs
            r11 = 0
            com.google.android.exoplayer2.Format r14 = r0.muxedAudioFormat
            java.util.List<com.google.android.exoplayer2.Format> r3 = r0.muxedCaptionFormats
            r10 = r19
            r13 = r6
            r5 = r15
            r15 = r3
            r16 = r25
            r17 = r21
            com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper r3 = r10.buildSampleStreamWrapper(r11, r12, r13, r14, r15, r16, r17)
            r10 = r23
            r10.add(r3)
            r10 = r24
            r10.add(r5)
            r5 = r19
            boolean r10 = r5.allowChunklessPreparation
            if (r10 == 0) goto L_0x0185
            if (r1 == 0) goto L_0x0185
            java.lang.String r8 = com.google.android.exoplayer2.util.Util.getCodecsOfType(r1, r8)
            if (r8 == 0) goto L_0x00bc
            r8 = 1
            goto L_0x00bd
        L_0x00bc:
            r8 = 0
        L_0x00bd:
            java.lang.String r10 = com.google.android.exoplayer2.util.Util.getCodecsOfType(r1, r9)
            if (r10 == 0) goto L_0x00c5
            r10 = 1
            goto L_0x00c6
        L_0x00c5:
            r10 = 0
        L_0x00c6:
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>()
            if (r8 == 0) goto L_0x0128
            com.google.android.exoplayer2.Format[] r1 = new com.google.android.exoplayer2.Format[r4]
            r4 = 0
        L_0x00d0:
            int r8 = r1.length
            if (r4 >= r8) goto L_0x00de
            r8 = r6[r4]
            com.google.android.exoplayer2.Format r8 = deriveVideoFormat(r8)
            r1[r4] = r8
            int r4 = r4 + 1
            goto L_0x00d0
        L_0x00de:
            com.google.android.exoplayer2.source.TrackGroup r4 = new com.google.android.exoplayer2.source.TrackGroup
            r4.<init>(r1)
            r11.add(r4)
            if (r10 == 0) goto L_0x0108
            com.google.android.exoplayer2.Format r1 = r0.muxedAudioFormat
            if (r1 != 0) goto L_0x00f4
            java.util.List<com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist$Rendition> r1 = r0.audios
            boolean r1 = r1.isEmpty()
            if (r1 == 0) goto L_0x0108
        L_0x00f4:
            com.google.android.exoplayer2.source.TrackGroup r1 = new com.google.android.exoplayer2.source.TrackGroup
            com.google.android.exoplayer2.Format[] r4 = new com.google.android.exoplayer2.Format[r9]
            r6 = r6[r2]
            com.google.android.exoplayer2.Format r8 = r0.muxedAudioFormat
            com.google.android.exoplayer2.Format r6 = deriveAudioFormat(r6, r8, r2)
            r4[r2] = r6
            r1.<init>(r4)
            r11.add(r1)
        L_0x0108:
            java.util.List<com.google.android.exoplayer2.Format> r0 = r0.muxedCaptionFormats
            if (r0 == 0) goto L_0x0145
            r1 = 0
        L_0x010d:
            int r4 = r0.size()
            if (r1 >= r4) goto L_0x0145
            com.google.android.exoplayer2.source.TrackGroup r4 = new com.google.android.exoplayer2.source.TrackGroup
            com.google.android.exoplayer2.Format[] r6 = new com.google.android.exoplayer2.Format[r9]
            java.lang.Object r8 = r0.get(r1)
            com.google.android.exoplayer2.Format r8 = (com.google.android.exoplayer2.Format) r8
            r6[r2] = r8
            r4.<init>(r6)
            r11.add(r4)
            int r1 = r1 + 1
            goto L_0x010d
        L_0x0128:
            if (r10 == 0) goto L_0x016e
            com.google.android.exoplayer2.Format[] r1 = new com.google.android.exoplayer2.Format[r4]
            r4 = 0
        L_0x012d:
            int r8 = r1.length
            if (r4 >= r8) goto L_0x013d
            r8 = r6[r4]
            com.google.android.exoplayer2.Format r10 = r0.muxedAudioFormat
            com.google.android.exoplayer2.Format r8 = deriveAudioFormat(r8, r10, r9)
            r1[r4] = r8
            int r4 = r4 + 1
            goto L_0x012d
        L_0x013d:
            com.google.android.exoplayer2.source.TrackGroup r0 = new com.google.android.exoplayer2.source.TrackGroup
            r0.<init>(r1)
            r11.add(r0)
        L_0x0145:
            com.google.android.exoplayer2.source.TrackGroup r0 = new com.google.android.exoplayer2.source.TrackGroup
            com.google.android.exoplayer2.Format[] r1 = new com.google.android.exoplayer2.Format[r9]
            r4 = 0
            java.lang.String r6 = "ID3"
            java.lang.String r8 = "application/id3"
            com.google.android.exoplayer2.Format r4 = com.google.android.exoplayer2.Format.createSampleFormat(r6, r8, r4, r7, r4)
            r1[r2] = r4
            r0.<init>(r1)
            r11.add(r0)
            com.google.android.exoplayer2.source.TrackGroup[] r1 = new com.google.android.exoplayer2.source.TrackGroup[r2]
            java.lang.Object[] r1 = r11.toArray(r1)
            com.google.android.exoplayer2.source.TrackGroup[] r1 = (com.google.android.exoplayer2.source.TrackGroup[]) r1
            int[] r4 = new int[r9]
            int r0 = r11.indexOf(r0)
            r4[r2] = r0
            r3.prepareWithMasterPlaylistInfo(r1, r2, r4)
            goto L_0x0185
        L_0x016e:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Unexpected codecs attribute: "
            r2.append(r3)
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            r0.<init>(r1)
            throw r0
        L_0x0185:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.hls.HlsMediaPeriod.buildAndPrepareMainSampleStreamWrapper(com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist, long, java.util.List, java.util.List, java.util.Map):void");
    }

    private void buildAndPrepareAudioSampleStreamWrappers(long j, List<Rendition> list, List<HlsSampleStreamWrapper> list2, List<int[]> list3, Map<String, DrmInitData> map) {
        List<Rendition> list4 = list;
        ArrayList arrayList = new ArrayList(list.size());
        ArrayList arrayList2 = new ArrayList(list.size());
        ArrayList arrayList3 = new ArrayList(list.size());
        HashSet hashSet = new HashSet();
        for (int i = 0; i < list.size(); i++) {
            String str = ((Rendition) list4.get(i)).name;
            if (!hashSet.add(str)) {
                List<HlsSampleStreamWrapper> list5 = list2;
                List<int[]> list6 = list3;
            } else {
                arrayList.clear();
                arrayList2.clear();
                arrayList3.clear();
                boolean z = true;
                for (int i2 = 0; i2 < list.size(); i2++) {
                    if (Util.areEqual(str, ((Rendition) list4.get(i2)).name)) {
                        Rendition rendition = (Rendition) list4.get(i2);
                        arrayList3.add(Integer.valueOf(i2));
                        arrayList.add(rendition.url);
                        arrayList2.add(rendition.format);
                        z &= rendition.format.codecs != null;
                    }
                }
                HlsSampleStreamWrapper buildSampleStreamWrapper = buildSampleStreamWrapper(1, (Uri[]) arrayList.toArray(Util.castNonNullTypeArray(new Uri[0])), (Format[]) arrayList2.toArray(new Format[0]), null, Collections.emptyList(), map, j);
                list3.add(Util.toArray(arrayList3));
                list2.add(buildSampleStreamWrapper);
                if (this.allowChunklessPreparation && z) {
                    buildSampleStreamWrapper.prepareWithMasterPlaylistInfo(new TrackGroup[]{new TrackGroup((Format[]) arrayList2.toArray(new Format[0]))}, 0, new int[0]);
                }
            }
        }
    }

    private HlsSampleStreamWrapper buildSampleStreamWrapper(int i, Uri[] uriArr, Format[] formatArr, Format format, List<Format> list, Map<String, DrmInitData> map, long j) {
        HlsChunkSource hlsChunkSource = new HlsChunkSource(this.extractorFactory, this.playlistTracker, uriArr, formatArr, this.dataSourceFactory, this.mediaTransferListener, this.timestampAdjusterProvider, list);
        HlsSampleStreamWrapper hlsSampleStreamWrapper = new HlsSampleStreamWrapper(i, this, hlsChunkSource, map, this.allocator, j, format, this.drmSessionManager, this.loadErrorHandlingPolicy, this.eventDispatcher, this.metadataType);
        return hlsSampleStreamWrapper;
    }

    private static Map<String, DrmInitData> deriveOverridingDrmInitData(List<DrmInitData> list) {
        ArrayList arrayList = new ArrayList(list);
        HashMap hashMap = new HashMap();
        int i = 0;
        while (i < arrayList.size()) {
            DrmInitData drmInitData = (DrmInitData) list.get(i);
            String str = drmInitData.schemeType;
            i++;
            DrmInitData drmInitData2 = drmInitData;
            int i2 = i;
            while (i2 < arrayList.size()) {
                DrmInitData drmInitData3 = (DrmInitData) arrayList.get(i2);
                if (TextUtils.equals(drmInitData3.schemeType, str)) {
                    drmInitData2 = drmInitData2.merge(drmInitData3);
                    arrayList.remove(i2);
                } else {
                    i2++;
                }
            }
            hashMap.put(str, drmInitData2);
        }
        return hashMap;
    }

    private static Format deriveVideoFormat(Format format) {
        String codecsOfType = Util.getCodecsOfType(format.codecs, 2);
        return Format.createVideoContainerFormat(format.f1474id, format.label, format.containerMimeType, MimeTypes.getMediaMimeType(codecsOfType), codecsOfType, format.metadata, format.bitrate, format.width, format.height, format.frameRate, null, format.selectionFlags, format.roleFlags);
    }

    private static Format deriveAudioFormat(Format format, Format format2, boolean z) {
        String str;
        int i;
        int i2;
        int i3;
        Metadata metadata;
        String str2;
        String str3;
        Format format3 = format;
        Format format4 = format2;
        if (format4 != null) {
            String str4 = format4.codecs;
            Metadata metadata2 = format4.metadata;
            int i4 = format4.channelCount;
            int i5 = format4.selectionFlags;
            int i6 = format4.roleFlags;
            String str5 = format4.language;
            str3 = format4.label;
            str2 = str4;
            metadata = metadata2;
            i3 = i4;
            i2 = i5;
            i = i6;
            str = str5;
        } else {
            String codecsOfType = Util.getCodecsOfType(format3.codecs, 1);
            Metadata metadata3 = format3.metadata;
            if (z) {
                int i7 = format3.channelCount;
                int i8 = format3.selectionFlags;
                str2 = codecsOfType;
                i3 = i7;
                i2 = i8;
                metadata = metadata3;
                i = format3.roleFlags;
                str = format3.language;
                str3 = format3.label;
            } else {
                str2 = codecsOfType;
                str3 = null;
                str = null;
                metadata = metadata3;
                i3 = -1;
                i2 = 0;
                i = 0;
            }
        }
        return Format.createAudioContainerFormat(format3.f1474id, str3, format3.containerMimeType, MimeTypes.getMediaMimeType(str2), str2, metadata, z ? format3.bitrate : -1, i3, -1, null, i2, i, str);
    }
}

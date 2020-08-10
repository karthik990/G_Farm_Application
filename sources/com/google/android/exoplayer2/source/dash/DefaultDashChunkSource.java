package com.google.android.exoplayer2.source.dash;

import android.os.SystemClock;
import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.chunk.BaseMediaChunkIterator;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.chunk.ChunkExtractorWrapper;
import com.google.android.exoplayer2.source.chunk.ChunkHolder;
import com.google.android.exoplayer2.source.chunk.ContainerMediaChunk;
import com.google.android.exoplayer2.source.chunk.InitializationChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.source.chunk.SingleSampleMediaChunk;
import com.google.android.exoplayer2.source.dash.PlayerEmsgHandler.PlayerTrackEmsgHandler;
import com.google.android.exoplayer2.source.dash.manifest.AdaptationSet;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.RangedUri;
import com.google.android.exoplayer2.source.dash.manifest.Representation;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.HttpDataSource.InvalidResponseCodeException;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DefaultDashChunkSource implements DashChunkSource {
    private final int[] adaptationSetIndices;
    private final DataSource dataSource;
    private final long elapsedRealtimeOffsetMs;
    private IOException fatalError;
    private long liveEdgeTimeUs = C1996C.TIME_UNSET;
    private DashManifest manifest;
    private final LoaderErrorThrower manifestLoaderErrorThrower;
    private final int maxSegmentsPerLoad;
    private boolean missingLastSegment;
    private int periodIndex;
    private final PlayerTrackEmsgHandler playerTrackEmsgHandler;
    protected final RepresentationHolder[] representationHolders;
    private TrackSelection trackSelection;
    private final int trackType;

    public static final class Factory implements com.google.android.exoplayer2.source.dash.DashChunkSource.Factory {
        private final com.google.android.exoplayer2.upstream.DataSource.Factory dataSourceFactory;
        private final int maxSegmentsPerLoad;

        public Factory(com.google.android.exoplayer2.upstream.DataSource.Factory factory) {
            this(factory, 1);
        }

        public Factory(com.google.android.exoplayer2.upstream.DataSource.Factory factory, int i) {
            this.dataSourceFactory = factory;
            this.maxSegmentsPerLoad = i;
        }

        public DashChunkSource createDashChunkSource(LoaderErrorThrower loaderErrorThrower, DashManifest dashManifest, int i, int[] iArr, TrackSelection trackSelection, int i2, long j, boolean z, List<Format> list, PlayerTrackEmsgHandler playerTrackEmsgHandler, TransferListener transferListener) {
            TransferListener transferListener2 = transferListener;
            DataSource createDataSource = this.dataSourceFactory.createDataSource();
            if (transferListener2 != null) {
                createDataSource.addTransferListener(transferListener2);
            }
            DefaultDashChunkSource defaultDashChunkSource = new DefaultDashChunkSource(loaderErrorThrower, dashManifest, i, iArr, trackSelection, i2, createDataSource, j, this.maxSegmentsPerLoad, z, list, playerTrackEmsgHandler);
            return defaultDashChunkSource;
        }
    }

    protected static final class RepresentationHolder {
        final ChunkExtractorWrapper extractorWrapper;
        /* access modifiers changed from: private */
        public final long periodDurationUs;
        public final Representation representation;
        public final DashSegmentIndex segmentIndex;
        private final long segmentNumShift;

        RepresentationHolder(long j, int i, Representation representation2, boolean z, List<Format> list, TrackOutput trackOutput) {
            this(j, representation2, createExtractorWrapper(i, representation2, z, list, trackOutput), 0, representation2.getIndex());
        }

        private RepresentationHolder(long j, Representation representation2, ChunkExtractorWrapper chunkExtractorWrapper, long j2, DashSegmentIndex dashSegmentIndex) {
            this.periodDurationUs = j;
            this.representation = representation2;
            this.segmentNumShift = j2;
            this.extractorWrapper = chunkExtractorWrapper;
            this.segmentIndex = dashSegmentIndex;
        }

        /* access modifiers changed from: 0000 */
        public RepresentationHolder copyWithNewRepresentation(long j, Representation representation2) throws BehindLiveWindowException {
            long j2;
            long j3;
            long j4 = j;
            DashSegmentIndex index = this.representation.getIndex();
            DashSegmentIndex index2 = representation2.getIndex();
            if (index == null) {
                RepresentationHolder representationHolder = new RepresentationHolder(j, representation2, this.extractorWrapper, this.segmentNumShift, index);
                return representationHolder;
            } else if (!index.isExplicit()) {
                RepresentationHolder representationHolder2 = new RepresentationHolder(j, representation2, this.extractorWrapper, this.segmentNumShift, index2);
                return representationHolder2;
            } else {
                int segmentCount = index.getSegmentCount(j4);
                if (segmentCount == 0) {
                    RepresentationHolder representationHolder3 = new RepresentationHolder(j, representation2, this.extractorWrapper, this.segmentNumShift, index2);
                    return representationHolder3;
                }
                long firstSegmentNum = index.getFirstSegmentNum();
                long timeUs = index.getTimeUs(firstSegmentNum);
                long j5 = (((long) segmentCount) + firstSegmentNum) - 1;
                long timeUs2 = index.getTimeUs(j5) + index.getDurationUs(j5, j4);
                long firstSegmentNum2 = index2.getFirstSegmentNum();
                long j6 = firstSegmentNum;
                long timeUs3 = index2.getTimeUs(firstSegmentNum2);
                long j7 = this.segmentNumShift;
                if (timeUs2 == timeUs3) {
                    j2 = j7 + ((j5 + 1) - firstSegmentNum2);
                    long j8 = j;
                } else if (timeUs2 >= timeUs3) {
                    if (timeUs3 < timeUs) {
                        j3 = j7 - (index2.getSegmentNum(timeUs, j) - j6);
                    } else {
                        j3 = (index.getSegmentNum(timeUs3, j) - firstSegmentNum2) + j7;
                    }
                    j2 = j3;
                } else {
                    throw new BehindLiveWindowException();
                }
                RepresentationHolder representationHolder4 = new RepresentationHolder(j, representation2, this.extractorWrapper, j2, index2);
                return representationHolder4;
            }
        }

        /* access modifiers changed from: 0000 */
        public RepresentationHolder copyWithNewSegmentIndex(DashSegmentIndex dashSegmentIndex) {
            RepresentationHolder representationHolder = new RepresentationHolder(this.periodDurationUs, this.representation, this.extractorWrapper, this.segmentNumShift, dashSegmentIndex);
            return representationHolder;
        }

        public long getFirstSegmentNum() {
            return this.segmentIndex.getFirstSegmentNum() + this.segmentNumShift;
        }

        public int getSegmentCount() {
            return this.segmentIndex.getSegmentCount(this.periodDurationUs);
        }

        public long getSegmentStartTimeUs(long j) {
            return this.segmentIndex.getTimeUs(j - this.segmentNumShift);
        }

        public long getSegmentEndTimeUs(long j) {
            return getSegmentStartTimeUs(j) + this.segmentIndex.getDurationUs(j - this.segmentNumShift, this.periodDurationUs);
        }

        public long getSegmentNum(long j) {
            return this.segmentIndex.getSegmentNum(j, this.periodDurationUs) + this.segmentNumShift;
        }

        public RangedUri getSegmentUrl(long j) {
            return this.segmentIndex.getSegmentUrl(j - this.segmentNumShift);
        }

        public long getFirstAvailableSegmentNum(DashManifest dashManifest, int i, long j) {
            if (getSegmentCount() != -1 || dashManifest.timeShiftBufferDepthMs == C1996C.TIME_UNSET) {
                return getFirstSegmentNum();
            }
            return Math.max(getFirstSegmentNum(), getSegmentNum(((j - C1996C.msToUs(dashManifest.availabilityStartTimeMs)) - C1996C.msToUs(dashManifest.getPeriod(i).startMs)) - C1996C.msToUs(dashManifest.timeShiftBufferDepthMs)));
        }

        public long getLastAvailableSegmentNum(DashManifest dashManifest, int i, long j) {
            long firstSegmentNum;
            int segmentCount = getSegmentCount();
            if (segmentCount == -1) {
                firstSegmentNum = getSegmentNum((j - C1996C.msToUs(dashManifest.availabilityStartTimeMs)) - C1996C.msToUs(dashManifest.getPeriod(i).startMs));
            } else {
                firstSegmentNum = getFirstSegmentNum() + ((long) segmentCount);
            }
            return firstSegmentNum - 1;
        }

        private static boolean mimeTypeIsWebm(String str) {
            return str.startsWith(MimeTypes.VIDEO_WEBM) || str.startsWith(MimeTypes.AUDIO_WEBM) || str.startsWith(MimeTypes.APPLICATION_WEBM);
        }

        private static boolean mimeTypeIsRawText(String str) {
            return MimeTypes.isText(str) || MimeTypes.APPLICATION_TTML.equals(str);
        }

        /* JADX WARNING: type inference failed for: r10v3, types: [com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor] */
        /* JADX WARNING: type inference failed for: r10v4, types: [com.google.android.exoplayer2.extractor.rawcc.RawCcExtractor] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* JADX WARNING: Unknown variable types count: 2 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static com.google.android.exoplayer2.source.chunk.ChunkExtractorWrapper createExtractorWrapper(int r8, com.google.android.exoplayer2.source.dash.manifest.Representation r9, boolean r10, java.util.List<com.google.android.exoplayer2.Format> r11, com.google.android.exoplayer2.extractor.TrackOutput r12) {
            /*
                com.google.android.exoplayer2.Format r0 = r9.format
                java.lang.String r0 = r0.containerMimeType
                boolean r1 = mimeTypeIsRawText(r0)
                if (r1 == 0) goto L_0x000c
                r8 = 0
                return r8
            L_0x000c:
                java.lang.String r1 = "application/x-rawcc"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x001c
                com.google.android.exoplayer2.extractor.rawcc.RawCcExtractor r10 = new com.google.android.exoplayer2.extractor.rawcc.RawCcExtractor
                com.google.android.exoplayer2.Format r11 = r9.format
                r10.<init>(r11)
                goto L_0x003b
            L_0x001c:
                boolean r0 = mimeTypeIsWebm(r0)
                if (r0 == 0) goto L_0x0029
                com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor r10 = new com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor
                r11 = 1
                r10.<init>(r11)
                goto L_0x003b
            L_0x0029:
                r0 = 0
                if (r10 == 0) goto L_0x002f
                r0 = 4
                r2 = 4
                goto L_0x0030
            L_0x002f:
                r2 = 0
            L_0x0030:
                com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor r10 = new com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor
                r3 = 0
                r4 = 0
                r5 = 0
                r1 = r10
                r6 = r11
                r7 = r12
                r1.<init>(r2, r3, r4, r5, r6, r7)
            L_0x003b:
                com.google.android.exoplayer2.source.chunk.ChunkExtractorWrapper r11 = new com.google.android.exoplayer2.source.chunk.ChunkExtractorWrapper
                com.google.android.exoplayer2.Format r9 = r9.format
                r11.<init>(r10, r8, r9)
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.DefaultDashChunkSource.RepresentationHolder.createExtractorWrapper(int, com.google.android.exoplayer2.source.dash.manifest.Representation, boolean, java.util.List, com.google.android.exoplayer2.extractor.TrackOutput):com.google.android.exoplayer2.source.chunk.ChunkExtractorWrapper");
        }
    }

    protected static final class RepresentationSegmentIterator extends BaseMediaChunkIterator {
        private final RepresentationHolder representationHolder;

        public RepresentationSegmentIterator(RepresentationHolder representationHolder2, long j, long j2) {
            super(j, j2);
            this.representationHolder = representationHolder2;
        }

        public DataSpec getDataSpec() {
            checkInBounds();
            Representation representation = this.representationHolder.representation;
            RangedUri segmentUrl = this.representationHolder.getSegmentUrl(getCurrentIndex());
            DataSpec dataSpec = new DataSpec(segmentUrl.resolveUri(representation.baseUrl), segmentUrl.start, segmentUrl.length, representation.getCacheKey());
            return dataSpec;
        }

        public long getChunkStartTimeUs() {
            checkInBounds();
            return this.representationHolder.getSegmentStartTimeUs(getCurrentIndex());
        }

        public long getChunkEndTimeUs() {
            checkInBounds();
            return this.representationHolder.getSegmentEndTimeUs(getCurrentIndex());
        }
    }

    public DefaultDashChunkSource(LoaderErrorThrower loaderErrorThrower, DashManifest dashManifest, int i, int[] iArr, TrackSelection trackSelection2, int i2, DataSource dataSource2, long j, int i3, boolean z, List<Format> list, PlayerTrackEmsgHandler playerTrackEmsgHandler2) {
        TrackSelection trackSelection3 = trackSelection2;
        this.manifestLoaderErrorThrower = loaderErrorThrower;
        this.manifest = dashManifest;
        this.adaptationSetIndices = iArr;
        this.trackSelection = trackSelection3;
        this.trackType = i2;
        this.dataSource = dataSource2;
        this.periodIndex = i;
        this.elapsedRealtimeOffsetMs = j;
        this.maxSegmentsPerLoad = i3;
        this.playerTrackEmsgHandler = playerTrackEmsgHandler2;
        long periodDurationUs = dashManifest.getPeriodDurationUs(i);
        ArrayList representations = getRepresentations();
        this.representationHolders = new RepresentationHolder[trackSelection2.length()];
        for (int i4 = 0; i4 < this.representationHolders.length; i4++) {
            Representation representation = (Representation) representations.get(trackSelection3.getIndexInTrackGroup(i4));
            RepresentationHolder[] representationHolderArr = this.representationHolders;
            RepresentationHolder representationHolder = new RepresentationHolder(periodDurationUs, i2, representation, z, list, playerTrackEmsgHandler2);
            representationHolderArr[i4] = representationHolder;
        }
    }

    public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
        RepresentationHolder[] representationHolderArr = this.representationHolders;
        int length = representationHolderArr.length;
        for (int i = 0; i < length; i++) {
            RepresentationHolder representationHolder = representationHolderArr[i];
            if (representationHolder.segmentIndex != null) {
                long segmentNum = representationHolder.getSegmentNum(j);
                long segmentStartTimeUs = representationHolder.getSegmentStartTimeUs(segmentNum);
                return Util.resolveSeekPositionUs(j, seekParameters, segmentStartTimeUs, (segmentStartTimeUs >= j || segmentNum >= ((long) (representationHolder.getSegmentCount() + -1))) ? segmentStartTimeUs : representationHolder.getSegmentStartTimeUs(segmentNum + 1));
            }
        }
        return j;
    }

    public void updateManifest(DashManifest dashManifest, int i) {
        try {
            this.manifest = dashManifest;
            this.periodIndex = i;
            long periodDurationUs = this.manifest.getPeriodDurationUs(this.periodIndex);
            ArrayList representations = getRepresentations();
            for (int i2 = 0; i2 < this.representationHolders.length; i2++) {
                this.representationHolders[i2] = this.representationHolders[i2].copyWithNewRepresentation(periodDurationUs, (Representation) representations.get(this.trackSelection.getIndexInTrackGroup(i2)));
            }
        } catch (BehindLiveWindowException e) {
            this.fatalError = e;
        }
    }

    public void updateTrackSelection(TrackSelection trackSelection2) {
        this.trackSelection = trackSelection2;
    }

    public void maybeThrowError() throws IOException {
        IOException iOException = this.fatalError;
        if (iOException == null) {
            this.manifestLoaderErrorThrower.maybeThrowError();
            return;
        }
        throw iOException;
    }

    public int getPreferredQueueSize(long j, List<? extends MediaChunk> list) {
        if (this.fatalError != null || this.trackSelection.length() < 2) {
            return list.size();
        }
        return this.trackSelection.evaluateQueueSize(j, list);
    }

    public void getNextChunk(long j, long j2, List<? extends MediaChunk> list, ChunkHolder chunkHolder) {
        MediaChunk mediaChunk;
        long j3;
        int i;
        MediaChunkIterator[] mediaChunkIteratorArr;
        ChunkHolder chunkHolder2 = chunkHolder;
        if (this.fatalError == null) {
            long j4 = j2 - j;
            long resolveTimeToLiveEdgeUs = resolveTimeToLiveEdgeUs(j);
            long msToUs = C1996C.msToUs(this.manifest.availabilityStartTimeMs) + C1996C.msToUs(this.manifest.getPeriod(this.periodIndex).startMs) + j2;
            PlayerTrackEmsgHandler playerTrackEmsgHandler2 = this.playerTrackEmsgHandler;
            if (playerTrackEmsgHandler2 == null || !playerTrackEmsgHandler2.maybeRefreshManifestBeforeLoadingNextChunk(msToUs)) {
                long nowUnixTimeUs = getNowUnixTimeUs();
                if (list.isEmpty()) {
                    List<? extends MediaChunk> list2 = list;
                    mediaChunk = null;
                } else {
                    mediaChunk = (MediaChunk) list.get(list.size() - 1);
                }
                MediaChunkIterator[] mediaChunkIteratorArr2 = new MediaChunkIterator[this.trackSelection.length()];
                int i2 = 0;
                while (i2 < mediaChunkIteratorArr2.length) {
                    RepresentationHolder representationHolder = this.representationHolders[i2];
                    if (representationHolder.segmentIndex == null) {
                        mediaChunkIteratorArr2[i2] = MediaChunkIterator.EMPTY;
                        mediaChunkIteratorArr = mediaChunkIteratorArr2;
                        i = i2;
                        j3 = nowUnixTimeUs;
                    } else {
                        long firstAvailableSegmentNum = representationHolder.getFirstAvailableSegmentNum(this.manifest, this.periodIndex, nowUnixTimeUs);
                        long lastAvailableSegmentNum = representationHolder.getLastAvailableSegmentNum(this.manifest, this.periodIndex, nowUnixTimeUs);
                        RepresentationHolder representationHolder2 = representationHolder;
                        mediaChunkIteratorArr = mediaChunkIteratorArr2;
                        i = i2;
                        j3 = nowUnixTimeUs;
                        long segmentNum = getSegmentNum(representationHolder, mediaChunk, j2, firstAvailableSegmentNum, lastAvailableSegmentNum);
                        if (segmentNum < firstAvailableSegmentNum) {
                            mediaChunkIteratorArr[i] = MediaChunkIterator.EMPTY;
                        } else {
                            RepresentationSegmentIterator representationSegmentIterator = new RepresentationSegmentIterator(representationHolder2, segmentNum, lastAvailableSegmentNum);
                            mediaChunkIteratorArr[i] = representationSegmentIterator;
                        }
                    }
                    i2 = i + 1;
                    List<? extends MediaChunk> list3 = list;
                    mediaChunkIteratorArr2 = mediaChunkIteratorArr;
                    nowUnixTimeUs = j3;
                }
                long j5 = nowUnixTimeUs;
                this.trackSelection.updateSelectedTrack(j, j4, resolveTimeToLiveEdgeUs, list, mediaChunkIteratorArr2);
                RepresentationHolder representationHolder3 = this.representationHolders[this.trackSelection.getSelectedIndex()];
                if (representationHolder3.extractorWrapper != null) {
                    Representation representation = representationHolder3.representation;
                    RangedUri initializationUri = representationHolder3.extractorWrapper.getSampleFormats() == null ? representation.getInitializationUri() : null;
                    RangedUri indexUri = representationHolder3.segmentIndex == null ? representation.getIndexUri() : null;
                    if (!(initializationUri == null && indexUri == null)) {
                        chunkHolder2.chunk = newInitializationChunk(representationHolder3, this.dataSource, this.trackSelection.getSelectedFormat(), this.trackSelection.getSelectionReason(), this.trackSelection.getSelectionData(), initializationUri, indexUri);
                        return;
                    }
                }
                long access$000 = representationHolder3.periodDurationUs;
                long j6 = C1996C.TIME_UNSET;
                boolean z = access$000 != C1996C.TIME_UNSET;
                if (representationHolder3.getSegmentCount() == 0) {
                    chunkHolder2.endOfStream = z;
                    return;
                }
                long j7 = j5;
                long firstAvailableSegmentNum2 = representationHolder3.getFirstAvailableSegmentNum(this.manifest, this.periodIndex, j7);
                long lastAvailableSegmentNum2 = representationHolder3.getLastAvailableSegmentNum(this.manifest, this.periodIndex, j7);
                updateLiveEdgeTimeUs(representationHolder3, lastAvailableSegmentNum2);
                long j8 = lastAvailableSegmentNum2;
                long segmentNum2 = getSegmentNum(representationHolder3, mediaChunk, j2, firstAvailableSegmentNum2, lastAvailableSegmentNum2);
                if (segmentNum2 < firstAvailableSegmentNum2) {
                    this.fatalError = new BehindLiveWindowException();
                } else if (segmentNum2 > j8 || (this.missingLastSegment && segmentNum2 >= j8)) {
                    chunkHolder2.endOfStream = z;
                } else if (!z || representationHolder3.getSegmentStartTimeUs(segmentNum2) < access$000) {
                    int min = (int) Math.min((long) this.maxSegmentsPerLoad, (j8 - segmentNum2) + 1);
                    if (access$000 != C1996C.TIME_UNSET) {
                        while (min > 1 && representationHolder3.getSegmentStartTimeUs((((long) min) + segmentNum2) - 1) >= access$000) {
                            min--;
                        }
                    }
                    int i3 = min;
                    if (list.isEmpty()) {
                        j6 = j2;
                    }
                    chunkHolder2.chunk = newMediaChunk(representationHolder3, this.dataSource, this.trackType, this.trackSelection.getSelectedFormat(), this.trackSelection.getSelectionReason(), this.trackSelection.getSelectionData(), segmentNum2, i3, j6);
                } else {
                    chunkHolder2.endOfStream = true;
                }
            }
        }
    }

    public void onChunkLoadCompleted(Chunk chunk) {
        if (chunk instanceof InitializationChunk) {
            int indexOf = this.trackSelection.indexOf(((InitializationChunk) chunk).trackFormat);
            RepresentationHolder representationHolder = this.representationHolders[indexOf];
            if (representationHolder.segmentIndex == null) {
                SeekMap seekMap = representationHolder.extractorWrapper.getSeekMap();
                if (seekMap != null) {
                    this.representationHolders[indexOf] = representationHolder.copyWithNewSegmentIndex(new DashWrappingSegmentIndex((ChunkIndex) seekMap, representationHolder.representation.presentationTimeOffsetUs));
                }
            }
        }
        PlayerTrackEmsgHandler playerTrackEmsgHandler2 = this.playerTrackEmsgHandler;
        if (playerTrackEmsgHandler2 != null) {
            playerTrackEmsgHandler2.onChunkLoadCompleted(chunk);
        }
    }

    public boolean onChunkLoadError(Chunk chunk, boolean z, Exception exc, long j) {
        boolean z2 = false;
        if (!z) {
            return false;
        }
        PlayerTrackEmsgHandler playerTrackEmsgHandler2 = this.playerTrackEmsgHandler;
        if (playerTrackEmsgHandler2 != null && playerTrackEmsgHandler2.maybeRefreshManifestOnLoadingError(chunk)) {
            return true;
        }
        if (!this.manifest.dynamic && (chunk instanceof MediaChunk) && (exc instanceof InvalidResponseCodeException) && ((InvalidResponseCodeException) exc).responseCode == 404) {
            RepresentationHolder representationHolder = this.representationHolders[this.trackSelection.indexOf(chunk.trackFormat)];
            int segmentCount = representationHolder.getSegmentCount();
            if (!(segmentCount == -1 || segmentCount == 0)) {
                if (((MediaChunk) chunk).getNextChunkIndex() > (representationHolder.getFirstSegmentNum() + ((long) segmentCount)) - 1) {
                    this.missingLastSegment = true;
                    return true;
                }
            }
        }
        if (j != C1996C.TIME_UNSET) {
            TrackSelection trackSelection2 = this.trackSelection;
            if (trackSelection2.blacklist(trackSelection2.indexOf(chunk.trackFormat), j)) {
                z2 = true;
            }
        }
        return z2;
    }

    private long getSegmentNum(RepresentationHolder representationHolder, MediaChunk mediaChunk, long j, long j2, long j3) {
        if (mediaChunk != null) {
            return mediaChunk.getNextChunkIndex();
        }
        return Util.constrainValue(representationHolder.getSegmentNum(j), j2, j3);
    }

    private ArrayList<Representation> getRepresentations() {
        List<AdaptationSet> list = this.manifest.getPeriod(this.periodIndex).adaptationSets;
        ArrayList<Representation> arrayList = new ArrayList<>();
        for (int i : this.adaptationSetIndices) {
            arrayList.addAll(((AdaptationSet) list.get(i)).representations);
        }
        return arrayList;
    }

    private void updateLiveEdgeTimeUs(RepresentationHolder representationHolder, long j) {
        this.liveEdgeTimeUs = this.manifest.dynamic ? representationHolder.getSegmentEndTimeUs(j) : C1996C.TIME_UNSET;
    }

    private long getNowUnixTimeUs() {
        long currentTimeMillis;
        if (this.elapsedRealtimeOffsetMs != 0) {
            currentTimeMillis = SystemClock.elapsedRealtime() + this.elapsedRealtimeOffsetMs;
        } else {
            currentTimeMillis = System.currentTimeMillis();
        }
        return currentTimeMillis * 1000;
    }

    private long resolveTimeToLiveEdgeUs(long j) {
        return this.manifest.dynamic && (this.liveEdgeTimeUs > C1996C.TIME_UNSET ? 1 : (this.liveEdgeTimeUs == C1996C.TIME_UNSET ? 0 : -1)) != 0 ? this.liveEdgeTimeUs - j : C1996C.TIME_UNSET;
    }

    /* access modifiers changed from: protected */
    public Chunk newInitializationChunk(RepresentationHolder representationHolder, DataSource dataSource2, Format format, int i, Object obj, RangedUri rangedUri, RangedUri rangedUri2) {
        String str = representationHolder.representation.baseUrl;
        if (rangedUri != null) {
            rangedUri2 = rangedUri.attemptMerge(rangedUri2, str);
            if (rangedUri2 == null) {
                rangedUri2 = rangedUri;
            }
        }
        DataSpec dataSpec = new DataSpec(rangedUri2.resolveUri(str), rangedUri2.start, rangedUri2.length, representationHolder.representation.getCacheKey());
        InitializationChunk initializationChunk = new InitializationChunk(dataSource2, dataSpec, format, i, obj, representationHolder.extractorWrapper);
        return initializationChunk;
    }

    /* access modifiers changed from: protected */
    public Chunk newMediaChunk(RepresentationHolder representationHolder, DataSource dataSource2, int i, Format format, int i2, Object obj, long j, int i3, long j2) {
        RepresentationHolder representationHolder2 = representationHolder;
        long j3 = j;
        Representation representation = representationHolder2.representation;
        long segmentStartTimeUs = representationHolder2.getSegmentStartTimeUs(j3);
        RangedUri segmentUrl = representationHolder2.getSegmentUrl(j3);
        String str = representation.baseUrl;
        if (representationHolder2.extractorWrapper == null) {
            long segmentEndTimeUs = representationHolder2.getSegmentEndTimeUs(j3);
            DataSpec dataSpec = new DataSpec(segmentUrl.resolveUri(str), segmentUrl.start, segmentUrl.length, representation.getCacheKey());
            SingleSampleMediaChunk singleSampleMediaChunk = new SingleSampleMediaChunk(dataSource2, dataSpec, format, i2, obj, segmentStartTimeUs, segmentEndTimeUs, j, i, format);
            return singleSampleMediaChunk;
        }
        int i4 = 1;
        RangedUri rangedUri = segmentUrl;
        int i5 = 1;
        int i6 = i3;
        while (i4 < i6) {
            RangedUri attemptMerge = rangedUri.attemptMerge(representationHolder2.getSegmentUrl(((long) i4) + j3), str);
            if (attemptMerge == null) {
                break;
            }
            i5++;
            i4++;
            rangedUri = attemptMerge;
        }
        long segmentEndTimeUs2 = representationHolder2.getSegmentEndTimeUs((((long) i5) + j3) - 1);
        long access$000 = representationHolder.periodDurationUs;
        long j4 = (access$000 == C1996C.TIME_UNSET || access$000 > segmentEndTimeUs2) ? -9223372036854775807L : access$000;
        DataSpec dataSpec2 = r18;
        DataSpec dataSpec3 = new DataSpec(rangedUri.resolveUri(str), rangedUri.start, rangedUri.length, representation.getCacheKey());
        ContainerMediaChunk containerMediaChunk = new ContainerMediaChunk(dataSource2, dataSpec2, format, i2, obj, segmentStartTimeUs, segmentEndTimeUs2, j2, j4, j, i5, -representation.presentationTimeOffsetUs, representationHolder2.extractorWrapper);
        return containerMediaChunk;
    }
}

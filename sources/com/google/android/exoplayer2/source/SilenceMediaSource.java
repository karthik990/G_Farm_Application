package com.google.android.exoplayer2.source;

import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.MediaPeriod.CC;
import com.google.android.exoplayer2.source.MediaPeriod.Callback;
import com.google.android.exoplayer2.source.MediaSource.MediaPeriodId;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.List;

public final class SilenceMediaSource extends BaseMediaSource {
    private static final int CHANNEL_COUNT = 2;
    private static final int ENCODING = 2;
    /* access modifiers changed from: private */
    public static final Format FORMAT = Format.createAudioSampleFormat(null, MimeTypes.AUDIO_RAW, null, -1, -1, 2, SAMPLE_RATE_HZ, 2, null, null, 0, null);
    private static final int SAMPLE_RATE_HZ = 44100;
    /* access modifiers changed from: private */
    public static final byte[] SILENCE_SAMPLE = new byte[(Util.getPcmFrameSize(2, 2) * 1024)];
    private final long durationUs;

    private static final class SilenceMediaPeriod implements MediaPeriod {
        private static final TrackGroupArray TRACKS = new TrackGroupArray(new TrackGroup(SilenceMediaSource.FORMAT));
        private final long durationUs;
        private final ArrayList<SampleStream> sampleStreams = new ArrayList<>();

        public boolean continueLoading(long j) {
            return false;
        }

        public void discardBuffer(long j, boolean z) {
        }

        public long getBufferedPositionUs() {
            return Long.MIN_VALUE;
        }

        public long getNextLoadPositionUs() {
            return Long.MIN_VALUE;
        }

        public /* synthetic */ List<StreamKey> getStreamKeys(List<TrackSelection> list) {
            return CC.$default$getStreamKeys(this, list);
        }

        public boolean isLoading() {
            return false;
        }

        public void maybeThrowPrepareError() {
        }

        public long readDiscontinuity() {
            return C1996C.TIME_UNSET;
        }

        public void reevaluateBuffer(long j) {
        }

        public SilenceMediaPeriod(long j) {
            this.durationUs = j;
        }

        public void prepare(Callback callback, long j) {
            callback.onPrepared(this);
        }

        public TrackGroupArray getTrackGroups() {
            return TRACKS;
        }

        /* JADX WARNING: Incorrect type for immutable var: ssa=com.google.android.exoplayer2.source.SampleStream[], code=java.lang.Object[], for r7v0, types: [com.google.android.exoplayer2.source.SampleStream[], java.lang.Object[]] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public long selectTracks(com.google.android.exoplayer2.trackselection.TrackSelection[] r5, boolean[] r6, java.lang.Object[] r7, boolean[] r8, long r9) {
            /*
                r4 = this;
                long r9 = r4.constrainSeekPosition(r9)
                r0 = 0
            L_0x0005:
                int r1 = r5.length
                if (r0 >= r1) goto L_0x003d
                r1 = r7[r0]
                if (r1 == 0) goto L_0x001e
                r1 = r5[r0]
                if (r1 == 0) goto L_0x0014
                boolean r1 = r6[r0]
                if (r1 != 0) goto L_0x001e
            L_0x0014:
                java.util.ArrayList<com.google.android.exoplayer2.source.SampleStream> r1 = r4.sampleStreams
                r2 = r7[r0]
                r1.remove(r2)
                r1 = 0
                r7[r0] = r1
            L_0x001e:
                r1 = r7[r0]
                if (r1 != 0) goto L_0x003a
                r1 = r5[r0]
                if (r1 == 0) goto L_0x003a
                com.google.android.exoplayer2.source.SilenceMediaSource$SilenceSampleStream r1 = new com.google.android.exoplayer2.source.SilenceMediaSource$SilenceSampleStream
                long r2 = r4.durationUs
                r1.<init>(r2)
                r1.seekTo(r9)
                java.util.ArrayList<com.google.android.exoplayer2.source.SampleStream> r2 = r4.sampleStreams
                r2.add(r1)
                r7[r0] = r1
                r1 = 1
                r8[r0] = r1
            L_0x003a:
                int r0 = r0 + 1
                goto L_0x0005
            L_0x003d:
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.SilenceMediaSource.SilenceMediaPeriod.selectTracks(com.google.android.exoplayer2.trackselection.TrackSelection[], boolean[], com.google.android.exoplayer2.source.SampleStream[], boolean[], long):long");
        }

        public long seekToUs(long j) {
            long constrainSeekPosition = constrainSeekPosition(j);
            for (int i = 0; i < this.sampleStreams.size(); i++) {
                ((SilenceSampleStream) this.sampleStreams.get(i)).seekTo(constrainSeekPosition);
            }
            return constrainSeekPosition;
        }

        public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
            return constrainSeekPosition(j);
        }

        private long constrainSeekPosition(long j) {
            return Util.constrainValue(j, 0, this.durationUs);
        }
    }

    private static final class SilenceSampleStream implements SampleStream {
        private final long durationBytes;
        private long positionBytes;
        private boolean sentFormat;

        public boolean isReady() {
            return true;
        }

        public void maybeThrowError() {
        }

        public SilenceSampleStream(long j) {
            this.durationBytes = SilenceMediaSource.getAudioByteCount(j);
            seekTo(0);
        }

        public void seekTo(long j) {
            this.positionBytes = Util.constrainValue(SilenceMediaSource.getAudioByteCount(j), 0, this.durationBytes);
        }

        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, boolean z) {
            if (!this.sentFormat || z) {
                formatHolder.format = SilenceMediaSource.FORMAT;
                this.sentFormat = true;
                return -5;
            }
            long j = this.durationBytes - this.positionBytes;
            if (j == 0) {
                decoderInputBuffer.addFlag(4);
                return -4;
            }
            int min = (int) Math.min((long) SilenceMediaSource.SILENCE_SAMPLE.length, j);
            decoderInputBuffer.ensureSpaceForWrite(min);
            decoderInputBuffer.data.put(SilenceMediaSource.SILENCE_SAMPLE, 0, min);
            decoderInputBuffer.timeUs = SilenceMediaSource.getAudioPositionUs(this.positionBytes);
            decoderInputBuffer.addFlag(1);
            this.positionBytes += (long) min;
            return -4;
        }

        public int skipData(long j) {
            long j2 = this.positionBytes;
            seekTo(j);
            return (int) ((this.positionBytes - j2) / ((long) SilenceMediaSource.SILENCE_SAMPLE.length));
        }
    }

    public void maybeThrowSourceInfoRefreshError() {
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
    }

    /* access modifiers changed from: protected */
    public void releaseSourceInternal() {
    }

    public SilenceMediaSource(long j) {
        Assertions.checkArgument(j >= 0);
        this.durationUs = j;
    }

    /* access modifiers changed from: protected */
    public void prepareSourceInternal(TransferListener transferListener) {
        SinglePeriodTimeline singlePeriodTimeline = new SinglePeriodTimeline(this.durationUs, true, false, false);
        refreshSourceInfo(singlePeriodTimeline);
    }

    public MediaPeriod createPeriod(MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
        return new SilenceMediaPeriod(this.durationUs);
    }

    /* access modifiers changed from: private */
    public static long getAudioByteCount(long j) {
        return ((long) Util.getPcmFrameSize(2, 2)) * ((j * 44100) / 1000000);
    }

    /* access modifiers changed from: private */
    public static long getAudioPositionUs(long j) {
        return ((j / ((long) Util.getPcmFrameSize(2, 2))) * 1000000) / 44100;
    }
}

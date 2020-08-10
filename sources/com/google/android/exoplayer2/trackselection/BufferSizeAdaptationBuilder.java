package com.google.android.exoplayer2.trackselection;

import android.util.Pair;
import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.DefaultLoadControl.Builder;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.trackselection.TrackSelection.Definition;
import com.google.android.exoplayer2.trackselection.TrackSelection.Factory;
import com.google.android.exoplayer2.trackselection.TrackSelectionUtil.AdaptiveTrackSelectionFactory;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import java.util.List;

public final class BufferSizeAdaptationBuilder {
    public static final int DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS = 5000;
    public static final int DEFAULT_BUFFER_FOR_PLAYBACK_MS = 2500;
    public static final int DEFAULT_HYSTERESIS_BUFFER_MS = 5000;
    public static final int DEFAULT_MAX_BUFFER_MS = 50000;
    public static final int DEFAULT_MIN_BUFFER_MS = 15000;
    public static final float DEFAULT_START_UP_BANDWIDTH_FRACTION = 0.7f;
    public static final int DEFAULT_START_UP_MIN_BUFFER_FOR_QUALITY_INCREASE_MS = 10000;
    private DefaultAllocator allocator;
    private int bufferForPlaybackAfterRebufferMs = 5000;
    private int bufferForPlaybackMs = 2500;
    private boolean buildCalled;
    /* access modifiers changed from: private */
    public Clock clock = Clock.DEFAULT;
    /* access modifiers changed from: private */
    public DynamicFormatFilter dynamicFormatFilter = DynamicFormatFilter.NO_FILTER;
    /* access modifiers changed from: private */
    public int hysteresisBufferMs = 5000;
    /* access modifiers changed from: private */
    public int maxBufferMs = 50000;
    /* access modifiers changed from: private */
    public int minBufferMs = 15000;
    /* access modifiers changed from: private */
    public float startUpBandwidthFraction = 0.7f;
    /* access modifiers changed from: private */
    public int startUpMinBufferForQualityIncreaseMs = 10000;

    private static final class BufferSizeAdaptiveTrackSelection extends BaseTrackSelection {
        private static final int BITRATE_BLACKLISTED = -1;
        private final BandwidthMeter bandwidthMeter;
        private final double bitrateToBufferFunctionIntercept;
        private final double bitrateToBufferFunctionSlope;
        private final Clock clock;
        private final DynamicFormatFilter dynamicFormatFilter;
        private final int[] formatBitrates;
        private final long hysteresisBufferUs;
        private boolean isInSteadyState;
        private final int maxBitrate;
        private final long maxBufferUs;
        private final int minBitrate;
        private final long minBufferUs;
        private float playbackSpeed;
        private int selectedIndex;
        private int selectionReason;
        private final float startUpBandwidthFraction;
        private final long startUpMinBufferForQualityIncreaseUs;

        private static long getCurrentPeriodBufferedDurationUs(long j, long j2) {
            return j >= 0 ? j2 : j2 + j;
        }

        public Object getSelectionData() {
            return null;
        }

        private BufferSizeAdaptiveTrackSelection(TrackGroup trackGroup, int[] iArr, BandwidthMeter bandwidthMeter2, int i, int i2, int i3, float f, int i4, DynamicFormatFilter dynamicFormatFilter2, Clock clock2) {
            super(trackGroup, iArr);
            this.bandwidthMeter = bandwidthMeter2;
            this.minBufferUs = C1996C.msToUs((long) i);
            this.maxBufferUs = C1996C.msToUs((long) i2);
            this.hysteresisBufferUs = C1996C.msToUs((long) i3);
            this.startUpBandwidthFraction = f;
            this.startUpMinBufferForQualityIncreaseUs = C1996C.msToUs((long) i4);
            this.dynamicFormatFilter = dynamicFormatFilter2;
            this.clock = clock2;
            this.formatBitrates = new int[this.length];
            this.maxBitrate = getFormat(0).bitrate;
            this.minBitrate = getFormat(this.length - 1).bitrate;
            this.selectionReason = 0;
            this.playbackSpeed = 1.0f;
            double d = (double) ((this.maxBufferUs - this.hysteresisBufferUs) - this.minBufferUs);
            double d2 = (double) this.maxBitrate;
            double d3 = (double) this.minBitrate;
            Double.isNaN(d2);
            Double.isNaN(d3);
            double log = Math.log(d2 / d3);
            Double.isNaN(d);
            this.bitrateToBufferFunctionSlope = d / log;
            double d4 = (double) this.minBufferUs;
            double log2 = this.bitrateToBufferFunctionSlope * Math.log((double) this.minBitrate);
            Double.isNaN(d4);
            this.bitrateToBufferFunctionIntercept = d4 - log2;
        }

        public void onPlaybackSpeed(float f) {
            this.playbackSpeed = f;
        }

        public void onDiscontinuity() {
            this.isInSteadyState = false;
        }

        public int getSelectedIndex() {
            return this.selectedIndex;
        }

        public int getSelectionReason() {
            return this.selectionReason;
        }

        public void updateSelectedTrack(long j, long j2, long j3, List<? extends MediaChunk> list, MediaChunkIterator[] mediaChunkIteratorArr) {
            updateFormatBitrates(this.clock.elapsedRealtime());
            if (this.selectionReason == 0) {
                this.selectionReason = 1;
                this.selectedIndex = selectIdealIndexUsingBandwidth(true);
                return;
            }
            long currentPeriodBufferedDurationUs = getCurrentPeriodBufferedDurationUs(j, j2);
            int i = this.selectedIndex;
            if (this.isInSteadyState) {
                selectIndexSteadyState(currentPeriodBufferedDurationUs);
            } else {
                selectIndexStartUpPhase(currentPeriodBufferedDurationUs);
            }
            if (this.selectedIndex != i) {
                this.selectionReason = 3;
            }
        }

        private void selectIndexSteadyState(long j) {
            if (isOutsideHysteresis(j)) {
                this.selectedIndex = selectIdealIndexUsingBufferSize(j);
            }
        }

        private boolean isOutsideHysteresis(long j) {
            int[] iArr = this.formatBitrates;
            int i = this.selectedIndex;
            boolean z = true;
            if (iArr[i] == -1) {
                return true;
            }
            if (Math.abs(j - getTargetBufferForBitrateUs(iArr[i])) <= this.hysteresisBufferUs) {
                z = false;
            }
            return z;
        }

        private int selectIdealIndexUsingBufferSize(long j) {
            int i = 0;
            int i2 = 0;
            while (true) {
                int[] iArr = this.formatBitrates;
                if (i >= iArr.length) {
                    return i2;
                }
                if (iArr[i] != -1) {
                    if (getTargetBufferForBitrateUs(iArr[i]) <= j && this.dynamicFormatFilter.isFormatAllowed(getFormat(i), this.formatBitrates[i], false)) {
                        return i;
                    }
                    i2 = i;
                }
                i++;
            }
        }

        private void selectIndexStartUpPhase(long j) {
            int selectIdealIndexUsingBandwidth = selectIdealIndexUsingBandwidth(false);
            int selectIdealIndexUsingBufferSize = selectIdealIndexUsingBufferSize(j);
            int i = this.selectedIndex;
            if (selectIdealIndexUsingBufferSize <= i) {
                this.selectedIndex = selectIdealIndexUsingBufferSize;
                this.isInSteadyState = true;
            } else if (j >= this.startUpMinBufferForQualityIncreaseUs || selectIdealIndexUsingBandwidth >= i || this.formatBitrates[i] == -1) {
                this.selectedIndex = selectIdealIndexUsingBandwidth;
            }
        }

        private int selectIdealIndexUsingBandwidth(boolean z) {
            long bitrateEstimate = (long) (((float) this.bandwidthMeter.getBitrateEstimate()) * this.startUpBandwidthFraction);
            int i = 0;
            int i2 = 0;
            while (true) {
                int[] iArr = this.formatBitrates;
                if (i >= iArr.length) {
                    return i2;
                }
                if (iArr[i] != -1) {
                    if (((long) Math.round(((float) iArr[i]) * this.playbackSpeed)) <= bitrateEstimate && this.dynamicFormatFilter.isFormatAllowed(getFormat(i), this.formatBitrates[i], z)) {
                        return i;
                    }
                    i2 = i;
                }
                i++;
            }
        }

        private void updateFormatBitrates(long j) {
            for (int i = 0; i < this.length; i++) {
                if (j == Long.MIN_VALUE || !isBlacklisted(i, j)) {
                    this.formatBitrates[i] = getFormat(i).bitrate;
                } else {
                    this.formatBitrates[i] = -1;
                }
            }
        }

        private long getTargetBufferForBitrateUs(int i) {
            if (i <= this.minBitrate) {
                return this.minBufferUs;
            }
            if (i >= this.maxBitrate) {
                return this.maxBufferUs - this.hysteresisBufferUs;
            }
            return (long) ((int) ((this.bitrateToBufferFunctionSlope * Math.log((double) i)) + this.bitrateToBufferFunctionIntercept));
        }
    }

    public interface DynamicFormatFilter {
        public static final DynamicFormatFilter NO_FILTER = C2473xcbf588bc.INSTANCE;

        /* renamed from: com.google.android.exoplayer2.trackselection.BufferSizeAdaptationBuilder$DynamicFormatFilter$-CC reason: invalid class name */
        public final /* synthetic */ class CC {
            public static /* synthetic */ boolean lambda$static$0(Format format, int i, boolean z) {
                return true;
            }
        }

        boolean isFormatAllowed(Format format, int i, boolean z);
    }

    public BufferSizeAdaptationBuilder setClock(Clock clock2) {
        Assertions.checkState(!this.buildCalled);
        this.clock = clock2;
        return this;
    }

    public BufferSizeAdaptationBuilder setAllocator(DefaultAllocator defaultAllocator) {
        Assertions.checkState(!this.buildCalled);
        this.allocator = defaultAllocator;
        return this;
    }

    public BufferSizeAdaptationBuilder setBufferDurationsMs(int i, int i2, int i3, int i4) {
        Assertions.checkState(!this.buildCalled);
        this.minBufferMs = i;
        this.maxBufferMs = i2;
        this.bufferForPlaybackMs = i3;
        this.bufferForPlaybackAfterRebufferMs = i4;
        return this;
    }

    public BufferSizeAdaptationBuilder setHysteresisBufferMs(int i) {
        Assertions.checkState(!this.buildCalled);
        this.hysteresisBufferMs = i;
        return this;
    }

    public BufferSizeAdaptationBuilder setStartUpTrackSelectionParameters(float f, int i) {
        Assertions.checkState(!this.buildCalled);
        this.startUpBandwidthFraction = f;
        this.startUpMinBufferForQualityIncreaseMs = i;
        return this;
    }

    public BufferSizeAdaptationBuilder setDynamicFormatFilter(DynamicFormatFilter dynamicFormatFilter2) {
        Assertions.checkState(!this.buildCalled);
        this.dynamicFormatFilter = dynamicFormatFilter2;
        return this;
    }

    public Pair<Factory, LoadControl> buildPlayerComponents() {
        Assertions.checkArgument(this.hysteresisBufferMs < this.maxBufferMs - this.minBufferMs);
        Assertions.checkState(!this.buildCalled);
        this.buildCalled = true;
        Builder targetBufferBytes = new Builder().setTargetBufferBytes(Integer.MAX_VALUE);
        int i = this.maxBufferMs;
        Builder bufferDurationsMs = targetBufferBytes.setBufferDurationsMs(i, i, this.bufferForPlaybackMs, this.bufferForPlaybackAfterRebufferMs);
        DefaultAllocator defaultAllocator = this.allocator;
        if (defaultAllocator != null) {
            bufferDurationsMs.setAllocator(defaultAllocator);
        }
        return Pair.create(new Factory() {
            public TrackSelection[] createTrackSelections(Definition[] definitionArr, BandwidthMeter bandwidthMeter) {
                return TrackSelectionUtil.createTrackSelectionsForDefinitions(definitionArr, new AdaptiveTrackSelectionFactory(bandwidthMeter) {
                    private final /* synthetic */ BandwidthMeter f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final TrackSelection createAdaptiveTrackSelection(Definition definition) {
                        return C24781.this.lambda$createTrackSelections$0$BufferSizeAdaptationBuilder$1(this.f$1, definition);
                    }
                });
            }

            public /* synthetic */ TrackSelection lambda$createTrackSelections$0$BufferSizeAdaptationBuilder$1(BandwidthMeter bandwidthMeter, Definition definition) {
                BufferSizeAdaptiveTrackSelection bufferSizeAdaptiveTrackSelection = new BufferSizeAdaptiveTrackSelection(definition.group, definition.tracks, bandwidthMeter, BufferSizeAdaptationBuilder.this.minBufferMs, BufferSizeAdaptationBuilder.this.maxBufferMs, BufferSizeAdaptationBuilder.this.hysteresisBufferMs, BufferSizeAdaptationBuilder.this.startUpBandwidthFraction, BufferSizeAdaptationBuilder.this.startUpMinBufferForQualityIncreaseMs, BufferSizeAdaptationBuilder.this.dynamicFormatFilter, BufferSizeAdaptationBuilder.this.clock);
                return bufferSizeAdaptiveTrackSelection;
            }
        }, bufferDurationsMs.createDefaultLoadControl());
    }
}

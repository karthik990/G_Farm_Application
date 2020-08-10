package com.google.android.exoplayer2.trackselection;

import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.trackselection.FixedTrackSelection.Factory;
import com.google.android.exoplayer2.trackselection.TrackSelection.Definition;
import com.google.android.exoplayer2.trackselection.TrackSelectionUtil.AdaptiveTrackSelectionFactory;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import java.util.List;

public final class FixedTrackSelection extends BaseTrackSelection {
    private final Object data;
    private final int reason;

    @Deprecated
    public static final class Factory implements com.google.android.exoplayer2.trackselection.TrackSelection.Factory {
        private final Object data;
        private final int reason;

        public Factory() {
            this.reason = 0;
            this.data = null;
        }

        public Factory(int i, Object obj) {
            this.reason = i;
            this.data = obj;
        }

        public TrackSelection[] createTrackSelections(Definition[] definitionArr, BandwidthMeter bandwidthMeter) {
            return TrackSelectionUtil.createTrackSelectionsForDefinitions(definitionArr, new AdaptiveTrackSelectionFactory() {
                public final TrackSelection createAdaptiveTrackSelection(Definition definition) {
                    return Factory.this.lambda$createTrackSelections$0$FixedTrackSelection$Factory(definition);
                }
            });
        }

        public /* synthetic */ TrackSelection lambda$createTrackSelections$0$FixedTrackSelection$Factory(Definition definition) {
            return new FixedTrackSelection(definition.group, definition.tracks[0], this.reason, this.data);
        }
    }

    public int getSelectedIndex() {
        return 0;
    }

    public void updateSelectedTrack(long j, long j2, long j3, List<? extends MediaChunk> list, MediaChunkIterator[] mediaChunkIteratorArr) {
    }

    public FixedTrackSelection(TrackGroup trackGroup, int i) {
        this(trackGroup, i, 0, null);
    }

    public FixedTrackSelection(TrackGroup trackGroup, int i, int i2, Object obj) {
        super(trackGroup, i);
        this.reason = i2;
        this.data = obj;
    }

    public int getSelectionReason() {
        return this.reason;
    }

    public Object getSelectionData() {
        return this.data;
    }
}

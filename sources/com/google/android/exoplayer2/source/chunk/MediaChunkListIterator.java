package com.google.android.exoplayer2.source.chunk;

import com.google.android.exoplayer2.upstream.DataSpec;
import java.util.List;

public final class MediaChunkListIterator extends BaseMediaChunkIterator {
    private final List<? extends MediaChunk> chunks;
    private final boolean reverseOrder;

    public MediaChunkListIterator(List<? extends MediaChunk> list, boolean z) {
        super(0, (long) (list.size() - 1));
        this.chunks = list;
        this.reverseOrder = z;
    }

    public DataSpec getDataSpec() {
        return getCurrentChunk().dataSpec;
    }

    public long getChunkStartTimeUs() {
        return getCurrentChunk().startTimeUs;
    }

    public long getChunkEndTimeUs() {
        return getCurrentChunk().endTimeUs;
    }

    private MediaChunk getCurrentChunk() {
        int currentIndex = (int) super.getCurrentIndex();
        if (this.reverseOrder) {
            currentIndex = (this.chunks.size() - 1) - currentIndex;
        }
        return (MediaChunk) this.chunks.get(currentIndex);
    }
}

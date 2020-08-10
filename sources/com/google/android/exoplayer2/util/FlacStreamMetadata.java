package com.google.android.exoplayer2.util;

import android.support.p009v4.media.session.PlaybackStateCompat;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.Metadata.Entry;
import com.google.android.exoplayer2.metadata.flac.PictureFrame;
import com.google.android.exoplayer2.metadata.flac.VorbisComment;
import java.util.ArrayList;
import java.util.List;

public final class FlacStreamMetadata {
    private static final String SEPARATOR = "=";
    private static final String TAG = "FlacStreamMetadata";
    public final int bitsPerSample;
    public final int channels;
    public final int maxBlockSize;
    public final int maxFrameSize;
    public final Metadata metadata;
    public final int minBlockSize;
    public final int minFrameSize;
    public final int sampleRate;
    public final long totalSamples;

    public FlacStreamMetadata(byte[] bArr, int i) {
        ParsableBitArray parsableBitArray = new ParsableBitArray(bArr);
        parsableBitArray.setPosition(i * 8);
        this.minBlockSize = parsableBitArray.readBits(16);
        this.maxBlockSize = parsableBitArray.readBits(16);
        this.minFrameSize = parsableBitArray.readBits(24);
        this.maxFrameSize = parsableBitArray.readBits(24);
        this.sampleRate = parsableBitArray.readBits(20);
        this.channels = parsableBitArray.readBits(3) + 1;
        this.bitsPerSample = parsableBitArray.readBits(5) + 1;
        this.totalSamples = ((((long) parsableBitArray.readBits(4)) & 15) << 32) | (((long) parsableBitArray.readBits(32)) & 4294967295L);
        this.metadata = null;
    }

    public FlacStreamMetadata(int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, List<String> list, List<PictureFrame> list2) {
        this.minBlockSize = i;
        this.maxBlockSize = i2;
        this.minFrameSize = i3;
        this.maxFrameSize = i4;
        this.sampleRate = i5;
        this.channels = i6;
        this.bitsPerSample = i7;
        this.totalSamples = j;
        this.metadata = buildMetadata(list, list2);
    }

    public int maxDecodedFrameSize() {
        return this.maxBlockSize * this.channels * (this.bitsPerSample / 8);
    }

    public int bitRate() {
        return this.bitsPerSample * this.sampleRate * this.channels;
    }

    public long durationUs() {
        return (this.totalSamples * 1000000) / ((long) this.sampleRate);
    }

    public long getSampleIndex(long j) {
        return Util.constrainValue((j * ((long) this.sampleRate)) / 1000000, 0, this.totalSamples - 1);
    }

    public long getApproxBytesPerFrame() {
        long j;
        long j2;
        int i = this.maxFrameSize;
        if (i > 0) {
            j = (((long) i) + ((long) this.minFrameSize)) / 2;
            j2 = 1;
        } else {
            int i2 = this.minBlockSize;
            j = ((((i2 != this.maxBlockSize || i2 <= 0) ? PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM : (long) i2) * ((long) this.channels)) * ((long) this.bitsPerSample)) / 8;
            j2 = 64;
        }
        return j + j2;
    }

    private static Metadata buildMetadata(List<String> list, List<PictureFrame> list2) {
        Metadata metadata2 = null;
        if (list.isEmpty() && list2.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            String str = (String) list.get(i);
            String[] splitAtFirst = Util.splitAtFirst(str, SEPARATOR);
            if (splitAtFirst.length != 2) {
                StringBuilder sb = new StringBuilder();
                sb.append("Failed to parse vorbis comment: ");
                sb.append(str);
                Log.m1396w(TAG, sb.toString());
            } else {
                arrayList.add(new VorbisComment(splitAtFirst[0], splitAtFirst[1]));
            }
        }
        arrayList.addAll(list2);
        if (!arrayList.isEmpty()) {
            metadata2 = new Metadata((List<? extends Entry>) arrayList);
        }
        return metadata2;
    }
}

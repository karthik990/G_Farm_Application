package com.google.android.exoplayer2.extractor.wav;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import java.io.IOException;

public final class WavExtractor implements Extractor {
    public static final ExtractorsFactory FACTORY = $$Lambda$WavExtractor$5r6M_S0QCNNj_Xavzq9WwuFHep0.INSTANCE;
    private static final int MAX_INPUT_SIZE = 32768;
    private int bytesPerFrame;
    private ExtractorOutput extractorOutput;
    private int pendingBytes;
    private TrackOutput trackOutput;
    private WavHeader wavHeader;

    public void release() {
    }

    static /* synthetic */ Extractor[] lambda$static$0() {
        return new Extractor[]{new WavExtractor()};
    }

    public boolean sniff(ExtractorInput extractorInput) throws IOException, InterruptedException {
        return WavHeaderReader.peek(extractorInput) != null;
    }

    public void init(ExtractorOutput extractorOutput2) {
        this.extractorOutput = extractorOutput2;
        this.trackOutput = extractorOutput2.track(0, 1);
        this.wavHeader = null;
        extractorOutput2.endTracks();
    }

    public void seek(long j, long j2) {
        this.pendingBytes = 0;
    }

    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException, InterruptedException {
        ExtractorInput extractorInput2 = extractorInput;
        if (this.wavHeader == null) {
            this.wavHeader = WavHeaderReader.peek(extractorInput);
            WavHeader wavHeader2 = this.wavHeader;
            if (wavHeader2 != null) {
                this.trackOutput.format(Format.createAudioSampleFormat(null, MimeTypes.AUDIO_RAW, null, wavHeader2.getBitrate(), 32768, this.wavHeader.getNumChannels(), this.wavHeader.getSampleRateHz(), this.wavHeader.getEncoding(), null, null, 0, null));
                this.bytesPerFrame = this.wavHeader.getBytesPerFrame();
            } else {
                throw new ParserException("Unsupported or unrecognized wav header.");
            }
        }
        if (!this.wavHeader.hasDataBounds()) {
            WavHeaderReader.skipToData(extractorInput2, this.wavHeader);
            this.extractorOutput.seekMap(this.wavHeader);
        } else if (extractorInput.getPosition() == 0) {
            extractorInput2.skipFully(this.wavHeader.getDataStartPosition());
        }
        long dataEndPosition = this.wavHeader.getDataEndPosition();
        Assertions.checkState(dataEndPosition != -1);
        long position = dataEndPosition - extractorInput.getPosition();
        int i = -1;
        if (position <= 0) {
            return -1;
        }
        int sampleData = this.trackOutput.sampleData(extractorInput2, (int) Math.min((long) (32768 - this.pendingBytes), position), true);
        if (sampleData != -1) {
            this.pendingBytes += sampleData;
        }
        int i2 = this.pendingBytes / this.bytesPerFrame;
        if (i2 > 0) {
            long timeUs = this.wavHeader.getTimeUs(extractorInput.getPosition() - ((long) this.pendingBytes));
            int i3 = i2 * this.bytesPerFrame;
            this.pendingBytes -= i3;
            this.trackOutput.sampleMetadata(timeUs, 1, i3, this.pendingBytes, null);
        }
        if (sampleData != -1) {
            i = 0;
        }
        return i;
    }
}

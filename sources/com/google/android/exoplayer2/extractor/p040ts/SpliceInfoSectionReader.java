package com.google.android.exoplayer2.extractor.p040ts;

import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.p040ts.TsPayloadReader.TrackIdGenerator;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;

/* renamed from: com.google.android.exoplayer2.extractor.ts.SpliceInfoSectionReader */
public final class SpliceInfoSectionReader implements SectionPayloadReader {
    private boolean formatDeclared;
    private TrackOutput output;
    private TimestampAdjuster timestampAdjuster;

    public void init(TimestampAdjuster timestampAdjuster2, ExtractorOutput extractorOutput, TrackIdGenerator trackIdGenerator) {
        this.timestampAdjuster = timestampAdjuster2;
        trackIdGenerator.generateNewId();
        this.output = extractorOutput.track(trackIdGenerator.getTrackId(), 4);
        this.output.format(Format.createSampleFormat(trackIdGenerator.getFormatId(), MimeTypes.APPLICATION_SCTE35, null, -1, null));
    }

    public void consume(ParsableByteArray parsableByteArray) {
        if (!this.formatDeclared) {
            if (this.timestampAdjuster.getTimestampOffsetUs() != C1996C.TIME_UNSET) {
                this.output.format(Format.createSampleFormat(null, MimeTypes.APPLICATION_SCTE35, this.timestampAdjuster.getTimestampOffsetUs()));
                this.formatDeclared = true;
            } else {
                return;
            }
        }
        int bytesLeft = parsableByteArray.bytesLeft();
        this.output.sampleData(parsableByteArray, bytesLeft);
        this.output.sampleMetadata(this.timestampAdjuster.getLastAdjustedTimestampUs(), 1, bytesLeft, 0, null);
    }
}

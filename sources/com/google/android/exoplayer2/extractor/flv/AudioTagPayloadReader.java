package com.google.android.exoplayer2.extractor.flv;

import android.util.Pair;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.flv.TagPayloadReader.UnsupportedFormatException;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.Collections;

final class AudioTagPayloadReader extends TagPayloadReader {
    private static final int AAC_PACKET_TYPE_AAC_RAW = 1;
    private static final int AAC_PACKET_TYPE_SEQUENCE_HEADER = 0;
    private static final int AUDIO_FORMAT_AAC = 10;
    private static final int AUDIO_FORMAT_ALAW = 7;
    private static final int AUDIO_FORMAT_MP3 = 2;
    private static final int AUDIO_FORMAT_ULAW = 8;
    private static final int[] AUDIO_SAMPLING_RATE_TABLE = {5512, 11025, 22050, 44100};
    private int audioFormat;
    private boolean hasOutputFormat;
    private boolean hasParsedAudioDataHeader;

    public void seek() {
    }

    public AudioTagPayloadReader(TrackOutput trackOutput) {
        super(trackOutput);
    }

    /* access modifiers changed from: protected */
    public boolean parseHeader(ParsableByteArray parsableByteArray) throws UnsupportedFormatException {
        if (!this.hasParsedAudioDataHeader) {
            int readUnsignedByte = parsableByteArray.readUnsignedByte();
            this.audioFormat = (readUnsignedByte >> 4) & 15;
            int i = this.audioFormat;
            if (i == 2) {
                this.output.format(Format.createAudioSampleFormat(null, MimeTypes.AUDIO_MPEG, null, -1, -1, 1, AUDIO_SAMPLING_RATE_TABLE[(readUnsignedByte >> 2) & 3], null, null, 0, null));
                this.hasOutputFormat = true;
            } else if (i == 7 || i == 8) {
                this.output.format(Format.createAudioSampleFormat(null, this.audioFormat == 7 ? MimeTypes.AUDIO_ALAW : MimeTypes.AUDIO_MLAW, null, -1, -1, 1, 8000, (readUnsignedByte & 1) == 1 ? 2 : 3, null, null, 0, null));
                this.hasOutputFormat = true;
            } else if (i != 10) {
                StringBuilder sb = new StringBuilder();
                sb.append("Audio format not supported: ");
                sb.append(this.audioFormat);
                throw new UnsupportedFormatException(sb.toString());
            }
            this.hasParsedAudioDataHeader = true;
        } else {
            parsableByteArray.skipBytes(1);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean parsePayload(ParsableByteArray parsableByteArray, long j) throws ParserException {
        ParsableByteArray parsableByteArray2 = parsableByteArray;
        if (this.audioFormat == 2) {
            int bytesLeft = parsableByteArray.bytesLeft();
            this.output.sampleData(parsableByteArray2, bytesLeft);
            this.output.sampleMetadata(j, 1, bytesLeft, 0, null);
            return true;
        }
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        if (readUnsignedByte == 0 && !this.hasOutputFormat) {
            byte[] bArr = new byte[parsableByteArray.bytesLeft()];
            parsableByteArray2.readBytes(bArr, 0, bArr.length);
            Pair parseAacAudioSpecificConfig = CodecSpecificDataUtil.parseAacAudioSpecificConfig(bArr);
            this.output.format(Format.createAudioSampleFormat(null, MimeTypes.AUDIO_AAC, null, -1, -1, ((Integer) parseAacAudioSpecificConfig.second).intValue(), ((Integer) parseAacAudioSpecificConfig.first).intValue(), Collections.singletonList(bArr), null, 0, null));
            this.hasOutputFormat = true;
            return false;
        } else if (this.audioFormat == 10 && readUnsignedByte != 1) {
            return false;
        } else {
            int bytesLeft2 = parsableByteArray.bytesLeft();
            this.output.sampleData(parsableByteArray2, bytesLeft2);
            this.output.sampleMetadata(j, 1, bytesLeft2, 0, null);
            return true;
        }
    }
}

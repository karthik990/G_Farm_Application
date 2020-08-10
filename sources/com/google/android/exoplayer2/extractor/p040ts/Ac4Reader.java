package com.google.android.exoplayer2.extractor.p040ts;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.p040ts.TsPayloadReader.TrackIdGenerator;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;

/* renamed from: com.google.android.exoplayer2.extractor.ts.Ac4Reader */
public final class Ac4Reader implements ElementaryStreamReader {
    private static final int STATE_FINDING_SYNC = 0;
    private static final int STATE_READING_HEADER = 1;
    private static final int STATE_READING_SAMPLE = 2;
    private int bytesRead;
    private Format format;
    private boolean hasCRC;
    private final ParsableBitArray headerScratchBits;
    private final ParsableByteArray headerScratchBytes;
    private final String language;
    private boolean lastByteWasAC;
    private TrackOutput output;
    private long sampleDurationUs;
    private int sampleSize;
    private int state;
    private long timeUs;
    private String trackFormatId;

    public void packetFinished() {
    }

    public Ac4Reader() {
        this(null);
    }

    public Ac4Reader(String str) {
        this.headerScratchBits = new ParsableBitArray(new byte[16]);
        this.headerScratchBytes = new ParsableByteArray(this.headerScratchBits.data);
        this.state = 0;
        this.bytesRead = 0;
        this.lastByteWasAC = false;
        this.hasCRC = false;
        this.language = str;
    }

    public void seek() {
        this.state = 0;
        this.bytesRead = 0;
        this.lastByteWasAC = false;
        this.hasCRC = false;
    }

    public void createTracks(ExtractorOutput extractorOutput, TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        this.trackFormatId = trackIdGenerator.getFormatId();
        this.output = extractorOutput.track(trackIdGenerator.getTrackId(), 1);
    }

    public void packetStarted(long j, int i) {
        this.timeUs = j;
    }

    public void consume(ParsableByteArray parsableByteArray) {
        while (parsableByteArray.bytesLeft() > 0) {
            int i = this.state;
            if (i != 0) {
                if (i != 1) {
                    if (i == 2) {
                        int min = Math.min(parsableByteArray.bytesLeft(), this.sampleSize - this.bytesRead);
                        this.output.sampleData(parsableByteArray, min);
                        this.bytesRead += min;
                        int i2 = this.bytesRead;
                        int i3 = this.sampleSize;
                        if (i2 == i3) {
                            this.output.sampleMetadata(this.timeUs, 1, i3, 0, null);
                            this.timeUs += this.sampleDurationUs;
                            this.state = 0;
                        }
                    }
                } else if (continueRead(parsableByteArray, this.headerScratchBytes.data, 16)) {
                    parseHeader();
                    this.headerScratchBytes.setPosition(0);
                    this.output.sampleData(this.headerScratchBytes, 16);
                    this.state = 2;
                }
            } else if (skipToNextSync(parsableByteArray)) {
                this.state = 1;
                this.headerScratchBytes.data[0] = -84;
                this.headerScratchBytes.data[1] = (byte) (this.hasCRC ? 65 : 64);
                this.bytesRead = 2;
            }
        }
    }

    private boolean continueRead(ParsableByteArray parsableByteArray, byte[] bArr, int i) {
        int min = Math.min(parsableByteArray.bytesLeft(), i - this.bytesRead);
        parsableByteArray.readBytes(bArr, this.bytesRead, min);
        this.bytesRead += min;
        return this.bytesRead == i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x002d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean skipToNextSync(com.google.android.exoplayer2.util.ParsableByteArray r6) {
        /*
            r5 = this;
        L_0x0000:
            int r0 = r6.bytesLeft()
            r1 = 0
            if (r0 <= 0) goto L_0x0031
            boolean r0 = r5.lastByteWasAC
            r2 = 172(0xac, float:2.41E-43)
            r3 = 1
            if (r0 != 0) goto L_0x0018
            int r0 = r6.readUnsignedByte()
            if (r0 != r2) goto L_0x0015
            r1 = 1
        L_0x0015:
            r5.lastByteWasAC = r1
            goto L_0x0000
        L_0x0018:
            int r0 = r6.readUnsignedByte()
            if (r0 != r2) goto L_0x0020
            r2 = 1
            goto L_0x0021
        L_0x0020:
            r2 = 0
        L_0x0021:
            r5.lastByteWasAC = r2
            r2 = 64
            r4 = 65
            if (r0 == r2) goto L_0x002b
            if (r0 != r4) goto L_0x0000
        L_0x002b:
            if (r0 != r4) goto L_0x002e
            r1 = 1
        L_0x002e:
            r5.hasCRC = r1
            return r3
        L_0x0031:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.p040ts.Ac4Reader.skipToNextSync(com.google.android.exoplayer2.util.ParsableByteArray):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x002a, code lost:
        if (com.google.android.exoplayer2.util.MimeTypes.AUDIO_AC4.equals(r13.format.sampleMimeType) == false) goto L_0x002c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void parseHeader() {
        /*
            r13 = this;
            com.google.android.exoplayer2.util.ParsableBitArray r0 = r13.headerScratchBits
            r1 = 0
            r0.setPosition(r1)
            com.google.android.exoplayer2.util.ParsableBitArray r0 = r13.headerScratchBits
            com.google.android.exoplayer2.audio.Ac4Util$SyncFrameInfo r0 = com.google.android.exoplayer2.audio.Ac4Util.parseAc4SyncframeInfo(r0)
            com.google.android.exoplayer2.Format r1 = r13.format
            if (r1 == 0) goto L_0x002c
            int r1 = r0.channelCount
            com.google.android.exoplayer2.Format r2 = r13.format
            int r2 = r2.channelCount
            if (r1 != r2) goto L_0x002c
            int r1 = r0.sampleRate
            com.google.android.exoplayer2.Format r2 = r13.format
            int r2 = r2.sampleRate
            if (r1 != r2) goto L_0x002c
            com.google.android.exoplayer2.Format r1 = r13.format
            java.lang.String r1 = r1.sampleMimeType
            java.lang.String r2 = "audio/ac4"
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L_0x0049
        L_0x002c:
            java.lang.String r2 = r13.trackFormatId
            r4 = 0
            r5 = -1
            r6 = -1
            int r7 = r0.channelCount
            int r8 = r0.sampleRate
            r9 = 0
            r10 = 0
            r11 = 0
            java.lang.String r12 = r13.language
            java.lang.String r3 = "audio/ac4"
            com.google.android.exoplayer2.Format r1 = com.google.android.exoplayer2.Format.createAudioSampleFormat(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
            r13.format = r1
            com.google.android.exoplayer2.extractor.TrackOutput r1 = r13.output
            com.google.android.exoplayer2.Format r2 = r13.format
            r1.format(r2)
        L_0x0049:
            int r1 = r0.frameSize
            r13.sampleSize = r1
            r1 = 1000000(0xf4240, double:4.940656E-318)
            int r0 = r0.sampleCount
            long r3 = (long) r0
            long r3 = r3 * r1
            com.google.android.exoplayer2.Format r0 = r13.format
            int r0 = r0.sampleRate
            long r0 = (long) r0
            long r3 = r3 / r0
            r13.sampleDurationUs = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.p040ts.Ac4Reader.parseHeader():void");
    }
}

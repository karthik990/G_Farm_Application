package com.google.android.exoplayer2.extractor.mp3;

import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.GaplessInfoHolder;
import com.google.android.exoplayer2.extractor.Id3Peeker;
import com.google.android.exoplayer2.extractor.MpegAudioHeader;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.mp3.Seeker.UnseekableSeeker;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.Metadata.Entry;
import com.google.android.exoplayer2.metadata.id3.Id3Decoder.FramePredicate;
import com.google.android.exoplayer2.metadata.id3.MlltFrame;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.EOFException;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Mp3Extractor implements Extractor {
    public static final ExtractorsFactory FACTORY = $$Lambda$Mp3Extractor$6eyGfoogMVGFHZKg1gVp93FAKZA.INSTANCE;
    public static final int FLAG_DISABLE_ID3_METADATA = 2;
    public static final int FLAG_ENABLE_CONSTANT_BITRATE_SEEKING = 1;
    private static final int MAX_SNIFF_BYTES = 16384;
    private static final int MAX_SYNC_BYTES = 131072;
    private static final int MPEG_AUDIO_HEADER_MASK = -128000;
    private static final FramePredicate REQUIRED_ID3_FRAME_PREDICATE = $$Lambda$Mp3Extractor$bb754AZIAMUosKBF4SefP1vYq88.INSTANCE;
    private static final int SCRATCH_LENGTH = 10;
    private static final int SEEK_HEADER_INFO = 1231971951;
    private static final int SEEK_HEADER_UNSET = 0;
    private static final int SEEK_HEADER_VBRI = 1447187017;
    private static final int SEEK_HEADER_XING = 1483304551;
    private long basisTimeUs;
    private boolean disableSeeking;
    private ExtractorOutput extractorOutput;
    private long firstSamplePosition;
    private final int flags;
    private final long forcedFirstSampleTimestampUs;
    private final GaplessInfoHolder gaplessInfoHolder;
    private final Id3Peeker id3Peeker;
    private Metadata metadata;
    private int sampleBytesRemaining;
    private long samplesRead;
    private final ParsableByteArray scratch;
    private Seeker seeker;
    private final MpegAudioHeader synchronizedHeader;
    private int synchronizedHeaderData;
    private TrackOutput trackOutput;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    private static boolean headersMatch(int i, long j) {
        return ((long) (i & MPEG_AUDIO_HEADER_MASK)) == (j & -128000);
    }

    static /* synthetic */ boolean lambda$static$1(int i, int i2, int i3, int i4, int i5) {
        return (i2 == 67 && i3 == 79 && i4 == 77 && (i5 == 77 || i == 2)) || (i2 == 77 && i3 == 76 && i4 == 76 && (i5 == 84 || i == 2));
    }

    public void release() {
    }

    static /* synthetic */ Extractor[] lambda$static$0() {
        return new Extractor[]{new Mp3Extractor()};
    }

    public Mp3Extractor() {
        this(0);
    }

    public Mp3Extractor(int i) {
        this(i, C1996C.TIME_UNSET);
    }

    public Mp3Extractor(int i, long j) {
        this.flags = i;
        this.forcedFirstSampleTimestampUs = j;
        this.scratch = new ParsableByteArray(10);
        this.synchronizedHeader = new MpegAudioHeader();
        this.gaplessInfoHolder = new GaplessInfoHolder();
        this.basisTimeUs = C1996C.TIME_UNSET;
        this.id3Peeker = new Id3Peeker();
    }

    public boolean sniff(ExtractorInput extractorInput) throws IOException, InterruptedException {
        return synchronize(extractorInput, true);
    }

    public void init(ExtractorOutput extractorOutput2) {
        this.extractorOutput = extractorOutput2;
        this.trackOutput = this.extractorOutput.track(0, 1);
        this.extractorOutput.endTracks();
    }

    public void seek(long j, long j2) {
        this.synchronizedHeaderData = 0;
        this.basisTimeUs = C1996C.TIME_UNSET;
        this.samplesRead = 0;
        this.sampleBytesRemaining = 0;
    }

    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException, InterruptedException {
        ExtractorInput extractorInput2 = extractorInput;
        if (this.synchronizedHeaderData == 0) {
            try {
                synchronize(extractorInput2, false);
            } catch (EOFException unused) {
                return -1;
            }
        }
        if (this.seeker == null) {
            Seeker maybeReadSeekFrame = maybeReadSeekFrame(extractorInput);
            MlltSeeker maybeHandleSeekMetadata = maybeHandleSeekMetadata(this.metadata, extractorInput.getPosition());
            if (this.disableSeeking) {
                this.seeker = new UnseekableSeeker();
            } else {
                if (maybeHandleSeekMetadata != null) {
                    this.seeker = maybeHandleSeekMetadata;
                } else if (maybeReadSeekFrame != null) {
                    this.seeker = maybeReadSeekFrame;
                }
                Seeker seeker2 = this.seeker;
                if (seeker2 == null || (!seeker2.isSeekable() && (this.flags & 1) != 0)) {
                    this.seeker = getConstantBitrateSeeker(extractorInput);
                }
            }
            this.extractorOutput.seekMap(this.seeker);
            this.trackOutput.format(Format.createAudioSampleFormat(null, this.synchronizedHeader.mimeType, null, -1, 4096, this.synchronizedHeader.channels, this.synchronizedHeader.sampleRate, -1, this.gaplessInfoHolder.encoderDelay, this.gaplessInfoHolder.encoderPadding, null, null, 0, null, (this.flags & 2) != 0 ? null : this.metadata));
            this.firstSamplePosition = extractorInput.getPosition();
        } else if (this.firstSamplePosition != 0) {
            long position = extractorInput.getPosition();
            long j = this.firstSamplePosition;
            if (position < j) {
                extractorInput2.skipFully((int) (j - position));
            }
        }
        return readSample(extractorInput);
    }

    public void disableSeeking() {
        this.disableSeeking = true;
    }

    private int readSample(ExtractorInput extractorInput) throws IOException, InterruptedException {
        if (this.sampleBytesRemaining == 0) {
            extractorInput.resetPeekPosition();
            if (peekEndOfStreamOrHeader(extractorInput)) {
                return -1;
            }
            this.scratch.setPosition(0);
            int readInt = this.scratch.readInt();
            if (!headersMatch(readInt, (long) this.synchronizedHeaderData) || MpegAudioHeader.getFrameSize(readInt) == -1) {
                extractorInput.skipFully(1);
                this.synchronizedHeaderData = 0;
                return 0;
            }
            MpegAudioHeader.populateHeader(readInt, this.synchronizedHeader);
            if (this.basisTimeUs == C1996C.TIME_UNSET) {
                this.basisTimeUs = this.seeker.getTimeUs(extractorInput.getPosition());
                if (this.forcedFirstSampleTimestampUs != C1996C.TIME_UNSET) {
                    this.basisTimeUs += this.forcedFirstSampleTimestampUs - this.seeker.getTimeUs(0);
                }
            }
            this.sampleBytesRemaining = this.synchronizedHeader.frameSize;
        }
        int sampleData = this.trackOutput.sampleData(extractorInput, this.sampleBytesRemaining, true);
        if (sampleData == -1) {
            return -1;
        }
        this.sampleBytesRemaining -= sampleData;
        if (this.sampleBytesRemaining > 0) {
            return 0;
        }
        this.trackOutput.sampleMetadata(this.basisTimeUs + ((this.samplesRead * 1000000) / ((long) this.synchronizedHeader.sampleRate)), 1, this.synchronizedHeader.frameSize, 0, null);
        this.samplesRead += (long) this.synchronizedHeader.samplesPerFrame;
        this.sampleBytesRemaining = 0;
        return 0;
    }

    private boolean synchronize(ExtractorInput extractorInput, boolean z) throws IOException, InterruptedException {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        FramePredicate framePredicate;
        int i6 = z ? 16384 : 131072;
        extractorInput.resetPeekPosition();
        if (extractorInput.getPosition() == 0) {
            if ((this.flags & 2) == 0) {
                framePredicate = null;
            } else {
                framePredicate = REQUIRED_ID3_FRAME_PREDICATE;
            }
            this.metadata = this.id3Peeker.peekId3Data(extractorInput, framePredicate);
            Metadata metadata2 = this.metadata;
            if (metadata2 != null) {
                this.gaplessInfoHolder.setFromMetadata(metadata2);
            }
            int peekPosition = (int) extractorInput.getPeekPosition();
            if (!z) {
                extractorInput.skipFully(peekPosition);
            }
            i = peekPosition;
            i4 = 0;
            i3 = 0;
            i2 = 0;
        } else {
            i4 = 0;
            i3 = 0;
            i2 = 0;
            i = 0;
        }
        while (true) {
            if (!peekEndOfStreamOrHeader(extractorInput)) {
                this.scratch.setPosition(0);
                int readInt = this.scratch.readInt();
                if (i4 == 0 || headersMatch(readInt, (long) i4)) {
                    int frameSize = MpegAudioHeader.getFrameSize(readInt);
                    if (frameSize != -1) {
                        i5 = i3 + 1;
                        if (i5 != 1) {
                            if (i5 == 4) {
                                break;
                            }
                        } else {
                            MpegAudioHeader.populateHeader(readInt, this.synchronizedHeader);
                            i4 = readInt;
                        }
                        extractorInput.advancePeekPosition(frameSize - 4);
                    }
                }
                int i7 = i2 + 1;
                if (i2 != i6) {
                    if (z) {
                        extractorInput.resetPeekPosition();
                        extractorInput.advancePeekPosition(i + i7);
                    } else {
                        extractorInput.skipFully(1);
                    }
                    i2 = i7;
                    i4 = 0;
                    i5 = 0;
                } else if (z) {
                    return false;
                } else {
                    throw new ParserException("Searched too many bytes.");
                }
            } else if (i3 <= 0) {
                throw new EOFException();
            }
        }
        if (z) {
            extractorInput.skipFully(i + i2);
        } else {
            extractorInput.resetPeekPosition();
        }
        this.synchronizedHeaderData = i4;
        return true;
    }

    private boolean peekEndOfStreamOrHeader(ExtractorInput extractorInput) throws IOException, InterruptedException {
        Seeker seeker2 = this.seeker;
        if (seeker2 != null) {
            long dataEndPosition = seeker2.getDataEndPosition();
            if (dataEndPosition != -1 && extractorInput.getPeekPosition() > dataEndPosition - 4) {
                return true;
            }
        }
        try {
            return !extractorInput.peekFully(this.scratch.data, 0, 4, true);
        } catch (EOFException unused) {
            return true;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0041 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.google.android.exoplayer2.extractor.mp3.Seeker maybeReadSeekFrame(com.google.android.exoplayer2.extractor.ExtractorInput r11) throws java.io.IOException, java.lang.InterruptedException {
        /*
            r10 = this;
            com.google.android.exoplayer2.util.ParsableByteArray r5 = new com.google.android.exoplayer2.util.ParsableByteArray
            com.google.android.exoplayer2.extractor.MpegAudioHeader r0 = r10.synchronizedHeader
            int r0 = r0.frameSize
            r5.<init>(r0)
            byte[] r0 = r5.data
            com.google.android.exoplayer2.extractor.MpegAudioHeader r1 = r10.synchronizedHeader
            int r1 = r1.frameSize
            r6 = 0
            r11.peekFully(r0, r6, r1)
            com.google.android.exoplayer2.extractor.MpegAudioHeader r0 = r10.synchronizedHeader
            int r0 = r0.version
            r1 = 1
            r0 = r0 & r1
            r2 = 21
            if (r0 == 0) goto L_0x0028
            com.google.android.exoplayer2.extractor.MpegAudioHeader r0 = r10.synchronizedHeader
            int r0 = r0.channels
            if (r0 == r1) goto L_0x002e
            r2 = 36
            r7 = 36
            goto L_0x0035
        L_0x0028:
            com.google.android.exoplayer2.extractor.MpegAudioHeader r0 = r10.synchronizedHeader
            int r0 = r0.channels
            if (r0 == r1) goto L_0x0031
        L_0x002e:
            r7 = 21
            goto L_0x0035
        L_0x0031:
            r2 = 13
            r7 = 13
        L_0x0035:
            int r8 = getSeekFrameHeader(r5, r7)
            r0 = 1483304551(0x58696e67, float:1.02664153E15)
            r9 = 1231971951(0x496e666f, float:976486.94)
            if (r8 == r0) goto L_0x0064
            if (r8 != r9) goto L_0x0044
            goto L_0x0064
        L_0x0044:
            r0 = 1447187017(0x56425249, float:5.3414667E13)
            if (r8 != r0) goto L_0x005f
            long r0 = r11.getLength()
            long r2 = r11.getPosition()
            com.google.android.exoplayer2.extractor.MpegAudioHeader r4 = r10.synchronizedHeader
            com.google.android.exoplayer2.extractor.mp3.VbriSeeker r0 = com.google.android.exoplayer2.extractor.mp3.VbriSeeker.create(r0, r2, r4, r5)
            com.google.android.exoplayer2.extractor.MpegAudioHeader r1 = r10.synchronizedHeader
            int r1 = r1.frameSize
            r11.skipFully(r1)
            goto L_0x00b2
        L_0x005f:
            r0 = 0
            r11.resetPeekPosition()
            goto L_0x00b2
        L_0x0064:
            long r0 = r11.getLength()
            long r2 = r11.getPosition()
            com.google.android.exoplayer2.extractor.MpegAudioHeader r4 = r10.synchronizedHeader
            com.google.android.exoplayer2.extractor.mp3.XingSeeker r0 = com.google.android.exoplayer2.extractor.mp3.XingSeeker.create(r0, r2, r4, r5)
            if (r0 == 0) goto L_0x009c
            com.google.android.exoplayer2.extractor.GaplessInfoHolder r1 = r10.gaplessInfoHolder
            boolean r1 = r1.hasGaplessInfo()
            if (r1 != 0) goto L_0x009c
            r11.resetPeekPosition()
            int r7 = r7 + 141
            r11.advancePeekPosition(r7)
            com.google.android.exoplayer2.util.ParsableByteArray r1 = r10.scratch
            byte[] r1 = r1.data
            r2 = 3
            r11.peekFully(r1, r6, r2)
            com.google.android.exoplayer2.util.ParsableByteArray r1 = r10.scratch
            r1.setPosition(r6)
            com.google.android.exoplayer2.extractor.GaplessInfoHolder r1 = r10.gaplessInfoHolder
            com.google.android.exoplayer2.util.ParsableByteArray r2 = r10.scratch
            int r2 = r2.readUnsignedInt24()
            r1.setFromXingHeaderValue(r2)
        L_0x009c:
            com.google.android.exoplayer2.extractor.MpegAudioHeader r1 = r10.synchronizedHeader
            int r1 = r1.frameSize
            r11.skipFully(r1)
            if (r0 == 0) goto L_0x00b2
            boolean r1 = r0.isSeekable()
            if (r1 != 0) goto L_0x00b2
            if (r8 != r9) goto L_0x00b2
            com.google.android.exoplayer2.extractor.mp3.Seeker r11 = r10.getConstantBitrateSeeker(r11)
            return r11
        L_0x00b2:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mp3.Mp3Extractor.maybeReadSeekFrame(com.google.android.exoplayer2.extractor.ExtractorInput):com.google.android.exoplayer2.extractor.mp3.Seeker");
    }

    private Seeker getConstantBitrateSeeker(ExtractorInput extractorInput) throws IOException, InterruptedException {
        extractorInput.peekFully(this.scratch.data, 0, 4);
        this.scratch.setPosition(0);
        MpegAudioHeader.populateHeader(this.scratch.readInt(), this.synchronizedHeader);
        ConstantBitrateSeeker constantBitrateSeeker = new ConstantBitrateSeeker(extractorInput.getLength(), extractorInput.getPosition(), this.synchronizedHeader);
        return constantBitrateSeeker;
    }

    private static int getSeekFrameHeader(ParsableByteArray parsableByteArray, int i) {
        if (parsableByteArray.limit() >= i + 4) {
            parsableByteArray.setPosition(i);
            int readInt = parsableByteArray.readInt();
            if (readInt == SEEK_HEADER_XING || readInt == SEEK_HEADER_INFO) {
                return readInt;
            }
        }
        if (parsableByteArray.limit() >= 40) {
            parsableByteArray.setPosition(36);
            if (parsableByteArray.readInt() == SEEK_HEADER_VBRI) {
                return SEEK_HEADER_VBRI;
            }
        }
        return 0;
    }

    private static MlltSeeker maybeHandleSeekMetadata(Metadata metadata2, long j) {
        if (metadata2 != null) {
            int length = metadata2.length();
            for (int i = 0; i < length; i++) {
                Entry entry = metadata2.get(i);
                if (entry instanceof MlltFrame) {
                    return MlltSeeker.create(j, (MlltFrame) entry);
                }
            }
        }
        return null;
    }
}

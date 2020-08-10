package com.google.android.exoplayer2.extractor.mp4;

import android.util.Pair;
import android.util.SparseArray;
import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.Ac4Util;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmInitData.SchemeData;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekMap.Unseekable;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.TrackOutput.CryptoData;
import com.google.android.exoplayer2.extractor.p040ts.PsExtractor;
import com.google.android.exoplayer2.metadata.emsg.EventMessage;
import com.google.android.exoplayer2.metadata.emsg.EventMessageEncoder;
import com.google.android.exoplayer2.text.cea.CeaUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class FragmentedMp4Extractor implements Extractor {
    private static final Format EMSG_FORMAT = Format.createSampleFormat(null, MimeTypes.APPLICATION_EMSG, Long.MAX_VALUE);
    public static final ExtractorsFactory FACTORY = $$Lambda$FragmentedMp4Extractor$i0zfpH_PcF0vytkdatCL0xeWFhQ.INSTANCE;
    public static final int FLAG_ENABLE_EMSG_TRACK = 4;
    private static final int FLAG_SIDELOADED = 8;
    public static final int FLAG_WORKAROUND_EVERY_VIDEO_FRAME_IS_SYNC_FRAME = 1;
    public static final int FLAG_WORKAROUND_IGNORE_EDIT_LISTS = 16;
    public static final int FLAG_WORKAROUND_IGNORE_TFDT_BOX = 2;
    private static final byte[] PIFF_SAMPLE_ENCRYPTION_BOX_EXTENDED_TYPE = {-94, 57, 79, 82, 90, -101, 79, Ascii.DC4, -94, 68, 108, 66, 124, 100, -115, -12};
    private static final int SAMPLE_GROUP_TYPE_seig = 1936025959;
    private static final int STATE_READING_ATOM_HEADER = 0;
    private static final int STATE_READING_ATOM_PAYLOAD = 1;
    private static final int STATE_READING_ENCRYPTION_DATA = 2;
    private static final int STATE_READING_SAMPLE_CONTINUE = 4;
    private static final int STATE_READING_SAMPLE_START = 3;
    private static final String TAG = "FragmentedMp4Extractor";
    private final TrackOutput additionalEmsgTrackOutput;
    private ParsableByteArray atomData;
    private final ParsableByteArray atomHeader;
    private int atomHeaderBytesRead;
    private long atomSize;
    private int atomType;
    private TrackOutput[] cea608TrackOutputs;
    private final List<Format> closedCaptionFormats;
    private final ArrayDeque<ContainerAtom> containerAtoms;
    private TrackBundle currentTrackBundle;
    private long durationUs;
    private TrackOutput[] emsgTrackOutputs;
    private long endOfMdatPosition;
    private final EventMessageEncoder eventMessageEncoder;
    private ExtractorOutput extractorOutput;
    private final int flags;
    private boolean haveOutputSeekMap;
    private boolean isAc4HeaderRequired;
    private final ParsableByteArray nalBuffer;
    private final ParsableByteArray nalPrefix;
    private final ParsableByteArray nalStartCode;
    private int parserState;
    private int pendingMetadataSampleBytes;
    private final ArrayDeque<MetadataSampleInfo> pendingMetadataSampleInfos;
    private long pendingSeekTimeUs;
    private boolean processSeiNalUnitPayload;
    private int sampleBytesWritten;
    private int sampleCurrentNalBytesRemaining;
    private int sampleSize;
    private final ParsableByteArray scratch;
    private final byte[] scratchBytes;
    private long segmentIndexEarliestPresentationTimeUs;
    private final DrmInitData sideloadedDrmInitData;
    private final Track sideloadedTrack;
    private final TimestampAdjuster timestampAdjuster;
    private final SparseArray<TrackBundle> trackBundles;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    private static final class MetadataSampleInfo {
        public final long presentationTimeDeltaUs;
        public final int size;

        public MetadataSampleInfo(long j, int i) {
            this.presentationTimeDeltaUs = j;
            this.size = i;
        }
    }

    private static final class TrackBundle {
        public int currentSampleInTrackRun;
        public int currentSampleIndex;
        public int currentTrackRunIndex;
        private final ParsableByteArray defaultInitializationVector = new ParsableByteArray();
        public DefaultSampleValues defaultSampleValues;
        private final ParsableByteArray encryptionSignalByte = new ParsableByteArray(1);
        public int firstSampleToOutputIndex;
        public final TrackFragment fragment = new TrackFragment();
        public final TrackOutput output;
        public Track track;

        public TrackBundle(TrackOutput trackOutput) {
            this.output = trackOutput;
        }

        public void init(Track track2, DefaultSampleValues defaultSampleValues2) {
            this.track = (Track) Assertions.checkNotNull(track2);
            this.defaultSampleValues = (DefaultSampleValues) Assertions.checkNotNull(defaultSampleValues2);
            this.output.format(track2.format);
            reset();
        }

        public void updateDrmInitData(DrmInitData drmInitData) {
            TrackEncryptionBox sampleDescriptionEncryptionBox = this.track.getSampleDescriptionEncryptionBox(this.fragment.header.sampleDescriptionIndex);
            this.output.format(this.track.format.copyWithDrmInitData(drmInitData.copyWithSchemeType(sampleDescriptionEncryptionBox != null ? sampleDescriptionEncryptionBox.schemeType : null)));
        }

        public void reset() {
            this.fragment.reset();
            this.currentSampleIndex = 0;
            this.currentTrackRunIndex = 0;
            this.currentSampleInTrackRun = 0;
            this.firstSampleToOutputIndex = 0;
        }

        public void seek(long j) {
            long usToMs = C1996C.usToMs(j);
            int i = this.currentSampleIndex;
            while (i < this.fragment.sampleCount && this.fragment.getSamplePresentationTime(i) < usToMs) {
                if (this.fragment.sampleIsSyncFrameTable[i]) {
                    this.firstSampleToOutputIndex = i;
                }
                i++;
            }
        }

        public boolean next() {
            this.currentSampleIndex++;
            this.currentSampleInTrackRun++;
            int i = this.currentSampleInTrackRun;
            int[] iArr = this.fragment.trunLength;
            int i2 = this.currentTrackRunIndex;
            if (i != iArr[i2]) {
                return true;
            }
            this.currentTrackRunIndex = i2 + 1;
            this.currentSampleInTrackRun = 0;
            return false;
        }

        public int outputSampleEncryptionData() {
            ParsableByteArray parsableByteArray;
            int i;
            TrackEncryptionBox encryptionBoxIfEncrypted = getEncryptionBoxIfEncrypted();
            if (encryptionBoxIfEncrypted == null) {
                return 0;
            }
            if (encryptionBoxIfEncrypted.perSampleIvSize != 0) {
                parsableByteArray = this.fragment.sampleEncryptionData;
                i = encryptionBoxIfEncrypted.perSampleIvSize;
            } else {
                byte[] bArr = encryptionBoxIfEncrypted.defaultInitializationVector;
                this.defaultInitializationVector.reset(bArr, bArr.length);
                parsableByteArray = this.defaultInitializationVector;
                i = bArr.length;
            }
            boolean sampleHasSubsampleEncryptionTable = this.fragment.sampleHasSubsampleEncryptionTable(this.currentSampleIndex);
            this.encryptionSignalByte.data[0] = (byte) ((sampleHasSubsampleEncryptionTable ? 128 : 0) | i);
            this.encryptionSignalByte.setPosition(0);
            this.output.sampleData(this.encryptionSignalByte, 1);
            this.output.sampleData(parsableByteArray, i);
            if (!sampleHasSubsampleEncryptionTable) {
                return i + 1;
            }
            ParsableByteArray parsableByteArray2 = this.fragment.sampleEncryptionData;
            int readUnsignedShort = parsableByteArray2.readUnsignedShort();
            parsableByteArray2.skipBytes(-2);
            int i2 = (readUnsignedShort * 6) + 2;
            this.output.sampleData(parsableByteArray2, i2);
            return i + 1 + i2;
        }

        /* access modifiers changed from: private */
        public void skipSampleEncryptionData() {
            TrackEncryptionBox encryptionBoxIfEncrypted = getEncryptionBoxIfEncrypted();
            if (encryptionBoxIfEncrypted != null) {
                ParsableByteArray parsableByteArray = this.fragment.sampleEncryptionData;
                if (encryptionBoxIfEncrypted.perSampleIvSize != 0) {
                    parsableByteArray.skipBytes(encryptionBoxIfEncrypted.perSampleIvSize);
                }
                if (this.fragment.sampleHasSubsampleEncryptionTable(this.currentSampleIndex)) {
                    parsableByteArray.skipBytes(parsableByteArray.readUnsignedShort() * 6);
                }
            }
        }

        /* access modifiers changed from: private */
        public TrackEncryptionBox getEncryptionBoxIfEncrypted() {
            TrackEncryptionBox trackEncryptionBox;
            int i = this.fragment.header.sampleDescriptionIndex;
            if (this.fragment.trackEncryptionBox != null) {
                trackEncryptionBox = this.fragment.trackEncryptionBox;
            } else {
                trackEncryptionBox = this.track.getSampleDescriptionEncryptionBox(i);
            }
            if (trackEncryptionBox == null || !trackEncryptionBox.isEncrypted) {
                return null;
            }
            return trackEncryptionBox;
        }
    }

    private static boolean shouldParseContainerAtom(int i) {
        return i == 1836019574 || i == 1953653099 || i == 1835297121 || i == 1835626086 || i == 1937007212 || i == 1836019558 || i == 1953653094 || i == 1836475768 || i == 1701082227;
    }

    private static boolean shouldParseLeafAtom(int i) {
        return i == 1751411826 || i == 1835296868 || i == 1836476516 || i == 1936286840 || i == 1937011556 || i == 1952867444 || i == 1952868452 || i == 1953196132 || i == 1953654136 || i == 1953658222 || i == 1886614376 || i == 1935763834 || i == 1935763823 || i == 1936027235 || i == 1970628964 || i == 1935828848 || i == 1936158820 || i == 1701606260 || i == 1835362404 || i == 1701671783;
    }

    /* access modifiers changed from: protected */
    public Track modifyTrack(Track track) {
        return track;
    }

    public void release() {
    }

    static /* synthetic */ Extractor[] lambda$static$0() {
        return new Extractor[]{new FragmentedMp4Extractor()};
    }

    public FragmentedMp4Extractor() {
        this(0);
    }

    public FragmentedMp4Extractor(int i) {
        this(i, null);
    }

    public FragmentedMp4Extractor(int i, TimestampAdjuster timestampAdjuster2) {
        this(i, timestampAdjuster2, null, null);
    }

    public FragmentedMp4Extractor(int i, TimestampAdjuster timestampAdjuster2, Track track, DrmInitData drmInitData) {
        this(i, timestampAdjuster2, track, drmInitData, Collections.emptyList());
    }

    public FragmentedMp4Extractor(int i, TimestampAdjuster timestampAdjuster2, Track track, DrmInitData drmInitData, List<Format> list) {
        this(i, timestampAdjuster2, track, drmInitData, list, null);
    }

    public FragmentedMp4Extractor(int i, TimestampAdjuster timestampAdjuster2, Track track, DrmInitData drmInitData, List<Format> list, TrackOutput trackOutput) {
        this.flags = i | (track != null ? 8 : 0);
        this.timestampAdjuster = timestampAdjuster2;
        this.sideloadedTrack = track;
        this.sideloadedDrmInitData = drmInitData;
        this.closedCaptionFormats = Collections.unmodifiableList(list);
        this.additionalEmsgTrackOutput = trackOutput;
        this.eventMessageEncoder = new EventMessageEncoder();
        this.atomHeader = new ParsableByteArray(16);
        this.nalStartCode = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
        this.nalPrefix = new ParsableByteArray(5);
        this.nalBuffer = new ParsableByteArray();
        this.scratchBytes = new byte[16];
        this.scratch = new ParsableByteArray(this.scratchBytes);
        this.containerAtoms = new ArrayDeque<>();
        this.pendingMetadataSampleInfos = new ArrayDeque<>();
        this.trackBundles = new SparseArray<>();
        this.durationUs = C1996C.TIME_UNSET;
        this.pendingSeekTimeUs = C1996C.TIME_UNSET;
        this.segmentIndexEarliestPresentationTimeUs = C1996C.TIME_UNSET;
        enterReadingAtomHeaderState();
    }

    public boolean sniff(ExtractorInput extractorInput) throws IOException, InterruptedException {
        return Sniffer.sniffFragmented(extractorInput);
    }

    public void init(ExtractorOutput extractorOutput2) {
        this.extractorOutput = extractorOutput2;
        Track track = this.sideloadedTrack;
        if (track != null) {
            TrackBundle trackBundle = new TrackBundle(extractorOutput2.track(0, track.type));
            trackBundle.init(this.sideloadedTrack, new DefaultSampleValues(0, 0, 0, 0));
            this.trackBundles.put(0, trackBundle);
            maybeInitExtraTracks();
            this.extractorOutput.endTracks();
        }
    }

    public void seek(long j, long j2) {
        int size = this.trackBundles.size();
        for (int i = 0; i < size; i++) {
            ((TrackBundle) this.trackBundles.valueAt(i)).reset();
        }
        this.pendingMetadataSampleInfos.clear();
        this.pendingMetadataSampleBytes = 0;
        this.pendingSeekTimeUs = j2;
        this.containerAtoms.clear();
        this.isAc4HeaderRequired = false;
        enterReadingAtomHeaderState();
    }

    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException, InterruptedException {
        while (true) {
            int i = this.parserState;
            if (i != 0) {
                if (i == 1) {
                    readAtomPayload(extractorInput);
                } else if (i == 2) {
                    readEncryptionData(extractorInput);
                } else if (readSample(extractorInput)) {
                    return 0;
                }
            } else if (!readAtomHeader(extractorInput)) {
                return -1;
            }
        }
    }

    private void enterReadingAtomHeaderState() {
        this.parserState = 0;
        this.atomHeaderBytesRead = 0;
    }

    private boolean readAtomHeader(ExtractorInput extractorInput) throws IOException, InterruptedException {
        if (this.atomHeaderBytesRead == 0) {
            if (!extractorInput.readFully(this.atomHeader.data, 0, 8, true)) {
                return false;
            }
            this.atomHeaderBytesRead = 8;
            this.atomHeader.setPosition(0);
            this.atomSize = this.atomHeader.readUnsignedInt();
            this.atomType = this.atomHeader.readInt();
        }
        long j = this.atomSize;
        if (j == 1) {
            extractorInput.readFully(this.atomHeader.data, 8, 8);
            this.atomHeaderBytesRead += 8;
            this.atomSize = this.atomHeader.readUnsignedLongToLong();
        } else if (j == 0) {
            long length = extractorInput.getLength();
            if (length == -1 && !this.containerAtoms.isEmpty()) {
                length = ((ContainerAtom) this.containerAtoms.peek()).endPosition;
            }
            if (length != -1) {
                this.atomSize = (length - extractorInput.getPosition()) + ((long) this.atomHeaderBytesRead);
            }
        }
        if (this.atomSize >= ((long) this.atomHeaderBytesRead)) {
            long position = extractorInput.getPosition() - ((long) this.atomHeaderBytesRead);
            if (this.atomType == 1836019558) {
                int size = this.trackBundles.size();
                for (int i = 0; i < size; i++) {
                    TrackFragment trackFragment = ((TrackBundle) this.trackBundles.valueAt(i)).fragment;
                    trackFragment.atomPosition = position;
                    trackFragment.auxiliaryDataPosition = position;
                    trackFragment.dataPosition = position;
                }
            }
            int i2 = this.atomType;
            if (i2 == 1835295092) {
                this.currentTrackBundle = null;
                this.endOfMdatPosition = this.atomSize + position;
                if (!this.haveOutputSeekMap) {
                    this.extractorOutput.seekMap(new Unseekable(this.durationUs, position));
                    this.haveOutputSeekMap = true;
                }
                this.parserState = 2;
                return true;
            }
            if (shouldParseContainerAtom(i2)) {
                long position2 = (extractorInput.getPosition() + this.atomSize) - 8;
                this.containerAtoms.push(new ContainerAtom(this.atomType, position2));
                if (this.atomSize == ((long) this.atomHeaderBytesRead)) {
                    processAtomEnded(position2);
                } else {
                    enterReadingAtomHeaderState();
                }
            } else if (shouldParseLeafAtom(this.atomType)) {
                if (this.atomHeaderBytesRead == 8) {
                    long j2 = this.atomSize;
                    if (j2 <= 2147483647L) {
                        this.atomData = new ParsableByteArray((int) j2);
                        System.arraycopy(this.atomHeader.data, 0, this.atomData.data, 0, 8);
                        this.parserState = 1;
                    } else {
                        throw new ParserException("Leaf atom with length > 2147483647 (unsupported).");
                    }
                } else {
                    throw new ParserException("Leaf atom defines extended atom size (unsupported).");
                }
            } else if (this.atomSize <= 2147483647L) {
                this.atomData = null;
                this.parserState = 1;
            } else {
                throw new ParserException("Skipping atom with length > 2147483647 (unsupported).");
            }
            return true;
        }
        throw new ParserException("Atom size less than header length (unsupported).");
    }

    private void readAtomPayload(ExtractorInput extractorInput) throws IOException, InterruptedException {
        int i = ((int) this.atomSize) - this.atomHeaderBytesRead;
        ParsableByteArray parsableByteArray = this.atomData;
        if (parsableByteArray != null) {
            extractorInput.readFully(parsableByteArray.data, 8, i);
            onLeafAtomRead(new LeafAtom(this.atomType, this.atomData), extractorInput.getPosition());
        } else {
            extractorInput.skipFully(i);
        }
        processAtomEnded(extractorInput.getPosition());
    }

    private void processAtomEnded(long j) throws ParserException {
        while (!this.containerAtoms.isEmpty() && ((ContainerAtom) this.containerAtoms.peek()).endPosition == j) {
            onContainerAtomRead((ContainerAtom) this.containerAtoms.pop());
        }
        enterReadingAtomHeaderState();
    }

    private void onLeafAtomRead(LeafAtom leafAtom, long j) throws ParserException {
        if (!this.containerAtoms.isEmpty()) {
            ((ContainerAtom) this.containerAtoms.peek()).add(leafAtom);
        } else if (leafAtom.type == 1936286840) {
            Pair parseSidx = parseSidx(leafAtom.data, j);
            this.segmentIndexEarliestPresentationTimeUs = ((Long) parseSidx.first).longValue();
            this.extractorOutput.seekMap((SeekMap) parseSidx.second);
            this.haveOutputSeekMap = true;
        } else if (leafAtom.type == 1701671783) {
            onEmsgLeafAtomRead(leafAtom.data);
        }
    }

    private void onContainerAtomRead(ContainerAtom containerAtom) throws ParserException {
        if (containerAtom.type == 1836019574) {
            onMoovContainerAtomRead(containerAtom);
        } else if (containerAtom.type == 1836019558) {
            onMoofContainerAtomRead(containerAtom);
        } else if (!this.containerAtoms.isEmpty()) {
            ((ContainerAtom) this.containerAtoms.peek()).add(containerAtom);
        }
    }

    private void onMoovContainerAtomRead(ContainerAtom containerAtom) throws ParserException {
        int i;
        int i2;
        ContainerAtom containerAtom2 = containerAtom;
        boolean z = true;
        int i3 = 0;
        Assertions.checkState(this.sideloadedTrack == null, "Unexpected moov box.");
        DrmInitData drmInitData = this.sideloadedDrmInitData;
        if (drmInitData == null) {
            drmInitData = getDrmInitDataFromAtoms(containerAtom2.leafChildren);
        }
        ContainerAtom containerAtomOfType = containerAtom2.getContainerAtomOfType(Atom.TYPE_mvex);
        SparseArray sparseArray = new SparseArray();
        int size = containerAtomOfType.leafChildren.size();
        long j = -9223372036854775807L;
        for (int i4 = 0; i4 < size; i4++) {
            LeafAtom leafAtom = (LeafAtom) containerAtomOfType.leafChildren.get(i4);
            if (leafAtom.type == 1953654136) {
                Pair parseTrex = parseTrex(leafAtom.data);
                sparseArray.put(((Integer) parseTrex.first).intValue(), parseTrex.second);
            } else if (leafAtom.type == 1835362404) {
                j = parseMehd(leafAtom.data);
            }
        }
        SparseArray sparseArray2 = new SparseArray();
        int size2 = containerAtom2.containerChildren.size();
        int i5 = 0;
        while (i5 < size2) {
            ContainerAtom containerAtom3 = (ContainerAtom) containerAtom2.containerChildren.get(i5);
            if (containerAtom3.type == 1953653099) {
                i = i5;
                i2 = size2;
                Track modifyTrack = modifyTrack(AtomParsers.parseTrak(containerAtom3, containerAtom2.getLeafAtomOfType(Atom.TYPE_mvhd), j, drmInitData, (this.flags & 16) != 0, false));
                if (modifyTrack != null) {
                    sparseArray2.put(modifyTrack.f1479id, modifyTrack);
                }
            } else {
                i = i5;
                i2 = size2;
            }
            i5 = i + 1;
            size2 = i2;
        }
        int size3 = sparseArray2.size();
        if (this.trackBundles.size() == 0) {
            while (i3 < size3) {
                Track track = (Track) sparseArray2.valueAt(i3);
                TrackBundle trackBundle = new TrackBundle(this.extractorOutput.track(i3, track.type));
                trackBundle.init(track, getDefaultSampleValues(sparseArray, track.f1479id));
                this.trackBundles.put(track.f1479id, trackBundle);
                this.durationUs = Math.max(this.durationUs, track.durationUs);
                i3++;
            }
            maybeInitExtraTracks();
            this.extractorOutput.endTracks();
            return;
        }
        if (this.trackBundles.size() != size3) {
            z = false;
        }
        Assertions.checkState(z);
        while (i3 < size3) {
            Track track2 = (Track) sparseArray2.valueAt(i3);
            ((TrackBundle) this.trackBundles.get(track2.f1479id)).init(track2, getDefaultSampleValues(sparseArray, track2.f1479id));
            i3++;
        }
    }

    private DefaultSampleValues getDefaultSampleValues(SparseArray<DefaultSampleValues> sparseArray, int i) {
        if (sparseArray.size() == 1) {
            return (DefaultSampleValues) sparseArray.valueAt(0);
        }
        return (DefaultSampleValues) Assertions.checkNotNull(sparseArray.get(i));
    }

    private void onMoofContainerAtomRead(ContainerAtom containerAtom) throws ParserException {
        DrmInitData drmInitData;
        parseMoof(containerAtom, this.trackBundles, this.flags, this.scratchBytes);
        if (this.sideloadedDrmInitData != null) {
            drmInitData = null;
        } else {
            drmInitData = getDrmInitDataFromAtoms(containerAtom.leafChildren);
        }
        if (drmInitData != null) {
            int size = this.trackBundles.size();
            for (int i = 0; i < size; i++) {
                ((TrackBundle) this.trackBundles.valueAt(i)).updateDrmInitData(drmInitData);
            }
        }
        if (this.pendingSeekTimeUs != C1996C.TIME_UNSET) {
            int size2 = this.trackBundles.size();
            for (int i2 = 0; i2 < size2; i2++) {
                ((TrackBundle) this.trackBundles.valueAt(i2)).seek(this.pendingSeekTimeUs);
            }
            this.pendingSeekTimeUs = C1996C.TIME_UNSET;
        }
    }

    private void maybeInitExtraTracks() {
        int i;
        if (this.emsgTrackOutputs == null) {
            this.emsgTrackOutputs = new TrackOutput[2];
            TrackOutput trackOutput = this.additionalEmsgTrackOutput;
            if (trackOutput != null) {
                this.emsgTrackOutputs[0] = trackOutput;
                i = 1;
            } else {
                i = 0;
            }
            if ((this.flags & 4) != 0) {
                int i2 = i + 1;
                this.emsgTrackOutputs[i] = this.extractorOutput.track(this.trackBundles.size(), 4);
                i = i2;
            }
            this.emsgTrackOutputs = (TrackOutput[]) Arrays.copyOf(this.emsgTrackOutputs, i);
            for (TrackOutput format : this.emsgTrackOutputs) {
                format.format(EMSG_FORMAT);
            }
        }
        if (this.cea608TrackOutputs == null) {
            this.cea608TrackOutputs = new TrackOutput[this.closedCaptionFormats.size()];
            for (int i3 = 0; i3 < this.cea608TrackOutputs.length; i3++) {
                TrackOutput track = this.extractorOutput.track(this.trackBundles.size() + 1 + i3, 3);
                track.format((Format) this.closedCaptionFormats.get(i3));
                this.cea608TrackOutputs[i3] = track;
            }
        }
    }

    private void onEmsgLeafAtomRead(ParsableByteArray parsableByteArray) {
        long j;
        long j2;
        String str;
        String str2;
        long j3;
        long j4;
        TrackOutput[] trackOutputArr;
        ParsableByteArray parsableByteArray2 = parsableByteArray;
        TrackOutput[] trackOutputArr2 = this.emsgTrackOutputs;
        if (!(trackOutputArr2 == null || trackOutputArr2.length == 0)) {
            parsableByteArray2.setPosition(8);
            int parseFullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
            if (parseFullAtomVersion == 0) {
                String str3 = (String) Assertions.checkNotNull(parsableByteArray.readNullTerminatedString());
                String str4 = (String) Assertions.checkNotNull(parsableByteArray.readNullTerminatedString());
                long readUnsignedInt = parsableByteArray.readUnsignedInt();
                j4 = Util.scaleLargeTimestamp(parsableByteArray.readUnsignedInt(), 1000000, readUnsignedInt);
                long j5 = this.segmentIndexEarliestPresentationTimeUs;
                long j6 = j5 != C1996C.TIME_UNSET ? j5 + j4 : -9223372036854775807L;
                str2 = str3;
                j2 = Util.scaleLargeTimestamp(parsableByteArray.readUnsignedInt(), 1000, readUnsignedInt);
                str = str4;
                j = parsableByteArray.readUnsignedInt();
                j3 = j6;
            } else if (parseFullAtomVersion != 1) {
                StringBuilder sb = new StringBuilder();
                sb.append("Skipping unsupported emsg version: ");
                sb.append(parseFullAtomVersion);
                Log.m1396w(TAG, sb.toString());
                return;
            } else {
                long readUnsignedInt2 = parsableByteArray.readUnsignedInt();
                j3 = Util.scaleLargeTimestamp(parsableByteArray.readUnsignedLongToLong(), 1000000, readUnsignedInt2);
                str2 = (String) Assertions.checkNotNull(parsableByteArray.readNullTerminatedString());
                j2 = Util.scaleLargeTimestamp(parsableByteArray.readUnsignedInt(), 1000, readUnsignedInt2);
                j = parsableByteArray.readUnsignedInt();
                str = (String) Assertions.checkNotNull(parsableByteArray.readNullTerminatedString());
                j4 = -9223372036854775807L;
            }
            byte[] bArr = new byte[parsableByteArray.bytesLeft()];
            parsableByteArray2.readBytes(bArr, 0, parsableByteArray.bytesLeft());
            EventMessage eventMessage = new EventMessage(str2, str, j2, j, bArr);
            ParsableByteArray parsableByteArray3 = new ParsableByteArray(this.eventMessageEncoder.encode(eventMessage));
            int bytesLeft = parsableByteArray3.bytesLeft();
            for (TrackOutput trackOutput : this.emsgTrackOutputs) {
                parsableByteArray3.setPosition(0);
                trackOutput.sampleData(parsableByteArray3, bytesLeft);
            }
            if (j3 == C1996C.TIME_UNSET) {
                this.pendingMetadataSampleInfos.addLast(new MetadataSampleInfo(j4, bytesLeft));
                this.pendingMetadataSampleBytes += bytesLeft;
            } else {
                TimestampAdjuster timestampAdjuster2 = this.timestampAdjuster;
                if (timestampAdjuster2 != null) {
                    j3 = timestampAdjuster2.adjustSampleTimestamp(j3);
                }
                for (TrackOutput sampleMetadata : this.emsgTrackOutputs) {
                    sampleMetadata.sampleMetadata(j3, 1, bytesLeft, 0, null);
                }
            }
        }
    }

    private static Pair<Integer, DefaultSampleValues> parseTrex(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(12);
        return Pair.create(Integer.valueOf(parsableByteArray.readInt()), new DefaultSampleValues(parsableByteArray.readUnsignedIntToInt() - 1, parsableByteArray.readUnsignedIntToInt(), parsableByteArray.readUnsignedIntToInt(), parsableByteArray.readInt()));
    }

    private static long parseMehd(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(8);
        return Atom.parseFullAtomVersion(parsableByteArray.readInt()) == 0 ? parsableByteArray.readUnsignedInt() : parsableByteArray.readUnsignedLongToLong();
    }

    private static void parseMoof(ContainerAtom containerAtom, SparseArray<TrackBundle> sparseArray, int i, byte[] bArr) throws ParserException {
        int size = containerAtom.containerChildren.size();
        for (int i2 = 0; i2 < size; i2++) {
            ContainerAtom containerAtom2 = (ContainerAtom) containerAtom.containerChildren.get(i2);
            if (containerAtom2.type == 1953653094) {
                parseTraf(containerAtom2, sparseArray, i, bArr);
            }
        }
    }

    private static void parseTraf(ContainerAtom containerAtom, SparseArray<TrackBundle> sparseArray, int i, byte[] bArr) throws ParserException {
        TrackBundle parseTfhd = parseTfhd(containerAtom.getLeafAtomOfType(Atom.TYPE_tfhd).data, sparseArray);
        if (parseTfhd != null) {
            TrackFragment trackFragment = parseTfhd.fragment;
            long j = trackFragment.nextFragmentDecodeTime;
            parseTfhd.reset();
            if (containerAtom.getLeafAtomOfType(Atom.TYPE_tfdt) != null && (i & 2) == 0) {
                j = parseTfdt(containerAtom.getLeafAtomOfType(Atom.TYPE_tfdt).data);
            }
            parseTruns(containerAtom, parseTfhd, j, i);
            TrackEncryptionBox sampleDescriptionEncryptionBox = parseTfhd.track.getSampleDescriptionEncryptionBox(trackFragment.header.sampleDescriptionIndex);
            LeafAtom leafAtomOfType = containerAtom.getLeafAtomOfType(Atom.TYPE_saiz);
            if (leafAtomOfType != null) {
                parseSaiz(sampleDescriptionEncryptionBox, leafAtomOfType.data, trackFragment);
            }
            LeafAtom leafAtomOfType2 = containerAtom.getLeafAtomOfType(Atom.TYPE_saio);
            if (leafAtomOfType2 != null) {
                parseSaio(leafAtomOfType2.data, trackFragment);
            }
            LeafAtom leafAtomOfType3 = containerAtom.getLeafAtomOfType(Atom.TYPE_senc);
            if (leafAtomOfType3 != null) {
                parseSenc(leafAtomOfType3.data, trackFragment);
            }
            LeafAtom leafAtomOfType4 = containerAtom.getLeafAtomOfType(Atom.TYPE_sbgp);
            LeafAtom leafAtomOfType5 = containerAtom.getLeafAtomOfType(Atom.TYPE_sgpd);
            if (!(leafAtomOfType4 == null || leafAtomOfType5 == null)) {
                parseSgpd(leafAtomOfType4.data, leafAtomOfType5.data, sampleDescriptionEncryptionBox != null ? sampleDescriptionEncryptionBox.schemeType : null, trackFragment);
            }
            int size = containerAtom.leafChildren.size();
            for (int i2 = 0; i2 < size; i2++) {
                LeafAtom leafAtom = (LeafAtom) containerAtom.leafChildren.get(i2);
                if (leafAtom.type == 1970628964) {
                    parseUuid(leafAtom.data, trackFragment, bArr);
                }
            }
        }
    }

    private static void parseTruns(ContainerAtom containerAtom, TrackBundle trackBundle, long j, int i) {
        TrackBundle trackBundle2 = trackBundle;
        List<LeafAtom> list = containerAtom.leafChildren;
        int size = list.size();
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            LeafAtom leafAtom = (LeafAtom) list.get(i4);
            if (leafAtom.type == 1953658222) {
                ParsableByteArray parsableByteArray = leafAtom.data;
                parsableByteArray.setPosition(12);
                int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
                if (readUnsignedIntToInt > 0) {
                    i3 += readUnsignedIntToInt;
                    i2++;
                }
            }
        }
        trackBundle2.currentTrackRunIndex = 0;
        trackBundle2.currentSampleInTrackRun = 0;
        trackBundle2.currentSampleIndex = 0;
        trackBundle2.fragment.initTables(i2, i3);
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 < size; i7++) {
            LeafAtom leafAtom2 = (LeafAtom) list.get(i7);
            if (leafAtom2.type == 1953658222) {
                int i8 = i5 + 1;
                i6 = parseTrun(trackBundle, i5, j, i, leafAtom2.data, i6);
                i5 = i8;
            }
        }
    }

    private static void parseSaiz(TrackEncryptionBox trackEncryptionBox, ParsableByteArray parsableByteArray, TrackFragment trackFragment) throws ParserException {
        int i;
        int i2 = trackEncryptionBox.perSampleIvSize;
        parsableByteArray.setPosition(8);
        boolean z = true;
        if ((Atom.parseFullAtomFlags(parsableByteArray.readInt()) & 1) == 1) {
            parsableByteArray.skipBytes(8);
        }
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
        if (readUnsignedIntToInt == trackFragment.sampleCount) {
            if (readUnsignedByte == 0) {
                boolean[] zArr = trackFragment.sampleHasSubsampleEncryptionTable;
                i = 0;
                for (int i3 = 0; i3 < readUnsignedIntToInt; i3++) {
                    int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
                    i += readUnsignedByte2;
                    zArr[i3] = readUnsignedByte2 > i2;
                }
            } else {
                if (readUnsignedByte <= i2) {
                    z = false;
                }
                i = (readUnsignedByte * readUnsignedIntToInt) + 0;
                Arrays.fill(trackFragment.sampleHasSubsampleEncryptionTable, 0, readUnsignedIntToInt, z);
            }
            trackFragment.initEncryptionData(i);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Length mismatch: ");
        sb.append(readUnsignedIntToInt);
        sb.append(", ");
        sb.append(trackFragment.sampleCount);
        throw new ParserException(sb.toString());
    }

    private static void parseSaio(ParsableByteArray parsableByteArray, TrackFragment trackFragment) throws ParserException {
        long j;
        parsableByteArray.setPosition(8);
        int readInt = parsableByteArray.readInt();
        if ((Atom.parseFullAtomFlags(readInt) & 1) == 1) {
            parsableByteArray.skipBytes(8);
        }
        int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
        if (readUnsignedIntToInt == 1) {
            int parseFullAtomVersion = Atom.parseFullAtomVersion(readInt);
            long j2 = trackFragment.auxiliaryDataPosition;
            if (parseFullAtomVersion == 0) {
                j = parsableByteArray.readUnsignedInt();
            } else {
                j = parsableByteArray.readUnsignedLongToLong();
            }
            trackFragment.auxiliaryDataPosition = j2 + j;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unexpected saio entry count: ");
        sb.append(readUnsignedIntToInt);
        throw new ParserException(sb.toString());
    }

    private static TrackBundle parseTfhd(ParsableByteArray parsableByteArray, SparseArray<TrackBundle> sparseArray) {
        parsableByteArray.setPosition(8);
        int parseFullAtomFlags = Atom.parseFullAtomFlags(parsableByteArray.readInt());
        TrackBundle trackBundle = getTrackBundle(sparseArray, parsableByteArray.readInt());
        if (trackBundle == null) {
            return null;
        }
        if ((parseFullAtomFlags & 1) != 0) {
            long readUnsignedLongToLong = parsableByteArray.readUnsignedLongToLong();
            trackBundle.fragment.dataPosition = readUnsignedLongToLong;
            trackBundle.fragment.auxiliaryDataPosition = readUnsignedLongToLong;
        }
        DefaultSampleValues defaultSampleValues = trackBundle.defaultSampleValues;
        trackBundle.fragment.header = new DefaultSampleValues((parseFullAtomFlags & 2) != 0 ? parsableByteArray.readUnsignedIntToInt() - 1 : defaultSampleValues.sampleDescriptionIndex, (parseFullAtomFlags & 8) != 0 ? parsableByteArray.readUnsignedIntToInt() : defaultSampleValues.duration, (parseFullAtomFlags & 16) != 0 ? parsableByteArray.readUnsignedIntToInt() : defaultSampleValues.size, (parseFullAtomFlags & 32) != 0 ? parsableByteArray.readUnsignedIntToInt() : defaultSampleValues.flags);
        return trackBundle;
    }

    private static TrackBundle getTrackBundle(SparseArray<TrackBundle> sparseArray, int i) {
        if (sparseArray.size() == 1) {
            return (TrackBundle) sparseArray.valueAt(0);
        }
        return (TrackBundle) sparseArray.get(i);
    }

    private static long parseTfdt(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(8);
        return Atom.parseFullAtomVersion(parsableByteArray.readInt()) == 1 ? parsableByteArray.readUnsignedLongToLong() : parsableByteArray.readUnsignedInt();
    }

    private static int parseTrun(TrackBundle trackBundle, int i, long j, int i2, ParsableByteArray parsableByteArray, int i3) {
        boolean z;
        int i4;
        boolean z2;
        int i5;
        boolean z3;
        boolean z4;
        boolean z5;
        TrackBundle trackBundle2 = trackBundle;
        parsableByteArray.setPosition(8);
        int parseFullAtomFlags = Atom.parseFullAtomFlags(parsableByteArray.readInt());
        Track track = trackBundle2.track;
        TrackFragment trackFragment = trackBundle2.fragment;
        DefaultSampleValues defaultSampleValues = trackFragment.header;
        trackFragment.trunLength[i] = parsableByteArray.readUnsignedIntToInt();
        trackFragment.trunDataPosition[i] = trackFragment.dataPosition;
        if ((parseFullAtomFlags & 1) != 0) {
            long[] jArr = trackFragment.trunDataPosition;
            jArr[i] = jArr[i] + ((long) parsableByteArray.readInt());
        }
        boolean z6 = (parseFullAtomFlags & 4) != 0;
        int i6 = defaultSampleValues.flags;
        if (z6) {
            i6 = parsableByteArray.readUnsignedIntToInt();
        }
        boolean z7 = (parseFullAtomFlags & 256) != 0;
        boolean z8 = (parseFullAtomFlags & 512) != 0;
        boolean z9 = (parseFullAtomFlags & 1024) != 0;
        boolean z10 = (parseFullAtomFlags & 2048) != 0;
        long j2 = 0;
        if (track.editListDurations != null && track.editListDurations.length == 1 && track.editListDurations[0] == 0) {
            j2 = Util.scaleLargeTimestamp(track.editListMediaTimes[0], 1000, track.timescale);
        }
        int[] iArr = trackFragment.sampleSizeTable;
        int[] iArr2 = trackFragment.sampleCompositionTimeOffsetTable;
        long[] jArr2 = trackFragment.sampleDecodingTimeTable;
        boolean[] zArr = trackFragment.sampleIsSyncFrameTable;
        int i7 = i6;
        boolean z11 = track.type == 2 && (i2 & 1) != 0;
        int i8 = i3 + trackFragment.trunLength[i];
        long j3 = j2;
        boolean[] zArr2 = zArr;
        long j4 = track.timescale;
        boolean[] zArr3 = zArr2;
        long[] jArr3 = jArr2;
        long j5 = i > 0 ? trackFragment.nextFragmentDecodeTime : j;
        int i9 = i3;
        while (i9 < i8) {
            int readUnsignedIntToInt = z7 ? parsableByteArray.readUnsignedIntToInt() : defaultSampleValues.duration;
            if (z8) {
                z = z7;
                i4 = parsableByteArray.readUnsignedIntToInt();
            } else {
                z = z7;
                i4 = defaultSampleValues.size;
            }
            if (i9 == 0 && z6) {
                z2 = z6;
                i5 = i7;
            } else if (z9) {
                z2 = z6;
                i5 = parsableByteArray.readInt();
            } else {
                z2 = z6;
                i5 = defaultSampleValues.flags;
            }
            if (z10) {
                z5 = z10;
                z4 = z8;
                z3 = z9;
                iArr2[i9] = (int) ((((long) parsableByteArray.readInt()) * 1000) / j4);
            } else {
                z5 = z10;
                z4 = z8;
                z3 = z9;
                iArr2[i9] = 0;
            }
            jArr3[i9] = Util.scaleLargeTimestamp(j5, 1000, j4) - j3;
            iArr[i9] = i4;
            zArr3[i9] = ((i5 >> 16) & 1) == 0 && (!z11 || i9 == 0);
            i9++;
            j5 += (long) readUnsignedIntToInt;
            z7 = z;
            z6 = z2;
            z10 = z5;
            z8 = z4;
            z9 = z3;
            i8 = i8;
        }
        int i10 = i8;
        trackFragment.nextFragmentDecodeTime = j5;
        return i10;
    }

    private static void parseUuid(ParsableByteArray parsableByteArray, TrackFragment trackFragment, byte[] bArr) throws ParserException {
        parsableByteArray.setPosition(8);
        parsableByteArray.readBytes(bArr, 0, 16);
        if (Arrays.equals(bArr, PIFF_SAMPLE_ENCRYPTION_BOX_EXTENDED_TYPE)) {
            parseSenc(parsableByteArray, 16, trackFragment);
        }
    }

    private static void parseSenc(ParsableByteArray parsableByteArray, TrackFragment trackFragment) throws ParserException {
        parseSenc(parsableByteArray, 0, trackFragment);
    }

    private static void parseSenc(ParsableByteArray parsableByteArray, int i, TrackFragment trackFragment) throws ParserException {
        parsableByteArray.setPosition(i + 8);
        int parseFullAtomFlags = Atom.parseFullAtomFlags(parsableByteArray.readInt());
        if ((parseFullAtomFlags & 1) == 0) {
            boolean z = (parseFullAtomFlags & 2) != 0;
            int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
            if (readUnsignedIntToInt == trackFragment.sampleCount) {
                Arrays.fill(trackFragment.sampleHasSubsampleEncryptionTable, 0, readUnsignedIntToInt, z);
                trackFragment.initEncryptionData(parsableByteArray.bytesLeft());
                trackFragment.fillEncryptionData(parsableByteArray);
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Length mismatch: ");
            sb.append(readUnsignedIntToInt);
            sb.append(", ");
            sb.append(trackFragment.sampleCount);
            throw new ParserException(sb.toString());
        }
        throw new ParserException("Overriding TrackEncryptionBox parameters is unsupported.");
    }

    private static void parseSgpd(ParsableByteArray parsableByteArray, ParsableByteArray parsableByteArray2, String str, TrackFragment trackFragment) throws ParserException {
        byte[] bArr;
        ParsableByteArray parsableByteArray3 = parsableByteArray;
        ParsableByteArray parsableByteArray4 = parsableByteArray2;
        TrackFragment trackFragment2 = trackFragment;
        parsableByteArray3.setPosition(8);
        int readInt = parsableByteArray.readInt();
        if (parsableByteArray.readInt() == SAMPLE_GROUP_TYPE_seig) {
            if (Atom.parseFullAtomVersion(readInt) == 1) {
                parsableByteArray3.skipBytes(4);
            }
            if (parsableByteArray.readInt() == 1) {
                parsableByteArray4.setPosition(8);
                int readInt2 = parsableByteArray2.readInt();
                if (parsableByteArray2.readInt() == SAMPLE_GROUP_TYPE_seig) {
                    int parseFullAtomVersion = Atom.parseFullAtomVersion(readInt2);
                    if (parseFullAtomVersion == 1) {
                        if (parsableByteArray2.readUnsignedInt() == 0) {
                            throw new ParserException("Variable length description in sgpd found (unsupported)");
                        }
                    } else if (parseFullAtomVersion >= 2) {
                        parsableByteArray4.skipBytes(4);
                    }
                    if (parsableByteArray2.readUnsignedInt() == 1) {
                        parsableByteArray4.skipBytes(1);
                        int readUnsignedByte = parsableByteArray2.readUnsignedByte();
                        int i = (readUnsignedByte & PsExtractor.VIDEO_STREAM_MASK) >> 4;
                        int i2 = readUnsignedByte & 15;
                        boolean z = parsableByteArray2.readUnsignedByte() == 1;
                        if (z) {
                            int readUnsignedByte2 = parsableByteArray2.readUnsignedByte();
                            byte[] bArr2 = new byte[16];
                            parsableByteArray4.readBytes(bArr2, 0, bArr2.length);
                            if (readUnsignedByte2 == 0) {
                                int readUnsignedByte3 = parsableByteArray2.readUnsignedByte();
                                byte[] bArr3 = new byte[readUnsignedByte3];
                                parsableByteArray4.readBytes(bArr3, 0, readUnsignedByte3);
                                bArr = bArr3;
                            } else {
                                bArr = null;
                            }
                            trackFragment2.definesEncryptionData = true;
                            TrackEncryptionBox trackEncryptionBox = new TrackEncryptionBox(z, str, readUnsignedByte2, bArr2, i, i2, bArr);
                            trackFragment2.trackEncryptionBox = trackEncryptionBox;
                            return;
                        }
                        return;
                    }
                    throw new ParserException("Entry count in sgpd != 1 (unsupported).");
                }
                return;
            }
            throw new ParserException("Entry count in sbgp != 1 (unsupported).");
        }
    }

    private static Pair<Long, ChunkIndex> parseSidx(ParsableByteArray parsableByteArray, long j) throws ParserException {
        long j2;
        long j3;
        ParsableByteArray parsableByteArray2 = parsableByteArray;
        parsableByteArray2.setPosition(8);
        int parseFullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        parsableByteArray2.skipBytes(4);
        long readUnsignedInt = parsableByteArray.readUnsignedInt();
        if (parseFullAtomVersion == 0) {
            j3 = parsableByteArray.readUnsignedInt();
            j2 = parsableByteArray.readUnsignedInt();
        } else {
            j3 = parsableByteArray.readUnsignedLongToLong();
            j2 = parsableByteArray.readUnsignedLongToLong();
        }
        long j4 = j3;
        long j5 = j + j2;
        long scaleLargeTimestamp = Util.scaleLargeTimestamp(j4, 1000000, readUnsignedInt);
        parsableByteArray2.skipBytes(2);
        int readUnsignedShort = parsableByteArray.readUnsignedShort();
        int[] iArr = new int[readUnsignedShort];
        long[] jArr = new long[readUnsignedShort];
        long[] jArr2 = new long[readUnsignedShort];
        long[] jArr3 = new long[readUnsignedShort];
        long j6 = j4;
        long j7 = scaleLargeTimestamp;
        int i = 0;
        while (i < readUnsignedShort) {
            int readInt = parsableByteArray.readInt();
            if ((readInt & Integer.MIN_VALUE) == 0) {
                long readUnsignedInt2 = parsableByteArray.readUnsignedInt();
                iArr[i] = readInt & Integer.MAX_VALUE;
                jArr[i] = j5;
                jArr3[i] = j7;
                j6 += readUnsignedInt2;
                long[] jArr4 = jArr2;
                long[] jArr5 = jArr3;
                int i2 = readUnsignedShort;
                int[] iArr2 = iArr;
                long[] jArr6 = jArr;
                j7 = Util.scaleLargeTimestamp(j6, 1000000, readUnsignedInt);
                jArr4[i] = j7 - jArr5[i];
                parsableByteArray2.skipBytes(4);
                j5 += (long) iArr2[i];
                i++;
                iArr = iArr2;
                jArr3 = jArr5;
                jArr2 = jArr4;
                jArr = jArr6;
                readUnsignedShort = i2;
            } else {
                throw new ParserException("Unhandled indirect reference");
            }
        }
        return Pair.create(Long.valueOf(scaleLargeTimestamp), new ChunkIndex(iArr, jArr, jArr2, jArr3));
    }

    private void readEncryptionData(ExtractorInput extractorInput) throws IOException, InterruptedException {
        int size = this.trackBundles.size();
        TrackBundle trackBundle = null;
        long j = Long.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            TrackFragment trackFragment = ((TrackBundle) this.trackBundles.valueAt(i)).fragment;
            if (trackFragment.sampleEncryptionDataNeedsFill && trackFragment.auxiliaryDataPosition < j) {
                long j2 = trackFragment.auxiliaryDataPosition;
                trackBundle = (TrackBundle) this.trackBundles.valueAt(i);
                j = j2;
            }
        }
        if (trackBundle == null) {
            this.parserState = 3;
            return;
        }
        int position = (int) (j - extractorInput.getPosition());
        if (position >= 0) {
            extractorInput.skipFully(position);
            trackBundle.fragment.fillEncryptionData(extractorInput);
            return;
        }
        throw new ParserException("Offset to encryption data was negative.");
    }

    private boolean readSample(ExtractorInput extractorInput) throws IOException, InterruptedException {
        CryptoData cryptoData;
        boolean z;
        boolean z2;
        int i;
        ExtractorInput extractorInput2 = extractorInput;
        int i2 = 4;
        int i3 = 1;
        int i4 = 0;
        if (this.parserState == 3) {
            if (this.currentTrackBundle == null) {
                TrackBundle nextFragmentRun = getNextFragmentRun(this.trackBundles);
                if (nextFragmentRun == null) {
                    int position = (int) (this.endOfMdatPosition - extractorInput.getPosition());
                    if (position >= 0) {
                        extractorInput2.skipFully(position);
                        enterReadingAtomHeaderState();
                        return false;
                    }
                    throw new ParserException("Offset to end of mdat was negative.");
                }
                int position2 = (int) (nextFragmentRun.fragment.trunDataPosition[nextFragmentRun.currentTrackRunIndex] - extractorInput.getPosition());
                if (position2 < 0) {
                    Log.m1396w(TAG, "Ignoring negative offset to sample data.");
                    position2 = 0;
                }
                extractorInput2.skipFully(position2);
                this.currentTrackBundle = nextFragmentRun;
            }
            this.sampleSize = this.currentTrackBundle.fragment.sampleSizeTable[this.currentTrackBundle.currentSampleIndex];
            if (this.currentTrackBundle.currentSampleIndex < this.currentTrackBundle.firstSampleToOutputIndex) {
                extractorInput2.skipFully(this.sampleSize);
                this.currentTrackBundle.skipSampleEncryptionData();
                if (!this.currentTrackBundle.next()) {
                    this.currentTrackBundle = null;
                }
                this.parserState = 3;
                return true;
            }
            if (this.currentTrackBundle.track.sampleTransformation == 1) {
                this.sampleSize -= 8;
                extractorInput2.skipFully(8);
            }
            this.sampleBytesWritten = this.currentTrackBundle.outputSampleEncryptionData();
            this.sampleSize += this.sampleBytesWritten;
            this.parserState = 4;
            this.sampleCurrentNalBytesRemaining = 0;
            this.isAc4HeaderRequired = MimeTypes.AUDIO_AC4.equals(this.currentTrackBundle.track.format.sampleMimeType);
        }
        TrackFragment trackFragment = this.currentTrackBundle.fragment;
        Track track = this.currentTrackBundle.track;
        TrackOutput trackOutput = this.currentTrackBundle.output;
        int i5 = this.currentTrackBundle.currentSampleIndex;
        long samplePresentationTime = trackFragment.getSamplePresentationTime(i5) * 1000;
        TimestampAdjuster timestampAdjuster2 = this.timestampAdjuster;
        if (timestampAdjuster2 != null) {
            samplePresentationTime = timestampAdjuster2.adjustSampleTimestamp(samplePresentationTime);
        }
        long j = samplePresentationTime;
        if (track.nalUnitLengthFieldLength == 0) {
            if (this.isAc4HeaderRequired) {
                Ac4Util.getAc4SampleHeader(this.sampleSize, this.scratch);
                int limit = this.scratch.limit();
                trackOutput.sampleData(this.scratch, limit);
                this.sampleSize += limit;
                this.sampleBytesWritten += limit;
                z2 = false;
                this.isAc4HeaderRequired = false;
            } else {
                z2 = false;
            }
            while (true) {
                int i6 = this.sampleBytesWritten;
                int i7 = this.sampleSize;
                if (i6 >= i7) {
                    break;
                }
                this.sampleBytesWritten += trackOutput.sampleData(extractorInput2, i7 - i6, z2);
            }
        } else {
            byte[] bArr = this.nalPrefix.data;
            bArr[0] = 0;
            bArr[1] = 0;
            bArr[2] = 0;
            int i8 = track.nalUnitLengthFieldLength + 1;
            int i9 = 4 - track.nalUnitLengthFieldLength;
            while (this.sampleBytesWritten < this.sampleSize) {
                int i10 = this.sampleCurrentNalBytesRemaining;
                if (i10 == 0) {
                    extractorInput2.readFully(bArr, i9, i8);
                    this.nalPrefix.setPosition(i4);
                    int readInt = this.nalPrefix.readInt();
                    if (readInt >= i3) {
                        this.sampleCurrentNalBytesRemaining = readInt - 1;
                        this.nalStartCode.setPosition(i4);
                        trackOutput.sampleData(this.nalStartCode, i2);
                        trackOutput.sampleData(this.nalPrefix, i3);
                        this.processSeiNalUnitPayload = this.cea608TrackOutputs.length > 0 && NalUnitUtil.isNalUnitSei(track.format.sampleMimeType, bArr[i2]);
                        this.sampleBytesWritten += 5;
                        this.sampleSize += i9;
                    } else {
                        throw new ParserException("Invalid NAL length");
                    }
                } else {
                    if (this.processSeiNalUnitPayload) {
                        this.nalBuffer.reset(i10);
                        extractorInput2.readFully(this.nalBuffer.data, i4, this.sampleCurrentNalBytesRemaining);
                        trackOutput.sampleData(this.nalBuffer, this.sampleCurrentNalBytesRemaining);
                        i = this.sampleCurrentNalBytesRemaining;
                        int unescapeStream = NalUnitUtil.unescapeStream(this.nalBuffer.data, this.nalBuffer.limit());
                        this.nalBuffer.setPosition(MimeTypes.VIDEO_H265.equals(track.format.sampleMimeType) ? 1 : 0);
                        this.nalBuffer.setLimit(unescapeStream);
                        CeaUtil.consume(j, this.nalBuffer, this.cea608TrackOutputs);
                    } else {
                        i = trackOutput.sampleData(extractorInput2, i10, false);
                    }
                    this.sampleBytesWritten += i;
                    this.sampleCurrentNalBytesRemaining -= i;
                    i2 = 4;
                    i3 = 1;
                    i4 = 0;
                }
            }
        }
        boolean z3 = trackFragment.sampleIsSyncFrameTable[i5];
        TrackEncryptionBox access$100 = this.currentTrackBundle.getEncryptionBoxIfEncrypted();
        if (access$100 != null) {
            z = z3 | true;
            cryptoData = access$100.cryptoData;
        } else {
            z = z3;
            cryptoData = null;
        }
        long j2 = j;
        trackOutput.sampleMetadata(j, z ? 1 : 0, this.sampleSize, 0, cryptoData);
        outputPendingMetadataSamples(j2);
        if (!this.currentTrackBundle.next()) {
            this.currentTrackBundle = null;
        }
        this.parserState = 3;
        return true;
    }

    private void outputPendingMetadataSamples(long j) {
        while (!this.pendingMetadataSampleInfos.isEmpty()) {
            MetadataSampleInfo metadataSampleInfo = (MetadataSampleInfo) this.pendingMetadataSampleInfos.removeFirst();
            this.pendingMetadataSampleBytes -= metadataSampleInfo.size;
            long j2 = metadataSampleInfo.presentationTimeDeltaUs + j;
            TimestampAdjuster timestampAdjuster2 = this.timestampAdjuster;
            if (timestampAdjuster2 != null) {
                j2 = timestampAdjuster2.adjustSampleTimestamp(j2);
            }
            for (TrackOutput sampleMetadata : this.emsgTrackOutputs) {
                sampleMetadata.sampleMetadata(j2, 1, metadataSampleInfo.size, this.pendingMetadataSampleBytes, null);
            }
        }
    }

    private static TrackBundle getNextFragmentRun(SparseArray<TrackBundle> sparseArray) {
        int size = sparseArray.size();
        TrackBundle trackBundle = null;
        long j = Long.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            TrackBundle trackBundle2 = (TrackBundle) sparseArray.valueAt(i);
            if (trackBundle2.currentTrackRunIndex != trackBundle2.fragment.trunCount) {
                long j2 = trackBundle2.fragment.trunDataPosition[trackBundle2.currentTrackRunIndex];
                if (j2 < j) {
                    trackBundle = trackBundle2;
                    j = j2;
                }
            }
        }
        return trackBundle;
    }

    private static DrmInitData getDrmInitDataFromAtoms(List<LeafAtom> list) {
        int size = list.size();
        ArrayList arrayList = null;
        for (int i = 0; i < size; i++) {
            LeafAtom leafAtom = (LeafAtom) list.get(i);
            if (leafAtom.type == 1886614376) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                byte[] bArr = leafAtom.data.data;
                UUID parseUuid = PsshAtomUtil.parseUuid(bArr);
                if (parseUuid == null) {
                    Log.m1396w(TAG, "Skipped pssh atom (failed to extract uuid)");
                } else {
                    arrayList.add(new SchemeData(parseUuid, MimeTypes.VIDEO_MP4, bArr));
                }
            }
        }
        if (arrayList == null) {
            return null;
        }
        return new DrmInitData((List<SchemeData>) arrayList);
    }
}

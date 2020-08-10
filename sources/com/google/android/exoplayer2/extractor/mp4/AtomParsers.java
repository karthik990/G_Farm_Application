package com.google.android.exoplayer2.extractor.mp4;

import android.util.Pair;
import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.Ac3Util;
import com.google.android.exoplayer2.audio.Ac4Util;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.p040ts.PsExtractor;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.Metadata.Entry;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.AvcConfig;
import com.google.android.exoplayer2.video.DolbyVisionConfig;
import com.google.android.exoplayer2.video.HevcConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.objectweb.asm.Opcodes;

final class AtomParsers {
    private static final int MAX_GAPLESS_TRIM_SIZE_SAMPLES = 4;
    private static final String TAG = "AtomParsers";
    private static final int TYPE_clcp = 1668047728;
    private static final int TYPE_mdta = 1835299937;
    private static final int TYPE_meta = 1835365473;
    private static final int TYPE_sbtl = 1935832172;
    private static final int TYPE_soun = 1936684398;
    private static final int TYPE_subt = 1937072756;
    private static final int TYPE_text = 1952807028;
    private static final int TYPE_vide = 1986618469;
    private static final byte[] opusMagic = Util.getUtf8Bytes("OpusHead");

    private static final class ChunkIterator {
        private final ParsableByteArray chunkOffsets;
        private final boolean chunkOffsetsAreLongs;
        public int index;
        public final int length;
        private int nextSamplesPerChunkChangeIndex;
        public int numSamples;
        public long offset;
        private int remainingSamplesPerChunkChanges;
        private final ParsableByteArray stsc;

        public ChunkIterator(ParsableByteArray parsableByteArray, ParsableByteArray parsableByteArray2, boolean z) {
            this.stsc = parsableByteArray;
            this.chunkOffsets = parsableByteArray2;
            this.chunkOffsetsAreLongs = z;
            parsableByteArray2.setPosition(12);
            this.length = parsableByteArray2.readUnsignedIntToInt();
            parsableByteArray.setPosition(12);
            this.remainingSamplesPerChunkChanges = parsableByteArray.readUnsignedIntToInt();
            boolean z2 = true;
            if (parsableByteArray.readInt() != 1) {
                z2 = false;
            }
            Assertions.checkState(z2, "first_chunk must be 1");
            this.index = -1;
        }

        public boolean moveNext() {
            long j;
            int i = this.index + 1;
            this.index = i;
            if (i == this.length) {
                return false;
            }
            if (this.chunkOffsetsAreLongs) {
                j = this.chunkOffsets.readUnsignedLongToLong();
            } else {
                j = this.chunkOffsets.readUnsignedInt();
            }
            this.offset = j;
            if (this.index == this.nextSamplesPerChunkChangeIndex) {
                this.numSamples = this.stsc.readUnsignedIntToInt();
                this.stsc.skipBytes(4);
                int i2 = this.remainingSamplesPerChunkChanges - 1;
                this.remainingSamplesPerChunkChanges = i2;
                this.nextSamplesPerChunkChangeIndex = i2 > 0 ? this.stsc.readUnsignedIntToInt() - 1 : -1;
            }
            return true;
        }
    }

    private interface SampleSizeBox {
        int getSampleCount();

        boolean isFixedSampleSize();

        int readNextSampleSize();
    }

    private static final class StsdData {
        public static final int STSD_HEADER_SIZE = 8;
        public Format format;
        public int nalUnitLengthFieldLength;
        public int requiredSampleTransformation = 0;
        public final TrackEncryptionBox[] trackEncryptionBoxes;

        public StsdData(int i) {
            this.trackEncryptionBoxes = new TrackEncryptionBox[i];
        }
    }

    static final class StszSampleSizeBox implements SampleSizeBox {
        private final ParsableByteArray data;
        private final int fixedSampleSize = this.data.readUnsignedIntToInt();
        private final int sampleCount = this.data.readUnsignedIntToInt();

        public StszSampleSizeBox(LeafAtom leafAtom) {
            this.data = leafAtom.data;
            this.data.setPosition(12);
        }

        public int getSampleCount() {
            return this.sampleCount;
        }

        public int readNextSampleSize() {
            int i = this.fixedSampleSize;
            return i == 0 ? this.data.readUnsignedIntToInt() : i;
        }

        public boolean isFixedSampleSize() {
            return this.fixedSampleSize != 0;
        }
    }

    static final class Stz2SampleSizeBox implements SampleSizeBox {
        private int currentByte;
        private final ParsableByteArray data;
        private final int fieldSize = (this.data.readUnsignedIntToInt() & 255);
        private final int sampleCount = this.data.readUnsignedIntToInt();
        private int sampleIndex;

        public boolean isFixedSampleSize() {
            return false;
        }

        public Stz2SampleSizeBox(LeafAtom leafAtom) {
            this.data = leafAtom.data;
            this.data.setPosition(12);
        }

        public int getSampleCount() {
            return this.sampleCount;
        }

        public int readNextSampleSize() {
            int i = this.fieldSize;
            if (i == 8) {
                return this.data.readUnsignedByte();
            }
            if (i == 16) {
                return this.data.readUnsignedShort();
            }
            int i2 = this.sampleIndex;
            this.sampleIndex = i2 + 1;
            if (i2 % 2 != 0) {
                return this.currentByte & 15;
            }
            this.currentByte = this.data.readUnsignedByte();
            return (this.currentByte & PsExtractor.VIDEO_STREAM_MASK) >> 4;
        }
    }

    private static final class TkhdData {
        /* access modifiers changed from: private */
        public final long duration;
        /* access modifiers changed from: private */

        /* renamed from: id */
        public final int f1478id;
        /* access modifiers changed from: private */
        public final int rotationDegrees;

        public TkhdData(int i, long j, int i2) {
            this.f1478id = i;
            this.duration = j;
            this.rotationDegrees = i2;
        }
    }

    private static int getTrackTypeForHdlr(int i) {
        if (i == TYPE_soun) {
            return 1;
        }
        if (i == TYPE_vide) {
            return 2;
        }
        if (i == TYPE_text || i == TYPE_sbtl || i == TYPE_subt || i == TYPE_clcp) {
            return 3;
        }
        return i == 1835365473 ? 4 : -1;
    }

    public static Track parseTrak(ContainerAtom containerAtom, LeafAtom leafAtom, long j, DrmInitData drmInitData, boolean z, boolean z2) throws ParserException {
        long j2;
        LeafAtom leafAtom2;
        long[] jArr;
        long[] jArr2;
        Track track;
        ContainerAtom containerAtom2 = containerAtom;
        ContainerAtom containerAtomOfType = containerAtom2.getContainerAtomOfType(Atom.TYPE_mdia);
        int trackTypeForHdlr = getTrackTypeForHdlr(parseHdlr(containerAtomOfType.getLeafAtomOfType(Atom.TYPE_hdlr).data));
        if (trackTypeForHdlr == -1) {
            return null;
        }
        TkhdData parseTkhd = parseTkhd(containerAtom2.getLeafAtomOfType(Atom.TYPE_tkhd).data);
        long j3 = C1996C.TIME_UNSET;
        if (j == C1996C.TIME_UNSET) {
            j2 = parseTkhd.duration;
            leafAtom2 = leafAtom;
        } else {
            leafAtom2 = leafAtom;
            j2 = j;
        }
        long parseMvhd = parseMvhd(leafAtom2.data);
        if (j2 != C1996C.TIME_UNSET) {
            j3 = Util.scaleLargeTimestamp(j2, 1000000, parseMvhd);
        }
        long j4 = j3;
        ContainerAtom containerAtomOfType2 = containerAtomOfType.getContainerAtomOfType(Atom.TYPE_minf).getContainerAtomOfType(Atom.TYPE_stbl);
        Pair parseMdhd = parseMdhd(containerAtomOfType.getLeafAtomOfType(Atom.TYPE_mdhd).data);
        StsdData parseStsd = parseStsd(containerAtomOfType2.getLeafAtomOfType(Atom.TYPE_stsd).data, parseTkhd.f1478id, parseTkhd.rotationDegrees, (String) parseMdhd.second, drmInitData, z2);
        if (!z) {
            Pair parseEdts = parseEdts(containerAtom2.getContainerAtomOfType(Atom.TYPE_edts));
            jArr = (long[]) parseEdts.second;
            jArr2 = (long[]) parseEdts.first;
        } else {
            jArr2 = null;
            jArr = null;
        }
        if (parseStsd.format == null) {
            track = null;
        } else {
            int access$100 = parseTkhd.f1478id;
            long longValue = ((Long) parseMdhd.first).longValue();
            Format format = parseStsd.format;
            int i = parseStsd.requiredSampleTransformation;
            TrackEncryptionBox[] trackEncryptionBoxArr = parseStsd.trackEncryptionBoxes;
            int i2 = parseStsd.nalUnitLengthFieldLength;
            track = new Track(access$100, trackTypeForHdlr, longValue, parseMvhd, j4, format, i, trackEncryptionBoxArr, i2, jArr2, jArr);
        }
        return track;
    }

    /* JADX WARNING: Removed duplicated region for block: B:104:0x024c  */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x029c  */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x02aa  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00df  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.exoplayer2.extractor.mp4.TrackSampleTable parseStbl(com.google.android.exoplayer2.extractor.mp4.Track r35, com.google.android.exoplayer2.extractor.mp4.Atom.ContainerAtom r36, com.google.android.exoplayer2.extractor.GaplessInfoHolder r37) throws com.google.android.exoplayer2.ParserException {
        /*
            r1 = r35
            r0 = r36
            r2 = r37
            r3 = 1937011578(0x7374737a, float:1.936741E31)
            com.google.android.exoplayer2.extractor.mp4.Atom$LeafAtom r3 = r0.getLeafAtomOfType(r3)
            if (r3 == 0) goto L_0x0015
            com.google.android.exoplayer2.extractor.mp4.AtomParsers$StszSampleSizeBox r4 = new com.google.android.exoplayer2.extractor.mp4.AtomParsers$StszSampleSizeBox
            r4.<init>(r3)
            goto L_0x0023
        L_0x0015:
            r3 = 1937013298(0x73747a32, float:1.9369489E31)
            com.google.android.exoplayer2.extractor.mp4.Atom$LeafAtom r3 = r0.getLeafAtomOfType(r3)
            if (r3 == 0) goto L_0x04cd
            com.google.android.exoplayer2.extractor.mp4.AtomParsers$Stz2SampleSizeBox r4 = new com.google.android.exoplayer2.extractor.mp4.AtomParsers$Stz2SampleSizeBox
            r4.<init>(r3)
        L_0x0023:
            int r3 = r4.getSampleCount()
            r5 = 0
            if (r3 != 0) goto L_0x0044
            com.google.android.exoplayer2.extractor.mp4.TrackSampleTable r9 = new com.google.android.exoplayer2.extractor.mp4.TrackSampleTable
            long[] r2 = new long[r5]
            int[] r3 = new int[r5]
            r4 = 0
            long[] r6 = new long[r5]
            int[] r7 = new int[r5]
            r10 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r0 = r9
            r1 = r35
            r5 = r6
            r6 = r7
            r7 = r10
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            return r9
        L_0x0044:
            r6 = 1937007471(0x7374636f, float:1.9362445E31)
            com.google.android.exoplayer2.extractor.mp4.Atom$LeafAtom r6 = r0.getLeafAtomOfType(r6)
            r7 = 1
            if (r6 != 0) goto L_0x0057
            r6 = 1668232756(0x636f3634, float:4.4126776E21)
            com.google.android.exoplayer2.extractor.mp4.Atom$LeafAtom r6 = r0.getLeafAtomOfType(r6)
            r8 = 1
            goto L_0x0058
        L_0x0057:
            r8 = 0
        L_0x0058:
            com.google.android.exoplayer2.util.ParsableByteArray r6 = r6.data
            r9 = 1937011555(0x73747363, float:1.9367382E31)
            com.google.android.exoplayer2.extractor.mp4.Atom$LeafAtom r9 = r0.getLeafAtomOfType(r9)
            com.google.android.exoplayer2.util.ParsableByteArray r9 = r9.data
            r10 = 1937011827(0x73747473, float:1.9367711E31)
            com.google.android.exoplayer2.extractor.mp4.Atom$LeafAtom r10 = r0.getLeafAtomOfType(r10)
            com.google.android.exoplayer2.util.ParsableByteArray r10 = r10.data
            r11 = 1937011571(0x73747373, float:1.9367401E31)
            com.google.android.exoplayer2.extractor.mp4.Atom$LeafAtom r11 = r0.getLeafAtomOfType(r11)
            r12 = 0
            if (r11 == 0) goto L_0x0079
            com.google.android.exoplayer2.util.ParsableByteArray r11 = r11.data
            goto L_0x007a
        L_0x0079:
            r11 = r12
        L_0x007a:
            r13 = 1668576371(0x63747473, float:4.5093966E21)
            com.google.android.exoplayer2.extractor.mp4.Atom$LeafAtom r0 = r0.getLeafAtomOfType(r13)
            if (r0 == 0) goto L_0x0086
            com.google.android.exoplayer2.util.ParsableByteArray r0 = r0.data
            goto L_0x0087
        L_0x0086:
            r0 = r12
        L_0x0087:
            com.google.android.exoplayer2.extractor.mp4.AtomParsers$ChunkIterator r13 = new com.google.android.exoplayer2.extractor.mp4.AtomParsers$ChunkIterator
            r13.<init>(r9, r6, r8)
            r6 = 12
            r10.setPosition(r6)
            int r8 = r10.readUnsignedIntToInt()
            int r8 = r8 - r7
            int r9 = r10.readUnsignedIntToInt()
            int r14 = r10.readUnsignedIntToInt()
            if (r0 == 0) goto L_0x00a8
            r0.setPosition(r6)
            int r15 = r0.readUnsignedIntToInt()
            goto L_0x00a9
        L_0x00a8:
            r15 = 0
        L_0x00a9:
            r16 = -1
            if (r11 == 0) goto L_0x00bf
            r11.setPosition(r6)
            int r6 = r11.readUnsignedIntToInt()
            if (r6 <= 0) goto L_0x00bd
            int r12 = r11.readUnsignedIntToInt()
            int r16 = r12 + -1
            goto L_0x00c0
        L_0x00bd:
            r11 = r12
            goto L_0x00c0
        L_0x00bf:
            r6 = 0
        L_0x00c0:
            boolean r12 = r4.isFixedSampleSize()
            if (r12 == 0) goto L_0x00da
            com.google.android.exoplayer2.Format r12 = r1.format
            java.lang.String r12 = r12.sampleMimeType
            java.lang.String r5 = "audio/raw"
            boolean r5 = r5.equals(r12)
            if (r5 == 0) goto L_0x00da
            if (r8 != 0) goto L_0x00da
            if (r15 != 0) goto L_0x00da
            if (r6 != 0) goto L_0x00da
            r5 = 1
            goto L_0x00db
        L_0x00da:
            r5 = 0
        L_0x00db:
            r18 = 0
            if (r5 != 0) goto L_0x024c
            long[] r5 = new long[r3]
            int[] r12 = new int[r3]
            long[] r7 = new long[r3]
            r36 = r6
            int[] r6 = new int[r3]
            r28 = r8
            r27 = r10
            r10 = r14
            r21 = r18
            r23 = r21
            r1 = 0
            r8 = 0
            r25 = 0
            r26 = 0
            r14 = r36
            r36 = r15
            r15 = r9
            r9 = r16
            r16 = 0
        L_0x0101:
            java.lang.String r2 = "AtomParsers"
            if (r8 >= r3) goto L_0x01c1
            r29 = r23
            r23 = 1
        L_0x0109:
            if (r16 != 0) goto L_0x0126
            boolean r23 = r13.moveNext()
            if (r23 == 0) goto L_0x0126
            r24 = r14
            r31 = r15
            long r14 = r13.offset
            r32 = r3
            int r3 = r13.numSamples
            r16 = r3
            r29 = r14
            r14 = r24
            r15 = r31
            r3 = r32
            goto L_0x0109
        L_0x0126:
            r32 = r3
            r24 = r14
            r31 = r15
            if (r23 != 0) goto L_0x0147
            java.lang.String r3 = "Unexpected end of chunk data"
            com.google.android.exoplayer2.util.Log.m1396w(r2, r3)
            long[] r5 = java.util.Arrays.copyOf(r5, r8)
            int[] r12 = java.util.Arrays.copyOf(r12, r8)
            long[] r7 = java.util.Arrays.copyOf(r7, r8)
            int[] r6 = java.util.Arrays.copyOf(r6, r8)
            r32 = r8
            goto L_0x01c7
        L_0x0147:
            if (r0 == 0) goto L_0x015e
            r2 = r36
        L_0x014b:
            if (r25 != 0) goto L_0x015a
            if (r2 <= 0) goto L_0x015a
            int r25 = r0.readUnsignedIntToInt()
            int r26 = r0.readInt()
            int r2 = r2 + -1
            goto L_0x014b
        L_0x015a:
            int r25 = r25 + -1
            r3 = r2
            goto L_0x0160
        L_0x015e:
            r3 = r36
        L_0x0160:
            r2 = r26
            r5[r8] = r29
            int r14 = r4.readNextSampleSize()
            r12[r8] = r14
            r14 = r12[r8]
            if (r14 <= r1) goto L_0x0170
            r1 = r12[r8]
        L_0x0170:
            long r14 = (long) r2
            long r14 = r21 + r14
            r7[r8] = r14
            if (r11 != 0) goto L_0x0179
            r14 = 1
            goto L_0x017a
        L_0x0179:
            r14 = 0
        L_0x017a:
            r6[r8] = r14
            if (r8 != r9) goto L_0x018f
            r14 = 1
            r6[r8] = r14
            int r15 = r24 + -1
            if (r15 <= 0) goto L_0x018a
            int r9 = r11.readUnsignedIntToInt()
            int r9 = r9 - r14
        L_0x018a:
            r36 = r1
            r14 = r15
            r15 = r2
            goto L_0x0194
        L_0x018f:
            r36 = r1
            r15 = r2
            r14 = r24
        L_0x0194:
            long r1 = (long) r10
            long r21 = r21 + r1
            int r1 = r31 + -1
            if (r1 != 0) goto L_0x01a8
            if (r28 <= 0) goto L_0x01a8
            int r1 = r27.readUnsignedIntToInt()
            int r2 = r27.readInt()
            int r28 = r28 + -1
            r10 = r2
        L_0x01a8:
            r2 = r12[r8]
            r23 = r1
            long r1 = (long) r2
            long r1 = r29 + r1
            int r16 = r16 + -1
            int r8 = r8 + 1
            r26 = r15
            r15 = r23
            r23 = r1
            r1 = r36
            r36 = r3
            r3 = r32
            goto L_0x0101
        L_0x01c1:
            r32 = r3
            r24 = r14
            r31 = r15
        L_0x01c7:
            r3 = r16
            r15 = r26
            long r8 = (long) r15
            long r21 = r21 + r8
            r4 = r36
        L_0x01d0:
            if (r4 <= 0) goto L_0x01e0
            int r8 = r0.readUnsignedIntToInt()
            if (r8 == 0) goto L_0x01da
            r0 = 0
            goto L_0x01e1
        L_0x01da:
            r0.readInt()
            int r4 = r4 + -1
            goto L_0x01d0
        L_0x01e0:
            r0 = 1
        L_0x01e1:
            if (r24 != 0) goto L_0x01f4
            if (r31 != 0) goto L_0x01f4
            if (r3 != 0) goto L_0x01f4
            if (r28 != 0) goto L_0x01f4
            r4 = r25
            if (r4 != 0) goto L_0x01f6
            if (r0 != 0) goto L_0x01f0
            goto L_0x01f6
        L_0x01f0:
            r9 = r1
            r1 = r35
            goto L_0x0247
        L_0x01f4:
            r4 = r25
        L_0x01f6:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "Inconsistent stbl box for track "
            r8.append(r9)
            r9 = r1
            r1 = r35
            int r10 = r1.f1479id
            r8.append(r10)
            java.lang.String r10 = ": remainingSynchronizationSamples "
            r8.append(r10)
            r14 = r24
            r8.append(r14)
            java.lang.String r10 = ", remainingSamplesAtTimestampDelta "
            r8.append(r10)
            r10 = r31
            r8.append(r10)
            java.lang.String r10 = ", remainingSamplesInChunk "
            r8.append(r10)
            r8.append(r3)
            java.lang.String r3 = ", remainingTimestampDeltaChanges "
            r8.append(r3)
            r3 = r28
            r8.append(r3)
            java.lang.String r3 = ", remainingSamplesAtTimestampOffset "
            r8.append(r3)
            r8.append(r4)
            if (r0 != 0) goto L_0x023b
            java.lang.String r0 = ", ctts invalid"
            goto L_0x023d
        L_0x023b:
            java.lang.String r0 = ""
        L_0x023d:
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            com.google.android.exoplayer2.util.Log.m1396w(r2, r0)
        L_0x0247:
            r2 = r5
            r5 = r7
            r4 = r9
            r3 = r12
            goto L_0x0288
        L_0x024c:
            r32 = r3
            int r0 = r13.length
            long[] r0 = new long[r0]
            int r2 = r13.length
            int[] r2 = new int[r2]
        L_0x0256:
            boolean r3 = r13.moveNext()
            if (r3 == 0) goto L_0x0269
            int r3 = r13.index
            long r4 = r13.offset
            r0[r3] = r4
            int r3 = r13.index
            int r4 = r13.numSamples
            r2[r3] = r4
            goto L_0x0256
        L_0x0269:
            com.google.android.exoplayer2.Format r3 = r1.format
            int r3 = r3.pcmEncoding
            com.google.android.exoplayer2.Format r4 = r1.format
            int r4 = r4.channelCount
            int r3 = com.google.android.exoplayer2.util.Util.getPcmFrameSize(r3, r4)
            long r4 = (long) r14
            com.google.android.exoplayer2.extractor.mp4.FixedSampleSizeRechunker$Results r0 = com.google.android.exoplayer2.extractor.mp4.FixedSampleSizeRechunker.rechunk(r3, r0, r2, r4)
            long[] r2 = r0.offsets
            int[] r3 = r0.sizes
            int r4 = r0.maximumSize
            long[] r5 = r0.timestamps
            int[] r6 = r0.flags
            long r7 = r0.duration
            r21 = r7
        L_0x0288:
            r0 = r32
            r11 = 1000000(0xf4240, double:4.940656E-318)
            long r13 = r1.timescale
            r9 = r21
            long r7 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r9, r11, r13)
            long[] r9 = r1.editListDurations
            r14 = 1000000(0xf4240, double:4.940656E-318)
            if (r9 != 0) goto L_0x02aa
            long r9 = r1.timescale
            com.google.android.exoplayer2.util.Util.scaleLargeTimestampsInPlace(r5, r14, r9)
            com.google.android.exoplayer2.extractor.mp4.TrackSampleTable r9 = new com.google.android.exoplayer2.extractor.mp4.TrackSampleTable
            r0 = r9
            r1 = r35
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            return r9
        L_0x02aa:
            long[] r7 = r1.editListDurations
            int r7 = r7.length
            r8 = 1
            if (r7 != r8) goto L_0x033c
            int r7 = r1.type
            if (r7 != r8) goto L_0x033c
            int r7 = r5.length
            r8 = 2
            if (r7 < r8) goto L_0x033c
            long[] r7 = r1.editListMediaTimes
            r8 = 0
            r23 = r7[r8]
            long[] r7 = r1.editListDurations
            r25 = r7[r8]
            long r7 = r1.timescale
            long r9 = r1.movieTimescale
            r27 = r7
            r29 = r9
            long r7 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r25, r27, r29)
            long r7 = r23 + r7
            r9 = r5
            r10 = r21
            r12 = r23
            r36 = r3
            r16 = r4
            r3 = r14
            r14 = r7
            boolean r9 = canApplyEditWithGaplessInfo(r9, r10, r12, r14)
            if (r9 == 0) goto L_0x0340
            long r10 = r21 - r7
            r7 = 0
            r8 = r5[r7]
            long r25 = r23 - r8
            com.google.android.exoplayer2.Format r7 = r1.format
            int r7 = r7.sampleRate
            long r7 = (long) r7
            long r12 = r1.timescale
            r27 = r7
            r29 = r12
            long r7 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r25, r27, r29)
            com.google.android.exoplayer2.Format r9 = r1.format
            int r9 = r9.sampleRate
            long r12 = (long) r9
            long r14 = r1.timescale
            long r9 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r10, r12, r14)
            int r11 = (r7 > r18 ? 1 : (r7 == r18 ? 0 : -1))
            if (r11 != 0) goto L_0x0309
            int r11 = (r9 > r18 ? 1 : (r9 == r18 ? 0 : -1))
            if (r11 == 0) goto L_0x0340
        L_0x0309:
            r11 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r13 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1))
            if (r13 > 0) goto L_0x0340
            int r13 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r13 > 0) goto L_0x0340
            int r0 = (int) r7
            r7 = r37
            r7.encoderDelay = r0
            int r0 = (int) r9
            r7.encoderPadding = r0
            long r7 = r1.timescale
            com.google.android.exoplayer2.util.Util.scaleLargeTimestampsInPlace(r5, r3, r7)
            long[] r0 = r1.editListDurations
            r3 = 0
            r7 = r0[r3]
            r9 = 1000000(0xf4240, double:4.940656E-318)
            long r11 = r1.movieTimescale
            long r7 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r7, r9, r11)
            com.google.android.exoplayer2.extractor.mp4.TrackSampleTable r9 = new com.google.android.exoplayer2.extractor.mp4.TrackSampleTable
            r0 = r9
            r1 = r35
            r3 = r36
            r4 = r16
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            return r9
        L_0x033c:
            r36 = r3
            r16 = r4
        L_0x0340:
            long[] r3 = r1.editListDurations
            int r3 = r3.length
            r4 = 1
            if (r3 != r4) goto L_0x0381
            long[] r3 = r1.editListDurations
            r4 = 0
            r7 = r3[r4]
            int r3 = (r7 > r18 ? 1 : (r7 == r18 ? 0 : -1))
            if (r3 != 0) goto L_0x0381
            long[] r0 = r1.editListMediaTimes
            r7 = r0[r4]
            r0 = 0
        L_0x0354:
            int r3 = r5.length
            if (r0 >= r3) goto L_0x0369
            r3 = r5[r0]
            long r9 = r3 - r7
            r11 = 1000000(0xf4240, double:4.940656E-318)
            long r13 = r1.timescale
            long r3 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r9, r11, r13)
            r5[r0] = r3
            int r0 = r0 + 1
            goto L_0x0354
        L_0x0369:
            long r9 = r21 - r7
            r11 = 1000000(0xf4240, double:4.940656E-318)
            long r13 = r1.timescale
            long r7 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r9, r11, r13)
            com.google.android.exoplayer2.extractor.mp4.TrackSampleTable r9 = new com.google.android.exoplayer2.extractor.mp4.TrackSampleTable
            r0 = r9
            r1 = r35
            r3 = r36
            r4 = r16
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            return r9
        L_0x0381:
            int r3 = r1.type
            r4 = 1
            if (r3 != r4) goto L_0x0388
            r3 = 1
            goto L_0x0389
        L_0x0388:
            r3 = 0
        L_0x0389:
            long[] r4 = r1.editListDurations
            int r4 = r4.length
            int[] r4 = new int[r4]
            long[] r7 = r1.editListDurations
            int r7 = r7.length
            int[] r7 = new int[r7]
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
        L_0x0397:
            long[] r12 = r1.editListDurations
            int r12 = r12.length
            if (r8 >= r12) goto L_0x03ff
            long[] r12 = r1.editListMediaTimes
            r13 = r12[r8]
            r21 = -1
            int r12 = (r13 > r21 ? 1 : (r13 == r21 ? 0 : -1))
            if (r12 == 0) goto L_0x03f1
            long[] r12 = r1.editListDurations
            r21 = r12[r8]
            r15 = r11
            long r11 = r1.timescale
            r37 = r9
            r27 = r10
            long r9 = r1.movieTimescale
            r23 = r11
            r25 = r9
            long r9 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r21, r23, r25)
            r11 = 1
            int r12 = com.google.android.exoplayer2.util.Util.binarySearchCeil(r5, r13, r11, r11)
            r4[r8] = r12
            long r13 = r13 + r9
            r9 = 0
            int r10 = com.google.android.exoplayer2.util.Util.binarySearchCeil(r5, r13, r3, r9)
            r7[r8] = r10
        L_0x03ca:
            r10 = r4[r8]
            r12 = r7[r8]
            if (r10 >= r12) goto L_0x03dd
            r10 = r4[r8]
            r10 = r6[r10]
            r10 = r10 & r11
            if (r10 != 0) goto L_0x03dd
            r10 = r4[r8]
            int r10 = r10 + r11
            r4[r8] = r10
            goto L_0x03ca
        L_0x03dd:
            r10 = r7[r8]
            r12 = r4[r8]
            int r10 = r10 - r12
            int r10 = r27 + r10
            r12 = r4[r8]
            r13 = r15
            if (r13 == r12) goto L_0x03eb
            r12 = 1
            goto L_0x03ec
        L_0x03eb:
            r12 = 0
        L_0x03ec:
            r12 = r37 | r12
            r13 = r7[r8]
            goto L_0x03fa
        L_0x03f1:
            r37 = r9
            r27 = r10
            r13 = r11
            r9 = 0
            r11 = 1
            r12 = r37
        L_0x03fa:
            int r8 = r8 + 1
            r9 = r12
            r11 = r13
            goto L_0x0397
        L_0x03ff:
            r37 = r9
            r9 = 0
            r11 = 1
            if (r10 == r0) goto L_0x0406
            goto L_0x0407
        L_0x0406:
            r11 = 0
        L_0x0407:
            r0 = r37 | r11
            if (r0 == 0) goto L_0x040e
            long[] r3 = new long[r10]
            goto L_0x040f
        L_0x040e:
            r3 = r2
        L_0x040f:
            if (r0 == 0) goto L_0x0414
            int[] r8 = new int[r10]
            goto L_0x0416
        L_0x0414:
            r8 = r36
        L_0x0416:
            if (r0 == 0) goto L_0x041a
            r16 = 0
        L_0x041a:
            if (r0 == 0) goto L_0x041f
            int[] r11 = new int[r10]
            goto L_0x0420
        L_0x041f:
            r11 = r6
        L_0x0420:
            long[] r10 = new long[r10]
            r20 = r16
            r12 = 0
        L_0x0425:
            long[] r13 = r1.editListDurations
            int r13 = r13.length
            if (r9 >= r13) goto L_0x04b0
            long[] r13 = r1.editListMediaTimes
            r21 = r13[r9]
            r13 = r4[r9]
            r14 = r7[r9]
            if (r0 == 0) goto L_0x0444
            int r15 = r14 - r13
            java.lang.System.arraycopy(r2, r13, r3, r12, r15)
            r23 = r2
            r2 = r36
            java.lang.System.arraycopy(r2, r13, r8, r12, r15)
            java.lang.System.arraycopy(r6, r13, r11, r12, r15)
            goto L_0x0448
        L_0x0444:
            r23 = r2
            r2 = r36
        L_0x0448:
            r15 = r13
            r34 = r20
            r20 = r12
            r12 = r34
        L_0x044f:
            if (r15 >= r14) goto L_0x0493
            r16 = 1000000(0xf4240, double:4.940656E-318)
            r36 = r6
            r24 = r7
            long r6 = r1.movieTimescale
            r25 = r4
            r4 = r12
            r12 = r18
            r26 = r14
            r27 = r15
            r14 = r16
            r16 = r6
            long r6 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r12, r14, r16)
            r12 = r5[r27]
            long r28 = r12 - r21
            r30 = 1000000(0xf4240, double:4.940656E-318)
            long r12 = r1.timescale
            r32 = r12
            long r12 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r28, r30, r32)
            long r6 = r6 + r12
            r10[r20] = r6
            if (r0 == 0) goto L_0x0485
            r6 = r8[r20]
            if (r6 <= r4) goto L_0x0485
            r4 = r2[r27]
        L_0x0485:
            r12 = r4
            int r20 = r20 + 1
            int r15 = r27 + 1
            r6 = r36
            r7 = r24
            r4 = r25
            r14 = r26
            goto L_0x044f
        L_0x0493:
            r25 = r4
            r36 = r6
            r24 = r7
            r4 = r12
            long[] r6 = r1.editListDurations
            r12 = r6[r9]
            long r18 = r18 + r12
            int r9 = r9 + 1
            r6 = r36
            r36 = r2
            r12 = r20
            r2 = r23
            r20 = r4
            r4 = r25
            goto L_0x0425
        L_0x04b0:
            r14 = 1000000(0xf4240, double:4.940656E-318)
            long r4 = r1.movieTimescale
            r12 = r18
            r16 = r4
            long r12 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r12, r14, r16)
            com.google.android.exoplayer2.extractor.mp4.TrackSampleTable r9 = new com.google.android.exoplayer2.extractor.mp4.TrackSampleTable
            r0 = r9
            r1 = r35
            r2 = r3
            r3 = r8
            r4 = r20
            r5 = r10
            r6 = r11
            r7 = r12
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            return r9
        L_0x04cd:
            com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
            java.lang.String r1 = "Track has no sample table size information"
            r0.<init>(r1)
            goto L_0x04d6
        L_0x04d5:
            throw r0
        L_0x04d6:
            goto L_0x04d5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mp4.AtomParsers.parseStbl(com.google.android.exoplayer2.extractor.mp4.Track, com.google.android.exoplayer2.extractor.mp4.Atom$ContainerAtom, com.google.android.exoplayer2.extractor.GaplessInfoHolder):com.google.android.exoplayer2.extractor.mp4.TrackSampleTable");
    }

    public static Metadata parseUdta(LeafAtom leafAtom, boolean z) {
        if (z) {
            return null;
        }
        ParsableByteArray parsableByteArray = leafAtom.data;
        parsableByteArray.setPosition(8);
        while (parsableByteArray.bytesLeft() >= 8) {
            int position = parsableByteArray.getPosition();
            int readInt = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1835365473) {
                parsableByteArray.setPosition(position);
                return parseUdtaMeta(parsableByteArray, position + readInt);
            }
            parsableByteArray.setPosition(position + readInt);
        }
        return null;
    }

    public static Metadata parseMdtaFromMeta(ContainerAtom containerAtom) {
        LeafAtom leafAtomOfType = containerAtom.getLeafAtomOfType(Atom.TYPE_hdlr);
        LeafAtom leafAtomOfType2 = containerAtom.getLeafAtomOfType(Atom.TYPE_keys);
        LeafAtom leafAtomOfType3 = containerAtom.getLeafAtomOfType(Atom.TYPE_ilst);
        if (leafAtomOfType == null || leafAtomOfType2 == null || leafAtomOfType3 == null || parseHdlr(leafAtomOfType.data) != TYPE_mdta) {
            return null;
        }
        ParsableByteArray parsableByteArray = leafAtomOfType2.data;
        parsableByteArray.setPosition(12);
        int readInt = parsableByteArray.readInt();
        String[] strArr = new String[readInt];
        for (int i = 0; i < readInt; i++) {
            int readInt2 = parsableByteArray.readInt();
            parsableByteArray.skipBytes(4);
            strArr[i] = parsableByteArray.readString(readInt2 - 8);
        }
        ParsableByteArray parsableByteArray2 = leafAtomOfType3.data;
        parsableByteArray2.setPosition(8);
        ArrayList arrayList = new ArrayList();
        while (parsableByteArray2.bytesLeft() > 8) {
            int position = parsableByteArray2.getPosition();
            int readInt3 = parsableByteArray2.readInt();
            int readInt4 = parsableByteArray2.readInt() - 1;
            if (readInt4 < 0 || readInt4 >= strArr.length) {
                StringBuilder sb = new StringBuilder();
                sb.append("Skipped metadata with unknown key index: ");
                sb.append(readInt4);
                Log.m1396w(TAG, sb.toString());
            } else {
                MdtaMetadataEntry parseMdtaMetadataEntryFromIlst = MetadataUtil.parseMdtaMetadataEntryFromIlst(parsableByteArray2, position + readInt3, strArr[readInt4]);
                if (parseMdtaMetadataEntryFromIlst != null) {
                    arrayList.add(parseMdtaMetadataEntryFromIlst);
                }
            }
            parsableByteArray2.setPosition(position + readInt3);
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new Metadata((List<? extends Entry>) arrayList);
    }

    private static Metadata parseUdtaMeta(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.skipBytes(12);
        while (parsableByteArray.getPosition() < i) {
            int position = parsableByteArray.getPosition();
            int readInt = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1768715124) {
                parsableByteArray.setPosition(position);
                return parseIlst(parsableByteArray, position + readInt);
            }
            parsableByteArray.setPosition(position + readInt);
        }
        return null;
    }

    private static Metadata parseIlst(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.skipBytes(8);
        ArrayList arrayList = new ArrayList();
        while (parsableByteArray.getPosition() < i) {
            Entry parseIlstElement = MetadataUtil.parseIlstElement(parsableByteArray);
            if (parseIlstElement != null) {
                arrayList.add(parseIlstElement);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new Metadata((List<? extends Entry>) arrayList);
    }

    private static long parseMvhd(ParsableByteArray parsableByteArray) {
        int i = 8;
        parsableByteArray.setPosition(8);
        if (Atom.parseFullAtomVersion(parsableByteArray.readInt()) != 0) {
            i = 16;
        }
        parsableByteArray.skipBytes(i);
        return parsableByteArray.readUnsignedInt();
    }

    private static TkhdData parseTkhd(ParsableByteArray parsableByteArray) {
        boolean z;
        int i = 8;
        parsableByteArray.setPosition(8);
        int parseFullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        parsableByteArray.skipBytes(parseFullAtomVersion == 0 ? 8 : 16);
        int readInt = parsableByteArray.readInt();
        parsableByteArray.skipBytes(4);
        int position = parsableByteArray.getPosition();
        if (parseFullAtomVersion == 0) {
            i = 4;
        }
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i3 >= i) {
                z = true;
                break;
            } else if (parsableByteArray.data[position + i3] != -1) {
                z = false;
                break;
            } else {
                i3++;
            }
        }
        long j = C1996C.TIME_UNSET;
        if (z) {
            parsableByteArray.skipBytes(i);
        } else {
            long readUnsignedInt = parseFullAtomVersion == 0 ? parsableByteArray.readUnsignedInt() : parsableByteArray.readUnsignedLongToLong();
            if (readUnsignedInt != 0) {
                j = readUnsignedInt;
            }
        }
        parsableByteArray.skipBytes(16);
        int readInt2 = parsableByteArray.readInt();
        int readInt3 = parsableByteArray.readInt();
        parsableByteArray.skipBytes(4);
        int readInt4 = parsableByteArray.readInt();
        int readInt5 = parsableByteArray.readInt();
        if (readInt2 == 0 && readInt3 == 65536 && readInt4 == -65536 && readInt5 == 0) {
            i2 = 90;
        } else if (readInt2 == 0 && readInt3 == -65536 && readInt4 == 65536 && readInt5 == 0) {
            i2 = 270;
        } else if (readInt2 == -65536 && readInt3 == 0 && readInt4 == 0 && readInt5 == -65536) {
            i2 = Opcodes.GETFIELD;
        }
        return new TkhdData(readInt, j, i2);
    }

    private static int parseHdlr(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(16);
        return parsableByteArray.readInt();
    }

    private static Pair<Long, String> parseMdhd(ParsableByteArray parsableByteArray) {
        int i = 8;
        parsableByteArray.setPosition(8);
        int parseFullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        parsableByteArray.skipBytes(parseFullAtomVersion == 0 ? 8 : 16);
        long readUnsignedInt = parsableByteArray.readUnsignedInt();
        if (parseFullAtomVersion == 0) {
            i = 4;
        }
        parsableByteArray.skipBytes(i);
        int readUnsignedShort = parsableByteArray.readUnsignedShort();
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append((char) (((readUnsignedShort >> 10) & 31) + 96));
        sb.append((char) (((readUnsignedShort >> 5) & 31) + 96));
        sb.append((char) ((readUnsignedShort & 31) + 96));
        return Pair.create(Long.valueOf(readUnsignedInt), sb.toString());
    }

    private static StsdData parseStsd(ParsableByteArray parsableByteArray, int i, int i2, String str, DrmInitData drmInitData, boolean z) throws ParserException {
        ParsableByteArray parsableByteArray2 = parsableByteArray;
        parsableByteArray2.setPosition(12);
        int readInt = parsableByteArray.readInt();
        StsdData stsdData = new StsdData(readInt);
        for (int i3 = 0; i3 < readInt; i3++) {
            int position = parsableByteArray.getPosition();
            int readInt2 = parsableByteArray.readInt();
            Assertions.checkArgument(readInt2 > 0, "childAtomSize should be positive");
            int readInt3 = parsableByteArray.readInt();
            if (readInt3 == 1635148593 || readInt3 == 1635148595 || readInt3 == 1701733238 || readInt3 == 1836070006 || readInt3 == 1752589105 || readInt3 == 1751479857 || readInt3 == 1932670515 || readInt3 == 1987063864 || readInt3 == 1987063865 || readInt3 == 1635135537 || readInt3 == 1685479798 || readInt3 == 1685479729 || readInt3 == 1685481573 || readInt3 == 1685481521) {
                parseVideoSampleEntry(parsableByteArray, readInt3, position, readInt2, i, i2, drmInitData, stsdData, i3);
            } else if (readInt3 == 1836069985 || readInt3 == 1701733217 || readInt3 == 1633889587 || readInt3 == 1700998451 || readInt3 == 1633889588 || readInt3 == 1685353315 || readInt3 == 1685353317 || readInt3 == 1685353320 || readInt3 == 1685353324 || readInt3 == 1935764850 || readInt3 == 1935767394 || readInt3 == 1819304813 || readInt3 == 1936684916 || readInt3 == 778924083 || readInt3 == 1634492771 || readInt3 == 1634492791 || readInt3 == 1970037111 || readInt3 == 1332770163 || readInt3 == 1716281667) {
                parseAudioSampleEntry(parsableByteArray, readInt3, position, readInt2, i, str, z, drmInitData, stsdData, i3);
            } else if (readInt3 == 1414810956 || readInt3 == 1954034535 || readInt3 == 2004251764 || readInt3 == 1937010800 || readInt3 == 1664495672) {
                parseTextSampleEntry(parsableByteArray, readInt3, position, readInt2, i, str, stsdData);
            } else if (readInt3 == 1667329389) {
                stsdData.format = Format.createSampleFormat(Integer.toString(i), MimeTypes.APPLICATION_CAMERA_MOTION, null, -1, null);
            }
            parsableByteArray2.setPosition(position + readInt2);
        }
        return stsdData;
    }

    private static void parseTextSampleEntry(ParsableByteArray parsableByteArray, int i, int i2, int i3, int i4, String str, StsdData stsdData) throws ParserException {
        ParsableByteArray parsableByteArray2 = parsableByteArray;
        int i5 = i;
        StsdData stsdData2 = stsdData;
        parsableByteArray2.setPosition(i2 + 8 + 8);
        String str2 = MimeTypes.APPLICATION_TTML;
        List list = null;
        long j = Long.MAX_VALUE;
        if (i5 != 1414810956) {
            if (i5 == 1954034535) {
                int i6 = (i3 - 8) - 8;
                byte[] bArr = new byte[i6];
                parsableByteArray2.readBytes(bArr, 0, i6);
                list = Collections.singletonList(bArr);
                str2 = MimeTypes.APPLICATION_TX3G;
            } else if (i5 == 2004251764) {
                str2 = MimeTypes.APPLICATION_MP4VTT;
            } else if (i5 == 1937010800) {
                j = 0;
            } else if (i5 == 1664495672) {
                stsdData2.requiredSampleTransformation = 1;
                str2 = MimeTypes.APPLICATION_MP4CEA608;
            } else {
                throw new IllegalStateException();
            }
        }
        stsdData2.format = Format.createTextSampleFormat(Integer.toString(i4), str2, null, -1, 0, str, -1, null, j, list);
    }

    private static void parseVideoSampleEntry(ParsableByteArray parsableByteArray, int i, int i2, int i3, int i4, int i5, DrmInitData drmInitData, StsdData stsdData, int i6) throws ParserException {
        ParsableByteArray parsableByteArray2 = parsableByteArray;
        int i7 = i2;
        int i8 = i3;
        DrmInitData drmInitData2 = drmInitData;
        StsdData stsdData2 = stsdData;
        parsableByteArray2.setPosition(i7 + 8 + 8);
        parsableByteArray2.skipBytes(16);
        int readUnsignedShort = parsableByteArray.readUnsignedShort();
        int readUnsignedShort2 = parsableByteArray.readUnsignedShort();
        parsableByteArray2.skipBytes(50);
        int position = parsableByteArray.getPosition();
        int i9 = i;
        if (i9 == 1701733238) {
            Pair parseSampleEntryEncryptionData = parseSampleEntryEncryptionData(parsableByteArray2, i7, i8);
            if (parseSampleEntryEncryptionData != null) {
                i9 = ((Integer) parseSampleEntryEncryptionData.first).intValue();
                if (drmInitData2 == null) {
                    drmInitData2 = null;
                } else {
                    drmInitData2 = drmInitData2.copyWithSchemeType(((TrackEncryptionBox) parseSampleEntryEncryptionData.second).schemeType);
                }
                stsdData2.trackEncryptionBoxes[i6] = (TrackEncryptionBox) parseSampleEntryEncryptionData.second;
            }
            parsableByteArray2.setPosition(position);
        }
        DrmInitData drmInitData3 = drmInitData2;
        String str = null;
        String str2 = null;
        List list = null;
        byte[] bArr = null;
        boolean z = false;
        float f = 1.0f;
        int i10 = -1;
        while (position - i7 < i8) {
            parsableByteArray2.setPosition(position);
            int position2 = parsableByteArray.getPosition();
            int readInt = parsableByteArray.readInt();
            if (readInt == 0 && parsableByteArray.getPosition() - i7 == i8) {
                break;
            }
            Assertions.checkArgument(readInt > 0, "childAtomSize should be positive");
            int readInt2 = parsableByteArray.readInt();
            if (readInt2 == 1635148611) {
                Assertions.checkState(str2 == null);
                parsableByteArray2.setPosition(position2 + 8);
                AvcConfig parse = AvcConfig.parse(parsableByteArray);
                list = parse.initializationData;
                stsdData2.nalUnitLengthFieldLength = parse.nalUnitLengthFieldLength;
                if (!z) {
                    f = parse.pixelWidthAspectRatio;
                }
                str2 = MimeTypes.VIDEO_H264;
            } else if (readInt2 == 1752589123) {
                Assertions.checkState(str2 == null);
                parsableByteArray2.setPosition(position2 + 8);
                HevcConfig parse2 = HevcConfig.parse(parsableByteArray);
                list = parse2.initializationData;
                stsdData2.nalUnitLengthFieldLength = parse2.nalUnitLengthFieldLength;
                str2 = MimeTypes.VIDEO_H265;
            } else if (readInt2 == 1685480259 || readInt2 == 1685485123) {
                DolbyVisionConfig parse3 = DolbyVisionConfig.parse(parsableByteArray);
                if (parse3 != null) {
                    str = parse3.codecs;
                    str2 = MimeTypes.VIDEO_DOLBY_VISION;
                }
            } else if (readInt2 == 1987076931) {
                Assertions.checkState(str2 == null);
                str2 = i9 == 1987063864 ? MimeTypes.VIDEO_VP8 : MimeTypes.VIDEO_VP9;
            } else if (readInt2 == 1635135811) {
                Assertions.checkState(str2 == null);
                str2 = MimeTypes.VIDEO_AV1;
            } else if (readInt2 == 1681012275) {
                Assertions.checkState(str2 == null);
                str2 = MimeTypes.VIDEO_H263;
            } else if (readInt2 == 1702061171) {
                Assertions.checkState(str2 == null);
                Pair parseEsdsFromParent = parseEsdsFromParent(parsableByteArray2, position2);
                str2 = (String) parseEsdsFromParent.first;
                list = Collections.singletonList(parseEsdsFromParent.second);
            } else if (readInt2 == 1885434736) {
                f = parsePaspFromParent(parsableByteArray2, position2);
                z = true;
            } else if (readInt2 == 1937126244) {
                bArr = parseProjFromParent(parsableByteArray2, position2, readInt);
            } else if (readInt2 == 1936995172) {
                int readUnsignedByte = parsableByteArray.readUnsignedByte();
                parsableByteArray2.skipBytes(3);
                if (readUnsignedByte == 0) {
                    int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
                    if (readUnsignedByte2 == 0) {
                        i10 = 0;
                    } else if (readUnsignedByte2 == 1) {
                        i10 = 1;
                    } else if (readUnsignedByte2 == 2) {
                        i10 = 2;
                    } else if (readUnsignedByte2 == 3) {
                        i10 = 3;
                    }
                }
            }
            position += readInt;
            i7 = i2;
        }
        if (str2 != null) {
            stsdData2.format = Format.createVideoSampleFormat(Integer.toString(i4), str2, str, -1, -1, readUnsignedShort, readUnsignedShort2, -1.0f, list, i5, f, bArr, i10, null, drmInitData3);
        }
    }

    private static Pair<long[], long[]> parseEdts(ContainerAtom containerAtom) {
        if (containerAtom != null) {
            LeafAtom leafAtomOfType = containerAtom.getLeafAtomOfType(Atom.TYPE_elst);
            if (leafAtomOfType != null) {
                ParsableByteArray parsableByteArray = leafAtomOfType.data;
                parsableByteArray.setPosition(8);
                int parseFullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
                int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
                long[] jArr = new long[readUnsignedIntToInt];
                long[] jArr2 = new long[readUnsignedIntToInt];
                int i = 0;
                while (i < readUnsignedIntToInt) {
                    jArr[i] = parseFullAtomVersion == 1 ? parsableByteArray.readUnsignedLongToLong() : parsableByteArray.readUnsignedInt();
                    jArr2[i] = parseFullAtomVersion == 1 ? parsableByteArray.readLong() : (long) parsableByteArray.readInt();
                    if (parsableByteArray.readShort() == 1) {
                        parsableByteArray.skipBytes(2);
                        i++;
                    } else {
                        throw new IllegalArgumentException("Unsupported media rate.");
                    }
                }
                return Pair.create(jArr, jArr2);
            }
        }
        return Pair.create(null, null);
    }

    private static float parsePaspFromParent(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.setPosition(i + 8);
        return ((float) parsableByteArray.readUnsignedIntToInt()) / ((float) parsableByteArray.readUnsignedIntToInt());
    }

    private static void parseAudioSampleEntry(ParsableByteArray parsableByteArray, int i, int i2, int i3, int i4, String str, boolean z, DrmInitData drmInitData, StsdData stsdData, int i5) throws ParserException {
        int i6;
        int i7;
        int i8;
        List list;
        String str2;
        String str3;
        DrmInitData drmInitData2;
        int i9;
        String str4;
        ParsableByteArray parsableByteArray2 = parsableByteArray;
        int i10 = i2;
        int i11 = i3;
        String str5 = str;
        DrmInitData drmInitData3 = drmInitData;
        StsdData stsdData2 = stsdData;
        parsableByteArray2.setPosition(i10 + 8 + 8);
        if (z) {
            i6 = parsableByteArray.readUnsignedShort();
            parsableByteArray2.skipBytes(6);
        } else {
            parsableByteArray2.skipBytes(8);
            i6 = 0;
        }
        if (i6 == 0 || i6 == 1) {
            i7 = parsableByteArray.readUnsignedShort();
            parsableByteArray2.skipBytes(6);
            i8 = parsableByteArray.readUnsignedFixedPoint1616();
            if (i6 == 1) {
                parsableByteArray2.skipBytes(16);
            }
        } else if (i6 == 2) {
            parsableByteArray2.skipBytes(16);
            int round = (int) Math.round(parsableByteArray.readDouble());
            int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
            parsableByteArray2.skipBytes(20);
            i7 = readUnsignedIntToInt;
            i8 = round;
        } else {
            return;
        }
        int position = parsableByteArray.getPosition();
        int i12 = i;
        if (i12 == 1701733217) {
            Pair parseSampleEntryEncryptionData = parseSampleEntryEncryptionData(parsableByteArray2, i10, i11);
            if (parseSampleEntryEncryptionData != null) {
                i12 = ((Integer) parseSampleEntryEncryptionData.first).intValue();
                if (drmInitData3 == null) {
                    drmInitData3 = null;
                } else {
                    drmInitData3 = drmInitData3.copyWithSchemeType(((TrackEncryptionBox) parseSampleEntryEncryptionData.second).schemeType);
                }
                stsdData2.trackEncryptionBoxes[i5] = (TrackEncryptionBox) parseSampleEntryEncryptionData.second;
            }
            parsableByteArray2.setPosition(position);
        }
        DrmInitData drmInitData4 = drmInitData3;
        String str6 = MimeTypes.AUDIO_RAW;
        String str7 = i12 == 1633889587 ? MimeTypes.AUDIO_AC3 : i12 == 1700998451 ? MimeTypes.AUDIO_E_AC3 : i12 == 1633889588 ? MimeTypes.AUDIO_AC4 : i12 == 1685353315 ? MimeTypes.AUDIO_DTS : (i12 == 1685353320 || i12 == 1685353324) ? MimeTypes.AUDIO_DTS_HD : i12 == 1685353317 ? MimeTypes.AUDIO_DTS_EXPRESS : i12 == 1935764850 ? MimeTypes.AUDIO_AMR_NB : i12 == 1935767394 ? MimeTypes.AUDIO_AMR_WB : (i12 == 1819304813 || i12 == 1936684916) ? str6 : i12 == 778924083 ? MimeTypes.AUDIO_MPEG : i12 == 1634492771 ? MimeTypes.AUDIO_ALAC : i12 == 1634492791 ? MimeTypes.AUDIO_ALAW : i12 == 1970037111 ? MimeTypes.AUDIO_MLAW : i12 == 1332770163 ? MimeTypes.AUDIO_OPUS : i12 == 1716281667 ? MimeTypes.AUDIO_FLAC : null;
        int i13 = i8;
        int i14 = position;
        int i15 = i7;
        byte[] bArr = null;
        String str8 = str7;
        while (i14 - i10 < i11) {
            parsableByteArray2.setPosition(i14);
            int readInt = parsableByteArray.readInt();
            Assertions.checkArgument(readInt > 0, "childAtomSize should be positive");
            int readInt2 = parsableByteArray.readInt();
            if (readInt2 == 1702061171 || (z && readInt2 == 2002876005)) {
                str3 = str6;
                String str9 = str8;
                i9 = i14;
                drmInitData2 = drmInitData4;
                int findEsdsPosition = readInt2 == 1702061171 ? i9 : findEsdsPosition(parsableByteArray2, i9, readInt);
                if (findEsdsPosition != -1) {
                    Pair parseEsdsFromParent = parseEsdsFromParent(parsableByteArray2, findEsdsPosition);
                    str4 = (String) parseEsdsFromParent.first;
                    bArr = (byte[]) parseEsdsFromParent.second;
                    if (MimeTypes.AUDIO_AAC.equals(str4)) {
                        Pair parseAacAudioSpecificConfig = CodecSpecificDataUtil.parseAacAudioSpecificConfig(bArr);
                        i13 = ((Integer) parseAacAudioSpecificConfig.first).intValue();
                        i15 = ((Integer) parseAacAudioSpecificConfig.second).intValue();
                    }
                } else {
                    str4 = str9;
                }
                str2 = str4;
            } else {
                if (readInt2 == 1684103987) {
                    parsableByteArray2.setPosition(i14 + 8);
                    stsdData2.format = Ac3Util.parseAc3AnnexFFormat(parsableByteArray2, Integer.toString(i4), str5, drmInitData4);
                } else if (readInt2 == 1684366131) {
                    parsableByteArray2.setPosition(i14 + 8);
                    stsdData2.format = Ac3Util.parseEAc3AnnexFFormat(parsableByteArray2, Integer.toString(i4), str5, drmInitData4);
                } else if (readInt2 == 1684103988) {
                    parsableByteArray2.setPosition(i14 + 8);
                    stsdData2.format = Ac4Util.parseAc4AnnexEFormat(parsableByteArray2, Integer.toString(i4), str5, drmInitData4);
                } else if (readInt2 == 1684305011) {
                    int i16 = readInt;
                    int i17 = i14;
                    str3 = str6;
                    drmInitData2 = drmInitData4;
                    str2 = str8;
                    stsdData2.format = Format.createAudioSampleFormat(Integer.toString(i4), str8, null, -1, -1, i15, i13, null, drmInitData2, 0, str);
                    readInt = i16;
                    i9 = i17;
                } else {
                    int i18 = readInt;
                    str3 = str6;
                    str2 = str8;
                    int i19 = i14;
                    drmInitData2 = drmInitData4;
                    if (readInt2 == 1682927731) {
                        readInt = i18;
                        int i20 = readInt - 8;
                        byte[] bArr2 = opusMagic;
                        byte[] bArr3 = new byte[(bArr2.length + i20)];
                        System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
                        i9 = i19;
                        parsableByteArray2.setPosition(i9 + 8);
                        parsableByteArray2.readBytes(bArr3, opusMagic.length, i20);
                        bArr = bArr3;
                    } else {
                        readInt = i18;
                        i9 = i19;
                        if (readInt2 == 1684425825) {
                            int i21 = readInt - 12;
                            byte[] bArr4 = new byte[(i21 + 4)];
                            bArr4[0] = 102;
                            bArr4[1] = 76;
                            bArr4[2] = 97;
                            bArr4[3] = 67;
                            parsableByteArray2.setPosition(i9 + 12);
                            parsableByteArray2.readBytes(bArr4, 4, i21);
                            bArr = bArr4;
                        } else if (readInt2 == 1634492771) {
                            int i22 = readInt - 12;
                            byte[] bArr5 = new byte[i22];
                            parsableByteArray2.setPosition(i9 + 12);
                            parsableByteArray2.readBytes(bArr5, 0, i22);
                            Pair parseAlacAudioSpecificConfig = CodecSpecificDataUtil.parseAlacAudioSpecificConfig(bArr5);
                            i13 = ((Integer) parseAlacAudioSpecificConfig.first).intValue();
                            i15 = ((Integer) parseAlacAudioSpecificConfig.second).intValue();
                            bArr = bArr5;
                        }
                    }
                }
                str3 = str6;
                str2 = str8;
                i9 = i14;
                drmInitData2 = drmInitData4;
            }
            i14 = i9 + readInt;
            i10 = i2;
            drmInitData4 = drmInitData2;
            str6 = str3;
            str8 = str2;
        }
        String str10 = str6;
        String str11 = str8;
        DrmInitData drmInitData5 = drmInitData4;
        if (stsdData2.format == null) {
            String str12 = str11;
            if (str12 != null) {
                int i23 = str10.equals(str12) ? 2 : -1;
                String num = Integer.toString(i4);
                if (bArr == null) {
                    list = null;
                } else {
                    list = Collections.singletonList(bArr);
                }
                stsdData2.format = Format.createAudioSampleFormat(num, str12, null, -1, -1, i15, i13, i23, list, drmInitData5, 0, str);
            }
        }
    }

    private static int findEsdsPosition(ParsableByteArray parsableByteArray, int i, int i2) {
        int position = parsableByteArray.getPosition();
        while (position - i < i2) {
            parsableByteArray.setPosition(position);
            int readInt = parsableByteArray.readInt();
            Assertions.checkArgument(readInt > 0, "childAtomSize should be positive");
            if (parsableByteArray.readInt() == 1702061171) {
                return position;
            }
            position += readInt;
        }
        return -1;
    }

    private static Pair<String, byte[]> parseEsdsFromParent(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.setPosition(i + 8 + 4);
        parsableByteArray.skipBytes(1);
        parseExpandableClassSize(parsableByteArray);
        parsableByteArray.skipBytes(2);
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        if ((readUnsignedByte & 128) != 0) {
            parsableByteArray.skipBytes(2);
        }
        if ((readUnsignedByte & 64) != 0) {
            parsableByteArray.skipBytes(parsableByteArray.readUnsignedShort());
        }
        if ((readUnsignedByte & 32) != 0) {
            parsableByteArray.skipBytes(2);
        }
        parsableByteArray.skipBytes(1);
        parseExpandableClassSize(parsableByteArray);
        String mimeTypeFromMp4ObjectType = MimeTypes.getMimeTypeFromMp4ObjectType(parsableByteArray.readUnsignedByte());
        if (MimeTypes.AUDIO_MPEG.equals(mimeTypeFromMp4ObjectType) || MimeTypes.AUDIO_DTS.equals(mimeTypeFromMp4ObjectType) || MimeTypes.AUDIO_DTS_HD.equals(mimeTypeFromMp4ObjectType)) {
            return Pair.create(mimeTypeFromMp4ObjectType, null);
        }
        parsableByteArray.skipBytes(12);
        parsableByteArray.skipBytes(1);
        int parseExpandableClassSize = parseExpandableClassSize(parsableByteArray);
        byte[] bArr = new byte[parseExpandableClassSize];
        parsableByteArray.readBytes(bArr, 0, parseExpandableClassSize);
        return Pair.create(mimeTypeFromMp4ObjectType, bArr);
    }

    private static Pair<Integer, TrackEncryptionBox> parseSampleEntryEncryptionData(ParsableByteArray parsableByteArray, int i, int i2) {
        int position = parsableByteArray.getPosition();
        while (position - i < i2) {
            parsableByteArray.setPosition(position);
            int readInt = parsableByteArray.readInt();
            Assertions.checkArgument(readInt > 0, "childAtomSize should be positive");
            if (parsableByteArray.readInt() == 1936289382) {
                Pair<Integer, TrackEncryptionBox> parseCommonEncryptionSinfFromParent = parseCommonEncryptionSinfFromParent(parsableByteArray, position, readInt);
                if (parseCommonEncryptionSinfFromParent != null) {
                    return parseCommonEncryptionSinfFromParent;
                }
            }
            position += readInt;
        }
        return null;
    }

    static Pair<Integer, TrackEncryptionBox> parseCommonEncryptionSinfFromParent(ParsableByteArray parsableByteArray, int i, int i2) {
        int i3 = i + 8;
        String str = null;
        Object obj = null;
        int i4 = -1;
        int i5 = 0;
        while (i3 - i < i2) {
            parsableByteArray.setPosition(i3);
            int readInt = parsableByteArray.readInt();
            int readInt2 = parsableByteArray.readInt();
            if (readInt2 == 1718775137) {
                obj = Integer.valueOf(parsableByteArray.readInt());
            } else if (readInt2 == 1935894637) {
                parsableByteArray.skipBytes(4);
                str = parsableByteArray.readString(4);
            } else if (readInt2 == 1935894633) {
                i4 = i3;
                i5 = readInt;
            }
            i3 += readInt;
        }
        if (!C1996C.CENC_TYPE_cenc.equals(str) && !C1996C.CENC_TYPE_cbc1.equals(str) && !C1996C.CENC_TYPE_cens.equals(str) && !C1996C.CENC_TYPE_cbcs.equals(str)) {
            return null;
        }
        boolean z = true;
        Assertions.checkArgument(obj != null, "frma atom is mandatory");
        Assertions.checkArgument(i4 != -1, "schi atom is mandatory");
        TrackEncryptionBox parseSchiFromParent = parseSchiFromParent(parsableByteArray, i4, i5, str);
        if (parseSchiFromParent == null) {
            z = false;
        }
        Assertions.checkArgument(z, "tenc atom is mandatory");
        return Pair.create(obj, parseSchiFromParent);
    }

    private static TrackEncryptionBox parseSchiFromParent(ParsableByteArray parsableByteArray, int i, int i2, String str) {
        int i3;
        int i4;
        int i5 = i + 8;
        while (true) {
            byte[] bArr = null;
            if (i5 - i >= i2) {
                return null;
            }
            parsableByteArray.setPosition(i5);
            int readInt = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1952804451) {
                int parseFullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
                parsableByteArray.skipBytes(1);
                if (parseFullAtomVersion == 0) {
                    parsableByteArray.skipBytes(1);
                    i4 = 0;
                    i3 = 0;
                } else {
                    int readUnsignedByte = parsableByteArray.readUnsignedByte();
                    i3 = readUnsignedByte & 15;
                    i4 = (readUnsignedByte & PsExtractor.VIDEO_STREAM_MASK) >> 4;
                }
                boolean z = parsableByteArray.readUnsignedByte() == 1;
                int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
                byte[] bArr2 = new byte[16];
                parsableByteArray.readBytes(bArr2, 0, bArr2.length);
                if (z && readUnsignedByte2 == 0) {
                    int readUnsignedByte3 = parsableByteArray.readUnsignedByte();
                    bArr = new byte[readUnsignedByte3];
                    parsableByteArray.readBytes(bArr, 0, readUnsignedByte3);
                }
                TrackEncryptionBox trackEncryptionBox = new TrackEncryptionBox(z, str, readUnsignedByte2, bArr2, i4, i3, bArr);
                return trackEncryptionBox;
            }
            i5 += readInt;
        }
    }

    private static byte[] parseProjFromParent(ParsableByteArray parsableByteArray, int i, int i2) {
        int i3 = i + 8;
        while (i3 - i < i2) {
            parsableByteArray.setPosition(i3);
            int readInt = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1886547818) {
                return Arrays.copyOfRange(parsableByteArray.data, i3, readInt + i3);
            }
            i3 += readInt;
        }
        return null;
    }

    private static int parseExpandableClassSize(ParsableByteArray parsableByteArray) {
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int i = readUnsignedByte & Opcodes.LAND;
        while ((readUnsignedByte & 128) == 128) {
            readUnsignedByte = parsableByteArray.readUnsignedByte();
            i = (i << 7) | (readUnsignedByte & Opcodes.LAND);
        }
        return i;
    }

    private static boolean canApplyEditWithGaplessInfo(long[] jArr, long j, long j2, long j3) {
        int length = jArr.length - 1;
        int constrainValue = Util.constrainValue(4, 0, length);
        int constrainValue2 = Util.constrainValue(jArr.length - 4, 0, length);
        if (jArr[0] > j2 || j2 >= jArr[constrainValue] || jArr[constrainValue2] >= j3 || j3 > j) {
            return false;
        }
        return true;
    }

    private AtomParsers() {
    }
}

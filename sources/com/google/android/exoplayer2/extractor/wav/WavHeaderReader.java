package com.google.android.exoplayer2.extractor.wav;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.WavUtil;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;

final class WavHeaderReader {
    private static final String TAG = "WavHeaderReader";

    private static final class ChunkHeader {
        public static final int SIZE_IN_BYTES = 8;

        /* renamed from: id */
        public final int f1480id;
        public final long size;

        private ChunkHeader(int i, long j) {
            this.f1480id = i;
            this.size = j;
        }

        public static ChunkHeader peek(ExtractorInput extractorInput, ParsableByteArray parsableByteArray) throws IOException, InterruptedException {
            extractorInput.peekFully(parsableByteArray.data, 0, 8);
            parsableByteArray.setPosition(0);
            return new ChunkHeader(parsableByteArray.readInt(), parsableByteArray.readLittleEndianUnsignedInt());
        }
    }

    public static WavHeader peek(ExtractorInput extractorInput) throws IOException, InterruptedException {
        Assertions.checkNotNull(extractorInput);
        ParsableByteArray parsableByteArray = new ParsableByteArray(16);
        if (ChunkHeader.peek(extractorInput, parsableByteArray).f1480id != 1380533830) {
            return null;
        }
        extractorInput.peekFully(parsableByteArray.data, 0, 4);
        parsableByteArray.setPosition(0);
        int readInt = parsableByteArray.readInt();
        String str = TAG;
        if (readInt != 1463899717) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unsupported RIFF format: ");
            sb.append(readInt);
            Log.m1392e(str, sb.toString());
            return null;
        }
        ChunkHeader peek = ChunkHeader.peek(extractorInput, parsableByteArray);
        while (peek.f1480id != 1718449184) {
            extractorInput.advancePeekPosition((int) peek.size);
            peek = ChunkHeader.peek(extractorInput, parsableByteArray);
        }
        Assertions.checkState(peek.size >= 16);
        extractorInput.peekFully(parsableByteArray.data, 0, 16);
        parsableByteArray.setPosition(0);
        int readLittleEndianUnsignedShort = parsableByteArray.readLittleEndianUnsignedShort();
        int readLittleEndianUnsignedShort2 = parsableByteArray.readLittleEndianUnsignedShort();
        int readLittleEndianUnsignedIntToInt = parsableByteArray.readLittleEndianUnsignedIntToInt();
        int readLittleEndianUnsignedIntToInt2 = parsableByteArray.readLittleEndianUnsignedIntToInt();
        int readLittleEndianUnsignedShort3 = parsableByteArray.readLittleEndianUnsignedShort();
        int readLittleEndianUnsignedShort4 = parsableByteArray.readLittleEndianUnsignedShort();
        int i = (readLittleEndianUnsignedShort2 * readLittleEndianUnsignedShort4) / 8;
        if (readLittleEndianUnsignedShort3 == i) {
            int encodingForType = WavUtil.getEncodingForType(readLittleEndianUnsignedShort, readLittleEndianUnsignedShort4);
            if (encodingForType == 0) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Unsupported WAV format: ");
                sb2.append(readLittleEndianUnsignedShort4);
                sb2.append(" bit/sample, type ");
                sb2.append(readLittleEndianUnsignedShort);
                Log.m1392e(str, sb2.toString());
                return null;
            }
            extractorInput.advancePeekPosition(((int) peek.size) - 16);
            WavHeader wavHeader = new WavHeader(readLittleEndianUnsignedShort2, readLittleEndianUnsignedIntToInt, readLittleEndianUnsignedIntToInt2, readLittleEndianUnsignedShort3, readLittleEndianUnsignedShort4, encodingForType);
            return wavHeader;
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("Expected block alignment: ");
        sb3.append(i);
        sb3.append("; got: ");
        sb3.append(readLittleEndianUnsignedShort3);
        throw new ParserException(sb3.toString());
    }

    public static void skipToData(ExtractorInput extractorInput, WavHeader wavHeader) throws IOException, InterruptedException {
        Assertions.checkNotNull(extractorInput);
        Assertions.checkNotNull(wavHeader);
        extractorInput.resetPeekPosition();
        ParsableByteArray parsableByteArray = new ParsableByteArray(8);
        ChunkHeader peek = ChunkHeader.peek(extractorInput, parsableByteArray);
        while (true) {
            int i = peek.f1480id;
            String str = TAG;
            if (i != 1684108385) {
                if (!(peek.f1480id == 1380533830 || peek.f1480id == 1718449184)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Ignoring unknown WAV chunk: ");
                    sb.append(peek.f1480id);
                    Log.m1396w(str, sb.toString());
                }
                long j = peek.size + 8;
                if (peek.f1480id == 1380533830) {
                    j = 12;
                }
                if (j <= 2147483647L) {
                    extractorInput.skipFully((int) j);
                    peek = ChunkHeader.peek(extractorInput, parsableByteArray);
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Chunk is too large (~2GB+) to skip; id: ");
                    sb2.append(peek.f1480id);
                    throw new ParserException(sb2.toString());
                }
            } else {
                extractorInput.skipFully(8);
                int position = (int) extractorInput.getPosition();
                long j2 = ((long) position) + peek.size;
                long length = extractorInput.getLength();
                if (length != -1 && j2 > length) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Data exceeds input length: ");
                    sb3.append(j2);
                    sb3.append(", ");
                    sb3.append(length);
                    Log.m1396w(str, sb3.toString());
                    j2 = length;
                }
                wavHeader.setDataBounds(position, j2);
                return;
            }
        }
    }

    private WavHeaderReader() {
    }
}

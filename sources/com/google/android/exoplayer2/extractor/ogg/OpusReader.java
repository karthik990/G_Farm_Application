package com.google.android.exoplayer2.extractor.ogg;

import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class OpusReader extends StreamReader {
    private static final int DEFAULT_SEEK_PRE_ROLL_SAMPLES = 3840;
    private static final int OPUS_CODE = 1332770163;
    private static final byte[] OPUS_SIGNATURE = {79, 112, 117, 115, 72, 101, 97, 100};
    private static final int SAMPLE_RATE = 48000;
    private boolean headerRead;

    OpusReader() {
    }

    public static boolean verifyBitstreamType(ParsableByteArray parsableByteArray) {
        int bytesLeft = parsableByteArray.bytesLeft();
        byte[] bArr = OPUS_SIGNATURE;
        if (bytesLeft < bArr.length) {
            return false;
        }
        byte[] bArr2 = new byte[bArr.length];
        parsableByteArray.readBytes(bArr2, 0, bArr.length);
        return Arrays.equals(bArr2, OPUS_SIGNATURE);
    }

    /* access modifiers changed from: protected */
    public void reset(boolean z) {
        super.reset(z);
        if (z) {
            this.headerRead = false;
        }
    }

    /* access modifiers changed from: protected */
    public long preparePayload(ParsableByteArray parsableByteArray) {
        return convertTimeToGranule(getPacketDurationUs(parsableByteArray.data));
    }

    /* access modifiers changed from: protected */
    public boolean readHeaders(ParsableByteArray parsableByteArray, long j, SetupData setupData) {
        boolean z = true;
        if (!this.headerRead) {
            byte[] copyOf = Arrays.copyOf(parsableByteArray.data, parsableByteArray.limit());
            byte b = copyOf[9] & 255;
            byte b2 = ((copyOf[11] & 255) << 8) | (copyOf[10] & 255);
            ArrayList arrayList = new ArrayList(3);
            arrayList.add(copyOf);
            putNativeOrderLong(arrayList, b2);
            putNativeOrderLong(arrayList, DEFAULT_SEEK_PRE_ROLL_SAMPLES);
            setupData.format = Format.createAudioSampleFormat(null, MimeTypes.AUDIO_OPUS, null, -1, -1, b, SAMPLE_RATE, arrayList, null, 0, null);
            this.headerRead = true;
            return true;
        }
        if (parsableByteArray.readInt() != 1332770163) {
            z = false;
        }
        parsableByteArray.setPosition(0);
        return z;
    }

    private void putNativeOrderLong(List<byte[]> list, int i) {
        list.add(ByteBuffer.allocate(8).order(ByteOrder.nativeOrder()).putLong((((long) i) * C1996C.NANOS_PER_SECOND) / 48000).array());
    }

    private long getPacketDurationUs(byte[] bArr) {
        byte b = bArr[0] & 255;
        byte b2 = b & 3;
        byte b3 = 2;
        if (b2 == 0) {
            b3 = 1;
        } else if (!(b2 == 1 || b2 == 2)) {
            b3 = bArr[1] & 63;
        }
        int i = b >> 3;
        int i2 = i & 3;
        int i3 = i >= 16 ? 2500 << i2 : i >= 12 ? 10000 << (i2 & 1) : i2 == 3 ? 60000 : 10000 << i2;
        return ((long) b3) * ((long) i3);
    }
}

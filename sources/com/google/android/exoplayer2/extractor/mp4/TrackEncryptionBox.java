package com.google.android.exoplayer2.extractor.mp4;

import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.extractor.TrackOutput.CryptoData;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;

public final class TrackEncryptionBox {
    private static final String TAG = "TrackEncryptionBox";
    public final CryptoData cryptoData;
    public final byte[] defaultInitializationVector;
    public final boolean isEncrypted;
    public final int perSampleIvSize;
    public final String schemeType;

    public TrackEncryptionBox(boolean z, String str, int i, byte[] bArr, int i2, int i3, byte[] bArr2) {
        boolean z2 = true;
        boolean z3 = i == 0;
        if (bArr2 != null) {
            z2 = false;
        }
        Assertions.checkArgument(z2 ^ z3);
        this.isEncrypted = z;
        this.schemeType = str;
        this.perSampleIvSize = i;
        this.defaultInitializationVector = bArr2;
        this.cryptoData = new CryptoData(schemeToCryptoMode(str), bArr, i2, i3);
    }

    private static int schemeToCryptoMode(String str) {
        if (str == null) {
            return 1;
        }
        char c = 65535;
        switch (str.hashCode()) {
            case 3046605:
                if (str.equals(C1996C.CENC_TYPE_cbc1)) {
                    c = 2;
                    break;
                }
                break;
            case 3046671:
                if (str.equals(C1996C.CENC_TYPE_cbcs)) {
                    c = 3;
                    break;
                }
                break;
            case 3049879:
                if (str.equals(C1996C.CENC_TYPE_cenc)) {
                    c = 0;
                    break;
                }
                break;
            case 3049895:
                if (str.equals(C1996C.CENC_TYPE_cens)) {
                    c = 1;
                    break;
                }
                break;
        }
        if (c == 0 || c == 1) {
            return 1;
        }
        if (c == 2 || c == 3) {
            return 2;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unsupported protection scheme type '");
        sb.append(str);
        sb.append("'. Assuming AES-CTR crypto mode.");
        Log.m1396w(TAG, sb.toString());
        return 1;
    }
}

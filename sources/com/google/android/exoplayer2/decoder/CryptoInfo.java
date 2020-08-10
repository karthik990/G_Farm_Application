package com.google.android.exoplayer2.decoder;

import android.media.MediaCodec.CryptoInfo.Pattern;
import com.google.android.exoplayer2.util.Util;

public final class CryptoInfo {
    public int clearBlocks;
    public int encryptedBlocks;
    private final android.media.MediaCodec.CryptoInfo frameworkCryptoInfo = new android.media.MediaCodec.CryptoInfo();

    /* renamed from: iv */
    public byte[] f1477iv;
    public byte[] key;
    public int mode;
    public int[] numBytesOfClearData;
    public int[] numBytesOfEncryptedData;
    public int numSubSamples;
    private final PatternHolderV24 patternHolder;

    private static final class PatternHolderV24 {
        private final android.media.MediaCodec.CryptoInfo frameworkCryptoInfo;
        private final Pattern pattern;

        private PatternHolderV24(android.media.MediaCodec.CryptoInfo cryptoInfo) {
            this.frameworkCryptoInfo = cryptoInfo;
            this.pattern = new Pattern(0, 0);
        }

        /* access modifiers changed from: private */
        public void set(int i, int i2) {
            this.pattern.set(i, i2);
            this.frameworkCryptoInfo.setPattern(this.pattern);
        }
    }

    public CryptoInfo() {
        this.patternHolder = Util.SDK_INT >= 24 ? new PatternHolderV24(this.frameworkCryptoInfo) : null;
    }

    public void set(int i, int[] iArr, int[] iArr2, byte[] bArr, byte[] bArr2, int i2, int i3, int i4) {
        this.numSubSamples = i;
        this.numBytesOfClearData = iArr;
        this.numBytesOfEncryptedData = iArr2;
        this.key = bArr;
        this.f1477iv = bArr2;
        this.mode = i2;
        this.encryptedBlocks = i3;
        this.clearBlocks = i4;
        android.media.MediaCodec.CryptoInfo cryptoInfo = this.frameworkCryptoInfo;
        cryptoInfo.numSubSamples = i;
        cryptoInfo.numBytesOfClearData = iArr;
        cryptoInfo.numBytesOfEncryptedData = iArr2;
        cryptoInfo.key = bArr;
        cryptoInfo.iv = bArr2;
        cryptoInfo.mode = i2;
        if (Util.SDK_INT >= 24) {
            this.patternHolder.set(i3, i4);
        }
    }

    public android.media.MediaCodec.CryptoInfo getFrameworkCryptoInfo() {
        return this.frameworkCryptoInfo;
    }

    @Deprecated
    public android.media.MediaCodec.CryptoInfo getFrameworkCryptoInfoV16() {
        return getFrameworkCryptoInfo();
    }
}

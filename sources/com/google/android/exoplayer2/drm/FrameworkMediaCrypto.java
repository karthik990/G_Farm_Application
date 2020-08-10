package com.google.android.exoplayer2.drm;

import java.util.UUID;

public final class FrameworkMediaCrypto implements ExoMediaCrypto {
    public static final boolean WORKAROUND_DEVICE_NEEDS_KEYS_TO_CONFIGURE_CODEC;
    public final boolean forceAllowInsecureDecoderComponents;
    public final byte[] sessionId;
    public final UUID uuid;

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x001c, code lost:
        if ("AFTB".equals(com.google.android.exoplayer2.util.Util.MODEL) != false) goto L_0x001e;
     */
    static {
        /*
            java.lang.String r0 = com.google.android.exoplayer2.util.Util.MANUFACTURER
            java.lang.String r1 = "Amazon"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0020
            java.lang.String r0 = com.google.android.exoplayer2.util.Util.MODEL
            java.lang.String r1 = "AFTM"
            boolean r0 = r1.equals(r0)
            if (r0 != 0) goto L_0x001e
            java.lang.String r0 = com.google.android.exoplayer2.util.Util.MODEL
            java.lang.String r1 = "AFTB"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0020
        L_0x001e:
            r0 = 1
            goto L_0x0021
        L_0x0020:
            r0 = 0
        L_0x0021:
            WORKAROUND_DEVICE_NEEDS_KEYS_TO_CONFIGURE_CODEC = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.drm.FrameworkMediaCrypto.<clinit>():void");
    }

    public FrameworkMediaCrypto(UUID uuid2, byte[] bArr, boolean z) {
        this.uuid = uuid2;
        this.sessionId = bArr;
        this.forceAllowInsecureDecoderComponents = z;
    }
}

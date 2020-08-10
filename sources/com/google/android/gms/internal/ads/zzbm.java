package com.google.android.gms.internal.ads;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

final class zzbm implements Runnable {
    private zzbm() {
    }

    public final void run() {
        try {
            zzbk.zzhz = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
        } catch (NoSuchAlgorithmException unused) {
        } catch (Throwable th) {
            zzbk.zzic.countDown();
            throw th;
        }
        zzbk.zzic.countDown();
    }
}

package com.google.android.gms.internal.firebase_auth;

import java.io.IOException;

public abstract class zzgr {
    int zzwe;
    int zzwf;
    int zzwg;
    zzgy zzwh;
    private boolean zzwi;

    public static long zza(long j) {
        return (-(j & 1)) ^ (j >>> 1);
    }

    static zzgr zza(byte[] bArr, int i, int i2, boolean z) {
        zzgt zzgt = new zzgt(bArr, 0, i2, false);
        try {
            zzgt.zzu(i2);
            return zzgt;
        } catch (zzic e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static int zzw(int i) {
        return (-(i & 1)) ^ (i >>> 1);
    }

    public abstract double readDouble() throws IOException;

    public abstract float readFloat() throws IOException;

    public abstract String readString() throws IOException;

    public abstract int zzgi() throws IOException;

    public abstract long zzgj() throws IOException;

    public abstract long zzgk() throws IOException;

    public abstract int zzgl() throws IOException;

    public abstract long zzgm() throws IOException;

    public abstract int zzgn() throws IOException;

    public abstract boolean zzgo() throws IOException;

    public abstract String zzgp() throws IOException;

    public abstract zzgf zzgq() throws IOException;

    public abstract int zzgr() throws IOException;

    public abstract int zzgs() throws IOException;

    public abstract int zzgt() throws IOException;

    public abstract long zzgu() throws IOException;

    public abstract int zzgv() throws IOException;

    public abstract long zzgw() throws IOException;

    /* access modifiers changed from: 0000 */
    public abstract long zzgx() throws IOException;

    public abstract boolean zzgy() throws IOException;

    public abstract int zzgz();

    public abstract void zzs(int i) throws zzic;

    public abstract boolean zzt(int i) throws IOException;

    public abstract int zzu(int i) throws zzic;

    public abstract void zzv(int i);

    private zzgr() {
        this.zzwf = 100;
        this.zzwg = Integer.MAX_VALUE;
        this.zzwi = false;
    }
}

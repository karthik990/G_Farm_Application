package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;

public abstract class zzgi {
    int zzox;
    int zzoy;
    int zzoz;
    zzgn zzpa;
    private boolean zzpb;

    static zzgi zza(byte[] bArr, int i, int i2, boolean z) {
        zzgk zzgk = new zzgk(bArr, 0, i2, false);
        try {
            zzgk.zzaa(i2);
            return zzgk;
        } catch (zzho e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static int zzac(int i) {
        return (-(i & 1)) ^ (i >>> 1);
    }

    public static long zze(long j) {
        return (-(j & 1)) ^ (j >>> 1);
    }

    public abstract double readDouble() throws IOException;

    public abstract float readFloat() throws IOException;

    public abstract String readString() throws IOException;

    public abstract int zzaa(int i) throws zzho;

    public abstract void zzab(int i);

    public abstract int zzfd() throws IOException;

    public abstract long zzfe() throws IOException;

    public abstract long zzff() throws IOException;

    public abstract int zzfg() throws IOException;

    public abstract long zzfh() throws IOException;

    public abstract int zzfi() throws IOException;

    public abstract boolean zzfj() throws IOException;

    public abstract String zzfk() throws IOException;

    public abstract zzfw zzfl() throws IOException;

    public abstract int zzfm() throws IOException;

    public abstract int zzfn() throws IOException;

    public abstract int zzfo() throws IOException;

    public abstract long zzfp() throws IOException;

    public abstract int zzfq() throws IOException;

    public abstract long zzfr() throws IOException;

    /* access modifiers changed from: 0000 */
    public abstract long zzfs() throws IOException;

    public abstract boolean zzft() throws IOException;

    public abstract int zzfu();

    public abstract void zzy(int i) throws zzho;

    public abstract boolean zzz(int i) throws IOException;

    private zzgi() {
        this.zzoy = 100;
        this.zzoz = Integer.MAX_VALUE;
        this.zzpb = false;
    }
}

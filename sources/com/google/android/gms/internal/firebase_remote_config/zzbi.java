package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

final class zzbi extends zzay {
    private final zzfl zzdv;
    private final zzbg zzdw;

    zzbi(zzbg zzbg, zzfl zzfl) {
        this.zzdw = zzbg;
        this.zzdv = zzfl;
        zzfl.setLenient(true);
    }

    public final void flush() throws IOException {
        this.zzdv.flush();
    }

    public final void writeBoolean(boolean z) throws IOException {
        this.zzdv.zzd(z);
    }

    public final void zzat() throws IOException {
        this.zzdv.zzef();
    }

    public final void zzav() throws IOException {
        this.zzdv.zzeh();
    }

    public final void zzad(String str) throws IOException {
        this.zzdv.zzbh(str);
    }

    public final void zzaw() throws IOException {
        this.zzdv.zzek();
    }

    public final void zze(int i) throws IOException {
        this.zzdv.zzd((long) i);
    }

    public final void zza(long j) throws IOException {
        this.zzdv.zzd(j);
    }

    public final void zza(BigInteger bigInteger) throws IOException {
        this.zzdv.zza(bigInteger);
    }

    public final void zza(double d) throws IOException {
        this.zzdv.zzb(d);
    }

    public final void zza(float f) throws IOException {
        this.zzdv.zzb((double) f);
    }

    public final void zza(BigDecimal bigDecimal) throws IOException {
        this.zzdv.zza(bigDecimal);
    }

    public final void zzas() throws IOException {
        this.zzdv.zzee();
    }

    public final void zzau() throws IOException {
        this.zzdv.zzeg();
    }

    public final void writeString(String str) throws IOException {
        this.zzdv.zzbi(str);
    }

    public final void zzax() throws IOException {
        this.zzdv.setIndent("  ");
    }
}

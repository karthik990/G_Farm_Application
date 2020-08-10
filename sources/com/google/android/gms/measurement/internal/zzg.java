package com.google.android.gms.measurement.internal;

import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;

final class zzg {
    private final zzbw zzada;
    private long zzade;
    private String zzafh;
    private String zzafi;
    private String zzafj;
    private String zzafk;
    private long zzafl;
    private long zzafm;
    private long zzafn;
    private long zzafo;
    private String zzafp;
    private long zzafq;
    private boolean zzafr;
    private long zzafs;
    private boolean zzaft;
    private boolean zzafu;
    private String zzafv;
    private long zzafw;
    private long zzafx;
    private long zzafy;
    private long zzafz;
    private long zzaga;
    private long zzagb;
    private String zzagc;
    private boolean zzagd;
    private long zzage;
    private long zzagf;
    private String zzts;
    private final String zztt;

    zzg(zzbw zzbw, String str) {
        Preconditions.checkNotNull(zzbw);
        Preconditions.checkNotEmpty(str);
        this.zzada = zzbw;
        this.zztt = str;
        this.zzada.zzgs().zzaf();
    }

    public final void zzha() {
        this.zzada.zzgs().zzaf();
        this.zzagd = false;
    }

    public final String zzal() {
        this.zzada.zzgs().zzaf();
        return this.zztt;
    }

    public final String getAppInstanceId() {
        this.zzada.zzgs().zzaf();
        return this.zzafh;
    }

    public final void zzaj(String str) {
        this.zzada.zzgs().zzaf();
        this.zzagd |= !zzfx.zzv(this.zzafh, str);
        this.zzafh = str;
    }

    public final String getGmpAppId() {
        this.zzada.zzgs().zzaf();
        return this.zzafi;
    }

    public final void zzak(String str) {
        this.zzada.zzgs().zzaf();
        if (TextUtils.isEmpty(str)) {
            str = null;
        }
        this.zzagd |= !zzfx.zzv(this.zzafi, str);
        this.zzafi = str;
    }

    public final String zzhb() {
        this.zzada.zzgs().zzaf();
        return this.zzafv;
    }

    public final void zzal(String str) {
        this.zzada.zzgs().zzaf();
        if (TextUtils.isEmpty(str)) {
            str = null;
        }
        this.zzagd |= !zzfx.zzv(this.zzafv, str);
        this.zzafv = str;
    }

    public final String zzhc() {
        this.zzada.zzgs().zzaf();
        return this.zzafj;
    }

    public final void zzam(String str) {
        this.zzada.zzgs().zzaf();
        this.zzagd |= !zzfx.zzv(this.zzafj, str);
        this.zzafj = str;
    }

    public final String getFirebaseInstanceId() {
        this.zzada.zzgs().zzaf();
        return this.zzafk;
    }

    public final void zzan(String str) {
        this.zzada.zzgs().zzaf();
        this.zzagd |= !zzfx.zzv(this.zzafk, str);
        this.zzafk = str;
    }

    public final long zzhd() {
        this.zzada.zzgs().zzaf();
        return this.zzafm;
    }

    public final void zzo(long j) {
        this.zzada.zzgs().zzaf();
        this.zzagd |= this.zzafm != j;
        this.zzafm = j;
    }

    public final long zzhe() {
        this.zzada.zzgs().zzaf();
        return this.zzafn;
    }

    public final void zzp(long j) {
        this.zzada.zzgs().zzaf();
        this.zzagd |= this.zzafn != j;
        this.zzafn = j;
    }

    public final String zzak() {
        this.zzada.zzgs().zzaf();
        return this.zzts;
    }

    public final void setAppVersion(String str) {
        this.zzada.zzgs().zzaf();
        this.zzagd |= !zzfx.zzv(this.zzts, str);
        this.zzts = str;
    }

    public final long zzhf() {
        this.zzada.zzgs().zzaf();
        return this.zzafo;
    }

    public final void zzq(long j) {
        this.zzada.zzgs().zzaf();
        this.zzagd |= this.zzafo != j;
        this.zzafo = j;
    }

    public final String zzhg() {
        this.zzada.zzgs().zzaf();
        return this.zzafp;
    }

    public final void zzao(String str) {
        this.zzada.zzgs().zzaf();
        this.zzagd |= !zzfx.zzv(this.zzafp, str);
        this.zzafp = str;
    }

    public final long zzhh() {
        this.zzada.zzgs().zzaf();
        return this.zzade;
    }

    public final void zzr(long j) {
        this.zzada.zzgs().zzaf();
        this.zzagd |= this.zzade != j;
        this.zzade = j;
    }

    public final long zzhi() {
        this.zzada.zzgs().zzaf();
        return this.zzafq;
    }

    public final void zzs(long j) {
        this.zzada.zzgs().zzaf();
        this.zzagd |= this.zzafq != j;
        this.zzafq = j;
    }

    public final boolean isMeasurementEnabled() {
        this.zzada.zzgs().zzaf();
        return this.zzafr;
    }

    public final void setMeasurementEnabled(boolean z) {
        this.zzada.zzgs().zzaf();
        this.zzagd |= this.zzafr != z;
        this.zzafr = z;
    }

    public final void zzt(long j) {
        boolean z = true;
        Preconditions.checkArgument(j >= 0);
        this.zzada.zzgs().zzaf();
        boolean z2 = this.zzagd;
        if (this.zzafl == j) {
            z = false;
        }
        this.zzagd = z | z2;
        this.zzafl = j;
    }

    public final long zzhj() {
        this.zzada.zzgs().zzaf();
        return this.zzafl;
    }

    public final long zzhk() {
        this.zzada.zzgs().zzaf();
        return this.zzage;
    }

    public final void zzu(long j) {
        this.zzada.zzgs().zzaf();
        this.zzagd |= this.zzage != j;
        this.zzage = j;
    }

    public final long zzhl() {
        this.zzada.zzgs().zzaf();
        return this.zzagf;
    }

    public final void zzv(long j) {
        this.zzada.zzgs().zzaf();
        this.zzagd |= this.zzagf != j;
        this.zzagf = j;
    }

    public final void zzhm() {
        this.zzada.zzgs().zzaf();
        long j = this.zzafl + 1;
        if (j > 2147483647L) {
            this.zzada.zzgt().zzjj().zzg("Bundle index overflow. appId", zzas.zzbw(this.zztt));
            j = 0;
        }
        this.zzagd = true;
        this.zzafl = j;
    }

    public final long zzhn() {
        this.zzada.zzgs().zzaf();
        return this.zzafw;
    }

    public final void zzw(long j) {
        this.zzada.zzgs().zzaf();
        this.zzagd |= this.zzafw != j;
        this.zzafw = j;
    }

    public final long zzho() {
        this.zzada.zzgs().zzaf();
        return this.zzafx;
    }

    public final void zzx(long j) {
        this.zzada.zzgs().zzaf();
        this.zzagd |= this.zzafx != j;
        this.zzafx = j;
    }

    public final long zzhp() {
        this.zzada.zzgs().zzaf();
        return this.zzafy;
    }

    public final void zzy(long j) {
        this.zzada.zzgs().zzaf();
        this.zzagd |= this.zzafy != j;
        this.zzafy = j;
    }

    public final long zzhq() {
        this.zzada.zzgs().zzaf();
        return this.zzafz;
    }

    public final void zzz(long j) {
        this.zzada.zzgs().zzaf();
        this.zzagd |= this.zzafz != j;
        this.zzafz = j;
    }

    public final long zzhr() {
        this.zzada.zzgs().zzaf();
        return this.zzagb;
    }

    public final void zzaa(long j) {
        this.zzada.zzgs().zzaf();
        this.zzagd |= this.zzagb != j;
        this.zzagb = j;
    }

    public final long zzhs() {
        this.zzada.zzgs().zzaf();
        return this.zzaga;
    }

    public final void zzab(long j) {
        this.zzada.zzgs().zzaf();
        this.zzagd |= this.zzaga != j;
        this.zzaga = j;
    }

    public final String zzht() {
        this.zzada.zzgs().zzaf();
        return this.zzagc;
    }

    public final String zzhu() {
        this.zzada.zzgs().zzaf();
        String str = this.zzagc;
        zzap(null);
        return str;
    }

    public final void zzap(String str) {
        this.zzada.zzgs().zzaf();
        this.zzagd |= !zzfx.zzv(this.zzagc, str);
        this.zzagc = str;
    }

    public final long zzhv() {
        this.zzada.zzgs().zzaf();
        return this.zzafs;
    }

    public final void zzac(long j) {
        this.zzada.zzgs().zzaf();
        this.zzagd |= this.zzafs != j;
        this.zzafs = j;
    }

    public final boolean zzhw() {
        this.zzada.zzgs().zzaf();
        return this.zzaft;
    }

    public final void zze(boolean z) {
        this.zzada.zzgs().zzaf();
        this.zzagd = this.zzaft != z;
        this.zzaft = z;
    }

    public final boolean zzhx() {
        this.zzada.zzgs().zzaf();
        return this.zzafu;
    }

    public final void zzf(boolean z) {
        this.zzada.zzgs().zzaf();
        this.zzagd = this.zzafu != z;
        this.zzafu = z;
    }
}

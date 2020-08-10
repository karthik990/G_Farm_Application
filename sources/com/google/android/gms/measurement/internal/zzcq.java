package com.google.android.gms.measurement.internal;

final class zzcq implements Runnable {
    private final /* synthetic */ String zzaeb;
    private final /* synthetic */ String zzagj;
    private final /* synthetic */ zzby zzaqo;
    private final /* synthetic */ String zzaqr;
    private final /* synthetic */ long zzaqs;

    zzcq(zzby zzby, String str, String str2, String str3, long j) {
        this.zzaqo = zzby;
        this.zzaqr = str;
        this.zzagj = str2;
        this.zzaeb = str3;
        this.zzaqs = j;
    }

    public final void run() {
        String str = this.zzaqr;
        if (str == null) {
            this.zzaqo.zzamx.zzmh().zzgm().zza(this.zzagj, (zzdx) null);
            return;
        }
        this.zzaqo.zzamx.zzmh().zzgm().zza(this.zzagj, new zzdx(this.zzaeb, str, this.zzaqs));
    }
}

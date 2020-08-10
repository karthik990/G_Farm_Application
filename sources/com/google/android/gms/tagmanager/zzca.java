package com.google.android.gms.tagmanager;

final class zzca implements Runnable {
    private final /* synthetic */ zzby zzahf;
    private final /* synthetic */ long zzahg;
    private final /* synthetic */ String zzahh;
    private final /* synthetic */ zzbz zzahi;

    zzca(zzbz zzbz, zzby zzby, long j, String str) {
        this.zzahi = zzbz;
        this.zzahf = zzby;
        this.zzahg = j;
        this.zzahh = str;
    }

    public final void run() {
        if (this.zzahi.zzahe == null) {
            zzfn zzjq = zzfn.zzjq();
            zzjq.zza(this.zzahi.zzrm, this.zzahf);
            this.zzahi.zzahe = zzjq.zzjr();
        }
        this.zzahi.zzahe.zzb(this.zzahg, this.zzahh);
    }
}

package com.google.android.gms.tagmanager;

final class zzec implements zzfw {
    private final /* synthetic */ zzeb zzaik;

    zzec(zzeb zzeb) {
        this.zzaik = zzeb;
    }

    public final void zza(zzbw zzbw) {
        this.zzaik.zze(zzbw.zzih());
    }

    public final void zzb(zzbw zzbw) {
        this.zzaik.zze(zzbw.zzih());
        long zzih = zzbw.zzih();
        StringBuilder sb = new StringBuilder(57);
        sb.append("Permanent failure dispatching hitId: ");
        sb.append(zzih);
        zzdi.zzab(sb.toString());
    }

    public final void zzc(zzbw zzbw) {
        long zzii = zzbw.zzii();
        if (zzii == 0) {
            this.zzaik.zzb(zzbw.zzih(), this.zzaik.zzsd.currentTimeMillis());
            return;
        }
        if (zzii + 14400000 < this.zzaik.zzsd.currentTimeMillis()) {
            this.zzaik.zze(zzbw.zzih());
            long zzih = zzbw.zzih();
            StringBuilder sb = new StringBuilder(47);
            sb.append("Giving up on failed hitId: ");
            sb.append(zzih);
            zzdi.zzab(sb.toString());
        }
    }
}

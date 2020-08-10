package com.google.android.gms.internal.gtm;

import java.io.IOException;

public final class zzj extends zzuq<zzj> {
    private static volatile zzj[] zzqg;
    public String name;
    private zzl zzqh;
    public zzh zzqi;

    public static zzj[] zzz() {
        if (zzqg == null) {
            synchronized (zzuu.zzbhk) {
                if (zzqg == null) {
                    zzqg = new zzj[0];
                }
            }
        }
        return zzqg;
    }

    public zzj() {
        this.name = "";
        this.zzqh = null;
        this.zzqi = null;
        this.zzbhb = null;
        this.zzbhl = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzj)) {
            return false;
        }
        zzj zzj = (zzj) obj;
        String str = this.name;
        if (str == null) {
            if (zzj.name != null) {
                return false;
            }
        } else if (!str.equals(zzj.name)) {
            return false;
        }
        zzl zzl = this.zzqh;
        if (zzl == null) {
            if (zzj.zzqh != null) {
                return false;
            }
        } else if (!zzl.equals(zzj.zzqh)) {
            return false;
        }
        zzh zzh = this.zzqi;
        if (zzh == null) {
            if (zzj.zzqi != null) {
                return false;
            }
        } else if (!zzh.equals(zzj.zzqi)) {
            return false;
        }
        if (this.zzbhb == null || this.zzbhb.isEmpty()) {
            return zzj.zzbhb == null || zzj.zzbhb.isEmpty();
        }
        return this.zzbhb.equals(zzj.zzbhb);
    }

    public final int hashCode() {
        int i;
        int i2;
        int hashCode = (getClass().getName().hashCode() + 527) * 31;
        String str = this.name;
        int i3 = 0;
        int hashCode2 = hashCode + (str == null ? 0 : str.hashCode());
        zzl zzl = this.zzqh;
        int i4 = hashCode2 * 31;
        if (zzl == null) {
            i = 0;
        } else {
            i = zzl.hashCode();
        }
        int i5 = i4 + i;
        zzh zzh = this.zzqi;
        int i6 = i5 * 31;
        if (zzh == null) {
            i2 = 0;
        } else {
            i2 = zzh.hashCode();
        }
        int i7 = (i6 + i2) * 31;
        if (this.zzbhb != null && !this.zzbhb.isEmpty()) {
            i3 = this.zzbhb.hashCode();
        }
        return i7 + i3;
    }

    public final void zza(zzuo zzuo) throws IOException {
        String str = this.name;
        if (str != null && !str.equals("")) {
            zzuo.zza(1, this.name);
        }
        zzl zzl = this.zzqh;
        if (zzl != null) {
            zzuo.zza(2, (zzuw) zzl);
        }
        zzh zzh = this.zzqi;
        if (zzh != null) {
            zzuo.zza(3, (zzuw) zzh);
        }
        super.zza(zzuo);
    }

    /* access modifiers changed from: protected */
    public final int zzy() {
        int zzy = super.zzy();
        String str = this.name;
        if (str != null && !str.equals("")) {
            zzy += zzuo.zzb(1, this.name);
        }
        zzl zzl = this.zzqh;
        if (zzl != null) {
            zzy += zzuo.zzb(2, (zzuw) zzl);
        }
        zzh zzh = this.zzqi;
        return zzh != null ? zzy + zzuo.zzb(3, (zzuw) zzh) : zzy;
    }

    public final /* synthetic */ zzuw zza(zzun zzun) throws IOException {
        while (true) {
            int zzni = zzun.zzni();
            if (zzni == 0) {
                return this;
            }
            if (zzni == 10) {
                this.name = zzun.readString();
            } else if (zzni == 18) {
                if (this.zzqh == null) {
                    this.zzqh = new zzl();
                }
                zzun.zza((zzuw) this.zzqh);
            } else if (zzni == 26) {
                if (this.zzqi == null) {
                    this.zzqi = new zzh();
                }
                zzun.zza((zzuw) this.zzqi);
            } else if (!super.zza(zzun, zzni)) {
                return this;
            }
        }
    }
}

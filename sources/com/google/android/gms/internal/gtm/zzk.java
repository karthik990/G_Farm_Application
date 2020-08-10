package com.google.android.gms.internal.gtm;

import java.io.IOException;

public final class zzk extends zzuq<zzk> {
    public zzj[] zzqj;
    public zzi zzqk;
    public String zzql;

    public zzk() {
        this.zzqj = zzj.zzz();
        this.zzqk = null;
        this.zzql = "";
        this.zzbhb = null;
        this.zzbhl = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzk)) {
            return false;
        }
        zzk zzk = (zzk) obj;
        if (!zzuu.equals((Object[]) this.zzqj, (Object[]) zzk.zzqj)) {
            return false;
        }
        zzi zzi = this.zzqk;
        if (zzi == null) {
            if (zzk.zzqk != null) {
                return false;
            }
        } else if (!zzi.equals(zzk.zzqk)) {
            return false;
        }
        String str = this.zzql;
        if (str == null) {
            if (zzk.zzql != null) {
                return false;
            }
        } else if (!str.equals(zzk.zzql)) {
            return false;
        }
        if (this.zzbhb == null || this.zzbhb.isEmpty()) {
            return zzk.zzbhb == null || zzk.zzbhb.isEmpty();
        }
        return this.zzbhb.equals(zzk.zzbhb);
    }

    public final int hashCode() {
        int i;
        int hashCode = ((getClass().getName().hashCode() + 527) * 31) + zzuu.hashCode((Object[]) this.zzqj);
        zzi zzi = this.zzqk;
        int i2 = hashCode * 31;
        int i3 = 0;
        if (zzi == null) {
            i = 0;
        } else {
            i = zzi.hashCode();
        }
        int i4 = (i2 + i) * 31;
        String str = this.zzql;
        int hashCode2 = (i4 + (str == null ? 0 : str.hashCode())) * 31;
        if (this.zzbhb != null && !this.zzbhb.isEmpty()) {
            i3 = this.zzbhb.hashCode();
        }
        return hashCode2 + i3;
    }

    public final void zza(zzuo zzuo) throws IOException {
        zzj[] zzjArr = this.zzqj;
        if (zzjArr != null && zzjArr.length > 0) {
            int i = 0;
            while (true) {
                zzj[] zzjArr2 = this.zzqj;
                if (i >= zzjArr2.length) {
                    break;
                }
                zzj zzj = zzjArr2[i];
                if (zzj != null) {
                    zzuo.zza(1, (zzuw) zzj);
                }
                i++;
            }
        }
        zzi zzi = this.zzqk;
        if (zzi != null) {
            zzuo.zza(2, (zzuw) zzi);
        }
        String str = this.zzql;
        if (str != null && !str.equals("")) {
            zzuo.zza(3, this.zzql);
        }
        super.zza(zzuo);
    }

    /* access modifiers changed from: protected */
    public final int zzy() {
        int zzy = super.zzy();
        zzj[] zzjArr = this.zzqj;
        if (zzjArr != null && zzjArr.length > 0) {
            int i = 0;
            while (true) {
                zzj[] zzjArr2 = this.zzqj;
                if (i >= zzjArr2.length) {
                    break;
                }
                zzj zzj = zzjArr2[i];
                if (zzj != null) {
                    zzy += zzuo.zzb(1, (zzuw) zzj);
                }
                i++;
            }
        }
        zzi zzi = this.zzqk;
        if (zzi != null) {
            zzy += zzuo.zzb(2, (zzuw) zzi);
        }
        String str = this.zzql;
        return (str == null || str.equals("")) ? zzy : zzy + zzuo.zzb(3, this.zzql);
    }

    public final /* synthetic */ zzuw zza(zzun zzun) throws IOException {
        while (true) {
            int zzni = zzun.zzni();
            if (zzni == 0) {
                return this;
            }
            if (zzni == 10) {
                int zzb = zzuz.zzb(zzun, 10);
                zzj[] zzjArr = this.zzqj;
                int length = zzjArr == null ? 0 : zzjArr.length;
                zzj[] zzjArr2 = new zzj[(zzb + length)];
                if (length != 0) {
                    System.arraycopy(this.zzqj, 0, zzjArr2, 0, length);
                }
                while (length < zzjArr2.length - 1) {
                    zzjArr2[length] = new zzj();
                    zzun.zza((zzuw) zzjArr2[length]);
                    zzun.zzni();
                    length++;
                }
                zzjArr2[length] = new zzj();
                zzun.zza((zzuw) zzjArr2[length]);
                this.zzqj = zzjArr2;
            } else if (zzni == 18) {
                if (this.zzqk == null) {
                    this.zzqk = new zzi();
                }
                zzun.zza((zzuw) this.zzqk);
            } else if (zzni == 26) {
                this.zzql = zzun.readString();
            } else if (!super.zza(zzun, zzni)) {
                return this;
            }
        }
    }
}

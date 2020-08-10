package com.google.android.gms.internal.firebase_remote_config;

abstract class zzdw extends zzdh<String> {
    private int limit;
    private int offset = 0;
    private final zzdk zzhh;
    private final boolean zzhi;
    final CharSequence zzhm;

    protected zzdw(zzdt zzdt, CharSequence charSequence) {
        this.zzhh = zzdt.zzhh;
        this.zzhi = false;
        this.limit = zzdt.limit;
        this.zzhm = charSequence;
    }

    /* access modifiers changed from: 0000 */
    public abstract int zzk(int i);

    /* access modifiers changed from: 0000 */
    public abstract int zzl(int i);

    /* access modifiers changed from: protected */
    public final /* synthetic */ Object zzch() {
        int i;
        int i2 = this.offset;
        while (true) {
            int i3 = this.offset;
            if (i3 != -1) {
                int zzk = zzk(i3);
                if (zzk == -1) {
                    zzk = this.zzhm.length();
                    this.offset = -1;
                } else {
                    this.offset = zzl(zzk);
                }
                int i4 = this.offset;
                if (i4 == i2) {
                    this.offset = i4 + 1;
                    if (this.offset > this.zzhm.length()) {
                        this.offset = -1;
                    }
                } else {
                    while (i2 < zzk && this.zzhh.zzb(this.zzhm.charAt(i2))) {
                        i2++;
                    }
                    while (i > i2 && this.zzhh.zzb(this.zzhm.charAt(i - 1))) {
                        zzk = i - 1;
                    }
                    if (!this.zzhi || i2 != i) {
                        int i5 = this.limit;
                    } else {
                        i2 = this.offset;
                    }
                }
            } else {
                zzci();
                return null;
            }
        }
        int i52 = this.limit;
        if (i52 == 1) {
            i = this.zzhm.length();
            this.offset = -1;
            while (i > i2 && this.zzhh.zzb(this.zzhm.charAt(i - 1))) {
                i--;
            }
        } else {
            this.limit = i52 - 1;
        }
        return this.zzhm.subSequence(i2, i).toString();
    }
}

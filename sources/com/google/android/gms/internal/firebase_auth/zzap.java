package com.google.android.gms.internal.firebase_auth;

abstract class zzap extends zzaa<String> {
    private int limit;
    private int offset = 0;
    private final zzae zzgm;
    private final boolean zzgn;
    final CharSequence zzgr;

    protected zzap(zzam zzam, CharSequence charSequence) {
        this.zzgm = zzam.zzgm;
        this.zzgn = false;
        this.limit = zzam.limit;
        this.zzgr = charSequence;
    }

    /* access modifiers changed from: 0000 */
    public abstract int zze(int i);

    /* access modifiers changed from: 0000 */
    public abstract int zzf(int i);

    /* access modifiers changed from: protected */
    public final /* synthetic */ Object zzbw() {
        int i;
        int i2 = this.offset;
        while (true) {
            int i3 = this.offset;
            if (i3 != -1) {
                int zze = zze(i3);
                if (zze == -1) {
                    zze = this.zzgr.length();
                    this.offset = -1;
                } else {
                    this.offset = zzf(zze);
                }
                int i4 = this.offset;
                if (i4 == i2) {
                    this.offset = i4 + 1;
                    if (this.offset > this.zzgr.length()) {
                        this.offset = -1;
                    }
                } else {
                    while (i2 < zze && this.zzgm.zza(this.zzgr.charAt(i2))) {
                        i2++;
                    }
                    while (i > i2 && this.zzgm.zza(this.zzgr.charAt(i - 1))) {
                        zze = i - 1;
                    }
                    if (!this.zzgn || i2 != i) {
                        int i5 = this.limit;
                    } else {
                        i2 = this.offset;
                    }
                }
            } else {
                zzbx();
                return null;
            }
        }
        int i52 = this.limit;
        if (i52 == 1) {
            i = this.zzgr.length();
            this.offset = -1;
            while (i > i2 && this.zzgm.zza(this.zzgr.charAt(i - 1))) {
                i--;
            }
        } else {
            this.limit = i52 - 1;
        }
        return this.zzgr.subSequence(i2, i).toString();
    }
}

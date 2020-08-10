package com.google.android.gms.internal.firebase_auth;

final class zzaq extends zzap {
    private final /* synthetic */ zzan zzgs;

    zzaq(zzan zzan, zzam zzam, CharSequence charSequence) {
        this.zzgs = zzan;
        super(zzam, charSequence);
    }

    public final int zze(int i) {
        int length = this.zzgs.zzgp.length();
        int length2 = this.zzgr.length() - length;
        while (i <= length2) {
            int i2 = 0;
            while (i2 < length) {
                if (this.zzgr.charAt(i2 + i) == this.zzgs.zzgp.charAt(i2)) {
                    i2++;
                } else {
                    i++;
                }
            }
            return i;
        }
        return -1;
    }

    public final int zzf(int i) {
        return i + this.zzgs.zzgp.length();
    }
}

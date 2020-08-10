package com.google.android.gms.internal.ads;

import java.io.IOException;

public final class zzij extends zzbfc<zzij> {
    private String zzanq;
    private Integer zzanr;
    private int[] zzans;
    private zzis zzant;

    public zzij() {
        this.zzanq = null;
        this.zzanr = null;
        this.zzans = zzbfl.zzeby;
        this.zzant = null;
        this.zzebk = null;
        this.zzebt = -1;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzl */
    public final zzij zza(zzbez zzbez) throws IOException {
        while (true) {
            int zzabk = zzbez.zzabk();
            if (zzabk == 0) {
                return this;
            }
            if (zzabk == 10) {
                this.zzanq = zzbez.readString();
            } else if (zzabk == 16) {
                int position = zzbez.getPosition();
                try {
                    this.zzanr = Integer.valueOf(zzia.zzd(zzbez.zzacc()));
                } catch (IllegalArgumentException unused) {
                    zzbez.zzdc(position);
                    zza(zzbez, zzabk);
                }
            } else if (zzabk == 24) {
                int zzb = zzbfl.zzb(zzbez, 24);
                int[] iArr = this.zzans;
                int length = iArr == null ? 0 : iArr.length;
                int[] iArr2 = new int[(zzb + length)];
                if (length != 0) {
                    System.arraycopy(this.zzans, 0, iArr2, 0, length);
                }
                while (length < iArr2.length - 1) {
                    iArr2[length] = zzbez.zzacc();
                    zzbez.zzabk();
                    length++;
                }
                iArr2[length] = zzbez.zzacc();
                this.zzans = iArr2;
            } else if (zzabk == 26) {
                int zzbr = zzbez.zzbr(zzbez.zzacc());
                int position2 = zzbez.getPosition();
                int i = 0;
                while (zzbez.zzagn() > 0) {
                    zzbez.zzacc();
                    i++;
                }
                zzbez.zzdc(position2);
                int[] iArr3 = this.zzans;
                int length2 = iArr3 == null ? 0 : iArr3.length;
                int[] iArr4 = new int[(i + length2)];
                if (length2 != 0) {
                    System.arraycopy(this.zzans, 0, iArr4, 0, length2);
                }
                while (length2 < iArr4.length) {
                    iArr4[length2] = zzbez.zzacc();
                    length2++;
                }
                this.zzans = iArr4;
                zzbez.zzbs(zzbr);
            } else if (zzabk == 34) {
                if (this.zzant == null) {
                    this.zzant = new zzis();
                }
                zzbez.zza(this.zzant);
            } else if (!super.zza(zzbez, zzabk)) {
                return this;
            }
        }
    }

    public final void zza(zzbfa zzbfa) throws IOException {
        String str = this.zzanq;
        if (str != null) {
            zzbfa.zzf(1, str);
        }
        Integer num = this.zzanr;
        if (num != null) {
            zzbfa.zzm(2, num.intValue());
        }
        int[] iArr = this.zzans;
        if (iArr != null && iArr.length > 0) {
            int i = 0;
            while (true) {
                int[] iArr2 = this.zzans;
                if (i >= iArr2.length) {
                    break;
                }
                zzbfa.zzm(3, iArr2[i]);
                i++;
            }
        }
        zzis zzis = this.zzant;
        if (zzis != null) {
            zzbfa.zza(4, (zzbfi) zzis);
        }
        super.zza(zzbfa);
    }

    /* access modifiers changed from: protected */
    public final int zzr() {
        int[] iArr;
        int zzr = super.zzr();
        String str = this.zzanq;
        if (str != null) {
            zzr += zzbfa.zzg(1, str);
        }
        Integer num = this.zzanr;
        if (num != null) {
            zzr += zzbfa.zzq(2, num.intValue());
        }
        int[] iArr2 = this.zzans;
        if (iArr2 != null && iArr2.length > 0) {
            int i = 0;
            int i2 = 0;
            while (true) {
                iArr = this.zzans;
                if (i >= iArr.length) {
                    break;
                }
                i2 += zzbfa.zzce(iArr[i]);
                i++;
            }
            zzr = zzr + i2 + (iArr.length * 1);
        }
        zzis zzis = this.zzant;
        return zzis != null ? zzr + zzbfa.zzb(4, (zzbfi) zzis) : zzr;
    }
}

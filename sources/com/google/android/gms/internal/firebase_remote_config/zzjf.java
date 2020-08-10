package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

final class zzjf extends zzfw {
    /* access modifiers changed from: private */
    public static final int[] zzwh;
    private final int zzwi;
    /* access modifiers changed from: private */
    public final zzfw zzwj;
    /* access modifiers changed from: private */
    public final zzfw zzwk;
    private final int zzwl;
    private final int zzwm;

    private zzjf(zzfw zzfw, zzfw zzfw2) {
        this.zzwj = zzfw;
        this.zzwk = zzfw2;
        this.zzwl = zzfw.size();
        this.zzwi = this.zzwl + zzfw2.size();
        this.zzwm = Math.max(zzfw.zzex(), zzfw2.zzex()) + 1;
    }

    static zzfw zza(zzfw zzfw, zzfw zzfw2) {
        if (zzfw2.size() == 0) {
            return zzfw;
        }
        if (zzfw.size() == 0) {
            return zzfw2;
        }
        int size = zzfw.size() + zzfw2.size();
        if (size < 128) {
            return zzb(zzfw, zzfw2);
        }
        if (zzfw instanceof zzjf) {
            zzjf zzjf = (zzjf) zzfw;
            if (zzjf.zzwk.size() + zzfw2.size() < 128) {
                return new zzjf(zzjf.zzwj, zzb(zzjf.zzwk, zzfw2));
            } else if (zzjf.zzwj.zzex() > zzjf.zzwk.zzex() && zzjf.zzex() > zzfw2.zzex()) {
                return new zzjf(zzjf.zzwj, new zzjf(zzjf.zzwk, zzfw2));
            }
        }
        if (size >= zzwh[Math.max(zzfw.zzex(), zzfw2.zzex()) + 1]) {
            return new zzjf(zzfw, zzfw2);
        }
        return new zzjh(null).zzc(zzfw, zzfw2);
    }

    private static zzfw zzb(zzfw zzfw, zzfw zzfw2) {
        int size = zzfw.size();
        int size2 = zzfw2.size();
        byte[] bArr = new byte[(size + size2)];
        zzfw.zza(bArr, 0, 0, size);
        zzfw2.zza(bArr, 0, size, size2);
        return zzfw.zza(bArr);
    }

    public final byte zzv(int i) {
        zzc(i, this.zzwi);
        return zzw(i);
    }

    /* access modifiers changed from: 0000 */
    public final byte zzw(int i) {
        int i2 = this.zzwl;
        if (i < i2) {
            return this.zzwj.zzw(i);
        }
        return this.zzwk.zzw(i - i2);
    }

    public final int size() {
        return this.zzwi;
    }

    public final zzgd zzev() {
        return new zzjg(this);
    }

    /* access modifiers changed from: protected */
    public final int zzex() {
        return this.zzwm;
    }

    /* access modifiers changed from: protected */
    public final boolean zzey() {
        return this.zzwi >= zzwh[this.zzwm];
    }

    public final zzfw zzb(int i, int i2) {
        int zzc = zzc(i, i2, this.zzwi);
        if (zzc == 0) {
            return zzfw.zzop;
        }
        if (zzc == this.zzwi) {
            return this;
        }
        int i3 = this.zzwl;
        if (i2 <= i3) {
            return this.zzwj.zzb(i, i2);
        }
        if (i >= i3) {
            return this.zzwk.zzb(i - i3, i2 - i3);
        }
        zzfw zzfw = this.zzwj;
        return new zzjf(zzfw.zzb(i, zzfw.size()), this.zzwk.zzb(0, i2 - this.zzwl));
    }

    /* access modifiers changed from: protected */
    public final void zzb(byte[] bArr, int i, int i2, int i3) {
        int i4 = i + i3;
        int i5 = this.zzwl;
        if (i4 <= i5) {
            this.zzwj.zzb(bArr, i, i2, i3);
        } else if (i >= i5) {
            this.zzwk.zzb(bArr, i - i5, i2, i3);
        } else {
            int i6 = i5 - i;
            this.zzwj.zzb(bArr, i, i2, i6);
            this.zzwk.zzb(bArr, 0, i2 + i6, i3 - i6);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void zza(zzfv zzfv) throws IOException {
        this.zzwj.zza(zzfv);
        this.zzwk.zza(zzfv);
    }

    /* access modifiers changed from: protected */
    public final String zzc(Charset charset) {
        byte[] bArr;
        int size = size();
        if (size == 0) {
            bArr = zzhk.zztt;
        } else {
            byte[] bArr2 = new byte[size];
            zzb(bArr2, 0, 0, size);
            bArr = bArr2;
        }
        return new String(bArr, charset);
    }

    public final boolean zzew() {
        int zza = this.zzwj.zza(0, 0, this.zzwl);
        zzfw zzfw = this.zzwk;
        if (zzfw.zza(zza, 0, zzfw.size()) == 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public final int zza(int i, int i2, int i3) {
        int i4 = i2 + i3;
        int i5 = this.zzwl;
        if (i4 <= i5) {
            return this.zzwj.zza(i, i2, i3);
        }
        if (i2 >= i5) {
            return this.zzwk.zza(i, i2 - i5, i3);
        }
        int i6 = i5 - i2;
        return this.zzwk.zza(this.zzwj.zza(i, i2, i6), 0, i3 - i6);
    }

    public final boolean equals(Object obj) {
        boolean z;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfw)) {
            return false;
        }
        zzfw zzfw = (zzfw) obj;
        if (this.zzwi != zzfw.size()) {
            return false;
        }
        if (this.zzwi == 0) {
            return true;
        }
        int zzez = zzez();
        int zzez2 = zzfw.zzez();
        if (zzez != 0 && zzez2 != 0 && zzez != zzez2) {
            return false;
        }
        zzji zzji = new zzji(this, null);
        zzgf zzgf = (zzgf) zzji.next();
        zzji zzji2 = new zzji(zzfw, null);
        zzgf zzgf2 = (zzgf) zzji2.next();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int size = zzgf.size() - i;
            int size2 = zzgf2.size() - i2;
            int min = Math.min(size, size2);
            if (i == 0) {
                z = zzgf.zza(zzgf2, i2, min);
            } else {
                z = zzgf2.zza(zzgf, i, min);
            }
            if (!z) {
                return false;
            }
            i3 += min;
            int i4 = this.zzwi;
            if (i3 < i4) {
                if (min == size) {
                    zzgf = (zzgf) zzji.next();
                    i = 0;
                } else {
                    i += min;
                }
                if (min == size2) {
                    zzgf2 = (zzgf) zzji2.next();
                    i2 = 0;
                } else {
                    i2 += min;
                }
            } else if (i3 == i4) {
                return true;
            } else {
                throw new IllegalStateException();
            }
        }
    }

    /* access modifiers changed from: protected */
    public final int zzb(int i, int i2, int i3) {
        int i4 = i2 + i3;
        int i5 = this.zzwl;
        if (i4 <= i5) {
            return this.zzwj.zzb(i, i2, i3);
        }
        if (i2 >= i5) {
            return this.zzwk.zzb(i, i2 - i5, i3);
        }
        int i6 = i5 - i2;
        return this.zzwk.zzb(this.zzwj.zzb(i, i2, i6), 0, i3 - i6);
    }

    public final /* synthetic */ Iterator iterator() {
        return iterator();
    }

    /* synthetic */ zzjf(zzfw zzfw, zzfw zzfw2, zzjg zzjg) {
        this(zzfw, zzfw2);
    }

    static {
        ArrayList arrayList = new ArrayList();
        int i = 1;
        int i2 = 1;
        while (i > 0) {
            arrayList.add(Integer.valueOf(i));
            int i3 = i2 + i;
            i2 = i;
            i = i3;
        }
        arrayList.add(Integer.valueOf(Integer.MAX_VALUE));
        zzwh = new int[arrayList.size()];
        int i4 = 0;
        while (true) {
            int[] iArr = zzwh;
            if (i4 < iArr.length) {
                iArr[i4] = ((Integer) arrayList.get(i4)).intValue();
                i4++;
            } else {
                return;
            }
        }
    }
}

package com.google.android.gms.internal.firebase_remote_config;

import com.google.android.gms.internal.firebase_remote_config.zzhi.zze;
import java.io.IOException;
import java.util.Arrays;

public final class zzkc {
    private static final zzkc zzxl = new zzkc(0, new int[0], new Object[0], false);
    private int count;
    private boolean zzog;
    private int zzsx;
    private Object[] zzvl;
    private int[] zzxm;

    public static zzkc zzje() {
        return zzxl;
    }

    static zzkc zzjf() {
        return new zzkc();
    }

    static zzkc zza(zzkc zzkc, zzkc zzkc2) {
        int i = zzkc.count + zzkc2.count;
        int[] copyOf = Arrays.copyOf(zzkc.zzxm, i);
        System.arraycopy(zzkc2.zzxm, 0, copyOf, zzkc.count, zzkc2.count);
        Object[] copyOf2 = Arrays.copyOf(zzkc.zzvl, i);
        System.arraycopy(zzkc2.zzvl, 0, copyOf2, zzkc.count, zzkc2.count);
        return new zzkc(i, copyOf, copyOf2, true);
    }

    private zzkc() {
        this(0, new int[8], new Object[8], true);
    }

    private zzkc(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zzsx = -1;
        this.count = i;
        this.zzxm = iArr;
        this.zzvl = objArr;
        this.zzog = z;
    }

    public final void zzer() {
        this.zzog = false;
    }

    /* access modifiers changed from: 0000 */
    public final void zza(zzkw zzkw) throws IOException {
        if (zzkw.zzgf() == zze.zztp) {
            for (int i = this.count - 1; i >= 0; i--) {
                zzkw.zza(this.zzxm[i] >>> 3, this.zzvl[i]);
            }
            return;
        }
        for (int i2 = 0; i2 < this.count; i2++) {
            zzkw.zza(this.zzxm[i2] >>> 3, this.zzvl[i2]);
        }
    }

    public final void zzb(zzkw zzkw) throws IOException {
        if (this.count != 0) {
            if (zzkw.zzgf() == zze.zzto) {
                for (int i = 0; i < this.count; i++) {
                    zzb(this.zzxm[i], this.zzvl[i], zzkw);
                }
                return;
            }
            for (int i2 = this.count - 1; i2 >= 0; i2--) {
                zzb(this.zzxm[i2], this.zzvl[i2], zzkw);
            }
        }
    }

    private static void zzb(int i, Object obj, zzkw zzkw) throws IOException {
        int i2 = i >>> 3;
        int i3 = i & 7;
        if (i3 == 0) {
            zzkw.zzi(i2, ((Long) obj).longValue());
        } else if (i3 == 1) {
            zzkw.zzc(i2, ((Long) obj).longValue());
        } else if (i3 == 2) {
            zzkw.zza(i2, (zzfw) obj);
        } else if (i3 != 3) {
            if (i3 == 5) {
                zzkw.zzh(i2, ((Integer) obj).intValue());
                return;
            }
            throw new RuntimeException(zzho.zzhl());
        } else if (zzkw.zzgf() == zze.zzto) {
            zzkw.zzba(i2);
            ((zzkc) obj).zzb(zzkw);
            zzkw.zzbb(i2);
        } else {
            zzkw.zzbb(i2);
            ((zzkc) obj).zzb(zzkw);
            zzkw.zzba(i2);
        }
    }

    public final int zzjg() {
        int i = this.zzsx;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.count; i3++) {
            i2 += zzgp.zzd(this.zzxm[i3] >>> 3, (zzfw) this.zzvl[i3]);
        }
        this.zzsx = i2;
        return i2;
    }

    public final int zzgo() {
        int i;
        int i2 = this.zzsx;
        if (i2 != -1) {
            return i2;
        }
        int i3 = 0;
        for (int i4 = 0; i4 < this.count; i4++) {
            int i5 = this.zzxm[i4];
            int i6 = i5 >>> 3;
            int i7 = i5 & 7;
            if (i7 == 0) {
                i = zzgp.zze(i6, ((Long) this.zzvl[i4]).longValue());
            } else if (i7 == 1) {
                i = zzgp.zzg(i6, ((Long) this.zzvl[i4]).longValue());
            } else if (i7 == 2) {
                i = zzgp.zzc(i6, (zzfw) this.zzvl[i4]);
            } else if (i7 == 3) {
                i = (zzgp.zzar(i6) << 1) + ((zzkc) this.zzvl[i4]).zzgo();
            } else if (i7 == 5) {
                i = zzgp.zzl(i6, ((Integer) this.zzvl[i4]).intValue());
            } else {
                throw new IllegalStateException(zzho.zzhl());
            }
            i3 += i;
        }
        this.zzsx = i3;
        return i3;
    }

    public final boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof zzkc)) {
            return false;
        }
        zzkc zzkc = (zzkc) obj;
        int i = this.count;
        if (i == zzkc.count) {
            int[] iArr = this.zzxm;
            int[] iArr2 = zzkc.zzxm;
            int i2 = 0;
            while (true) {
                if (i2 >= i) {
                    z = true;
                    break;
                } else if (iArr[i2] != iArr2[i2]) {
                    z = false;
                    break;
                } else {
                    i2++;
                }
            }
            if (z) {
                Object[] objArr = this.zzvl;
                Object[] objArr2 = zzkc.zzvl;
                int i3 = this.count;
                int i4 = 0;
                while (true) {
                    if (i4 >= i3) {
                        z2 = true;
                        break;
                    } else if (!objArr[i4].equals(objArr2[i4])) {
                        z2 = false;
                        break;
                    } else {
                        i4++;
                    }
                }
                return z2;
            }
        }
    }

    public final int hashCode() {
        int i = this.count;
        int i2 = (i + 527) * 31;
        int[] iArr = this.zzxm;
        int i3 = 17;
        int i4 = 17;
        for (int i5 = 0; i5 < i; i5++) {
            i4 = (i4 * 31) + iArr[i5];
        }
        int i6 = (i2 + i4) * 31;
        Object[] objArr = this.zzvl;
        for (int i7 = 0; i7 < this.count; i7++) {
            i3 = (i3 * 31) + objArr[i7].hashCode();
        }
        return i6 + i3;
    }

    /* access modifiers changed from: 0000 */
    public final void zzb(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < this.count; i2++) {
            zzir.zza(sb, i, String.valueOf(this.zzxm[i2] >>> 3), this.zzvl[i2]);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void zzb(int i, Object obj) {
        if (this.zzog) {
            int i2 = this.count;
            if (i2 == this.zzxm.length) {
                int i3 = this.count + (i2 < 4 ? 8 : i2 >> 1);
                this.zzxm = Arrays.copyOf(this.zzxm, i3);
                this.zzvl = Arrays.copyOf(this.zzvl, i3);
            }
            int[] iArr = this.zzxm;
            int i4 = this.count;
            iArr[i4] = i;
            this.zzvl[i4] = obj;
            this.count = i4 + 1;
            return;
        }
        throw new UnsupportedOperationException();
    }
}

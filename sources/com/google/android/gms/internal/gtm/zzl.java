package com.google.android.gms.internal.gtm;

import java.io.IOException;

public final class zzl extends zzuq<zzl> {
    private static volatile zzl[] zzqm;
    public String string;
    public int type = 1;
    public zzl[] zzqn;
    public zzl[] zzqo;
    public zzl[] zzqp;
    public String zzqq;
    public String zzqr;
    public long zzqs;
    public boolean zzqt;
    public zzl[] zzqu;
    public int[] zzqv;
    public boolean zzqw;

    private static int zzc(int i) {
        if (i > 0 && i <= 17) {
            return i;
        }
        StringBuilder sb = new StringBuilder(40);
        sb.append(i);
        sb.append(" is not a valid enum Escaping");
        throw new IllegalArgumentException(sb.toString());
    }

    public static zzl[] zzaa() {
        if (zzqm == null) {
            synchronized (zzuu.zzbhk) {
                if (zzqm == null) {
                    zzqm = new zzl[0];
                }
            }
        }
        return zzqm;
    }

    public zzl() {
        String str = "";
        this.string = str;
        this.zzqn = zzaa();
        this.zzqo = zzaa();
        this.zzqp = zzaa();
        this.zzqq = str;
        this.zzqr = str;
        this.zzqs = 0;
        this.zzqt = false;
        this.zzqu = zzaa();
        this.zzqv = zzuz.zzbcw;
        this.zzqw = false;
        this.zzbhb = null;
        this.zzbhl = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzl)) {
            return false;
        }
        zzl zzl = (zzl) obj;
        if (this.type != zzl.type) {
            return false;
        }
        String str = this.string;
        if (str == null) {
            if (zzl.string != null) {
                return false;
            }
        } else if (!str.equals(zzl.string)) {
            return false;
        }
        if (!zzuu.equals((Object[]) this.zzqn, (Object[]) zzl.zzqn) || !zzuu.equals((Object[]) this.zzqo, (Object[]) zzl.zzqo) || !zzuu.equals((Object[]) this.zzqp, (Object[]) zzl.zzqp)) {
            return false;
        }
        String str2 = this.zzqq;
        if (str2 == null) {
            if (zzl.zzqq != null) {
                return false;
            }
        } else if (!str2.equals(zzl.zzqq)) {
            return false;
        }
        String str3 = this.zzqr;
        if (str3 == null) {
            if (zzl.zzqr != null) {
                return false;
            }
        } else if (!str3.equals(zzl.zzqr)) {
            return false;
        }
        if (this.zzqs != zzl.zzqs || this.zzqt != zzl.zzqt || !zzuu.equals((Object[]) this.zzqu, (Object[]) zzl.zzqu) || !zzuu.equals(this.zzqv, zzl.zzqv) || this.zzqw != zzl.zzqw) {
            return false;
        }
        if (this.zzbhb == null || this.zzbhb.isEmpty()) {
            return zzl.zzbhb == null || zzl.zzbhb.isEmpty();
        }
        return this.zzbhb.equals(zzl.zzbhb);
    }

    public final int hashCode() {
        int hashCode = (((getClass().getName().hashCode() + 527) * 31) + this.type) * 31;
        String str = this.string;
        int i = 0;
        int hashCode2 = (((((((hashCode + (str == null ? 0 : str.hashCode())) * 31) + zzuu.hashCode((Object[]) this.zzqn)) * 31) + zzuu.hashCode((Object[]) this.zzqo)) * 31) + zzuu.hashCode((Object[]) this.zzqp)) * 31;
        String str2 = this.zzqq;
        int hashCode3 = (hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.zzqr;
        int hashCode4 = (hashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        long j = this.zzqs;
        int i2 = (hashCode4 + ((int) (j ^ (j >>> 32)))) * 31;
        int i3 = 1231;
        int hashCode5 = (((((i2 + (this.zzqt ? 1231 : 1237)) * 31) + zzuu.hashCode((Object[]) this.zzqu)) * 31) + zzuu.hashCode(this.zzqv)) * 31;
        if (!this.zzqw) {
            i3 = 1237;
        }
        int i4 = (hashCode5 + i3) * 31;
        if (this.zzbhb != null && !this.zzbhb.isEmpty()) {
            i = this.zzbhb.hashCode();
        }
        return i4 + i;
    }

    public final void zza(zzuo zzuo) throws IOException {
        zzuo.zze(1, this.type);
        String str = this.string;
        String str2 = "";
        if (str != null && !str.equals(str2)) {
            zzuo.zza(2, this.string);
        }
        zzl[] zzlArr = this.zzqn;
        int i = 0;
        if (zzlArr != null && zzlArr.length > 0) {
            int i2 = 0;
            while (true) {
                zzl[] zzlArr2 = this.zzqn;
                if (i2 >= zzlArr2.length) {
                    break;
                }
                zzl zzl = zzlArr2[i2];
                if (zzl != null) {
                    zzuo.zza(3, (zzuw) zzl);
                }
                i2++;
            }
        }
        zzl[] zzlArr3 = this.zzqo;
        if (zzlArr3 != null && zzlArr3.length > 0) {
            int i3 = 0;
            while (true) {
                zzl[] zzlArr4 = this.zzqo;
                if (i3 >= zzlArr4.length) {
                    break;
                }
                zzl zzl2 = zzlArr4[i3];
                if (zzl2 != null) {
                    zzuo.zza(4, (zzuw) zzl2);
                }
                i3++;
            }
        }
        zzl[] zzlArr5 = this.zzqp;
        if (zzlArr5 != null && zzlArr5.length > 0) {
            int i4 = 0;
            while (true) {
                zzl[] zzlArr6 = this.zzqp;
                if (i4 >= zzlArr6.length) {
                    break;
                }
                zzl zzl3 = zzlArr6[i4];
                if (zzl3 != null) {
                    zzuo.zza(5, (zzuw) zzl3);
                }
                i4++;
            }
        }
        String str3 = this.zzqq;
        if (str3 != null && !str3.equals(str2)) {
            zzuo.zza(6, this.zzqq);
        }
        String str4 = this.zzqr;
        if (str4 != null && !str4.equals(str2)) {
            zzuo.zza(7, this.zzqr);
        }
        long j = this.zzqs;
        if (j != 0) {
            zzuo.zzi(8, j);
        }
        boolean z = this.zzqw;
        if (z) {
            zzuo.zzb(9, z);
        }
        int[] iArr = this.zzqv;
        if (iArr != null && iArr.length > 0) {
            int i5 = 0;
            while (true) {
                int[] iArr2 = this.zzqv;
                if (i5 >= iArr2.length) {
                    break;
                }
                zzuo.zze(10, iArr2[i5]);
                i5++;
            }
        }
        zzl[] zzlArr7 = this.zzqu;
        if (zzlArr7 != null && zzlArr7.length > 0) {
            while (true) {
                zzl[] zzlArr8 = this.zzqu;
                if (i >= zzlArr8.length) {
                    break;
                }
                zzl zzl4 = zzlArr8[i];
                if (zzl4 != null) {
                    zzuo.zza(11, (zzuw) zzl4);
                }
                i++;
            }
        }
        boolean z2 = this.zzqt;
        if (z2) {
            zzuo.zzb(12, z2);
        }
        super.zza(zzuo);
    }

    /* access modifiers changed from: protected */
    public final int zzy() {
        int[] iArr;
        int zzy = super.zzy() + zzuo.zzi(1, this.type);
        String str = this.string;
        String str2 = "";
        if (str != null && !str.equals(str2)) {
            zzy += zzuo.zzb(2, this.string);
        }
        zzl[] zzlArr = this.zzqn;
        int i = 0;
        if (zzlArr != null && zzlArr.length > 0) {
            int i2 = zzy;
            int i3 = 0;
            while (true) {
                zzl[] zzlArr2 = this.zzqn;
                if (i3 >= zzlArr2.length) {
                    break;
                }
                zzl zzl = zzlArr2[i3];
                if (zzl != null) {
                    i2 += zzuo.zzb(3, (zzuw) zzl);
                }
                i3++;
            }
            zzy = i2;
        }
        zzl[] zzlArr3 = this.zzqo;
        if (zzlArr3 != null && zzlArr3.length > 0) {
            int i4 = zzy;
            int i5 = 0;
            while (true) {
                zzl[] zzlArr4 = this.zzqo;
                if (i5 >= zzlArr4.length) {
                    break;
                }
                zzl zzl2 = zzlArr4[i5];
                if (zzl2 != null) {
                    i4 += zzuo.zzb(4, (zzuw) zzl2);
                }
                i5++;
            }
            zzy = i4;
        }
        zzl[] zzlArr5 = this.zzqp;
        if (zzlArr5 != null && zzlArr5.length > 0) {
            int i6 = zzy;
            int i7 = 0;
            while (true) {
                zzl[] zzlArr6 = this.zzqp;
                if (i7 >= zzlArr6.length) {
                    break;
                }
                zzl zzl3 = zzlArr6[i7];
                if (zzl3 != null) {
                    i6 += zzuo.zzb(5, (zzuw) zzl3);
                }
                i7++;
            }
            zzy = i6;
        }
        String str3 = this.zzqq;
        if (str3 != null && !str3.equals(str2)) {
            zzy += zzuo.zzb(6, this.zzqq);
        }
        String str4 = this.zzqr;
        if (str4 != null && !str4.equals(str2)) {
            zzy += zzuo.zzb(7, this.zzqr);
        }
        long j = this.zzqs;
        if (j != 0) {
            zzy += zzuo.zzd(8, j);
        }
        if (this.zzqw) {
            zzy += zzuo.zzbb(9) + 1;
        }
        int[] iArr2 = this.zzqv;
        if (iArr2 != null && iArr2.length > 0) {
            int i8 = 0;
            int i9 = 0;
            while (true) {
                iArr = this.zzqv;
                if (i8 >= iArr.length) {
                    break;
                }
                i9 += zzuo.zzbc(iArr[i8]);
                i8++;
            }
            zzy = zzy + i9 + (iArr.length * 1);
        }
        zzl[] zzlArr7 = this.zzqu;
        if (zzlArr7 != null && zzlArr7.length > 0) {
            while (true) {
                zzl[] zzlArr8 = this.zzqu;
                if (i >= zzlArr8.length) {
                    break;
                }
                zzl zzl4 = zzlArr8[i];
                if (zzl4 != null) {
                    zzy += zzuo.zzb(11, (zzuw) zzl4);
                }
                i++;
            }
        }
        return this.zzqt ? zzy + zzuo.zzbb(12) + 1 : zzy;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzb */
    public final zzl zza(zzun zzun) throws IOException {
        int zzoa;
        while (true) {
            int zzni = zzun.zzni();
            switch (zzni) {
                case 0:
                    return this;
                case 8:
                    try {
                        zzoa = zzun.zzoa();
                        if (zzoa > 0 && zzoa <= 8) {
                            this.type = zzoa;
                            break;
                        } else {
                            StringBuilder sb = new StringBuilder(36);
                            sb.append(zzoa);
                            sb.append(" is not a valid enum Type");
                            break;
                        }
                    } catch (IllegalArgumentException unused) {
                        zzun.zzbz(zzun.getPosition());
                        zza(zzun, zzni);
                        break;
                    }
                case 18:
                    this.string = zzun.readString();
                    break;
                case 26:
                    int zzb = zzuz.zzb(zzun, 26);
                    zzl[] zzlArr = this.zzqn;
                    int length = zzlArr == null ? 0 : zzlArr.length;
                    zzl[] zzlArr2 = new zzl[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzqn, 0, zzlArr2, 0, length);
                    }
                    while (length < zzlArr2.length - 1) {
                        zzlArr2[length] = new zzl();
                        zzun.zza((zzuw) zzlArr2[length]);
                        zzun.zzni();
                        length++;
                    }
                    zzlArr2[length] = new zzl();
                    zzun.zza((zzuw) zzlArr2[length]);
                    this.zzqn = zzlArr2;
                    break;
                case 34:
                    int zzb2 = zzuz.zzb(zzun, 34);
                    zzl[] zzlArr3 = this.zzqo;
                    int length2 = zzlArr3 == null ? 0 : zzlArr3.length;
                    zzl[] zzlArr4 = new zzl[(zzb2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.zzqo, 0, zzlArr4, 0, length2);
                    }
                    while (length2 < zzlArr4.length - 1) {
                        zzlArr4[length2] = new zzl();
                        zzun.zza((zzuw) zzlArr4[length2]);
                        zzun.zzni();
                        length2++;
                    }
                    zzlArr4[length2] = new zzl();
                    zzun.zza((zzuw) zzlArr4[length2]);
                    this.zzqo = zzlArr4;
                    break;
                case 42:
                    int zzb3 = zzuz.zzb(zzun, 42);
                    zzl[] zzlArr5 = this.zzqp;
                    int length3 = zzlArr5 == null ? 0 : zzlArr5.length;
                    zzl[] zzlArr6 = new zzl[(zzb3 + length3)];
                    if (length3 != 0) {
                        System.arraycopy(this.zzqp, 0, zzlArr6, 0, length3);
                    }
                    while (length3 < zzlArr6.length - 1) {
                        zzlArr6[length3] = new zzl();
                        zzun.zza((zzuw) zzlArr6[length3]);
                        zzun.zzni();
                        length3++;
                    }
                    zzlArr6[length3] = new zzl();
                    zzun.zza((zzuw) zzlArr6[length3]);
                    this.zzqp = zzlArr6;
                    break;
                case 50:
                    this.zzqq = zzun.readString();
                    break;
                case 58:
                    this.zzqr = zzun.readString();
                    break;
                case 64:
                    this.zzqs = zzun.zzob();
                    break;
                case 72:
                    this.zzqw = zzun.zzno();
                    break;
                case 80:
                    int zzb4 = zzuz.zzb(zzun, 80);
                    int[] iArr = new int[zzb4];
                    int i = 0;
                    for (int i2 = 0; i2 < zzb4; i2++) {
                        if (i2 != 0) {
                            zzun.zzni();
                        }
                        int position = zzun.getPosition();
                        try {
                            iArr[i] = zzc(zzun.zzoa());
                            i++;
                        } catch (IllegalArgumentException unused2) {
                            zzun.zzbz(position);
                            zza(zzun, zzni);
                        }
                    }
                    if (i == 0) {
                        break;
                    } else {
                        int[] iArr2 = this.zzqv;
                        int length4 = iArr2 == null ? 0 : iArr2.length;
                        if (length4 != 0 || i != iArr.length) {
                            int[] iArr3 = new int[(length4 + i)];
                            if (length4 != 0) {
                                System.arraycopy(this.zzqv, 0, iArr3, 0, length4);
                            }
                            System.arraycopy(iArr, 0, iArr3, length4, i);
                            this.zzqv = iArr3;
                            break;
                        } else {
                            this.zzqv = iArr;
                            break;
                        }
                    }
                case 82:
                    int zzaq = zzun.zzaq(zzun.zzoa());
                    int position2 = zzun.getPosition();
                    int i3 = 0;
                    while (zzun.zzrv() > 0) {
                        try {
                            zzc(zzun.zzoa());
                            i3++;
                        } catch (IllegalArgumentException unused3) {
                        }
                    }
                    if (i3 != 0) {
                        zzun.zzbz(position2);
                        int[] iArr4 = this.zzqv;
                        int length5 = iArr4 == null ? 0 : iArr4.length;
                        int[] iArr5 = new int[(i3 + length5)];
                        if (length5 != 0) {
                            System.arraycopy(this.zzqv, 0, iArr5, 0, length5);
                        }
                        while (zzun.zzrv() > 0) {
                            int position3 = zzun.getPosition();
                            try {
                                iArr5[length5] = zzc(zzun.zzoa());
                                length5++;
                            } catch (IllegalArgumentException unused4) {
                                zzun.zzbz(position3);
                                zza(zzun, 80);
                            }
                        }
                        this.zzqv = iArr5;
                    }
                    zzun.zzar(zzaq);
                    break;
                case 90:
                    int zzb5 = zzuz.zzb(zzun, 90);
                    zzl[] zzlArr7 = this.zzqu;
                    int length6 = zzlArr7 == null ? 0 : zzlArr7.length;
                    zzl[] zzlArr8 = new zzl[(zzb5 + length6)];
                    if (length6 != 0) {
                        System.arraycopy(this.zzqu, 0, zzlArr8, 0, length6);
                    }
                    while (length6 < zzlArr8.length - 1) {
                        zzlArr8[length6] = new zzl();
                        zzun.zza((zzuw) zzlArr8[length6]);
                        zzun.zzni();
                        length6++;
                    }
                    zzlArr8[length6] = new zzl();
                    zzun.zza((zzuw) zzlArr8[length6]);
                    this.zzqu = zzlArr8;
                    break;
                case 96:
                    this.zzqt = zzun.zzno();
                    break;
                default:
                    if (super.zza(zzun, zzni)) {
                        break;
                    } else {
                        return this;
                    }
            }
        }
        StringBuilder sb2 = new StringBuilder(36);
        sb2.append(zzoa);
        sb2.append(" is not a valid enum Type");
        throw new IllegalArgumentException(sb2.toString());
    }
}

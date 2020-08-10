package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzc.zza;
import com.google.android.gms.internal.gtm.zzc.zza.C6218zza;
import com.google.android.gms.internal.gtm.zzc.zzb;
import com.google.android.gms.internal.gtm.zzc.zzd;
import com.google.android.gms.internal.gtm.zzc.zze;
import java.io.IOException;
import org.objectweb.asm.Opcodes;

public final class zzi extends zzuq<zzi> {
    public String version;
    private String[] zzph = zzuz.zzbhu;
    public String[] zzpi = zzuz.zzbhu;
    public zzl[] zzpj = zzl.zzaa();
    public zzd[] zzpk = new zzd[0];
    public zzb[] zzpl = new zzb[0];
    public zzb[] zzpm = new zzb[0];
    public zzb[] zzpn = new zzb[0];
    public zze[] zzpo = new zze[0];
    private String zzpp;
    private String zzpq;
    private String zzpr;
    private zza zzps;
    private float zzpt;
    private boolean zzpu;
    private String[] zzpv;
    public int zzpw;

    public zzi() {
        String str = "";
        this.zzpp = str;
        this.zzpq = str;
        this.zzpr = "0";
        this.version = str;
        this.zzps = null;
        this.zzpt = 0.0f;
        this.zzpu = false;
        this.zzpv = zzuz.zzbhu;
        this.zzpw = 0;
        this.zzbhb = null;
        this.zzbhl = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzi)) {
            return false;
        }
        zzi zzi = (zzi) obj;
        if (!zzuu.equals((Object[]) this.zzph, (Object[]) zzi.zzph) || !zzuu.equals((Object[]) this.zzpi, (Object[]) zzi.zzpi) || !zzuu.equals((Object[]) this.zzpj, (Object[]) zzi.zzpj) || !zzuu.equals((Object[]) this.zzpk, (Object[]) zzi.zzpk) || !zzuu.equals((Object[]) this.zzpl, (Object[]) zzi.zzpl) || !zzuu.equals((Object[]) this.zzpm, (Object[]) zzi.zzpm) || !zzuu.equals((Object[]) this.zzpn, (Object[]) zzi.zzpn) || !zzuu.equals((Object[]) this.zzpo, (Object[]) zzi.zzpo)) {
            return false;
        }
        String str = this.zzpp;
        if (str == null) {
            if (zzi.zzpp != null) {
                return false;
            }
        } else if (!str.equals(zzi.zzpp)) {
            return false;
        }
        String str2 = this.zzpq;
        if (str2 == null) {
            if (zzi.zzpq != null) {
                return false;
            }
        } else if (!str2.equals(zzi.zzpq)) {
            return false;
        }
        String str3 = this.zzpr;
        if (str3 == null) {
            if (zzi.zzpr != null) {
                return false;
            }
        } else if (!str3.equals(zzi.zzpr)) {
            return false;
        }
        String str4 = this.version;
        if (str4 == null) {
            if (zzi.version != null) {
                return false;
            }
        } else if (!str4.equals(zzi.version)) {
            return false;
        }
        zza zza = this.zzps;
        if (zza == null) {
            if (zzi.zzps != null) {
                return false;
            }
        } else if (!zza.equals(zzi.zzps)) {
            return false;
        }
        if (Float.floatToIntBits(this.zzpt) != Float.floatToIntBits(zzi.zzpt) || this.zzpu != zzi.zzpu || !zzuu.equals((Object[]) this.zzpv, (Object[]) zzi.zzpv) || this.zzpw != zzi.zzpw) {
            return false;
        }
        if (this.zzbhb == null || this.zzbhb.isEmpty()) {
            return zzi.zzbhb == null || zzi.zzbhb.isEmpty();
        }
        return this.zzbhb.equals(zzi.zzbhb);
    }

    public final int hashCode() {
        int i;
        int hashCode = (((((((((((((((((getClass().getName().hashCode() + 527) * 31) + zzuu.hashCode((Object[]) this.zzph)) * 31) + zzuu.hashCode((Object[]) this.zzpi)) * 31) + zzuu.hashCode((Object[]) this.zzpj)) * 31) + zzuu.hashCode((Object[]) this.zzpk)) * 31) + zzuu.hashCode((Object[]) this.zzpl)) * 31) + zzuu.hashCode((Object[]) this.zzpm)) * 31) + zzuu.hashCode((Object[]) this.zzpn)) * 31) + zzuu.hashCode((Object[]) this.zzpo)) * 31;
        String str = this.zzpp;
        int i2 = 0;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.zzpq;
        int hashCode3 = (hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.zzpr;
        int hashCode4 = (hashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.version;
        int hashCode5 = hashCode4 + (str4 == null ? 0 : str4.hashCode());
        zza zza = this.zzps;
        int i3 = hashCode5 * 31;
        if (zza == null) {
            i = 0;
        } else {
            i = zza.hashCode();
        }
        int floatToIntBits = (((((((((i3 + i) * 31) + Float.floatToIntBits(this.zzpt)) * 31) + (this.zzpu ? 1231 : 1237)) * 31) + zzuu.hashCode((Object[]) this.zzpv)) * 31) + this.zzpw) * 31;
        if (this.zzbhb != null && !this.zzbhb.isEmpty()) {
            i2 = this.zzbhb.hashCode();
        }
        return floatToIntBits + i2;
    }

    public final void zza(zzuo zzuo) throws IOException {
        String[] strArr = this.zzpi;
        int i = 0;
        if (strArr != null && strArr.length > 0) {
            int i2 = 0;
            while (true) {
                String[] strArr2 = this.zzpi;
                if (i2 >= strArr2.length) {
                    break;
                }
                String str = strArr2[i2];
                if (str != null) {
                    zzuo.zza(1, str);
                }
                i2++;
            }
        }
        zzl[] zzlArr = this.zzpj;
        if (zzlArr != null && zzlArr.length > 0) {
            int i3 = 0;
            while (true) {
                zzl[] zzlArr2 = this.zzpj;
                if (i3 >= zzlArr2.length) {
                    break;
                }
                zzl zzl = zzlArr2[i3];
                if (zzl != null) {
                    zzuo.zza(2, (zzuw) zzl);
                }
                i3++;
            }
        }
        zzd[] zzdArr = this.zzpk;
        if (zzdArr != null && zzdArr.length > 0) {
            int i4 = 0;
            while (true) {
                zzd[] zzdArr2 = this.zzpk;
                if (i4 >= zzdArr2.length) {
                    break;
                }
                zzd zzd = zzdArr2[i4];
                if (zzd != null) {
                    zzuo.zze(3, (zzsk) zzd);
                }
                i4++;
            }
        }
        zzb[] zzbArr = this.zzpl;
        if (zzbArr != null && zzbArr.length > 0) {
            int i5 = 0;
            while (true) {
                zzb[] zzbArr2 = this.zzpl;
                if (i5 >= zzbArr2.length) {
                    break;
                }
                zzb zzb = zzbArr2[i5];
                if (zzb != null) {
                    zzuo.zze(4, (zzsk) zzb);
                }
                i5++;
            }
        }
        zzb[] zzbArr3 = this.zzpm;
        if (zzbArr3 != null && zzbArr3.length > 0) {
            int i6 = 0;
            while (true) {
                zzb[] zzbArr4 = this.zzpm;
                if (i6 >= zzbArr4.length) {
                    break;
                }
                zzb zzb2 = zzbArr4[i6];
                if (zzb2 != null) {
                    zzuo.zze(5, (zzsk) zzb2);
                }
                i6++;
            }
        }
        zzb[] zzbArr5 = this.zzpn;
        if (zzbArr5 != null && zzbArr5.length > 0) {
            int i7 = 0;
            while (true) {
                zzb[] zzbArr6 = this.zzpn;
                if (i7 >= zzbArr6.length) {
                    break;
                }
                zzb zzb3 = zzbArr6[i7];
                if (zzb3 != null) {
                    zzuo.zze(6, (zzsk) zzb3);
                }
                i7++;
            }
        }
        zze[] zzeArr = this.zzpo;
        if (zzeArr != null && zzeArr.length > 0) {
            int i8 = 0;
            while (true) {
                zze[] zzeArr2 = this.zzpo;
                if (i8 >= zzeArr2.length) {
                    break;
                }
                zze zze = zzeArr2[i8];
                if (zze != null) {
                    zzuo.zze(7, (zzsk) zze);
                }
                i8++;
            }
        }
        String str2 = this.zzpp;
        String str3 = "";
        if (str2 != null && !str2.equals(str3)) {
            zzuo.zza(9, this.zzpp);
        }
        String str4 = this.zzpq;
        if (str4 != null && !str4.equals(str3)) {
            zzuo.zza(10, this.zzpq);
        }
        String str5 = this.zzpr;
        if (str5 != null && !str5.equals("0")) {
            zzuo.zza(12, this.zzpr);
        }
        String str6 = this.version;
        if (str6 != null && !str6.equals(str3)) {
            zzuo.zza(13, this.version);
        }
        zza zza = this.zzps;
        if (zza != null) {
            zzuo.zze(14, (zzsk) zza);
        }
        if (Float.floatToIntBits(this.zzpt) != Float.floatToIntBits(0.0f)) {
            float f = this.zzpt;
            zzuo.zzd(15, 5);
            zzuo.zzcc(Float.floatToIntBits(f));
        }
        String[] strArr3 = this.zzpv;
        if (strArr3 != null && strArr3.length > 0) {
            int i9 = 0;
            while (true) {
                String[] strArr4 = this.zzpv;
                if (i9 >= strArr4.length) {
                    break;
                }
                String str7 = strArr4[i9];
                if (str7 != null) {
                    zzuo.zza(16, str7);
                }
                i9++;
            }
        }
        int i10 = this.zzpw;
        if (i10 != 0) {
            zzuo.zze(17, i10);
        }
        boolean z = this.zzpu;
        if (z) {
            zzuo.zzb(18, z);
        }
        String[] strArr5 = this.zzph;
        if (strArr5 != null && strArr5.length > 0) {
            while (true) {
                String[] strArr6 = this.zzph;
                if (i >= strArr6.length) {
                    break;
                }
                String str8 = strArr6[i];
                if (str8 != null) {
                    zzuo.zza(19, str8);
                }
                i++;
            }
        }
        super.zza(zzuo);
    }

    /* access modifiers changed from: protected */
    public final int zzy() {
        int zzy = super.zzy();
        String[] strArr = this.zzpi;
        int i = 0;
        if (strArr != null && strArr.length > 0) {
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            while (true) {
                String[] strArr2 = this.zzpi;
                if (i2 >= strArr2.length) {
                    break;
                }
                String str = strArr2[i2];
                if (str != null) {
                    i4++;
                    i3 += zzuo.zzda(str);
                }
                i2++;
            }
            zzy = zzy + i3 + (i4 * 1);
        }
        zzl[] zzlArr = this.zzpj;
        if (zzlArr != null && zzlArr.length > 0) {
            int i5 = zzy;
            int i6 = 0;
            while (true) {
                zzl[] zzlArr2 = this.zzpj;
                if (i6 >= zzlArr2.length) {
                    break;
                }
                zzl zzl = zzlArr2[i6];
                if (zzl != null) {
                    i5 += zzuo.zzb(2, (zzuw) zzl);
                }
                i6++;
            }
            zzy = i5;
        }
        zzd[] zzdArr = this.zzpk;
        if (zzdArr != null && zzdArr.length > 0) {
            int i7 = zzy;
            int i8 = 0;
            while (true) {
                zzd[] zzdArr2 = this.zzpk;
                if (i8 >= zzdArr2.length) {
                    break;
                }
                zzd zzd = zzdArr2[i8];
                if (zzd != null) {
                    i7 += zzqj.zzc(3, (zzsk) zzd);
                }
                i8++;
            }
            zzy = i7;
        }
        zzb[] zzbArr = this.zzpl;
        if (zzbArr != null && zzbArr.length > 0) {
            int i9 = zzy;
            int i10 = 0;
            while (true) {
                zzb[] zzbArr2 = this.zzpl;
                if (i10 >= zzbArr2.length) {
                    break;
                }
                zzb zzb = zzbArr2[i10];
                if (zzb != null) {
                    i9 += zzqj.zzc(4, (zzsk) zzb);
                }
                i10++;
            }
            zzy = i9;
        }
        zzb[] zzbArr3 = this.zzpm;
        if (zzbArr3 != null && zzbArr3.length > 0) {
            int i11 = zzy;
            int i12 = 0;
            while (true) {
                zzb[] zzbArr4 = this.zzpm;
                if (i12 >= zzbArr4.length) {
                    break;
                }
                zzb zzb2 = zzbArr4[i12];
                if (zzb2 != null) {
                    i11 += zzqj.zzc(5, (zzsk) zzb2);
                }
                i12++;
            }
            zzy = i11;
        }
        zzb[] zzbArr5 = this.zzpn;
        if (zzbArr5 != null && zzbArr5.length > 0) {
            int i13 = zzy;
            int i14 = 0;
            while (true) {
                zzb[] zzbArr6 = this.zzpn;
                if (i14 >= zzbArr6.length) {
                    break;
                }
                zzb zzb3 = zzbArr6[i14];
                if (zzb3 != null) {
                    i13 += zzqj.zzc(6, (zzsk) zzb3);
                }
                i14++;
            }
            zzy = i13;
        }
        zze[] zzeArr = this.zzpo;
        if (zzeArr != null && zzeArr.length > 0) {
            int i15 = zzy;
            int i16 = 0;
            while (true) {
                zze[] zzeArr2 = this.zzpo;
                if (i16 >= zzeArr2.length) {
                    break;
                }
                zze zze = zzeArr2[i16];
                if (zze != null) {
                    i15 += zzqj.zzc(7, (zzsk) zze);
                }
                i16++;
            }
            zzy = i15;
        }
        String str2 = this.zzpp;
        String str3 = "";
        if (str2 != null && !str2.equals(str3)) {
            zzy += zzuo.zzb(9, this.zzpp);
        }
        String str4 = this.zzpq;
        if (str4 != null && !str4.equals(str3)) {
            zzy += zzuo.zzb(10, this.zzpq);
        }
        String str5 = this.zzpr;
        if (str5 != null && !str5.equals("0")) {
            zzy += zzuo.zzb(12, this.zzpr);
        }
        String str6 = this.version;
        if (str6 != null && !str6.equals(str3)) {
            zzy += zzuo.zzb(13, this.version);
        }
        zza zza = this.zzps;
        if (zza != null) {
            zzy += zzqj.zzc(14, (zzsk) zza);
        }
        if (Float.floatToIntBits(this.zzpt) != Float.floatToIntBits(0.0f)) {
            zzy += zzuo.zzbb(15) + 4;
        }
        String[] strArr3 = this.zzpv;
        if (strArr3 != null && strArr3.length > 0) {
            int i17 = 0;
            int i18 = 0;
            int i19 = 0;
            while (true) {
                String[] strArr4 = this.zzpv;
                if (i17 >= strArr4.length) {
                    break;
                }
                String str7 = strArr4[i17];
                if (str7 != null) {
                    i19++;
                    i18 += zzuo.zzda(str7);
                }
                i17++;
            }
            zzy = zzy + i18 + (i19 * 2);
        }
        int i20 = this.zzpw;
        if (i20 != 0) {
            zzy += zzuo.zzi(17, i20);
        }
        if (this.zzpu) {
            zzy += zzuo.zzbb(18) + 1;
        }
        String[] strArr5 = this.zzph;
        if (strArr5 == null || strArr5.length <= 0) {
            return zzy;
        }
        int i21 = 0;
        int i22 = 0;
        while (true) {
            String[] strArr6 = this.zzph;
            if (i >= strArr6.length) {
                return zzy + i21 + (i22 * 2);
            }
            String str8 = strArr6[i];
            if (str8 != null) {
                i22++;
                i21 += zzuo.zzda(str8);
            }
            i++;
        }
    }

    public final /* synthetic */ zzuw zza(zzun zzun) throws IOException {
        while (true) {
            int zzni = zzun.zzni();
            switch (zzni) {
                case 0:
                    return this;
                case 10:
                    int zzb = zzuz.zzb(zzun, 10);
                    String[] strArr = this.zzpi;
                    int length = strArr == null ? 0 : strArr.length;
                    String[] strArr2 = new String[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzpi, 0, strArr2, 0, length);
                    }
                    while (length < strArr2.length - 1) {
                        strArr2[length] = zzun.readString();
                        zzun.zzni();
                        length++;
                    }
                    strArr2[length] = zzun.readString();
                    this.zzpi = strArr2;
                    break;
                case 18:
                    int zzb2 = zzuz.zzb(zzun, 18);
                    zzl[] zzlArr = this.zzpj;
                    int length2 = zzlArr == null ? 0 : zzlArr.length;
                    zzl[] zzlArr2 = new zzl[(zzb2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.zzpj, 0, zzlArr2, 0, length2);
                    }
                    while (length2 < zzlArr2.length - 1) {
                        zzlArr2[length2] = new zzl();
                        zzun.zza((zzuw) zzlArr2[length2]);
                        zzun.zzni();
                        length2++;
                    }
                    zzlArr2[length2] = new zzl();
                    zzun.zza((zzuw) zzlArr2[length2]);
                    this.zzpj = zzlArr2;
                    break;
                case 26:
                    int zzb3 = zzuz.zzb(zzun, 26);
                    zzd[] zzdArr = this.zzpk;
                    int length3 = zzdArr == null ? 0 : zzdArr.length;
                    zzd[] zzdArr2 = new zzd[(zzb3 + length3)];
                    if (length3 != 0) {
                        System.arraycopy(this.zzpk, 0, zzdArr2, 0, length3);
                    }
                    while (length3 < zzdArr2.length - 1) {
                        zzdArr2[length3] = (zzd) zzun.zza(zzd.zza());
                        zzun.zzni();
                        length3++;
                    }
                    zzdArr2[length3] = (zzd) zzun.zza(zzd.zza());
                    this.zzpk = zzdArr2;
                    break;
                case 34:
                    int zzb4 = zzuz.zzb(zzun, 34);
                    zzb[] zzbArr = this.zzpl;
                    int length4 = zzbArr == null ? 0 : zzbArr.length;
                    zzb[] zzbArr2 = new zzb[(zzb4 + length4)];
                    if (length4 != 0) {
                        System.arraycopy(this.zzpl, 0, zzbArr2, 0, length4);
                    }
                    while (length4 < zzbArr2.length - 1) {
                        zzbArr2[length4] = (zzb) zzun.zza(zzb.zza());
                        zzun.zzni();
                        length4++;
                    }
                    zzbArr2[length4] = (zzb) zzun.zza(zzb.zza());
                    this.zzpl = zzbArr2;
                    break;
                case 42:
                    int zzb5 = zzuz.zzb(zzun, 42);
                    zzb[] zzbArr3 = this.zzpm;
                    int length5 = zzbArr3 == null ? 0 : zzbArr3.length;
                    zzb[] zzbArr4 = new zzb[(zzb5 + length5)];
                    if (length5 != 0) {
                        System.arraycopy(this.zzpm, 0, zzbArr4, 0, length5);
                    }
                    while (length5 < zzbArr4.length - 1) {
                        zzbArr4[length5] = (zzb) zzun.zza(zzb.zza());
                        zzun.zzni();
                        length5++;
                    }
                    zzbArr4[length5] = (zzb) zzun.zza(zzb.zza());
                    this.zzpm = zzbArr4;
                    break;
                case 50:
                    int zzb6 = zzuz.zzb(zzun, 50);
                    zzb[] zzbArr5 = this.zzpn;
                    int length6 = zzbArr5 == null ? 0 : zzbArr5.length;
                    zzb[] zzbArr6 = new zzb[(zzb6 + length6)];
                    if (length6 != 0) {
                        System.arraycopy(this.zzpn, 0, zzbArr6, 0, length6);
                    }
                    while (length6 < zzbArr6.length - 1) {
                        zzbArr6[length6] = (zzb) zzun.zza(zzb.zza());
                        zzun.zzni();
                        length6++;
                    }
                    zzbArr6[length6] = (zzb) zzun.zza(zzb.zza());
                    this.zzpn = zzbArr6;
                    break;
                case 58:
                    int zzb7 = zzuz.zzb(zzun, 58);
                    zze[] zzeArr = this.zzpo;
                    int length7 = zzeArr == null ? 0 : zzeArr.length;
                    zze[] zzeArr2 = new zze[(zzb7 + length7)];
                    if (length7 != 0) {
                        System.arraycopy(this.zzpo, 0, zzeArr2, 0, length7);
                    }
                    while (length7 < zzeArr2.length - 1) {
                        zzeArr2[length7] = (zze) zzun.zza(zze.zza());
                        zzun.zzni();
                        length7++;
                    }
                    zzeArr2[length7] = (zze) zzun.zza(zze.zza());
                    this.zzpo = zzeArr2;
                    break;
                case 74:
                    this.zzpp = zzun.readString();
                    break;
                case 82:
                    this.zzpq = zzun.readString();
                    break;
                case 98:
                    this.zzpr = zzun.readString();
                    break;
                case 106:
                    this.version = zzun.readString();
                    break;
                case 114:
                    zza zza = (zza) zzun.zza(zza.zza());
                    zza zza2 = this.zzps;
                    if (zza2 != null) {
                        zza = (zza) ((zzrc) ((C6218zza) ((C6218zza) zza2.zzpd()).zza(zza)).zzpm());
                    }
                    this.zzps = zza;
                    break;
                case Opcodes.LUSHR /*125*/:
                    this.zzpt = Float.intBitsToFloat(zzun.zzoc());
                    break;
                case 130:
                    int zzb8 = zzuz.zzb(zzun, 130);
                    String[] strArr3 = this.zzpv;
                    int length8 = strArr3 == null ? 0 : strArr3.length;
                    String[] strArr4 = new String[(zzb8 + length8)];
                    if (length8 != 0) {
                        System.arraycopy(this.zzpv, 0, strArr4, 0, length8);
                    }
                    while (length8 < strArr4.length - 1) {
                        strArr4[length8] = zzun.readString();
                        zzun.zzni();
                        length8++;
                    }
                    strArr4[length8] = zzun.readString();
                    this.zzpv = strArr4;
                    break;
                case Opcodes.L2I /*136*/:
                    this.zzpw = zzun.zzoa();
                    break;
                case 144:
                    this.zzpu = zzun.zzno();
                    break;
                case Opcodes.IFNE /*154*/:
                    int zzb9 = zzuz.zzb(zzun, Opcodes.IFNE);
                    String[] strArr5 = this.zzph;
                    int length9 = strArr5 == null ? 0 : strArr5.length;
                    String[] strArr6 = new String[(zzb9 + length9)];
                    if (length9 != 0) {
                        System.arraycopy(this.zzph, 0, strArr6, 0, length9);
                    }
                    while (length9 < strArr6.length - 1) {
                        strArr6[length9] = zzun.readString();
                        zzun.zzni();
                        length9++;
                    }
                    strArr6[length9] = zzun.readString();
                    this.zzph = strArr6;
                    break;
                default:
                    if (super.zza(zzun, zzni)) {
                        break;
                    } else {
                        return this;
                    }
            }
        }
    }
}

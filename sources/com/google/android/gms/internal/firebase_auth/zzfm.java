package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.firebase_auth.zzp.C6215zzp;
import com.google.android.gms.internal.firebase_auth.zzp.C6215zzp.zza;
import com.google.firebase.auth.api.internal.zzfd;

public final class zzfm extends AbstractSafeParcelable implements zzfd<C6215zzp> {
    public static final Creator<zzfm> CREATOR = new zzfp();
    private String zzhy;
    private String zzia;
    private String zzib;
    private String zzic;
    private String zzie;
    private String zzif;
    private boolean zzjp;
    private boolean zzsj;
    private String zzsn;
    private String zzso;
    private String zzsp;
    private String zzsq;
    private String zzsr;
    private String zzss;
    private String zzst;
    private boolean zzsu;

    public zzfm() {
        this.zzsj = true;
        this.zzjp = true;
    }

    zzfm(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, boolean z, boolean z2, String str9, String str10, String str11, String str12, boolean z3, String str13) {
        this.zzsn = str;
        this.zzso = str2;
        this.zzib = str3;
        this.zzic = str4;
        this.zzia = str5;
        this.zzif = str6;
        this.zzsp = str7;
        this.zzsq = str8;
        this.zzsj = z;
        this.zzjp = z2;
        this.zzsr = str9;
        this.zzss = str10;
        this.zzst = str11;
        this.zzhy = str12;
        this.zzsu = z3;
        this.zzie = str13;
    }

    public zzfm(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        this.zzsn = "http://localhost";
        this.zzib = str;
        this.zzic = str2;
        this.zzsq = str5;
        this.zzsr = str6;
        this.zzhy = str7;
        this.zzie = str8;
        this.zzsj = true;
        if (!TextUtils.isEmpty(this.zzib) || !TextUtils.isEmpty(this.zzic) || !TextUtils.isEmpty(this.zzsr)) {
            this.zzia = Preconditions.checkNotEmpty(str3);
            this.zzif = null;
            StringBuilder sb = new StringBuilder();
            String str9 = "&";
            if (!TextUtils.isEmpty(this.zzib)) {
                sb.append("id_token=");
                sb.append(this.zzib);
                sb.append(str9);
            }
            if (!TextUtils.isEmpty(this.zzic)) {
                sb.append("access_token=");
                sb.append(this.zzic);
                sb.append(str9);
            }
            if (!TextUtils.isEmpty(this.zzif)) {
                sb.append("identifier=");
                sb.append(this.zzif);
                sb.append(str9);
            }
            if (!TextUtils.isEmpty(this.zzsq)) {
                sb.append("oauth_token_secret=");
                sb.append(this.zzsq);
                sb.append(str9);
            }
            if (!TextUtils.isEmpty(this.zzsr)) {
                sb.append("code=");
                sb.append(this.zzsr);
                sb.append(str9);
            }
            sb.append("providerId=");
            sb.append(this.zzia);
            this.zzsp = sb.toString();
            this.zzjp = true;
            return;
        }
        throw new IllegalArgumentException("idToken, accessToken and authCode cannot all be null");
    }

    public final zzfm zzcy(String str) {
        this.zzso = Preconditions.checkNotEmpty(str);
        return this;
    }

    public final zzfm zzp(boolean z) {
        this.zzjp = false;
        return this;
    }

    public final zzfm zzcz(String str) {
        this.zzhy = str;
        return this;
    }

    public final zzfm zzq(boolean z) {
        this.zzsj = true;
        return this;
    }

    public final zzfm zzr(boolean z) {
        this.zzsu = z;
        return this;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzsn, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzso, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzib, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzic, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzia, false);
        SafeParcelWriter.writeString(parcel, 7, this.zzif, false);
        SafeParcelWriter.writeString(parcel, 8, this.zzsp, false);
        SafeParcelWriter.writeString(parcel, 9, this.zzsq, false);
        SafeParcelWriter.writeBoolean(parcel, 10, this.zzsj);
        SafeParcelWriter.writeBoolean(parcel, 11, this.zzjp);
        SafeParcelWriter.writeString(parcel, 12, this.zzsr, false);
        SafeParcelWriter.writeString(parcel, 13, this.zzss, false);
        SafeParcelWriter.writeString(parcel, 14, this.zzst, false);
        SafeParcelWriter.writeString(parcel, 15, this.zzhy, false);
        SafeParcelWriter.writeBoolean(parcel, 16, this.zzsu);
        SafeParcelWriter.writeString(parcel, 17, this.zzie, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final /* synthetic */ zzjc zzeq() {
        zza zzk = C6215zzp.zzat().zzi(this.zzsj).zzk(this.zzjp);
        String str = this.zzso;
        if (str != null) {
            zzk.zzbg(str);
        }
        String str2 = this.zzsn;
        if (str2 != null) {
            zzk.zzbd(str2);
        }
        String str3 = this.zzsp;
        if (str3 != null) {
            zzk.zzbe(str3);
        }
        String str4 = this.zzhy;
        if (str4 != null) {
            zzk.zzbh(str4);
        }
        String str5 = this.zzie;
        if (str5 != null) {
            zzk.zzbi(str5);
        }
        if (!TextUtils.isEmpty(this.zzss)) {
            zzk.zzbf(this.zzss);
        }
        if (!TextUtils.isEmpty(this.zzst)) {
            zzk.zzbd(this.zzst);
        }
        return (C6215zzp) ((zzhs) zzk.zzj(this.zzsu).zzih());
    }
}

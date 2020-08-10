package com.google.firebase.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.firebase_auth.zzfm;

public class zzf extends OAuthCredential {
    public static final Creator<zzf> CREATOR = new zze();
    private final String zzia;
    private final String zzib;
    private final String zzic;
    private final zzfm zzid;
    private final String zzie;

    zzf(String str, String str2, String str3, zzfm zzfm, String str4) {
        this.zzia = str;
        this.zzib = str2;
        this.zzic = str3;
        this.zzid = zzfm;
        this.zzie = str4;
    }

    public static zzf zza(String str, String str2, String str3) {
        return zzb(str, str2, str3, null);
    }

    public static zzf zza(String str, String str2, String str3, String str4) {
        return zzb(str, str2, str3, str4);
    }

    private static zzf zzb(String str, String str2, String str3, String str4) {
        Preconditions.checkNotEmpty(str, "Must specify a non-empty providerId");
        if (!TextUtils.isEmpty(str2) || !TextUtils.isEmpty(str3)) {
            zzf zzf = new zzf(str, str2, str3, null, str4);
            return zzf;
        }
        throw new IllegalArgumentException("Must specify an idToken or an accessToken.");
    }

    public static zzf zza(zzfm zzfm) {
        Preconditions.checkNotNull(zzfm, "Must specify a non-null webSignInCredential");
        zzf zzf = new zzf(null, null, null, zzfm, null);
        return zzf;
    }

    public String getProvider() {
        return this.zzia;
    }

    public String getSignInMethod() {
        return this.zzia;
    }

    public String getIdToken() {
        return this.zzib;
    }

    public String getAccessToken() {
        return this.zzic;
    }

    public static zzfm zza(zzf zzf, String str) {
        Preconditions.checkNotNull(zzf);
        zzfm zzfm = zzf.zzid;
        if (zzfm != null) {
            return zzfm;
        }
        zzfm zzfm2 = new zzfm(zzf.getIdToken(), zzf.getAccessToken(), zzf.getProvider(), null, null, null, str, zzf.zzie);
        return zzfm2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, getProvider(), false);
        SafeParcelWriter.writeString(parcel, 2, getIdToken(), false);
        SafeParcelWriter.writeString(parcel, 3, getAccessToken(), false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzid, i, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzie, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}

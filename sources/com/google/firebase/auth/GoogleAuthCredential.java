package com.google.firebase.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.firebase_auth.zzfm;

public class GoogleAuthCredential extends AuthCredential {
    public static final Creator<GoogleAuthCredential> CREATOR = new zzt();
    private final String zzib;
    private final String zzic;

    GoogleAuthCredential(String str, String str2) {
        if (str == null && str2 == null) {
            throw new IllegalArgumentException("Must specify an idToken or an accessToken.");
        }
        this.zzib = zzb(str, "idToken");
        this.zzic = zzb(str2, "accessToken");
    }

    public String getProvider() {
        return "google.com";
    }

    public String getSignInMethod() {
        return "google.com";
    }

    public static zzfm zza(GoogleAuthCredential googleAuthCredential, String str) {
        Preconditions.checkNotNull(googleAuthCredential);
        zzfm zzfm = new zzfm(googleAuthCredential.zzib, googleAuthCredential.zzic, googleAuthCredential.getProvider(), null, null, null, str, null);
        return zzfm;
    }

    private static String zzb(String str, String str2) {
        if (str == null || !TextUtils.isEmpty(str)) {
            return str;
        }
        throw new IllegalArgumentException(String.valueOf(str2).concat(" must not be empty"));
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzib, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzic, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}

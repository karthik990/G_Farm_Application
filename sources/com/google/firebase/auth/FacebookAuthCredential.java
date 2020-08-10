package com.google.firebase.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.firebase_auth.zzfm;

public class FacebookAuthCredential extends AuthCredential {
    public static final Creator<FacebookAuthCredential> CREATOR = new zzh();
    private final String zzic;

    FacebookAuthCredential(String str) {
        this.zzic = Preconditions.checkNotEmpty(str);
    }

    public String getProvider() {
        return "facebook.com";
    }

    public String getSignInMethod() {
        return "facebook.com";
    }

    public static zzfm zza(FacebookAuthCredential facebookAuthCredential, String str) {
        Preconditions.checkNotNull(facebookAuthCredential);
        zzfm zzfm = new zzfm(null, facebookAuthCredential.zzic, facebookAuthCredential.getProvider(), null, null, null, str, null);
        return zzfm;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzic, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}

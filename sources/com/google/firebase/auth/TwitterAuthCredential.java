package com.google.firebase.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.firebase_auth.zzfm;

public class TwitterAuthCredential extends AuthCredential {
    public static final Creator<TwitterAuthCredential> CREATOR = new zzae();
    private String zzji;
    private String zzjy;

    TwitterAuthCredential(String str, String str2) {
        this.zzji = Preconditions.checkNotEmpty(str);
        this.zzjy = Preconditions.checkNotEmpty(str2);
    }

    public String getProvider() {
        return "twitter.com";
    }

    public String getSignInMethod() {
        return "twitter.com";
    }

    public static zzfm zza(TwitterAuthCredential twitterAuthCredential, String str) {
        Preconditions.checkNotNull(twitterAuthCredential);
        zzfm zzfm = new zzfm(null, twitterAuthCredential.zzji, twitterAuthCredential.getProvider(), null, twitterAuthCredential.zzjy, null, str, null);
        return zzfm;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzji, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzjy, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}

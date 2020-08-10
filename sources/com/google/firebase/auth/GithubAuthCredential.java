package com.google.firebase.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.firebase_auth.zzfm;

public class GithubAuthCredential extends AuthCredential {
    public static final Creator<GithubAuthCredential> CREATOR = new zzs();
    private String zzji;

    GithubAuthCredential(String str) {
        this.zzji = Preconditions.checkNotEmpty(str);
    }

    public String getProvider() {
        return "github.com";
    }

    public String getSignInMethod() {
        return "github.com";
    }

    public static zzfm zza(GithubAuthCredential githubAuthCredential, String str) {
        Preconditions.checkNotNull(githubAuthCredential);
        zzfm zzfm = new zzfm(null, githubAuthCredential.zzji, githubAuthCredential.getProvider(), null, null, null, str, null);
        return zzfm;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzji, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}

package com.google.firebase.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.firebase_auth.zzfm;

public class PlayGamesAuthCredential extends AuthCredential {
    public static final Creator<PlayGamesAuthCredential> CREATOR = new zzad();
    private final String zzjx;

    PlayGamesAuthCredential(String str) {
        this.zzjx = Preconditions.checkNotEmpty(str);
    }

    public String getProvider() {
        return "playgames.google.com";
    }

    public String getSignInMethod() {
        return "playgames.google.com";
    }

    public static zzfm zza(PlayGamesAuthCredential playGamesAuthCredential, String str) {
        Preconditions.checkNotNull(playGamesAuthCredential);
        zzfm zzfm = new zzfm(null, null, playGamesAuthCredential.getProvider(), null, null, playGamesAuthCredential.zzjx, str, null);
        return zzfm;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzjx, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}

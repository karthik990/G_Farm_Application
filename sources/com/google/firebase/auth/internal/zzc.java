package com.google.firebase.auth.internal;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzfm;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.GithubAuthCredential;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.PlayGamesAuthCredential;
import com.google.firebase.auth.TwitterAuthCredential;
import com.google.firebase.auth.zzf;

public final class zzc {
    public static zzfm zza(AuthCredential authCredential, String str) {
        Preconditions.checkNotNull(authCredential);
        if (GoogleAuthCredential.class.isAssignableFrom(authCredential.getClass())) {
            return GoogleAuthCredential.zza((GoogleAuthCredential) authCredential, str);
        }
        if (FacebookAuthCredential.class.isAssignableFrom(authCredential.getClass())) {
            return FacebookAuthCredential.zza((FacebookAuthCredential) authCredential, str);
        }
        if (TwitterAuthCredential.class.isAssignableFrom(authCredential.getClass())) {
            return TwitterAuthCredential.zza((TwitterAuthCredential) authCredential, str);
        }
        if (GithubAuthCredential.class.isAssignableFrom(authCredential.getClass())) {
            return GithubAuthCredential.zza((GithubAuthCredential) authCredential, str);
        }
        if (PlayGamesAuthCredential.class.isAssignableFrom(authCredential.getClass())) {
            return PlayGamesAuthCredential.zza((PlayGamesAuthCredential) authCredential, str);
        }
        if (zzf.class.isAssignableFrom(authCredential.getClass())) {
            return zzf.zza((zzf) authCredential, str);
        }
        throw new IllegalArgumentException("Unsupported credential type.");
    }
}

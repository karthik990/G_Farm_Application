package com.google.firebase.auth.internal;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import com.google.android.gms.internal.firebase_auth.zzfm;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.zzf;

public final class zzac {
    private static zzac zzue;
    private boolean zzud = false;

    private zzac() {
    }

    public static zzac zzfk() {
        if (zzue == null) {
            zzue = new zzac();
        }
        return zzue;
    }

    public final boolean zza(Activity activity, TaskCompletionSource<AuthResult> taskCompletionSource, FirebaseAuth firebaseAuth) {
        return zza(activity, taskCompletionSource, firebaseAuth, (FirebaseUser) null);
    }

    public final boolean zza(Activity activity, TaskCompletionSource<AuthResult> taskCompletionSource, FirebaseAuth firebaseAuth, FirebaseUser firebaseUser) {
        if (this.zzud) {
            return false;
        }
        LocalBroadcastManager instance = LocalBroadcastManager.getInstance(activity);
        zzah zzah = new zzah(this, activity, taskCompletionSource, firebaseAuth, firebaseUser);
        instance.registerReceiver(zzah, new IntentFilter("com.google.firebase.auth.ACTION_RECEIVE_FIREBASE_AUTH_INTENT"));
        this.zzud = true;
        return true;
    }

    /* access modifiers changed from: private */
    public final void zza(Intent intent, TaskCompletionSource<AuthResult> taskCompletionSource, FirebaseAuth firebaseAuth) {
        firebaseAuth.signInWithCredential(zza(intent)).addOnSuccessListener(new zzae(this, taskCompletionSource)).addOnFailureListener(new zzab(this, taskCompletionSource));
    }

    /* access modifiers changed from: private */
    public final void zza(Intent intent, TaskCompletionSource<AuthResult> taskCompletionSource, FirebaseUser firebaseUser) {
        firebaseUser.linkWithCredential(zza(intent)).addOnSuccessListener(new zzag(this, taskCompletionSource)).addOnFailureListener(new zzad(this, taskCompletionSource));
    }

    /* access modifiers changed from: private */
    public final void zzb(Intent intent, TaskCompletionSource<AuthResult> taskCompletionSource, FirebaseUser firebaseUser) {
        firebaseUser.reauthenticateAndRetrieveData(zza(intent)).addOnSuccessListener(new zzai(this, taskCompletionSource)).addOnFailureListener(new zzaf(this, taskCompletionSource));
    }

    private static AuthCredential zza(Intent intent) {
        Preconditions.checkNotNull(intent);
        return zzf.zza(((zzfm) SafeParcelableSerializer.deserializeFromIntentExtra(intent, "com.google.firebase.auth.internal.VERIFY_ASSERTION_REQUEST", zzfm.CREATOR)).zzq(true));
    }

    static void zzfl() {
        zzue.zzud = false;
    }
}

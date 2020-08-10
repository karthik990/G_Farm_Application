package com.google.firebase.auth;

import android.app.Activity;
import android.net.Uri;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import java.util.List;

public abstract class FirebaseUser extends AbstractSafeParcelable implements UserInfo {
    public abstract String getDisplayName();

    public abstract String getEmail();

    public abstract FirebaseUserMetadata getMetadata();

    public abstract String getPhoneNumber();

    public abstract Uri getPhotoUrl();

    public abstract List<? extends UserInfo> getProviderData();

    public abstract String getProviderId();

    public abstract String getUid();

    public abstract boolean isAnonymous();

    public abstract FirebaseUser zza(List<? extends UserInfo> list);

    public abstract void zza(zzes zzes);

    public abstract void zzb(List<zzx> list);

    public abstract String zzba();

    public abstract FirebaseApp zzcu();

    public abstract List<String> zzcw();

    public abstract FirebaseUser zzcx();

    public abstract zzes zzcy();

    public abstract String zzcz();

    public abstract String zzda();

    public abstract zzv zzdb();

    public Task<GetTokenResult> getIdToken(boolean z) {
        return FirebaseAuth.getInstance(zzcu()).zza(this, z);
    }

    public Task<Void> reload() {
        return FirebaseAuth.getInstance(zzcu()).zzd(this);
    }

    public Task<Void> reauthenticate(AuthCredential authCredential) {
        Preconditions.checkNotNull(authCredential);
        return FirebaseAuth.getInstance(zzcu()).zza(this, authCredential);
    }

    public Task<AuthResult> reauthenticateAndRetrieveData(AuthCredential authCredential) {
        Preconditions.checkNotNull(authCredential);
        return FirebaseAuth.getInstance(zzcu()).zzb(this, authCredential);
    }

    public Task<AuthResult> startActivityForReauthenticateWithProvider(Activity activity, FederatedAuthProvider federatedAuthProvider) {
        Preconditions.checkNotNull(activity);
        Preconditions.checkNotNull(federatedAuthProvider);
        return FirebaseAuth.getInstance(zzcu()).zzb(activity, federatedAuthProvider, this);
    }

    public Task<AuthResult> linkWithCredential(AuthCredential authCredential) {
        Preconditions.checkNotNull(authCredential);
        return FirebaseAuth.getInstance(zzcu()).zzc(this, authCredential);
    }

    public Task<AuthResult> startActivityForLinkWithProvider(Activity activity, FederatedAuthProvider federatedAuthProvider) {
        Preconditions.checkNotNull(activity);
        Preconditions.checkNotNull(federatedAuthProvider);
        return FirebaseAuth.getInstance(zzcu()).zza(activity, federatedAuthProvider, this);
    }

    public Task<AuthResult> unlink(String str) {
        Preconditions.checkNotEmpty(str);
        return FirebaseAuth.getInstance(zzcu()).zza(this, str);
    }

    public Task<Void> updateProfile(UserProfileChangeRequest userProfileChangeRequest) {
        Preconditions.checkNotNull(userProfileChangeRequest);
        return FirebaseAuth.getInstance(zzcu()).zza(this, userProfileChangeRequest);
    }

    public Task<Void> updateEmail(String str) {
        Preconditions.checkNotEmpty(str);
        return FirebaseAuth.getInstance(zzcu()).zzb(this, str);
    }

    public Task<Void> updatePhoneNumber(PhoneAuthCredential phoneAuthCredential) {
        return FirebaseAuth.getInstance(zzcu()).zza(this, phoneAuthCredential);
    }

    public Task<Void> updatePassword(String str) {
        Preconditions.checkNotEmpty(str);
        return FirebaseAuth.getInstance(zzcu()).zzc(this, str);
    }

    public Task<Void> delete() {
        return FirebaseAuth.getInstance(zzcu()).zze(this);
    }

    public Task<Void> sendEmailVerification() {
        return FirebaseAuth.getInstance(zzcu()).zza(this, false).continueWithTask(new zzq(this));
    }

    public Task<Void> sendEmailVerification(ActionCodeSettings actionCodeSettings) {
        return FirebaseAuth.getInstance(zzcu()).zza(this, false).continueWithTask(new zzr(this, actionCodeSettings));
    }
}

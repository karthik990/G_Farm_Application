package com.google.firebase.auth;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.logging.Logger;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class PhoneAuthProvider {
    public static final String PHONE_SIGN_IN_METHOD = "phone";
    public static final String PROVIDER_ID = "phone";
    private FirebaseAuth zzjr;

    public static class ForceResendingToken extends AbstractSafeParcelable {
        public static final Creator<ForceResendingToken> CREATOR = new zzd();

        ForceResendingToken() {
        }

        public void writeToParcel(Parcel parcel, int i) {
            SafeParcelWriter.finishObjectHeader(parcel, SafeParcelWriter.beginObjectHeader(parcel));
        }

        public static ForceResendingToken zzdd() {
            return new ForceResendingToken();
        }
    }

    public static abstract class OnVerificationStateChangedCallbacks {
        private static final Logger zzjt = new Logger("PhoneAuthProvider", new String[0]);

        public void onCodeSent(String str, ForceResendingToken forceResendingToken) {
        }

        public abstract void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential);

        public abstract void onVerificationFailed(FirebaseException firebaseException);

        public void onCodeAutoRetrievalTimeOut(String str) {
            zzjt.mo26593i("Sms auto retrieval timed-out.", new Object[0]);
        }
    }

    private PhoneAuthProvider(FirebaseAuth firebaseAuth) {
        this.zzjr = firebaseAuth;
    }

    public static PhoneAuthProvider getInstance(FirebaseAuth firebaseAuth) {
        return new PhoneAuthProvider(firebaseAuth);
    }

    public static PhoneAuthProvider getInstance() {
        return new PhoneAuthProvider(FirebaseAuth.getInstance(FirebaseApp.getInstance()));
    }

    public void verifyPhoneNumber(String str, long j, TimeUnit timeUnit, Activity activity, OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks) {
        zza(Preconditions.checkNotEmpty(str), j, timeUnit, (Activity) Preconditions.checkNotNull(activity), TaskExecutors.MAIN_THREAD, (OnVerificationStateChangedCallbacks) Preconditions.checkNotNull(onVerificationStateChangedCallbacks), null);
    }

    public void verifyPhoneNumber(String str, long j, TimeUnit timeUnit, Executor executor, OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks) {
        zza(Preconditions.checkNotEmpty(str), j, timeUnit, null, (Executor) Preconditions.checkNotNull(executor), (OnVerificationStateChangedCallbacks) Preconditions.checkNotNull(onVerificationStateChangedCallbacks), null);
    }

    public void verifyPhoneNumber(String str, long j, TimeUnit timeUnit, Activity activity, OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks, ForceResendingToken forceResendingToken) {
        zza(Preconditions.checkNotEmpty(str), j, timeUnit, (Activity) Preconditions.checkNotNull(activity), TaskExecutors.MAIN_THREAD, (OnVerificationStateChangedCallbacks) Preconditions.checkNotNull(onVerificationStateChangedCallbacks), forceResendingToken);
    }

    public void verifyPhoneNumber(String str, long j, TimeUnit timeUnit, Executor executor, OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks, ForceResendingToken forceResendingToken) {
        zza(Preconditions.checkNotEmpty(str), j, timeUnit, null, (Executor) Preconditions.checkNotNull(executor), (OnVerificationStateChangedCallbacks) Preconditions.checkNotNull(onVerificationStateChangedCallbacks), forceResendingToken);
    }

    private final void zza(String str, long j, TimeUnit timeUnit, Activity activity, Executor executor, OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks, ForceResendingToken forceResendingToken) {
        this.zzjr.zza(str, j, timeUnit, onVerificationStateChangedCallbacks, activity, executor, forceResendingToken != null);
    }

    public static PhoneAuthCredential getCredential(String str, String str2) {
        PhoneAuthCredential phoneAuthCredential = new PhoneAuthCredential(str, str2, false, null, true, null);
        return phoneAuthCredential;
    }
}

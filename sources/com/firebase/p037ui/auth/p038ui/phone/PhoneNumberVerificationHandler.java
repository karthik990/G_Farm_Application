package com.firebase.p037ui.auth.p038ui.phone;

import android.app.Application;
import android.os.Bundle;
import com.firebase.p037ui.auth.data.model.PhoneNumberVerificationRequiredException;
import com.firebase.p037ui.auth.data.model.Resource;
import com.firebase.p037ui.auth.viewmodel.AuthViewModelBase;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken;
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks;
import java.util.concurrent.TimeUnit;

/* renamed from: com.firebase.ui.auth.ui.phone.PhoneNumberVerificationHandler */
public class PhoneNumberVerificationHandler extends AuthViewModelBase<PhoneVerification> {
    private static final long AUTO_RETRIEVAL_TIMEOUT_SECONDS = 120;
    private static final String VERIFICATION_ID_KEY = "verification_id";
    /* access modifiers changed from: private */
    public ForceResendingToken mForceResendingToken;
    /* access modifiers changed from: private */
    public String mVerificationId;

    public PhoneNumberVerificationHandler(Application application) {
        super(application);
    }

    public void verifyPhoneNumber(final String str, boolean z) {
        setResult(Resource.forLoading());
        getPhoneAuth().verifyPhoneNumber(str, (long) AUTO_RETRIEVAL_TIMEOUT_SECONDS, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD, (OnVerificationStateChangedCallbacks) new OnVerificationStateChangedCallbacks() {
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                PhoneNumberVerificationHandler.this.setResult(Resource.forSuccess(new PhoneVerification(str, phoneAuthCredential, true)));
            }

            public void onVerificationFailed(FirebaseException firebaseException) {
                PhoneNumberVerificationHandler.this.setResult(Resource.forFailure(firebaseException));
            }

            public void onCodeSent(String str, ForceResendingToken forceResendingToken) {
                PhoneNumberVerificationHandler.this.mVerificationId = str;
                PhoneNumberVerificationHandler.this.mForceResendingToken = forceResendingToken;
                PhoneNumberVerificationHandler.this.setResult(Resource.forFailure(new PhoneNumberVerificationRequiredException(str)));
            }
        }, z ? this.mForceResendingToken : null);
    }

    public void submitVerificationCode(String str, String str2) {
        setResult(Resource.forSuccess(new PhoneVerification(str, PhoneAuthProvider.getCredential(this.mVerificationId, str2), false)));
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString(VERIFICATION_ID_KEY, this.mVerificationId);
    }

    public void onRestoreInstanceState(Bundle bundle) {
        if (this.mVerificationId == null && bundle != null) {
            this.mVerificationId = bundle.getString(VERIFICATION_ID_KEY);
        }
    }
}

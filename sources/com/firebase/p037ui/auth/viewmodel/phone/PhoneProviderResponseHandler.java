package com.firebase.p037ui.auth.viewmodel.phone;

import android.app.Application;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.data.model.Resource;
import com.firebase.p037ui.auth.util.data.AuthOperationManager;
import com.firebase.p037ui.auth.viewmodel.SignInViewModelBase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.PhoneAuthCredential;

/* renamed from: com.firebase.ui.auth.viewmodel.phone.PhoneProviderResponseHandler */
public class PhoneProviderResponseHandler extends SignInViewModelBase {
    public PhoneProviderResponseHandler(Application application) {
        super(application);
    }

    public void startSignIn(PhoneAuthCredential phoneAuthCredential, final IdpResponse idpResponse) {
        if (!idpResponse.isSuccessful()) {
            setResult(Resource.forFailure(idpResponse.getError()));
        } else if (idpResponse.getProviderType().equals("phone")) {
            setResult(Resource.forLoading());
            AuthOperationManager.getInstance().signInAndLinkWithCredential(getAuth(), (FlowParameters) getArguments(), phoneAuthCredential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                public void onSuccess(AuthResult authResult) {
                    PhoneProviderResponseHandler.this.handleSuccess(idpResponse, authResult);
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception exc) {
                    if (exc instanceof FirebaseAuthUserCollisionException) {
                        PhoneProviderResponseHandler.this.handleMergeFailure(((FirebaseAuthUserCollisionException) exc).getUpdatedCredential());
                    } else {
                        PhoneProviderResponseHandler.this.setResult(Resource.forFailure(exc));
                    }
                }
            });
        } else {
            throw new IllegalStateException("This handler cannot be used without a phone response.");
        }
    }
}

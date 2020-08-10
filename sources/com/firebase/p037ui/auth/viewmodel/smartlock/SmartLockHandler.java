package com.firebase.p037ui.auth.viewmodel.smartlock;

import android.app.Application;
import android.util.Log;
import com.firebase.p037ui.auth.FirebaseUiException;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.data.model.PendingIntentRequiredException;
import com.firebase.p037ui.auth.data.model.Resource;
import com.firebase.p037ui.auth.util.CredentialUtils;
import com.firebase.p037ui.auth.util.GoogleApiUtils;
import com.firebase.p037ui.auth.util.data.ProviderUtils;
import com.firebase.p037ui.auth.viewmodel.AuthViewModelBase;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

/* renamed from: com.firebase.ui.auth.viewmodel.smartlock.SmartLockHandler */
public class SmartLockHandler extends AuthViewModelBase<IdpResponse> {
    private static final String TAG = "SmartLockViewModel";
    /* access modifiers changed from: private */
    public IdpResponse mResponse;

    public SmartLockHandler(Application application) {
        super(application);
    }

    public void setResponse(IdpResponse idpResponse) {
        this.mResponse = idpResponse;
    }

    public void onActivityResult(int i, int i2) {
        if (i != 100) {
            return;
        }
        if (i2 == -1) {
            setResult(Resource.forSuccess(this.mResponse));
            return;
        }
        Log.e(TAG, "SAVE: Canceled by user.");
        setResult(Resource.forFailure(new FirebaseUiException(0, "Save canceled by user.")));
    }

    public void saveCredentials(FirebaseUser firebaseUser, String str, String str2) {
        saveCredentials(CredentialUtils.buildCredential(firebaseUser, str, str2));
    }

    public void saveCredentials(Credential credential) {
        if (!((FlowParameters) getArguments()).enableCredentials) {
            setResult(Resource.forSuccess(this.mResponse));
            return;
        }
        setResult(Resource.forLoading());
        if (credential == null) {
            setResult(Resource.forFailure(new FirebaseUiException(0, "Failed to build credential.")));
            return;
        }
        deleteUnusedCredentials();
        getCredentialsClient().save(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    SmartLockHandler smartLockHandler = SmartLockHandler.this;
                    smartLockHandler.setResult(Resource.forSuccess(smartLockHandler.mResponse));
                } else if (task.getException() instanceof ResolvableApiException) {
                    SmartLockHandler.this.setResult(Resource.forFailure(new PendingIntentRequiredException(((ResolvableApiException) task.getException()).getResolution(), 100)));
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Non-resolvable exception: ");
                    sb.append(task.getException());
                    Log.w(SmartLockHandler.TAG, sb.toString());
                    SmartLockHandler.this.setResult(Resource.forFailure(new FirebaseUiException(0, "Error when saving credential.", task.getException())));
                }
            }
        });
    }

    private void deleteUnusedCredentials() {
        String str = "google.com";
        if (this.mResponse.getProviderType().equals(str)) {
            GoogleApiUtils.getCredentialsClient(getApplication()).delete(CredentialUtils.buildCredentialOrThrow(getCurrentUser(), "pass", ProviderUtils.providerIdToAccountType(str)));
        }
    }
}

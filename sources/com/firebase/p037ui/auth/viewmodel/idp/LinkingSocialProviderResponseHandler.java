package com.firebase.p037ui.auth.viewmodel.idp;

import android.app.Application;
import com.firebase.p037ui.auth.AuthUI;
import com.firebase.p037ui.auth.FirebaseUiException;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.data.model.Resource;
import com.firebase.p037ui.auth.util.data.AuthOperationManager;
import com.firebase.p037ui.auth.util.data.ProviderUtils;
import com.firebase.p037ui.auth.viewmodel.SignInViewModelBase;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;

/* renamed from: com.firebase.ui.auth.viewmodel.idp.LinkingSocialProviderResponseHandler */
public class LinkingSocialProviderResponseHandler extends SignInViewModelBase {
    private String mEmail;
    /* access modifiers changed from: private */
    public AuthCredential mRequestedSignInCredential;

    public LinkingSocialProviderResponseHandler(Application application) {
        super(application);
    }

    public void setRequestedSignInCredentialForEmail(AuthCredential authCredential, String str) {
        this.mRequestedSignInCredential = authCredential;
        this.mEmail = str;
    }

    public void startSignIn(final IdpResponse idpResponse) {
        if (!idpResponse.isSuccessful()) {
            setResult(Resource.forFailure(idpResponse.getError()));
        } else if (AuthUI.SOCIAL_PROVIDERS.contains(idpResponse.getProviderType())) {
            String str = this.mEmail;
            if (str == null || str.equals(idpResponse.getEmail())) {
                setResult(Resource.forLoading());
                AuthOperationManager instance = AuthOperationManager.getInstance();
                final AuthCredential authCredential = ProviderUtils.getAuthCredential(idpResponse);
                if (instance.canUpgradeAnonymous(getAuth(), (FlowParameters) getArguments())) {
                    AuthCredential authCredential2 = this.mRequestedSignInCredential;
                    if (authCredential2 == null) {
                        handleMergeFailure(authCredential);
                    } else {
                        instance.safeLink(authCredential, authCredential2, (FlowParameters) getArguments()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            public void onSuccess(AuthResult authResult) {
                                LinkingSocialProviderResponseHandler.this.handleMergeFailure(authCredential);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            public void onFailure(Exception exc) {
                                LinkingSocialProviderResponseHandler.this.setResult(Resource.forFailure(exc));
                            }
                        });
                    }
                } else {
                    getAuth().signInWithCredential(authCredential).continueWithTask(new Continuation<AuthResult, Task<AuthResult>>() {
                        public Task<AuthResult> then(Task<AuthResult> task) {
                            final AuthResult authResult = (AuthResult) task.getResult();
                            if (LinkingSocialProviderResponseHandler.this.mRequestedSignInCredential == null) {
                                return Tasks.forResult(authResult);
                            }
                            return authResult.getUser().linkWithCredential(LinkingSocialProviderResponseHandler.this.mRequestedSignInCredential).continueWith(new Continuation<AuthResult, AuthResult>() {
                                public AuthResult then(Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        return (AuthResult) task.getResult();
                                    }
                                    return authResult;
                                }
                            });
                        }
                    }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                LinkingSocialProviderResponseHandler.this.handleSuccess(idpResponse, (AuthResult) task.getResult());
                            } else {
                                LinkingSocialProviderResponseHandler.this.setResult(Resource.forFailure(task.getException()));
                            }
                        }
                    });
                }
                return;
            }
            setResult(Resource.forFailure(new FirebaseUiException(6)));
        } else {
            throw new IllegalStateException("This handler cannot be used to link email or phone providers");
        }
    }
}

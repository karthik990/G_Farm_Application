package com.firebase.p037ui.auth.viewmodel.email;

import android.app.Application;
import com.firebase.p037ui.auth.AuthUI;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.IdpResponse.Builder;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.data.model.Resource;
import com.firebase.p037ui.auth.data.model.User;
import com.firebase.p037ui.auth.data.remote.ProfileMerger;
import com.firebase.p037ui.auth.util.data.AuthOperationManager;
import com.firebase.p037ui.auth.util.data.TaskFailureLogger;
import com.firebase.p037ui.auth.viewmodel.SignInViewModelBase;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;

/* renamed from: com.firebase.ui.auth.viewmodel.email.WelcomeBackPasswordHandler */
public class WelcomeBackPasswordHandler extends SignInViewModelBase {
    private static final String TAG = "WBPasswordHandler";
    private String mPendingPassword;

    public WelcomeBackPasswordHandler(Application application) {
        super(application);
    }

    public void startSignIn(String str, String str2, IdpResponse idpResponse, final AuthCredential authCredential) {
        final IdpResponse idpResponse2;
        setResult(Resource.forLoading());
        this.mPendingPassword = str2;
        if (authCredential == null) {
            idpResponse2 = new Builder(new User.Builder("password", str).build()).build();
        } else {
            idpResponse2 = new Builder(idpResponse.getUser()).setToken(idpResponse.getIdpToken()).setSecret(idpResponse.getIdpSecret()).build();
        }
        AuthOperationManager instance = AuthOperationManager.getInstance();
        if (instance.canUpgradeAnonymous(getAuth(), (FlowParameters) getArguments())) {
            final AuthCredential credential = EmailAuthProvider.getCredential(str, str2);
            if (AuthUI.SOCIAL_PROVIDERS.contains(idpResponse.getProviderType())) {
                instance.safeLink(credential, authCredential, (FlowParameters) getArguments()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    public void onSuccess(AuthResult authResult) {
                        WelcomeBackPasswordHandler.this.handleMergeFailure(credential);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    public void onFailure(Exception exc) {
                        WelcomeBackPasswordHandler.this.setResult(Resource.forFailure(exc));
                    }
                });
            } else {
                instance.validateCredential(credential, (FlowParameters) getArguments()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            WelcomeBackPasswordHandler.this.handleMergeFailure(credential);
                        } else {
                            WelcomeBackPasswordHandler.this.setResult(Resource.forFailure(task.getException()));
                        }
                    }
                });
            }
        } else {
            getAuth().signInWithEmailAndPassword(str, str2).continueWithTask(new Continuation<AuthResult, Task<AuthResult>>() {
                public Task<AuthResult> then(Task<AuthResult> task) throws Exception {
                    AuthResult authResult = (AuthResult) task.getResult(Exception.class);
                    if (authCredential == null) {
                        return Tasks.forResult(authResult);
                    }
                    return authResult.getUser().linkWithCredential(authCredential).continueWithTask(new ProfileMerger(idpResponse2)).addOnFailureListener(new TaskFailureLogger(WelcomeBackPasswordHandler.TAG, "linkWithCredential+merge failed."));
                }
            }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                public void onSuccess(AuthResult authResult) {
                    WelcomeBackPasswordHandler.this.handleSuccess(idpResponse2, authResult);
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception exc) {
                    WelcomeBackPasswordHandler.this.setResult(Resource.forFailure(exc));
                }
            }).addOnFailureListener(new TaskFailureLogger(TAG, "signInWithEmailAndPassword failed."));
        }
    }

    public String getPendingPassword() {
        return this.mPendingPassword;
    }
}

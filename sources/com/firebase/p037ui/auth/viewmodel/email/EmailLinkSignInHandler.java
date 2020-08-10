package com.firebase.p037ui.auth.viewmodel.email;

import android.app.Application;
import android.text.TextUtils;
import com.firebase.p037ui.auth.FirebaseUiException;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.IdpResponse.Builder;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.data.model.Resource;
import com.firebase.p037ui.auth.data.model.User;
import com.firebase.p037ui.auth.data.remote.ProfileMerger;
import com.firebase.p037ui.auth.util.data.AuthOperationManager;
import com.firebase.p037ui.auth.util.data.EmailLinkParser;
import com.firebase.p037ui.auth.util.data.EmailLinkPersistenceManager;
import com.firebase.p037ui.auth.util.data.EmailLinkPersistenceManager.SessionRecord;
import com.firebase.p037ui.auth.util.data.ProviderUtils;
import com.firebase.p037ui.auth.util.data.TaskFailureLogger;
import com.firebase.p037ui.auth.viewmodel.SignInViewModelBase;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeResult;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

/* renamed from: com.firebase.ui.auth.viewmodel.email.EmailLinkSignInHandler */
public class EmailLinkSignInHandler extends SignInViewModelBase {
    private static final String TAG = "EmailLinkSignInHandler";

    public EmailLinkSignInHandler(Application application) {
        super(application);
    }

    public void startSignIn() {
        setResult(Resource.forLoading());
        String str = ((FlowParameters) getArguments()).emailLink;
        if (!getAuth().isSignInWithEmailLink(str)) {
            setResult(Resource.forFailure(new FirebaseUiException(7)));
            return;
        }
        SessionRecord retrieveSessionRecord = EmailLinkPersistenceManager.getInstance().retrieveSessionRecord(getApplication());
        EmailLinkParser emailLinkParser = new EmailLinkParser(str);
        String sessionId = emailLinkParser.getSessionId();
        String anonymousUserId = emailLinkParser.getAnonymousUserId();
        String oobCode = emailLinkParser.getOobCode();
        String providerId = emailLinkParser.getProviderId();
        boolean forceSameDeviceBit = emailLinkParser.getForceSameDeviceBit();
        if (isDifferentDeviceFlow(retrieveSessionRecord, sessionId)) {
            if (TextUtils.isEmpty(sessionId)) {
                setResult(Resource.forFailure(new FirebaseUiException(7)));
            } else if (forceSameDeviceBit || !TextUtils.isEmpty(anonymousUserId)) {
                setResult(Resource.forFailure(new FirebaseUiException(8)));
            } else {
                determineDifferentDeviceErrorFlowAndFinish(oobCode, providerId);
            }
        } else if (anonymousUserId == null || (getAuth().getCurrentUser() != null && (!getAuth().getCurrentUser().isAnonymous() || anonymousUserId.equals(getAuth().getCurrentUser().getUid())))) {
            finishSignIn(retrieveSessionRecord);
        } else {
            setResult(Resource.forFailure(new FirebaseUiException(11)));
        }
    }

    public void finishSignIn(String str) {
        setResult(Resource.forLoading());
        finishSignIn(str, null);
    }

    private void finishSignIn(SessionRecord sessionRecord) {
        finishSignIn(sessionRecord.getEmail(), sessionRecord.getIdpResponseForLinking());
    }

    private void finishSignIn(String str, IdpResponse idpResponse) {
        if (TextUtils.isEmpty(str)) {
            setResult(Resource.forFailure(new FirebaseUiException(6)));
            return;
        }
        AuthOperationManager instance = AuthOperationManager.getInstance();
        EmailLinkPersistenceManager instance2 = EmailLinkPersistenceManager.getInstance();
        String str2 = ((FlowParameters) getArguments()).emailLink;
        if (idpResponse == null) {
            handleNormalFlow(instance, instance2, str, str2);
        } else {
            handleLinkingFlow(instance, instance2, idpResponse, str2);
        }
    }

    private void determineDifferentDeviceErrorFlowAndFinish(String str, final String str2) {
        getAuth().checkActionCode(str).addOnCompleteListener(new OnCompleteListener<ActionCodeResult>() {
            public void onComplete(Task<ActionCodeResult> task) {
                if (!task.isSuccessful()) {
                    EmailLinkSignInHandler.this.setResult(Resource.forFailure(new FirebaseUiException(7)));
                } else if (!TextUtils.isEmpty(str2)) {
                    EmailLinkSignInHandler.this.setResult(Resource.forFailure(new FirebaseUiException(10)));
                } else {
                    EmailLinkSignInHandler.this.setResult(Resource.forFailure(new FirebaseUiException(9)));
                }
            }
        });
    }

    private void handleLinkingFlow(AuthOperationManager authOperationManager, final EmailLinkPersistenceManager emailLinkPersistenceManager, final IdpResponse idpResponse, String str) {
        final AuthCredential authCredential = ProviderUtils.getAuthCredential(idpResponse);
        AuthCredential credentialWithLink = EmailAuthProvider.getCredentialWithLink(idpResponse.getEmail(), str);
        if (authOperationManager.canUpgradeAnonymous(getAuth(), (FlowParameters) getArguments())) {
            authOperationManager.safeLink(credentialWithLink, authCredential, (FlowParameters) getArguments()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                public void onComplete(Task<AuthResult> task) {
                    emailLinkPersistenceManager.clearAllData(EmailLinkSignInHandler.this.getApplication());
                    if (task.isSuccessful()) {
                        EmailLinkSignInHandler.this.handleMergeFailure(authCredential);
                    } else {
                        EmailLinkSignInHandler.this.setResult(Resource.forFailure(task.getException()));
                    }
                }
            });
        } else {
            getAuth().signInWithCredential(credentialWithLink).continueWithTask(new Continuation<AuthResult, Task<AuthResult>>() {
                public Task<AuthResult> then(Task<AuthResult> task) {
                    emailLinkPersistenceManager.clearAllData(EmailLinkSignInHandler.this.getApplication());
                    if (!task.isSuccessful()) {
                        return task;
                    }
                    return ((AuthResult) task.getResult()).getUser().linkWithCredential(authCredential).continueWithTask(new ProfileMerger(idpResponse)).addOnFailureListener(new TaskFailureLogger(EmailLinkSignInHandler.TAG, "linkWithCredential+merge failed."));
                }
            }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                public void onSuccess(AuthResult authResult) {
                    FirebaseUser user = authResult.getUser();
                    EmailLinkSignInHandler.this.handleSuccess(new Builder(new User.Builder("emailLink", user.getEmail()).setName(user.getDisplayName()).setPhotoUri(user.getPhotoUrl()).build()).build(), authResult);
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception exc) {
                    EmailLinkSignInHandler.this.setResult(Resource.forFailure(exc));
                }
            });
        }
    }

    private void handleNormalFlow(AuthOperationManager authOperationManager, final EmailLinkPersistenceManager emailLinkPersistenceManager, String str, String str2) {
        AuthCredential credentialWithLink = EmailAuthProvider.getCredentialWithLink(str, str2);
        final AuthCredential credentialWithLink2 = EmailAuthProvider.getCredentialWithLink(str, str2);
        authOperationManager.signInAndLinkWithCredential(getAuth(), (FlowParameters) getArguments(), credentialWithLink).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            public void onSuccess(AuthResult authResult) {
                emailLinkPersistenceManager.clearAllData(EmailLinkSignInHandler.this.getApplication());
                FirebaseUser user = authResult.getUser();
                EmailLinkSignInHandler.this.handleSuccess(new Builder(new User.Builder("emailLink", user.getEmail()).setName(user.getDisplayName()).setPhotoUri(user.getPhotoUrl()).build()).build(), authResult);
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception exc) {
                emailLinkPersistenceManager.clearAllData(EmailLinkSignInHandler.this.getApplication());
                if (exc instanceof FirebaseAuthUserCollisionException) {
                    EmailLinkSignInHandler.this.handleMergeFailure(credentialWithLink2);
                } else {
                    EmailLinkSignInHandler.this.setResult(Resource.forFailure(exc));
                }
            }
        });
    }

    private boolean isDifferentDeviceFlow(SessionRecord sessionRecord, String str) {
        return sessionRecord == null || TextUtils.isEmpty(sessionRecord.getSessionId()) || TextUtils.isEmpty(str) || !str.equals(sessionRecord.getSessionId());
    }
}

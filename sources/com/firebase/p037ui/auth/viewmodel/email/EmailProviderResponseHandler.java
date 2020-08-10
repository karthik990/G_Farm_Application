package com.firebase.p037ui.auth.viewmodel.email;

import android.app.Application;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.IdpResponse.Builder;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.data.model.IntentRequiredException;
import com.firebase.p037ui.auth.data.model.Resource;
import com.firebase.p037ui.auth.data.model.User;
import com.firebase.p037ui.auth.data.remote.ProfileMerger;
import com.firebase.p037ui.auth.p038ui.email.WelcomeBackEmailLinkPrompt;
import com.firebase.p037ui.auth.p038ui.email.WelcomeBackPasswordPrompt;
import com.firebase.p037ui.auth.p038ui.idp.WelcomeBackIdpPrompt;
import com.firebase.p037ui.auth.util.data.AuthOperationManager;
import com.firebase.p037ui.auth.util.data.ProviderUtils;
import com.firebase.p037ui.auth.util.data.TaskFailureLogger;
import com.firebase.p037ui.auth.viewmodel.SignInViewModelBase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

/* renamed from: com.firebase.ui.auth.viewmodel.email.EmailProviderResponseHandler */
public class EmailProviderResponseHandler extends SignInViewModelBase {
    private static final String TAG = "EmailProviderResponseHa";

    /* renamed from: com.firebase.ui.auth.viewmodel.email.EmailProviderResponseHandler$StartWelcomeBackFlow */
    private class StartWelcomeBackFlow implements OnSuccessListener<String> {
        private final String mEmail;

        public StartWelcomeBackFlow(String str) {
            this.mEmail = str;
        }

        public void onSuccess(String str) {
            if (str != null) {
                String str2 = "password";
                if (str2.equalsIgnoreCase(str)) {
                    EmailProviderResponseHandler emailProviderResponseHandler = EmailProviderResponseHandler.this;
                    emailProviderResponseHandler.setResult(Resource.forFailure(new IntentRequiredException(WelcomeBackPasswordPrompt.createIntent(emailProviderResponseHandler.getApplication(), (FlowParameters) EmailProviderResponseHandler.this.getArguments(), new Builder(new User.Builder(str2, this.mEmail).build()).build()), 104)));
                    return;
                }
                String str3 = "emailLink";
                if (str3.equalsIgnoreCase(str)) {
                    EmailProviderResponseHandler emailProviderResponseHandler2 = EmailProviderResponseHandler.this;
                    emailProviderResponseHandler2.setResult(Resource.forFailure(new IntentRequiredException(WelcomeBackEmailLinkPrompt.createIntent(emailProviderResponseHandler2.getApplication(), (FlowParameters) EmailProviderResponseHandler.this.getArguments(), new Builder(new User.Builder(str3, this.mEmail).build()).build()), 112)));
                    return;
                }
                EmailProviderResponseHandler emailProviderResponseHandler3 = EmailProviderResponseHandler.this;
                emailProviderResponseHandler3.setResult(Resource.forFailure(new IntentRequiredException(WelcomeBackIdpPrompt.createIntent(emailProviderResponseHandler3.getApplication(), (FlowParameters) EmailProviderResponseHandler.this.getArguments(), new User.Builder(str, this.mEmail).build()), 103)));
                return;
            }
            throw new IllegalStateException("User has no providers even though we got a collision.");
        }
    }

    public EmailProviderResponseHandler(Application application) {
        super(application);
    }

    public void startSignIn(final IdpResponse idpResponse, final String str) {
        if (!idpResponse.isSuccessful()) {
            setResult(Resource.forFailure(idpResponse.getError()));
        } else if (idpResponse.getProviderType().equals("password")) {
            setResult(Resource.forLoading());
            final AuthOperationManager instance = AuthOperationManager.getInstance();
            final String email = idpResponse.getEmail();
            instance.createOrLinkUserWithEmailAndPassword(getAuth(), (FlowParameters) getArguments(), email, str).continueWithTask(new ProfileMerger(idpResponse)).addOnFailureListener(new TaskFailureLogger(TAG, "Error creating user")).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                public void onSuccess(AuthResult authResult) {
                    EmailProviderResponseHandler.this.handleSuccess(idpResponse, authResult);
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception exc) {
                    if (!(exc instanceof FirebaseAuthUserCollisionException)) {
                        EmailProviderResponseHandler.this.setResult(Resource.forFailure(exc));
                    } else if (instance.canUpgradeAnonymous(EmailProviderResponseHandler.this.getAuth(), (FlowParameters) EmailProviderResponseHandler.this.getArguments())) {
                        EmailProviderResponseHandler.this.handleMergeFailure(EmailAuthProvider.getCredential(email, str));
                    } else {
                        ProviderUtils.fetchTopProvider(EmailProviderResponseHandler.this.getAuth(), (FlowParameters) EmailProviderResponseHandler.this.getArguments(), email).addOnSuccessListener(new StartWelcomeBackFlow(email)).addOnFailureListener(new OnFailureListener() {
                            public void onFailure(Exception exc) {
                                EmailProviderResponseHandler.this.setResult(Resource.forFailure(exc));
                            }
                        });
                    }
                }
            });
        } else {
            throw new IllegalStateException("This handler can only be used with the email provider");
        }
    }
}

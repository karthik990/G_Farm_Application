package com.firebase.p037ui.auth.viewmodel.idp;

import android.app.Application;
import android.content.Intent;
import com.firebase.p037ui.auth.AuthUI;
import com.firebase.p037ui.auth.ErrorCodes;
import com.firebase.p037ui.auth.FirebaseUiException;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.data.model.IntentRequiredException;
import com.firebase.p037ui.auth.data.model.Resource;
import com.firebase.p037ui.auth.data.model.User.Builder;
import com.firebase.p037ui.auth.data.remote.ProfileMerger;
import com.firebase.p037ui.auth.p038ui.email.WelcomeBackEmailLinkPrompt;
import com.firebase.p037ui.auth.p038ui.email.WelcomeBackPasswordPrompt;
import com.firebase.p037ui.auth.p038ui.idp.WelcomeBackIdpPrompt;
import com.firebase.p037ui.auth.util.data.AuthOperationManager;
import com.firebase.p037ui.auth.util.data.ProviderUtils;
import com.firebase.p037ui.auth.viewmodel.SignInViewModelBase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import java.util.List;

/* renamed from: com.firebase.ui.auth.viewmodel.idp.SocialProviderResponseHandler */
public class SocialProviderResponseHandler extends SignInViewModelBase {
    public SocialProviderResponseHandler(Application application) {
        super(application);
    }

    public void startSignIn(final IdpResponse idpResponse) {
        if (!idpResponse.isSuccessful()) {
            setResult(Resource.forFailure(idpResponse.getError()));
        } else if (AuthUI.SOCIAL_PROVIDERS.contains(idpResponse.getProviderType())) {
            setResult(Resource.forLoading());
            final AuthCredential authCredential = ProviderUtils.getAuthCredential(idpResponse);
            AuthOperationManager.getInstance().signInAndLinkWithCredential(getAuth(), (FlowParameters) getArguments(), authCredential).continueWithTask(new ProfileMerger(idpResponse)).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                public void onSuccess(AuthResult authResult) {
                    SocialProviderResponseHandler.this.handleSuccess(idpResponse, authResult);
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception exc) {
                    if (exc instanceof FirebaseAuthUserCollisionException) {
                        String email = idpResponse.getEmail();
                        if (email == null) {
                            SocialProviderResponseHandler.this.setResult(Resource.forFailure(exc));
                            return;
                        }
                        ProviderUtils.fetchSortedProviders(SocialProviderResponseHandler.this.getAuth(), (FlowParameters) SocialProviderResponseHandler.this.getArguments(), email).addOnSuccessListener(new OnSuccessListener<List<String>>() {
                            public void onSuccess(List<String> list) {
                                if (list.contains(idpResponse.getProviderType())) {
                                    SocialProviderResponseHandler.this.handleMergeFailure(authCredential);
                                } else if (list.isEmpty()) {
                                    SocialProviderResponseHandler.this.setResult(Resource.forFailure(new FirebaseUiException(3, "No supported providers.")));
                                } else {
                                    SocialProviderResponseHandler.this.startWelcomeBackFlowForLinking((String) list.get(0), idpResponse);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            public void onFailure(Exception exc) {
                                SocialProviderResponseHandler.this.setResult(Resource.forFailure(exc));
                            }
                        });
                    } else if (exc instanceof FirebaseAuthInvalidUserException) {
                        SocialProviderResponseHandler.this.setResult(Resource.forFailure(new FirebaseUiException(12, ErrorCodes.toFriendlyMessage(12))));
                    }
                }
            });
        } else {
            throw new IllegalStateException("This handler cannot be used with email or phone providers");
        }
    }

    public void startWelcomeBackFlowForLinking(String str, IdpResponse idpResponse) {
        if (str == null) {
            throw new IllegalStateException("No provider even though we received a FirebaseAuthUserCollisionException");
        } else if (str.equals("password")) {
            setResult(Resource.forFailure(new IntentRequiredException(WelcomeBackPasswordPrompt.createIntent(getApplication(), (FlowParameters) getArguments(), idpResponse), 108)));
        } else if (str.equals("emailLink")) {
            setResult(Resource.forFailure(new IntentRequiredException(WelcomeBackEmailLinkPrompt.createIntent(getApplication(), (FlowParameters) getArguments(), idpResponse), 112)));
        } else {
            setResult(Resource.forFailure(new IntentRequiredException(WelcomeBackIdpPrompt.createIntent(getApplication(), (FlowParameters) getArguments(), new Builder(str, idpResponse.getEmail()).build(), idpResponse), 108)));
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        FirebaseUiException firebaseUiException;
        if (i == 108) {
            IdpResponse fromResultIntent = IdpResponse.fromResultIntent(intent);
            if (i2 == -1) {
                setResult(Resource.forSuccess(fromResultIntent));
                return;
            }
            if (fromResultIntent == null) {
                firebaseUiException = new FirebaseUiException(0, "Link canceled by user.");
            } else {
                firebaseUiException = fromResultIntent.getError();
            }
            setResult(Resource.forFailure(firebaseUiException));
        }
    }
}

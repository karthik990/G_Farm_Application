package com.firebase.p037ui.auth.data.remote;

import android.app.Application;
import android.content.Intent;
import com.firebase.p037ui.auth.AuthUI;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.IdpResponse.Builder;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.data.model.Resource;
import com.firebase.p037ui.auth.data.model.User;
import com.firebase.p037ui.auth.p038ui.HelperActivityBase;
import com.firebase.p037ui.auth.viewmodel.ProviderSignInBase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/* renamed from: com.firebase.ui.auth.data.remote.AnonymousSignInHandler */
public class AnonymousSignInHandler extends ProviderSignInBase<FlowParameters> {
    public FirebaseAuth mAuth;

    public void onActivityResult(int i, int i2, Intent intent) {
    }

    public AnonymousSignInHandler(Application application) {
        super(application);
    }

    /* access modifiers changed from: protected */
    public void onCreate() {
        this.mAuth = getAuth();
    }

    public void startSignIn(HelperActivityBase helperActivityBase) {
        setResult(Resource.forLoading());
        this.mAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            public void onSuccess(AuthResult authResult) {
                AnonymousSignInHandler anonymousSignInHandler = AnonymousSignInHandler.this;
                anonymousSignInHandler.setResult(Resource.forSuccess(anonymousSignInHandler.initResponse(authResult.getAdditionalUserInfo().isNewUser())));
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception exc) {
                AnonymousSignInHandler.this.setResult(Resource.forFailure(exc));
            }
        });
    }

    /* access modifiers changed from: private */
    public IdpResponse initResponse(boolean z) {
        return new Builder(new User.Builder(AuthUI.ANONYMOUS_PROVIDER, null).build()).setNewUser(z).build();
    }

    private FirebaseAuth getAuth() {
        return FirebaseAuth.getInstance(FirebaseApp.getInstance(((FlowParameters) getArguments()).appName));
    }
}

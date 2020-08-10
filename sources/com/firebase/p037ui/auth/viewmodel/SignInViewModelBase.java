package com.firebase.p037ui.auth.viewmodel;

import android.app.Application;
import com.firebase.p037ui.auth.FirebaseAuthAnonymousUpgradeException;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.IdpResponse.Builder;
import com.firebase.p037ui.auth.data.model.Resource;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;

/* renamed from: com.firebase.ui.auth.viewmodel.SignInViewModelBase */
public abstract class SignInViewModelBase extends AuthViewModelBase<IdpResponse> {
    protected SignInViewModelBase(Application application) {
        super(application);
    }

    /* access modifiers changed from: protected */
    public void setResult(Resource<IdpResponse> resource) {
        super.setResult(resource);
    }

    /* access modifiers changed from: protected */
    public void handleSuccess(IdpResponse idpResponse, AuthResult authResult) {
        setResult(Resource.forSuccess(idpResponse.withResult(authResult)));
    }

    /* access modifiers changed from: protected */
    public void handleMergeFailure(AuthCredential authCredential) {
        handleMergeFailure(new Builder(authCredential).build());
    }

    /* access modifiers changed from: protected */
    public void handleMergeFailure(IdpResponse idpResponse) {
        setResult(Resource.forFailure(new FirebaseAuthAnonymousUpgradeException(5, idpResponse)));
    }
}

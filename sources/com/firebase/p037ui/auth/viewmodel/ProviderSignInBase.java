package com.firebase.p037ui.auth.viewmodel;

import android.app.Application;
import android.content.Intent;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.data.model.Resource;
import com.firebase.p037ui.auth.p038ui.HelperActivityBase;

/* renamed from: com.firebase.ui.auth.viewmodel.ProviderSignInBase */
public abstract class ProviderSignInBase<T> extends OperableViewModel<T, Resource<IdpResponse>> {
    public abstract void onActivityResult(int i, int i2, Intent intent);

    public abstract void startSignIn(HelperActivityBase helperActivityBase);

    protected ProviderSignInBase(Application application) {
        super(application);
    }
}

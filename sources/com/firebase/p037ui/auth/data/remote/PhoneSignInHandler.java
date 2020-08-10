package com.firebase.p037ui.auth.data.remote;

import android.app.Application;
import android.content.Intent;
import com.firebase.p037ui.auth.AuthUI.IdpConfig;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.data.model.Resource;
import com.firebase.p037ui.auth.data.model.UserCancellationException;
import com.firebase.p037ui.auth.p038ui.HelperActivityBase;
import com.firebase.p037ui.auth.p038ui.phone.PhoneActivity;
import com.firebase.p037ui.auth.viewmodel.ProviderSignInBase;

/* renamed from: com.firebase.ui.auth.data.remote.PhoneSignInHandler */
public class PhoneSignInHandler extends ProviderSignInBase<IdpConfig> {
    public PhoneSignInHandler(Application application) {
        super(application);
    }

    public void startSignIn(HelperActivityBase helperActivityBase) {
        helperActivityBase.startActivityForResult(PhoneActivity.createIntent(helperActivityBase, helperActivityBase.getFlowParams(), ((IdpConfig) getArguments()).getParams()), 107);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 107) {
            IdpResponse fromResultIntent = IdpResponse.fromResultIntent(intent);
            if (fromResultIntent == null) {
                setResult(Resource.forFailure(new UserCancellationException()));
            } else {
                setResult(Resource.forSuccess(fromResultIntent));
            }
        }
    }
}

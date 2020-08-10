package com.firebase.p037ui.auth.data.remote;

import android.app.Application;
import android.content.Intent;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.data.model.Resource;
import com.firebase.p037ui.auth.data.model.UserCancellationException;
import com.firebase.p037ui.auth.p038ui.HelperActivityBase;
import com.firebase.p037ui.auth.p038ui.email.EmailActivity;
import com.firebase.p037ui.auth.viewmodel.ProviderSignInBase;

/* renamed from: com.firebase.ui.auth.data.remote.EmailSignInHandler */
public class EmailSignInHandler extends ProviderSignInBase<Void> {
    public EmailSignInHandler(Application application) {
        super(application);
    }

    public void startSignIn(HelperActivityBase helperActivityBase) {
        helperActivityBase.startActivityForResult(EmailActivity.createIntent(helperActivityBase, helperActivityBase.getFlowParams()), 106);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 != 5 && i == 106) {
            IdpResponse fromResultIntent = IdpResponse.fromResultIntent(intent);
            if (fromResultIntent == null) {
                setResult(Resource.forFailure(new UserCancellationException()));
            } else {
                setResult(Resource.forSuccess(fromResultIntent));
            }
        }
    }
}

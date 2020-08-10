package com.firebase.p037ui.auth.viewmodel.email;

import android.app.Application;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.data.model.Resource;
import com.firebase.p037ui.auth.util.data.AuthOperationManager;
import com.firebase.p037ui.auth.util.data.ContinueUrlBuilder;
import com.firebase.p037ui.auth.util.data.EmailLinkPersistenceManager;
import com.firebase.p037ui.auth.util.data.SessionUtils;
import com.firebase.p037ui.auth.viewmodel.AuthViewModelBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;

/* renamed from: com.firebase.ui.auth.viewmodel.email.EmailLinkSendEmailHandler */
public class EmailLinkSendEmailHandler extends AuthViewModelBase<String> {
    private static final int SESSION_ID_LENGTH = 10;

    public EmailLinkSendEmailHandler(Application application) {
        super(application);
    }

    public void sendSignInLinkToEmail(final String str, ActionCodeSettings actionCodeSettings, IdpResponse idpResponse, boolean z) {
        if (getAuth() != null) {
            setResult(Resource.forLoading());
            final String uid = AuthOperationManager.getInstance().canUpgradeAnonymous(getAuth(), (FlowParameters) getArguments()) ? getAuth().getCurrentUser().getUid() : null;
            final String generateRandomAlphaNumericString = SessionUtils.generateRandomAlphaNumericString(10);
            getAuth().sendSignInLinkToEmail(str, addSessionInfoToActionCodeSettings(actionCodeSettings, generateRandomAlphaNumericString, uid, idpResponse, z)).addOnCompleteListener(new OnCompleteListener<Void>() {
                public void onComplete(Task<Void> task) {
                    if (task.isSuccessful()) {
                        EmailLinkPersistenceManager.getInstance().saveEmail(EmailLinkSendEmailHandler.this.getApplication(), str, generateRandomAlphaNumericString, uid);
                        EmailLinkSendEmailHandler.this.setResult(Resource.forSuccess(str));
                        return;
                    }
                    EmailLinkSendEmailHandler.this.setResult(Resource.forFailure(task.getException()));
                }
            });
        }
    }

    private ActionCodeSettings addSessionInfoToActionCodeSettings(ActionCodeSettings actionCodeSettings, String str, String str2, IdpResponse idpResponse, boolean z) {
        ContinueUrlBuilder continueUrlBuilder = new ContinueUrlBuilder(actionCodeSettings.getUrl());
        continueUrlBuilder.appendSessionId(str);
        continueUrlBuilder.appendAnonymousUserId(str2);
        continueUrlBuilder.appendForceSameDeviceBit(z);
        if (idpResponse != null) {
            continueUrlBuilder.appendProviderId(idpResponse.getProviderType());
        }
        return ActionCodeSettings.newBuilder().setUrl(continueUrlBuilder.build()).setHandleCodeInApp(true).setAndroidPackageName(actionCodeSettings.getAndroidPackageName(), actionCodeSettings.getAndroidInstallApp(), actionCodeSettings.getAndroidMinimumVersion()).setIOSBundleId(actionCodeSettings.getIOSBundle()).build();
    }
}

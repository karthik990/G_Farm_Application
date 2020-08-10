package com.firebase.p037ui.auth.p038ui.credentials;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.data.model.Resource;
import com.firebase.p037ui.auth.p038ui.InvisibleActivityBase;
import com.firebase.p037ui.auth.util.ExtraConstants;
import com.firebase.p037ui.auth.viewmodel.ResourceObserver;
import com.firebase.p037ui.auth.viewmodel.smartlock.SmartLockHandler;
import com.google.android.gms.auth.api.credentials.Credential;

/* renamed from: com.firebase.ui.auth.ui.credentials.CredentialSaveActivity */
public class CredentialSaveActivity extends InvisibleActivityBase {
    private static final String TAG = "CredentialSaveActivity";
    private SmartLockHandler mHandler;

    public static Intent createIntent(Context context, FlowParameters flowParameters, Credential credential, IdpResponse idpResponse) {
        return createBaseIntent(context, CredentialSaveActivity.class, flowParameters).putExtra(ExtraConstants.CREDENTIAL, credential).putExtra(ExtraConstants.IDP_RESPONSE, idpResponse);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        final IdpResponse idpResponse = (IdpResponse) getIntent().getParcelableExtra(ExtraConstants.IDP_RESPONSE);
        Credential credential = (Credential) getIntent().getParcelableExtra(ExtraConstants.CREDENTIAL);
        this.mHandler = (SmartLockHandler) ViewModelProviders.m78of((FragmentActivity) this).get(SmartLockHandler.class);
        this.mHandler.init(getFlowParams());
        this.mHandler.setResponse(idpResponse);
        this.mHandler.getOperation().observe(this, new ResourceObserver<IdpResponse>(this) {
            /* access modifiers changed from: protected */
            public void onSuccess(IdpResponse idpResponse) {
                CredentialSaveActivity.this.finish(-1, idpResponse.toIntent());
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception exc) {
                CredentialSaveActivity.this.finish(-1, idpResponse.toIntent());
            }
        });
        Resource resource = (Resource) this.mHandler.getOperation().getValue();
        String str = TAG;
        if (resource == null) {
            Log.d(str, "Launching save operation.");
            this.mHandler.saveCredentials(credential);
            return;
        }
        Log.d(str, "Save operation in progress, doing nothing.");
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        this.mHandler.onActivityResult(i, i2);
    }
}

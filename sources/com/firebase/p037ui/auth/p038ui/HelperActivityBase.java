package com.firebase.p037ui.auth.p038ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Parcelable;
import androidx.appcompat.app.AppCompatActivity;
import com.firebase.p037ui.auth.AuthUI;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.p038ui.credentials.CredentialSaveActivity;
import com.firebase.p037ui.auth.util.CredentialUtils;
import com.firebase.p037ui.auth.util.ExtraConstants;
import com.firebase.p037ui.auth.util.Preconditions;
import com.firebase.p037ui.auth.util.data.ProviderUtils;
import com.google.firebase.auth.FirebaseUser;

/* renamed from: com.firebase.ui.auth.ui.HelperActivityBase */
public abstract class HelperActivityBase extends AppCompatActivity implements ProgressView {
    private FlowParameters mParams;

    protected static Intent createBaseIntent(Context context, Class<? extends Activity> cls, FlowParameters flowParameters) {
        Intent putExtra = new Intent((Context) Preconditions.checkNotNull(context, "context cannot be null", new Object[0]), (Class) Preconditions.checkNotNull(cls, "target activity cannot be null", new Object[0])).putExtra(ExtraConstants.FLOW_PARAMS, (Parcelable) Preconditions.checkNotNull(flowParameters, "flowParams cannot be null", new Object[0]));
        putExtra.setExtrasClassLoader(AuthUI.class.getClassLoader());
        return putExtra;
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 102 || i2 == 5) {
            finish(i2, intent);
        }
    }

    public FlowParameters getFlowParams() {
        if (this.mParams == null) {
            this.mParams = FlowParameters.fromIntent(getIntent());
        }
        return this.mParams;
    }

    public void finish(int i, Intent intent) {
        setResult(i, intent);
        finish();
    }

    public void startSaveCredentials(FirebaseUser firebaseUser, IdpResponse idpResponse, String str) {
        startActivityForResult(CredentialSaveActivity.createIntent(this, getFlowParams(), CredentialUtils.buildCredential(firebaseUser, str, ProviderUtils.idpResponseToAccountType(idpResponse)), idpResponse), 102);
    }

    /* access modifiers changed from: protected */
    public boolean isOffline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService("connectivity");
        return connectivityManager == null || connectivityManager.getActiveNetworkInfo() == null || !connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}

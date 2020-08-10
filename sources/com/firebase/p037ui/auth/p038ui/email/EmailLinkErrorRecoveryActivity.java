package com.firebase.p037ui.auth.p038ui.email;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.firebase.p037ui.auth.C1330R;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.p038ui.AppCompatBase;

/* renamed from: com.firebase.ui.auth.ui.email.EmailLinkErrorRecoveryActivity */
public class EmailLinkErrorRecoveryActivity extends AppCompatBase implements EmailLinkPromptEmailListener, FinishEmailLinkSignInListener {
    private static final String RECOVERY_TYPE_KEY = "com.firebase.ui.auth.ui.email.recoveryTypeKey";

    public static Intent createIntent(Context context, FlowParameters flowParameters, int i) {
        return createBaseIntent(context, EmailLinkErrorRecoveryActivity.class, flowParameters).putExtra(RECOVERY_TYPE_KEY, i);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        Fragment fragment;
        super.onCreate(bundle);
        setContentView(C1330R.layout.fui_activity_register_email);
        if (bundle == null) {
            if (getIntent().getIntExtra(RECOVERY_TYPE_KEY, -1) == 116) {
                fragment = EmailLinkCrossDeviceLinkingFragment.newInstance();
            } else {
                fragment = EmailLinkPromptEmailFragment.newInstance();
            }
            switchFragment(fragment, C1330R.C1333id.fragment_register_email, EmailLinkPromptEmailFragment.TAG);
        }
    }

    public void onEmailPromptSuccess(IdpResponse idpResponse) {
        finish(-1, idpResponse.toIntent());
    }

    public void completeCrossDeviceEmailLinkFlow() {
        switchFragment(EmailLinkPromptEmailFragment.newInstance(), C1330R.C1333id.fragment_register_email, EmailLinkCrossDeviceLinkingFragment.TAG, true, true);
    }

    public void showProgress(int i) {
        throw new UnsupportedOperationException("Fragments must handle progress updates.");
    }

    public void hideProgress() {
        throw new UnsupportedOperationException("Fragments must handle progress updates.");
    }
}

package com.firebase.p037ui.auth.p038ui.email;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p037ui.auth.C1330R;
import com.firebase.p037ui.auth.FirebaseAuthAnonymousUpgradeException;
import com.firebase.p037ui.auth.FirebaseUiException;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.data.model.UserCancellationException;
import com.firebase.p037ui.auth.p038ui.InvisibleActivityBase;
import com.firebase.p037ui.auth.util.ExtraConstants;
import com.firebase.p037ui.auth.viewmodel.ResourceObserver;
import com.firebase.p037ui.auth.viewmodel.email.EmailLinkSignInHandler;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

/* renamed from: com.firebase.ui.auth.ui.email.EmailLinkCatcherActivity */
public class EmailLinkCatcherActivity extends InvisibleActivityBase {
    private EmailLinkSignInHandler mHandler;

    public static Intent createIntent(Context context, FlowParameters flowParameters) {
        return createBaseIntent(context, EmailLinkCatcherActivity.class, flowParameters);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initHandler();
        if (getFlowParams().emailLink != null) {
            this.mHandler.startSignIn();
        }
    }

    private void initHandler() {
        this.mHandler = (EmailLinkSignInHandler) ViewModelProviders.m78of((FragmentActivity) this).get(EmailLinkSignInHandler.class);
        this.mHandler.init(getFlowParams());
        this.mHandler.getOperation().observe(this, new ResourceObserver<IdpResponse>(this) {
            /* access modifiers changed from: protected */
            public void onSuccess(IdpResponse idpResponse) {
                EmailLinkCatcherActivity.this.finish(-1, idpResponse.toIntent());
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception exc) {
                if (exc instanceof UserCancellationException) {
                    EmailLinkCatcherActivity.this.finish(0, null);
                } else if (exc instanceof FirebaseAuthAnonymousUpgradeException) {
                    EmailLinkCatcherActivity.this.finish(0, new Intent().putExtra(ExtraConstants.IDP_RESPONSE, ((FirebaseAuthAnonymousUpgradeException) exc).getResponse()));
                } else if (exc instanceof FirebaseUiException) {
                    int errorCode = ((FirebaseUiException) exc).getErrorCode();
                    if (errorCode == 8 || errorCode == 7 || errorCode == 11) {
                        EmailLinkCatcherActivity.this.buildAlertDialog(errorCode).show();
                    } else if (errorCode == 9 || errorCode == 6) {
                        EmailLinkCatcherActivity.this.startErrorRecoveryFlow(115);
                    } else if (errorCode == 10) {
                        EmailLinkCatcherActivity.this.startErrorRecoveryFlow(116);
                    }
                } else if (exc instanceof FirebaseAuthInvalidCredentialsException) {
                    EmailLinkCatcherActivity.this.startErrorRecoveryFlow(115);
                } else {
                    EmailLinkCatcherActivity.this.finish(0, IdpResponse.getErrorIntent(exc));
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void startErrorRecoveryFlow(int i) {
        if (i == 116 || i == 115) {
            startActivityForResult(EmailLinkErrorRecoveryActivity.createIntent(getApplicationContext(), getFlowParams(), i), i);
            return;
        }
        throw new IllegalStateException("Invalid flow param. It must be either RequestCodes.EMAIL_LINK_CROSS_DEVICE_LINKING_FLOW or RequestCodes.EMAIL_LINK_PROMPT_FOR_EMAIL_FLOW");
    }

    /* access modifiers changed from: private */
    public AlertDialog buildAlertDialog(final int i) {
        String str;
        String str2;
        Builder builder = new Builder(this);
        if (i == 11) {
            str2 = getString(C1330R.C1335string.fui_email_link_different_anonymous_user_header);
            str = getString(C1330R.C1335string.fui_email_link_different_anonymous_user_message);
        } else if (i == 7) {
            str2 = getString(C1330R.C1335string.fui_email_link_invalid_link_header);
            str = getString(C1330R.C1335string.fui_email_link_invalid_link_message);
        } else {
            str2 = getString(C1330R.C1335string.fui_email_link_wrong_device_header);
            str = getString(C1330R.C1335string.fui_email_link_wrong_device_message);
        }
        return builder.setTitle(str2).setMessage(str).setPositiveButton(C1330R.C1335string.fui_email_link_dismiss_button, new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                EmailLinkCatcherActivity.this.finish(i, null);
            }
        }).create();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 115 || i == 116) {
            IdpResponse fromResultIntent = IdpResponse.fromResultIntent(intent);
            if (i2 == -1) {
                finish(-1, fromResultIntent.toIntent());
            } else {
                finish(0, null);
            }
        }
    }
}

package com.firebase.p037ui.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.data.model.UserCancellationException;
import com.firebase.p037ui.auth.data.remote.SignInKickstarter;
import com.firebase.p037ui.auth.p038ui.InvisibleActivityBase;
import com.firebase.p037ui.auth.util.ExtraConstants;
import com.firebase.p037ui.auth.viewmodel.ResourceObserver;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

/* renamed from: com.firebase.ui.auth.KickoffActivity */
public class KickoffActivity extends InvisibleActivityBase {
    /* access modifiers changed from: private */
    public SignInKickstarter mKickstarter;

    public static Intent createIntent(Context context, FlowParameters flowParameters) {
        return createBaseIntent(context, KickoffActivity.class, flowParameters);
    }

    /* access modifiers changed from: protected */
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.mKickstarter = (SignInKickstarter) ViewModelProviders.m78of((FragmentActivity) this).get(SignInKickstarter.class);
        this.mKickstarter.init(getFlowParams());
        this.mKickstarter.getOperation().observe(this, new ResourceObserver<IdpResponse>(this) {
            /* access modifiers changed from: protected */
            public void onSuccess(IdpResponse idpResponse) {
                KickoffActivity.this.finish(-1, idpResponse.toIntent());
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception exc) {
                if (exc instanceof UserCancellationException) {
                    KickoffActivity.this.finish(0, null);
                } else if (exc instanceof FirebaseAuthAnonymousUpgradeException) {
                    KickoffActivity.this.finish(0, new Intent().putExtra(ExtraConstants.IDP_RESPONSE, ((FirebaseAuthAnonymousUpgradeException) exc).getResponse()));
                } else {
                    KickoffActivity.this.finish(0, IdpResponse.getErrorIntent(exc));
                }
            }
        });
        GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this).addOnSuccessListener((Activity) this, (OnSuccessListener<? super TResult>) new OnSuccessListener<Void>() {
            public void onSuccess(Void voidR) {
                if (bundle == null) {
                    KickoffActivity.this.mKickstarter.start();
                }
            }
        }).addOnFailureListener((Activity) this, (OnFailureListener) new OnFailureListener() {
            public void onFailure(Exception exc) {
                KickoffActivity.this.finish(0, IdpResponse.getErrorIntent(new FirebaseUiException(2, (Throwable) exc)));
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 106 && (i2 == 113 || i2 == 114)) {
            invalidateEmailLink();
        }
        this.mKickstarter.onActivityResult(i, i2, intent);
    }

    public void invalidateEmailLink() {
        FlowParameters flowParams = getFlowParams();
        flowParams.emailLink = null;
        setIntent(getIntent().putExtra(ExtraConstants.FLOW_PARAMS, flowParams));
    }
}

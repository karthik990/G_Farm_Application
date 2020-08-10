package com.firebase.p037ui.auth.p038ui.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p037ui.auth.C1330R;
import com.firebase.p037ui.auth.FirebaseAuthAnonymousUpgradeException;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.IdpResponse.Builder;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.data.model.PhoneNumberVerificationRequiredException;
import com.firebase.p037ui.auth.data.model.User;
import com.firebase.p037ui.auth.p038ui.AppCompatBase;
import com.firebase.p037ui.auth.p038ui.FragmentBase;
import com.firebase.p037ui.auth.util.ExtraConstants;
import com.firebase.p037ui.auth.util.FirebaseAuthError;
import com.firebase.p037ui.auth.viewmodel.ResourceObserver;
import com.firebase.p037ui.auth.viewmodel.phone.PhoneProviderResponseHandler;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuthException;

/* renamed from: com.firebase.ui.auth.ui.phone.PhoneActivity */
public class PhoneActivity extends AppCompatBase {
    private PhoneNumberVerificationHandler mPhoneVerifier;

    /* renamed from: com.firebase.ui.auth.ui.phone.PhoneActivity$3 */
    static /* synthetic */ class C13813 {
        static final /* synthetic */ int[] $SwitchMap$com$firebase$ui$auth$util$FirebaseAuthError = new int[FirebaseAuthError.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                com.firebase.ui.auth.util.FirebaseAuthError[] r0 = com.firebase.p037ui.auth.util.FirebaseAuthError.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$firebase$ui$auth$util$FirebaseAuthError = r0
                int[] r0 = $SwitchMap$com$firebase$ui$auth$util$FirebaseAuthError     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.firebase.ui.auth.util.FirebaseAuthError r1 = com.firebase.p037ui.auth.util.FirebaseAuthError.ERROR_INVALID_PHONE_NUMBER     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$com$firebase$ui$auth$util$FirebaseAuthError     // Catch:{ NoSuchFieldError -> 0x001f }
                com.firebase.ui.auth.util.FirebaseAuthError r1 = com.firebase.p037ui.auth.util.FirebaseAuthError.ERROR_TOO_MANY_REQUESTS     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$com$firebase$ui$auth$util$FirebaseAuthError     // Catch:{ NoSuchFieldError -> 0x002a }
                com.firebase.ui.auth.util.FirebaseAuthError r1 = com.firebase.p037ui.auth.util.FirebaseAuthError.ERROR_QUOTA_EXCEEDED     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$com$firebase$ui$auth$util$FirebaseAuthError     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.firebase.ui.auth.util.FirebaseAuthError r1 = com.firebase.p037ui.auth.util.FirebaseAuthError.ERROR_INVALID_VERIFICATION_CODE     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = $SwitchMap$com$firebase$ui$auth$util$FirebaseAuthError     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.firebase.ui.auth.util.FirebaseAuthError r1 = com.firebase.p037ui.auth.util.FirebaseAuthError.ERROR_SESSION_EXPIRED     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.firebase.p037ui.auth.p038ui.phone.PhoneActivity.C13813.<clinit>():void");
        }
    }

    public static Intent createIntent(Context context, FlowParameters flowParameters, Bundle bundle) {
        return createBaseIntent(context, PhoneActivity.class, flowParameters).putExtra(ExtraConstants.PARAMS, bundle);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C1330R.layout.fui_activity_register_phone);
        final PhoneProviderResponseHandler phoneProviderResponseHandler = (PhoneProviderResponseHandler) ViewModelProviders.m78of((FragmentActivity) this).get(PhoneProviderResponseHandler.class);
        phoneProviderResponseHandler.init(getFlowParams());
        phoneProviderResponseHandler.getOperation().observe(this, new ResourceObserver<IdpResponse>(this, C1330R.C1335string.fui_progress_dialog_signing_in) {
            /* access modifiers changed from: protected */
            public void onSuccess(IdpResponse idpResponse) {
                PhoneActivity.this.startSaveCredentials(phoneProviderResponseHandler.getCurrentUser(), idpResponse, null);
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception exc) {
                PhoneActivity.this.handleError(exc);
            }
        });
        this.mPhoneVerifier = (PhoneNumberVerificationHandler) ViewModelProviders.m78of((FragmentActivity) this).get(PhoneNumberVerificationHandler.class);
        this.mPhoneVerifier.init(getFlowParams());
        this.mPhoneVerifier.onRestoreInstanceState(bundle);
        this.mPhoneVerifier.getOperation().observe(this, new ResourceObserver<PhoneVerification>(this, C1330R.C1335string.fui_verifying) {
            /* access modifiers changed from: protected */
            public void onSuccess(PhoneVerification phoneVerification) {
                if (phoneVerification.isAutoVerified()) {
                    Toast.makeText(PhoneActivity.this, C1330R.C1335string.fui_auto_verified, 1).show();
                    FragmentManager supportFragmentManager = PhoneActivity.this.getSupportFragmentManager();
                    if (supportFragmentManager.findFragmentByTag(SubmitConfirmationCodeFragment.TAG) != null) {
                        supportFragmentManager.popBackStack();
                    }
                }
                phoneProviderResponseHandler.startSignIn(phoneVerification.getCredential(), new Builder(new User.Builder("phone", null).setPhoneNumber(phoneVerification.getNumber()).build()).build());
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception exc) {
                if (exc instanceof PhoneNumberVerificationRequiredException) {
                    if (PhoneActivity.this.getSupportFragmentManager().findFragmentByTag(SubmitConfirmationCodeFragment.TAG) == null) {
                        PhoneActivity.this.showSubmitCodeFragment(((PhoneNumberVerificationRequiredException) exc).getPhoneNumber());
                    }
                    PhoneActivity.this.handleError(null);
                    return;
                }
                PhoneActivity.this.handleError(exc);
            }
        });
        if (bundle == null) {
            getSupportFragmentManager().beginTransaction().replace(C1330R.C1333id.fragment_phone, CheckPhoneNumberFragment.newInstance(getIntent().getExtras().getBundle(ExtraConstants.PARAMS)), CheckPhoneNumberFragment.TAG).disallowAddToBackStack().commit();
        }
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.mPhoneVerifier.onSaveInstanceState(bundle);
    }

    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    /* access modifiers changed from: private */
    public void handleError(Exception exc) {
        TextInputLayout errorView = getErrorView();
        if (errorView != null) {
            if (exc instanceof FirebaseAuthAnonymousUpgradeException) {
                finish(5, ((FirebaseAuthAnonymousUpgradeException) exc).getResponse().toIntent());
            } else if (exc instanceof FirebaseAuthException) {
                errorView.setError(getErrorMessage(FirebaseAuthError.fromException((FirebaseAuthException) exc)));
            } else if (exc != null) {
                errorView.setError(exc.getLocalizedMessage());
            } else {
                errorView.setError(null);
            }
        }
    }

    private TextInputLayout getErrorView() {
        CheckPhoneNumberFragment checkPhoneNumberFragment = (CheckPhoneNumberFragment) getSupportFragmentManager().findFragmentByTag(CheckPhoneNumberFragment.TAG);
        SubmitConfirmationCodeFragment submitConfirmationCodeFragment = (SubmitConfirmationCodeFragment) getSupportFragmentManager().findFragmentByTag(SubmitConfirmationCodeFragment.TAG);
        if (checkPhoneNumberFragment != null && checkPhoneNumberFragment.getView() != null) {
            return (TextInputLayout) checkPhoneNumberFragment.getView().findViewById(C1330R.C1333id.phone_layout);
        }
        if (submitConfirmationCodeFragment == null || submitConfirmationCodeFragment.getView() == null) {
            return null;
        }
        return (TextInputLayout) submitConfirmationCodeFragment.getView().findViewById(C1330R.C1333id.confirmation_code_layout);
    }

    private String getErrorMessage(FirebaseAuthError firebaseAuthError) {
        int i = C13813.$SwitchMap$com$firebase$ui$auth$util$FirebaseAuthError[firebaseAuthError.ordinal()];
        if (i == 1) {
            return getString(C1330R.C1335string.fui_invalid_phone_number);
        }
        if (i == 2) {
            return getString(C1330R.C1335string.fui_error_too_many_attempts);
        }
        if (i == 3) {
            return getString(C1330R.C1335string.fui_error_quota_exceeded);
        }
        if (i == 4) {
            return getString(C1330R.C1335string.fui_incorrect_code_dialog_body);
        }
        if (i != 5) {
            return firebaseAuthError.getDescription();
        }
        return getString(C1330R.C1335string.fui_error_session_expired);
    }

    /* access modifiers changed from: private */
    public void showSubmitCodeFragment(String str) {
        getSupportFragmentManager().beginTransaction().replace(C1330R.C1333id.fragment_phone, SubmitConfirmationCodeFragment.newInstance(str), SubmitConfirmationCodeFragment.TAG).addToBackStack(null).commit();
    }

    public void showProgress(int i) {
        getActiveFragment().showProgress(i);
    }

    public void hideProgress() {
        getActiveFragment().hideProgress();
    }

    private FragmentBase getActiveFragment() {
        FragmentBase fragmentBase = (CheckPhoneNumberFragment) getSupportFragmentManager().findFragmentByTag(CheckPhoneNumberFragment.TAG);
        if (fragmentBase == null || fragmentBase.getView() == null) {
            fragmentBase = (SubmitConfirmationCodeFragment) getSupportFragmentManager().findFragmentByTag(SubmitConfirmationCodeFragment.TAG);
        }
        if (fragmentBase != null && fragmentBase.getView() != null) {
            return fragmentBase;
        }
        throw new IllegalStateException("No fragments added");
    }
}

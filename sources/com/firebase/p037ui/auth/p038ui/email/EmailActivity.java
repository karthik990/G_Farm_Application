package com.firebase.p037ui.auth.p038ui.email;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentTransaction;
import com.firebase.p037ui.auth.AuthUI.IdpConfig;
import com.firebase.p037ui.auth.C1330R;
import com.firebase.p037ui.auth.FirebaseUiException;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.IdpResponse.Builder;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.data.model.User;
import com.firebase.p037ui.auth.p038ui.AppCompatBase;
import com.firebase.p037ui.auth.p038ui.idp.WelcomeBackIdpPrompt;
import com.firebase.p037ui.auth.util.ExtraConstants;
import com.firebase.p037ui.auth.util.data.EmailLinkPersistenceManager;
import com.firebase.p037ui.auth.util.data.ProviderUtils;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.ActionCodeSettings;

/* renamed from: com.firebase.ui.auth.ui.email.EmailActivity */
public class EmailActivity extends AppCompatBase implements CheckEmailListener, AnonymousUpgradeListener, TroubleSigningInListener, ResendEmailListener {
    public static Intent createIntent(Context context, FlowParameters flowParameters) {
        return createBaseIntent(context, EmailActivity.class, flowParameters);
    }

    public static Intent createIntent(Context context, FlowParameters flowParameters, String str) {
        return createBaseIntent(context, EmailActivity.class, flowParameters).putExtra(ExtraConstants.EMAIL, str);
    }

    public static Intent createIntentForLinking(Context context, FlowParameters flowParameters, IdpResponse idpResponse) {
        return createIntent(context, flowParameters, idpResponse.getEmail()).putExtra(ExtraConstants.IDP_RESPONSE, idpResponse);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C1330R.layout.fui_activity_register_email);
        if (bundle == null) {
            String string = getIntent().getExtras().getString(ExtraConstants.EMAIL);
            IdpResponse idpResponse = (IdpResponse) getIntent().getExtras().getParcelable(ExtraConstants.IDP_RESPONSE);
            if (string == null || idpResponse == null) {
                switchFragment(CheckEmailFragment.newInstance(string), C1330R.C1333id.fragment_register_email, CheckEmailFragment.TAG);
            } else {
                IdpConfig configFromIdpsOrThrow = ProviderUtils.getConfigFromIdpsOrThrow(getFlowParams().providers, "emailLink");
                ActionCodeSettings actionCodeSettings = (ActionCodeSettings) configFromIdpsOrThrow.getParams().getParcelable(ExtraConstants.ACTION_CODE_SETTINGS);
                EmailLinkPersistenceManager.getInstance().saveIdpResponseForLinking(getApplication(), idpResponse);
                switchFragment(EmailLinkFragment.newInstance(string, actionCodeSettings, idpResponse, configFromIdpsOrThrow.getParams().getBoolean(ExtraConstants.FORCE_SAME_DEVICE)), C1330R.C1333id.fragment_register_email, EmailLinkFragment.TAG);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 104 || i == 103) {
            finish(i2, intent);
        }
    }

    public void onExistingEmailUser(User user) {
        String str = "emailLink";
        if (user.getProviderId().equals(str)) {
            showRegisterEmailLinkFragment(ProviderUtils.getConfigFromIdpsOrThrow(getFlowParams().providers, str), user.getEmail());
            return;
        }
        startActivityForResult(WelcomeBackPasswordPrompt.createIntent(this, getFlowParams(), new Builder(user).build()), 104);
        setSlideAnimation();
    }

    public void onExistingIdpUser(User user) {
        startActivityForResult(WelcomeBackIdpPrompt.createIntent(this, getFlowParams(), user), 103);
        setSlideAnimation();
    }

    public void onNewUser(User user) {
        TextInputLayout textInputLayout = (TextInputLayout) findViewById(C1330R.C1333id.email_layout);
        IdpConfig configFromIdps = ProviderUtils.getConfigFromIdps(getFlowParams().providers, "password");
        String str = "emailLink";
        if (configFromIdps == null) {
            configFromIdps = ProviderUtils.getConfigFromIdps(getFlowParams().providers, str);
        }
        if (configFromIdps.getParams().getBoolean(ExtraConstants.ALLOW_NEW_EMAILS, true)) {
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            if (configFromIdps.getProviderId().equals(str)) {
                showRegisterEmailLinkFragment(configFromIdps, user.getEmail());
                return;
            }
            beginTransaction.replace(C1330R.C1333id.fragment_register_email, RegisterEmailFragment.newInstance(user), RegisterEmailFragment.TAG);
            if (textInputLayout != null) {
                String string = getString(C1330R.C1335string.fui_email_field_name);
                ViewCompat.setTransitionName(textInputLayout, string);
                beginTransaction.addSharedElement(textInputLayout, string);
            }
            beginTransaction.disallowAddToBackStack().commit();
            return;
        }
        textInputLayout.setError(getString(C1330R.C1335string.fui_error_email_does_not_exist));
    }

    public void onTroubleSigningIn(String str) {
        switchFragment(TroubleSigningInFragment.newInstance(str), C1330R.C1333id.fragment_register_email, TroubleSigningInFragment.TAG, true, true);
    }

    public void onClickResendEmail(String str) {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
        showRegisterEmailLinkFragment(ProviderUtils.getConfigFromIdpsOrThrow(getFlowParams().providers, "emailLink"), str);
    }

    public void onSendEmailFailure(Exception exc) {
        finishOnDeveloperError(exc);
    }

    public void onDeveloperFailure(Exception exc) {
        finishOnDeveloperError(exc);
    }

    private void finishOnDeveloperError(Exception exc) {
        finish(0, IdpResponse.getErrorIntent(new FirebaseUiException(3, exc.getMessage())));
    }

    private void setSlideAnimation() {
        overridePendingTransition(C1330R.anim.fui_slide_in_right, C1330R.anim.fui_slide_out_left);
    }

    private void showRegisterEmailLinkFragment(IdpConfig idpConfig, String str) {
        switchFragment(EmailLinkFragment.newInstance(str, (ActionCodeSettings) idpConfig.getParams().getParcelable(ExtraConstants.ACTION_CODE_SETTINGS)), C1330R.C1333id.fragment_register_email, EmailLinkFragment.TAG);
    }

    public void showProgress(int i) {
        throw new UnsupportedOperationException("Email fragments must handle progress updates.");
    }

    public void hideProgress() {
        throw new UnsupportedOperationException("Email fragments must handle progress updates.");
    }

    public void onMergeFailure(IdpResponse idpResponse) {
        finish(5, idpResponse.toIntent());
    }
}

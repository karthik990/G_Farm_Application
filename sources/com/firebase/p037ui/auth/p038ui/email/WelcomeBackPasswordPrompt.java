package com.firebase.p037ui.auth.p038ui.email;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p037ui.auth.C1330R;
import com.firebase.p037ui.auth.FirebaseAuthAnonymousUpgradeException;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.p038ui.AppCompatBase;
import com.firebase.p037ui.auth.util.ExtraConstants;
import com.firebase.p037ui.auth.util.data.PrivacyDisclosureUtils;
import com.firebase.p037ui.auth.util.data.ProviderUtils;
import com.firebase.p037ui.auth.util.p039ui.ImeHelper;
import com.firebase.p037ui.auth.util.p039ui.ImeHelper.DonePressedListener;
import com.firebase.p037ui.auth.util.p039ui.TextHelper;
import com.firebase.p037ui.auth.viewmodel.ResourceObserver;
import com.firebase.p037ui.auth.viewmodel.email.WelcomeBackPasswordHandler;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

/* renamed from: com.firebase.ui.auth.ui.email.WelcomeBackPasswordPrompt */
public class WelcomeBackPasswordPrompt extends AppCompatBase implements OnClickListener, DonePressedListener {
    private Button mDoneButton;
    /* access modifiers changed from: private */
    public WelcomeBackPasswordHandler mHandler;
    private IdpResponse mIdpResponse;
    private EditText mPasswordField;
    /* access modifiers changed from: private */
    public TextInputLayout mPasswordLayout;
    private ProgressBar mProgressBar;

    public static Intent createIntent(Context context, FlowParameters flowParameters, IdpResponse idpResponse) {
        return createBaseIntent(context, WelcomeBackPasswordPrompt.class, flowParameters).putExtra(ExtraConstants.IDP_RESPONSE, idpResponse);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C1330R.layout.fui_welcome_back_password_prompt_layout);
        getWindow().setSoftInputMode(4);
        this.mIdpResponse = IdpResponse.fromResultIntent(getIntent());
        String email = this.mIdpResponse.getEmail();
        this.mDoneButton = (Button) findViewById(C1330R.C1333id.button_done);
        this.mProgressBar = (ProgressBar) findViewById(C1330R.C1333id.top_progress_bar);
        this.mPasswordLayout = (TextInputLayout) findViewById(C1330R.C1333id.password_layout);
        this.mPasswordField = (EditText) findViewById(C1330R.C1333id.password);
        ImeHelper.setImeOnDoneListener(this.mPasswordField, this);
        String string = getString(C1330R.C1335string.fui_welcome_back_password_prompt_body, new Object[]{email});
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(string);
        TextHelper.boldAllOccurencesOfText(spannableStringBuilder, string, email);
        ((TextView) findViewById(C1330R.C1333id.welcome_back_password_body)).setText(spannableStringBuilder);
        this.mDoneButton.setOnClickListener(this);
        findViewById(C1330R.C1333id.trouble_signing_in).setOnClickListener(this);
        this.mHandler = (WelcomeBackPasswordHandler) ViewModelProviders.m78of((FragmentActivity) this).get(WelcomeBackPasswordHandler.class);
        this.mHandler.init(getFlowParams());
        this.mHandler.getOperation().observe(this, new ResourceObserver<IdpResponse>(this, C1330R.C1335string.fui_progress_dialog_signing_in) {
            /* access modifiers changed from: protected */
            public void onSuccess(IdpResponse idpResponse) {
                WelcomeBackPasswordPrompt welcomeBackPasswordPrompt = WelcomeBackPasswordPrompt.this;
                welcomeBackPasswordPrompt.startSaveCredentials(welcomeBackPasswordPrompt.mHandler.getCurrentUser(), idpResponse, WelcomeBackPasswordPrompt.this.mHandler.getPendingPassword());
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception exc) {
                if (exc instanceof FirebaseAuthAnonymousUpgradeException) {
                    WelcomeBackPasswordPrompt.this.finish(5, ((FirebaseAuthAnonymousUpgradeException) exc).getResponse().toIntent());
                    return;
                }
                TextInputLayout access$200 = WelcomeBackPasswordPrompt.this.mPasswordLayout;
                WelcomeBackPasswordPrompt welcomeBackPasswordPrompt = WelcomeBackPasswordPrompt.this;
                access$200.setError(welcomeBackPasswordPrompt.getString(welcomeBackPasswordPrompt.getErrorMessage(exc)));
            }
        });
        PrivacyDisclosureUtils.setupTermsOfServiceFooter(this, getFlowParams(), (TextView) findViewById(C1330R.C1333id.email_footer_tos_and_pp_text));
    }

    /* access modifiers changed from: private */
    public int getErrorMessage(Exception exc) {
        if (exc instanceof FirebaseAuthInvalidCredentialsException) {
            return C1330R.C1335string.fui_error_invalid_password;
        }
        return C1330R.C1335string.fui_error_unknown;
    }

    private void onForgotPasswordClicked() {
        startActivity(RecoverPasswordActivity.createIntent(this, getFlowParams(), this.mIdpResponse.getEmail()));
    }

    public void onDonePressed() {
        validateAndSignIn();
    }

    private void validateAndSignIn() {
        validateAndSignIn(this.mPasswordField.getText().toString());
    }

    private void validateAndSignIn(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mPasswordLayout.setError(getString(C1330R.C1335string.fui_error_invalid_password));
            return;
        }
        this.mPasswordLayout.setError(null);
        this.mHandler.startSignIn(this.mIdpResponse.getEmail(), str, this.mIdpResponse, ProviderUtils.getAuthCredential(this.mIdpResponse));
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == C1330R.C1333id.button_done) {
            validateAndSignIn();
        } else if (id == C1330R.C1333id.trouble_signing_in) {
            onForgotPasswordClicked();
        }
    }

    public void showProgress(int i) {
        this.mDoneButton.setEnabled(false);
        this.mProgressBar.setVisibility(0);
    }

    public void hideProgress() {
        this.mDoneButton.setEnabled(true);
        this.mProgressBar.setVisibility(4);
    }
}

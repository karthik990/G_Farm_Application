package com.firebase.p037ui.auth.p038ui.email;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.firebase.p037ui.auth.C1330R;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.p038ui.AppCompatBase;
import com.firebase.p037ui.auth.util.ExtraConstants;
import com.firebase.p037ui.auth.util.data.PrivacyDisclosureUtils;
import com.firebase.p037ui.auth.util.p039ui.TextHelper;

/* renamed from: com.firebase.ui.auth.ui.email.WelcomeBackEmailLinkPrompt */
public class WelcomeBackEmailLinkPrompt extends AppCompatBase implements OnClickListener {
    private IdpResponse mIdpResponseForLinking;
    private ProgressBar mProgressBar;
    private Button mSignInButton;

    public static Intent createIntent(Context context, FlowParameters flowParameters, IdpResponse idpResponse) {
        return createBaseIntent(context, WelcomeBackEmailLinkPrompt.class, flowParameters).putExtra(ExtraConstants.IDP_RESPONSE, idpResponse);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C1330R.layout.fui_welcome_back_email_link_prompt_layout);
        this.mIdpResponseForLinking = IdpResponse.fromResultIntent(getIntent());
        initializeViewObjects();
        setBodyText();
        setOnClickListeners();
        setPrivacyFooter();
    }

    private void startEmailLinkFlow() {
        startActivityForResult(EmailActivity.createIntentForLinking(this, getFlowParams(), this.mIdpResponseForLinking), 112);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        finish(i2, intent);
    }

    private void initializeViewObjects() {
        this.mSignInButton = (Button) findViewById(C1330R.C1333id.button_sign_in);
        this.mProgressBar = (ProgressBar) findViewById(C1330R.C1333id.top_progress_bar);
    }

    private void setBodyText() {
        TextView textView = (TextView) findViewById(C1330R.C1333id.welcome_back_email_link_body);
        String string = getString(C1330R.C1335string.fui_welcome_back_email_link_prompt_body, new Object[]{this.mIdpResponseForLinking.getEmail(), this.mIdpResponseForLinking.getProviderType()});
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(string);
        TextHelper.boldAllOccurencesOfText(spannableStringBuilder, string, this.mIdpResponseForLinking.getEmail());
        TextHelper.boldAllOccurencesOfText(spannableStringBuilder, string, this.mIdpResponseForLinking.getProviderType());
        textView.setText(spannableStringBuilder);
        if (VERSION.SDK_INT >= 26) {
            textView.setJustificationMode(1);
        }
    }

    private void setOnClickListeners() {
        this.mSignInButton.setOnClickListener(this);
    }

    private void setPrivacyFooter() {
        PrivacyDisclosureUtils.setupTermsOfServiceFooter(this, getFlowParams(), (TextView) findViewById(C1330R.C1333id.email_footer_tos_and_pp_text));
    }

    public void onClick(View view) {
        if (view.getId() == C1330R.C1333id.button_sign_in) {
            startEmailLinkFlow();
        }
    }

    public void showProgress(int i) {
        this.mSignInButton.setEnabled(false);
        this.mProgressBar.setVisibility(0);
    }

    public void hideProgress() {
        this.mProgressBar.setEnabled(true);
        this.mProgressBar.setVisibility(4);
    }
}

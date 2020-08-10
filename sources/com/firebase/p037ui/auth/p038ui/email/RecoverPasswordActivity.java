package com.firebase.p037ui.auth.p038ui.email;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p037ui.auth.C1330R;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.p038ui.AppCompatBase;
import com.firebase.p037ui.auth.util.ExtraConstants;
import com.firebase.p037ui.auth.util.data.PrivacyDisclosureUtils;
import com.firebase.p037ui.auth.util.p039ui.ImeHelper;
import com.firebase.p037ui.auth.util.p039ui.ImeHelper.DonePressedListener;
import com.firebase.p037ui.auth.util.p039ui.fieldvalidators.EmailFieldValidator;
import com.firebase.p037ui.auth.viewmodel.ResourceObserver;
import com.firebase.p037ui.auth.viewmodel.email.RecoverPasswordHandler;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

/* renamed from: com.firebase.ui.auth.ui.email.RecoverPasswordActivity */
public class RecoverPasswordActivity extends AppCompatBase implements OnClickListener, DonePressedListener {
    private EditText mEmailEditText;
    private EmailFieldValidator mEmailFieldValidator;
    /* access modifiers changed from: private */
    public TextInputLayout mEmailInputLayout;
    private RecoverPasswordHandler mHandler;
    private ProgressBar mProgressBar;
    private Button mSubmitButton;

    public static Intent createIntent(Context context, FlowParameters flowParameters, String str) {
        return createBaseIntent(context, RecoverPasswordActivity.class, flowParameters).putExtra(ExtraConstants.EMAIL, str);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C1330R.layout.fui_forgot_password_layout);
        this.mHandler = (RecoverPasswordHandler) ViewModelProviders.m78of((FragmentActivity) this).get(RecoverPasswordHandler.class);
        this.mHandler.init(getFlowParams());
        this.mHandler.getOperation().observe(this, new ResourceObserver<String>(this, C1330R.C1335string.fui_progress_dialog_sending) {
            /* access modifiers changed from: protected */
            public void onSuccess(String str) {
                RecoverPasswordActivity.this.mEmailInputLayout.setError(null);
                RecoverPasswordActivity.this.showEmailSentDialog(str);
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception exc) {
                if ((exc instanceof FirebaseAuthInvalidUserException) || (exc instanceof FirebaseAuthInvalidCredentialsException)) {
                    RecoverPasswordActivity.this.mEmailInputLayout.setError(RecoverPasswordActivity.this.getString(C1330R.C1335string.fui_error_email_does_not_exist));
                } else {
                    RecoverPasswordActivity.this.mEmailInputLayout.setError(RecoverPasswordActivity.this.getString(C1330R.C1335string.fui_error_unknown));
                }
            }
        });
        this.mProgressBar = (ProgressBar) findViewById(C1330R.C1333id.top_progress_bar);
        this.mSubmitButton = (Button) findViewById(C1330R.C1333id.button_done);
        this.mEmailInputLayout = (TextInputLayout) findViewById(C1330R.C1333id.email_layout);
        this.mEmailEditText = (EditText) findViewById(C1330R.C1333id.email);
        this.mEmailFieldValidator = new EmailFieldValidator(this.mEmailInputLayout);
        String stringExtra = getIntent().getStringExtra(ExtraConstants.EMAIL);
        if (stringExtra != null) {
            this.mEmailEditText.setText(stringExtra);
        }
        ImeHelper.setImeOnDoneListener(this.mEmailEditText, this);
        this.mSubmitButton.setOnClickListener(this);
        PrivacyDisclosureUtils.setupTermsOfServiceFooter(this, getFlowParams(), (TextView) findViewById(C1330R.C1333id.email_footer_tos_and_pp_text));
    }

    public void onClick(View view) {
        if (view.getId() == C1330R.C1333id.button_done) {
            onDonePressed();
        }
    }

    public void onDonePressed() {
        if (this.mEmailFieldValidator.validate(this.mEmailEditText.getText())) {
            this.mHandler.startReset(this.mEmailEditText.getText().toString());
        }
    }

    /* access modifiers changed from: private */
    public void showEmailSentDialog(String str) {
        new Builder(this).setTitle(C1330R.C1335string.fui_title_confirm_recover_password).setMessage((CharSequence) getString(C1330R.C1335string.fui_confirm_recovery_body, new Object[]{str})).setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialogInterface) {
                RecoverPasswordActivity.this.finish(-1, new Intent());
            }
        }).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).show();
    }

    public void showProgress(int i) {
        this.mSubmitButton.setEnabled(false);
        this.mProgressBar.setVisibility(0);
    }

    public void hideProgress() {
        this.mSubmitButton.setEnabled(true);
        this.mProgressBar.setVisibility(4);
    }
}

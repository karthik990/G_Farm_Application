package com.firebase.p037ui.auth.p038ui.email;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p037ui.auth.C1330R;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.p038ui.FragmentBase;
import com.firebase.p037ui.auth.util.data.PrivacyDisclosureUtils;
import com.firebase.p037ui.auth.util.p039ui.fieldvalidators.EmailFieldValidator;
import com.firebase.p037ui.auth.viewmodel.ResourceObserver;
import com.firebase.p037ui.auth.viewmodel.email.EmailLinkSignInHandler;
import com.google.android.material.textfield.TextInputLayout;

/* renamed from: com.firebase.ui.auth.ui.email.EmailLinkPromptEmailFragment */
public class EmailLinkPromptEmailFragment extends FragmentBase implements OnClickListener {
    public static final String TAG = "EmailLinkPromptEmailFragment";
    private EditText mEmailEditText;
    private EmailFieldValidator mEmailFieldValidator;
    /* access modifiers changed from: private */
    public TextInputLayout mEmailLayout;
    private EmailLinkSignInHandler mHandler;
    /* access modifiers changed from: private */
    public EmailLinkPromptEmailListener mListener;
    private Button mNextButton;
    private ProgressBar mProgressBar;

    /* renamed from: com.firebase.ui.auth.ui.email.EmailLinkPromptEmailFragment$EmailLinkPromptEmailListener */
    interface EmailLinkPromptEmailListener {
        void onEmailPromptSuccess(IdpResponse idpResponse);
    }

    public static EmailLinkPromptEmailFragment newInstance() {
        return new EmailLinkPromptEmailFragment();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(C1330R.layout.fui_check_email_layout, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        this.mNextButton = (Button) view.findViewById(C1330R.C1333id.button_next);
        this.mProgressBar = (ProgressBar) view.findViewById(C1330R.C1333id.top_progress_bar);
        this.mNextButton.setOnClickListener(this);
        this.mEmailLayout = (TextInputLayout) view.findViewById(C1330R.C1333id.email_layout);
        this.mEmailEditText = (EditText) view.findViewById(C1330R.C1333id.email);
        this.mEmailFieldValidator = new EmailFieldValidator(this.mEmailLayout);
        this.mEmailLayout.setOnClickListener(this);
        this.mEmailEditText.setOnClickListener(this);
        getActivity().setTitle(C1330R.C1335string.fui_email_link_confirm_email_header);
        PrivacyDisclosureUtils.setupTermsOfServiceFooter(requireContext(), getFlowParams(), (TextView) view.findViewById(C1330R.C1333id.email_footer_tos_and_pp_text));
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        FragmentActivity activity = getActivity();
        if (activity instanceof EmailLinkPromptEmailListener) {
            this.mListener = (EmailLinkPromptEmailListener) activity;
            initHandler();
            return;
        }
        throw new IllegalStateException("Activity must implement EmailLinkPromptEmailListener");
    }

    private void initHandler() {
        this.mHandler = (EmailLinkSignInHandler) ViewModelProviders.m76of((Fragment) this).get(EmailLinkSignInHandler.class);
        this.mHandler.init(getFlowParams());
        this.mHandler.getOperation().observe(this, new ResourceObserver<IdpResponse>(this) {
            /* access modifiers changed from: protected */
            public void onSuccess(IdpResponse idpResponse) {
                EmailLinkPromptEmailFragment.this.mListener.onEmailPromptSuccess(idpResponse);
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception exc) {
                EmailLinkPromptEmailFragment.this.mEmailLayout.setError(exc.getMessage());
            }
        });
    }

    private void validateEmailAndFinishSignIn() {
        String obj = this.mEmailEditText.getText().toString();
        if (this.mEmailFieldValidator.validate(obj)) {
            this.mHandler.finishSignIn(obj);
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == C1330R.C1333id.button_next) {
            validateEmailAndFinishSignIn();
        } else if (id == C1330R.C1333id.email_layout || id == C1330R.C1333id.email) {
            this.mEmailLayout.setError(null);
        }
    }

    public void showProgress(int i) {
        this.mNextButton.setEnabled(false);
        this.mProgressBar.setVisibility(0);
    }

    public void hideProgress() {
        this.mNextButton.setEnabled(true);
        this.mProgressBar.setVisibility(4);
    }
}

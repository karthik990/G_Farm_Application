package com.firebase.p037ui.auth.p038ui.email;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.firebase.p037ui.auth.FirebaseUiException;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.data.model.User;
import com.firebase.p037ui.auth.data.model.User.Builder;
import com.firebase.p037ui.auth.p038ui.FragmentBase;
import com.firebase.p037ui.auth.util.ExtraConstants;
import com.firebase.p037ui.auth.util.data.PrivacyDisclosureUtils;
import com.firebase.p037ui.auth.util.p039ui.ImeHelper;
import com.firebase.p037ui.auth.util.p039ui.ImeHelper.DonePressedListener;
import com.firebase.p037ui.auth.util.p039ui.fieldvalidators.EmailFieldValidator;
import com.firebase.p037ui.auth.viewmodel.ResourceObserver;
import com.google.android.material.textfield.TextInputLayout;

/* renamed from: com.firebase.ui.auth.ui.email.CheckEmailFragment */
public class CheckEmailFragment extends FragmentBase implements OnClickListener, DonePressedListener {
    public static final String TAG = "CheckEmailFragment";
    /* access modifiers changed from: private */
    public EditText mEmailEditText;
    private EmailFieldValidator mEmailFieldValidator;
    private TextInputLayout mEmailLayout;
    private CheckEmailHandler mHandler;
    /* access modifiers changed from: private */
    public CheckEmailListener mListener;
    private Button mNextButton;
    private ProgressBar mProgressBar;

    /* renamed from: com.firebase.ui.auth.ui.email.CheckEmailFragment$CheckEmailListener */
    interface CheckEmailListener {
        void onDeveloperFailure(Exception exc);

        void onExistingEmailUser(User user);

        void onExistingIdpUser(User user);

        void onNewUser(User user);
    }

    public static CheckEmailFragment newInstance(String str) {
        CheckEmailFragment checkEmailFragment = new CheckEmailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ExtraConstants.EMAIL, str);
        checkEmailFragment.setArguments(bundle);
        return checkEmailFragment;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(C1330R.layout.fui_check_email_layout, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        this.mNextButton = (Button) view.findViewById(C1330R.C1333id.button_next);
        this.mProgressBar = (ProgressBar) view.findViewById(C1330R.C1333id.top_progress_bar);
        this.mEmailLayout = (TextInputLayout) view.findViewById(C1330R.C1333id.email_layout);
        this.mEmailEditText = (EditText) view.findViewById(C1330R.C1333id.email);
        this.mEmailFieldValidator = new EmailFieldValidator(this.mEmailLayout);
        this.mEmailLayout.setOnClickListener(this);
        this.mEmailEditText.setOnClickListener(this);
        TextView textView = (TextView) view.findViewById(C1330R.C1333id.header_text);
        if (textView != null) {
            textView.setVisibility(8);
        }
        ImeHelper.setImeOnDoneListener(this.mEmailEditText, this);
        if (VERSION.SDK_INT >= 26 && getFlowParams().enableHints) {
            this.mEmailEditText.setImportantForAutofill(2);
        }
        this.mNextButton.setOnClickListener(this);
        TextView textView2 = (TextView) view.findViewById(C1330R.C1333id.email_tos_and_pp_text);
        TextView textView3 = (TextView) view.findViewById(C1330R.C1333id.email_footer_tos_and_pp_text);
        FlowParameters flowParams = getFlowParams();
        if (!flowParams.shouldShowProviderChoice()) {
            PrivacyDisclosureUtils.setupTermsOfServiceAndPrivacyPolicyText(requireContext(), flowParams, textView2);
            return;
        }
        textView2.setVisibility(8);
        PrivacyDisclosureUtils.setupTermsOfServiceFooter(requireContext(), flowParams, textView3);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mHandler = (CheckEmailHandler) ViewModelProviders.m76of((Fragment) this).get(CheckEmailHandler.class);
        this.mHandler.init(getFlowParams());
        FragmentActivity activity = getActivity();
        if (activity instanceof CheckEmailListener) {
            this.mListener = (CheckEmailListener) activity;
            this.mHandler.getOperation().observe(this, new ResourceObserver<User>(this, C1330R.C1335string.fui_progress_dialog_checking_accounts) {
                /* access modifiers changed from: protected */
                public void onSuccess(User user) {
                    String email = user.getEmail();
                    String providerId = user.getProviderId();
                    CheckEmailFragment.this.mEmailEditText.setText(email);
                    String str = "password";
                    if (providerId == null) {
                        CheckEmailFragment.this.mListener.onNewUser(new Builder(str, email).setName(user.getName()).setPhotoUri(user.getPhotoUri()).build());
                    } else if (providerId.equals(str) || providerId.equals("emailLink")) {
                        CheckEmailFragment.this.mListener.onExistingEmailUser(user);
                    } else {
                        CheckEmailFragment.this.mListener.onExistingIdpUser(user);
                    }
                }

                /* access modifiers changed from: protected */
                public void onFailure(Exception exc) {
                    if ((exc instanceof FirebaseUiException) && ((FirebaseUiException) exc).getErrorCode() == 3) {
                        CheckEmailFragment.this.mListener.onDeveloperFailure(exc);
                    }
                }
            });
            if (bundle == null) {
                String string = getArguments().getString(ExtraConstants.EMAIL);
                if (!TextUtils.isEmpty(string)) {
                    this.mEmailEditText.setText(string);
                    validateAndProceed();
                } else if (getFlowParams().enableHints) {
                    this.mHandler.fetchCredential();
                }
                return;
            }
            return;
        }
        throw new IllegalStateException("Activity must implement CheckEmailListener");
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        this.mHandler.onActivityResult(i, i2, intent);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == C1330R.C1333id.button_next) {
            validateAndProceed();
        } else if (id == C1330R.C1333id.email_layout || id == C1330R.C1333id.email) {
            this.mEmailLayout.setError(null);
        }
    }

    public void onDonePressed() {
        validateAndProceed();
    }

    private void validateAndProceed() {
        String obj = this.mEmailEditText.getText().toString();
        if (this.mEmailFieldValidator.validate(obj)) {
            this.mHandler.fetchProvider(obj);
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

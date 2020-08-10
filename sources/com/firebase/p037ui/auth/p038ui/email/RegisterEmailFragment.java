package com.firebase.p037ui.auth.p038ui.email;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p037ui.auth.C1330R;
import com.firebase.p037ui.auth.FirebaseAuthAnonymousUpgradeException;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.data.model.User;
import com.firebase.p037ui.auth.data.model.User.Builder;
import com.firebase.p037ui.auth.p038ui.FragmentBase;
import com.firebase.p037ui.auth.util.ExtraConstants;
import com.firebase.p037ui.auth.util.data.PrivacyDisclosureUtils;
import com.firebase.p037ui.auth.util.data.ProviderUtils;
import com.firebase.p037ui.auth.util.p039ui.ImeHelper;
import com.firebase.p037ui.auth.util.p039ui.ImeHelper.DonePressedListener;
import com.firebase.p037ui.auth.util.p039ui.fieldvalidators.BaseValidator;
import com.firebase.p037ui.auth.util.p039ui.fieldvalidators.EmailFieldValidator;
import com.firebase.p037ui.auth.util.p039ui.fieldvalidators.NoOpValidator;
import com.firebase.p037ui.auth.util.p039ui.fieldvalidators.PasswordFieldValidator;
import com.firebase.p037ui.auth.util.p039ui.fieldvalidators.RequiredFieldValidator;
import com.firebase.p037ui.auth.viewmodel.ResourceObserver;
import com.firebase.p037ui.auth.viewmodel.email.EmailProviderResponseHandler;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

/* renamed from: com.firebase.ui.auth.ui.email.RegisterEmailFragment */
public class RegisterEmailFragment extends FragmentBase implements OnClickListener, OnFocusChangeListener, DonePressedListener {
    public static final String TAG = "RegisterEmailFragment";
    private EditText mEmailEditText;
    private EmailFieldValidator mEmailFieldValidator;
    /* access modifiers changed from: private */
    public TextInputLayout mEmailInput;
    /* access modifiers changed from: private */
    public EmailProviderResponseHandler mHandler;
    /* access modifiers changed from: private */
    public AnonymousUpgradeListener mListener;
    private EditText mNameEditText;
    private BaseValidator mNameValidator;
    private Button mNextButton;
    /* access modifiers changed from: private */
    public EditText mPasswordEditText;
    private PasswordFieldValidator mPasswordFieldValidator;
    /* access modifiers changed from: private */
    public TextInputLayout mPasswordInput;
    private ProgressBar mProgressBar;
    private User mUser;

    /* renamed from: com.firebase.ui.auth.ui.email.RegisterEmailFragment$AnonymousUpgradeListener */
    interface AnonymousUpgradeListener {
        void onMergeFailure(IdpResponse idpResponse);
    }

    public static RegisterEmailFragment newInstance(User user) {
        RegisterEmailFragment registerEmailFragment = new RegisterEmailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ExtraConstants.USER, user);
        registerEmailFragment.setArguments(bundle);
        return registerEmailFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            this.mUser = User.getUser(getArguments());
        } else {
            this.mUser = User.getUser(bundle);
        }
        this.mHandler = (EmailProviderResponseHandler) ViewModelProviders.m76of((Fragment) this).get(EmailProviderResponseHandler.class);
        this.mHandler.init(getFlowParams());
        this.mHandler.getOperation().observe(this, new ResourceObserver<IdpResponse>(this, C1330R.C1335string.fui_progress_dialog_signing_up) {
            /* access modifiers changed from: protected */
            public void onSuccess(IdpResponse idpResponse) {
                RegisterEmailFragment registerEmailFragment = RegisterEmailFragment.this;
                registerEmailFragment.startSaveCredentials(registerEmailFragment.mHandler.getCurrentUser(), idpResponse, RegisterEmailFragment.this.mPasswordEditText.getText().toString());
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception exc) {
                if (exc instanceof FirebaseAuthWeakPasswordException) {
                    RegisterEmailFragment.this.mPasswordInput.setError(RegisterEmailFragment.this.getResources().getQuantityString(C1330R.plurals.fui_error_weak_password, C1330R.integer.fui_min_password_length));
                } else if (exc instanceof FirebaseAuthInvalidCredentialsException) {
                    RegisterEmailFragment.this.mEmailInput.setError(RegisterEmailFragment.this.getString(C1330R.C1335string.fui_invalid_email_address));
                } else if (exc instanceof FirebaseAuthAnonymousUpgradeException) {
                    RegisterEmailFragment.this.mListener.onMergeFailure(((FirebaseAuthAnonymousUpgradeException) exc).getResponse());
                } else {
                    RegisterEmailFragment.this.mEmailInput.setError(RegisterEmailFragment.this.getString(C1330R.C1335string.fui_email_account_creation_error));
                }
            }
        });
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(C1330R.layout.fui_register_email_layout, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        this.mNextButton = (Button) view.findViewById(C1330R.C1333id.button_create);
        this.mProgressBar = (ProgressBar) view.findViewById(C1330R.C1333id.top_progress_bar);
        this.mEmailEditText = (EditText) view.findViewById(C1330R.C1333id.email);
        this.mNameEditText = (EditText) view.findViewById(C1330R.C1333id.name);
        this.mPasswordEditText = (EditText) view.findViewById(C1330R.C1333id.password);
        this.mEmailInput = (TextInputLayout) view.findViewById(C1330R.C1333id.email_layout);
        this.mPasswordInput = (TextInputLayout) view.findViewById(C1330R.C1333id.password_layout);
        TextInputLayout textInputLayout = (TextInputLayout) view.findViewById(C1330R.C1333id.name_layout);
        boolean z = ProviderUtils.getConfigFromIdpsOrThrow(getFlowParams().providers, "password").getParams().getBoolean(ExtraConstants.REQUIRE_NAME, true);
        this.mPasswordFieldValidator = new PasswordFieldValidator(this.mPasswordInput, getResources().getInteger(C1330R.integer.fui_min_password_length));
        this.mNameValidator = z ? new RequiredFieldValidator(textInputLayout, getResources().getString(C1330R.C1335string.fui_missing_first_and_last_name)) : new NoOpValidator(textInputLayout);
        this.mEmailFieldValidator = new EmailFieldValidator(this.mEmailInput);
        ImeHelper.setImeOnDoneListener(this.mPasswordEditText, this);
        this.mEmailEditText.setOnFocusChangeListener(this);
        this.mNameEditText.setOnFocusChangeListener(this);
        this.mPasswordEditText.setOnFocusChangeListener(this);
        this.mNextButton.setOnClickListener(this);
        textInputLayout.setVisibility(z ? 0 : 8);
        if (VERSION.SDK_INT >= 26 && getFlowParams().enableCredentials) {
            this.mEmailEditText.setImportantForAutofill(2);
        }
        PrivacyDisclosureUtils.setupTermsOfServiceFooter(requireContext(), getFlowParams(), (TextView) view.findViewById(C1330R.C1333id.email_footer_tos_and_pp_text));
        if (bundle == null) {
            String email = this.mUser.getEmail();
            if (!TextUtils.isEmpty(email)) {
                this.mEmailEditText.setText(email);
            }
            String name = this.mUser.getName();
            if (!TextUtils.isEmpty(name)) {
                this.mNameEditText.setText(name);
            }
            if (!z || !TextUtils.isEmpty(this.mNameEditText.getText())) {
                safeRequestFocus(this.mPasswordEditText);
            } else if (!TextUtils.isEmpty(this.mEmailEditText.getText())) {
                safeRequestFocus(this.mNameEditText);
            } else {
                safeRequestFocus(this.mEmailEditText);
            }
        }
    }

    private void safeRequestFocus(final View view) {
        view.post(new Runnable() {
            public void run() {
                view.requestFocus();
            }
        });
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        FragmentActivity requireActivity = requireActivity();
        requireActivity.setTitle(C1330R.C1335string.fui_title_register_email);
        if (requireActivity instanceof AnonymousUpgradeListener) {
            this.mListener = (AnonymousUpgradeListener) requireActivity;
            return;
        }
        throw new IllegalStateException("Activity must implement CheckEmailListener");
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelable(ExtraConstants.USER, new Builder("password", this.mEmailEditText.getText().toString()).setName(this.mNameEditText.getText().toString()).setPhotoUri(this.mUser.getPhotoUri()).build());
    }

    public void onFocusChange(View view, boolean z) {
        if (!z) {
            int id = view.getId();
            if (id == C1330R.C1333id.email) {
                this.mEmailFieldValidator.validate(this.mEmailEditText.getText());
            } else if (id == C1330R.C1333id.name) {
                this.mNameValidator.validate(this.mNameEditText.getText());
            } else if (id == C1330R.C1333id.password) {
                this.mPasswordFieldValidator.validate(this.mPasswordEditText.getText());
            }
        }
    }

    public void onClick(View view) {
        if (view.getId() == C1330R.C1333id.button_create) {
            validateAndRegisterUser();
        }
    }

    public void onDonePressed() {
        validateAndRegisterUser();
    }

    public void showProgress(int i) {
        this.mNextButton.setEnabled(false);
        this.mProgressBar.setVisibility(0);
    }

    public void hideProgress() {
        this.mNextButton.setEnabled(true);
        this.mProgressBar.setVisibility(4);
    }

    private void validateAndRegisterUser() {
        String obj = this.mEmailEditText.getText().toString();
        String obj2 = this.mPasswordEditText.getText().toString();
        String obj3 = this.mNameEditText.getText().toString();
        boolean validate = this.mEmailFieldValidator.validate(obj);
        boolean validate2 = this.mPasswordFieldValidator.validate(obj2);
        boolean validate3 = this.mNameValidator.validate(obj3);
        if (validate && validate2 && validate3) {
            this.mHandler.startSignIn(new IdpResponse.Builder(new Builder("password", obj).setName(obj3).setPhotoUri(this.mUser.getPhotoUri()).build()).build(), obj2);
        }
    }
}

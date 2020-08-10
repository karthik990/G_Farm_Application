package com.firebase.p037ui.auth.p038ui.phone;

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
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p037ui.auth.C1330R;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.data.model.PhoneNumber;
import com.firebase.p037ui.auth.p038ui.FragmentBase;
import com.firebase.p037ui.auth.util.ExtraConstants;
import com.firebase.p037ui.auth.util.data.PhoneNumberUtils;
import com.firebase.p037ui.auth.util.data.PrivacyDisclosureUtils;
import com.firebase.p037ui.auth.util.p039ui.ImeHelper;
import com.firebase.p037ui.auth.util.p039ui.ImeHelper.DonePressedListener;
import com.firebase.p037ui.auth.viewmodel.ResourceObserver;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Locale;

/* renamed from: com.firebase.ui.auth.ui.phone.CheckPhoneNumberFragment */
public class CheckPhoneNumberFragment extends FragmentBase implements OnClickListener {
    public static final String TAG = "VerifyPhoneFragment";
    private boolean mCalled;
    private CheckPhoneHandler mCheckPhoneHandler;
    private CountryListSpinner mCountryListSpinner;
    private TextView mFooterText;
    private EditText mPhoneEditText;
    /* access modifiers changed from: private */
    public TextInputLayout mPhoneInputLayout;
    private ProgressBar mProgressBar;
    private TextView mSmsTermsText;
    private Button mSubmitButton;
    private PhoneNumberVerificationHandler mVerificationHandler;

    public static CheckPhoneNumberFragment newInstance(Bundle bundle) {
        CheckPhoneNumberFragment checkPhoneNumberFragment = new CheckPhoneNumberFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putBundle(ExtraConstants.PARAMS, bundle);
        checkPhoneNumberFragment.setArguments(bundle2);
        return checkPhoneNumberFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mVerificationHandler = (PhoneNumberVerificationHandler) ViewModelProviders.m78of(requireActivity()).get(PhoneNumberVerificationHandler.class);
        this.mCheckPhoneHandler = (CheckPhoneHandler) ViewModelProviders.m76of((Fragment) this).get(CheckPhoneHandler.class);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(C1330R.layout.fui_phone_layout, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        this.mProgressBar = (ProgressBar) view.findViewById(C1330R.C1333id.top_progress_bar);
        this.mSubmitButton = (Button) view.findViewById(C1330R.C1333id.send_code);
        this.mCountryListSpinner = (CountryListSpinner) view.findViewById(C1330R.C1333id.country_list);
        this.mPhoneInputLayout = (TextInputLayout) view.findViewById(C1330R.C1333id.phone_layout);
        this.mPhoneEditText = (EditText) view.findViewById(C1330R.C1333id.phone_number);
        this.mSmsTermsText = (TextView) view.findViewById(C1330R.C1333id.send_sms_tos);
        this.mFooterText = (TextView) view.findViewById(C1330R.C1333id.email_footer_tos_and_pp_text);
        this.mSmsTermsText.setText(getString(C1330R.C1335string.fui_sms_terms_of_service, getString(C1330R.C1335string.fui_verify_phone_number)));
        if (VERSION.SDK_INT >= 26 && getFlowParams().enableHints) {
            this.mPhoneEditText.setImportantForAutofill(2);
        }
        requireActivity().setTitle(getString(C1330R.C1335string.fui_verify_phone_number_title));
        ImeHelper.setImeOnDoneListener(this.mPhoneEditText, new DonePressedListener() {
            public void onDonePressed() {
                CheckPhoneNumberFragment.this.onNext();
            }
        });
        this.mSubmitButton.setOnClickListener(this);
        setupPrivacyDisclosures();
        setupCountrySpinner();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mCheckPhoneHandler.getOperation().observe(this, new ResourceObserver<PhoneNumber>(this) {
            /* access modifiers changed from: protected */
            public void onFailure(Exception exc) {
            }

            /* access modifiers changed from: protected */
            public void onSuccess(PhoneNumber phoneNumber) {
                CheckPhoneNumberFragment.this.start(phoneNumber);
            }
        });
        if (bundle == null && !this.mCalled) {
            this.mCalled = true;
            setDefaultCountryForSpinner();
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        this.mCheckPhoneHandler.onActivityResult(i, i2, intent);
    }

    public void onClick(View view) {
        onNext();
    }

    /* access modifiers changed from: private */
    public void start(PhoneNumber phoneNumber) {
        if (!PhoneNumber.isValid(phoneNumber)) {
            this.mPhoneInputLayout.setError(getString(C1330R.C1335string.fui_invalid_phone_number));
            return;
        }
        this.mPhoneEditText.setText(phoneNumber.getPhoneNumber());
        this.mPhoneEditText.setSelection(phoneNumber.getPhoneNumber().length());
        String countryIso = phoneNumber.getCountryIso();
        if (PhoneNumber.isCountryValid(phoneNumber) && this.mCountryListSpinner.isValidIso(countryIso)) {
            setCountryCode(phoneNumber);
            onNext();
        }
    }

    /* access modifiers changed from: private */
    public void onNext() {
        String pseudoValidPhoneNumber = getPseudoValidPhoneNumber();
        if (pseudoValidPhoneNumber == null) {
            this.mPhoneInputLayout.setError(getString(C1330R.C1335string.fui_invalid_phone_number));
        } else {
            this.mVerificationHandler.verifyPhoneNumber(pseudoValidPhoneNumber, false);
        }
    }

    private String getPseudoValidPhoneNumber() {
        String obj = this.mPhoneEditText.getText().toString();
        if (TextUtils.isEmpty(obj)) {
            return null;
        }
        return PhoneNumberUtils.format(obj, this.mCountryListSpinner.getSelectedCountryInfo());
    }

    private void setupPrivacyDisclosures() {
        FlowParameters flowParams = getFlowParams();
        boolean z = flowParams.isTermsOfServiceUrlProvided() && flowParams.isPrivacyPolicyUrlProvided();
        if (flowParams.shouldShowProviderChoice() || !z) {
            PrivacyDisclosureUtils.setupTermsOfServiceFooter(requireContext(), flowParams, this.mFooterText);
            this.mSmsTermsText.setText(getString(C1330R.C1335string.fui_sms_terms_of_service, getString(C1330R.C1335string.fui_verify_phone_number)));
            return;
        }
        PrivacyDisclosureUtils.setupTermsOfServiceAndPrivacyPolicySmsText(requireContext(), flowParams, this.mSmsTermsText);
    }

    private void setCountryCode(PhoneNumber phoneNumber) {
        this.mCountryListSpinner.setSelectedForCountry(new Locale("", phoneNumber.getCountryIso()), phoneNumber.getCountryCode());
    }

    private void setupCountrySpinner() {
        this.mCountryListSpinner.init(getArguments().getBundle(ExtraConstants.PARAMS));
        this.mCountryListSpinner.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CheckPhoneNumberFragment.this.mPhoneInputLayout.setError(null);
            }
        });
    }

    private void setDefaultCountryForSpinner() {
        String str;
        String str2;
        Bundle bundle = getArguments().getBundle(ExtraConstants.PARAMS);
        String str3 = null;
        if (bundle != null) {
            str3 = bundle.getString(ExtraConstants.PHONE);
            str = bundle.getString(ExtraConstants.COUNTRY_ISO);
            str2 = bundle.getString(ExtraConstants.NATIONAL_NUMBER);
        } else {
            str2 = null;
            str = null;
        }
        if (!TextUtils.isEmpty(str3)) {
            start(PhoneNumberUtils.getPhoneNumber(str3));
        } else if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            start(PhoneNumberUtils.getPhoneNumber(str, str2));
        } else if (!TextUtils.isEmpty(str)) {
            setCountryCode(new PhoneNumber("", str, String.valueOf(PhoneNumberUtils.getCountryCode(str))));
        } else if (getFlowParams().enableHints) {
            this.mCheckPhoneHandler.fetchCredential();
        }
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

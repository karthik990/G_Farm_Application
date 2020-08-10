package com.firebase.p037ui.auth.p038ui.phone;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p037ui.auth.C1330R;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.data.model.Resource;
import com.firebase.p037ui.auth.data.model.State;
import com.firebase.p037ui.auth.p038ui.FragmentBase;
import com.firebase.p037ui.auth.util.ExtraConstants;
import com.firebase.p037ui.auth.util.data.PrivacyDisclosureUtils;
import com.firebase.p037ui.auth.util.p039ui.BucketedTextChangeListener;
import com.firebase.p037ui.auth.util.p039ui.BucketedTextChangeListener.ContentChangeCallback;
import com.firebase.p037ui.auth.viewmodel.phone.PhoneProviderResponseHandler;
import java.util.concurrent.TimeUnit;

/* renamed from: com.firebase.ui.auth.ui.phone.SubmitConfirmationCodeFragment */
public class SubmitConfirmationCodeFragment extends FragmentBase {
    private static final String EXTRA_MILLIS_UNTIL_FINISHED = "millis_until_finished";
    private static final long RESEND_WAIT_MILLIS = 15000;
    public static final String TAG = "SubmitConfirmationCodeFragment";
    private static final long TICK_INTERVAL_MILLIS = 500;
    private static final int VERIFICATION_CODE_LENGTH = 6;
    /* access modifiers changed from: private */
    public SpacedEditText mConfirmationCodeEditText;
    /* access modifiers changed from: private */
    public TextView mCountDownTextView;
    /* access modifiers changed from: private */
    public final Runnable mCountdown = new Runnable() {
        public void run() {
            SubmitConfirmationCodeFragment.this.processCountdownTick();
        }
    };
    /* access modifiers changed from: private */
    public PhoneNumberVerificationHandler mHandler;
    private boolean mHasResumed;
    /* access modifiers changed from: private */
    public final Handler mLooper = new Handler();
    /* access modifiers changed from: private */
    public long mMillisUntilFinished = 15000;
    /* access modifiers changed from: private */
    public String mPhoneNumber;
    private TextView mPhoneTextView;
    private ProgressBar mProgressBar;
    /* access modifiers changed from: private */
    public TextView mResendCodeTextView;

    public static SubmitConfirmationCodeFragment newInstance(String str) {
        SubmitConfirmationCodeFragment submitConfirmationCodeFragment = new SubmitConfirmationCodeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ExtraConstants.PHONE, str);
        submitConfirmationCodeFragment.setArguments(bundle);
        return submitConfirmationCodeFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mHandler = (PhoneNumberVerificationHandler) ViewModelProviders.m78of(requireActivity()).get(PhoneNumberVerificationHandler.class);
        this.mPhoneNumber = getArguments().getString(ExtraConstants.PHONE);
        if (bundle != null) {
            this.mMillisUntilFinished = bundle.getLong(EXTRA_MILLIS_UNTIL_FINISHED);
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(C1330R.layout.fui_confirmation_code_layout, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        this.mProgressBar = (ProgressBar) view.findViewById(C1330R.C1333id.top_progress_bar);
        this.mPhoneTextView = (TextView) view.findViewById(C1330R.C1333id.edit_phone_number);
        this.mCountDownTextView = (TextView) view.findViewById(C1330R.C1333id.ticker);
        this.mResendCodeTextView = (TextView) view.findViewById(C1330R.C1333id.resend_code);
        this.mConfirmationCodeEditText = (SpacedEditText) view.findViewById(C1330R.C1333id.confirmation_code);
        requireActivity().setTitle(getString(C1330R.C1335string.fui_verify_your_phone_title));
        processCountdownTick();
        setupConfirmationCodeEditText();
        setupEditPhoneNumberTextView();
        setupResendConfirmationCodeTextView();
        PrivacyDisclosureUtils.setupTermsOfServiceFooter(requireContext(), getFlowParams(), (TextView) view.findViewById(C1330R.C1333id.email_footer_tos_and_pp_text));
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ((PhoneProviderResponseHandler) ViewModelProviders.m78of(requireActivity()).get(PhoneProviderResponseHandler.class)).getOperation().observe(this, new Observer<Resource<IdpResponse>>() {
            public void onChanged(Resource<IdpResponse> resource) {
                if (resource.getState() == State.FAILURE) {
                    SubmitConfirmationCodeFragment.this.mConfirmationCodeEditText.setText("");
                }
            }
        });
    }

    public void onStart() {
        super.onStart();
        this.mConfirmationCodeEditText.requestFocus();
        ((InputMethodManager) requireActivity().getSystemService("input_method")).showSoftInput(this.mConfirmationCodeEditText, 0);
    }

    public void onResume() {
        super.onResume();
        if (!this.mHasResumed) {
            this.mHasResumed = true;
            return;
        }
        ClipData primaryClip = ((ClipboardManager) ContextCompat.getSystemService(requireContext(), ClipboardManager.class)).getPrimaryClip();
        if (primaryClip != null && primaryClip.getItemCount() == 1) {
            CharSequence text = primaryClip.getItemAt(0).getText();
            if (text != null && text.length() == 6) {
                try {
                    Integer.parseInt(text.toString());
                    this.mConfirmationCodeEditText.setText(text);
                } catch (NumberFormatException unused) {
                }
            }
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        this.mLooper.removeCallbacks(this.mCountdown);
        bundle.putLong(EXTRA_MILLIS_UNTIL_FINISHED, this.mMillisUntilFinished);
    }

    public void onDestroy() {
        super.onDestroy();
        this.mLooper.removeCallbacks(this.mCountdown);
    }

    private void setupConfirmationCodeEditText() {
        this.mConfirmationCodeEditText.setText("------");
        SpacedEditText spacedEditText = this.mConfirmationCodeEditText;
        spacedEditText.addTextChangedListener(new BucketedTextChangeListener(spacedEditText, 6, "-", new ContentChangeCallback() {
            public void whileIncomplete() {
            }

            public void whenComplete() {
                SubmitConfirmationCodeFragment.this.submitCode();
            }
        }));
    }

    private void setupEditPhoneNumberTextView() {
        this.mPhoneTextView.setText(this.mPhoneNumber);
        this.mPhoneTextView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SubmitConfirmationCodeFragment.this.getFragmentManager().popBackStack();
            }
        });
    }

    private void setupResendConfirmationCodeTextView() {
        this.mResendCodeTextView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SubmitConfirmationCodeFragment.this.mHandler.verifyPhoneNumber(SubmitConfirmationCodeFragment.this.mPhoneNumber, true);
                SubmitConfirmationCodeFragment.this.mResendCodeTextView.setVisibility(8);
                SubmitConfirmationCodeFragment.this.mCountDownTextView.setVisibility(0);
                SubmitConfirmationCodeFragment.this.mCountDownTextView.setText(String.format(SubmitConfirmationCodeFragment.this.getString(C1330R.C1335string.fui_resend_code_in), new Object[]{Long.valueOf(15)}));
                SubmitConfirmationCodeFragment.this.mMillisUntilFinished = 15000;
                SubmitConfirmationCodeFragment.this.mLooper.postDelayed(SubmitConfirmationCodeFragment.this.mCountdown, SubmitConfirmationCodeFragment.TICK_INTERVAL_MILLIS);
            }
        });
    }

    /* access modifiers changed from: private */
    public void processCountdownTick() {
        this.mMillisUntilFinished -= TICK_INTERVAL_MILLIS;
        if (this.mMillisUntilFinished <= 0) {
            this.mCountDownTextView.setText("");
            this.mCountDownTextView.setVisibility(8);
            this.mResendCodeTextView.setVisibility(0);
            return;
        }
        this.mCountDownTextView.setText(String.format(getString(C1330R.C1335string.fui_resend_code_in), new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(this.mMillisUntilFinished) + 1)}));
        this.mLooper.postDelayed(this.mCountdown, TICK_INTERVAL_MILLIS);
    }

    /* access modifiers changed from: private */
    public void submitCode() {
        this.mHandler.submitVerificationCode(this.mPhoneNumber, this.mConfirmationCodeEditText.getUnspacedText().toString());
    }

    public void showProgress(int i) {
        this.mProgressBar.setVisibility(0);
    }

    public void hideProgress() {
        this.mProgressBar.setVisibility(4);
    }
}

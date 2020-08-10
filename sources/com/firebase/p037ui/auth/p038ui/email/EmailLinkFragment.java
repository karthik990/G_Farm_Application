package com.firebase.p037ui.auth.p038ui.email;

import android.content.Context;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p037ui.auth.C1330R;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.p038ui.InvisibleFragmentBase;
import com.firebase.p037ui.auth.util.ExtraConstants;
import com.firebase.p037ui.auth.util.data.PrivacyDisclosureUtils;
import com.firebase.p037ui.auth.util.p039ui.TextHelper;
import com.firebase.p037ui.auth.viewmodel.ResourceObserver;
import com.firebase.p037ui.auth.viewmodel.email.EmailLinkSendEmailHandler;
import com.google.firebase.auth.ActionCodeSettings;

/* renamed from: com.firebase.ui.auth.ui.email.EmailLinkFragment */
public class EmailLinkFragment extends InvisibleFragmentBase {
    private static final String EMAIL_SENT = "emailSent";
    public static final String TAG = "EmailLinkFragment";
    private EmailLinkSendEmailHandler mEmailLinkSendEmailHandler;
    /* access modifiers changed from: private */
    public boolean mEmailSent;
    /* access modifiers changed from: private */
    public TroubleSigningInListener mListener;
    /* access modifiers changed from: private */
    public ScrollView mTopLevelView;

    /* renamed from: com.firebase.ui.auth.ui.email.EmailLinkFragment$TroubleSigningInListener */
    interface TroubleSigningInListener {
        void onSendEmailFailure(Exception exc);

        void onTroubleSigningIn(String str);
    }

    public static EmailLinkFragment newInstance(String str, ActionCodeSettings actionCodeSettings) {
        return newInstance(str, actionCodeSettings, null, false);
    }

    public static EmailLinkFragment newInstance(String str, ActionCodeSettings actionCodeSettings, IdpResponse idpResponse, boolean z) {
        EmailLinkFragment emailLinkFragment = new EmailLinkFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ExtraConstants.EMAIL, str);
        bundle.putParcelable(ExtraConstants.ACTION_CODE_SETTINGS, actionCodeSettings);
        bundle.putParcelable(ExtraConstants.IDP_RESPONSE, idpResponse);
        bundle.putBoolean(ExtraConstants.FORCE_SAME_DEVICE, z);
        emailLinkFragment.setArguments(bundle);
        return emailLinkFragment;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        FragmentActivity activity = getActivity();
        if (activity instanceof TroubleSigningInListener) {
            this.mListener = (TroubleSigningInListener) activity;
            return;
        }
        throw new IllegalStateException("Activity must implement TroubleSigningInListener");
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(C1330R.layout.fui_email_link_sign_in_layout, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (bundle != null) {
            this.mEmailSent = bundle.getBoolean(EMAIL_SENT);
        }
        this.mTopLevelView = (ScrollView) view.findViewById(C1330R.C1333id.top_level_view);
        if (!this.mEmailSent) {
            this.mTopLevelView.setVisibility(8);
        }
        String string = getArguments().getString(ExtraConstants.EMAIL);
        setBodyText(view, string);
        setOnClickListeners(view, string);
        setPrivacyFooter(view);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        initHandler();
        String string = getArguments().getString(ExtraConstants.EMAIL);
        ActionCodeSettings actionCodeSettings = (ActionCodeSettings) getArguments().getParcelable(ExtraConstants.ACTION_CODE_SETTINGS);
        IdpResponse idpResponse = (IdpResponse) getArguments().getParcelable(ExtraConstants.IDP_RESPONSE);
        boolean z = getArguments().getBoolean(ExtraConstants.FORCE_SAME_DEVICE);
        if (!this.mEmailSent) {
            this.mEmailLinkSendEmailHandler.sendSignInLinkToEmail(string, actionCodeSettings, idpResponse, z);
        }
    }

    private void initHandler() {
        this.mEmailLinkSendEmailHandler = (EmailLinkSendEmailHandler) ViewModelProviders.m76of((Fragment) this).get(EmailLinkSendEmailHandler.class);
        this.mEmailLinkSendEmailHandler.init(getFlowParams());
        this.mEmailLinkSendEmailHandler.getOperation().observe(this, new ResourceObserver<String>(this, C1330R.C1335string.fui_progress_dialog_sending) {
            /* access modifiers changed from: protected */
            public void onSuccess(String str) {
                Log.w(EmailLinkFragment.TAG, "Email for email link sign in sent successfully.");
                EmailLinkFragment.this.doAfterTimeout(new Runnable() {
                    public void run() {
                        EmailLinkFragment.this.mTopLevelView.setVisibility(0);
                    }
                });
                EmailLinkFragment.this.mEmailSent = true;
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception exc) {
                EmailLinkFragment.this.mListener.onSendEmailFailure(exc);
            }
        });
    }

    private void setBodyText(View view, String str) {
        TextView textView = (TextView) view.findViewById(C1330R.C1333id.sign_in_email_sent_text);
        String string = getString(C1330R.C1335string.fui_email_link_email_sent, str);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(string);
        TextHelper.boldAllOccurencesOfText(spannableStringBuilder, string, str);
        textView.setText(spannableStringBuilder);
    }

    private void setOnClickListeners(View view, final String str) {
        view.findViewById(C1330R.C1333id.trouble_signing_in).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                EmailLinkFragment.this.mListener.onTroubleSigningIn(str);
            }
        });
    }

    private void setPrivacyFooter(View view) {
        PrivacyDisclosureUtils.setupTermsOfServiceFooter(requireContext(), getFlowParams(), (TextView) view.findViewById(C1330R.C1333id.email_footer_tos_and_pp_text));
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean(EMAIL_SENT, this.mEmailSent);
    }
}

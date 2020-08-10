package com.firebase.p037ui.auth.p038ui.email;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import com.firebase.p037ui.auth.C1330R;
import com.firebase.p037ui.auth.p038ui.FragmentBase;
import com.firebase.p037ui.auth.util.ExtraConstants;
import com.firebase.p037ui.auth.util.data.PrivacyDisclosureUtils;

/* renamed from: com.firebase.ui.auth.ui.email.TroubleSigningInFragment */
public class TroubleSigningInFragment extends FragmentBase implements OnClickListener {
    public static final String TAG = "TroubleSigningInFragment";
    private String mEmail;
    private ResendEmailListener mListener;
    private ProgressBar mProgressBar;

    /* renamed from: com.firebase.ui.auth.ui.email.TroubleSigningInFragment$ResendEmailListener */
    interface ResendEmailListener {
        void onClickResendEmail(String str);
    }

    public static TroubleSigningInFragment newInstance(String str) {
        TroubleSigningInFragment troubleSigningInFragment = new TroubleSigningInFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ExtraConstants.EMAIL, str);
        troubleSigningInFragment.setArguments(bundle);
        return troubleSigningInFragment;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(C1330R.layout.fui_email_link_trouble_signing_in_layout, viewGroup, false);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        FragmentActivity activity = getActivity();
        if (activity instanceof ResendEmailListener) {
            this.mListener = (ResendEmailListener) activity;
            return;
        }
        throw new IllegalStateException("Activity must implement ResendEmailListener");
    }

    public void onViewCreated(View view, Bundle bundle) {
        this.mProgressBar = (ProgressBar) view.findViewById(C1330R.C1333id.top_progress_bar);
        this.mEmail = getArguments().getString(ExtraConstants.EMAIL);
        setOnClickListeners(view);
        setPrivacyFooter(view);
    }

    private void setOnClickListeners(View view) {
        view.findViewById(C1330R.C1333id.button_resend_email).setOnClickListener(this);
    }

    private void setPrivacyFooter(View view) {
        PrivacyDisclosureUtils.setupTermsOfServiceFooter(requireContext(), getFlowParams(), (TextView) view.findViewById(C1330R.C1333id.email_footer_tos_and_pp_text));
    }

    public void onClick(View view) {
        if (view.getId() == C1330R.C1333id.button_resend_email) {
            this.mListener.onClickResendEmail(this.mEmail);
        }
    }

    public void showProgress(int i) {
        this.mProgressBar.setVisibility(0);
    }

    public void hideProgress() {
        this.mProgressBar.setVisibility(4);
    }
}

package com.firebase.p037ui.auth.p038ui.email;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import com.firebase.p037ui.auth.C1330R;
import com.firebase.p037ui.auth.p038ui.FragmentBase;
import com.firebase.p037ui.auth.util.data.EmailLinkParser;
import com.firebase.p037ui.auth.util.data.PrivacyDisclosureUtils;
import com.firebase.p037ui.auth.util.data.ProviderUtils;
import com.firebase.p037ui.auth.util.p039ui.TextHelper;

/* renamed from: com.firebase.ui.auth.ui.email.EmailLinkCrossDeviceLinkingFragment */
public class EmailLinkCrossDeviceLinkingFragment extends FragmentBase implements OnClickListener {
    public static final String TAG = "CrossDeviceFragment";
    private Button mContinueButton;
    private FinishEmailLinkSignInListener mListener;
    private ProgressBar mProgressBar;

    /* renamed from: com.firebase.ui.auth.ui.email.EmailLinkCrossDeviceLinkingFragment$FinishEmailLinkSignInListener */
    interface FinishEmailLinkSignInListener {
        void completeCrossDeviceEmailLinkFlow();
    }

    public static EmailLinkCrossDeviceLinkingFragment newInstance() {
        return new EmailLinkCrossDeviceLinkingFragment();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(C1330R.layout.fui_email_link_cross_device_linking, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        this.mProgressBar = (ProgressBar) view.findViewById(C1330R.C1333id.top_progress_bar);
        this.mContinueButton = (Button) view.findViewById(C1330R.C1333id.button_continue);
        this.mContinueButton.setOnClickListener(this);
        String providerIdToProviderName = ProviderUtils.providerIdToProviderName(new EmailLinkParser(getFlowParams().emailLink).getProviderId());
        TextView textView = (TextView) view.findViewById(C1330R.C1333id.cross_device_linking_body);
        String string = getString(C1330R.C1335string.fui_email_link_cross_device_linking_text, providerIdToProviderName);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(string);
        TextHelper.boldAllOccurencesOfText(spannableStringBuilder, string, providerIdToProviderName);
        textView.setText(spannableStringBuilder);
        if (VERSION.SDK_INT >= 26) {
            textView.setJustificationMode(1);
        }
        PrivacyDisclosureUtils.setupTermsOfServiceFooter(requireContext(), getFlowParams(), (TextView) view.findViewById(C1330R.C1333id.email_footer_tos_and_pp_text));
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        FragmentActivity activity = getActivity();
        if (activity instanceof FinishEmailLinkSignInListener) {
            this.mListener = (FinishEmailLinkSignInListener) activity;
            return;
        }
        throw new IllegalStateException("Activity must implement EmailLinkPromptEmailListener");
    }

    public void onClick(View view) {
        if (view.getId() == C1330R.C1333id.button_continue) {
            this.mListener.completeCrossDeviceEmailLinkFlow();
        }
    }

    public void showProgress(int i) {
        this.mProgressBar.setVisibility(0);
    }

    public void hideProgress() {
        this.mProgressBar.setVisibility(4);
    }
}

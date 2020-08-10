package com.firebase.p037ui.auth.util.data;

import android.content.Context;
import android.widget.TextView;
import com.firebase.p037ui.auth.C1330R;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.util.p039ui.PreambleHandler;

/* renamed from: com.firebase.ui.auth.util.data.PrivacyDisclosureUtils */
public class PrivacyDisclosureUtils {
    private static final int NO_TOS_OR_PP = -1;

    public static void setupTermsOfServiceAndPrivacyPolicyText(Context context, FlowParameters flowParameters, TextView textView) {
        PreambleHandler.setup(context, flowParameters, getGlobalTermsStringResource(flowParameters), textView);
    }

    public static void setupTermsOfServiceFooter(Context context, FlowParameters flowParameters, TextView textView) {
        PreambleHandler.setup(context, flowParameters, getGlobalTermsFooterStringResource(flowParameters), textView);
    }

    public static void setupTermsOfServiceAndPrivacyPolicySmsText(Context context, FlowParameters flowParameters, TextView textView) {
        PreambleHandler.setup(context, flowParameters, C1330R.C1335string.fui_verify_phone_number, getTermsSmsStringResource(flowParameters), textView);
    }

    private static int getGlobalTermsStringResource(FlowParameters flowParameters) {
        boolean isTermsOfServiceUrlProvided = flowParameters.isTermsOfServiceUrlProvided();
        boolean isPrivacyPolicyUrlProvided = flowParameters.isPrivacyPolicyUrlProvided();
        if (!isTermsOfServiceUrlProvided || !isPrivacyPolicyUrlProvided) {
            return -1;
        }
        return C1330R.C1335string.fui_tos_and_pp;
    }

    private static int getGlobalTermsFooterStringResource(FlowParameters flowParameters) {
        boolean isTermsOfServiceUrlProvided = flowParameters.isTermsOfServiceUrlProvided();
        boolean isPrivacyPolicyUrlProvided = flowParameters.isPrivacyPolicyUrlProvided();
        if (!isTermsOfServiceUrlProvided || !isPrivacyPolicyUrlProvided) {
            return -1;
        }
        return C1330R.C1335string.fui_tos_and_pp_footer;
    }

    private static int getTermsSmsStringResource(FlowParameters flowParameters) {
        boolean isTermsOfServiceUrlProvided = flowParameters.isTermsOfServiceUrlProvided();
        boolean isPrivacyPolicyUrlProvided = flowParameters.isPrivacyPolicyUrlProvided();
        if (!isTermsOfServiceUrlProvided || !isPrivacyPolicyUrlProvided) {
            return -1;
        }
        return C1330R.C1335string.fui_sms_terms_of_service_and_privacy_policy_extended;
    }
}

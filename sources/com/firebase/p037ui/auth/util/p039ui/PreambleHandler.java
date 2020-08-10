package com.firebase.p037ui.auth.util.p039ui;

import android.content.Context;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsIntent.Builder;
import androidx.core.content.ContextCompat;
import com.firebase.p037ui.auth.C1330R;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import java.lang.ref.WeakReference;

/* renamed from: com.firebase.ui.auth.util.ui.PreambleHandler */
public class PreambleHandler {
    private static final String BTN_TARGET = "%BTN%";
    private static final int NO_BUTTON = -1;
    private static final String PP_TARGET = "%PP%";
    private static final String TOS_TARGET = "%TOS%";
    private SpannableStringBuilder mBuilder;
    private final int mButtonText;
    private final Context mContext;
    private final FlowParameters mFlowParameters;
    private final ForegroundColorSpan mLinkSpan = new ForegroundColorSpan(ContextCompat.getColor(this.mContext, C1330R.C1331color.fui_linkColor));

    /* renamed from: com.firebase.ui.auth.util.ui.PreambleHandler$CustomTabsSpan */
    private static final class CustomTabsSpan extends ClickableSpan {
        private final WeakReference<Context> mContext;
        private final CustomTabsIntent mCustomTabsIntent;
        private final String mUrl;

        public CustomTabsSpan(Context context, String str) {
            this.mContext = new WeakReference<>(context);
            this.mUrl = str;
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(C1330R.attr.colorPrimary, typedValue, true);
            this.mCustomTabsIntent = new Builder().setToolbarColor(typedValue.data).setShowTitle(true).build();
        }

        public void onClick(View view) {
            Context context = (Context) this.mContext.get();
            if (context != null) {
                this.mCustomTabsIntent.launchUrl(context, Uri.parse(this.mUrl));
            }
        }
    }

    private PreambleHandler(Context context, FlowParameters flowParameters, int i) {
        this.mContext = context;
        this.mFlowParameters = flowParameters;
        this.mButtonText = i;
    }

    public static void setup(Context context, FlowParameters flowParameters, int i, TextView textView) {
        setup(context, flowParameters, -1, i, textView);
    }

    public static void setup(Context context, FlowParameters flowParameters, int i, int i2, TextView textView) {
        PreambleHandler preambleHandler = new PreambleHandler(context, flowParameters, i);
        preambleHandler.initPreamble(i2);
        preambleHandler.setPreamble(textView);
    }

    private void setPreamble(TextView textView) {
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(this.mBuilder);
    }

    private void initPreamble(int i) {
        String preambleStringWithTargets = getPreambleStringWithTargets(i, this.mButtonText != -1);
        if (preambleStringWithTargets != null) {
            this.mBuilder = new SpannableStringBuilder(preambleStringWithTargets);
            replaceTarget(BTN_TARGET, this.mButtonText);
            replaceUrlTarget(TOS_TARGET, C1330R.C1335string.fui_terms_of_service, this.mFlowParameters.termsOfServiceUrl);
            replaceUrlTarget(PP_TARGET, C1330R.C1335string.fui_privacy_policy, this.mFlowParameters.privacyPolicyUrl);
        }
    }

    private void replaceTarget(String str, int i) {
        int indexOf = this.mBuilder.toString().indexOf(str);
        if (indexOf != -1) {
            this.mBuilder.replace(indexOf, str.length() + indexOf, this.mContext.getString(i));
        }
    }

    private void replaceUrlTarget(String str, int i, String str2) {
        int indexOf = this.mBuilder.toString().indexOf(str);
        if (indexOf != -1) {
            String string = this.mContext.getString(i);
            this.mBuilder.replace(indexOf, str.length() + indexOf, string);
            int length = string.length() + indexOf;
            this.mBuilder.setSpan(this.mLinkSpan, indexOf, length, 0);
            this.mBuilder.setSpan(new CustomTabsSpan(this.mContext, str2), indexOf, length, 0);
        }
    }

    private String getPreambleStringWithTargets(int i, boolean z) {
        boolean z2 = !TextUtils.isEmpty(this.mFlowParameters.privacyPolicyUrl);
        if (!(!TextUtils.isEmpty(this.mFlowParameters.termsOfServiceUrl)) || !z2) {
            return null;
        }
        String str = PP_TARGET;
        String str2 = TOS_TARGET;
        return this.mContext.getString(i, z ? new Object[]{BTN_TARGET, str2, str} : new Object[]{str2, str});
    }
}

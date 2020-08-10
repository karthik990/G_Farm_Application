package com.firebase.p037ui.auth.p038ui.idp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p037ui.auth.AuthMethodPickerLayout;
import com.firebase.p037ui.auth.AuthUI;
import com.firebase.p037ui.auth.AuthUI.IdpConfig;
import com.firebase.p037ui.auth.C1330R;
import com.firebase.p037ui.auth.FirebaseAuthAnonymousUpgradeException;
import com.firebase.p037ui.auth.FirebaseUiException;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.data.model.UserCancellationException;
import com.firebase.p037ui.auth.p038ui.AppCompatBase;
import com.firebase.p037ui.auth.util.data.PrivacyDisclosureUtils;
import com.firebase.p037ui.auth.viewmodel.ProviderSignInBase;
import com.firebase.p037ui.auth.viewmodel.ResourceObserver;
import com.firebase.p037ui.auth.viewmodel.idp.SocialProviderResponseHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* renamed from: com.firebase.ui.auth.ui.idp.AuthMethodPickerActivity */
public class AuthMethodPickerActivity extends AppCompatBase {
    private AuthMethodPickerLayout customLayout;
    /* access modifiers changed from: private */
    public SocialProviderResponseHandler mHandler;
    private ProgressBar mProgressBar;
    private ViewGroup mProviderHolder;
    private List<ProviderSignInBase<?>> mProviders;

    public static Intent createIntent(Context context, FlowParameters flowParameters) {
        return createBaseIntent(context, AuthMethodPickerActivity.class, flowParameters);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        int i;
        super.onCreate(bundle);
        FlowParameters flowParams = getFlowParams();
        this.customLayout = flowParams.authMethodPickerLayout;
        this.mHandler = (SocialProviderResponseHandler) ViewModelProviders.m78of((FragmentActivity) this).get(SocialProviderResponseHandler.class);
        this.mHandler.init(flowParams);
        this.mProviders = new ArrayList();
        AuthMethodPickerLayout authMethodPickerLayout = this.customLayout;
        if (authMethodPickerLayout != null) {
            setContentView(authMethodPickerLayout.getMainLayout());
            populateIdpListCustomLayout(flowParams.providers);
        } else {
            setContentView(C1330R.layout.fui_auth_method_picker_layout);
            this.mProgressBar = (ProgressBar) findViewById(C1330R.C1333id.top_progress_bar);
            this.mProviderHolder = (ViewGroup) findViewById(C1330R.C1333id.btn_holder);
            populateIdpList(flowParams.providers);
            int i2 = flowParams.logoId;
            if (i2 == -1) {
                findViewById(C1330R.C1333id.logo).setVisibility(8);
                ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(C1330R.C1333id.root);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);
                constraintSet.setHorizontalBias(C1330R.C1333id.container, 0.5f);
                constraintSet.setVerticalBias(C1330R.C1333id.container, 0.5f);
                constraintSet.applyTo(constraintLayout);
            } else {
                ((ImageView) findViewById(C1330R.C1333id.logo)).setImageResource(i2);
            }
        }
        boolean z = getFlowParams().isPrivacyPolicyUrlProvided() && getFlowParams().isTermsOfServiceUrlProvided();
        AuthMethodPickerLayout authMethodPickerLayout2 = this.customLayout;
        if (authMethodPickerLayout2 == null) {
            i = C1330R.C1333id.main_tos_and_pp;
        } else {
            i = authMethodPickerLayout2.getTosPpView();
        }
        if (i >= 0) {
            TextView textView = (TextView) findViewById(i);
            if (!z) {
                textView.setVisibility(8);
            } else {
                PrivacyDisclosureUtils.setupTermsOfServiceAndPrivacyPolicyText(this, getFlowParams(), textView);
            }
        }
        this.mHandler.getOperation().observe(this, new ResourceObserver<IdpResponse>(this, C1330R.C1335string.fui_progress_dialog_signing_in) {
            /* access modifiers changed from: protected */
            public void onSuccess(IdpResponse idpResponse) {
                AuthMethodPickerActivity authMethodPickerActivity = AuthMethodPickerActivity.this;
                authMethodPickerActivity.startSaveCredentials(authMethodPickerActivity.mHandler.getCurrentUser(), idpResponse, null);
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception exc) {
                String str;
                if (exc instanceof FirebaseAuthAnonymousUpgradeException) {
                    AuthMethodPickerActivity.this.finish(5, ((FirebaseAuthAnonymousUpgradeException) exc).getResponse().toIntent());
                } else if (!(exc instanceof UserCancellationException)) {
                    if (exc instanceof FirebaseUiException) {
                        str = exc.getMessage();
                    } else {
                        str = AuthMethodPickerActivity.this.getString(C1330R.C1335string.fui_error_unknown);
                    }
                    Toast.makeText(AuthMethodPickerActivity.this, str, 0).show();
                }
            }
        });
    }

    private void populateIdpList(List<IdpConfig> list) {
        int i;
        ViewModelProviders.m78of((FragmentActivity) this);
        this.mProviders = new ArrayList();
        for (IdpConfig idpConfig : list) {
            String providerId = idpConfig.getProviderId();
            char c = 65535;
            switch (providerId.hashCode()) {
                case -2095811475:
                    if (providerId.equals(AuthUI.ANONYMOUS_PROVIDER)) {
                        c = 7;
                        break;
                    }
                    break;
                case -1830313082:
                    if (providerId.equals("twitter.com")) {
                        c = 2;
                        break;
                    }
                    break;
                case -1536293812:
                    if (providerId.equals("google.com")) {
                        c = 0;
                        break;
                    }
                    break;
                case -364826023:
                    if (providerId.equals("facebook.com")) {
                        c = 1;
                        break;
                    }
                    break;
                case 106642798:
                    if (providerId.equals("phone")) {
                        c = 6;
                        break;
                    }
                    break;
                case 1216985755:
                    if (providerId.equals("password")) {
                        c = 5;
                        break;
                    }
                    break;
                case 1985010934:
                    if (providerId.equals("github.com")) {
                        c = 3;
                        break;
                    }
                    break;
                case 2120171958:
                    if (providerId.equals("emailLink")) {
                        c = 4;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    i = C1330R.layout.fui_idp_button_google;
                    break;
                case 1:
                    i = C1330R.layout.fui_idp_button_facebook;
                    break;
                case 2:
                    i = C1330R.layout.fui_idp_button_twitter;
                    break;
                case 3:
                    i = C1330R.layout.fui_idp_button_github;
                    break;
                case 4:
                case 5:
                    i = C1330R.layout.fui_provider_button_email;
                    break;
                case 6:
                    i = C1330R.layout.fui_provider_button_phone;
                    break;
                case 7:
                    i = C1330R.layout.fui_provider_button_anonymous;
                    break;
                default:
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unknown provider: ");
                    sb.append(providerId);
                    throw new IllegalStateException(sb.toString());
            }
            View inflate = getLayoutInflater().inflate(i, this.mProviderHolder, false);
            handleSignInOperation(idpConfig, inflate);
            this.mProviderHolder.addView(inflate);
        }
    }

    private void populateIdpListCustomLayout(List<IdpConfig> list) {
        Map providersButton = this.customLayout.getProvidersButton();
        for (IdpConfig idpConfig : list) {
            String providerId = idpConfig.getProviderId();
            if (providersButton.containsKey(providerId)) {
                handleSignInOperation(idpConfig, findViewById(((Integer) providersButton.get(providerId)).intValue()));
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("No button found for auth provider: ");
                sb.append(providerId);
                throw new IllegalStateException(sb.toString());
            }
        }
    }

    /* JADX WARNING: type inference failed for: r0v1, types: [java.lang.Object, com.firebase.ui.auth.viewmodel.ProviderSignInBase] */
    /* JADX WARNING: type inference failed for: r5v4 */
    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: type inference failed for: r0v13 */
    /* JADX WARNING: type inference failed for: r0v14 */
    /* JADX WARNING: type inference failed for: r5v15 */
    /* JADX WARNING: type inference failed for: r0v15 */
    /* JADX WARNING: type inference failed for: r5v16 */
    /* JADX WARNING: type inference failed for: r0v16 */
    /* JADX WARNING: type inference failed for: r5v17 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void handleSignInOperation(com.firebase.p037ui.auth.AuthUI.IdpConfig r5, android.view.View r6) {
        /*
            r4 = this;
            androidx.lifecycle.ViewModelProvider r0 = androidx.lifecycle.ViewModelProviders.m78of(r4)
            java.lang.String r1 = r5.getProviderId()
            int r2 = r1.hashCode()
            switch(r2) {
                case -2095811475: goto L_0x0057;
                case -1830313082: goto L_0x004c;
                case -1536293812: goto L_0x0042;
                case -364826023: goto L_0x0038;
                case 106642798: goto L_0x002e;
                case 1216985755: goto L_0x0024;
                case 1985010934: goto L_0x001a;
                case 2120171958: goto L_0x0010;
                default: goto L_0x000f;
            }
        L_0x000f:
            goto L_0x0061
        L_0x0010:
            java.lang.String r2 = "emailLink"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0061
            r2 = 4
            goto L_0x0062
        L_0x001a:
            java.lang.String r2 = "github.com"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0061
            r2 = 3
            goto L_0x0062
        L_0x0024:
            java.lang.String r2 = "password"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0061
            r2 = 5
            goto L_0x0062
        L_0x002e:
            java.lang.String r2 = "phone"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0061
            r2 = 6
            goto L_0x0062
        L_0x0038:
            java.lang.String r2 = "facebook.com"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0061
            r2 = 1
            goto L_0x0062
        L_0x0042:
            java.lang.String r2 = "google.com"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0061
            r2 = 0
            goto L_0x0062
        L_0x004c:
            java.lang.String r2 = "twitter.com"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0061
            r2 = 2
            goto L_0x0062
        L_0x0057:
            java.lang.String r2 = "anonymous"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0061
            r2 = 7
            goto L_0x0062
        L_0x0061:
            r2 = -1
        L_0x0062:
            r3 = 0
            switch(r2) {
                case 0: goto L_0x00ca;
                case 1: goto L_0x00be;
                case 2: goto L_0x00b1;
                case 3: goto L_0x00a5;
                case 4: goto L_0x0099;
                case 5: goto L_0x0099;
                case 6: goto L_0x008d;
                case 7: goto L_0x007d;
                default: goto L_0x0066;
            }
        L_0x0066:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r0 = "Unknown provider: "
            r6.append(r0)
            r6.append(r1)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6)
            throw r5
        L_0x007d:
            java.lang.Class<com.firebase.ui.auth.data.remote.AnonymousSignInHandler> r5 = com.firebase.p037ui.auth.data.remote.AnonymousSignInHandler.class
            androidx.lifecycle.ViewModel r5 = r0.get(r5)
            com.firebase.ui.auth.data.remote.AnonymousSignInHandler r5 = (com.firebase.p037ui.auth.data.remote.AnonymousSignInHandler) r5
            com.firebase.ui.auth.data.model.FlowParameters r0 = r4.getFlowParams()
            r5.init(r0)
            goto L_0x00bc
        L_0x008d:
            java.lang.Class<com.firebase.ui.auth.data.remote.PhoneSignInHandler> r2 = com.firebase.p037ui.auth.data.remote.PhoneSignInHandler.class
            androidx.lifecycle.ViewModel r0 = r0.get(r2)
            com.firebase.ui.auth.data.remote.PhoneSignInHandler r0 = (com.firebase.p037ui.auth.data.remote.PhoneSignInHandler) r0
            r0.init(r5)
            goto L_0x00da
        L_0x0099:
            java.lang.Class<com.firebase.ui.auth.data.remote.EmailSignInHandler> r5 = com.firebase.p037ui.auth.data.remote.EmailSignInHandler.class
            androidx.lifecycle.ViewModel r5 = r0.get(r5)
            com.firebase.ui.auth.data.remote.EmailSignInHandler r5 = (com.firebase.p037ui.auth.data.remote.EmailSignInHandler) r5
            r5.init(r3)
            goto L_0x00bc
        L_0x00a5:
            java.lang.Class<com.firebase.ui.auth.viewmodel.ProviderSignInBase<com.firebase.ui.auth.AuthUI$IdpConfig>> r2 = com.firebase.p037ui.auth.data.remote.GitHubSignInHandlerBridge.HANDLER_CLASS
            androidx.lifecycle.ViewModel r0 = r0.get(r2)
            com.firebase.ui.auth.viewmodel.ProviderSignInBase r0 = (com.firebase.p037ui.auth.viewmodel.ProviderSignInBase) r0
            r0.init(r5)
            goto L_0x00da
        L_0x00b1:
            java.lang.Class<com.firebase.ui.auth.data.remote.TwitterSignInHandler> r5 = com.firebase.p037ui.auth.data.remote.TwitterSignInHandler.class
            androidx.lifecycle.ViewModel r5 = r0.get(r5)
            com.firebase.ui.auth.data.remote.TwitterSignInHandler r5 = (com.firebase.p037ui.auth.data.remote.TwitterSignInHandler) r5
            r5.init(r3)
        L_0x00bc:
            r0 = r5
            goto L_0x00da
        L_0x00be:
            java.lang.Class<com.firebase.ui.auth.data.remote.FacebookSignInHandler> r2 = com.firebase.p037ui.auth.data.remote.FacebookSignInHandler.class
            androidx.lifecycle.ViewModel r0 = r0.get(r2)
            com.firebase.ui.auth.data.remote.FacebookSignInHandler r0 = (com.firebase.p037ui.auth.data.remote.FacebookSignInHandler) r0
            r0.init(r5)
            goto L_0x00da
        L_0x00ca:
            java.lang.Class<com.firebase.ui.auth.data.remote.GoogleSignInHandler> r2 = com.firebase.p037ui.auth.data.remote.GoogleSignInHandler.class
            androidx.lifecycle.ViewModel r0 = r0.get(r2)
            com.firebase.ui.auth.data.remote.GoogleSignInHandler r0 = (com.firebase.p037ui.auth.data.remote.GoogleSignInHandler) r0
            com.firebase.ui.auth.data.remote.GoogleSignInHandler$Params r2 = new com.firebase.ui.auth.data.remote.GoogleSignInHandler$Params
            r2.<init>(r5)
            r0.init(r2)
        L_0x00da:
            java.util.List<com.firebase.ui.auth.viewmodel.ProviderSignInBase<?>> r5 = r4.mProviders
            r5.add(r0)
            androidx.lifecycle.LiveData r5 = r0.getOperation()
            com.firebase.ui.auth.ui.idp.AuthMethodPickerActivity$2 r2 = new com.firebase.ui.auth.ui.idp.AuthMethodPickerActivity$2
            r2.<init>(r4, r1)
            r5.observe(r4, r2)
            com.firebase.ui.auth.ui.idp.AuthMethodPickerActivity$3 r5 = new com.firebase.ui.auth.ui.idp.AuthMethodPickerActivity$3
            r5.<init>(r0)
            r6.setOnClickListener(r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.firebase.p037ui.auth.p038ui.idp.AuthMethodPickerActivity.handleSignInOperation(com.firebase.ui.auth.AuthUI$IdpConfig, android.view.View):void");
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        this.mHandler.onActivityResult(i, i2, intent);
        for (ProviderSignInBase onActivityResult : this.mProviders) {
            onActivityResult.onActivityResult(i, i2, intent);
        }
    }

    public void showProgress(int i) {
        if (this.customLayout == null) {
            this.mProgressBar.setVisibility(0);
            for (int i2 = 0; i2 < this.mProviderHolder.getChildCount(); i2++) {
                View childAt = this.mProviderHolder.getChildAt(i2);
                childAt.setEnabled(false);
                childAt.setAlpha(0.75f);
            }
        }
    }

    public void hideProgress() {
        if (this.customLayout == null) {
            this.mProgressBar.setVisibility(4);
            for (int i = 0; i < this.mProviderHolder.getChildCount(); i++) {
                View childAt = this.mProviderHolder.getChildAt(i);
                childAt.setEnabled(true);
                childAt.setAlpha(1.0f);
            }
        }
    }
}

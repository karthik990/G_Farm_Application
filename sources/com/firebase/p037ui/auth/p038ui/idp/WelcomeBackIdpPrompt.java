package com.firebase.p037ui.auth.p038ui.idp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p037ui.auth.AuthUI.IdpConfig;
import com.firebase.p037ui.auth.C1330R;
import com.firebase.p037ui.auth.FirebaseAuthAnonymousUpgradeException;
import com.firebase.p037ui.auth.FirebaseUiException;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.data.model.FlowParameters;
import com.firebase.p037ui.auth.data.model.User;
import com.firebase.p037ui.auth.data.remote.FacebookSignInHandler;
import com.firebase.p037ui.auth.data.remote.GitHubSignInHandlerBridge;
import com.firebase.p037ui.auth.data.remote.GoogleSignInHandler;
import com.firebase.p037ui.auth.data.remote.GoogleSignInHandler.Params;
import com.firebase.p037ui.auth.data.remote.TwitterSignInHandler;
import com.firebase.p037ui.auth.p038ui.AppCompatBase;
import com.firebase.p037ui.auth.util.ExtraConstants;
import com.firebase.p037ui.auth.util.data.PrivacyDisclosureUtils;
import com.firebase.p037ui.auth.util.data.ProviderUtils;
import com.firebase.p037ui.auth.viewmodel.ProviderSignInBase;
import com.firebase.p037ui.auth.viewmodel.ResourceObserver;
import com.firebase.p037ui.auth.viewmodel.idp.LinkingSocialProviderResponseHandler;

/* renamed from: com.firebase.ui.auth.ui.idp.WelcomeBackIdpPrompt */
public class WelcomeBackIdpPrompt extends AppCompatBase {
    private Button mDoneButton;
    private ProgressBar mProgressBar;
    /* access modifiers changed from: private */
    public ProviderSignInBase<?> mProvider;

    public static Intent createIntent(Context context, FlowParameters flowParameters, User user) {
        return createIntent(context, flowParameters, user, null);
    }

    public static Intent createIntent(Context context, FlowParameters flowParameters, User user, IdpResponse idpResponse) {
        return createBaseIntent(context, WelcomeBackIdpPrompt.class, flowParameters).putExtra(ExtraConstants.IDP_RESPONSE, idpResponse).putExtra(ExtraConstants.USER, user);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        int i;
        super.onCreate(bundle);
        setContentView(C1330R.layout.fui_welcome_back_idp_prompt_layout);
        this.mDoneButton = (Button) findViewById(C1330R.C1333id.welcome_back_idp_button);
        this.mProgressBar = (ProgressBar) findViewById(C1330R.C1333id.top_progress_bar);
        User user = User.getUser(getIntent());
        IdpResponse fromResultIntent = IdpResponse.fromResultIntent(getIntent());
        ViewModelProvider of = ViewModelProviders.m78of((FragmentActivity) this);
        final LinkingSocialProviderResponseHandler linkingSocialProviderResponseHandler = (LinkingSocialProviderResponseHandler) of.get(LinkingSocialProviderResponseHandler.class);
        linkingSocialProviderResponseHandler.init(getFlowParams());
        if (fromResultIntent != null) {
            linkingSocialProviderResponseHandler.setRequestedSignInCredentialForEmail(ProviderUtils.getAuthCredential(fromResultIntent), user.getEmail());
        }
        String providerId = user.getProviderId();
        IdpConfig configFromIdps = ProviderUtils.getConfigFromIdps(getFlowParams().providers, providerId);
        if (configFromIdps == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Firebase login unsuccessful. Account linking failed due to provider not enabled by application: ");
            sb.append(providerId);
            finish(0, IdpResponse.getErrorIntent(new FirebaseUiException(3, sb.toString())));
            return;
        }
        char c = 65535;
        switch (providerId.hashCode()) {
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
            case 1985010934:
                if (providerId.equals("github.com")) {
                    c = 3;
                    break;
                }
                break;
        }
        if (c == 0) {
            GoogleSignInHandler googleSignInHandler = (GoogleSignInHandler) of.get(GoogleSignInHandler.class);
            googleSignInHandler.init(new Params(configFromIdps, user.getEmail()));
            this.mProvider = googleSignInHandler;
            i = C1330R.C1335string.fui_idp_name_google;
        } else if (c == 1) {
            FacebookSignInHandler facebookSignInHandler = (FacebookSignInHandler) of.get(FacebookSignInHandler.class);
            facebookSignInHandler.init(configFromIdps);
            this.mProvider = facebookSignInHandler;
            i = C1330R.C1335string.fui_idp_name_facebook;
        } else if (c == 2) {
            TwitterSignInHandler twitterSignInHandler = (TwitterSignInHandler) of.get(TwitterSignInHandler.class);
            twitterSignInHandler.init(null);
            this.mProvider = twitterSignInHandler;
            i = C1330R.C1335string.fui_idp_name_twitter;
        } else if (c == 3) {
            ProviderSignInBase<?> providerSignInBase = (ProviderSignInBase) of.get(GitHubSignInHandlerBridge.HANDLER_CLASS);
            providerSignInBase.init(configFromIdps);
            this.mProvider = providerSignInBase;
            i = C1330R.C1335string.fui_idp_name_github;
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Invalid provider id: ");
            sb2.append(providerId);
            throw new IllegalStateException(sb2.toString());
        }
        this.mProvider.getOperation().observe(this, new ResourceObserver<IdpResponse>(this) {
            /* access modifiers changed from: protected */
            public void onSuccess(IdpResponse idpResponse) {
                linkingSocialProviderResponseHandler.startSignIn(idpResponse);
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception exc) {
                linkingSocialProviderResponseHandler.startSignIn(IdpResponse.from(exc));
            }
        });
        ((TextView) findViewById(C1330R.C1333id.welcome_back_idp_prompt)).setText(getString(C1330R.C1335string.fui_welcome_back_idp_prompt, new Object[]{user.getEmail(), getString(i)}));
        this.mDoneButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                WelcomeBackIdpPrompt.this.mProvider.startSignIn(WelcomeBackIdpPrompt.this);
            }
        });
        linkingSocialProviderResponseHandler.getOperation().observe(this, new ResourceObserver<IdpResponse>(this) {
            /* access modifiers changed from: protected */
            public void onSuccess(IdpResponse idpResponse) {
                WelcomeBackIdpPrompt.this.finish(-1, idpResponse.toIntent());
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception exc) {
                if (exc instanceof FirebaseAuthAnonymousUpgradeException) {
                    WelcomeBackIdpPrompt.this.finish(5, ((FirebaseAuthAnonymousUpgradeException) exc).getResponse().toIntent());
                    return;
                }
                WelcomeBackIdpPrompt.this.finish(0, IdpResponse.getErrorIntent(exc));
            }
        });
        PrivacyDisclosureUtils.setupTermsOfServiceFooter(this, getFlowParams(), (TextView) findViewById(C1330R.C1333id.email_footer_tos_and_pp_text));
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        this.mProvider.onActivityResult(i, i2, intent);
    }

    public void showProgress(int i) {
        this.mDoneButton.setEnabled(false);
        this.mProgressBar.setVisibility(0);
    }

    public void hideProgress() {
        this.mDoneButton.setEnabled(true);
        this.mProgressBar.setVisibility(4);
    }
}

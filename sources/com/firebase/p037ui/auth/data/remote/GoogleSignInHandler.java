package com.firebase.p037ui.auth.data.remote;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.firebase.p037ui.auth.AuthUI.IdpConfig;
import com.firebase.p037ui.auth.FirebaseUiException;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.IdpResponse.Builder;
import com.firebase.p037ui.auth.data.model.IntentRequiredException;
import com.firebase.p037ui.auth.data.model.Resource;
import com.firebase.p037ui.auth.data.model.User;
import com.firebase.p037ui.auth.data.model.UserCancellationException;
import com.firebase.p037ui.auth.p038ui.HelperActivityBase;
import com.firebase.p037ui.auth.util.ExtraConstants;
import com.firebase.p037ui.auth.viewmodel.ProviderSignInBase;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;

/* renamed from: com.firebase.ui.auth.data.remote.GoogleSignInHandler */
public class GoogleSignInHandler extends ProviderSignInBase<Params> {
    private static final String TAG = "GoogleSignInHandler";
    private IdpConfig mConfig;
    private String mEmail;

    /* renamed from: com.firebase.ui.auth.data.remote.GoogleSignInHandler$Params */
    public static final class Params {
        /* access modifiers changed from: private */
        public final IdpConfig config;
        /* access modifiers changed from: private */
        public final String email;

        public Params(IdpConfig idpConfig) {
            this(idpConfig, null);
        }

        public Params(IdpConfig idpConfig, String str) {
            this.config = idpConfig;
            this.email = str;
        }
    }

    public GoogleSignInHandler(Application application) {
        super(application);
    }

    private static IdpResponse createIdpResponse(GoogleSignInAccount googleSignInAccount) {
        return new Builder(new User.Builder("google.com", googleSignInAccount.getEmail()).setName(googleSignInAccount.getDisplayName()).setPhotoUri(googleSignInAccount.getPhotoUrl()).build()).setToken(googleSignInAccount.getIdToken()).build();
    }

    /* access modifiers changed from: protected */
    public void onCreate() {
        Params params = (Params) getArguments();
        this.mConfig = params.config;
        this.mEmail = params.email;
    }

    public void startSignIn(HelperActivityBase helperActivityBase) {
        start();
    }

    private void start() {
        setResult(Resource.forLoading());
        setResult(Resource.forFailure(new IntentRequiredException(GoogleSignIn.getClient((Context) getApplication(), getSignInOptions()).getSignInIntent(), 110)));
    }

    private GoogleSignInOptions getSignInOptions() {
        GoogleSignInOptions.Builder builder = new GoogleSignInOptions.Builder((GoogleSignInOptions) this.mConfig.getParams().getParcelable(ExtraConstants.GOOGLE_SIGN_IN_OPTIONS));
        if (!TextUtils.isEmpty(this.mEmail)) {
            builder.setAccountName(this.mEmail);
        }
        return builder.build();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 110) {
            try {
                setResult(Resource.forSuccess(createIdpResponse((GoogleSignInAccount) GoogleSignIn.getSignedInAccountFromIntent(intent).getResult(ApiException.class))));
            } catch (ApiException e) {
                if (e.getStatusCode() == 5) {
                    this.mEmail = null;
                    start();
                } else if (e.getStatusCode() == 12502) {
                    start();
                } else if (e.getStatusCode() == 12501) {
                    setResult(Resource.forFailure(new UserCancellationException()));
                } else {
                    if (e.getStatusCode() == 10) {
                        Log.w(TAG, "Developer error: this application is misconfigured. Check your SHA1 and package name in the Firebase console.");
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("Code: ");
                    sb.append(e.getStatusCode());
                    sb.append(", message: ");
                    sb.append(e.getMessage());
                    setResult(Resource.forFailure(new FirebaseUiException(4, sb.toString())));
                }
            }
        }
    }
}

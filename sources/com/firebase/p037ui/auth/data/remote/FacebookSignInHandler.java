package com.firebase.p037ui.auth.data.remote;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.braintreepayments.api.models.PostalAddressParser;
import com.facebook.CallbackManager;
import com.facebook.CallbackManager.Factory;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphRequest.GraphJSONObjectCallback;
import com.facebook.GraphResponse;
import com.facebook.WebDialog;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.firebase.p037ui.auth.AuthUI.IdpConfig;
import com.firebase.p037ui.auth.FirebaseUiException;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.IdpResponse.Builder;
import com.firebase.p037ui.auth.data.model.Resource;
import com.firebase.p037ui.auth.data.model.User;
import com.firebase.p037ui.auth.p038ui.HelperActivityBase;
import com.firebase.p037ui.auth.util.ExtraConstants;
import com.firebase.p037ui.auth.viewmodel.ProviderSignInBase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.firebase.ui.auth.data.remote.FacebookSignInHandler */
public class FacebookSignInHandler extends ProviderSignInBase<IdpConfig> {
    private static final String EMAIL = "email";
    private static final String PUBLIC_PROFILE = "public_profile";
    private final FacebookCallback<LoginResult> mCallback = new Callback();
    private final CallbackManager mCallbackManager = Factory.create();
    private List<String> mPermissions;

    /* renamed from: com.firebase.ui.auth.data.remote.FacebookSignInHandler$Callback */
    private class Callback implements FacebookCallback<LoginResult> {
        private Callback() {
        }

        public void onSuccess(LoginResult loginResult) {
            FacebookSignInHandler.this.setResult(Resource.forLoading());
            GraphRequest newMeRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new ProfileRequest(loginResult));
            Bundle bundle = new Bundle();
            bundle.putString("fields", "id,name,email,picture");
            newMeRequest.setParameters(bundle);
            newMeRequest.executeAsync();
        }

        public void onCancel() {
            onError(new FacebookException());
        }

        public void onError(FacebookException facebookException) {
            FacebookSignInHandler.this.setResult(Resource.forFailure(new FirebaseUiException(4, (Throwable) facebookException)));
        }
    }

    /* renamed from: com.firebase.ui.auth.data.remote.FacebookSignInHandler$ProfileRequest */
    private class ProfileRequest implements GraphJSONObjectCallback {
        private final LoginResult mResult;

        public ProfileRequest(LoginResult loginResult) {
            this.mResult = loginResult;
        }

        public void onCompleted(JSONObject jSONObject, GraphResponse graphResponse) {
            String str;
            String str2;
            FacebookRequestError error = graphResponse.getError();
            if (error != null) {
                FacebookSignInHandler.this.setResult(Resource.forFailure(new FirebaseUiException(4, (Throwable) error.getException())));
            } else if (jSONObject == null) {
                FacebookSignInHandler.this.setResult(Resource.forFailure(new FirebaseUiException(4, "Facebook graph request failed")));
            } else {
                Uri uri = null;
                try {
                    str = jSONObject.getString("email");
                } catch (JSONException unused) {
                    str = null;
                }
                try {
                    str2 = jSONObject.getString(PostalAddressParser.USER_ADDRESS_NAME_KEY);
                } catch (JSONException unused2) {
                    str2 = null;
                }
                try {
                    uri = Uri.parse(jSONObject.getJSONObject("picture").getJSONObject("data").getString("url"));
                } catch (JSONException unused3) {
                }
                FacebookSignInHandler.this.setResult(Resource.forSuccess(FacebookSignInHandler.createIdpResponse(this.mResult, str, str2, uri)));
            }
        }
    }

    public FacebookSignInHandler(Application application) {
        super(application);
    }

    /* access modifiers changed from: private */
    public static IdpResponse createIdpResponse(LoginResult loginResult, String str, String str2, Uri uri) {
        return new Builder(new User.Builder("facebook.com", str).setName(str2).setPhotoUri(uri).build()).setToken(loginResult.getAccessToken().getToken()).build();
    }

    /* access modifiers changed from: protected */
    public void onCreate() {
        Collection stringArrayList = ((IdpConfig) getArguments()).getParams().getStringArrayList(ExtraConstants.FACEBOOK_PERMISSIONS);
        if (stringArrayList == null) {
            stringArrayList = Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(stringArrayList);
        String str = "email";
        if (!arrayList.contains(str)) {
            arrayList.add(str);
        }
        String str2 = PUBLIC_PROFILE;
        if (!arrayList.contains(str2)) {
            arrayList.add(str2);
        }
        this.mPermissions = arrayList;
        LoginManager.getInstance().registerCallback(this.mCallbackManager, this.mCallback);
    }

    public void startSignIn(HelperActivityBase helperActivityBase) {
        WebDialog.setWebDialogTheme(helperActivityBase.getFlowParams().themeId);
        LoginManager.getInstance().logInWithReadPermissions(helperActivityBase, this.mPermissions);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        this.mCallbackManager.onActivityResult(i, i2, intent);
    }

    /* access modifiers changed from: protected */
    public void onCleared() {
        super.onCleared();
        LoginManager.getInstance().unregisterCallback(this.mCallbackManager);
    }
}

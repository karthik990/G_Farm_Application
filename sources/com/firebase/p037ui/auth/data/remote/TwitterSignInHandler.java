package com.firebase.p037ui.auth.data.remote;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.firebase.p037ui.auth.AuthUI;
import com.firebase.p037ui.auth.C1330R;
import com.firebase.p037ui.auth.FirebaseUiException;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.data.model.Resource;
import com.firebase.p037ui.auth.p038ui.HelperActivityBase;
import com.firebase.p037ui.auth.util.data.ProviderAvailability;
import com.firebase.p037ui.auth.viewmodel.ProviderSignInBase;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig.Builder;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.AccountService;

/* renamed from: com.firebase.ui.auth.data.remote.TwitterSignInHandler */
public class TwitterSignInHandler extends ProviderSignInBase<Void> {
    private final TwitterSessionResult mCallback = new TwitterSessionResult();
    private final TwitterAuthClient mClient = new TwitterAuthClient();

    /* renamed from: com.firebase.ui.auth.data.remote.TwitterSignInHandler$TwitterSessionResult */
    private class TwitterSessionResult extends Callback<TwitterSession> {
        private TwitterSessionResult() {
        }

        public void success(final Result<TwitterSession> result) {
            TwitterSignInHandler.this.setResult(Resource.forLoading());
            AccountService accountService = TwitterCore.getInstance().getApiClient().getAccountService();
            Boolean valueOf = Boolean.valueOf(false);
            accountService.verifyCredentials(valueOf, valueOf, Boolean.valueOf(true)).enqueue(new Callback<User>() {
                public void success(Result<User> result) {
                    User user = (User) result.data;
                    TwitterSignInHandler.this.setResult(Resource.forSuccess(TwitterSignInHandler.createIdpResponse((TwitterSession) result.data, user.email, user.name, Uri.parse(user.profileImageUrlHttps))));
                }

                public void failure(TwitterException twitterException) {
                    TwitterSignInHandler.this.setResult(Resource.forFailure(new FirebaseUiException(4, (Throwable) twitterException)));
                }
            });
        }

        public void failure(TwitterException twitterException) {
            TwitterSignInHandler.this.setResult(Resource.forFailure(new FirebaseUiException(4, (Throwable) twitterException)));
        }
    }

    public static void initializeTwitter() {
    }

    static {
        if (ProviderAvailability.IS_TWITTER_AVAILABLE) {
            Context applicationContext = AuthUI.getApplicationContext();
            Twitter.initialize(new Builder(applicationContext).twitterAuthConfig(new TwitterAuthConfig(applicationContext.getString(C1330R.C1335string.twitter_consumer_key), applicationContext.getString(C1330R.C1335string.twitter_consumer_secret))).build());
        }
    }

    public TwitterSignInHandler(Application application) {
        super(application);
    }

    /* access modifiers changed from: private */
    public static IdpResponse createIdpResponse(TwitterSession twitterSession, String str, String str2, Uri uri) {
        return new IdpResponse.Builder(new com.firebase.p037ui.auth.data.model.User.Builder("twitter.com", str).setName(str2).setPhotoUri(uri).build()).setToken(((TwitterAuthToken) twitterSession.getAuthToken()).token).setSecret(((TwitterAuthToken) twitterSession.getAuthToken()).secret).build();
    }

    public void startSignIn(HelperActivityBase helperActivityBase) {
        this.mClient.authorize(helperActivityBase, this.mCallback);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        this.mClient.onActivityResult(i, i2, intent);
    }
}

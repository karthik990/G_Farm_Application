package com.twitter.sdk.android.core.identity;

import android.app.Activity;
import android.content.Intent;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthException;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterSession;

public abstract class AuthHandler {
    static final String EXTRA_AUTH_ERROR = "auth_error";
    static final String EXTRA_SCREEN_NAME = "screen_name";
    static final String EXTRA_TOKEN = "tk";
    static final String EXTRA_TOKEN_SECRET = "ts";
    static final String EXTRA_USER_ID = "user_id";
    static final int RESULT_CODE_ERROR = 1;
    private final Callback<TwitterSession> callback;
    private final TwitterAuthConfig config;
    protected final int requestCode;

    public abstract boolean authorize(Activity activity);

    AuthHandler(TwitterAuthConfig twitterAuthConfig, Callback<TwitterSession> callback2, int i) {
        this.config = twitterAuthConfig;
        this.callback = callback2;
        this.requestCode = i;
    }

    /* access modifiers changed from: 0000 */
    public TwitterAuthConfig getAuthConfig() {
        return this.config;
    }

    /* access modifiers changed from: 0000 */
    public Callback<TwitterSession> getCallback() {
        return this.callback;
    }

    public boolean handleOnActivityResult(int i, int i2, Intent intent) {
        if (this.requestCode != i) {
            return false;
        }
        Callback callback2 = getCallback();
        if (callback2 != null) {
            if (i2 == -1) {
                String stringExtra = intent.getStringExtra(EXTRA_TOKEN);
                String stringExtra2 = intent.getStringExtra(EXTRA_TOKEN_SECRET);
                String stringExtra3 = intent.getStringExtra(EXTRA_SCREEN_NAME);
                callback2.success(new Result(new TwitterSession(new TwitterAuthToken(stringExtra, stringExtra2), intent.getLongExtra(EXTRA_USER_ID, 0), stringExtra3), null));
            } else {
                if (intent != null) {
                    String str = EXTRA_AUTH_ERROR;
                    if (intent.hasExtra(str)) {
                        callback2.failure((TwitterAuthException) intent.getSerializableExtra(str));
                    }
                }
                callback2.failure(new TwitterAuthException("Authorize failed."));
            }
        }
        return true;
    }
}

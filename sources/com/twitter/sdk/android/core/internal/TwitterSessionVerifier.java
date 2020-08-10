package com.twitter.sdk.android.core.internal;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.services.AccountService;
import java.io.IOException;

public class TwitterSessionVerifier implements SessionVerifier<TwitterSession> {
    private final AccountServiceProvider accountServiceProvider;

    protected static class AccountServiceProvider {
        protected AccountServiceProvider() {
        }

        public AccountService getAccountService(TwitterSession twitterSession) {
            return new TwitterApiClient(twitterSession).getAccountService();
        }
    }

    public TwitterSessionVerifier() {
        this(new AccountServiceProvider());
    }

    TwitterSessionVerifier(AccountServiceProvider accountServiceProvider2) {
        this.accountServiceProvider = accountServiceProvider2;
    }

    public void verifySession(TwitterSession twitterSession) {
        try {
            this.accountServiceProvider.getAccountService(twitterSession).verifyCredentials(Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(false)).execute();
        } catch (IOException | RuntimeException unused) {
        }
    }
}

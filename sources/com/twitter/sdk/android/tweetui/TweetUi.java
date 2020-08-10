package com.twitter.sdk.android.tweetui;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.GuestSessionProvider;
import com.twitter.sdk.android.core.SessionManager;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

public class TweetUi {
    static final String LOGTAG = "TweetUi";
    static volatile TweetUi instance;
    Context context = Twitter.getInstance().getContext(getIdentifier());
    GuestSessionProvider guestSessionProvider;
    private Picasso imageLoader;
    SessionManager<TwitterSession> sessionManager;
    private TweetRepository tweetRepository;

    public String getIdentifier() {
        return "com.twitter.sdk.android:tweet-ui";
    }

    public String getVersion() {
        return "3.3.0.12";
    }

    public static TweetUi getInstance() {
        if (instance == null) {
            synchronized (TweetUi.class) {
                if (instance == null) {
                    instance = new TweetUi();
                }
            }
        }
        return instance;
    }

    TweetUi() {
        TwitterCore instance2 = TwitterCore.getInstance();
        this.sessionManager = instance2.getSessionManager();
        this.guestSessionProvider = instance2.getGuestSessionProvider();
        this.tweetRepository = new TweetRepository(new Handler(Looper.getMainLooper()), instance2.getSessionManager());
        this.imageLoader = Picasso.with(Twitter.getInstance().getContext(getIdentifier()));
    }

    /* access modifiers changed from: 0000 */
    public TweetRepository getTweetRepository() {
        return this.tweetRepository;
    }

    /* access modifiers changed from: 0000 */
    public void setTweetRepository(TweetRepository tweetRepository2) {
        this.tweetRepository = tweetRepository2;
    }

    public Picasso getImageLoader() {
        return this.imageLoader;
    }

    /* access modifiers changed from: 0000 */
    public void setImageLoader(Picasso picasso) {
        this.imageLoader = picasso;
    }
}

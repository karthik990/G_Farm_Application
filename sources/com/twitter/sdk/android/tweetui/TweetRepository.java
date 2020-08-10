package com.twitter.sdk.android.tweetui;

import android.os.Handler;
import android.text.TextUtils;
import androidx.collection.LruCache;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.SessionManager;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthException;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import java.util.List;

class TweetRepository {
    private static final int DEFAULT_CACHE_SIZE = 20;
    final LruCache<Long, FormattedTweetText> formatCache;
    private final Handler mainHandler;
    final LruCache<Long, Tweet> tweetCache;
    /* access modifiers changed from: private */
    public final TwitterCore twitterCore;
    private final SessionManager<TwitterSession> userSessionManagers;

    class MultiTweetsCallback extends Callback<List<Tweet>> {

        /* renamed from: cb */
        final Callback<List<Tweet>> f3673cb;
        final List<Long> tweetIds;

        MultiTweetsCallback(List<Long> list, Callback<List<Tweet>> callback) {
            this.f3673cb = callback;
            this.tweetIds = list;
        }

        public void success(Result<List<Tweet>> result) {
            if (this.f3673cb != null) {
                this.f3673cb.success(new Result(C5249Utils.orderTweets(this.tweetIds, (List) result.data), result.response));
            }
        }

        public void failure(TwitterException twitterException) {
            this.f3673cb.failure(twitterException);
        }
    }

    class SingleTweetCallback extends Callback<Tweet> {

        /* renamed from: cb */
        final Callback<Tweet> f3674cb;

        SingleTweetCallback(Callback<Tweet> callback) {
            this.f3674cb = callback;
        }

        public void success(Result<Tweet> result) {
            Tweet tweet = (Tweet) result.data;
            TweetRepository.this.updateCache(tweet);
            Callback<Tweet> callback = this.f3674cb;
            if (callback != null) {
                callback.success(new Result(tweet, result.response));
            }
        }

        public void failure(TwitterException twitterException) {
            this.f3674cb.failure(twitterException);
        }
    }

    TweetRepository(Handler handler, SessionManager<TwitterSession> sessionManager) {
        this(handler, sessionManager, TwitterCore.getInstance());
    }

    TweetRepository(Handler handler, SessionManager<TwitterSession> sessionManager, TwitterCore twitterCore2) {
        this.twitterCore = twitterCore2;
        this.mainHandler = handler;
        this.userSessionManagers = sessionManager;
        this.tweetCache = new LruCache<>(20);
        this.formatCache = new LruCache<>(20);
    }

    /* access modifiers changed from: 0000 */
    public FormattedTweetText formatTweetText(Tweet tweet) {
        if (tweet == null) {
            return null;
        }
        FormattedTweetText formattedTweetText = (FormattedTweetText) this.formatCache.get(Long.valueOf(tweet.f3662id));
        if (formattedTweetText != null) {
            return formattedTweetText;
        }
        FormattedTweetText formatTweetText = TweetTextUtils.formatTweetText(tweet);
        if (formatTweetText != null && !TextUtils.isEmpty(formatTweetText.text)) {
            this.formatCache.put(Long.valueOf(tweet.f3662id), formatTweetText);
        }
        return formatTweetText;
    }

    /* access modifiers changed from: 0000 */
    public void updateCache(Tweet tweet) {
        this.tweetCache.put(Long.valueOf(tweet.f3662id), tweet);
    }

    private void deliverTweet(Tweet tweet, Callback<Tweet> callback) {
        if (callback != null) {
            this.mainHandler.post(new Runnable(tweet) {
                private final /* synthetic */ Tweet f$1;

                {
                    this.f$1 = r2;
                }

                public final void run() {
                    Callback.this.success(new Result(this.f$1, null));
                }
            });
        }
    }

    /* access modifiers changed from: 0000 */
    public void favorite(long j, Callback<Tweet> callback) {
        final long j2 = j;
        final Callback<Tweet> callback2 = callback;
        C52401 r0 = new LoggingCallback<TwitterSession>(callback, Twitter.getLogger()) {
            public void success(Result<TwitterSession> result) {
                TweetRepository.this.twitterCore.getApiClient((TwitterSession) result.data).getFavoriteService().create(Long.valueOf(j2), Boolean.valueOf(false)).enqueue(callback2);
            }
        };
        getUserSession(r0);
    }

    /* access modifiers changed from: 0000 */
    public void unfavorite(long j, Callback<Tweet> callback) {
        final long j2 = j;
        final Callback<Tweet> callback2 = callback;
        C52412 r0 = new LoggingCallback<TwitterSession>(callback, Twitter.getLogger()) {
            public void success(Result<TwitterSession> result) {
                TweetRepository.this.twitterCore.getApiClient((TwitterSession) result.data).getFavoriteService().destroy(Long.valueOf(j2), Boolean.valueOf(false)).enqueue(callback2);
            }
        };
        getUserSession(r0);
    }

    /* access modifiers changed from: 0000 */
    public void retweet(long j, Callback<Tweet> callback) {
        final long j2 = j;
        final Callback<Tweet> callback2 = callback;
        C52423 r0 = new LoggingCallback<TwitterSession>(callback, Twitter.getLogger()) {
            public void success(Result<TwitterSession> result) {
                TweetRepository.this.twitterCore.getApiClient((TwitterSession) result.data).getStatusesService().retweet(Long.valueOf(j2), Boolean.valueOf(false)).enqueue(callback2);
            }
        };
        getUserSession(r0);
    }

    /* access modifiers changed from: 0000 */
    public void unretweet(long j, Callback<Tweet> callback) {
        final long j2 = j;
        final Callback<Tweet> callback2 = callback;
        C52434 r0 = new LoggingCallback<TwitterSession>(callback, Twitter.getLogger()) {
            public void success(Result<TwitterSession> result) {
                TweetRepository.this.twitterCore.getApiClient((TwitterSession) result.data).getStatusesService().unretweet(Long.valueOf(j2), Boolean.valueOf(false)).enqueue(callback2);
            }
        };
        getUserSession(r0);
    }

    /* access modifiers changed from: 0000 */
    public void getUserSession(Callback<TwitterSession> callback) {
        TwitterSession twitterSession = (TwitterSession) this.userSessionManagers.getActiveSession();
        if (twitterSession == null) {
            callback.failure(new TwitterAuthException("User authorization required"));
        } else {
            callback.success(new Result(twitterSession, null));
        }
    }

    /* access modifiers changed from: 0000 */
    public void loadTweet(long j, Callback<Tweet> callback) {
        Tweet tweet = (Tweet) this.tweetCache.get(Long.valueOf(j));
        if (tweet != null) {
            deliverTweet(tweet, callback);
        } else {
            this.twitterCore.getApiClient().getStatusesService().show(Long.valueOf(j), null, null, null).enqueue(new SingleTweetCallback(callback));
        }
    }

    /* access modifiers changed from: 0000 */
    public void loadTweets(List<Long> list, Callback<List<Tweet>> callback) {
        this.twitterCore.getApiClient().getStatusesService().lookup(TextUtils.join(",", list), null, null, null).enqueue(new MultiTweetsCallback(list, callback));
    }
}

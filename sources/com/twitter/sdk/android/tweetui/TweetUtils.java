package com.twitter.sdk.android.tweetui;

import android.net.Uri;
import android.text.TextUtils;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.models.Tweet;
import java.util.List;
import java.util.Locale;

public final class TweetUtils {
    private static final String HASHTAG_URL = "https://twitter.com/hashtag/%s?ref_src=twsrc%%5Etwitterkit";
    static final String LOAD_TWEET_DEBUG = "loadTweet failure for Tweet Id %d.";
    private static final String PROFILE_URL = "https://twitter.com/%s?ref_src=twsrc%%5Etwitterkit";
    private static final String SYMBOL_URL = "https://twitter.com/search?q=%%24%s&ref_src=twsrc%%5Etwitterkit";
    private static final String TWEET_URL = "https://twitter.com/%s/status/%d?ref_src=twsrc%%5Etwitterkit";
    private static final String TWITTER_KIT_REF = "ref_src=twsrc%%5Etwitterkit";
    private static final String TWITTER_URL = "https://twitter.com/";
    private static final String UNKNOWN_SCREEN_NAME = "twitter_unknown";

    private TweetUtils() {
    }

    public static void loadTweet(long j, final Callback<Tweet> callback) {
        TweetUi.getInstance().getTweetRepository().loadTweet(j, new LoggingCallback<Tweet>(Twitter.getLogger(), callback) {
            public void success(Result<Tweet> result) {
                Callback callback = callback;
                if (callback != null) {
                    callback.success(result);
                }
            }
        });
    }

    public static void loadTweets(List<Long> list, final Callback<List<Tweet>> callback) {
        TweetUi.getInstance().getTweetRepository().loadTweets(list, new LoggingCallback<List<Tweet>>(Twitter.getLogger(), callback) {
            public void success(Result<List<Tweet>> result) {
                Callback callback = callback;
                if (callback != null) {
                    callback.success(result);
                }
            }
        });
    }

    static boolean isTweetResolvable(Tweet tweet) {
        return tweet != null && tweet.f3662id > 0 && tweet.user != null && !TextUtils.isEmpty(tweet.user.screenName);
    }

    static Tweet getDisplayTweet(Tweet tweet) {
        return (tweet == null || tweet.retweetedStatus == null) ? tweet : tweet.retweetedStatus;
    }

    static boolean showQuoteTweet(Tweet tweet) {
        return tweet.quotedStatus != null && tweet.card == null && (tweet.entities == null || tweet.entities.media == null || tweet.entities.media.isEmpty());
    }

    static Uri getPermalink(String str, long j) {
        String str2;
        if (j <= 0) {
            return null;
        }
        boolean isEmpty = TextUtils.isEmpty(str);
        String str3 = TWEET_URL;
        if (isEmpty) {
            str2 = String.format(Locale.US, str3, new Object[]{UNKNOWN_SCREEN_NAME, Long.valueOf(j)});
        } else {
            str2 = String.format(Locale.US, str3, new Object[]{str, Long.valueOf(j)});
        }
        return Uri.parse(str2);
    }

    static String getProfilePermalink(String str) {
        boolean isEmpty = TextUtils.isEmpty(str);
        String str2 = PROFILE_URL;
        if (isEmpty) {
            return String.format(Locale.US, str2, new Object[]{UNKNOWN_SCREEN_NAME});
        }
        return String.format(Locale.US, str2, new Object[]{str});
    }

    static String getHashtagPermalink(String str) {
        return String.format(Locale.US, HASHTAG_URL, new Object[]{str});
    }

    static String getSymbolPermalink(String str) {
        return String.format(Locale.US, SYMBOL_URL, new Object[]{str});
    }
}

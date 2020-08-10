package com.twitter.sdk.android.tweetui;

import android.view.View;
import android.view.View.OnClickListener;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiException;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.TweetBuilder;

class LikeTweetAction extends BaseTweetAction implements OnClickListener {
    final Tweet tweet;
    final TweetRepository tweetRepository;
    final TweetUi tweetUi;

    static class LikeCallback extends Callback<Tweet> {
        final ToggleImageButton button;

        /* renamed from: cb */
        final Callback<Tweet> f3669cb;
        final Tweet tweet;

        LikeCallback(ToggleImageButton toggleImageButton, Tweet tweet2, Callback<Tweet> callback) {
            this.button = toggleImageButton;
            this.tweet = tweet2;
            this.f3669cb = callback;
        }

        public void success(Result<Tweet> result) {
            this.f3669cb.success(result);
        }

        public void failure(TwitterException twitterException) {
            if (twitterException instanceof TwitterApiException) {
                int errorCode = ((TwitterApiException) twitterException).getErrorCode();
                if (errorCode == 139) {
                    this.f3669cb.success(new Result(new TweetBuilder().copy(this.tweet).setFavorited(true).build(), null));
                } else if (errorCode != 144) {
                    this.button.setToggledOn(this.tweet.favorited);
                    this.f3669cb.failure(twitterException);
                } else {
                    this.f3669cb.success(new Result(new TweetBuilder().copy(this.tweet).setFavorited(false).build(), null));
                }
            } else {
                this.button.setToggledOn(this.tweet.favorited);
                this.f3669cb.failure(twitterException);
            }
        }
    }

    LikeTweetAction(Tweet tweet2, TweetUi tweetUi2, Callback<Tweet> callback) {
        super(callback);
        this.tweet = tweet2;
        this.tweetUi = tweetUi2;
        this.tweetRepository = tweetUi2.getTweetRepository();
    }

    public void onClick(View view) {
        if (view instanceof ToggleImageButton) {
            ToggleImageButton toggleImageButton = (ToggleImageButton) view;
            if (this.tweet.favorited) {
                this.tweetRepository.unfavorite(this.tweet.f3662id, new LikeCallback(toggleImageButton, this.tweet, getActionCallback()));
            } else {
                this.tweetRepository.favorite(this.tweet.f3662id, new LikeCallback(toggleImageButton, this.tweet, getActionCallback()));
            }
        }
    }
}

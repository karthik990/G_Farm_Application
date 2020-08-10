package com.twitter.sdk.android.tweetui;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.IntentUtils;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.internal.UserUtils;
import com.twitter.sdk.android.core.internal.UserUtils.AvatarSize;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.TweetBuilder;
import java.util.Locale;

public abstract class BaseTweetView extends AbstractTweetView {
    ColorDrawable avatarMediaBg;
    ImageView avatarView;
    int birdLogoResId;
    View bottomSeparator;
    int containerBgColor;
    ViewGroup quoteTweetHolder;
    QuoteTweetView quoteTweetView;
    int retweetIconResId;
    TextView retweetedByView;
    TextView timestampView;
    TweetActionBarView tweetActionBarView;
    ImageView twitterLogoView;

    public /* bridge */ /* synthetic */ Tweet getTweet() {
        return super.getTweet();
    }

    public /* bridge */ /* synthetic */ long getTweetId() {
        return super.getTweetId();
    }

    public /* bridge */ /* synthetic */ void setTweet(Tweet tweet) {
        super.setTweet(tweet);
    }

    BaseTweetView(Context context, Tweet tweet) {
        this(context, tweet, DEFAULT_STYLE);
    }

    BaseTweetView(Context context, Tweet tweet, int i) {
        this(context, tweet, i, new DependencyProvider());
    }

    BaseTweetView(Context context, Tweet tweet, int i, DependencyProvider dependencyProvider) {
        super(context, null, i, dependencyProvider);
        initAttributes(i);
        applyStyles();
        if (isTweetUiEnabled()) {
            initTweetActions();
            setTweet(tweet);
        }
    }

    public BaseTweetView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BaseTweetView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, new DependencyProvider());
        initXmlAttributes(context, attributeSet);
        applyStyles();
    }

    private void initAttributes(int i) {
        this.styleResId = i;
        TypedArray obtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(i, C5234R.styleable.tw__TweetView);
        try {
            setStyleAttributes(obtainStyledAttributes);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    private void initXmlAttributes(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, C5234R.styleable.tw__TweetView, 0, 0);
            try {
                setXmlDataAttributes(obtainStyledAttributes);
                setStyleAttributes(obtainStyledAttributes);
            } finally {
                obtainStyledAttributes.recycle();
            }
        }
    }

    private void setXmlDataAttributes(TypedArray typedArray) {
        long longValue = C5249Utils.numberOrDefault(typedArray.getString(C5234R.styleable.tw__TweetView_tw__tweet_id), -1).longValue();
        if (longValue > 0) {
            setPermalinkUri(null, Long.valueOf(longValue));
            this.tweet = new TweetBuilder().setId(longValue).build();
            return;
        }
        throw new IllegalArgumentException("Invalid tw__tweet_id");
    }

    private void setStyleAttributes(TypedArray typedArray) {
        this.containerBgColor = typedArray.getColor(C5234R.styleable.tw__TweetView_tw__container_bg_color, getResources().getColor(C5234R.C5235color.tw__tweet_light_container_bg_color));
        this.primaryTextColor = typedArray.getColor(C5234R.styleable.tw__TweetView_tw__primary_text_color, getResources().getColor(C5234R.C5235color.tw__tweet_light_primary_text_color));
        this.actionColor = typedArray.getColor(C5234R.styleable.tw__TweetView_tw__action_color, getResources().getColor(C5234R.C5235color.tw__tweet_action_color));
        this.actionHighlightColor = typedArray.getColor(C5234R.styleable.tw__TweetView_tw__action_highlight_color, getResources().getColor(C5234R.C5235color.tw__tweet_action_light_highlight_color));
        this.tweetActionsEnabled = typedArray.getBoolean(C5234R.styleable.tw__TweetView_tw__tweet_actions_enabled, false);
        boolean isLightColor = ColorUtils.isLightColor(this.containerBgColor);
        if (isLightColor) {
            this.photoErrorResId = C5234R.C5236drawable.tw__ic_tweet_photo_error_light;
            this.birdLogoResId = C5234R.C5236drawable.tw__ic_logo_blue;
            this.retweetIconResId = C5234R.C5236drawable.tw__ic_retweet_light;
        } else {
            this.photoErrorResId = C5234R.C5236drawable.tw__ic_tweet_photo_error_dark;
            this.birdLogoResId = C5234R.C5236drawable.tw__ic_logo_white;
            this.retweetIconResId = C5234R.C5236drawable.tw__ic_retweet_dark;
        }
        int i = -1;
        this.secondaryTextColor = ColorUtils.calculateOpacityTransform(isLightColor ? 0.4d : 0.35d, isLightColor ? -1 : ViewCompat.MEASURED_STATE_MASK, this.primaryTextColor);
        double d = isLightColor ? 0.08d : 0.12d;
        if (isLightColor) {
            i = ViewCompat.MEASURED_STATE_MASK;
        }
        this.mediaBgColor = ColorUtils.calculateOpacityTransform(d, i, this.containerBgColor);
        this.avatarMediaBg = new ColorDrawable(this.mediaBgColor);
    }

    private void loadTweet() {
        final long tweetId = getTweetId();
        this.dependencyProvider.getTweetUi().getTweetRepository().loadTweet(getTweetId(), new Callback<Tweet>() {
            public void success(Result<Tweet> result) {
                BaseTweetView.this.setTweet((Tweet) result.data);
            }

            public void failure(TwitterException twitterException) {
                Twitter.getLogger().mo20817d("TweetUi", String.format(Locale.ENGLISH, "loadTweet failure for Tweet Id %d.", new Object[]{Long.valueOf(tweetId)}));
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        if (isTweetUiEnabled()) {
            initTweetActions();
            loadTweet();
        }
    }

    private void initTweetActions() {
        setTweetActionsEnabled(this.tweetActionsEnabled);
        this.tweetActionBarView.setOnActionCallback(new ResetTweetCallback(this, this.dependencyProvider.getTweetUi().getTweetRepository(), null));
    }

    /* access modifiers changed from: 0000 */
    public void findSubviews() {
        super.findSubviews();
        this.avatarView = (ImageView) findViewById(C5234R.C5237id.tw__tweet_author_avatar);
        this.timestampView = (TextView) findViewById(C5234R.C5237id.tw__tweet_timestamp);
        this.twitterLogoView = (ImageView) findViewById(C5234R.C5237id.tw__twitter_logo);
        this.retweetedByView = (TextView) findViewById(C5234R.C5237id.tw__tweet_retweeted_by);
        this.tweetActionBarView = (TweetActionBarView) findViewById(C5234R.C5237id.tw__tweet_action_bar);
        this.quoteTweetHolder = (ViewGroup) findViewById(C5234R.C5237id.quote_tweet_holder);
        this.bottomSeparator = findViewById(C5234R.C5237id.bottom_separator);
    }

    public void setOnActionCallback(Callback<Tweet> callback) {
        this.tweetActionBarView.setOnActionCallback(new ResetTweetCallback(this, this.dependencyProvider.getTweetUi().getTweetRepository(), callback));
        this.tweetActionBarView.setTweet(this.tweet);
    }

    /* access modifiers changed from: 0000 */
    public void render() {
        super.render();
        Tweet displayTweet = TweetUtils.getDisplayTweet(this.tweet);
        setProfilePhotoView(displayTweet);
        linkifyProfilePhotoView(displayTweet);
        setTimestamp(displayTweet);
        setTweetActions(this.tweet);
        showRetweetedBy(this.tweet);
        setQuoteTweet(this.tweet);
    }

    /* access modifiers changed from: 0000 */
    public void setQuoteTweet(Tweet tweet) {
        this.quoteTweetView = null;
        this.quoteTweetHolder.removeAllViews();
        if (tweet == null || !TweetUtils.showQuoteTweet(tweet)) {
            this.quoteTweetHolder.setVisibility(8);
            return;
        }
        this.quoteTweetView = new QuoteTweetView(getContext());
        this.quoteTweetView.setStyle(this.primaryTextColor, this.secondaryTextColor, this.actionColor, this.actionHighlightColor, this.mediaBgColor, this.photoErrorResId);
        this.quoteTweetView.setTweet(tweet.quotedStatus);
        this.quoteTweetView.setTweetLinkClickListener(this.tweetLinkClickListener);
        this.quoteTweetView.setTweetMediaClickListener(this.tweetMediaClickListener);
        this.quoteTweetHolder.setVisibility(0);
        this.quoteTweetHolder.addView(this.quoteTweetView);
    }

    /* access modifiers changed from: 0000 */
    public void showRetweetedBy(Tweet tweet) {
        if (tweet == null || tweet.retweetedStatus == null) {
            this.retweetedByView.setVisibility(8);
            return;
        }
        this.retweetedByView.setText(getResources().getString(C5234R.C5238string.tw__retweeted_by_format, new Object[]{tweet.user.name}));
        this.retweetedByView.setVisibility(0);
    }

    /* access modifiers changed from: protected */
    public void applyStyles() {
        setBackgroundColor(this.containerBgColor);
        this.fullNameView.setTextColor(this.primaryTextColor);
        this.screenNameView.setTextColor(this.secondaryTextColor);
        this.contentView.setTextColor(this.primaryTextColor);
        this.tweetMediaView.setMediaBgColor(this.mediaBgColor);
        this.tweetMediaView.setPhotoErrorResId(this.photoErrorResId);
        this.avatarView.setImageDrawable(this.avatarMediaBg);
        this.timestampView.setTextColor(this.secondaryTextColor);
        this.twitterLogoView.setImageResource(this.birdLogoResId);
        this.retweetedByView.setTextColor(this.secondaryTextColor);
    }

    private void setTimestamp(Tweet tweet) {
        String str;
        if (tweet == null || tweet.createdAt == null || !TweetDateUtils.isValidTimestamp(tweet.createdAt)) {
            str = "";
        } else {
            str = TweetDateUtils.dotPrefix(TweetDateUtils.getRelativeTimeString(getResources(), System.currentTimeMillis(), Long.valueOf(TweetDateUtils.apiTimeToLong(tweet.createdAt)).longValue()));
        }
        this.timestampView.setText(str);
    }

    /* access modifiers changed from: 0000 */
    public void setProfilePhotoView(Tweet tweet) {
        Picasso imageLoader = this.dependencyProvider.getImageLoader();
        if (imageLoader != null) {
            imageLoader.load((tweet == null || tweet.user == null) ? null : UserUtils.getProfileImageUrlHttps(tweet.user, AvatarSize.REASONABLY_SMALL)).placeholder((Drawable) this.avatarMediaBg).into(this.avatarView);
        }
    }

    /* access modifiers changed from: 0000 */
    public void linkifyProfilePhotoView(Tweet tweet) {
        if (tweet != null && tweet.user != null) {
            this.avatarView.setOnClickListener(new OnClickListener(tweet) {
                private final /* synthetic */ Tweet f$1;

                {
                    this.f$1 = r2;
                }

                public final void onClick(View view) {
                    BaseTweetView.this.lambda$linkifyProfilePhotoView$0$BaseTweetView(this.f$1, view);
                }
            });
            this.avatarView.setOnTouchListener(new OnTouchListener() {
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return BaseTweetView.this.lambda$linkifyProfilePhotoView$1$BaseTweetView(view, motionEvent);
                }
            });
        }
    }

    public /* synthetic */ void lambda$linkifyProfilePhotoView$0$BaseTweetView(Tweet tweet, View view) {
        if (this.tweetLinkClickListener != null) {
            this.tweetLinkClickListener.onLinkClick(tweet, TweetUtils.getProfilePermalink(tweet.user.screenName));
            return;
        }
        if (!IntentUtils.safeStartActivity(getContext(), new Intent("android.intent.action.VIEW", Uri.parse(TweetUtils.getProfilePermalink(tweet.user.screenName))))) {
            Twitter.getLogger().mo20819e("TweetUi", "Activity cannot be found to open URL");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x000d, code lost:
        if (r4 != 3) goto L_0x0034;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ boolean lambda$linkifyProfilePhotoView$1$BaseTweetView(android.view.View r3, android.view.MotionEvent r4) {
        /*
            r2 = this;
            r0 = r3
            android.widget.ImageView r0 = (android.widget.ImageView) r0
            int r4 = r4.getAction()
            if (r4 == 0) goto L_0x001e
            r1 = 1
            if (r4 == r1) goto L_0x0010
            r3 = 3
            if (r4 == r3) goto L_0x0013
            goto L_0x0034
        L_0x0010:
            r3.performClick()
        L_0x0013:
            android.graphics.drawable.Drawable r3 = r0.getDrawable()
            r3.clearColorFilter()
            r0.invalidate()
            goto L_0x0034
        L_0x001e:
            android.graphics.drawable.Drawable r3 = r0.getDrawable()
            android.content.res.Resources r4 = r2.getResources()
            int r1 = com.twitter.sdk.android.tweetui.C5234R.C5235color.tw__black_opacity_10
            int r4 = r4.getColor(r1)
            android.graphics.PorterDuff$Mode r1 = android.graphics.PorterDuff.Mode.SRC_ATOP
            r3.setColorFilter(r4, r1)
            r0.invalidate()
        L_0x0034:
            r3 = 0
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.twitter.sdk.android.tweetui.BaseTweetView.lambda$linkifyProfilePhotoView$1$BaseTweetView(android.view.View, android.view.MotionEvent):boolean");
    }

    /* access modifiers changed from: 0000 */
    public void setTweetActions(Tweet tweet) {
        this.tweetActionBarView.setTweet(tweet);
    }

    public void setTweetMediaClickListener(TweetMediaClickListener tweetMediaClickListener) {
        super.setTweetMediaClickListener(tweetMediaClickListener);
        QuoteTweetView quoteTweetView2 = this.quoteTweetView;
        if (quoteTweetView2 != null) {
            quoteTweetView2.setTweetMediaClickListener(tweetMediaClickListener);
        }
    }

    public void setTweetLinkClickListener(TweetLinkClickListener tweetLinkClickListener) {
        super.setTweetLinkClickListener(tweetLinkClickListener);
        QuoteTweetView quoteTweetView2 = this.quoteTweetView;
        if (quoteTweetView2 != null) {
            quoteTweetView2.setTweetLinkClickListener(tweetLinkClickListener);
        }
    }

    public void setTweetActionsEnabled(boolean z) {
        this.tweetActionsEnabled = z;
        if (this.tweetActionsEnabled) {
            this.tweetActionBarView.setVisibility(0);
            this.bottomSeparator.setVisibility(8);
            return;
        }
        this.tweetActionBarView.setVisibility(8);
        this.bottomSeparator.setVisibility(0);
    }
}

package com.twitter.sdk.android.tweetui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.IntentUtils;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.internal.UserUtils;
import com.twitter.sdk.android.core.internal.VineCardUtils;
import com.twitter.sdk.android.core.models.Card;
import com.twitter.sdk.android.core.models.ImageValue;
import com.twitter.sdk.android.core.models.MediaEntity;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.internal.AspectRatioFrameLayout;
import com.twitter.sdk.android.tweetui.internal.MediaBadgeView;
import com.twitter.sdk.android.tweetui.internal.SpanClickHandler;
import com.twitter.sdk.android.tweetui.internal.TweetMediaUtils;
import com.twitter.sdk.android.tweetui.internal.TweetMediaView;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

abstract class AbstractTweetView extends RelativeLayout {
    static final double DEFAULT_ASPECT_RATIO = 1.7777777777777777d;
    static final int DEFAULT_STYLE = C5234R.C5239style.tw__TweetLightStyle;
    static final String EMPTY_STRING = "";
    static final long INVALID_ID = -1;
    static final double MEDIA_BG_DARK_OPACITY = 0.12d;
    static final double MEDIA_BG_LIGHT_OPACITY = 0.08d;
    static final double SECONDARY_TEXT_COLOR_DARK_OPACITY = 0.35d;
    static final double SECONDARY_TEXT_COLOR_LIGHT_OPACITY = 0.4d;
    static final String TAG = "TweetUi";
    int actionColor;
    int actionHighlightColor;
    TextView contentView;
    final DependencyProvider dependencyProvider;
    TextView fullNameView;
    private LinkClickListener linkClickListener;
    MediaBadgeView mediaBadgeView;
    int mediaBgColor;
    AspectRatioFrameLayout mediaContainer;
    private Uri permalinkUri;
    int photoErrorResId;
    int primaryTextColor;
    TextView screenNameView;
    int secondaryTextColor;
    int styleResId;
    Tweet tweet;
    boolean tweetActionsEnabled;
    TweetLinkClickListener tweetLinkClickListener;
    TweetMediaClickListener tweetMediaClickListener;
    TweetMediaView tweetMediaView;

    static class DependencyProvider {
        DependencyProvider() {
        }

        /* access modifiers changed from: 0000 */
        public TweetUi getTweetUi() {
            return TweetUi.getInstance();
        }

        /* access modifiers changed from: 0000 */
        public Picasso getImageLoader() {
            return TweetUi.getInstance().getImageLoader();
        }
    }

    class PermalinkClickListener implements OnClickListener {
        PermalinkClickListener() {
        }

        public void onClick(View view) {
            if (AbstractTweetView.this.getPermalinkUri() != null) {
                AbstractTweetView.this.launchPermalink();
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract double getAspectRatioForPhotoEntity(int i);

    /* access modifiers changed from: 0000 */
    public abstract int getLayout();

    AbstractTweetView(Context context, AttributeSet attributeSet, int i, DependencyProvider dependencyProvider2) {
        super(context, attributeSet, i);
        this.dependencyProvider = dependencyProvider2;
        inflateView(context);
        findSubviews();
    }

    private void inflateView(Context context) {
        LayoutInflater.from(context).inflate(getLayout(), this, true);
    }

    /* access modifiers changed from: 0000 */
    public boolean isTweetUiEnabled() {
        if (isInEditMode()) {
            return false;
        }
        try {
            this.dependencyProvider.getTweetUi();
            return true;
        } catch (IllegalStateException e) {
            Twitter.getLogger().mo20819e(TAG, e.getMessage());
            setEnabled(false);
            return false;
        }
    }

    /* access modifiers changed from: 0000 */
    public void findSubviews() {
        this.fullNameView = (TextView) findViewById(C5234R.C5237id.tw__tweet_author_full_name);
        this.screenNameView = (TextView) findViewById(C5234R.C5237id.tw__tweet_author_screen_name);
        this.mediaContainer = (AspectRatioFrameLayout) findViewById(C5234R.C5237id.tw__aspect_ratio_media_container);
        this.tweetMediaView = (TweetMediaView) findViewById(C5234R.C5237id.tweet_media_view);
        this.contentView = (TextView) findViewById(C5234R.C5237id.tw__tweet_text);
        this.mediaBadgeView = (MediaBadgeView) findViewById(C5234R.C5237id.tw__tweet_media_badge);
    }

    public long getTweetId() {
        Tweet tweet2 = this.tweet;
        if (tweet2 == null) {
            return -1;
        }
        return tweet2.f3662id;
    }

    public void setTweet(Tweet tweet2) {
        this.tweet = tweet2;
        render();
    }

    public Tweet getTweet() {
        return this.tweet;
    }

    public void setTweetMediaClickListener(TweetMediaClickListener tweetMediaClickListener2) {
        this.tweetMediaClickListener = tweetMediaClickListener2;
        this.tweetMediaView.setTweetMediaClickListener(tweetMediaClickListener2);
    }

    public void setTweetLinkClickListener(TweetLinkClickListener tweetLinkClickListener2) {
        this.tweetLinkClickListener = tweetLinkClickListener2;
    }

    /* access modifiers changed from: 0000 */
    public void render() {
        Tweet displayTweet = TweetUtils.getDisplayTweet(this.tweet);
        setName(displayTweet);
        setScreenName(displayTweet);
        setTweetMedia(displayTweet);
        setText(displayTweet);
        setContentDescription(displayTweet);
        if (TweetUtils.isTweetResolvable(this.tweet)) {
            setPermalinkUri(this.tweet.user.screenName, Long.valueOf(getTweetId()));
        } else {
            this.permalinkUri = null;
        }
        setPermalinkLauncher();
    }

    /* access modifiers changed from: 0000 */
    public Uri getPermalinkUri() {
        return this.permalinkUri;
    }

    /* access modifiers changed from: 0000 */
    public void setPermalinkUri(String str, Long l) {
        if (l.longValue() > 0) {
            this.permalinkUri = TweetUtils.getPermalink(str, l.longValue());
        }
    }

    private void setPermalinkLauncher() {
        setOnClickListener(new PermalinkClickListener());
    }

    /* access modifiers changed from: 0000 */
    public void launchPermalink() {
        if (!IntentUtils.safeStartActivity(getContext(), new Intent("android.intent.action.VIEW", getPermalinkUri()))) {
            Twitter.getLogger().mo20819e(TAG, "Activity cannot be found to open permalink URI");
        }
    }

    private void setName(Tweet tweet2) {
        if (tweet2 == null || tweet2.user == null) {
            this.fullNameView.setText("");
        } else {
            this.fullNameView.setText(C5249Utils.stringOrEmpty(tweet2.user.name));
        }
    }

    private void setScreenName(Tweet tweet2) {
        if (tweet2 == null || tweet2.user == null) {
            this.screenNameView.setText("");
        } else {
            this.screenNameView.setText(UserUtils.formatScreenName(C5249Utils.stringOrEmpty(tweet2.user.screenName)));
        }
    }

    private void setText(Tweet tweet2) {
        if (VERSION.SDK_INT >= 16) {
            this.contentView.setImportantForAccessibility(2);
        }
        CharSequence charSeqOrEmpty = C5249Utils.charSeqOrEmpty(getLinkifiedText(tweet2));
        SpanClickHandler.enableClicksOnSpans(this.contentView);
        if (!TextUtils.isEmpty(charSeqOrEmpty)) {
            this.contentView.setText(charSeqOrEmpty);
            this.contentView.setVisibility(0);
            return;
        }
        this.contentView.setText("");
        this.contentView.setVisibility(8);
    }

    /* access modifiers changed from: 0000 */
    public final void setTweetMedia(Tweet tweet2) {
        clearTweetMedia();
        if (tweet2 != null) {
            if (tweet2.card != null && VineCardUtils.isVine(tweet2.card)) {
                Card card = tweet2.card;
                ImageValue imageValue = VineCardUtils.getImageValue(card);
                String streamUrl = VineCardUtils.getStreamUrl(card);
                if (imageValue != null && !TextUtils.isEmpty(streamUrl)) {
                    setViewsForMedia(getAspectRatio(imageValue));
                    this.tweetMediaView.setVineCard(tweet2);
                    this.mediaBadgeView.setVisibility(0);
                    this.mediaBadgeView.setCard(card);
                }
            } else if (TweetMediaUtils.hasSupportedVideo(tweet2)) {
                MediaEntity videoEntity = TweetMediaUtils.getVideoEntity(tweet2);
                setViewsForMedia(getAspectRatio(videoEntity));
                this.tweetMediaView.setTweetMediaEntities(this.tweet, Collections.singletonList(videoEntity));
                this.mediaBadgeView.setVisibility(0);
                this.mediaBadgeView.setMediaEntity(videoEntity);
            } else if (TweetMediaUtils.hasPhoto(tweet2)) {
                List photoEntities = TweetMediaUtils.getPhotoEntities(tweet2);
                setViewsForMedia(getAspectRatioForPhotoEntity(photoEntities.size()));
                this.tweetMediaView.setTweetMediaEntities(tweet2, photoEntities);
                this.mediaBadgeView.setVisibility(8);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void setViewsForMedia(double d) {
        this.mediaContainer.setVisibility(0);
        this.mediaContainer.setAspectRatio(d);
        this.tweetMediaView.setVisibility(0);
    }

    /* access modifiers changed from: protected */
    public double getAspectRatio(MediaEntity mediaEntity) {
        if (mediaEntity == null || mediaEntity.sizes == null || mediaEntity.sizes.medium == null || mediaEntity.sizes.medium.f3659w == 0 || mediaEntity.sizes.medium.f3658h == 0) {
            return DEFAULT_ASPECT_RATIO;
        }
        double d = (double) mediaEntity.sizes.medium.f3659w;
        double d2 = (double) mediaEntity.sizes.medium.f3658h;
        Double.isNaN(d);
        Double.isNaN(d2);
        return d / d2;
    }

    /* access modifiers changed from: protected */
    public double getAspectRatio(ImageValue imageValue) {
        if (imageValue == null || imageValue.width == 0 || imageValue.height == 0) {
            return DEFAULT_ASPECT_RATIO;
        }
        double d = (double) imageValue.width;
        double d2 = (double) imageValue.height;
        Double.isNaN(d);
        Double.isNaN(d2);
        return d / d2;
    }

    /* access modifiers changed from: protected */
    public void clearTweetMedia() {
        this.mediaContainer.setVisibility(8);
    }

    /* access modifiers changed from: protected */
    public CharSequence getLinkifiedText(Tweet tweet2) {
        FormattedTweetText formatTweetText = this.dependencyProvider.getTweetUi().getTweetRepository().formatTweetText(tweet2);
        if (formatTweetText == null) {
            return null;
        }
        return TweetTextLinkifier.linkifyUrls(formatTweetText, getLinkClickListener(), this.actionColor, this.actionHighlightColor, TweetUtils.showQuoteTweet(tweet2), tweet2.card != null && VineCardUtils.isVine(tweet2.card));
    }

    /* access modifiers changed from: 0000 */
    public void setContentDescription(Tweet tweet2) {
        if (!TweetUtils.isTweetResolvable(tweet2)) {
            setContentDescription(getResources().getString(C5234R.C5238string.tw__loading_tweet));
            return;
        }
        FormattedTweetText formatTweetText = this.dependencyProvider.getTweetUi().getTweetRepository().formatTweetText(tweet2);
        String str = null;
        String str2 = formatTweetText != null ? formatTweetText.text : null;
        long apiTimeToLong = TweetDateUtils.apiTimeToLong(tweet2.createdAt);
        if (apiTimeToLong != -1) {
            str = DateFormat.getDateInstance().format(new Date(apiTimeToLong));
        }
        setContentDescription(getResources().getString(C5234R.C5238string.tw__tweet_content_description, new Object[]{C5249Utils.stringOrEmpty(tweet2.user.name), C5249Utils.stringOrEmpty(str2), C5249Utils.stringOrEmpty(str)}));
    }

    /* access modifiers changed from: protected */
    public LinkClickListener getLinkClickListener() {
        if (this.linkClickListener == null) {
            this.linkClickListener = new LinkClickListener() {
                public final void onUrlClicked(String str) {
                    AbstractTweetView.this.lambda$getLinkClickListener$0$AbstractTweetView(str);
                }
            };
        }
        return this.linkClickListener;
    }

    public /* synthetic */ void lambda$getLinkClickListener$0$AbstractTweetView(String str) {
        if (!TextUtils.isEmpty(str)) {
            TweetLinkClickListener tweetLinkClickListener2 = this.tweetLinkClickListener;
            if (tweetLinkClickListener2 != null) {
                tweetLinkClickListener2.onLinkClick(this.tweet, str);
            } else {
                if (!IntentUtils.safeStartActivity(getContext(), new Intent("android.intent.action.VIEW", Uri.parse(str)))) {
                    Twitter.getLogger().mo20819e(TAG, "Activity cannot be found to open URL");
                }
            }
        }
    }
}

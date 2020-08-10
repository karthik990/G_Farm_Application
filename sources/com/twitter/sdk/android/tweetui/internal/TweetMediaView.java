package com.twitter.sdk.android.tweetui.internal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.core.view.ViewCompat;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.IntentUtils;
import com.twitter.sdk.android.core.internal.VineCardUtils;
import com.twitter.sdk.android.core.models.Card;
import com.twitter.sdk.android.core.models.ImageValue;
import com.twitter.sdk.android.core.models.MediaEntity;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.C5234R;
import com.twitter.sdk.android.tweetui.GalleryActivity;
import com.twitter.sdk.android.tweetui.GalleryActivity.GalleryItem;
import com.twitter.sdk.android.tweetui.PlayerActivity;
import com.twitter.sdk.android.tweetui.PlayerActivity.PlayerItem;
import com.twitter.sdk.android.tweetui.TweetMediaClickListener;
import com.twitter.sdk.android.tweetui.TweetUi;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

public class TweetMediaView extends ViewGroup implements OnClickListener {
    static final int MAX_IMAGE_VIEW_COUNT = 4;
    static final String SIZED_IMAGE_SMALL = ":small";
    final DependencyProvider dependencyProvider;
    private int imageCount;
    private final OverlayImageView[] imageViews;
    boolean internalRoundedCornersEnabled;
    int mediaBgColor;
    private final int mediaDividerSize;
    private List<MediaEntity> mediaEntities;
    private final Path path;
    int photoErrorResId;
    final float[] radii;
    private final RectF rect;
    Tweet tweet;
    TweetMediaClickListener tweetMediaClickListener;

    static class DependencyProvider {
        DependencyProvider() {
        }

        /* access modifiers changed from: 0000 */
        public Picasso getImageLoader() {
            return TweetUi.getInstance().getImageLoader();
        }
    }

    static class PicassoCallback implements Callback {
        final WeakReference<ImageView> imageViewWeakReference;

        public void onError() {
        }

        PicassoCallback(ImageView imageView) {
            this.imageViewWeakReference = new WeakReference<>(imageView);
        }

        public void onSuccess() {
            ImageView imageView = (ImageView) this.imageViewWeakReference.get();
            if (imageView != null) {
                imageView.setBackgroundResource(17170445);
            }
        }
    }

    static class Size {
        static final Size EMPTY = new Size();
        final int height;
        final int width;

        private Size() {
            this(0, 0);
        }

        private Size(int i, int i2) {
            this.width = i;
            this.height = i2;
        }

        static Size fromSize(int i, int i2) {
            int max = Math.max(i, 0);
            int max2 = Math.max(i2, 0);
            return (max == 0 && max2 == 0) ? EMPTY : new Size(max, max2);
        }
    }

    public TweetMediaView(Context context) {
        this(context, null);
    }

    public TweetMediaView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, new DependencyProvider());
    }

    TweetMediaView(Context context, AttributeSet attributeSet, DependencyProvider dependencyProvider2) {
        super(context, attributeSet);
        this.imageViews = new OverlayImageView[4];
        this.mediaEntities = Collections.emptyList();
        this.path = new Path();
        this.rect = new RectF();
        this.radii = new float[8];
        this.mediaBgColor = ViewCompat.MEASURED_STATE_MASK;
        this.dependencyProvider = dependencyProvider2;
        this.mediaDividerSize = getResources().getDimensionPixelSize(C5234R.dimen.tw__media_view_divider_size);
        this.photoErrorResId = C5234R.C5236drawable.tw__ic_tweet_photo_error_dark;
    }

    public void setRoundedCornersRadii(int i, int i2, int i3, int i4) {
        float[] fArr = this.radii;
        float f = (float) i;
        fArr[0] = f;
        fArr[1] = f;
        float f2 = (float) i2;
        fArr[2] = f2;
        fArr[3] = f2;
        float f3 = (float) i3;
        fArr[4] = f3;
        fArr[5] = f3;
        float f4 = (float) i4;
        fArr[6] = f4;
        fArr[7] = f4;
        requestLayout();
    }

    public void setMediaBgColor(int i) {
        this.mediaBgColor = i;
    }

    public void setTweetMediaClickListener(TweetMediaClickListener tweetMediaClickListener2) {
        this.tweetMediaClickListener = tweetMediaClickListener2;
    }

    public void setPhotoErrorResId(int i) {
        this.photoErrorResId = i;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.imageCount > 0) {
            layoutImages();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        Size size;
        if (this.imageCount > 0) {
            size = measureImages(i, i2);
        } else {
            size = Size.EMPTY;
        }
        setMeasuredDimension(size.width, size.height);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.path.reset();
        this.rect.set(0.0f, 0.0f, (float) i, (float) i2);
        this.path.addRoundRect(this.rect, this.radii, Direction.CW);
        this.path.close();
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        if (!this.internalRoundedCornersEnabled || VERSION.SDK_INT < 18) {
            super.dispatchDraw(canvas);
            return;
        }
        int save = canvas.save();
        canvas.clipPath(this.path);
        super.dispatchDraw(canvas);
        canvas.restoreToCount(save);
    }

    public void onClick(View view) {
        Integer num = (Integer) view.getTag(C5234R.C5237id.tw__entity_index);
        if (this.tweetMediaClickListener != null) {
            this.tweetMediaClickListener.onMediaEntityClick(this.tweet, !this.mediaEntities.isEmpty() ? (MediaEntity) this.mediaEntities.get(num.intValue()) : null);
        } else if (!this.mediaEntities.isEmpty()) {
            MediaEntity mediaEntity = (MediaEntity) this.mediaEntities.get(num.intValue());
            if (TweetMediaUtils.isVideoType(mediaEntity)) {
                launchVideoPlayer(mediaEntity);
            } else if (TweetMediaUtils.isPhotoType(mediaEntity)) {
                launchPhotoGallery(num.intValue());
            }
        } else {
            launchVideoPlayer(this.tweet);
        }
    }

    public void launchVideoPlayer(MediaEntity mediaEntity) {
        if (TweetMediaUtils.getSupportedVariant(mediaEntity) != null) {
            Intent intent = new Intent(getContext(), PlayerActivity.class);
            PlayerItem playerItem = new PlayerItem(TweetMediaUtils.getSupportedVariant(mediaEntity).url, TweetMediaUtils.isLooping(mediaEntity), TweetMediaUtils.showVideoControls(mediaEntity), null, null);
            intent.putExtra(PlayerActivity.PLAYER_ITEM, playerItem);
            IntentUtils.safeStartActivity(getContext(), intent);
        }
    }

    public void launchVideoPlayer(Tweet tweet2) {
        Card card = tweet2.card;
        Intent intent = new Intent(getContext(), PlayerActivity.class);
        PlayerItem playerItem = new PlayerItem(VineCardUtils.getStreamUrl(card), true, false, null, null);
        intent.putExtra(PlayerActivity.PLAYER_ITEM, playerItem);
        IntentUtils.safeStartActivity(getContext(), intent);
    }

    public void launchPhotoGallery(int i) {
        Intent intent = new Intent(getContext(), GalleryActivity.class);
        intent.putExtra(GalleryActivity.GALLERY_ITEM, new GalleryItem(this.tweet.f3662id, i, this.mediaEntities));
        IntentUtils.safeStartActivity(getContext(), intent);
    }

    public void setTweetMediaEntities(Tweet tweet2, List<MediaEntity> list) {
        if (tweet2 != null && list != null && !list.isEmpty() && !list.equals(this.mediaEntities)) {
            this.tweet = tweet2;
            this.mediaEntities = list;
            clearImageViews();
            initializeImageViews(list);
            this.internalRoundedCornersEnabled = TweetMediaUtils.isPhotoType((MediaEntity) list.get(0));
            requestLayout();
        }
    }

    public void setVineCard(Tweet tweet2) {
        if (tweet2 != null && tweet2.card != null && VineCardUtils.isVine(tweet2.card)) {
            this.tweet = tweet2;
            this.mediaEntities = Collections.emptyList();
            clearImageViews();
            initializeImageViews(tweet2.card);
            this.internalRoundedCornersEnabled = false;
            requestLayout();
        }
    }

    /* access modifiers changed from: 0000 */
    public Size measureImages(int i, int i2) {
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        int i3 = this.mediaDividerSize;
        int i4 = (size - i3) / 2;
        int i5 = (size2 - i3) / 2;
        int i6 = this.imageCount;
        if (i6 == 1) {
            measureImageView(0, size, size2);
        } else if (i6 == 2) {
            measureImageView(0, i4, size2);
            measureImageView(1, i4, size2);
        } else if (i6 == 3) {
            measureImageView(0, i4, size2);
            measureImageView(1, i4, i5);
            measureImageView(2, i4, i5);
        } else if (i6 == 4) {
            measureImageView(0, i4, i5);
            measureImageView(1, i4, i5);
            measureImageView(2, i4, i5);
            measureImageView(3, i4, i5);
        }
        return Size.fromSize(size, size2);
    }

    /* access modifiers changed from: 0000 */
    public void measureImageView(int i, int i2, int i3) {
        this.imageViews[i].measure(MeasureSpec.makeMeasureSpec(i2, 1073741824), MeasureSpec.makeMeasureSpec(i3, 1073741824));
    }

    /* access modifiers changed from: 0000 */
    public void layoutImages() {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int i = this.mediaDividerSize;
        int i2 = (measuredWidth - i) / 2;
        int i3 = (measuredHeight - i) / 2;
        int i4 = i2 + i;
        int i5 = this.imageCount;
        if (i5 == 1) {
            layoutImage(0, 0, 0, measuredWidth, measuredHeight);
        } else if (i5 == 2) {
            int i6 = measuredHeight;
            layoutImage(0, 0, 0, i2, i6);
            layoutImage(1, i2 + this.mediaDividerSize, 0, measuredWidth, i6);
        } else if (i5 == 3) {
            layoutImage(0, 0, 0, i2, measuredHeight);
            int i7 = i4;
            int i8 = measuredWidth;
            layoutImage(1, i7, 0, i8, i3);
            layoutImage(2, i7, i3 + this.mediaDividerSize, i8, measuredHeight);
        } else if (i5 == 4) {
            int i9 = i2;
            layoutImage(0, 0, 0, i9, i3);
            layoutImage(2, 0, i3 + this.mediaDividerSize, i9, measuredHeight);
            int i10 = i4;
            int i11 = measuredWidth;
            layoutImage(1, i10, 0, i11, i3);
            layoutImage(3, i10, i3 + this.mediaDividerSize, i11, measuredHeight);
        }
    }

    /* access modifiers changed from: 0000 */
    public void layoutImage(int i, int i2, int i3, int i4, int i5) {
        OverlayImageView overlayImageView = this.imageViews[i];
        if (overlayImageView.getLeft() != i2 || overlayImageView.getTop() != i3 || overlayImageView.getRight() != i4 || overlayImageView.getBottom() != i5) {
            overlayImageView.layout(i2, i3, i4, i5);
        }
    }

    /* access modifiers changed from: 0000 */
    public void clearImageViews() {
        for (int i = 0; i < this.imageCount; i++) {
            OverlayImageView overlayImageView = this.imageViews[i];
            if (overlayImageView != null) {
                overlayImageView.setVisibility(8);
            }
        }
        this.imageCount = 0;
    }

    /* access modifiers changed from: 0000 */
    public void initializeImageViews(List<MediaEntity> list) {
        this.imageCount = Math.min(4, list.size());
        for (int i = 0; i < this.imageCount; i++) {
            OverlayImageView orCreateImageView = getOrCreateImageView(i);
            MediaEntity mediaEntity = (MediaEntity) list.get(i);
            setAltText(orCreateImageView, mediaEntity.altText);
            setMediaImage(orCreateImageView, getSizedImagePath(mediaEntity));
            setOverlayImage(orCreateImageView, TweetMediaUtils.isVideoType(mediaEntity));
        }
    }

    /* access modifiers changed from: 0000 */
    public void initializeImageViews(Card card) {
        this.imageCount = 1;
        OverlayImageView orCreateImageView = getOrCreateImageView(0);
        ImageValue imageValue = VineCardUtils.getImageValue(card);
        setAltText(orCreateImageView, imageValue.alt);
        setMediaImage(orCreateImageView, imageValue.url);
        setOverlayImage(orCreateImageView, true);
    }

    /* access modifiers changed from: 0000 */
    public OverlayImageView getOrCreateImageView(int i) {
        OverlayImageView overlayImageView = this.imageViews[i];
        if (overlayImageView == null) {
            overlayImageView = new OverlayImageView(getContext());
            overlayImageView.setLayoutParams(generateDefaultLayoutParams());
            overlayImageView.setOnClickListener(this);
            this.imageViews[i] = overlayImageView;
            addView(overlayImageView, i);
        } else {
            measureImageView(i, 0, 0);
            layoutImage(i, 0, 0, 0, 0);
        }
        overlayImageView.setVisibility(0);
        overlayImageView.setBackgroundColor(this.mediaBgColor);
        overlayImageView.setTag(C5234R.C5237id.tw__entity_index, Integer.valueOf(i));
        return overlayImageView;
    }

    /* access modifiers changed from: 0000 */
    public String getSizedImagePath(MediaEntity mediaEntity) {
        if (this.imageCount <= 1) {
            return mediaEntity.mediaUrlHttps;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(mediaEntity.mediaUrlHttps);
        sb.append(SIZED_IMAGE_SMALL);
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public void setAltText(ImageView imageView, String str) {
        if (!TextUtils.isEmpty(str)) {
            imageView.setContentDescription(str);
        } else {
            imageView.setContentDescription(getResources().getString(C5234R.C5238string.tw__tweet_media));
        }
    }

    /* access modifiers changed from: 0000 */
    public void setOverlayImage(OverlayImageView overlayImageView, boolean z) {
        if (z) {
            overlayImageView.setOverlayDrawable(getContext().getResources().getDrawable(C5234R.C5236drawable.tw__player_overlay));
        } else {
            overlayImageView.setOverlayDrawable(null);
        }
    }

    /* access modifiers changed from: 0000 */
    public void setMediaImage(ImageView imageView, String str) {
        Picasso imageLoader = this.dependencyProvider.getImageLoader();
        if (imageLoader != null) {
            imageLoader.load(str).fit().centerCrop().error(this.photoErrorResId).into(imageView, new PicassoCallback(imageView));
        }
    }
}

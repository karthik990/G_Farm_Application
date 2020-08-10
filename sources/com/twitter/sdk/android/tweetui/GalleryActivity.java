package com.twitter.sdk.android.tweetui;

import android.app.Activity;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.twitter.sdk.android.core.models.MediaEntity;
import com.twitter.sdk.android.tweetui.internal.SwipeToDismissTouchListener.Callback;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class GalleryActivity extends Activity {
    public static final String GALLERY_ITEM = "GALLERY_ITEM";
    static final String MEDIA_ENTITY = "MEDIA_ENTITY";
    GalleryItem galleryItem;

    public static class GalleryItem implements Serializable {
        public final List<MediaEntity> mediaEntities;
        public final int mediaEntityIndex;
        public final long tweetId;

        public GalleryItem(int i, List<MediaEntity> list) {
            this(0, i, list);
        }

        public GalleryItem(long j, int i, List<MediaEntity> list) {
            this.tweetId = j;
            this.mediaEntityIndex = i;
            this.mediaEntities = list;
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C5234R.layout.tw__gallery_activity);
        this.galleryItem = getGalleryItem();
        GalleryAdapter galleryAdapter = new GalleryAdapter(this, getSwipeToDismissCallback());
        galleryAdapter.addAll(this.galleryItem.mediaEntities);
        ViewPager viewPager = (ViewPager) findViewById(C5234R.C5237id.tw__view_pager);
        viewPager.setPageMargin(getResources().getDimensionPixelSize(C5234R.dimen.tw__gallery_page_margin));
        viewPager.addOnPageChangeListener(getOnPageChangeListener());
        viewPager.setAdapter(galleryAdapter);
        viewPager.setCurrentItem(this.galleryItem.mediaEntityIndex);
    }

    /* access modifiers changed from: 0000 */
    public OnPageChangeListener getOnPageChangeListener() {
        return new OnPageChangeListener() {
            int galleryPosition = -1;

            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
                int i3 = this.galleryPosition;
                if (i3 == -1 && i == 0 && ((double) f) == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                    this.galleryPosition = i3 + 1;
                }
            }

            public void onPageSelected(int i) {
                this.galleryPosition++;
            }
        };
    }

    /* access modifiers changed from: 0000 */
    public Callback getSwipeToDismissCallback() {
        return new Callback() {
            public void onMove(float f) {
            }

            public void onDismiss() {
                GalleryActivity.this.finish();
                GalleryActivity.this.overridePendingTransition(0, C5234R.anim.tw__slide_out);
            }
        };
    }

    /* access modifiers changed from: 0000 */
    public GalleryItem getGalleryItem() {
        MediaEntity mediaEntity = (MediaEntity) getIntent().getSerializableExtra(MEDIA_ENTITY);
        if (mediaEntity != null) {
            return new GalleryItem(0, Collections.singletonList(mediaEntity));
        }
        return (GalleryItem) getIntent().getSerializableExtra(GALLERY_ITEM);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, C5234R.anim.tw__slide_out);
    }
}

package com.twitter.sdk.android.tweetui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager.widget.PagerAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.twitter.sdk.android.core.models.MediaEntity;
import com.twitter.sdk.android.tweetui.internal.GalleryImageView;
import com.twitter.sdk.android.tweetui.internal.SwipeToDismissTouchListener.Callback;
import java.util.ArrayList;
import java.util.List;

class GalleryAdapter extends PagerAdapter {
    final Callback callback;
    final Context context;
    final List<MediaEntity> items = new ArrayList();

    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    GalleryAdapter(Context context2, Callback callback2) {
        this.context = context2;
        this.callback = callback2;
    }

    /* access modifiers changed from: 0000 */
    public void addAll(List<MediaEntity> list) {
        this.items.addAll(list);
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.items.size();
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        GalleryImageView galleryImageView = new GalleryImageView(this.context);
        galleryImageView.setSwipeToDismissCallback(this.callback);
        viewGroup.addView(galleryImageView);
        Picasso.with(this.context).load(((MediaEntity) this.items.get(i)).mediaUrlHttps).into((Target) galleryImageView);
        return galleryImageView;
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }
}

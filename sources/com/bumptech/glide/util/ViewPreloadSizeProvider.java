package com.bumptech.glide.util;

import android.graphics.drawable.Drawable;
import android.view.View;
import com.bumptech.glide.ListPreloader.PreloadSizeProvider;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;
import java.util.Arrays;

public class ViewPreloadSizeProvider<T> implements PreloadSizeProvider<T>, SizeReadyCallback {
    private int[] size;
    private SizeViewTarget viewTarget;

    static final class SizeViewTarget extends CustomViewTarget<View, Object> {
        public void onLoadFailed(Drawable drawable) {
        }

        /* access modifiers changed from: protected */
        public void onResourceCleared(Drawable drawable) {
        }

        public void onResourceReady(Object obj, Transition<? super Object> transition) {
        }

        SizeViewTarget(View view) {
            super(view);
        }
    }

    public ViewPreloadSizeProvider() {
    }

    public ViewPreloadSizeProvider(View view) {
        this.viewTarget = new SizeViewTarget(view);
        this.viewTarget.getSize(this);
    }

    public int[] getPreloadSize(T t, int i, int i2) {
        int[] iArr = this.size;
        if (iArr == null) {
            return null;
        }
        return Arrays.copyOf(iArr, iArr.length);
    }

    public void onSizeReady(int i, int i2) {
        this.size = new int[]{i, i2};
        this.viewTarget = null;
    }

    public void setView(View view) {
        if (this.size == null && this.viewTarget == null) {
            this.viewTarget = new SizeViewTarget(view);
            this.viewTarget.getSize(this);
        }
    }
}

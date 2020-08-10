package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import java.security.MessageDigest;

public class CenterCrop extends BitmapTransformation {

    /* renamed from: ID */
    private static final String f254ID = "com.bumptech.glide.load.resource.bitmap.CenterCrop";
    private static final byte[] ID_BYTES = f254ID.getBytes(CHARSET);

    /* access modifiers changed from: protected */
    public Bitmap transform(BitmapPool bitmapPool, Bitmap bitmap, int i, int i2) {
        return TransformationUtils.centerCrop(bitmapPool, bitmap, i, i2);
    }

    public boolean equals(Object obj) {
        return obj instanceof CenterCrop;
    }

    public int hashCode() {
        return f254ID.hashCode();
    }

    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }
}

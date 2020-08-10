package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import java.security.MessageDigest;

public class CircleCrop extends BitmapTransformation {

    /* renamed from: ID */
    private static final String f256ID = "com.bumptech.glide.load.resource.bitmap.CircleCrop.1";
    private static final byte[] ID_BYTES = f256ID.getBytes(CHARSET);
    private static final int VERSION = 1;

    /* access modifiers changed from: protected */
    public Bitmap transform(BitmapPool bitmapPool, Bitmap bitmap, int i, int i2) {
        return TransformationUtils.circleCrop(bitmapPool, bitmap, i, i2);
    }

    public boolean equals(Object obj) {
        return obj instanceof CircleCrop;
    }

    public int hashCode() {
        return f256ID.hashCode();
    }

    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }
}

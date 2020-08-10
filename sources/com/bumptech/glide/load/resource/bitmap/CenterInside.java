package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import java.security.MessageDigest;

public class CenterInside extends BitmapTransformation {

    /* renamed from: ID */
    private static final String f255ID = "com.bumptech.glide.load.resource.bitmap.CenterInside";
    private static final byte[] ID_BYTES = f255ID.getBytes(CHARSET);

    /* access modifiers changed from: protected */
    public Bitmap transform(BitmapPool bitmapPool, Bitmap bitmap, int i, int i2) {
        return TransformationUtils.centerInside(bitmapPool, bitmap, i, i2);
    }

    public boolean equals(Object obj) {
        return obj instanceof CenterInside;
    }

    public int hashCode() {
        return f255ID.hashCode();
    }

    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }
}

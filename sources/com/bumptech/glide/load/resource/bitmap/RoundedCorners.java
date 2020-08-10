package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Util;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

public final class RoundedCorners extends BitmapTransformation {

    /* renamed from: ID */
    private static final String f261ID = "com.bumptech.glide.load.resource.bitmap.RoundedCorners";
    private static final byte[] ID_BYTES = f261ID.getBytes(CHARSET);
    private final int roundingRadius;

    public RoundedCorners(int i) {
        Preconditions.checkArgument(i > 0, "roundingRadius must be greater than 0.");
        this.roundingRadius = i;
    }

    /* access modifiers changed from: protected */
    public Bitmap transform(BitmapPool bitmapPool, Bitmap bitmap, int i, int i2) {
        return TransformationUtils.roundedCorners(bitmapPool, bitmap, this.roundingRadius);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof RoundedCorners)) {
            return false;
        }
        if (this.roundingRadius == ((RoundedCorners) obj).roundingRadius) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Util.hashCode(f261ID.hashCode(), Util.hashCode(this.roundingRadius));
    }

    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
        messageDigest.update(ByteBuffer.allocate(4).putInt(this.roundingRadius).array());
    }
}

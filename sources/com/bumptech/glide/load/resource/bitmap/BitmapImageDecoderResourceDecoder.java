package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.ImageDecoder.OnHeaderDecodedListener;
import android.graphics.ImageDecoder.Source;
import android.util.Log;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPoolAdapter;
import com.bumptech.glide.load.resource.ImageDecoderResourceDecoder;
import java.io.IOException;

public final class BitmapImageDecoderResourceDecoder extends ImageDecoderResourceDecoder<Bitmap> {
    private static final String TAG = "BitmapImageDecoder";
    private final BitmapPool bitmapPool = new BitmapPoolAdapter();

    /* access modifiers changed from: protected */
    public Resource<Bitmap> decode(Source source, int i, int i2, OnHeaderDecodedListener onHeaderDecodedListener) throws IOException {
        Bitmap decodeBitmap = ImageDecoder.decodeBitmap(source, onHeaderDecodedListener);
        String str = TAG;
        if (Log.isLoggable(str, 2)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Decoded [");
            sb.append(decodeBitmap.getWidth());
            String str2 = "x";
            sb.append(str2);
            sb.append(decodeBitmap.getHeight());
            sb.append("] for [");
            sb.append(i);
            sb.append(str2);
            sb.append(i2);
            sb.append("]");
            Log.v(str, sb.toString());
        }
        return new BitmapResource(decodeBitmap, this.bitmapPool);
    }
}

package com.airbnb.lottie.manager;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable.Callback;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import com.airbnb.lottie.C0873L;
import com.airbnb.lottie.ImageAssetDelegate;
import com.airbnb.lottie.LottieImageAsset;
import com.fasterxml.jackson.core.JsonPointer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.objectweb.asm.Opcodes;

public class ImageAssetManager {
    private static final Object bitmapHashLock = new Object();
    private final Map<String, Bitmap> bitmaps = new HashMap();
    private final Context context;
    private ImageAssetDelegate delegate;
    private final Map<String, LottieImageAsset> imageAssets;
    private String imagesFolder;

    public ImageAssetManager(Callback callback, String str, ImageAssetDelegate imageAssetDelegate, Map<String, LottieImageAsset> map) {
        this.imagesFolder = str;
        if (!TextUtils.isEmpty(str)) {
            String str2 = this.imagesFolder;
            if (str2.charAt(str2.length() - 1) != '/') {
                StringBuilder sb = new StringBuilder();
                sb.append(this.imagesFolder);
                sb.append(JsonPointer.SEPARATOR);
                this.imagesFolder = sb.toString();
            }
        }
        if (!(callback instanceof View)) {
            Log.w(C0873L.TAG, "LottieDrawable must be inside of a view for images to work.");
            this.imageAssets = new HashMap();
            this.context = null;
            return;
        }
        this.context = ((View) callback).getContext();
        this.imageAssets = map;
        setDelegate(imageAssetDelegate);
    }

    public void setDelegate(ImageAssetDelegate imageAssetDelegate) {
        this.delegate = imageAssetDelegate;
    }

    public Bitmap updateBitmap(String str, Bitmap bitmap) {
        if (bitmap == null) {
            return (Bitmap) this.bitmaps.remove(str);
        }
        return putBitmap(str, bitmap);
    }

    public Bitmap bitmapForId(String str) {
        Bitmap bitmap = (Bitmap) this.bitmaps.get(str);
        if (bitmap != null) {
            return bitmap;
        }
        LottieImageAsset lottieImageAsset = (LottieImageAsset) this.imageAssets.get(str);
        if (lottieImageAsset == null) {
            return null;
        }
        ImageAssetDelegate imageAssetDelegate = this.delegate;
        if (imageAssetDelegate != null) {
            Bitmap fetchBitmap = imageAssetDelegate.fetchBitmap(lottieImageAsset);
            if (fetchBitmap != null) {
                putBitmap(str, fetchBitmap);
            }
            return fetchBitmap;
        }
        String fileName = lottieImageAsset.getFileName();
        Options options = new Options();
        options.inScaled = true;
        options.inDensity = Opcodes.IF_ICMPNE;
        boolean startsWith = fileName.startsWith("data:");
        String str2 = C0873L.TAG;
        if (!startsWith || fileName.indexOf("base64,") <= 0) {
            try {
                if (!TextUtils.isEmpty(this.imagesFolder)) {
                    AssetManager assets = this.context.getAssets();
                    StringBuilder sb = new StringBuilder();
                    sb.append(this.imagesFolder);
                    sb.append(fileName);
                    return putBitmap(str, BitmapFactory.decodeStream(assets.open(sb.toString()), null, options));
                }
                throw new IllegalStateException("You must set an images folder before loading an image. Set it with LottieComposition#setImagesFolder or LottieDrawable#setImagesFolder");
            } catch (IOException e) {
                Log.w(str2, "Unable to open asset.", e);
                return null;
            }
        } else {
            try {
                byte[] decode = Base64.decode(fileName.substring(fileName.indexOf(44) + 1), 0);
                return putBitmap(str, BitmapFactory.decodeByteArray(decode, 0, decode.length, options));
            } catch (IllegalArgumentException e2) {
                Log.w(str2, "data URL did not have correct base64 format.", e2);
                return null;
            }
        }
    }

    public void recycleBitmaps() {
        synchronized (bitmapHashLock) {
            Iterator it = this.bitmaps.entrySet().iterator();
            while (it.hasNext()) {
                ((Bitmap) ((Entry) it.next()).getValue()).recycle();
                it.remove();
            }
        }
    }

    public boolean hasSameContext(Context context2) {
        return (context2 == null && this.context == null) || (context2 != null && this.context.equals(context2));
    }

    private Bitmap putBitmap(String str, Bitmap bitmap) {
        Bitmap bitmap2;
        synchronized (bitmapHashLock) {
            bitmap2 = (Bitmap) this.bitmaps.put(str, bitmap);
        }
        return bitmap2;
    }
}

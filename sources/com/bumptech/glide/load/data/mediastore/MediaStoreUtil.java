package com.bumptech.glide.load.data.mediastore;

import android.net.Uri;
import com.google.firebase.analytics.FirebaseAnalytics.Param;

public final class MediaStoreUtil {
    private static final int MINI_THUMB_HEIGHT = 384;
    private static final int MINI_THUMB_WIDTH = 512;

    public static boolean isThumbnailSize(int i, int i2) {
        return i != Integer.MIN_VALUE && i2 != Integer.MIN_VALUE && i <= 512 && i2 <= MINI_THUMB_HEIGHT;
    }

    private MediaStoreUtil() {
    }

    public static boolean isMediaStoreUri(Uri uri) {
        if (uri != null) {
            if (Param.CONTENT.equals(uri.getScheme())) {
                if ("media".equals(uri.getAuthority())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isVideoUri(Uri uri) {
        return uri.getPathSegments().contains("video");
    }

    public static boolean isMediaStoreVideoUri(Uri uri) {
        return isMediaStoreUri(uri) && isVideoUri(uri);
    }

    public static boolean isMediaStoreImageUri(Uri uri) {
        return isMediaStoreUri(uri) && !isVideoUri(uri);
    }
}

package com.startapp.android.publish.ads.nativead;

import android.content.Context;
import android.graphics.Bitmap;
import com.startapp.android.publish.ads.nativead.StartAppNativeAd.C4819b;

/* compiled from: StartAppSDK */
public interface NativeAdInterface {
    C4819b getCampaignAction();

    String getCategory();

    String getDescription();

    Bitmap getImageBitmap();

    String getImageUrl();

    String getInstalls();

    String getPackacgeName();

    float getRating();

    Bitmap getSecondaryImageBitmap();

    String getSecondaryImageUrl();

    String getTitle();

    Boolean isApp();

    void sendClick(Context context);

    void sendImpression(Context context);
}

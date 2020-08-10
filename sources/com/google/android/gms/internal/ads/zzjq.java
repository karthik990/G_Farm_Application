package com.google.android.gms.internal.ads;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.C2520R;
import com.startapp.android.publish.common.model.AdPreferences;

@zzadh
public final class zzjq {
    private final AdSize[] zzarh;
    private final String zzye;

    public zzjq(Context context, AttributeSet attributeSet) {
        IllegalArgumentException illegalArgumentException;
        TypedArray obtainAttributes = context.getResources().obtainAttributes(attributeSet, C2520R.styleable.AdsAttrs);
        String string = obtainAttributes.getString(C2520R.styleable.AdsAttrs_adSize);
        String string2 = obtainAttributes.getString(C2520R.styleable.AdsAttrs_adSizes);
        boolean z = !TextUtils.isEmpty(string);
        boolean z2 = !TextUtils.isEmpty(string2);
        if (z && !z2) {
            this.zzarh = zzab(string);
        } else if (!z && z2) {
            this.zzarh = zzab(string2);
        } else if (z) {
            illegalArgumentException = new IllegalArgumentException("Either XML attribute \"adSize\" or XML attribute \"supportedAdSizes\" should be specified, but not both.");
            throw illegalArgumentException;
        } else {
            illegalArgumentException = new IllegalArgumentException("Required XML attribute \"adSize\" was missing.");
            throw illegalArgumentException;
        }
        this.zzye = obtainAttributes.getString(C2520R.styleable.AdsAttrs_adUnitId);
        if (TextUtils.isEmpty(this.zzye)) {
            throw new IllegalArgumentException("Required XML attribute \"adUnitId\" was missing.");
        }
    }

    private static AdSize[] zzab(String str) {
        String[] split = str.split("\\s*,\\s*");
        AdSize[] adSizeArr = new AdSize[split.length];
        int i = 0;
        while (true) {
            String str2 = "Could not parse XML attribute \"adSize\": ";
            if (i < split.length) {
                String trim = split[i].trim();
                if (trim.matches("^(\\d+|FULL_WIDTH)\\s*[xX]\\s*(\\d+|AUTO_HEIGHT)$")) {
                    String[] split2 = trim.split("[xX]");
                    split2[0] = split2[0].trim();
                    split2[1] = split2[1].trim();
                    try {
                        adSizeArr[i] = new AdSize("FULL_WIDTH".equals(split2[0]) ? -1 : Integer.parseInt(split2[0]), "AUTO_HEIGHT".equals(split2[1]) ? -2 : Integer.parseInt(split2[1]));
                    } catch (NumberFormatException unused) {
                        String valueOf = String.valueOf(trim);
                        throw new IllegalArgumentException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                    }
                } else if (AdPreferences.TYPE_BANNER.equals(trim)) {
                    adSizeArr[i] = AdSize.BANNER;
                } else if ("LARGE_BANNER".equals(trim)) {
                    adSizeArr[i] = AdSize.LARGE_BANNER;
                } else if ("FULL_BANNER".equals(trim)) {
                    adSizeArr[i] = AdSize.FULL_BANNER;
                } else if ("LEADERBOARD".equals(trim)) {
                    adSizeArr[i] = AdSize.LEADERBOARD;
                } else if ("MEDIUM_RECTANGLE".equals(trim)) {
                    adSizeArr[i] = AdSize.MEDIUM_RECTANGLE;
                } else if ("SMART_BANNER".equals(trim)) {
                    adSizeArr[i] = AdSize.SMART_BANNER;
                } else if ("WIDE_SKYSCRAPER".equals(trim)) {
                    adSizeArr[i] = AdSize.WIDE_SKYSCRAPER;
                } else if ("FLUID".equals(trim)) {
                    adSizeArr[i] = AdSize.FLUID;
                } else if ("ICON".equals(trim)) {
                    adSizeArr[i] = AdSize.zzup;
                } else {
                    String valueOf2 = String.valueOf(trim);
                    throw new IllegalArgumentException(valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2));
                }
                i++;
            } else if (adSizeArr.length != 0) {
                return adSizeArr;
            } else {
                String valueOf3 = String.valueOf(str);
                throw new IllegalArgumentException(valueOf3.length() != 0 ? str2.concat(valueOf3) : new String(str2));
            }
        }
    }

    public final String getAdUnitId() {
        return this.zzye;
    }

    public final AdSize[] zzi(boolean z) {
        if (z || this.zzarh.length == 1) {
            return this.zzarh;
        }
        throw new IllegalArgumentException("The adSizes XML attribute is only allowed on PublisherAdViews.");
    }
}

package com.google.android.gms.internal.ads;

import android.location.Location;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.NativeAdOptions.Builder;
import com.google.android.gms.ads.mediation.NativeMediationAdRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@zzadh
public final class zzyo implements NativeMediationAdRequest {
    private final int zzaqn;
    private final boolean zzaqz;
    private final int zzbur;
    private final Map<String, Boolean> zzbva = new HashMap();
    private final Date zzhl;
    private final Set<String> zzhn;
    private final boolean zzho;
    private final Location zzhp;
    private final zzpl zzyb;
    private final List<String> zzyc = new ArrayList();

    public zzyo(Date date, int i, Set<String> set, Location location, boolean z, int i2, zzpl zzpl, List<String> list, boolean z2) {
        Map<String, Boolean> map;
        String str;
        Boolean valueOf;
        this.zzhl = date;
        this.zzaqn = i;
        this.zzhn = set;
        this.zzhp = location;
        this.zzho = z;
        this.zzbur = i2;
        this.zzyb = zzpl;
        this.zzaqz = z2;
        if (list != null) {
            for (String str2 : list) {
                if (str2.startsWith("custom:")) {
                    String[] split = str2.split(":", 3);
                    if (split.length == 3) {
                        if ("true".equals(split[2])) {
                            map = this.zzbva;
                            str = split[1];
                            valueOf = Boolean.valueOf(true);
                        } else {
                            if ("false".equals(split[2])) {
                                map = this.zzbva;
                                str = split[1];
                                valueOf = Boolean.valueOf(false);
                            }
                        }
                        map.put(str, valueOf);
                    }
                } else {
                    this.zzyc.add(str2);
                }
            }
        }
    }

    public final float getAdVolume() {
        return zzmb.zziv().zzdo();
    }

    public final Date getBirthday() {
        return this.zzhl;
    }

    public final int getGender() {
        return this.zzaqn;
    }

    public final Set<String> getKeywords() {
        return this.zzhn;
    }

    public final Location getLocation() {
        return this.zzhp;
    }

    public final NativeAdOptions getNativeAdOptions() {
        if (this.zzyb == null) {
            return null;
        }
        Builder requestMultipleImages = new Builder().setReturnUrlsForImageAssets(this.zzyb.zzbjn).setImageOrientation(this.zzyb.zzbjo).setRequestMultipleImages(this.zzyb.zzbjp);
        if (this.zzyb.versionCode >= 2) {
            requestMultipleImages.setAdChoicesPlacement(this.zzyb.zzbjq);
        }
        if (this.zzyb.versionCode >= 3 && this.zzyb.zzbjr != null) {
            requestMultipleImages.setVideoOptions(new VideoOptions(this.zzyb.zzbjr));
        }
        return requestMultipleImages.build();
    }

    public final boolean isAdMuted() {
        return zzmb.zziv().zzdp();
    }

    public final boolean isAppInstallAdRequested() {
        List<String> list = this.zzyc;
        return list != null && (list.contains(ExifInterface.GPS_MEASUREMENT_2D) || this.zzyc.contains("6"));
    }

    public final boolean isContentAdRequested() {
        List<String> list = this.zzyc;
        return list != null && (list.contains(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE) || this.zzyc.contains("6"));
    }

    public final boolean isDesignedForFamilies() {
        return this.zzaqz;
    }

    public final boolean isTesting() {
        return this.zzho;
    }

    public final boolean isUnifiedNativeAdRequested() {
        List<String> list = this.zzyc;
        return list != null && list.contains("6");
    }

    public final int taggedForChildDirectedTreatment() {
        return this.zzbur;
    }

    public final boolean zzna() {
        List<String> list = this.zzyc;
        return list != null && list.contains(ExifInterface.GPS_MEASUREMENT_3D);
    }

    public final Map<String, Boolean> zznb() {
        return this.zzbva;
    }
}
